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

import org.polarsys.capella.core.data.information.InformationPackage;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

//@formatter:off
/**
 * Test ExchangeItemElement kind and direction propagation
 * identifier:name:'Physical Functions',id=#05cf849f-9db1-401b-8c4b-96825155a66e
 * identifier:name:'PS',id=#3f454e9a-effa-4907-804c-2fd02018add5  
 */
//@formatter:on

public class ExchangeItemTest extends System2SubsystemTest implements Interphase, Crossphase {

  private String _id_e1 = "b63d4273-8d91-4a30-ba9f-0120fede25eb"; //$NON-NLS-1$
  private String _id_e1_float = "2778351d-e660-48b9-a9b3-953398e9cb93"; //$NON-NLS-1$
  private String _id_e2 = "2ecd231e-8920-4d06-8773-7736380fcb34"; //$NON-NLS-1$
  private String _id_e2_byte = "0129d47e-6389-4858-bbbf-f3956b85605c"; //$NON-NLS-1$
  private String _id_e3_byte = "f7735b8a-9715-4c44-b1fa-85ac2a1423ba"; //$NON-NLS-1$
  private String _id_e4_byte = "7a02faaa-3be8-4b36-a017-34a6176ad1fb"; //$NON-NLS-1$
  private String _id_e5_byte = "22b98700-d966-4da3-b247-66e95ae69827"; //$NON-NLS-1$
  private String _id_e3 = "9c7a3c3a-98ce-4646-aeb3-353dab8031de"; //$NON-NLS-1$
  private String _id_e6_float = "9e3c379d-57f8-438a-a148-747f0dd75c3b"; //$NON-NLS-1$
  private String _id_e4 = "f217135c-8b9c-4d1d-9f7e-51b03ee0872e"; //$NON-NLS-1$
  private String _id_e7_double = "81adbe60-fcb5-4c32-977a-38daca5bbe26"; //$NON-NLS-1$
  private String _id_e5 = "f724f91c-077e-4e09-8c4d-fb48687eb71a"; //$NON-NLS-1$
  private String _id_e8_boolean = "9752d64c-864f-4864-841f-63574c37cfa7"; //$NON-NLS-1$
  private String _id_pc1 = "e566f7bd-0d66-486c-bc56-67a23f771e9e"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_pc1);
  }

  protected void sameItemElement(String id) {
    mustBeTransitioned(id);
    testAttributeIdentity(getObject(id), mustBeTransitioned(id),
        InformationPackage.Literals.EXCHANGE_ITEM_ELEMENT__COMPOSITE);
    testAttributeIdentity(getObject(id), mustBeTransitioned(id),
        InformationPackage.Literals.EXCHANGE_ITEM_ELEMENT__DIRECTION);
    testAttributeIdentity(getObject(id), mustBeTransitioned(id),
        InformationPackage.Literals.EXCHANGE_ITEM_ELEMENT__KIND);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(_id_pc1);

    mustBeTransitioned(_id_e1);
    sameItemElement(_id_e1_float);
    mustBeTransitioned(_id_e2);
    sameItemElement(_id_e2_byte);
    sameItemElement(_id_e3_byte);
    sameItemElement(_id_e4_byte);
    sameItemElement(_id_e5_byte);
    mustBeTransitioned(_id_e3);
    sameItemElement(_id_e6_float);
    mustBeTransitioned(_id_e4);
    sameItemElement(_id_e7_double);
    mustBeTransitioned(_id_e5);
    sameItemElement(_id_e8_boolean);
  }
}
