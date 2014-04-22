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

import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.test.action.ActionTestAction;

import com.opensymphony.xwork2.ActionProxy;

/**
 * @author Dian Aditya
 * 
 */
public class ActionTest extends IncaJUnitTestCase<ActionTestAction> {
	@Test
	public void testHttpMethod() {
		testMethod("POST");
		testMethod("GET");
		testMethod("HEAD");
	}

	private void testMethod(String method) {
		request.setMethod(method);
		ActionProxy proxy = getActionProxy("/default/list");

		Assert.assertEquals("list", proxy.getMethod());
		Assert.assertEquals("/default", proxy.getNamespace());
	}

	@Test
	public void testDefaultActionMethod() throws Exception {
		request.setMethod("GET");
		ActionProxy proxy = getActionProxy("/default");

		Assert.assertEquals("defaultActionMethod", proxy.getMethod());

		exception
				.expectMessage("There is no Action mapped for namespace / and action name default");
		request.setMethod("POST");
		proxy = getActionProxy("/default");
	}

	@Test
	public void testUrlHandling() {
		ActionProxy proxy = getActionProxy("/default/detail/1");
		Assert.assertEquals("detail1", proxy.getMethod());

		proxy = getActionProxy("/default/detail/2");
		Assert.assertEquals("detail", proxy.getMethod());
	}

	@Test
	public void testStringResult() throws Exception {
		request.setMethod("GET");

		Assert.assertNull(getActionProxy("/default").execute());
	}
}