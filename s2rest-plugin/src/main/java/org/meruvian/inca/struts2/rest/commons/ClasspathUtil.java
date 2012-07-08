/**
 * Copyright 2012 Meruvian
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

import java.io.IOException;

import org.apache.commons.lang.ObjectUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterface;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterfaceDelegate;
import com.opensymphony.xwork2.util.finder.UrlSet;

/**
 * @author Dian Aditya
 * 
 */
public class ClasspathUtil {
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static ClassLoaderInterface getClassLoaderInterface() {
		return getClassLoaderInterface(getClassLoader());
	}

	public static ClassLoaderInterface getClassLoaderInterface(
			ClassLoader classLoader) {
		ClassLoaderInterface classLoaderInterface = null;
		ActionContext ctx = ActionContext.getContext();
		if (ctx != null)
			classLoaderInterface = (ClassLoaderInterface) ctx
					.get(ClassLoaderInterface.CLASS_LOADER_INTERFACE);

		return (ClassLoaderInterface) ObjectUtils.defaultIfNull(
				classLoaderInterface, new ClassLoaderInterfaceDelegate(
						classLoader));
	}

	public static UrlSet getUrlSet() throws IOException {
		return getUrlSet(getClassLoaderInterface());
	}

	public static UrlSet getUrlSet(ClassLoaderInterface loaderInterface)
			throws IOException {
		UrlSet urlSet = new UrlSet(getClassLoaderInterface());
		urlSet.excludeJavaEndorsedDirs();
		urlSet.excludeJavaExtDirs();

		return urlSet;
	}
}
