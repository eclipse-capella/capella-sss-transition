/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests;

import java.util.ArrayList;
import java.util.List;

import org.polarsys.capella.test.framework.api.BasicTestArtefact;
import org.polarsys.capella.test.framework.api.BasicTestSuite;
import org.polarsys.capella.transition.system2subsystem.tests.crossphases.AllCrossPhasesTests;
import org.polarsys.capella.transition.system2subsystem.tests.interphases.AllInterPhasesTests;
import org.polarsys.capella.transition.system2subsystem.tests.mixed.AllMixedTests;
import org.polarsys.capella.transition.system2subsystem.tests.multiphases.AllMultiPhasesTests;
import org.polarsys.capella.transition.system2subsystem.tests.util.SubSystemSessionHandlerTest;

import junit.framework.Test;

public class AllSystem2SubsystemTests extends BasicTestSuite {

  @Override
  protected List<BasicTestArtefact> getTests() {
    final List<BasicTestArtefact> tests = new ArrayList<BasicTestArtefact>();
    tests.add(new SubSystemSessionHandlerTest());
    tests.add(new AllMixedTests());
    tests.add(new AllCrossPhasesTests());
    tests.add(new AllInterPhasesTests());
    tests.add(new AllMultiPhasesTests());

    return tests;
  }

  public static Test suite() {
    return new AllSystem2SubsystemTests();
  }

}
