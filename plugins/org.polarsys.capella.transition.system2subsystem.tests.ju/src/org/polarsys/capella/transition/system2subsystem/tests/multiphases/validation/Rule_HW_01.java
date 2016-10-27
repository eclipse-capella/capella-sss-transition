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
public class Rule_HW_01 extends ValidationRulePartialTestCase {

  protected EClass getTargetedEClass() {
    return LaPackage.Literals.LOGICAL_FUNCTION;
  }

  protected String getRuleID() {
    return "org.polarsys.capella.transition.system2subsystem.multiphases.HW_01";
  }

  protected List<String> getScopeDefinition() {
    return Arrays
        .asList(new String[] { "b924a59b-2791-4c96-8d10-7fde3b692442", "f206807a-964c-48dc-a4ab-31cc904a7feb" });
  }

  protected List<OracleDefinition> getOracleDefinitions() {
    return Arrays.asList(new OracleDefinition[] { new OracleDefinition("f206807a-964c-48dc-a4ab-31cc904a7feb", 1) });
  }

  @Override
  protected String getRequiredTestModel() {
    return "HWTestModel";
  }

}
