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

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.meruvian.inca.struts2.test.action.MethodParameterTestAction;

import com.opensymphony.xwork2.ActionProxy;

/**
 * @author Dian Aditya
 * 
 */
public class MethodParameterTest extends
		IncaJUnitTestCase<MethodParameterTestAction> {

	@Test
	public void testAction() throws Exception {
		request.setParameter("parameter", "pp");
		request.setParameter("md.name", "Name");
		request.setParameter("md.description", "Description");
		request.setParameter("i", "100");

		ActionProxy proxy = getActionProxy("/methodparameter");
		proxy.execute();

		MethodParameterTestAction action = (MethodParameterTestAction) proxy
				.getAction();
		Map<String, Object> model = action.getModel();
		Assert.assertEquals("pp", model.get("param"));
		Assert.assertEquals("Name", model.get("name"));
		Assert.assertEquals("Description", model.get("desc"));
		Assert.assertEquals(100, model.get("i"));
	}
}
