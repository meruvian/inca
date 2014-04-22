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

import java.util.LinkedHashMap;
import java.util.Map;

import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.inca.struts2.test.action.model.TestActionModel;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/methodparameter")
public class MethodParameterTestAction {
	private Map<String, Object> model = new LinkedHashMap<String, Object>();

	@Action
	public ActionResult index(@ActionParam("parameter") String param,
			@ActionParam("i") int i, @ActionParam("md") TestActionModel model) {
		this.model.put("param", param);
		this.model.put("i", i);
		this.model.put("name", model.getName());
		this.model.put("desc", model.getDescription());

		return new ActionResult("freemarker", "/view/default_test_result.ftl")
				.addToModel("mod", model).addToModel("p", param);
	}

	public Map<String, Object> getModel() {
		return model;
	}
}
