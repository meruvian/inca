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
import java.util.Map;

/**
 * @author Dian Aditya
 * 
 */
public class ActionResult {
	private String type = "dispatcher";
	private String location;
	private Map<String, String> parameters = new HashMap<String, String>();

	public ActionResult() {
	}

	public ActionResult(String location) {
		this.location = location;
	}

	public ActionResult(String type, String location) {
		this.type = type;
		this.location = location;
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

	public Map<String, String> getParameter() {
		return parameters;
	}

	public ActionResult setParameter(Map<String, String> parameters) {
		this.parameters = parameters;

		return this;
	}

	public ActionResult addParameter(String key, String value) {
		parameters.put(key, value);

		return this;
	}

}
