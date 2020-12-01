/*******************************************************************************
 * Copyright (c) 2016, 2019 THALES GLOBAL SERVICES.
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

import java.util.Collection;

import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;

public class PhysicalToSystemComponent {

  public static final String PC1 = "581d4e58-c3fb-40ac-8c27-b0b31bf63431";
  public static final String PC11 = "461435b7-5c4c-4080-92d6-89d8401252ab";
  public static final String PC2 = "a5c9fb66-c6ec-4213-b93c-c52d6f9da50f"; //$NON-NLS-1$

  /**
   * PC to System: Test if a PC is correctly transitionned to a System
   */
  public static class Test1 extends CrossPhasesTest {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(PC11);
    }

    @Override
    protected void verify() {

      retrieveReferenceElement(PC11);
      testInstanceOf(retrieveTargetElement(PC11), ComponentType.SYSTEM);

      retrieveReferenceElement(PC2);
      testInstanceOf(retrieveTargetElement(PC2), ComponentType.ACTOR);
    }
  }

  /**
   * System name: Test if transitioned System has same name than Reference component
   */
  public static class Test2 extends CrossPhasesTest {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(PC11);
    }

    @Override
    protected void verify() {
      retrieveReferenceElement(PC11);
      retrieveReferenceElement(PC2);
      testAttributeIdentity(retrieveReferenceElement(PC11), retrieveTargetElement(PC11),
          ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME);
    }
  }
}