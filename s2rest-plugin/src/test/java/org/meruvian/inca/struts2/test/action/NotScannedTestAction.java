/**
 * 
 */
package org.meruvian.inca.struts2.test.action;

import org.meruvian.inca.struts2.rest.annotation.Action;

/**
 * This class should never be scanned by the plugin
 * 
 * @author Dian Aditya
 * 
 */
public class NotScannedTestAction {
	@Action(name = "never_scanned")
	public String execute() {
		return null;
	}
}
