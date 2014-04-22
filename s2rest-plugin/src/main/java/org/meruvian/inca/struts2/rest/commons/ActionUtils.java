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
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import javassist.CtClass;
import javassist.CtMember;

import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.discoverer.ActionMethodParameterDetails;

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

	@Deprecated
	public static <T extends Annotation> T findAnnotation(CtMember ct,
			Class<T> clazz) throws ClassNotFoundException {
		if (ct == null || clazz == null)
			return null;

		return (T) ct.getAnnotation(clazz);
	}

	@Deprecated
	public static <T extends Annotation> T findAnnotation(CtClass ct,
			Class<T> clazz) throws ClassNotFoundException {
		if (ct == null || clazz == null)
			return null;

		return (T) ct.getAnnotation(clazz);
	}

	@Deprecated
	public static Action findActionAnnotation(CtClass ct)
			throws ClassNotFoundException {
		return (Action) ct.getAnnotation(Action.class);
	}

	public static <T extends Annotation> T findAnnotation(
			AnnotatedElement element, Class<T> annotationClass) {
		if (element == null)
			return null;

		return element.getAnnotation(annotationClass);
	}

	public static <T extends Annotation> void findAnnotatedMethodParam(
			Class<T> annotation,
			Collection<ActionMethodParameterDetails> parameterDetails,
			ActionMethodParameterCallback<T> callback) {
		for (ActionMethodParameterDetails pd : parameterDetails) {
			Class<?> parameter = pd.getType();
			T param = null;
			for (Annotation a : pd.getAnnotations()) {
				if (a.annotationType().equals(annotation)) {
					param = (T) a;

					break;
				}
			}

			callback.methodParameterDiscovered(param, parameter);
		}
	}

	public static interface ActionMethodParameterCallback<T extends Annotation> {
		void methodParameterDiscovered(T annotation, Class<?> type);
	}
}
