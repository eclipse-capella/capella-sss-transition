/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

import junit.framework.Test;

public class AllSystem2SubsystemTests extends BasicTestSuite {

  @Override
  protected List<BasicTestArtefact> getTests() {
    final List<BasicTestArtefact> tests = new ArrayList<BasicTestArtefact>();
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
