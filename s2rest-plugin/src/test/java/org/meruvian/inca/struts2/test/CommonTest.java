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
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.commons.StringUtils;

/**
 * @author Dian Aditya
 * 
 */
public class CommonTest {

	@Test
	public void testCamelCase() {
		String underscorses = "THIS_IS_WORD";
		String notreadable = "not*readable)wOrd";

		Assert.assertEquals(StringUtils.toCamelCase(underscorses), "ThisIsWord");
		Assert.assertEquals(StringUtils.toCamelCase(notreadable),
				"NotReadableWord");
	}

	@Test
	public void testConvertFromString() {
		Assert.assertEquals(HttpMethod.GET, HttpMethod.fromString("get"));
		Assert.assertEquals(HttpMethod.POST, HttpMethod.fromString("post"));
		Assert.assertNotSame(HttpMethod.GET, HttpMethod.fromString("post"));
		Assert.assertEquals(HttpMethod.ALL, HttpMethod.fromString("asdf"));
	}
}
