/**
 * 
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