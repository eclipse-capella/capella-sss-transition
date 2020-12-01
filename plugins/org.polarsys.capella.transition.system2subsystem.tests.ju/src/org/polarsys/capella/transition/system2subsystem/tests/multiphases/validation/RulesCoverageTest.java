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
package org.polarsys.capella.transition.system2subsystem.tests.multiphases.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.polarsys.capella.test.framework.api.BasicTestCase;

/**
 * A Test, which checks the coverage of TSSS validation rules tested
 */
public class RulesCoverageTest extends BasicTestCase {

  private List<String> _testedRulesIdList;

  public RulesCoverageTest() {
    _testedRulesIdList = new ArrayList<String>();
    _testedRulesIdList.add("org.polarsys.capella.transition.system2subsystem.multiphases.HW_01");
    _testedRulesIdList.add("org.polarsys.capella.transition.system2subsystem.multiphases.HW_02");
    _testedRulesIdList.add("org.polarsys.capella.transition.system2subsystem.multiphases.HW_03");
    _testedRulesIdList.add("org.polarsys.capella.transition.system2subsystem.multiphases.HW_04");
  }

  /**
   * get the list of all TSSS validation rules id
   * 
   * @return
   */
  private List<String> getAllCapellaConstraintId() {
    ConstraintRegistry registry = ConstraintRegistry.getInstance();
    List<String> allRulesId = new ArrayList<String>();
    Collection<IConstraintDescriptor> allDescriptors = registry.getAllDescriptors();
    for (IConstraintDescriptor desc : allDescriptors) {
      if (desc.getId().contains("org.polarsys.capella.transition.system2subsystem")) { //$NON-NLS-1$
        allRulesId.add(desc.getId());
      }
    }
    return allRulesId;
  }

  @Override
  public void test() throws Exception {
    List<String> allRulesId = getAllCapellaConstraintId();

    // the list of not tested rules
    List<String> missingRules = new ArrayList<String>();
    for (String current : allRulesId) {
      // if an id of allRulesId is not contains in _testedRulesIdList, it means it is not tested
      if (!_testedRulesIdList.contains(current)) {
        missingRules.add(current);
      }
    }

    Assert.assertTrue(missingRules.size() + " validation rules are not tested: " + missingRules, //$NON-NLS-1$
        missingRules.isEmpty());
  }

  public void testRulesCoverageTest() throws Exception {
    test();
  }
}
