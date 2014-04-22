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
package org.meruvian.inca.struts2.rest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Dian Aditya
 * 
 */
public class ActionResult {
	private String type = "dispatcher";
	private String location;
	private Map<String, String> parameters = new HashMap<String, String>();
	private Map<String, Object> model = new LinkedHashMap<String, Object>();

	public static final String ROOT_MODEL = "ROOT";

	public ActionResult() {
	}

	public ActionResult(String location) {
		this.location = location;
	}

	public ActionResult(String type, String location) {
		this.type = type;
		this.location = location;
	}

	public ActionResult(String location, Map<String, Object> model) {
		this.location = location;
		this.model = model;
	}

	public ActionResult(String type, String location, Map<String, Object> model) {
		this.type = type;
		this.location = location;
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public ActionResult setType(String type) {
		this.type = type;

		return this;
	}

	public String getLocation() {
		return location;
	}

	public ActionResult setLocation(String location) {
		this.location = location;

		return this;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public ActionResult setParameters(Map<String, String> parameters) {
		this.parameters = parameters;

		return this;
	}

	public ActionResult addParameter(String key, String value) {
		parameters.put(key, value);

		return this;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public ActionResult addToModel(String key, Object value) {
		model.put(key, value);

		return this;
	}

}
