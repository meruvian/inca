/**
 * 
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
