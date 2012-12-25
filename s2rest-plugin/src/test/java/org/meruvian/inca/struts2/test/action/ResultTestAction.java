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
import org.meruvian.inca.struts2.rest.annotation.Param;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/result")
@Results({ @Result(name = "success", type = "freemarker", params = {
		@Param(name = "location", value = "/view/default_test_result.ftl"),
		@Param(name = "contentType", value = "application/javascript") }) })
public class ResultTestAction {

	@Action(method = HttpMethod.GET)
	@Results({ @Result(name = "index", type = "freemarker", params = {
			@Param(name = "location", value = "/view/default_test_result.ftl"),
			@Param(name = "contentType", value = "text/plain") }) })
	public String indexGet() {
		return "index";
	}

	@Action(method = HttpMethod.POST)
	public String indexPost() {
		return "success";
	}

	@Action(method = HttpMethod.HEAD)
	public ActionResult indexHead() {
		return new ActionResult("freemarker", "/view/default_test_result.ftl")
				.addParameter("contentType", "application/octet-stream");
	}
}