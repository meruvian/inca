/**
 * 
 */
package org.meruvian.inca.struts2.test.action;

import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Param;
import org.meruvian.inca.struts2.test.action.model.TestActionModel;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/defaultpattern")
public class WildcardTestAction {
	private TestActionModel model = new TestActionModel();

	@Action(name = "/*/*", params = {
			@Param(name = "model.name", value = "{1}{2}d"),
			@Param(name = "model.description", value = "{2}") })
	public String index() {
		return null;
	}

	public TestActionModel getModel() {
		return model;
	}

	public void setModel(TestActionModel model) {
		this.model = model;
	}

}
