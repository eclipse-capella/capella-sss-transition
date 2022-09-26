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

import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

/**
 * Allocated Exchange Items test
 */
public class AllocatedExchangeItemTest extends System2SubsystemTest implements Interphase, Crossphase {

  public static String BEHAVIOR_PC = "5892b130-2d27-4b33-aa6b-071ebf8b7b2d"; //$NON-NLS-1$

  // Should be transitioned

  public static String EI_FOP1 = "fd4e8de4-d839-438c-9da4-3653743bb129"; //$NON-NLS-1$
  public static String EI_FIP1 = "d335f117-c19a-4466-91f2-e7dc83dd4218"; //$NON-NLS-1$
  public static String EI_FE1 = "f3685b18-6179-4aea-b249-91b4dcd537a9"; //$NON-NLS-1$
  public static String EI_CE1 = "69d78fb0-3614-485b-9ce6-b9360866175c"; //$NON-NLS-1$
  public static String EI_ITF1 = "cb34546f-7606-47dc-863b-bb2065f3036a"; //$NON-NLS-1$
  public static String EI_ITF2 = "52b962c9-e551-4e50-b0ef-42eddd648b42"; //$NON-NLS-1$

  public static String FOP1 = "9eb87c76-fa5b-42c2-b5fa-b09077e13a76"; //$NON-NLS-1$
  public static String FIP1 = "0c17a944-894c-464b-8c57-6c3736b65711"; //$NON-NLS-1$
  public static String FE1 = "bb4547d2-bf76-47b9-a299-e216fa557330"; //$NON-NLS-1$
  public static String CE1 = "293edf7c-81df-40d2-affc-98dde43154b5"; //$NON-NLS-1$
  public static String ITF1 = "974ded47-c9a6-4943-90e4-bd7bfedcafb1"; //$NON-NLS-1$
  public static String ITF2 = "16cc6322-3a12-4a2e-9b6a-b04943bd21c9"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(BEHAVIOR_PC);
  }

  @Override
  protected void verify() {
    mustBeTransitionedAndLinkedTo(FOP1, EI_FOP1, FaPackage.Literals.FUNCTION_OUTPUT_PORT__OUTGOING_EXCHANGE_ITEMS);
    mustBeTransitionedAndLinkedTo(FIP1, EI_FIP1, FaPackage.Literals.FUNCTION_INPUT_PORT__INCOMING_EXCHANGE_ITEMS);
    mustBeTransitionedAndLinkedTo(FE1, EI_FE1, FaPackage.Literals.FUNCTIONAL_EXCHANGE__EXCHANGED_ITEMS);
    mustBeTransitioned(EI_CE1); // Could not find COMPONENT_EXCHANGE__CONVOYED_INFORMATIONS
    mustBeTransitionedAndLinkedTo(ITF1, EI_ITF1, CsPackage.Literals.INTERFACE__EXCHANGE_ITEMS);
    mustBeTransitionedAndLinkedTo(ITF2, EI_ITF2, CsPackage.Literals.INTERFACE__EXCHANGE_ITEMS);
  }

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("AllocatedExchangeItemTest", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }
}
