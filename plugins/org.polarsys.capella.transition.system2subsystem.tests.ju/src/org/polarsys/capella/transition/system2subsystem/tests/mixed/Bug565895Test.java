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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.polarsys.capella.core.data.information.communication.CommunicationPackage;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

public class Bug565895Test extends System2SubsystemTest implements Crossphase, Interphase {
  
  /** test */
  public static final String interface1_ID = "46c1e1ed-9058-4d0c-a500-a6e78e3bd794"; //$NON-NLS-1$
  public static final String exchangeItem1_ID = "71c24fa0-6769-46f1-9987-83b7c837ceb7"; //$NON-NLS-1$
  public static final String exchangeItem2_ID = "52bde1bf-1ae1-49f5-ab47-b7c821d12da8"; //$NON-NLS-1$
  public static final String comLink_ID = "529fb0fb-6e84-408b-8e5d-a6f4899cbff4"; //$NON-NLS-1$
  public static final String generalization_ID = "5b9716b9-4a6e-48f3-b71a-758599d0d7d8"; //$NON-NLS-1$
  
  public static final String EXCHANGEITEM_1 = "c77a71c9-0652-4f68-bdd0-42f46c3aa5f9"; //$NON-NLS-1$
  public static final String EXCHANGEITEM_3 = "8d8b830c-0739-4378-8a28-08cc5d790f21"; //$NON-NLS-1$
  public static final String EXCHANGEITEM_2 = "5e0c60a6-c3c7-48d4-aea2-72083ab513c5"; //$NON-NLS-1$
  public static final String EXCHANGEITEM_4 = "4a993979-6409-4807-b67b-f320993d919c"; //$NON-NLS-1$
  public static final String LINK1_TO_EXCHANGEITEM_1 = "329ef257-283f-4ef6-8a10-36b9fa125907";  //$NON-NLS-1$
  public static final String LINK2_TO_EXCHANGEITEM_1 = "528f8520-6aea-4703-a4bd-d240a6784f91"; //$NON-NLS-1$
  public static final String LINK2_TO_EXCHANGEITEM_2 = "a1aca21a-0f18-45c1-bff2-70cc3f665662"; //$NON-NLS-1$
  public static final String LINK3_TO_EXCHANGEITEM_2 = "7f593627-4042-4735-8f00-ea6e6306adc6"; //$NON-NLS-1$
  
  public static final String PC_1 = "3e6f14e8-983b-42dc-9d5d-0065073c0315"; //$NON-NLS-1$
  public static final String PC_2 = "53339442-c7c5-487d-8d0e-c280fe184504"; //$NON-NLS-1$
  public static final String PC_3 = "f460d96d-2682-4423-aacc-6fdb44bf72e0"; //$NON-NLS-1$
  
  public static final String PC_11 = "2df75d46-df63-4789-8c50-8f9516b8c1e7"; //$NON-NLS-1$
  public static final String EXCHANGEITEM_5 = "089a1503-1b71-4f98-a090-040baaa391cd"; //$NON-NLS-1$
  public static final String LINK_TO_EXCHANGEITEM_5 = "dabcff85-a52d-4d94-863f-9be19b4f0b4e"; //$NON-NLS-1$
  
  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("test", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return Collections.singleton(getObject(PC_1));
  }

  @Override
  protected void verify() {
    mustBeTransitioned(PC_1);
    mustBeTransitioned(interface1_ID);
    mustBeTransitioned(exchangeItem1_ID);
    mustBeTransitioned(exchangeItem2_ID);
    mustBeTransitioned(generalization_ID);
    mustBeTransitioned(comLink_ID);

    // Generalization of EI shall be retrieved on all EI propagated.
    mustBeTransitioned(EXCHANGEITEM_1);
    mustBeTransitioned(EXCHANGEITEM_3);
    shouldNotBeTransitioned(EXCHANGEITEM_2);
    shouldNotBeTransitioned(EXCHANGEITEM_4);

    // Exchange Items not connected directly to components from source scope shall not be included.
    // and referencing CL shall not be created with null target then.
    shouldNotBeTransitioned(PC_3);
    mustBeTransitioned(PC_2);
    mustBeTransitioned(LINK1_TO_EXCHANGEITEM_1);
    mustBeTransitioned(LINK2_TO_EXCHANGEITEM_1);
    mustBeTransitionedAndLinkedTo(LINK1_TO_EXCHANGEITEM_1, EXCHANGEITEM_1, CommunicationPackage.Literals.COMMUNICATION_LINK__EXCHANGE_ITEM);
    mustBeTransitionedAndLinkedTo(LINK2_TO_EXCHANGEITEM_1, EXCHANGEITEM_1, CommunicationPackage.Literals.COMMUNICATION_LINK__EXCHANGE_ITEM);
    mustBeTransitionedInto(LINK1_TO_EXCHANGEITEM_1, PC_1);
    mustBeTransitionedInto(LINK2_TO_EXCHANGEITEM_1, PC_2);
    shouldNotBeTransitioned(LINK2_TO_EXCHANGEITEM_2);
    shouldNotBeTransitioned(LINK3_TO_EXCHANGEITEM_2);
    
    // Communication Link on sub component shall be created on the root component on CrossPhase, on child on Interphase
    mustBeTransitioned(LINK_TO_EXCHANGEITEM_5);
    mustBeTransitionedAndLinkedTo(LINK_TO_EXCHANGEITEM_5, EXCHANGEITEM_5, CommunicationPackage.Literals.COMMUNICATION_LINK__EXCHANGE_ITEM);
    mustBeTransitionedInto(LINK_TO_EXCHANGEITEM_5, kind == Kind.CROSS_PHASES ? PC_1 : PC_11);

  }
}
