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
package org.meruvian.inca.struts2.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dian Aditya
 * 
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
	public enum HttpMethod {
		GET, POST, PUT, DELETE, HEAD, OPTION, ALL;

		public static HttpMethod fromString(String method) {
			for (HttpMethod m : values()) {
				if (m.toString().equalsIgnoreCase(method))
					return m;
			}

			return ALL;
		}
	}

	String name() default "/";

	HttpMethod method() default HttpMethod.ALL;

	Param[] params() default {};
}
