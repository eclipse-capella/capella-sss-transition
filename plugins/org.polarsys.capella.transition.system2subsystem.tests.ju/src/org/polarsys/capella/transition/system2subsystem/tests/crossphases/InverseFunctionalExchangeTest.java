/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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

import java.util.Arrays;
import java.util.Collection;

import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;

/*
 * Test that functional exchanges allocated to component exchanges going the opposite way are transitioned properly.
 */
public class InverseFunctionalExchangeTest extends System2SubsystemTest implements Crossphase {

  private static final String LC_1 = "aa75d1f7-8bd9-49ff-8e61-a3462115e04e";
  private static final String CE_1 = "e161e693-8fae-4479-980e-9674377933a0";
  private static final String FE_1 = "87cf0c0c-64c6-4303-bfaa-209bde3923ea";
  private static final String FE_2 = "1568d464-7d07-43f7-9aca-30118936815d";
  private static final String CE_2 = "a919d173-63f2-4c09-bf6b-1421a422501e";
  private static final String FE_3 = "9de26a5c-a22b-4358-a1b7-d03c26c6a053";
  private static final String FE_4 = "2c7d566a-87af-4072-ad2e-40eb617fbc20";
  private static final String CE_3 = "3f9ffc6e-4ac5-4357-89f1-6486b1ac5c54";
  private static final String FE_5 = "c82b3544-8948-421b-83aa-0846de91baa9";
  private static final String FE_6 = "e3f63eba-27db-4957-bdca-2f34b9f60bab";

  @Override
  protected void verify() throws Exception {
    mustBeTransitioned(LC_1);

    FunctionalExchange fe1 = (FunctionalExchange) mustBeTransitioned(FE_1);
    FunctionalExchange fe2 = (FunctionalExchange) mustBeTransitioned(FE_2);
    FunctionalExchange fe3 = (FunctionalExchange) mustBeTransitioned(FE_3);
    FunctionalExchange fe4 = (FunctionalExchange) mustBeTransitioned(FE_4);
    FunctionalExchange fe5 = (FunctionalExchange) mustBeTransitioned(FE_5);
    FunctionalExchange fe6 = (FunctionalExchange) mustBeTransitioned(FE_6);
    
    ComponentExchange ce1 = (ComponentExchange) mustBeTransitioned(CE_1);
    ComponentExchange ce2 = (ComponentExchange) mustBeTransitioned(CE_2);
    ComponentExchange ce3 = (ComponentExchange) mustBeTransitioned(CE_3);
    
    Arrays.asList(fe1, fe2).stream().forEach(fe -> assertTrue(ce1.getAllocatedFunctionalExchanges().contains(fe)));
    Arrays.asList(fe3, fe4).stream().forEach(fe -> assertTrue(ce2.getAllocatedFunctionalExchanges().contains(fe)));
    Arrays.asList(fe5, fe6).stream().forEach(fe -> assertTrue(ce3.getAllocatedFunctionalExchanges().contains(fe)));
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(LC_1);
  }

}
