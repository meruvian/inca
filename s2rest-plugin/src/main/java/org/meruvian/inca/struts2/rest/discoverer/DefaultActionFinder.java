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
package org.meruvian.inca.struts2.rest.discoverer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;

import org.apache.struts2.StrutsConstants;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.inca.struts2.rest.annotation.ExceptionMappings;
import org.meruvian.inca.struts2.rest.annotation.Interceptors;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.inca.struts2.rest.commons.ActionUtils;
import org.meruvian.inca.struts2.rest.commons.RestConstants;
import org.meruvian.inca.struts2.rest.commons.StringUtils;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.PatternMatcher;
import com.opensymphony.xwork2.util.finder.Test;

/**
 * @author Dian Aditya
 * 
 */
public class DefaultActionFinder implements ActionFinder {

	private Test<String> filter;
	private PatternMatcher patternMatcher;

	private Map<String, ActionDetails> actionForRequest;
	private Map<String, ActionDetails> actionForClassName;
	private ClassPool pool;
	private StrutsAnnotationDiscoverer discoverer;

	private Map<String, Class<?>> actionClasses = new HashMap<String, Class<?>>();

	@Inject
	public DefaultActionFinder(Container container) throws Exception {
		this.filter = container.getInstance(Test.class, container.getInstance(
				String.class, RestConstants.INCA_ACTION_FILTER));
		this.patternMatcher = container.getInstance(PatternMatcher.class,
				container.getInstance(String.class,
						StrutsConstants.STRUTS_PATTERNMATCHER));
		this.pool = ClassPool.getDefault();
		this.discoverer = new StrutsAnnotationDiscoverer(filter);

		actionForClassName = new HashMap<String, ActionDetails>();
		actionForRequest = new ActionPool(patternMatcher);

		List<String> annotatedClasses = discoverer
				.discoverClassesForAnnotation(Action.class);
		for (String classname : annotatedClasses) {
			buildActionFromMethod(classname);
		}
	}

	protected void buildActionFromMethod(String classname) throws Exception {
		List<String> methods = discoverer.discoverAnnotatedMethods(classname,
				Action.class);

		CtClass actionclass = pool.get(classname);
		// Create proxyObject
		actionclass.setName(classname + "$$$ActionProxy");
		actionclass.setSuperclass(pool.get(classname));

		buildClassProperty(classname, actionclass);

		// if (methods.size() < 1)
		{
			ActionDetails details = buildActionDetails(actionclass, null);

			actionForRequest.put(details.getRequestUri(), details);
			actionForClassName.put(actionclass.getName(), details);
		}

		for (String method : methods) {
			CtMethod actionMethod = actionclass.getDeclaredMethod(method);
			ActionDetails details = buildActionDetails(actionclass,
					actionMethod);

			actionForRequest.put(details.getRequestUri(), details);
			actionForClassName.put(actionclass.getName(), details);
		}
	}

	private ActionDetails buildActionDetails(CtClass actionClass,
			CtMethod actionMethod) throws Exception {

		Action classAnnotation = ActionUtils.findActionAnnotation(actionClass);
		Action methodAnnotation = ActionUtils.findAnnotation(actionMethod,
				Action.class);
		Interceptors interceptors = ActionUtils.findAnnotation(actionMethod,
				Interceptors.class);
		ExceptionMappings exceptionMappings = ActionUtils.findAnnotation(
				actionMethod, ExceptionMappings.class);

		Results actionResults = ActionUtils.findAnnotation(actionClass,
				Results.class);
		Results methodResults = ActionUtils.findAnnotation(actionMethod,
				Results.class);

		ActionDetails details = new ActionDetails();

		if (methodAnnotation == null) {
			details.setActionAnnotation(classAnnotation);
			details.setActionMethod("execute");
		} else {
			details.setActionAnnotation(methodAnnotation);
			details.setActionMethod(actionMethod.getName());
		}

		Class<?> generatedActionclass = actionClasses
				.get(actionClass.getName());

		if (generatedActionclass == null) {
			generatedActionclass = actionClass.toClass();
			actionClasses.put(actionClass.getName(), generatedActionclass);
		}

		details.setActionClass(generatedActionclass);
		details.setNamespace(ActionUtils.getUriFromAnnotation(classAnnotation));
		details.setAction(ActionUtils.getUriFromAnnotation(methodAnnotation));
		details.setHttpMethod(details.getActionAnnotation().method());
		details.setInterceptors(interceptors);
		details.setExceptionMappings(exceptionMappings);

		if (actionResults != null) {
			for (Result result : actionResults.value()) {
				details.getActionResults().put(result.name(), result);
			}
		}

		if (methodResults != null) {
			for (Result result : methodResults.value()) {
				details.getActionResults().put(result.name(), result);
			}
		}

		return details;
	}

	public void buildClassProperty(String classname, CtClass actionClass)
			throws Exception {
		List<String> fields = discoverer.discoverAnnotatedFields(classname,
				ActionParam.class);

		for (String fieldName : fields) {
			CtField field = actionClass.getDeclaredField(fieldName);
			ActionParam param = (ActionParam) field
					.getAnnotation(ActionParam.class);

			String fieldValue = param.value();

			CtMethod getter = CtNewMethod.getter(
					"get" + StringUtils.toCamelCase(fieldValue), field);
			CtMethod setter = CtNewMethod.setter(
					"set" + StringUtils.toCamelCase(fieldValue), field);

			actionClass.addMethod(getter);
			actionClass.addMethod(setter);
		}
	}

	@Override
	public ActionDetails findAction(String namespace, String action,
			String httpMethod) {
		ActionDetails details = new ActionDetails(namespace, action,
				HttpMethod.fromString(httpMethod));

		if (details != null) {
			if (HttpMethod.ALL.equals(details.getHttpMethod())
					|| HttpMethod.fromString(httpMethod).equals(
							details.getHttpMethod()))

				return actionForRequest.get(details.getRequestUri());
		}

		return null;
	}

	@Override
	public ActionDetails findActionClass(String className) {
		return actionForClassName.get(className);
	}

	protected static class ActionPool extends HashMap<String, ActionDetails> {

		private PatternMatcher patternMatcher;

		public ActionPool(PatternMatcher patternMatcher) {
			this.patternMatcher = patternMatcher;
		}

		@Override
		public ActionDetails get(Object key) {
			if (key == null)
				return null;

			for (String k : keySet()) {
				Object o = patternMatcher.compilePattern(k);
				Map<String, String> map = new HashMap<String, String>();
				if (patternMatcher.match(map, (String) key, o)) {
					ActionDetails details = super.get(k);
					details.setParameter(map);
					return details;
				}
			}

			ActionDetails action = super.get(key);
			if (action != null)
				return action;

			return null;
		}
	}

}
