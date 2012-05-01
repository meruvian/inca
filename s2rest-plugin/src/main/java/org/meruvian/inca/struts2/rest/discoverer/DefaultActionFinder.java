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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

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

	private Map<ActionDetails, ActionDetails> actionForRequest;
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

		for (String method : methods) {
			CtMethod actionMethod = actionclass.getDeclaredMethod(method);
			ActionDetails details = buildActionDetails(actionclass,
					actionMethod);

			// actionForRequest.put(details.getRequestUri(), details);
			actionForRequest.put(details, details);
			actionForClassName.put(actionclass.getName(), details);
		}

		{
			ActionDetails details = buildActionDetails(actionclass, null);

			actionForRequest.put(details, details);
			actionForClassName.put(actionclass.getName(), details);
		}
	}

	private ActionDetails buildActionDetails(CtClass actionClass,
			CtMethod actionMethod) throws Exception {

		if (actionMethod == null) {
			try {
				actionMethod = actionClass.getDeclaredMethod("execute");
			} catch (NotFoundException e) {
			}
		}

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
			details.setActionMethod("execute");
			details.setActionAnnotation(classAnnotation);
		} else {
			details.setActionMethod(actionMethod.getName());
			details.setActionAnnotation(methodAnnotation);
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

		return actionForRequest.get(details);

	}

	@Override
	public ActionDetails findActionClass(String className) {
		return actionForClassName.get(className);
	}

	protected static class ActionPool extends
			LinkedHashMap<ActionDetails, ActionDetails> {

		private PatternMatcher patternMatcher;

		public ActionPool(PatternMatcher patternMatcher) {
			this.patternMatcher = patternMatcher;
		}

		private boolean httpMethodMatch(ActionDetails details,
				ActionDetails request) {
			return (HttpMethod.ALL.equals(details.getHttpMethod()) || request
					.getHttpMethod().equals(details.getHttpMethod()));
		}

		@Override
		public ActionDetails get(Object key) {
			if (key == null || !(key instanceof ActionDetails))
				return null;

			ActionDetails detailsKey = (ActionDetails) key;
			for (java.util.Map.Entry<ActionDetails, ActionDetails> e : entrySet()) {
				ActionDetails details = e.getKey();

				Object o = patternMatcher.compilePattern(details
						.getRequestUri());

				Map<String, String> map = new HashMap<String, String>();
				if (details.getRequestUri().equals(detailsKey.getRequestUri())) {
					if (httpMethodMatch(details, detailsKey))
						return e.getValue();
				} else if (patternMatcher.match(map,
						detailsKey.getRequestUri(), o)) {
					if (httpMethodMatch(details, detailsKey)) {
						details = e.getValue();
						details.setParameter(map);

						return details;
					}
				}
			}

			return null;
		}

		// @Override
		// public ActionDetails get(Object key) {
		// if (key == null)
		// return null;
		//
		// for (String k : keySet()) {
		// Object o = patternMatcher.compilePattern(k);
		// Map<String, String> map = new HashMap<String, String>();
		// if (patternMatcher.match(map, (String) key, o)) {
		// ActionDetails details = super.get(k);
		// details.setParameter(map);
		// return details;
		// }
		// }
		//
		// ActionDetails action = super.get(key);
		// if (action != null)
		// return action;
		//
		// return null;
		// }
	}

}
