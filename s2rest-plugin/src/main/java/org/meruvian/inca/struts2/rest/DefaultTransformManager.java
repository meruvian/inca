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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.meruvian.inca.struts2.rest.commons.RestConstants;
import org.meruvian.inca.struts2.rest.transform.ResourceTransformer;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;

/**
 * @author Dian Aditya
 * 
 */
public class DefaultTransformManager implements TransformManager {

	private Map<String, ResourceTransformer> handlerByExtension = new HashMap<String, ResourceTransformer>();
	private Map<String, ResourceTransformer> handlerByContentType = new HashMap<String, ResourceTransformer>();
	private boolean handleByExtension = false;
	private String extensionPrefix = ".";

	@Inject
	public void setContainer(Container container) {
		Set<String> handlerNames = container
				.getInstanceNames(ResourceTransformer.class);

		for (String name : handlerNames) {
			ResourceTransformer handler = container.getInstance(
					ResourceTransformer.class, name);
			if (handler.getExtension() != null)
				handlerByExtension.put(handler.getExtension(), handler);
			if (handler.getContentType() != null)
				handlerByContentType.put(handler.getContentType(), handler);
		}

		handleByExtension = container
				.getInstance(String.class, RestConstants.INCA_HANDLE_BY_HEADER)
				.trim().equalsIgnoreCase("false");
	}

	@Inject(value = RestConstants.INCA_REST_EXTENSION_PREFIX, required = false)
	public void setExtensionPrefix(String extensionPrefix) {
		this.extensionPrefix = extensionPrefix;
	}

	public String getExtensionPrefix() {
		return extensionPrefix;
	}

	public String findExtension(String url) {
		int mid = url.lastIndexOf(extensionPrefix);
		url = url.substring(mid + 1);

		if (mid >= 0) {
			return url;
		}

		return null;
	}

	protected ResourceTransformer getHandlerByExtension(String extension) {
		return handlerByExtension.get(extension);
	}

	protected ResourceTransformer getHandlerByContentType(String contentType) {
		return handlerByContentType.get(contentType);
	}

	public ResourceTransformer getProcessorForResponse(HttpServletRequest request) {
		String extension = findExtension(request.getServletPath());

		if (handleByExtension && (extension != null)) {
			return getHandlerByExtension(extension);
		}

		return getHandlerByContentType(request.getHeader("Accept"));
	}

	@Override
	public ResourceTransformer getProcessorForRequest(HttpServletRequest request) {
		return getHandlerByContentType(request.getContentType());
	}

	public boolean isHandleByUrlExtension() {
		return handleByExtension;
	}

}
