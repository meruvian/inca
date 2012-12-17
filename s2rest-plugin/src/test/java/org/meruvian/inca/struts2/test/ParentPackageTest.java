/**
 * 
 */
package org.meruvian.inca.struts2.test;

import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.test.action.ParentPackageTestAction;

import com.opensymphony.xwork2.ActionProxy;

/**
 * @author Dian Aditya
 * 
 */
public class ParentPackageTest extends
		IncaJUnitTestCase<ParentPackageTestAction> {
	@Test
	public void testParentPackage() {
		ActionProxy proxy = getActionProxy("/parentpackage");

		Assert.assertEquals("test-default", proxy.getConfig().getPackageName());
	}

	protected String getConfigPath() {
		return "struts-plugin.xml,struts-parentpackage-test.xml";
	}
}
