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
package org.polarsys.capella.transition.system2subsystem.tests.crossphases;

import java.util.Collection;

public class InternalExchangesTest {

  public static final String INTERNALEXCHANGES__PA__PHYSICAL_CONTEXT__C_1 = "0cfe1e13-257b-4cde-accf-3430f186e02d"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PHYSICAL_CONTEXT__PL_1 = "dd0b3b64-0d58-4959-a859-3062eca665bd"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PHYSICAL_SYSTEM = "06ba1ca2-dc10-4a84-afda-2bd12a36e136"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__C_1 = "9f9d3dbe-516e-4b72-ad1f-84f0394de766"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__C_2 = "c87ec9ec-892a-4165-b49a-480ca2d49b94"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__C_3 = "96d5f9c7-75a5-405e-bed6-48c7a049502f"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__C_4 = "e72523d9-da50-46e8-ae57-ec30485605dd"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PART_PC_1 = "42dbbe6b-5c6f-4539-956a-be72b669639f"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PART_PC_2 = "df1def9a-cdae-4d20-9735-3b60212f07b5"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PART_PC_3 = "452b3860-5c43-4594-9cd0-0967250d8548"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PART_PC_4 = "9a6c8de8-df65-40e6-9354-63ef4a3f3d35"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PART_PC_5 = "3d31c24f-f13e-4d99-bfff-d604abfcb4a3"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PART_PC_6 = "de5ff0da-289d-472a-9740-4044b783bf2b"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PART_PC_7 = "e26bf8db-1c14-4a46-a200-ce1ded86559b"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PART_PC_8 = "7ac6caea-2703-4a29-80d0-0b2ac3c5a185"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PL_1 = "d97e2612-26d1-49db-8f57-25cb45220902"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PL_2 = "bcd8f411-5070-42a6-8e1d-1cd59b6bacbf"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_1 = "67440afe-ada0-4c77-afdc-c53e1647444d"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_1__PP_1 = "8e1a3587-1525-4b4e-b4f8-6fcf66828805"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_1__PP_2 = "aee2155c-c02b-4212-bf01-ba0abaf72979"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_1__PP_3 = "c45b61a1-7986-4ad7-8c30-f81b0eeb06c9"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_1__PP_4 = "471c7d44-22a4-414b-b317-29461bd94d56"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_1__PP_5 = "4d2c3438-8883-4518-9281-5e8999ded99c"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_2 = "c471a652-b830-4fa1-a21d-9182e5235807"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_2__PP_1 = "0ab5fcf6-6944-4e97-ad03-248220604e2d"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_2__PP_2 = "17cb9456-15a4-41d2-a5e9-b55aad1ddbeb"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_3 = "81e15490-f139-4762-b8db-bc1c9306db16"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_3__CP_1 = "f222c26c-6f7c-4e2b-b034-d291d94ed781"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_3__CP_2 = "528b0e57-c7b3-42ec-b0c1-9f7bbf022172"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_3__CP_3 = "b5740ce3-150f-4987-b185-0b5eff2df143"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_3__CP_4 = "f7fc4108-2dfc-4bf4-8e09-8a1de1c25b84"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_3__CP_5 = "920fc2d8-5f2c-44d1-80ef-c79cd53a89ac"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_3__CP_6 = "835863d7-6e51-442e-b6e3-775df21577c3"; //$NON-NLS-1$

  public static final String INTERNALEXCHANGES__PA__PC_4 = "5e977d95-108d-49c2-9f2b-46388dbc93f4"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_4__CP_1 = "a69fc814-85a2-4825-8cea-dd42624b9fb5"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_4__CP_2 = "343a2631-6a31-47e6-a416-d7ee828526a6"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_4__CP_3 = "f576fc5d-69f6-477a-9e8a-f03208416d5a"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_5 = "54c98b1e-237c-4a34-a08d-cfa9d87aaebc"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_5__PP_1 = "0f397554-0e65-4c3b-a0a8-28ede6ecea23"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_5__PP_2 = "533b58f4-0f64-47bf-9605-01c8bd3b1440"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_5__PP_3 = "38013904-11bf-4e25-be77-c4b29aa740ff"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_6 = "10757ab9-072c-4f7d-9c86-07407e981689"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_6__CP_1 = "b4ec368b-92a0-4bcf-88d8-4895c1052274"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_6__CP_2 = "fc0e8222-f26e-4686-938c-34c44221ee7a"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_6__CP_3 = "a9317898-579f-417f-8ef0-873c8ced9acd"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_7 = "cb597888-cd6b-47e4-8e08-bdfedb93df75"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_7__PP_1 = "37f2dbb5-4c64-45df-9d50-e88a28cc0ca9"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_7__PP_2 = "bfb5cc82-dffb-4b0e-b4ed-482693083f5b"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_8 = "8ef12385-efaf-4076-a132-ab255efdc42e"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_8__CP_1 = "ae85d88e-b370-4cac-91d3-a8f529fde87a"; //$NON-NLS-1$
  public static final String INTERNALEXCHANGES__PA__PC_8__CP_2 = "50ace1ec-9c53-4a55-9356-eaaf6c45e797"; //$NON-NLS-1$

  /**
   * Internal Exchange transition: Internal exchanges should not be propagated
   */
  public static class Exchanges extends CrossPhasesTest {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(INTERNALEXCHANGES__PA__PC_1, INTERNALEXCHANGES__PA__PC_2);
    }

    @Override
    protected void verify() {
      shouldNotBeTransitioned(INTERNALEXCHANGES__PA__C_1);
      mustBeTransitioned(INTERNALEXCHANGES__PA__C_2);
      mustBeTransitioned(INTERNALEXCHANGES__PA__C_3);
      shouldNotBeTransitioned(INTERNALEXCHANGES__PA__C_4);

      shouldNotBeTransitioned(INTERNALEXCHANGES__PA__PL_1);
      mustBeTransitioned(INTERNALEXCHANGES__PA__PL_2);
    }
  }

  /**
   * Internal ports: Internal ports should not be propagated
   */
  public static class Ports extends CrossPhasesTest {

    protected Collection<?> getProjectionElements() {
      return getObjects(INTERNALEXCHANGES__PA__PC_1, INTERNALEXCHANGES__PA__PC_2);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(INTERNALEXCHANGES__PA__PC_1__PP_2);
      mustBeTransitioned(INTERNALEXCHANGES__PA__PC_1__PP_3);
      mustBeTransitioned(INTERNALEXCHANGES__PA__PC_1__PP_4);
      shouldNotBeTransitioned(INTERNALEXCHANGES__PA__PC_1__PP_1);
      mustBeTransitioned(INTERNALEXCHANGES__PA__PC_1__PP_5);

      mustBeTransitioned(INTERNALEXCHANGES__PA__PC_3__CP_2);
      mustBeTransitioned(INTERNALEXCHANGES__PA__PC_3__CP_3);
      mustBeTransitioned(INTERNALEXCHANGES__PA__PC_3__CP_4);
      shouldNotBeTransitioned(INTERNALEXCHANGES__PA__PC_3__CP_1);
      mustBeTransitioned(INTERNALEXCHANGES__PA__PC_3__CP_5);
      shouldNotBeTransitioned(INTERNALEXCHANGES__PA__PC_3__CP_6);
    }
  }
}
