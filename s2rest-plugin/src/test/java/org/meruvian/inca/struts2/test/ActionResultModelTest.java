/**
 * Copyright 2014 BlueOxygen Technology
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

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.test.action.ActionResultModelTestAction;

import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * @author Dian Aditya
 * 
 */
public class ActionResultModelTest extends
		IncaJUnitTestCase<ActionResultModelTestAction> {
	@Test
	public void testActionResultModel() throws Exception {
		ActionProxy proxy = getActionProxy("/resultmodel/3");
		proxy.execute();

		ValueStack stack = proxy.getInvocation().getStack();
		Assert.assertEquals("modelFromResult",
				stack.findValue("modelFromResult"));
		Assert.assertArrayEquals(new String[] { "0", "1", "2" },
				((String[]) stack.findValue("arr")));
	}

	@Test
	public void testActionResultTemplate() throws Exception {
		String output = executeAction("/resultmodel/3");

		Assert.assertEquals("modelFromResult\n\n0\n1\n2\n", output);
	}

	@Test
	public void testJsonOutput() throws Exception {
		request.addHeader("Accept", "application/json");

		String output = executeAction("/resultmodel/4");

		ObjectMapper mapper = new ObjectMapper();
		String[] arr = new String[4];
		for (int i = 0; i < 4; i++) {
			arr[i] = i + "";
		}
		ActionResult result = new ActionResult();
		result.addToModel("modelFromResult", "modelFromResult").addToModel(
				"arr", arr);

		Assert.assertEquals(mapper.writeValueAsString(result.getModel()),
				output);
	}
}
