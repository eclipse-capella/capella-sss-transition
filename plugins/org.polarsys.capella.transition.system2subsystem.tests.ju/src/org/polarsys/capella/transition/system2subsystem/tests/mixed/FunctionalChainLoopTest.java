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
package org.polarsys.capella.transition.system2subsystem.tests.mixed;

import java.util.Collection;

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

/**
 * Functional Chain Cycle: Test that a cycle in a chain doesn't loop transition
 */

public class FunctionalChainLoopTest extends System2SubsystemTest implements Interphase, Crossphase {

  public static String FUNCTIONALCHAINLOOP__PA__ROOT_PF__FUNCTIONALCHAIN_1 = "89480e4f-0ef5-4974-b5ff-820c28c787ce"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINLOOP__PA__ROOT_PF__PHYSICALFUNCTION_1 = "3f6e0fa5-8795-470e-9d75-d57407e6fb9b"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINLOOP__PA__ROOT_PF__PHYSICALFUNCTION_2 = "f32a6e8d-fcd6-419e-9d5c-aac2aa34ceb3"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINLOOP__PA__PC_1 = "06da8bcf-fe54-4452-9567-467c0667949e"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(FUNCTIONALCHAINLOOP__PA__PC_1);
  }

  @Override
  protected void verify() {
    shouldNotBeTransitioned(FUNCTIONALCHAINLOOP__PA__ROOT_PF__FUNCTIONALCHAIN_1);
    mustBeTransitioned(FUNCTIONALCHAINLOOP__PA__ROOT_PF__PHYSICALFUNCTION_1);
    mustBeTransitioned(FUNCTIONALCHAINLOOP__PA__ROOT_PF__PHYSICALFUNCTION_2);
    mustBeTransitioned(FUNCTIONALCHAINLOOP__PA__PC_1);
  }
}
