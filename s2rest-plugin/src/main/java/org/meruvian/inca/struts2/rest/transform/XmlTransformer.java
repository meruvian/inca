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

import com.thoughtworks.xstream.XStream;

/**
 * @author Dian Aditya
 * 
 */
public class XmlTransformer implements ResourceTransformer {

	private XStream xStream;

	public XmlTransformer() {
		xStream = new XStream();
	}

	@Override
	public void serialize(Object object, OutputStream outputStream)
			throws IOException {
		xStream.toXML(object, outputStream);
	}

	@Override
	public <T> T deserialize(Class<T> clazz, InputStream inputStream)
			throws IOException {
		try {
			return (T) xStream.fromXML(inputStream, clazz.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getContentType() {
		return "application/xml";
	}

	@Override
	public String getExtension() {
		return "xml";
	}

}
