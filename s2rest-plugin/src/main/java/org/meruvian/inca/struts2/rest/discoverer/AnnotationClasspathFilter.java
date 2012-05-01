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
package org.meruvian.inca.struts2.rest.discoverer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.meruvian.inca.struts2.rest.commons.RestConstants;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.finder.Test;

/**
 * @author Dian Aditya
 * 
 */
public class AnnotationClasspathFilter implements Test<String> {

	private transient String[] ignoredPackages = { "javax", "java", "sun",
			"com.sun", "javassist" };
	private transient String[] includePackages = {};

	@Inject
	public AnnotationClasspathFilter(
			@Inject(RestConstants.INCA_PACKAGE_LOCATORS) String excludePackage,
			@Inject(RestConstants.INCA_EXCLUDE_PACKAGE_PREFIX) String packageLocators) {

		List<String> excluded = new ArrayList<String>();
		excluded.addAll(Arrays.asList(ignoredPackages));
		excluded.addAll(Arrays.asList(excludePackage.split("\\s*[,]\\s*")));
		ignoredPackages = excluded.toArray(new String[0]);

		includePackages = packageLocators.split("\\s*[,]\\s*");
	}

	@Override
	public boolean test(String filename) {
		if (filename.endsWith(".class")) {
			if (filename.startsWith("/")) {
				filename = filename.substring(1);
			}
			if (match(filename.replace('/', '.'))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @param replace
	 * @return
	 */
	private boolean match(String replace) {

		for (String ignored : ignoredPackages) {
			if (replace.startsWith(ignored + "."))
				return false;
		}

		// for (String include : includePackages) {
		// if (replace.lastIndexOf(".") >= 0) {
		// String r = replace.substring(0, replace.lastIndexOf("."));
		// if (r.endsWith(include))
		// return true;
		// }
		// }

		return true;
	}

}
