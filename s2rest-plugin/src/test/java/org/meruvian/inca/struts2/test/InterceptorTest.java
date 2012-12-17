/**
 * 
 */
package org.meruvian.inca.struts2.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.test.action.InterceptorTestAction;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;

/**
 * @author Dian Aditya
 * 
 */
public class InterceptorTest extends IncaJUnitTestCase<InterceptorTestAction> {
	@Test
	public void testInterceptor() {
		ActionProxy proxy = getActionProxy("/interceptor");

		List<InterceptorMapping> interceptors = proxy.getConfig()
				.getInterceptors();
		Assert.assertEquals("alias", interceptors.get(0).getName());
		Assert.assertEquals("chain", interceptors.get(1).getName());
		Assert.assertEquals("cookie", interceptors.get(2).getName());
	}

	@Test
	public void testDefaultInterceptor() {
		ActionProxy proxy = getActionProxy("/interceptor/default");

		List<InterceptorMapping> interceptors = proxy.getConfig()
				.getInterceptors();

		String[] interceptorStack = { "exception", "alias", "servletConfig",
				"i18n", "prepare", "chain", "scopedModelDriven", "modelDriven",
				"fileUpload", "checkbox", "multiselect", "staticParams",
				"actionMappingParams", "params", "conversionError",
				"validation", "workflow", "debugging", };

		int i = 0;
		for (InterceptorMapping mapping : interceptors) {
			Assert.assertEquals(interceptorStack[i++], mapping.getName());
		}
	}
}
