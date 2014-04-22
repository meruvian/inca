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
package org.meruvian.inca.struts2.rest;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.struts2.interceptor.validation.AnnotationValidationInterceptor;
import org.meruvian.inca.struts2.rest.discoverer.ActionDetails;
import org.meruvian.inca.struts2.rest.discoverer.ActionMethodParameterDetails;

/**
 * @author Dian Aditya
 * 
 */
public class RestAnnotationValidationInterceptor extends
		AnnotationValidationInterceptor {
	private ActionDetails details;

	public void setDetails(ActionDetails details) {
		this.details = details;
	}

	@Override
	protected Method getActionMethod(Class actionClass, String methodName)
			throws NoSuchMethodException {
		try {
			return super.getActionMethod(actionClass, methodName);
		} catch (NoSuchMethodException e) {
			try {
				List<ActionMethodParameterDetails> params = details
						.getActionMethodDetails().getParameterDetails();
				Class<?>[] methodParams = new Class<?>[params.size()];

				int i = 0;
				for (ActionMethodParameterDetails param : params) {
					methodParams[i] = param.getType();

					i++;
				}

				return actionClass.getMethod(details.getActionMethodDetails()
						.getActionMethod(), methodParams);
			} catch (NoSuchMethodException ex) {
				return null;
			}
		}
	}
}
