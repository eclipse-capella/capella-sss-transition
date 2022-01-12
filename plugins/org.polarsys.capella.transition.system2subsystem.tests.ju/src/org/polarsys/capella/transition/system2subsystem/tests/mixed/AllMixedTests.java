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
package org.polarsys.capella.transition.system2subsystem.tests.mixed;

import static org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.testsFor;

import java.util.ArrayList;
import java.util.List;

import org.polarsys.capella.test.framework.api.BasicTestArtefact;
import org.polarsys.capella.test.framework.api.BasicTestSuite;

import junit.framework.Test;

public class AllMixedTests extends BasicTestSuite {

  /**
   * @see com.thalesgroup.mde.tests.common.AbstractExtendedTestSuite#getTests()
   */
  @Override
  protected List<BasicTestArtefact> getTests() {
    final List<BasicTestArtefact> tests = new ArrayList<BasicTestArtefact>();

    tests.addAll(testsFor(FakeExchange.class));
    tests.addAll(testsFor(CycleMerge.class));
    tests.addAll(testsFor(CyclePremices.class));
    
    tests.add(new IncrementalModeTest());

    tests.addAll(testsFor(FakeExchangeChain.Always.class));
    tests.addAll(testsFor(TestComposite568503.class));
    tests.addAll(testsFor(CapabilityTest.class));

    tests.add(new CategoriesTest.Crossphases());
    tests.add(new CategoriesTest.Interphases());
    tests.add(new CategoriesTest.Multiphase());

    tests.addAll(testsFor(ConstraintTest.class));
    tests.addAll(testsFor(DataPkgTest.class));

    tests.addAll(testsFor(DataValuesTest.class));
    tests.add(new DataValuesTest.Crossphase());
    tests.add(new DataValuesTest.Interphase());

    tests.addAll(testsFor(ExchangeCategoryTest.class));
    tests.addAll(testsFor(FakeChainInvolvementLink.Always.class));
    tests.addAll(testsFor(FunctionalChainLoopTest.class));
    tests.addAll(testsFor(FunctionalChainScopeTest.class));
    tests.addAll(testsFor(FunctionalChainTest.class));
    tests.addAll(testsFor(HierarchicalFunctionAndPkgs.Always.class));
    tests.addAll(testsFor(HierarchicalFunctionParameter.Always.class));
    tests.addAll(testsFor(HierarchicalFunctionParameter.Restricted.class));
    tests.addAll(testsFor(HierarchicalFunctionParameter.Leaf.class));
    tests.addAll(testsFor(InterfacePkgTest.class));

    tests.addAll(testsFor(NoDuplicationTest.Test1.class));
    tests.addAll(testsFor(NoDuplicationTest.Test2.class));
    tests.addAll(testsFor(NoDuplicationTest.Test3.class));
    tests.addAll(testsFor(NoDuplicationTest.Test4.class));

    tests.addAll(testsFor(NullAttributeTest.class));

    tests.addAll(testsFor(ExchangeItemTest.class));
    tests.addAll(testsFor(PropertyValuesTest.Test1.class));
    tests.addAll(testsFor(PropertyValuesTest.Test2.class));
    tests.addAll(testsFor(PropertyValuesTest.Test3.class));

    tests.addAll(testsFor(ScenarioTest.Enabled.class));
    tests.addAll(testsFor(ScenarioTest.Disabled.class));
    tests.addAll(testsFor(ScenarioTest.Scope1.class));
    tests.addAll(testsFor(ScenarioTest.Scope2.class));
    tests.addAll(testsFor(ScenarioTest.Scope3.class));
    tests.addAll(testsFor(ScenarioTest.Scope4.class));
    tests.addAll(testsFor(ScenarioTest.Scope5.class));
    tests.addAll(testsFor(ScenarioTest.Scope6.class));
    tests.addAll(testsFor(ScenarioTest.Cycle1.class));
    tests.addAll(testsFor(ScenarioTest.Cycle2.class));
    tests.addAll(testsFor(ScenarioTest.Cycle3.class));

    tests.addAll(testsFor(StateMachineTest.Test1.class));
    tests.addAll(testsFor(StateMachineTest.Test2.class));
    tests.addAll(testsFor(StateMachineTest.Test3.class));
    tests.addAll(testsFor(StateMachineTest.Test4.class));

    tests.addAll(testsFor(StatesReferencesTest.class));
    tests.addAll(testsFor(Bug565895Test.class));

    tests.addAll(testsFor(ComponentToSystemName.Test1.class));
    tests.addAll(testsFor(ComponentToSystemName.Test2.class));
    tests.addAll(testsFor(ComponentToSystemName.Test3.class));

    return tests;
  }

  public static Test suite() {
    return new AllMixedTests();
  }

}
