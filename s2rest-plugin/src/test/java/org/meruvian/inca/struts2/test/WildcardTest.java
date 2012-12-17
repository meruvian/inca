/**
 * 
 */
package org.meruvian.inca.struts2.test;

import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.test.action.WildcardTestAction;

/**
 * @author Dian Aditya
 * 
 */
public class WildcardTest extends IncaJUnitTestCase<WildcardTestAction> {
	@Test
	public void testParameter() throws Exception {
		executeAction("/defaultpattern/param1/param2");

		WildcardTestAction action = getAction();
		Assert.assertEquals("param1param2d", action.getModel().getName());
		Assert.assertEquals("param2", action.getModel().getDescription());
	}

	protected String getConfigPath() {
		return "struts-plugin.xml,struts-wildcard-test.xml";
	}
}
