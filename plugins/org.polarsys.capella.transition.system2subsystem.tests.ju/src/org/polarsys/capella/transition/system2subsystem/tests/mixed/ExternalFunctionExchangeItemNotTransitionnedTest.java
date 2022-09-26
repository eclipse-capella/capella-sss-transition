/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

/**
 * Allocated Exchange Items test
 */
public class ExternalFunctionExchangeItemNotTransitionnedTest extends System2SubsystemTest
    implements Interphase, Crossphase {

  public static String BEHAVIOR_PC = "4c895a3e-3f5b-4d6c-8f2d-f9d7d1767362"; //$NON-NLS-1$

  // Should not be transitioned

  public static String FE = "f7d34d23-8f17-44fb-9e46-7ae195e36332"; //$NON-NLS-1$
  public static String EI = "886b29f0-f702-48bb-bd64-6e8470de7fd6"; //$NON-NLS-1$
  public static String EIE = "046931d1-db10-4010-aced-56142ee87cb1"; //$NON-NLS-1$
  public static String CLASS = "Id  85d0d044-4bf1-41eb-91a8-3f07c0c6c926"; //$NON-NLS-1$
  public static String DATAPKG_1 = "0ff48036-3091-4bf6-823e-f2c01fa3b35f"; //$NON-NLS-1$
  public static String DATAPKG_2 = "6c00e5b5-e766-49f2-b026-0f3aa74913d1"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(BEHAVIOR_PC);
  }

  @Override
  protected void verify() {
    shouldNotBeTransitioned(FE);
    shouldNotBeTransitioned(FE);
    shouldNotBeTransitioned(EI);
    shouldNotBeTransitioned(CLASS);
    shouldNotBeTransitioned(DATAPKG_1);
    shouldNotBeTransitioned(DATAPKG_2);
  }

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("ExternalFunctionExchangeItemTransition", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }
}
