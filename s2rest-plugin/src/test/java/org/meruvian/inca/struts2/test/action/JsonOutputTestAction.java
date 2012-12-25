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
package org.meruvian.inca.struts2.test.action;

import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.InterceptorRef;
import org.meruvian.inca.struts2.rest.annotation.Interceptors;
import org.meruvian.inca.struts2.test.action.model.TestActionModel;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/json")
public class JsonOutputTestAction implements ModelDriven<TestActionModel> {
	private TestActionModel model = new TestActionModel();

	@Action(name = "/responseonly")
	@Interceptors(@InterceptorRef(name = "modelDrivenStack"))
	public ActionResult resp() {
		model = new TestActionModel();
		model.setName("Inca");
		model.setDescription("S2RestPlugin");

		return new ActionResult("freemarker", "/view/default_test_result.ftl");
	}

	@Action(name = "/requestresponse", method = HttpMethod.POST)
	@Interceptors(@InterceptorRef(name = "modelDrivenStack"))
	public ActionResult reqresp() {
		return new ActionResult("freemarker", "/view/default_test_result.ftl");
	}

	public TestActionModel getModel() {
		return model;
	}
}
