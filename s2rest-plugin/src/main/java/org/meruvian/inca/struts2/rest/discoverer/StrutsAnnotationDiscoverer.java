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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javassist.ClassPool;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;

import org.apache.commons.lang.ObjectUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterface;
import com.opensymphony.xwork2.util.finder.ClassLoaderInterfaceDelegate;
import com.opensymphony.xwork2.util.finder.Test;
import com.opensymphony.xwork2.util.finder.UrlSet;

/**
 * @author Dian Aditya
 * 
 */
public class StrutsAnnotationDiscoverer implements AnnotationDiscoverer {

	private Test<String> filter;
	private ClassPool classPool;

	public StrutsAnnotationDiscoverer(Test<String> filter) {
		this.filter = filter;
		classPool = ClassPool.getDefault();
	}

	@Override
	public List<String> discoverClassesForAnnotation(
			Class<? extends java.lang.annotation.Annotation> annotation)
			throws Exception {
		List<String> annotatedClasses = new ArrayList<String>();

		for (InputStream stream : getClassFiles(getClassLoaderInterface(),
				filter)) {
			ClassFile classFile = new ClassFile(new DataInputStream(stream));

			Set<Annotation> annotations = new HashSet<Annotation>();

			AnnotationsAttribute visibleAnnotataion = (AnnotationsAttribute) classFile
					.getAttribute(AnnotationsAttribute.visibleTag);
			AnnotationsAttribute invisibleAnnotataion = (AnnotationsAttribute) classFile
					.getAttribute(AnnotationsAttribute.invisibleTag);

			if (visibleAnnotataion != null) {
				annotations.addAll(Arrays.asList(visibleAnnotataion
						.getAnnotations()));
			}

			if (invisibleAnnotataion != null) {
				annotations.addAll(Arrays.asList(invisibleAnnotataion
						.getAnnotations()));
			}

			for (Annotation a : annotations) {
				if (annotation.getName().equals(a.getTypeName())) {
					annotatedClasses.add(classFile.getName());
				}
			}
		}

		return annotatedClasses;
	}

	@Override
	public List<String> discoverAnnotatedMethods(String className,
			Class<? extends java.lang.annotation.Annotation> annotation)
			throws Exception {
		List<String> annotatedMethods = new ArrayList<String>();

		ClassFile classFile = classPool.get(className).getClassFile();
		for (MethodInfo info : (List<MethodInfo>) classFile.getMethods()) {
			Set<Annotation> annotations = new HashSet<Annotation>();

			AnnotationsAttribute visibleAnnotataion = (AnnotationsAttribute) info
					.getAttribute(AnnotationsAttribute.visibleTag);
			AnnotationsAttribute invisibleAnnotataion = (AnnotationsAttribute) info
					.getAttribute(AnnotationsAttribute.invisibleTag);

			if (visibleAnnotataion != null) {
				annotations.addAll(Arrays.asList(visibleAnnotataion
						.getAnnotations()));
			}

			if (invisibleAnnotataion != null) {
				annotations.addAll(Arrays.asList(invisibleAnnotataion
						.getAnnotations()));
			}

			for (Annotation a : annotations) {
				if (annotation.getName().equals(a.getTypeName())) {
					annotatedMethods.add(info.getName());
				}
			}
		}

		return annotatedMethods;
	}

	@Override
	public List<String> discoverAnnotatedFields(String className,
			Class<? extends java.lang.annotation.Annotation> annotation)
			throws Exception {
		List<String> annotatedFields = new ArrayList<String>();

		ClassFile classFile = classPool.get(className).getClassFile();
		for (FieldInfo info : (List<FieldInfo>) classFile.getFields()) {
			Set<Annotation> annotations = new HashSet<Annotation>();

			AnnotationsAttribute visibleAnnotataion = (AnnotationsAttribute) info
					.getAttribute(AnnotationsAttribute.visibleTag);
			AnnotationsAttribute invisibleAnnotataion = (AnnotationsAttribute) info
					.getAttribute(AnnotationsAttribute.invisibleTag);

			if (visibleAnnotataion != null) {
				annotations.addAll(Arrays.asList(visibleAnnotataion
						.getAnnotations()));
			}

			if (invisibleAnnotataion != null) {
				annotations.addAll(Arrays.asList(invisibleAnnotataion
						.getAnnotations()));
			}

			for (Annotation a : annotations) {
				if (annotation.getName().equals(a.getTypeName())) {
					annotatedFields.add(info.getName());
				}
			}
		}

		return annotatedFields;
	}

	protected List<InputStream> getClassFiles(
			ClassLoaderInterface loaderInterface, final Test<String> test)
			throws Exception {
		List<InputStream> classStream = new ArrayList<InputStream>();

		UrlSet urlSet = getUrlSet(loaderInterface);

		for (URL url : urlSet.getUrls()) {
			classPool.appendClassPath(url.getPath());

			String urlString = url.toString();
			if (urlString.endsWith("!/")) {
				urlString = urlString.substring(4);
				urlString = urlString.substring(0, urlString.length() - 2);
				url = new URL(urlString);
			}

			if (!urlString.endsWith("/")) {
				JarFile file = new JarFile(new File(url.getPath()));
				Enumeration<JarEntry> enumeration = file.entries();
				while (enumeration.hasMoreElements()) {
					JarEntry jarEntry = enumeration.nextElement();
					if (test.test(jarEntry.getName())) {
						classStream.add(file.getInputStream(jarEntry));
					}
				}
			} else {
				File file = new File(url.getPath());
				if (file.isDirectory()) {
					for (File f : getFilesFromDirectory(file, test)) {
						classStream.add(new FileInputStream(f));
					}
				}
			}
		}

		return classStream;
	}

	protected List<File> getFilesFromDirectory(File dir, final Test<String> test) {
		List<File> files = new ArrayList<File>();

		if (dir.isDirectory()) {
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return test.test(pathname.getPath());
				}
			};

			for (File f : dir.listFiles()) {
				if (!f.isDirectory() && test.test(f.getPath())) {
					files.add(f);
				}
				if (f.isDirectory()) {
					files.addAll(getFilesFromDirectory(f, test));
				}
			}
		}

		return files;
	}

	protected UrlSet getUrlSet(ClassLoaderInterface loaderInterface)
			throws IOException {
		UrlSet urlSet = new UrlSet(getClassLoaderInterface());
		urlSet.excludeJavaEndorsedDirs();
		urlSet.excludeJavaExtDirs();

		return urlSet;
	}

	protected ClassLoaderInterface getClassLoaderInterface() {
		ClassLoaderInterface classLoaderInterface = null;
		ActionContext ctx = ActionContext.getContext();
		if (ctx != null)
			classLoaderInterface = (ClassLoaderInterface) ctx
					.get(ClassLoaderInterface.CLASS_LOADER_INTERFACE);

		return (ClassLoaderInterface) ObjectUtils.defaultIfNull(
				classLoaderInterface, new ClassLoaderInterfaceDelegate(
						getClassLoader()));
	}

	protected ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
