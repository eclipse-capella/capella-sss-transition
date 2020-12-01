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
package org.polarsys.capella.transition.system2subsystem.tests.multiphases;

import java.util.ArrayList;
import java.util.List;

import org.polarsys.capella.test.framework.api.BasicTestArtefact;
import org.polarsys.capella.test.framework.api.BasicTestSuite;
import org.polarsys.capella.transition.system2subsystem.tests.multiphases.validation.HWRulesTestSuite;

import junit.framework.Test;

public class AllMultiPhasesTests extends BasicTestSuite {

  /**
   * @see com.thalesgroup.mde.tests.common.AbstractExtendedTestSuite#getTests()
   */
  @Override
  protected List<BasicTestArtefact> getTests() {
    final List<BasicTestArtefact> tests = new ArrayList<BasicTestArtefact>();
    tests.add(new SimpleComponents());
    tests.add(new SubPhyscialComponent());
    tests.add(new Interfaces());
    tests.add(new HWRulesTestSuite());
    tests.add(new OrderedPhysicalComponents());
    tests.add(new MultiphasesTransitionTest());

    tests.add(new SimpleComponents(true));
    tests.add(new Interfaces(true));
    tests.add(new OrderedPhysicalComponents(true));
    tests.add(new MultiphasesTransitionTest(true));
    return tests;
  }

  public static Test suite() {
    return new AllMultiPhasesTests();
  }

}
