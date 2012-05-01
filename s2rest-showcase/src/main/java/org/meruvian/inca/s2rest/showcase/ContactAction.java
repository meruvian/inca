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
package org.meruvian.inca.s2rest.showcase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.inca.struts2.rest.annotation.ExceptionMapping;
import org.meruvian.inca.struts2.rest.annotation.ExceptionMappings;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;

import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/contact")
@Results({
		@Result(name = "redirect", type = "redirect", location = "/contact"),
		@Result(name = "form", location = "/WEB-INF/view/contact/contact-form.jsp") })
public class ContactAction implements SessionAware {

	@ActionParam("id")
	private String id = "";

	@ActionParam("contact")
	private Contact contact;

	@ActionParam("contacts")
	private List<Contact> contacts;

	@Action
	public ActionResult list() {
		return new ActionResult("/WEB-INF/view/contact/contact-list.jsp");
	}

	@Action(name = "/new", method = HttpMethod.GET)
	public String form() {
		return "form";
	}

	@Action(name = "/new", method = HttpMethod.POST)
	@ExceptionMappings(@ExceptionMapping(exception = "java.lang.Exception", result = "redirect"))
	public String save() {
		if (contact != null) {
			if (id.trim().equalsIgnoreCase("")) {
				contacts.add(contact);
			} else {
				contacts.set(new Integer(id), contact);
			}
		}

		return "redirect";
	}

	@Action(name = "/{id}")
	@ExceptionMappings(@ExceptionMapping(exception = "java.lang.Exception", result = "redirect"))
	public String edit() {
		contact = contacts.get(new Integer(id));

		return "form";
	}

	@Override
	public void setSession(Map<String, Object> session) {
		contacts = (List<Contact>) session.get("contact");
		if (contacts == null) {
			contacts = new ArrayList<Contact>();
			session.put("contact", contacts);
		}
	}
}
