/*******************************************************************************
 * Copyright (c) 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.osgi.framework.internal.defaultadaptor;

import java.io.IOException;
import java.security.ProtectionDomain;
import org.eclipse.osgi.framework.adaptor.ClassLoaderDelegate;
import org.eclipse.osgi.framework.adaptor.core.AbstractBundleData;

public class AdaptorElementFactory {

	public AbstractBundleData getBundleData(DefaultAdaptor adaptor, long id) throws IOException {
		return new DefaultBundleData(adaptor, id);
	}

	/**
	  * Creates the ClassLoader for the BundleData.  The ClassLoader created
	  * must use the <code>ClassLoaderDelegate</code> to delegate class, resource
	  * and library loading.  The delegate is responsible for finding any resource
	  * or classes imported by the bundle or provided by bundle fragments or 
	  * bundle hosts.  The <code>ProtectionDomain</code> domain must be used
	  * by the Classloader when defining a class.  
	  * @param delegate The <code>ClassLoaderDelegate</code> to delegate to.
	  * @param domain The <code>ProtectionDomain</code> to use when defining a class.
	  * @param bundleclasspath An array of bundle classpaths to use to create this
	  * classloader.  This is specified by the Bundle-ClassPath manifest entry.
	  * @param data the bundle data with which this classloader will be associated.
	  * @return The new ClassLoader for the BundleData.
	  */
	public org.eclipse.osgi.framework.adaptor.BundleClassLoader createClassLoader(ClassLoaderDelegate delegate, ProtectionDomain domain, String[] bundleclasspath, DefaultBundleData data) {
		return new DefaultClassLoader(delegate, domain, bundleclasspath, data);
	}

}
