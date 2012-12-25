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

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.test.action.JsonOutputTestAction;
import org.meruvian.inca.struts2.test.action.model.TestActionModel;

/**
 * @author Dian Aditya
 * 
 */
public class JsonOutputTest extends IncaJUnitTestCase<JsonOutputTestAction> {
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testJsonResponse() throws Exception {
		request.addHeader("Accept", "application/json");
		String result = executeAction("/json/responseonly");

		TestActionModel model = new TestActionModel();
		model.setName("Inca");
		model.setDescription("S2RestPlugin");

		Assert.assertEquals(mapper.writeValueAsString(model), result);
	}

	@Test
	public void testJsonParameter() throws Exception {
		TestActionModel model = new TestActionModel();
		model.setName("Inca");
		model.setDescription("S2RestPlugin");

		request.addHeader("Accept", "application/json");
		request.setContentType("application/json");
		request.setMethod("POST");
		request.setContent(mapper.writeValueAsBytes(model));

		String result = executeAction("/json/requestresponse");
		JsonOutputTestAction action = getAction();

		Assert.assertEquals("Inca", action.getModel().getName());
		Assert.assertEquals("S2RestPlugin", action.getModel().getDescription());
		Assert.assertEquals(mapper.writeValueAsString(model), result);
	}
}
