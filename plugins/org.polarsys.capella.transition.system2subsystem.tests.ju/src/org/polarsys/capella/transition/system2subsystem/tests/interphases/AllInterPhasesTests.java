/*******************************************************************************
 * Copyright (c) 2016, 2022 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.tests.interphases;

import static org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.testsFor;

import java.util.ArrayList;
import java.util.List;

import org.polarsys.capella.test.framework.api.BasicTestArtefact;
import org.polarsys.capella.test.framework.api.BasicTestSuite;

import junit.framework.Test;

public class AllInterPhasesTests extends BasicTestSuite {

  /**
   * @see com.thalesgroup.mde.tests.common.AbstractExtendedTestSuite#getTests()
   */
  @Override
  protected List<BasicTestArtefact> getTests() {
    final List<BasicTestArtefact> tests = new ArrayList<BasicTestArtefact>();

    tests.addAll(testsFor(ComponentExchangeAllocationTest.class));
    tests.addAll(testsFor(PhysicalPathTest.class));

    tests.addAll(testsFor(BehaviorComponentTest.class));

    return tests;
  }

  public static Test suite() {
    return new AllInterPhasesTests();
  }

}
