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
package org.meruvian.inca.struts2.rest.transform;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Handles metadata response from object and vice versa.
 * 
 * @author Dian Aditya
 * 
 */
public interface ResourceTransformer {

	/**
	 * Writes an object to outputstream
	 * 
	 * @param object
	 *            An object to write to the stream
	 * @param outputStream
	 *            The outputstream, usually the response
	 * @throws IOException
	 */
	void serialize(Object object, OutputStream outputStream) throws IOException;

	/**
	 * Method to deserialize stream (it can be json, xml, or whatever) into a
	 * type, typically a bean or action
	 * 
	 * @param clazz
	 * @param inputStream
	 *            The inputsream, usually the request
	 * @return The generic object specified by generic type of first parameter
	 * @throws IOException
	 */
	<T> T deserialize(Class<T> clazz, InputStream inputStream)
			throws IOException;

	/**
	 * 
	 * @return The mime type
	 */
	String getContentType();

	/**
	 * 
	 * @return The extension of request uri eg. .json, .xml
	 */
	String getExtension();
}
