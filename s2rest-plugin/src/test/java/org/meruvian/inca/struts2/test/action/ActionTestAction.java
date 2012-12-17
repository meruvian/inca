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
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/default")
public class ActionTestAction {

	@Action(name = "/list")
	public String list() {
		return null;
	}

	@Action(method = HttpMethod.GET)
	public String defaultActionMethod() {
		return null;
	}

	@Action(name = "detail/{param1}")
	public String detail() {
		return null;
	}

	@Action(name = "detail/1")
	public String detail1() {
		return null;
	}
}
