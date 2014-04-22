/**
 * Copyright 2012 BlueOxygen Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meruvian.inca.struts2.rest;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.AnnotationValidationInterceptor;
import org.apache.struts2.util.RegexPatternMatcher;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.inca.struts2.rest.annotation.ExceptionMapping;
import org.meruvian.inca.struts2.rest.annotation.ExceptionMappings;
import org.meruvian.inca.struts2.rest.annotation.InterceptorRef;
import org.meruvian.inca.struts2.rest.annotation.Interceptors;
import org.meruvian.inca.struts2.rest.annotation.Param;
import org.meruvian.inca.struts2.rest.commons.ActionUtils;
import org.meruvian.inca.struts2.rest.commons.ActionUtils.ActionMethodParameterCallback;
import org.meruvian.inca.struts2.rest.commons.RestConstants;
import org.meruvian.inca.struts2.rest.commons.StringUtils;
import org.meruvian.inca.struts2.rest.discoverer.ActionDetails;
import org.meruvian.inca.struts2.rest.discoverer.ActionFinder;
import org.meruvian.inca.struts2.rest.discoverer.ActionMethodDetails;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.UnknownHandler;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.ActionConfig.Builder;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.opensymphony.xwork2.config.providers.InterceptorBuilder;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author Dian Aditya
 * 
 */
public class RestUnknownHandler implements UnknownHandler {

	private static final Logger LOG = LoggerFactory
			.getLogger(RestUnknownHandler.class);

	protected String defaultParentPackage;
	protected ActionFinder actionFinder;
	protected Configuration configuration;
	protected PackageConfig parentPackage;
	protected ObjectFactory objectFactory;
	protected ActionDetails details;
	private RegexPatternMatcher matcher = new RegexPatternMatcher();
	private Container container;

	private static final Map<Class<?>, Object> primitiveDefaults;

	static {
		Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
		map.put(Boolean.TYPE, Boolean.FALSE);
		map.put(Byte.TYPE, Byte.valueOf((byte) 0));
		map.put(Short.TYPE, Short.valueOf((short) 0));
		map.put(Character.TYPE, new Character((char) 0));
		map.put(Integer.TYPE, Integer.valueOf(0));
		map.put(Long.TYPE, Long.valueOf(0L));
		map.put(Float.TYPE, new Float(0.0f));
		map.put(Double.TYPE, new Double(0.0));
		map.put(BigInteger.class, new BigInteger("0"));
		map.put(BigDecimal.class, new BigDecimal(0.0));
		primitiveDefaults = Collections.unmodifiableMap(map);
	}

	@Inject
	public RestUnknownHandler(
			@Inject(RestConstants.INCA_DEFAULT_PARENT_PACKAGE) String defaultParentPackage) {
		this.defaultParentPackage = defaultParentPackage;
	}

	@Inject
	public void setContainer(Container cont) {
		this.actionFinder = cont.getInstance(ActionFinder.class, cont
				.getInstance(String.class, RestConstants.INCA_ACTION_FINDER));
		this.configuration = cont.getInstance(Configuration.class);
		this.objectFactory = cont.getInstance(ObjectFactory.class);
		this.container = cont;
		setParentPackage(defaultParentPackage);
	}

	protected void setParentPackage(String parentPackage) {
		this.parentPackage = configuration.getPackageConfig(parentPackage);

		if (this.parentPackage == null) {
			throw new ConfigurationException("Unknown default parent package ["
					+ defaultParentPackage + "]");
		}
	}

	@Override
	public ActionConfig handleUnknownAction(String namespace, String actionName)
			throws XWorkException {
		namespace = StringUtils.prependWithSlash(namespace);
		actionName = StringUtils.prependWithSlash(actionName);
		details = actionFinder.findAction(namespace, actionName,
				ServletActionContext.getRequest().getMethod());

		if (details == null) {
			return null;
		}

		if (details.getParentPackage() != null) {
			defaultParentPackage = details.getParentPackage();
			setParentPackage(defaultParentPackage);
		}

		String actionClass = details.getActionClass().getName();

		actionFinder.findActionClass(actionClass).setParameter(
				details.getParameter());

		Builder builder = new Builder(defaultParentPackage, details
				.getActionMethodDetails().getActionMethod(), actionClass);
		builder.methodName(details.getActionMethodDetails().getActionMethod());

		Map<String, String> params = details.getParameter();
		buildActionParam(builder, details.getActionAnnotation(), params);
		buildInterceptors(builder, details.getInterceptors(), params);
		buildExceptionMappings(builder, details.getExceptionMappings(), params);

		ActionContext context = ActionContext.getContext();
		if (context != null) {
			context.put(RestConstants.INCA_ACTION_DETAILS, details);
		}

		return builder.build();
	}

	private void buildParam(Builder builder, Param param,
			Map<String, String> params) {
		String paramValue = param.value();

		if (matcher.isLiteral(paramValue)) {
			builder.addParam(param.name(), param.value());
		} else {
			Pattern p = Pattern.compile("\\{(.*?)\\}");
			Matcher m = p.matcher(paramValue);

			StringBuilder sb = new StringBuilder();
			int i = 0;
			for (; m.find();) {
				String replacement = params.get(m.group(1));

				sb.append(paramValue.substring(i, m.start()));
				if (replacement == null)
					sb.append(m.group(0));
				else {
					sb.append(replacement);
				}
				i = m.end();
			}
			sb.append(paramValue.substring(i, paramValue.length()));
			builder.addParam(param.name(), sb.toString());
		}
	}

	protected void buildActionParam(Builder builder, Action action,
			Map<String, String> params) {

		for (Param param : action.params()) {
			buildParam(builder, param, params);
		}

		params.remove("0");
		builder.addParams(params);
	}

	protected void buildInterceptors(Builder builder,
			Interceptors interceptors, Map<String, String> params) {
		if (interceptors == null) {
			List<InterceptorMapping> mappings = InterceptorBuilder
					.constructInterceptorReference(parentPackage, parentPackage
							.getDefaultInterceptorRef(), null, builder.build()
							.getLocation(), configuration.getContainer()
							.getInstance(ObjectFactory.class));

			for (InterceptorMapping mapping : mappings) {
				if (mapping.getInterceptor() instanceof AnnotationValidationInterceptor) {
					RestAnnotationValidationInterceptor interceptor = container
							.inject(RestAnnotationValidationInterceptor.class);
					interceptor.setDetails(details);

					mapping = new InterceptorMapping(mapping.getName(),
							interceptor);
				}

				builder.addInterceptor(mapping);
			}

			return;
		}

		for (InterceptorRef ref : interceptors.value()) {
			Map<String, String> temp = new HashMap<String, String>();

			for (Param param : ref.params()) {
				temp.put(param.name(), param.value());
			}

			List<InterceptorMapping> mappings = InterceptorBuilder
					.constructInterceptorReference(
							parentPackage,
							ref.name(),
							temp,
							builder.build().getLocation(),
							configuration.getContainer().getInstance(
									ObjectFactory.class));

			builder.addInterceptors(mappings);
		}
	}

	protected void buildExceptionMappings(Builder builder,
			ExceptionMappings mappings, Map<String, String> params) {
		if (mappings == null)
			return;

		for (ExceptionMapping mapping : mappings.value()) {
			ExceptionMappingConfig.Builder ex = new ExceptionMappingConfig.Builder(
					null, mapping.exception(), mapping.result());
			for (Param param : mapping.params()) {
				ex.addParam(param.name(), param.value());
			}

			builder.addExceptionMapping(ex.build());
		}
	}

	@Override
	public Result handleUnknownResult(ActionContext actionContext,
			String actionName, ActionConfig actionConfig, String resultCode)
			throws XWorkException {

		Map<String, ResultTypeConfig> results = parentPackage
				.getAllResultTypeConfigs();

		String secretResult = (String) actionContext
				.get(RestActionInvocation.SECRET_RESULT);
		ActionResult result = (ActionResult) actionContext
				.get(RestActionInvocation.ACTION_RESULT);

		ResultConfig.Builder builder = null;

		if (result != null && resultCode.equals(secretResult)) {
			ResultTypeConfig config = results.get(result.getType());
			builder = new ResultConfig.Builder(null, config.getClassName())
					.addParams(result.getParameters()).addParam(
							config.getDefaultResultParam(),
							result.getLocation());
		} else if (details != null) {
			Map<String, org.meruvian.inca.struts2.rest.annotation.Result> actionResults = details
					.getActionResults();

			org.meruvian.inca.struts2.rest.annotation.Result actionResult;
			if ((actionResult = actionResults.get(resultCode)) != null) {
				ResultTypeConfig config = results.get(actionResult.type());
				builder = new ResultConfig.Builder(null, config.getClassName())
						.addParam(config.getDefaultResultParam(),
								actionResult.location());
				for (Param param : actionResult.params()) {
					builder.addParam(param.name(), param.value());
				}
			}
		}

		if (builder != null) {
			try {
				return objectFactory.buildResult(builder.build(),
						actionContext.getContextMap());
			} catch (Exception e) {
				throw new XWorkException("Unable to create result");
			}
		}

		return null;
	}

	@Override
	public Object handleUnknownActionMethod(Object action, String methodName)
			throws NoSuchMethodException {
		if (details == null)
			return null;

		ActionContext context = ActionContext.getContext();
		ActionInvocation invocation = context.getActionInvocation();
		final ValueStack stack = invocation.getStack();

		ActionMethodDetails methodDetails = details.getActionMethodDetails();
		Method method = action.getClass().getMethod(
				methodDetails.getActionMethod(), methodDetails.getParameters());
		final Object[] parameters = new Object[methodDetails.getParameters().length];

		ActionMethodParameterCallback<ActionParam> callback = new ActionMethodParameterCallback<ActionParam>() {
			int i = 0;

			@Override
			public void methodParameterDiscovered(ActionParam annotation,
					Class<?> type) {
				String expr = annotation == null ? type.getName() : annotation
						.value();

				Object parameter = stack.findValue(expr, type);
				if (type.isPrimitive() && parameter == null) {
					parameter = primitiveDefaults.get(type);
				}

				parameters[i] = parameter;

				i++;
			}
		};

		ActionUtils.findAnnotatedMethodParam(ActionParam.class,
				methodDetails.getParameterDetails(), callback);

		try {
			return method.invoke(action, parameters);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);

			return null;
		}
	}
}
