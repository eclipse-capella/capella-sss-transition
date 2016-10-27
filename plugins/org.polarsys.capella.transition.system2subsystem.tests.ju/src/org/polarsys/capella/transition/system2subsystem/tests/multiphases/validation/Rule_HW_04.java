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
public class Rule_HW_04 extends ValidationRulePartialTestCase {

  protected EClass getTargetedEClass() {
    return LaPackage.Literals.LOGICAL_FUNCTION;
  }

  protected String getRuleID() {
    return "org.polarsys.capella.transition.system2subsystem.multiphases.HW_04";
  }

  protected List<String> getScopeDefinition() {
    return Arrays
        .asList(new String[] { "9b9b217b-89e5-4e0f-8a60-0b30209e7b1c", "bef1f6aa-7f6f-4b66-a538-f313b2d24451" });
  }

  protected List<OracleDefinition> getOracleDefinitions() {
    return Arrays.asList(new OracleDefinition[] { new OracleDefinition("bef1f6aa-7f6f-4b66-a538-f313b2d24451", 1) });
  }

  @Override
  protected String getRequiredTestModel() {
    return "HWTestModel";
  }

}
