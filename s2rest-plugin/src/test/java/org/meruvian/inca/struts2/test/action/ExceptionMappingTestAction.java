/**
 * 
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
