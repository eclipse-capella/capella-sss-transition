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

import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.information.InformationPackage;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

/**
 * Allocated Exchange Items test
 */
public class ExternalFunctionExchangeItemTransitionnedTest extends System2SubsystemTest
    implements Interphase, Crossphase {

  public static String BEHAVIOR_PC = "4c895a3e-3f5b-4d6c-8f2d-f9d7d1767362"; //$NON-NLS-1$

  // Should be transitioned

  public static String FE = "0b85472a-8a4d-44a0-99f2-6cb79e6945ee"; //$NON-NLS-1$
  public static String EI = "b8ef9338-4c6f-4951-8820-cb841b3741bc"; //$NON-NLS-1$
  public static String EIE = "9263a02a-be7c-4dfa-ae43-c77f7a910b01"; //$NON-NLS-1$
  public static String CLASS = "97a21031-aa2c-49cb-845c-bd3153143ea6"; //$NON-NLS-1$
  public static String DATAPKG_1 = "575af067-4597-49d7-8ffe-e97033041a84"; //$NON-NLS-1$
  public static String DATAPKG_2 = "0d775144-be57-4801-993e-4e030fb5ea31"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(BEHAVIOR_PC);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(FE);
    mustBeTransitionedAndLinkedTo(FE, EI, FaPackage.Literals.FUNCTIONAL_EXCHANGE__EXCHANGED_ITEMS);
    mustBeTransitionedAndLinkedTo(EI, EIE, InformationPackage.Literals.EXCHANGE_ITEM__OWNED_ELEMENTS);
    mustBeTransitioned(CLASS);
    mustBeTransitioned(DATAPKG_1);
    mustBeTransitioned(DATAPKG_2);
  }

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("ExternalFunctionExchangeItemTransition", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }
}
