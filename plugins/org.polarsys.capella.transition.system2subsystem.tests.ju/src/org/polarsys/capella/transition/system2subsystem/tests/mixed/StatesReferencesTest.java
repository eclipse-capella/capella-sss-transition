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
package org.polarsys.capella.transition.system2subsystem.tests.mixed;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.behavior.AbstractEvent;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.data.capellacommon.CapellacommonPackage;
import org.polarsys.capella.core.data.capellacommon.State;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

/**
 * StateMachine transition: Test if hierarchical StateMachine are correctly imported
 */
public class StatesReferencesTest extends System2SubsystemTest implements Interphase, Crossphase {

  private String _id_logicalfunction_1 = "178c3b9e-8620-480e-af4b-ef310d3bdf63"; //$NON-NLS-1$
  private String _id_logicalfunction_2 = "7662a65f-aba8-4aa6-99f6-e66fb1053f26"; //$NON-NLS-1$
  private String _id_logicalfunction_3 = "428bb542-525f-4647-817c-2d3fe8308bd7"; //$NON-NLS-1$
  private String _id_ls = "d909bc18-dd5c-4f2b-8196-0cb3eb40dfa9"; //$NON-NLS-1$
  private String _id_sm1 = "7ad2bc6f-5d68-4be1-a82b-45d145f54ed2"; //$NON-NLS-1$
  private String _id_r11 = "b0023e1a-5517-4ebc-930c-8714127d9bd7"; //$NON-NLS-1$
  private String _id_m111 = "b0276dbc-87f9-420b-900e-5f2cb7c25f6d"; //$NON-NLS-1$
  private String _id_r1111 = "681b300d-d657-49e5-b24a-3035ff863386"; //$NON-NLS-1$
  private String _id_fs11111 = "dec47805-4d01-48fd-8f9c-1f825f8658ce"; //$NON-NLS-1$
  private String _id_r111111 = "eff892ea-61c4-431e-8849-c310100e39d0"; //$NON-NLS-1$
  private String _id_m112 = "bc2bdfa4-b018-47c4-aafc-c8449ada45dc"; //$NON-NLS-1$
  private String _id_r1121 = "075c4561-0d39-4e08-90b5-7d0bd3793281"; //$NON-NLS-1$
  private String _id_lc1 = "8bce62a4-2147-4321-86af-a98bdec3e3ff"; //$NON-NLS-1$
  private String _id_sm11 = "1861edc3-de3f-46e2-808a-7c6815722b0a"; //$NON-NLS-1$
  private String _id_r111 = "28b6b005-bc8b-4486-9b3f-5c7273f0cbe2"; //$NON-NLS-1$
  private String _id_fs1111 = "72b4b002-e1f5-4692-b8dd-cd397c91c6ae"; //$NON-NLS-1$
  private String _id_r11111 = "54240901-a79e-4bd0-bb01-4f526609264c"; //$NON-NLS-1$
  private String _id_lc2 = "20c6afae-224d-4ead-b3cc-a88d159e9ece"; //$NON-NLS-1$
  private String _id_sm21 = "f842b79c-6be4-45ab-96d4-2f10527229cc"; //$NON-NLS-1$
  private String _id_r211 = "583fd6f5-a93d-41ec-a324-5e1b8f7ea3ba"; //$NON-NLS-1$
  private String _id_tps2111 = "561b4575-54bf-43d6-9f02-bd222cf4f3ef"; //$NON-NLS-1$
  private String _id_s2112 = "b432fd4a-9f33-4b6c-9a4e-1473da1e68e6"; //$NON-NLS-1$
  private String _id_root_logical_function = "60eb052e-e8a1-4166-bdea-2ba9396d805c"; //$NON-NLS-1$
  private String _id_logicalfunction_4 = "160b9095-3aaa-497b-ac22-da6a06c23418"; //$NON-NLS-1$

  private String _id_st_to_m112 = "6ec08c38-d45d-4fa6-8899-52b300f22974"; //$NON-NLS-1$
  private String _id_st_to_m1122 = "90a9dde9-d910-439e-acff-051527fe735e"; //$NON-NLS-1$

  @Override
  public void setUp() throws Exception {
    super.setUp();
    addSharedParameter(IOptionsConstants.STATE_MODES_EXPORT, IOptionsConstants.STATE_MODES_DEFAULT_VALUE);
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_lc1);
  }

  @Override
  protected void verify() {

    mustBeTransitioned(_id_logicalfunction_1);
    mustBeTransitioned(_id_logicalfunction_2);
    mustBeTransitioned(_id_logicalfunction_3);
    shouldNotBeTransitioned(_id_logicalfunction_4);
    mustBeTransitioned(_id_sm1);
    mustBeTransitioned(_id_r11);
    mustBeTransitioned(_id_m111);
    mustBeTransitioned(_id_r1111);
    mustBeTransitioned(_id_fs11111);
    mustBeTransitioned(_id_r111111);
    mustBeTransitioned(_id_m112);
    mustBeTransitioned(_id_r1121);
    mustBeTransitioned(_id_lc1);
    mustBeTransitioned(_id_sm11);
    mustBeTransitioned(_id_r111);
    mustBeTransitioned(_id_fs1111);
    mustBeTransitioned(_id_r11111);
    shouldNotBeTransitioned(_id_sm21);
    shouldNotBeTransitioned(_id_r211);
    shouldNotBeTransitioned(_id_tps2111);
    shouldNotBeTransitioned(_id_s2112);

    // Function.availableInStates
    mustBeTransitionedAndLinkedTo(_id_logicalfunction_1, _id_m111,
        FaPackage.Literals.ABSTRACT_FUNCTION__AVAILABLE_IN_STATES);
    mustBeTransitionedAndLinkedTo(_id_logicalfunction_1, _id_m112,
        FaPackage.Literals.ABSTRACT_FUNCTION__AVAILABLE_IN_STATES);
    mustBeTransitionedAndLinkedTo(_id_logicalfunction_2, _id_m111,
        FaPackage.Literals.ABSTRACT_FUNCTION__AVAILABLE_IN_STATES);
    mustBeTransitionedAndLinkedTo(_id_logicalfunction_2, _id_m112,
        FaPackage.Literals.ABSTRACT_FUNCTION__AVAILABLE_IN_STATES);
    mustBeTransitionedAndLinkedTo(_id_logicalfunction_3, _id_m111,
        FaPackage.Literals.ABSTRACT_FUNCTION__AVAILABLE_IN_STATES);
    mustBeTransitionedAndNotLinkedTo(_id_logicalfunction_3, _id_s2112,
        FaPackage.Literals.ABSTRACT_FUNCTION__AVAILABLE_IN_STATES);

    // Region.involvedStates
    mustBeTransitionedAndLinkedTo(_id_r11, _id_m111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    mustBeTransitionedAndLinkedTo(_id_r11, _id_m112, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    mustBeTransitionedAndLinkedTo(_id_r11, _id_fs11111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    mustBeTransitionedAndLinkedTo(_id_r11, _id_fs1111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    mustBeTransitionedAndNotLinkedTo(_id_r11, _id_tps2111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);

    // Region.involvedStates
    mustBeTransitionedAndLinkedTo(_id_r1111, _id_fs11111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);

    // Region.involvedStates
    mustBeTransitionedAndLinkedTo(_id_r111111, _id_m111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    mustBeTransitionedAndLinkedTo(_id_r111111, _id_m112, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    mustBeTransitionedAndLinkedTo(_id_r111111, _id_fs11111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    mustBeTransitionedAndLinkedTo(_id_r111111, _id_fs1111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    mustBeTransitionedAndNotLinkedTo(_id_r111111, _id_tps2111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);

    // Region.involvedStates
    // mustBeTransitionedAndLinkedTo(_id_r111, _id_m111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    // mustBeTransitionedAndLinkedTo(_id_r111, _id_m112, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    mustBeTransitionedAndLinkedTo(_id_r111, _id_fs11111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);
    mustBeTransitionedAndLinkedTo(_id_r111, _id_fs1111, CapellacommonPackage.Literals.REGION__INVOLVED_STATES);

    // Mode.referencedStates
    mustBeTransitionedAndLinkedTo(_id_m111, _id_fs11111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndLinkedTo(_id_m111, _id_fs1111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndNotLinkedTo(_id_m111, _id_tps2111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);

    // Mode.referencedStates
    mustBeTransitionedAndLinkedTo(_id_fs11111, _id_m111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndLinkedTo(_id_fs11111, _id_m112, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndNotLinkedTo(_id_fs11111, _id_tps2111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndLinkedTo(_id_fs11111, _id_fs1111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);

    // Mode.referencedStates
    mustBeTransitionedAndLinkedTo(_id_m112, _id_m111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndNotLinkedTo(_id_m112, _id_tps2111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndLinkedTo(_id_m112, _id_fs11111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndLinkedTo(_id_m112, _id_fs1111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);

    // Mode.referencedStates
    mustBeTransitionedAndLinkedTo(_id_fs1111, _id_m111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndLinkedTo(_id_fs1111, _id_m112, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndNotLinkedTo(_id_fs1111, _id_tps2111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);
    mustBeTransitionedAndLinkedTo(_id_fs1111, _id_fs11111, ModellingcorePackage.Literals.ISTATE__REFERENCED_STATES);

    // Mode.doActivity
    mustBeTransitionedAndLinkedTo(_id_m111, _id_logicalfunction_1, CapellacommonPackage.Literals.STATE__DO_ACTIVITY);
    mustBeTransitionedAndLinkedTo(_id_m112, _id_logicalfunction_3, CapellacommonPackage.Literals.STATE__DO_ACTIVITY);
    mustBeTransitionedAndNotLinkedTo(_id_fs1111, _id_logicalfunction_4,
        CapellacommonPackage.Literals.STATE__DO_ACTIVITY);

    EObject source = getObject(_id_root_logical_function);
    if (BlockArchitectureExt.getRootFunction(BlockArchitectureExt.getRootBlockArchitecture(source)) == source) {
      State target = (State) retrieveTargetElement(_id_fs11111);
      List<AbstractEvent> value = target.getDoActivity();
      assertEquals(1, value.size());
      assertTrue(value.get(0) instanceof AbstractFunction);
      assertTrue(BlockArchitectureExt
          .getRootFunction(BlockArchitectureExt.getRootBlockArchitecture(value.get(0))) == value.get(0));
    }

    // StateTransition
    mustBeTransitionedAndLinkedTo(_id_st_to_m1122, _id_logicalfunction_1,
        CapellacommonPackage.Literals.STATE_TRANSITION__EFFECT);
    // mustBeTransitionedAndLinkedTo(_id_st_to_m1122, _id_logicalfunction_2,
    // CapellacommonPackage.Literals.STATE_TRANSITION__TRIGGER);
    mustBeTransitionedAndLinkedTo(_id_st_to_m112, _id_logicalfunction_3,
        CapellacommonPackage.Literals.STATE_TRANSITION__EFFECT);
    // mustBeTransitionedAndNotLinkedTo(_id_st_to_m112, _id_logicalfunction_4,
    // CapellacommonPackage.Literals.STATE_TRANSITION__TRIGGER);

    // testAttributeIdentity(getObject(_id_st_to_m112), retrieveTargetElement(_id_st_to_m112),
    // CapellacommonPackage.Literals.STATE_TRANSITION__GUARD);
    testAttributeIdentity(getObject(_id_st_to_m112), retrieveTargetElement(_id_st_to_m112),
        CapellacommonPackage.Literals.STATE_TRANSITION__KIND);
    testAttributeIdentity(getObject(_id_st_to_m112), retrieveTargetElement(_id_st_to_m112),
        CapellacommonPackage.Literals.STATE_TRANSITION__TRIGGER_DESCRIPTION);

    // testAttributeIdentity(getObject(_id_st_to_m1122), retrieveTargetElement(_id_st_to_m1122),
    // CapellacommonPackage.Literals.STATE_TRANSITION__GUARD);
    testAttributeIdentity(getObject(_id_st_to_m1122), retrieveTargetElement(_id_st_to_m1122),
        CapellacommonPackage.Literals.STATE_TRANSITION__KIND);
    testAttributeIdentity(getObject(_id_st_to_m1122), retrieveTargetElement(_id_st_to_m1122),
        CapellacommonPackage.Literals.STATE_TRANSITION__TRIGGER_DESCRIPTION);

  }
}
