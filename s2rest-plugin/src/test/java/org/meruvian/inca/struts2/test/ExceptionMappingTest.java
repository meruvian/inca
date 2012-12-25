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