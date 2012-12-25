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
