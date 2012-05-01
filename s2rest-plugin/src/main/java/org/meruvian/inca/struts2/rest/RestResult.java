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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.meruvian.inca.struts2.rest.commons.RestConstants;
import org.meruvian.inca.struts2.rest.transform.ResourceTransformer;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author Dian Aditya
 * 
 */
public class RestResult extends StrutsResultSupport {

	private Logger LOG = LoggerFactory.getLogger(RestResult.class);
	private TransformManager manager;

	@Inject
	public RestResult(Container container) {
		this.manager = container.getInstance(TransformManager.class, container
				.getInstance(String.class,
						RestConstants.INCA_REST_TRANSFORM_MANAGER));
	}

	protected void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {

		ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context
				.get(HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) context
				.get(HTTP_RESPONSE);

		Object model = invocation.getAction();
		if (model instanceof ModelDriven) {
			model = ((ModelDriven) model).getModel();
		}

		if (model instanceof Map) {
			Map map = (Map) model;
			if (map.get("root") != null)
				model = map.get("root");
		}

		ResourceTransformer processor = manager.getProcessorForResponse(request);
		if (processor != null) {
			response.setContentType(processor.getContentType());
			processor.serialize(model, response.getOutputStream());
			response.getOutputStream().close();
		} else {
			LOG.error("Unable to serialize object");
		}
	}

}
