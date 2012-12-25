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
package org.meruvian.inca.struts2.test.action;

import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.ExceptionMapping;
import org.meruvian.inca.struts2.rest.annotation.ExceptionMappings;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/exceptionmapping")
@Results(@Result(name = "success", type = "freemarker", location = "/view/default_test_result.ftl"))
public class ExceptionMappingTestAction {
	@Action
	@ExceptionMappings({ @ExceptionMapping(exception = "java.lang.RuntimeException", result = "success") })
	public String index() {
		throw new RuntimeException("Error");
	}

	@Action(name = "/exception")
	public String withoutExceptionMapping() {
		throw new RuntimeException("Error");
	}
}
