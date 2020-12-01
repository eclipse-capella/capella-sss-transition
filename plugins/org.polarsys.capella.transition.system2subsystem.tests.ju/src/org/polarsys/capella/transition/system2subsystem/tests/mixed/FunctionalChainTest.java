/*******************************************************************************
 * Copyright (c) 2016, 2020 THALES GLOBAL SERVICES.
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
 * Functional Chain: test functional chain transition
 */
public class FunctionalChainTest extends System2SubsystemTest implements Interphase, Crossphase {

  public static String FUNCTIONALCHAIN__LC2 = "2b60d2e3-edc8-44c5-b82c-72528d90a25b"; //$NON-NLS-1$

  // Should not be transtioned

  public static String FUNCTIONALCHAIN__LA1 = "69781e5e-5720-41db-a038-33c2ca86f656"; //$NON-NLS-1$

  public static String FUNCTIONALCHAIN__LC1_F1 = "6f0cb67a-1c57-477a-a2a9-6ae471bb43f6"; //$NON-NLS-1$
  public static String FUNCTIONALCHAIN__LC1_F3 = "627d14d0-6b0a-4688-93bb-28b63e17f749"; //$NON-NLS-1$

  public static final String CONTROL_NODE_1 = "43b66322-6d35-4044-9781-530ef52b26f4"; //$NON-NLS-1$
  public static final String CONTROL_NODE_2 = "75ff61a4-3ed6-46ad-be66-4893fff8442d"; //$NON-NLS-1$
  public static final String CONTROL_NODE_3 = "d640b851-c4bd-402a-83ea-9d25358a4c6a"; //$NON-NLS-1$
  public static final String CONTROL_NODE_4 = "b2788e52-f5ff-4093-ad49-a0c5a90a48e7"; //$NON-NLS-1$
  
  // Should be transitioned

  public static String FUNCTIONALCHAIN__LA2 = "58425650-571e-48c7-a3d9-8724f82830a4"; //$NON-NLS-1$

  public static String FUNCTIONALCHAIN__LC1_F2 = "03de6f0c-316d-4853-9e1d-2205245a3278"; //$NON-NLS-1$
  public static String FUNCTIONALCHAIN__LC1_F4 = "27fa8491-d033-452b-9792-5111d2936a2e"; //$NON-NLS-1$

  public static String FUNCTIONALCHAIN__FUNCTIONALEXCHANGE_25 = "e3c77ae8-b0bd-4da5-8fdd-a7db1718d065"; //$NON-NLS-1$
  public static String FUNCTIONALCHAIN__FUNCTIONALEXCHANGE_23 = "15e951a9-9dc1-4a4b-b7e3-d26a79519a6a"; //$NON-NLS-1$

  public static final String CONTROL_NODE_5 = "f00fb1ae-71b0-4319-8b0e-f28eeab61e98"; //$NON-NLS-1$
  public static final String CONTROL_NODE_6 = "c4370c40-0e52-468e-a86b-37f9fb4e2400"; //$NON-NLS-1$
  public static final String CONTROL_NODE_7 = "f5985bd0-6766-455d-a17d-74e4f67e153c"; //$NON-NLS-1$
  public static final String CONTROL_NODE_8 = "309929f7-ef07-4057-b4c9-bc9a8c0cbecb"; //$NON-NLS-1$
  
  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(FUNCTIONALCHAIN__LC2);
  }

  @Override
  protected void verify() {
    shouldNotBeTransitioned(FUNCTIONALCHAIN__LA1);
    shouldNotBeTransitioned(FUNCTIONALCHAIN__LC1_F1);
    shouldNotBeTransitioned(FUNCTIONALCHAIN__LC1_F3);
    
    shouldNotBeTransitioned(CONTROL_NODE_5);
    shouldNotBeTransitioned(CONTROL_NODE_6);
    shouldNotBeTransitioned(CONTROL_NODE_7);
    shouldNotBeTransitioned(CONTROL_NODE_8);
    
    mustBeTransitioned(FUNCTIONALCHAIN__LA2);
    mustBeTransitioned(FUNCTIONALCHAIN__LC1_F4);
    mustBeTransitioned(FUNCTIONALCHAIN__LC1_F2);

    mustBeTransitioned(FUNCTIONALCHAIN__FUNCTIONALEXCHANGE_25);
    mustBeTransitioned(FUNCTIONALCHAIN__FUNCTIONALEXCHANGE_23);
    
    mustBeTransitioned(CONTROL_NODE_1);
    mustBeTransitioned(CONTROL_NODE_2);
    mustBeTransitioned(CONTROL_NODE_3);
    mustBeTransitioned(CONTROL_NODE_4);
  }

}
