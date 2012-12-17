/**
 * 
 */
package org.meruvian.inca.struts2.test;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsJUnit4TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * @author Dian Aditya
 * 
 */
public abstract class IncaJUnitTestCase<T> extends StrutsJUnit4TestCase<T> {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		ServletActionContext.setServletContext(servletContext);
		ServletActionContext.setRequest(request);
		ServletActionContext.setResponse(response);
	}
}
