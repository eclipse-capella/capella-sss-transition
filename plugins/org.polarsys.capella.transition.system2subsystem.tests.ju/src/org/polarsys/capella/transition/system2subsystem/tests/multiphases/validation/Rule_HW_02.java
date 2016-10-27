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
package org.polarsys.capella.transition.system2subsystem.tests.multiphases.validation;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.polarsys.capella.core.data.la.LaPackage;
import org.polarsys.capella.test.framework.api.OracleDefinition;
import org.polarsys.capella.test.validation.rules.ju.testcases.ValidationRulePartialTestCase;

/**
 * test on Rule_HW_01
 * 
 * @generated
 */
public class Rule_HW_02 extends ValidationRulePartialTestCase {

  protected EClass getTargetedEClass() {
    return LaPackage.Literals.LOGICAL_FUNCTION;
  }

  protected String getRuleID() {
    return "org.polarsys.capella.transition.system2subsystem.multiphases.HW_02";
  }

  protected List<String> getScopeDefinition() {
    return Arrays
        .asList(new String[] { "22ed16b3-a8b6-4b2c-8c1e-37bc373f0156", "122b04a1-c098-40fc-aa06-ef30435181e7" });
  }

  protected List<OracleDefinition> getOracleDefinitions() {
    return Arrays.asList(new OracleDefinition[] { new OracleDefinition("122b04a1-c098-40fc-aa06-ef30435181e7", 1) });
  }

  @Override
  protected String getRequiredTestModel() {
    return "HWTestModel";
  }

}
