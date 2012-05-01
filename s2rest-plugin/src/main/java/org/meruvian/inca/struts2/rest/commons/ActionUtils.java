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
package org.meruvian.inca.struts2.rest.commons;

import java.lang.annotation.Annotation;

import javassist.CtClass;
import javassist.CtMember;

import org.meruvian.inca.struts2.rest.annotation.Action;

/**
 * @author Dian Aditya
 * 
 */
public class ActionUtils {
	protected ActionUtils() {
	}

	public static String getUriFromAnnotation(Action action) {
		String actionUri = "";

		if (action != null) {
			actionUri = StringUtils.prependWithSlash(action.name());
		}

		return actionUri;
	}

	public static <T> T findAnnotation(CtMember ct,
			Class<? extends Annotation> clazz) throws ClassNotFoundException {
		if (ct == null || clazz == null)
			return null;

		return (T) ct.getAnnotation(clazz);
	}

	public static <T> T findAnnotation(CtClass ct,
			Class<? extends Annotation> clazz) throws ClassNotFoundException {
		if (ct == null || clazz == null)
			return null;

		return (T) ct.getAnnotation(clazz);
	}

	public static Action findActionAnnotation(CtClass ct)
			throws ClassNotFoundException {
		return (Action) ct.getAnnotation(Action.class);
	}
}
