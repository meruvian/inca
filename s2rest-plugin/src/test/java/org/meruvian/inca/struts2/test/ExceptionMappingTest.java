/**
 * 
 */
package org.meruvian.inca.struts2.test;

import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.test.action.ExceptionMappingTestAction;

import com.opensymphony.xwork2.ActionProxy;

/**
 * @author Dian Aditya
 * 
 */
public class ExceptionMappingTest extends
		IncaJUnitTestCase<ExceptionMappingTestAction> {
	@Test
	public void testExceptionMapping() throws Exception {
		ActionProxy proxy = getActionProxy("/exceptionmapping");

		Assert.assertEquals("success", proxy.execute());
	}

	@Test
	public void testWithoutExceptionMapping() throws Exception {
		ActionProxy proxy = getActionProxy("/exceptionmapping/exception");

		exception.expect(RuntimeException.class);
		exception.expectMessage("Error");

		proxy.execute();
	}
}