/**
 * 
 */
package org.meruvian.inca.struts2.test;

import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.test.action.ResultTestAction;

/**
 * @author Dian Aditya
 * 
 */
public class ResultTest extends IncaJUnitTestCase<ResultTestAction> {
	@Test
	public void testClassLevelResult() throws Exception {
		request.setMethod("POST");
		executeAction("/result");

		Assert.assertTrue(response.getContentType().contains(
				"application/javascript"));
	}

	@Test
	public void testMethodLevelResult() throws Exception {
		request.setMethod("GET");
		executeAction("/result");

		Assert.assertTrue(
				"Expected : text/plain, output " + response.getContentType(),
				response.getContentType().contains("text/plain"));
	}

	@Test
	public void testActionResult() throws Exception {
		request.setMethod("HEAD");
		executeAction("/result");

		Assert.assertTrue(response.getContentType().contains(
				"application/octet-stream"));
	}
}
