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
import java.util.Map;

import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.ExceptionMappings;
import org.meruvian.inca.struts2.rest.annotation.Interceptors;
import org.meruvian.inca.struts2.rest.annotation.Result;

/**
 * @author Dian Aditya
 * 
 */
public class ActionDetails {
	private Class<?> actionClass;
	private ActionMethodDetails actionMethodDetails = new ActionMethodDetails();
	private Action actionAnnotation;
	private Map<String, Result> actionResults = new HashMap<String, Result>();
	private Interceptors interceptors;
	private ExceptionMappings exceptions;
	private String parentPackage;
	private String namespace;
	private String action;
	private HttpMethod httpMethod;
	private Map<String, String> parameter = new HashMap<String, String>();

	public ActionDetails(String namespace, String action, HttpMethod httpMethod) {
		this.namespace = namespace;
		this.action = action;
		this.httpMethod = httpMethod;
	}

	public ActionDetails() {

	}

	public Class<?> getActionClass() {
		return actionClass;
	}

	public void setActionClass(Class<?> actionClass) {
		this.actionClass = actionClass;
	}

	public ActionMethodDetails getActionMethodDetails() {
		return actionMethodDetails;
	}

	public void setActionMethodDetails(ActionMethodDetails actionMethodDetails) {
		this.actionMethodDetails = actionMethodDetails;
	}

	public Action getActionAnnotation() {
		return actionAnnotation;
	}

	public void setActionAnnotation(Action actionAnnotation) {
		this.actionAnnotation = actionAnnotation;
	}

	public Map<String, Result> getActionResults() {
		return actionResults;
	}

	public void setActionResults(Map<String, Result> actionResults) {
		this.actionResults = actionResults;
	}

	public Interceptors getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(Interceptors interceptors) {
		this.interceptors = interceptors;
	}

	public ExceptionMappings getExceptionMappings() {
		return exceptions;
	}

	public void setExceptionMappings(ExceptionMappings exceptions) {
		this.exceptions = exceptions;
	}

	public String getParentPackage() {
		return parentPackage;
	}

	public void setParentPackage(String parentPackage) {
		this.parentPackage = parentPackage;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getRequestUri() {
		return namespace + action;
	}

	public Map<String, String> getParameter() {
		return parameter;
	}

	/**
	 * @param parameter
	 *            the parameter to set
	 */
	public void setParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}
}
