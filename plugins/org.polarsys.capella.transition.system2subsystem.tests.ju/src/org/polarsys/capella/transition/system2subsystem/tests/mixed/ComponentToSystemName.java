/*******************************************************************************
 * Copyright (c) 2020 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests.mixed;

import java.util.Collection;

import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.model.helpers.naming.NamingConstants;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Multiphase;

/**
 * When a unique component is selected, the system shall have this name
 * 
 * When multiple component are selected, the System shall be named 'System'
 */
public class ComponentToSystemName {

  public static final String PC_4 = "54ab5921-cbab-4211-9117-4b95904219a7"; //$NON-NLS-1$
  public static final String PC_1 = "7500ee96-37de-47ea-9689-6752ce6b4c52"; //$NON-NLS-1$
  public static final String PC_2 = "f35a6478-63d6-4c1d-9a5f-78e3e44092eb"; //$NON-NLS-1$
  public static final String PC_3 = "f2111b49-aee1-4b4a-a6c9-7e59f7455124"; //$NON-NLS-1$

  public static class Test1 extends System2SubsystemTest implements Crossphase, Multiphase {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(PC_1);
    }

    @Override
    protected void verify() {
      // PC1 is transformed to System.
      // But as it is also connected with a Physical Path. PhysicalPathRule will retrieve the Default container and will
      // create root System before the transformation of PC1.

      Component source = getObject(PC_1);
      Component targetSystem = retrieveTargetSystem();
      testAttributeIdentity(source, targetSystem, ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME);
    }

  }

  public static class Test2 extends System2SubsystemTest implements Crossphase, Multiphase {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(PC_3);
    }

    @Override
    protected void verify() {
      // PC3 is a standalone component and will be transformed to System. Name shall be PC3

      Component source = getObject(PC_3);
      Component targetSystem = retrieveTargetSystem();
      testAttributeIdentity(source, targetSystem, ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME);
    }

  }

  public static class Test3 extends System2SubsystemTest implements Crossphase, Multiphase {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(PC_3, PC_4);
    }

    @Override
    protected void verify() {
      // When we select two component, system shall be named System.

      Component targetSystem = retrieveTargetSystem();
      assertTrue(targetSystem.getName().equals(NamingConstants.CreateSysAnalysisCmd_system_name));
    }

  }

}
