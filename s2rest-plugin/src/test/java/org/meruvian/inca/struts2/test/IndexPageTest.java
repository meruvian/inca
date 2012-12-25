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
import org.meruvian.inca.struts2.test.action.IndexPageTestAction;

import com.opensymphony.xwork2.ActionProxy;

/**
 * @author Dian Aditya
 * 
 */
public class IndexPageTest extends IncaJUnitTestCase<IndexPageTestAction> {
	@Test
	public void testIndexPage() {
		ActionProxy proxy = getActionProxy("/");

		Assert.assertEquals(IndexPageTestAction.class, proxy.getAction()
				.getClass());
	}

	@Test
	public void testNeverScannedAction() {
		exception.expectMessage("There is no Action mapped");

		getActionProxy("/never_scanned");
	}
}
