/**
 * Copyright 2014 BlueOxygen Technology
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dian Aditya
 * 
 */
public class ActionMethodDetails {
	private String actionMethod;
	private List<ActionMethodParameterDetails> parameterDetails = new ArrayList<ActionMethodParameterDetails>();

	public ActionMethodDetails() {
	}

	public ActionMethodDetails(String actionMethod) {
		this.actionMethod = actionMethod;
	}

	public ActionMethodDetails(String actionMethod,
			List<ActionMethodParameterDetails> parameterDetails) {
		this.actionMethod = actionMethod;
		this.parameterDetails = parameterDetails;
	}

	public String getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}

	public List<ActionMethodParameterDetails> getParameterDetails() {
		return parameterDetails;
	}

	public void setParameterDetails(
			List<ActionMethodParameterDetails> parameterDetails) {
		this.parameterDetails = parameterDetails;
	}

	public Class<?>[] getParameters() {
		Class<?>[] parameters = new Class<?>[parameterDetails.size()];

		int i = 0;
		for (ActionMethodParameterDetails details : parameterDetails) {
			parameters[i] = details.getType();

			i++;
		}

		return parameters;
	}
}
