/**
 * Copyright 2012 BlueOxygen Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
