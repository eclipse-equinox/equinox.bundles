/*******************************************************************************
 * Copyright (c) 2010, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.bidi.internal;

import java.util.Locale;
import org.eclipse.osgi.framework.log.FrameworkLog;
import org.eclipse.osgi.framework.log.FrameworkLogEntry;
import org.eclipse.osgi.service.localization.LocaleProvider;
import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides services related to OSGi bundles.
 */
public class STextActivator implements BundleActivator {

	private ServiceTracker logTracker = null;
	private BundleContext bundleContext;
	private static STextActivator instance;

	public STextActivator() {
		instance = this; // there is only one bundle activator
	}

	public void start(BundleContext context) throws Exception {
		bundleContext = context;
		instance = this;
	}

	public void stop(BundleContext context) throws Exception {
		if (logTracker != null) {
			logTracker.close();
			logTracker = null;
		}
		bundleContext = null;
	}

	/**
	 * @return the bundle instance, or <code>null</code> iff OSGi is not running
	 */
	public static STextActivator getInstance() {
		return instance;
	}

	/**
	 * Returns the value of the specified property. If OSGi is not running or the key is not found in
	 * the Framework properties, the system properties are then searched.
	 * <p>
	 * This method can be used without OSGi running.
	 * </p>
	 *
	 * @param key the name of the requested property
	 * @return the value of the requested property, or {@code null} if the property is undefined
	 */
	public static String getProperty(String key) {
		if (instance != null)
			return instance.bundleContext.getProperty(key);
		return System.getProperty(key);
	}

	public Locale getDefaultLocale() {
		// use OSGi service
		ServiceReference[] references = null;
		try {
			references = bundleContext.getAllServiceReferences(null, LocaleProvider.class.getName());
		} catch (InvalidSyntaxException e) {
			// do nothing
		}
		if (references == null || references.length < 1)
			return Locale.getDefault();
		Object service = bundleContext.getService(references[0]);
		LocaleProvider localeProvider = (LocaleProvider) service;
		if (localeProvider != null) {
			Locale currentLocale = localeProvider.getLocale();
			bundleContext.ungetService(references[0]);
			if (currentLocale != null)
				return currentLocale;
		}
		return Locale.getDefault();
	}

	private FrameworkLog getFrameworkLog() {
		if (logTracker == null) {
			logTracker = new ServiceTracker(bundleContext, FrameworkLog.class.getName(), null);
			logTracker.open();
		}
		return (FrameworkLog) logTracker.getService();
	}

	static public void logError(String message, Exception e) {
		if (instance != null) {
			FrameworkLog frameworkLog = instance.getFrameworkLog();
			if (frameworkLog != null) {
				frameworkLog.log(new FrameworkLogEntry("org.eclipse.equinox.bidi", FrameworkLogEntry.ERROR, 1, message, 0, e, null)); //$NON-NLS-1$
				return;
			}
		}
		System.err.println(message);
		if (e != null)
			e.printStackTrace();
	}

}
