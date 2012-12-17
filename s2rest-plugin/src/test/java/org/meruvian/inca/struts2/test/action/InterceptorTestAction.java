/**
 * 
 */
package org.meruvian.inca.struts2.test.action;

import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.InterceptorRef;
import org.meruvian.inca.struts2.rest.annotation.Interceptors;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/interceptor")
public class InterceptorTestAction {
	@Action
	@Interceptors({ @InterceptorRef(name = "alias"),
			@InterceptorRef(name = "chain"), @InterceptorRef(name = "cookie") })
	public String index() {
		return null;
	}

	@Action(name = "/default")
	public String defaultInterceptor() {
		return null;
	}
}
