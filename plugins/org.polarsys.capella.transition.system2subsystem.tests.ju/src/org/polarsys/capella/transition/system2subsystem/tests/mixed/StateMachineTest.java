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

import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Multiphase;

//@formatter:off
/**
 * identifier:name:'Physical Functions',id=#42c33fab-3b52-43e7-b4c2-9b129445ede9
 * identifier:name:'PS',id=#aa22456c-7415-480f-a8cb-95ce6a1c3932

 * - Create 'Physical Functions' [PhysicalFunctionPkg]
 *   - Create 'PF1' [PhysicalFunction]
 *     - Create '[Function Realization] to Root Logical Function' [FunctionRealization]
 *       > Link '[Function Realization] to Root Logical Function' to 'PF1' [sourceElement], 'Root Logical Function' [targetElement]
 *     - Create 'PF11' [PhysicalFunction]
 *       - Create 'FOP111' [FunctionOutputPort]
 *       - Create 'FOP112' [FunctionOutputPort]
 *     - Create 'PF12' [PhysicalFunction]
 *       - Create 'FIP121' [FunctionInputPort]
 *     - Create 'PF13' [PhysicalFunction]
 *       - Create 'FIP131' [FunctionInputPort]
 *     - Create 'PhysicalFunction 4' [PhysicalFunction]
 *       > Link 'PhysicalFunction 4' to '{FS1111, FS3111, FS11111, FS2111, FS111, FS21111, FS211, FS4111}' [availableInStates]
 *       - Create 'FOP 1' [FunctionOutputPort]
 *     - Create 'PhysicalFunction 5' [PhysicalFunction]
 *       > Link 'PhysicalFunction 5' to '{FS1111, FS3111, FS11111, FS2111, FS111, FS21111, FS211, FS4111}' [availableInStates]
 *       - Create 'FIP 1' [FunctionInputPort]
 *     - Create 'FE11' [FunctionalExchange]
 *       > Link 'FE11' to 'FOP111' [source], 'FIP121' [target]
 *       > Link 'FE11' to '{EI212, EI111}' [exchangedItems]
 *     - Create 'FE12' [FunctionalExchange]
 *       > Link 'FE12' to 'FOP112' [source], 'FIP131' [target]
 *     - Create 'Exchange 3' [FunctionalExchange]
 *       > Link 'Exchange 3' to 'FOP 1' [source], 'FIP 1' [target]

 * - Create 'PS' [PhysicalComponent]
 *   > Set 'PS' to 'UNSET' [kind], 'UNSET' [nature]
 *   - Create 'SM1' [StateMachine]
 *     - Create 'R11' [Region]
 *       - Create 'FS111' [FinalState]
 *         - Create 'R1111' [Region]
 *       - Create 'FPS112' [ForkPseudoState]
 *   - Create 'SM2' [StateMachine]
 *     - Create 'R21' [Region]
 *       - Create 'FS211' [FinalState]
 *         - Create 'R2111' [Region]
 *       - Create 'FPS212' [ForkPseudoState]
 *   - Create 'PC1: PC1' [Part]
 *     > Link 'PC1: PC1' to 'PC1' [abstractType]
 *   - Create 'PC2: PC2' [Part]
 *     > Link 'PC2: PC2' to 'PC2' [abstractType]
 *   - Create 'PC3: PC3' [Part]
 *     > Link 'PC3: PC3' to 'PC3' [abstractType]
 *   - Create 'PC4: PC4' [Part]
 *     > Link 'PC4: PC4' to 'PC4' [abstractType]
 *   - Create 'PC1' [PhysicalComponent]
 *     > Set 'PC1' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'SM11' [StateMachine]
 *       - Create 'R111' [Region]
 *         - Create 'FS1111' [FinalState]
 *           - Create 'R11111' [Region]
 *         - Create 'FPS1112' [ForkPseudoState]
 *     - Create 'PC11: PC11' [Part]
 *       > Link 'PC11: PC11' to 'PC11' [abstractType]
 *       - Create '[Part Deployment Link] to PC4' [PartDeploymentLink]
 *     - Create 'PC11' [PhysicalComponent]
 *       > Set 'PC11' to 'UNSET' [kind], 'NODE' [nature]
 *       - Create 'SM111' [StateMachine]
 *         - Create 'R1111' [Region]
 *           - Create 'FS11111' [FinalState]
 *             - Create 'R111111' [Region]
 *           - Create 'FPS11112' [ForkPseudoState]
 *   - Create 'PC2' [PhysicalComponent]
 *     > Set 'PC2' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'SM21' [StateMachine]
 *       - Create 'R211' [Region]
 *         - Create 'FS2111' [FinalState]
 *           - Create 'R21111' [Region]
 *         - Create 'FPS2112' [ForkPseudoState]
 *     - Create 'PC21: PC21' [Part]
 *       > Link 'PC21: PC21' to 'PC21' [abstractType]
 *       - Create '[Part Deployment Link] to PC3' [PartDeploymentLink]
 *     - Create 'PC21' [PhysicalComponent]
 *       > Set 'PC21' to 'UNSET' [kind], 'NODE' [nature]
 *       - Create 'SM211' [StateMachine]
 *         - Create 'R2111' [Region]
 *           - Create 'FS21111' [FinalState]
 *             - Create 'R211111' [Region]
 *           - Create 'FPS21112' [ForkPseudoState]
 *   - Create 'PC3' [PhysicalComponent]
 *     > Set 'PC3' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create '[Component Functional Allocation] to PhysicalFunction 5' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to PhysicalFunction 5' to 'PC3' [sourceElement], 'PhysicalFunction 5' [targetElement]
 *     - Create 'SM31' [StateMachine]
 *       - Create 'R311' [Region]
 *         - Create 'FS3111' [FinalState]
 *           - Create 'R31111' [Region]
 *         - Create 'FPS3112' [ForkPseudoState]
 *   - Create 'PC4' [PhysicalComponent]
 *     > Set 'PC4' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create '[Component Functional Allocation] to PhysicalFunction 4' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to PhysicalFunction 4' to 'PC4' [sourceElement], 'PhysicalFunction 4' [targetElement]
 *     - Create 'SM41' [StateMachine]
 *       - Create 'R411' [Region]
 *         - Create 'FS4111' [FinalState]
 *           - Create 'R41111' [Region]
 *         - Create 'FPS4112' [ForkPseudoState]
 *   - Create '[Logical Component Realization] to Logical System' [LogicalComponentRealization]
 *     > Link '[Logical Component Realization] to Logical System' to 'PS' [sourceElement], 'Logical System' [targetElement]
 */
//@formatter:on

public class StateMachineTest {

  /**
   * StateMachine transition: Test if hierarchical StateMachine are correctly imported
   */
  public static class Test1 extends System2SubsystemTest implements Interphase, Crossphase {

    private String _id_pc11 = "4561d20e-cbd6-4c1c-ade5-fd46c54e33d4"; //$NON-NLS-1$

    private String _id_fs111 = "b0ecb948-11b1-4583-b9b3-456b96c14b3c"; //$NON-NLS-1$
    private String _id_fps112 = "2925d1e3-0390-4bb2-b701-509a1f62e504"; //$NON-NLS-1$
    private String _id_fs211 = "c4a3ac5e-858b-4e60-9c0f-af90fde09fc0"; //$NON-NLS-1$
    private String _id_fps212 = "d50168b5-71ac-4ddc-a67d-d59d8beb2e8d"; //$NON-NLS-1$
    private String _id_fs1111 = "cc577f66-633e-4967-91ff-85024e2ca80c"; //$NON-NLS-1$
    private String _id_fps1112 = "df9f68bf-0e86-40f9-b95d-6e8bafc3bbca"; //$NON-NLS-1$
    private String _id_fs11111 = "eef12620-dfb0-4213-baef-7577d5713758"; //$NON-NLS-1$
    private String _id_fps11112 = "b5ddc47b-7ce8-4c98-b1cb-d25277c29a3c"; //$NON-NLS-1$
    private String _id_fs2111 = "e359a527-d97a-4e3c-98cb-a729e6e62c33"; //$NON-NLS-1$
    private String _id_fps2112 = "7ea5e708-cd61-4df5-9b9f-febb6cfc471f"; //$NON-NLS-1$
    private String _id_fs21111 = "4bb5f4c1-d4f8-42b2-90c3-665c2ee09c7c"; //$NON-NLS-1$
    private String _id_fps21112 = "d8649229-09d9-418b-a856-3438bd12d5b9"; //$NON-NLS-1$
    private String _id_fs3111 = "042ea569-4c34-4153-b6ee-0013b3e7e64c"; //$NON-NLS-1$
    private String _id_fps3112 = "747215d0-ab82-4a6e-871b-31ff89ef5252"; //$NON-NLS-1$
    private String _id_fs4111 = "fea8f023-a210-4552-8ecf-cb8d4fde6aff"; //$NON-NLS-1$
    private String _id_fps4112 = "4711bfd0-52ea-4c8d-8f5b-d71db45c09e4"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.STATE_MODES_EXPORT, IOptionsConstants.STATE_MODES_HIERARCHICAL_VALUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_fs1111);
      mustBeTransitioned(_id_fps1112);
      mustBeTransitioned(_id_fs11111);
      mustBeTransitioned(_id_fps11112);
      mustBeTransitioned(_id_fs4111);
      mustBeTransitioned(_id_fps4112);
      mustBeTransitioned(_id_fs111);
      mustBeTransitioned(_id_fps112);
      mustBeTransitioned(_id_fs211);
      mustBeTransitioned(_id_fps212);
      shouldNotBeTransitioned(_id_fs2111);
      shouldNotBeTransitioned(_id_fps2112);
      shouldNotBeTransitioned(_id_fs21111);
      shouldNotBeTransitioned(_id_fps21112);
      shouldNotBeTransitioned(_id_fs3111);
      shouldNotBeTransitioned(_id_fps3112);

    }
  }

  /**
   * StateMachine transition: Test if only component ref StateMachine are correctly imported
   */
  public static class Test2 extends System2SubsystemTest implements Interphase, Crossphase {
    private String _id_pc11 = "4561d20e-cbd6-4c1c-ade5-fd46c54e33d4"; //$NON-NLS-1$

    private String _id_fs111 = "b0ecb948-11b1-4583-b9b3-456b96c14b3c"; //$NON-NLS-1$
    private String _id_fps112 = "2925d1e3-0390-4bb2-b701-509a1f62e504"; //$NON-NLS-1$
    private String _id_fs211 = "c4a3ac5e-858b-4e60-9c0f-af90fde09fc0"; //$NON-NLS-1$
    private String _id_fps212 = "d50168b5-71ac-4ddc-a67d-d59d8beb2e8d"; //$NON-NLS-1$
    private String _id_fs1111 = "cc577f66-633e-4967-91ff-85024e2ca80c"; //$NON-NLS-1$
    private String _id_fps1112 = "df9f68bf-0e86-40f9-b95d-6e8bafc3bbca"; //$NON-NLS-1$
    private String _id_fs11111 = "eef12620-dfb0-4213-baef-7577d5713758"; //$NON-NLS-1$
    private String _id_fps11112 = "b5ddc47b-7ce8-4c98-b1cb-d25277c29a3c"; //$NON-NLS-1$
    private String _id_fs2111 = "e359a527-d97a-4e3c-98cb-a729e6e62c33"; //$NON-NLS-1$
    private String _id_fps2112 = "7ea5e708-cd61-4df5-9b9f-febb6cfc471f"; //$NON-NLS-1$
    private String _id_fs21111 = "4bb5f4c1-d4f8-42b2-90c3-665c2ee09c7c"; //$NON-NLS-1$
    private String _id_fps21112 = "d8649229-09d9-418b-a856-3438bd12d5b9"; //$NON-NLS-1$
    private String _id_fs3111 = "042ea569-4c34-4153-b6ee-0013b3e7e64c"; //$NON-NLS-1$
    private String _id_fps3112 = "747215d0-ab82-4a6e-871b-31ff89ef5252"; //$NON-NLS-1$
    private String _id_fs4111 = "fea8f023-a210-4552-8ecf-cb8d4fde6aff"; //$NON-NLS-1$
    private String _id_fps4112 = "4711bfd0-52ea-4c8d-8f5b-d71db45c09e4"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.STATE_MODES_EXPORT, IOptionsConstants.STATE_MODES_ONLY_REFERENCES_VALUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {

      shouldNotBeTransitioned(_id_fs111);
      shouldNotBeTransitioned(_id_fps112);
      shouldNotBeTransitioned(_id_fs211);
      shouldNotBeTransitioned(_id_fps212);
      shouldNotBeTransitioned(_id_fs1111);
      shouldNotBeTransitioned(_id_fps1112);
      mustBeTransitioned(_id_fs11111);
      mustBeTransitioned(_id_fps11112);
      mustBeTransitioned(_id_fs4111);
      mustBeTransitioned(_id_fps4112);
      shouldNotBeTransitioned(_id_fs2111);
      shouldNotBeTransitioned(_id_fps2112);
      shouldNotBeTransitioned(_id_fs21111);
      shouldNotBeTransitioned(_id_fps21112);
      shouldNotBeTransitioned(_id_fs3111);
      shouldNotBeTransitioned(_id_fps3112);

    }
  }

  /**
   * StateMachine transition: Test if hierarchical StateMachine are correctly imported
   */
  public static class Test3 extends System2SubsystemTest implements Multiphase {
    private String _id_pc11 = "4561d20e-cbd6-4c1c-ade5-fd46c54e33d4"; //$NON-NLS-1$
    private String _id_fs111 = "b0ecb948-11b1-4583-b9b3-456b96c14b3c"; //$NON-NLS-1$
    private String _id_fps112 = "2925d1e3-0390-4bb2-b701-509a1f62e504"; //$NON-NLS-1$
    private String _id_fs211 = "c4a3ac5e-858b-4e60-9c0f-af90fde09fc0"; //$NON-NLS-1$
    private String _id_fps212 = "d50168b5-71ac-4ddc-a67d-d59d8beb2e8d"; //$NON-NLS-1$
    private String _id_fs1111 = "cc577f66-633e-4967-91ff-85024e2ca80c"; //$NON-NLS-1$
    private String _id_fps1112 = "df9f68bf-0e86-40f9-b95d-6e8bafc3bbca"; //$NON-NLS-1$
    private String _id_fs11111 = "eef12620-dfb0-4213-baef-7577d5713758"; //$NON-NLS-1$
    private String _id_fps11112 = "b5ddc47b-7ce8-4c98-b1cb-d25277c29a3c"; //$NON-NLS-1$
    private String _id_fs2111 = "e359a527-d97a-4e3c-98cb-a729e6e62c33"; //$NON-NLS-1$
    private String _id_fps2112 = "7ea5e708-cd61-4df5-9b9f-febb6cfc471f"; //$NON-NLS-1$
    private String _id_fs21111 = "4bb5f4c1-d4f8-42b2-90c3-665c2ee09c7c"; //$NON-NLS-1$
    private String _id_fps21112 = "d8649229-09d9-418b-a856-3438bd12d5b9"; //$NON-NLS-1$
    private String _id_fs3111 = "042ea569-4c34-4153-b6ee-0013b3e7e64c"; //$NON-NLS-1$
    private String _id_fps3112 = "747215d0-ab82-4a6e-871b-31ff89ef5252"; //$NON-NLS-1$
    private String _id_fs4111 = "fea8f023-a210-4552-8ecf-cb8d4fde6aff"; //$NON-NLS-1$
    private String _id_fps4112 = "4711bfd0-52ea-4c8d-8f5b-d71db45c09e4"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.STATE_MODES_EXPORT, IOptionsConstants.STATE_MODES_HIERARCHICAL_VALUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {
      mustBeTransitionedInto(_id_fs1111, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fps1112, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fs11111, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fps11112, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fs4111, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fps4112, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fs111, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fps112, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fs211, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fps212, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fs2111, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fps2112, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fs21111, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fps21112, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fs3111, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fps3112, ComponentType.SYSTEM_ANALYSIS);

      mustBeTransitionedInto(_id_fs1111, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps1112, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs11111, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps11112, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs4111, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps4112, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs111, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps112, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs211, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps212, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs2111, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps2112, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs21111, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps21112, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs3111, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps3112, ComponentType.LOGICAL_ARCHITECTURE);

      mustBeTransitionedInto(_id_fs1111, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps1112, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs11111, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps11112, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs4111, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps4112, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs111, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps112, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs211, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps212, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs2111, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps2112, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs21111, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps21112, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs3111, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps3112, ComponentType.PHYSICAL_ARCHITECTURE);

    }

  }

  /**
   * StateMachine transition Test if only component ref StateMachine are correctly imported
   */
  public static class Test4 extends System2SubsystemTest implements Multiphase {

    private String _id_pc11 = "4561d20e-cbd6-4c1c-ade5-fd46c54e33d4"; //$NON-NLS-1$

    private String _id_fs111 = "b0ecb948-11b1-4583-b9b3-456b96c14b3c"; //$NON-NLS-1$
    private String _id_fps112 = "2925d1e3-0390-4bb2-b701-509a1f62e504"; //$NON-NLS-1$
    private String _id_fs211 = "c4a3ac5e-858b-4e60-9c0f-af90fde09fc0"; //$NON-NLS-1$
    private String _id_fps212 = "d50168b5-71ac-4ddc-a67d-d59d8beb2e8d"; //$NON-NLS-1$
    private String _id_fs1111 = "cc577f66-633e-4967-91ff-85024e2ca80c"; //$NON-NLS-1$
    private String _id_fps1112 = "df9f68bf-0e86-40f9-b95d-6e8bafc3bbca"; //$NON-NLS-1$
    private String _id_fs11111 = "eef12620-dfb0-4213-baef-7577d5713758"; //$NON-NLS-1$
    private String _id_fps11112 = "b5ddc47b-7ce8-4c98-b1cb-d25277c29a3c"; //$NON-NLS-1$
    private String _id_fs2111 = "e359a527-d97a-4e3c-98cb-a729e6e62c33"; //$NON-NLS-1$
    private String _id_fps2112 = "7ea5e708-cd61-4df5-9b9f-febb6cfc471f"; //$NON-NLS-1$
    private String _id_fs21111 = "4bb5f4c1-d4f8-42b2-90c3-665c2ee09c7c"; //$NON-NLS-1$
    private String _id_fps21112 = "d8649229-09d9-418b-a856-3438bd12d5b9"; //$NON-NLS-1$
    private String _id_fs3111 = "042ea569-4c34-4153-b6ee-0013b3e7e64c"; //$NON-NLS-1$
    private String _id_fps3112 = "747215d0-ab82-4a6e-871b-31ff89ef5252"; //$NON-NLS-1$
    private String _id_fs4111 = "fea8f023-a210-4552-8ecf-cb8d4fde6aff"; //$NON-NLS-1$
    private String _id_fps4112 = "4711bfd0-52ea-4c8d-8f5b-d71db45c09e4"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.STATE_MODES_EXPORT, IOptionsConstants.STATE_MODES_ONLY_REFERENCES_VALUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {

      shouldNotBeTransitionedInto(_id_fs111, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fps112, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fs211, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fps212, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fs1111, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fps1112, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fs11111, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fps11112, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fs4111, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(_id_fps4112, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fs2111, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fps2112, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fs21111, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fps21112, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fs3111, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(_id_fps3112, ComponentType.SYSTEM_ANALYSIS);

      shouldNotBeTransitionedInto(_id_fs111, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps112, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs211, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps212, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs1111, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps1112, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs11111, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps11112, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs4111, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps4112, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs2111, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps2112, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs21111, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps21112, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs3111, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps3112, ComponentType.LOGICAL_ARCHITECTURE);

      shouldNotBeTransitionedInto(_id_fs111, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps112, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs211, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps212, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs1111, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps1112, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs11111, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps11112, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fs4111, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(_id_fps4112, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs2111, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps2112, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs21111, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps21112, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fs3111, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(_id_fps3112, ComponentType.PHYSICAL_ARCHITECTURE);

    }
  }
}
