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
package org.meruvian.inca.struts2.test.action;

import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;

/**
 * 
 * @author Dian Aditya
 * 
 */
@Action(name = "/resultmodel/{st}")
public class ActionResultModelTestAction {
	private int st;

	@Action
	public ActionResult index() {
		String[] arr = new String[st];
		for (int i = 0; i < st; i++) {
			arr[i] = i + "";
		}

		return new ActionResult("freemarker", "/view/action_result_model.ftl")
				.addToModel("modelFromResult", "modelFromResult").addToModel(
						"arr", arr);
	}

	public void setSt(int st) {
		this.st = st;
	}
}
