/**
 * 
 */
package org.meruvian.inca.struts2.test.action;

import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.ParentPackage;

/**
 * @author Dian Aditya
 * 
 */
@ParentPackage("test-default")
@Action(name = "/parentpackage")
public class ParentPackageTestAction {
	@Action
	public String index() {
		return null;
	}
}
