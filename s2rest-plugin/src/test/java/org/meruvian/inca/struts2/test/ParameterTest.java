/**
 * 
 */
package org.meruvian.inca.struts2.test;

import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.test.action.ParameterTestAction;

/**
 * @author Dian Aditya
 * 
 */
public class ParameterTest extends IncaJUnitTestCase<ParameterTestAction> {
	@Test
	public void testUrlParameter() throws Exception {
		request.setMethod("POST");
		request.setParameter("model.description", "abc");

		executeAction("/parameter/test/123");
		ParameterTestAction action = getAction();
		Assert.assertEquals("123", action.getModel().getName());
		Assert.assertEquals("abc", action.getModel().getDescription());
	}
}
