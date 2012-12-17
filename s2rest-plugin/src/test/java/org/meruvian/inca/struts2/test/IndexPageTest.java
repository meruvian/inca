/**
 * 
 */
package org.meruvian.inca.struts2.test;

import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.test.action.IndexPageTestAction;

import com.opensymphony.xwork2.ActionProxy;

/**
 * @author Dian Aditya
 * 
 */
public class IndexPageTest extends IncaJUnitTestCase<IndexPageTestAction> {
	@Test
	public void testIndexPage() {
		ActionProxy proxy = getActionProxy("/");

		Assert.assertEquals(IndexPageTestAction.class, proxy.getAction()
				.getClass());
	}

	@Test
	public void testNeverScannedAction() {
		exception.expectMessage("There is no Action mapped");

		getActionProxy("/never_scanned");
	}
}
