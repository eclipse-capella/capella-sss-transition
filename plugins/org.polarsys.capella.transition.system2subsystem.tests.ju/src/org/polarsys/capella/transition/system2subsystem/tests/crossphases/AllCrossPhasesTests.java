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
package org.polarsys.capella.transition.system2subsystem.tests.crossphases;

import java.util.ArrayList;
import java.util.List;

import org.polarsys.capella.test.framework.api.BasicTestArtefact;
import org.polarsys.capella.test.framework.api.BasicTestSuite;
import org.polarsys.capella.transition.system2subsystem.tests.mixed.ComponentToSystemName;

import junit.framework.Test;

public class AllCrossPhasesTests extends BasicTestSuite {

  /**
   * @see com.thalesgroup.mde.tests.common.AbstractExtendedTestSuite#getTests()
   */
  @Override
  protected List<BasicTestArtefact> getTests() {
    final List<BasicTestArtefact> tests = new ArrayList<BasicTestArtefact>();

    tests.add(new PhysicalToSystemComponent.Test1());
    tests.add(new PhysicalToSystemComponent.Test2());
    tests.add(new ComponentExchangesAndPorts.Test1());
    tests.add(new ComponentExchangesAndPorts.Test2());
    tests.add(new FunctionAllocations());
    tests.add(new CItoSystemName());

    tests.add(new ComponentMerge.Test1());
    tests.add(new ComponentMerge.Test2());
    tests.add(new ComponentMerge.Test3());
    tests.add(new ComponentMerge.Test4());
    tests.add(new ComponentMerge.Test5());
    tests.add(new ComponentMerge.Test6());
    tests.add(new ScenarioComponentMerge.Test1());
    tests.add(new ScenarioComponentMerge.Test2());
    tests.add(new InternalExchangesTest.Exchanges());
    tests.add(new InternalExchangesTest.Ports());

    return tests;
  }

  public static Test suite() {
    return new AllCrossPhasesTests();
  }

}
