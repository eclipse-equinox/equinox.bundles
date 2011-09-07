/*******************************************************************************
 * Copyright (c) 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.core.internal.preferences;

import java.util.*;
import org.eclipse.core.runtime.preferences.AbstractPreferenceStorage;
import org.osgi.service.prefs.BackingStoreException;

public class ScopeDescriptor {
	private String name;
	private AbstractPreferenceStorage storage;
	private Set loadedNodes = Collections.synchronizedSet(new HashSet());

	String getName() {
		return name;
	}

	String[] childrenNames(String path) {
		return storage.childrenNames(path);
	}

	Properties load(String path) throws BackingStoreException {
		return storage == null ? null : storage.load(path);
	}

	void save(String nodePath, Properties properties) throws BackingStoreException {
		if (storage == null)
			return;
		storage.save(nodePath, properties);
	}

	boolean isAlreadyLoaded(String node) {
		return loadedNodes.contains(node);
	}

	void loaded(String node) {
		loadedNodes.add(node);
	}

}
