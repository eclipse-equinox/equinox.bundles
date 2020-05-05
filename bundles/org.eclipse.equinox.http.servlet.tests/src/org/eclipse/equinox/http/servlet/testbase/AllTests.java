/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.http.servlet.testbase;

import org.eclipse.equinox.http.servlet.tests.Bug500783_Test;
import org.eclipse.equinox.http.servlet.tests.Bug562440_Test;
import org.eclipse.equinox.http.servlet.tests.Bug562843_Test;
import org.eclipse.equinox.http.servlet.tests.DispatchingTest;
import org.eclipse.equinox.http.servlet.tests.ServletTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	DispatchingTest.class,
	ServletTest.class,
	Bug500783_Test.class,
	Bug562440_Test.class,
	Bug562843_Test.class
})
public class AllTests {
	// see @SuiteClasses
}
