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

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author Dian Aditya
 * 
 */
public class JsonTransformer implements ResourceTransformer {

	private ObjectMapper mapper;

	public JsonTransformer() {
		mapper = new ObjectMapper();
		mapper.configure(
				org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES,
				false);
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(
				org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
				false);
	}

	public void serialize(Object object, OutputStream outputStream)
			throws IOException {
		mapper.writeValue(outputStream, object);
	}

	public <T> T deserialize(Class<T> clazz, InputStream inputStream)
			throws IOException {

		return mapper.readValue(inputStream, clazz);
	}

	public String getContentType() {
		return "application/json";
	}

	public String getExtension() {
		return "json";
	}

}
