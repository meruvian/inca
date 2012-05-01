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
package org.meruvian.inca.struts2.rest.commons;

/**
 * @author Dian Aditya
 * 
 */
public class StringUtils {

	protected StringUtils() {
	}

	public static String toCamelCase(String s) {
		String[] parts = s.split("[^a-zA-Z0-9]");
		String camelCaseString = "";
		for (String part : parts) {
			camelCaseString = camelCaseString + toProperCase(part);
		}
		return camelCaseString;
	}

	public static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	public static String prependWithSlash(String s) {
		if (s == null)
			return null;

		if (!s.startsWith("/"))
			s = "/" + s;
		if (s.endsWith("/"))
			s = s.substring(0, s.length() - 1);

		return s;
	}

}
