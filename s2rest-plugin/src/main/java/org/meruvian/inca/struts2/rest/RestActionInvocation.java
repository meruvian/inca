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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.meruvian.inca.struts2.rest.commons.RestConstants;
import org.meruvian.inca.struts2.rest.discoverer.ActionDetails;
import org.meruvian.inca.struts2.rest.discoverer.ActionFinder;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.DefaultActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * @author Dian Aditya
 * 
 */
public class RestActionInvocation extends DefaultActionInvocation implements
		RestConstants {

	private static final Logger LOG = LoggerFactory
			.getLogger(RestActionInvocation.class);

	public static final String SECRET_RESULT = "SECRET_RESULT";
	public static final String ACTION_RESULT = "ACTION_RESULT";

	private static String secretResult = "";

	protected ActionFinder actionFinder;
	protected TransformManager manager;
	protected ActionResult actionResult;
	protected ActionDetails details;

	/**
	 * @param extraContext
	 * @param pushAction
	 */
	public RestActionInvocation(Map<String, Object> extraContext,
			boolean pushAction) {
		super(extraContext, pushAction);
	}

	@Inject
	public void setContainer(Container cont) {
		this.actionFinder = cont.getInstance(ActionFinder.class,
				cont.getInstance(String.class, INCA_ACTION_FINDER));
		this.manager = cont.getInstance(TransformManager.class,
				cont.getInstance(String.class, INCA_REST_TRANSFORM_MANAGER));
		this.container = cont;
	}

	@Override
	public void init(ActionProxy proxy) {
		super.init(proxy);
		List<InterceptorMapping> interceptorList = new ArrayList<InterceptorMapping>(
				proxy.getConfig().getInterceptors());
		interceptorList.add(new InterceptorMapping("incaRest", container
				.inject(RestInterceptor.class)));
		interceptors = interceptorList.iterator();
	}

	protected String saveResult(ActionConfig actionConfig, Object methodResult) {
		if (methodResult instanceof Result) {
			this.explicitResult = (Result) methodResult;

			// Wire the result automatically
			container.inject(explicitResult);
			return null;
		} else if (methodResult instanceof ActionResult) {
			this.actionResult = (ActionResult) methodResult;

			return secretResult = UUID.randomUUID().toString();
		} else {
			return (String) methodResult;
		}
	}

	@Override
	public Result createResult() throws Exception {
		HttpServletRequest request = (HttpServletRequest) invocationContext
				.get(StrutsStatics.HTTP_REQUEST);

		if (manager.getProcessorForResponse(request) != null) {
			RestResult result = container.inject(RestResult.class);

			return result;
		}

		if (actionResult != null && secretResult.equals(resultCode)) {
			invocationContext.put(SECRET_RESULT, secretResult);
			invocationContext.put(ACTION_RESULT, actionResult);
		}

		return super.createResult();
	}

	protected void createAction(Map<String, Object> contextMap) {
		details = actionFinder
				.findActionClass(proxy.getConfig().getClassName());

		if (details != null) {
			try {
				action = objectFactory.buildBean(details.getActionClass(),
						contextMap);

				return;
			} catch (Exception e) {
				LOG.error("Problem while compiling new action class", e);
			}
		}

		super.createAction(contextMap);
	}
}
