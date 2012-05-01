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

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.meruvian.inca.struts2.rest.commons.RestConstants;
import org.meruvian.inca.struts2.rest.transform.ResourceTransformer;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author Dian Aditya
 * 
 */
public class RestInterceptor extends AbstractInterceptor implements
		StrutsStatics, RestConstants {

	private TransformManager manager;

	@Inject
	public RestInterceptor(Container container) {
		this.manager = container.getInstance(TransformManager.class, container
				.getInstance(String.class, INCA_REST_TRANSFORM_MANAGER));
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) invocation
				.getInvocationContext().get(HTTP_REQUEST);

		ResourceTransformer processor = manager.getProcessorForRequest(request);

		if (processor != null) {
			Map<String, Object> stack = processor.deserialize(Map.class,
					request.getInputStream());

			invocation.getInvocationContext().getParameters().putAll(stack);

			ValueStack valueStack = invocation.getStack();
			for (Entry<String, Object> param : stack.entrySet()) {
				valueStack.setParameter(param.getKey(), param.getValue());
			}
		}

		return invocation.invoke();
	}
}
