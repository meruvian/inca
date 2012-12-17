/**
 * 
 */
package org.meruvian.inca.struts2.test.action;

import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.test.action.model.TestActionModel;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/parameter")
public class ParameterTestAction {
	private TestActionModel model;

	@Action(name = "/test/{model.name}", method = HttpMethod.POST)
	public String detail() {
		return null;
	}

	public TestActionModel getModel() {
		return model;
	}

	public void setModel(TestActionModel model) {
		this.model = model;
	}
}
