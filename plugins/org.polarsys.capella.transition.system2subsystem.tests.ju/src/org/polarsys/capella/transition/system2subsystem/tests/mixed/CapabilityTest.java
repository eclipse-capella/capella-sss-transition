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

import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

//@formatter:off
/**
 * identifier:name:'Root Logical Function',id=#bb184993-1114-4e35-a598-a111f0887e03
 * identifier:name:'Capabilities',id=#7c3c5378-776e-4d07-bd7f-0c2fb8e860e4
 * identifier:name:'Logical System',id=#d26b0d40-32c5-4acb-badb-2ed32a2374d9

 * - Create 'Root Logical Function' [LogicalFunction]
 *   - Create 'FC1' [FunctionalChain]
 *     - Create '[Functional Chain Involvement] to LF1' [FunctionalChainInvolvement]
 *       > Link '[Functional Chain Involvement] to LF1' to 'FC1' [involver], 'LF1' [involved]
 *     - Create '[Functional Chain Involvement] to LF2' [FunctionalChainInvolvement]
 *       > Link '[Functional Chain Involvement] to LF2' to 'FC1' [involver], 'LF2' [involved]
 *     - Create '[Functional Chain Involvement] to FE1' [FunctionalChainInvolvement]
 *       > Link '[Functional Chain Involvement] to FE1' to 'FC1' [involver], 'FE1' [involved]
 *   - Create 'FC2' [FunctionalChain]
 *     - Create '[Functional Chain Involvement] to LF4' [FunctionalChainInvolvement]
 *       > Link '[Functional Chain Involvement] to LF4' to 'FC2' [involver], 'LF4' [involved]
 *     - Create '[Functional Chain Involvement] to LF2' [FunctionalChainInvolvement]
 *       > Link '[Functional Chain Involvement] to LF2' to 'FC2' [involver], 'LF2' [involved]
 *     - Create '[Functional Chain Involvement] to FE3' [FunctionalChainInvolvement]
 *       > Link '[Functional Chain Involvement] to FE3' to 'FC2' [involver], 'FE3' [involved]
 *   - Create '[Function Realization] to Root System Function' [FunctionRealization]
 *     > Link '[Function Realization] to Root System Function' to 'Root Logical Function' [sourceElement], 'Root System Function' [targetElement]
 *   - Create 'LF1' [LogicalFunction]
 *     - Create 'FOP11' [FunctionOutputPort]
 *     - Create 'FOP12' [FunctionOutputPort]
 *   - Create 'LF2' [LogicalFunction]
 *     - Create 'FIP21' [FunctionInputPort]
 *     - Create 'FIP22' [FunctionInputPort]
 *   - Create 'LF3' [LogicalFunction]
 *     - Create 'FIP31' [FunctionInputPort]
 *   - Create 'LF4' [LogicalFunction]
 *     - Create 'FOP41' [FunctionOutputPort]
 *   - Create 'LF5' [LogicalFunction]
 *   - Create 'FE1' [FunctionalExchange]
 *     > Link 'FE1' to 'FOP11' [source], 'FIP21' [target], 
 *   - Create 'FE2' [FunctionalExchange]
 *     > Link 'FE2' to 'FOP12' [source], 'FIP31' [target], 
 *   - Create 'FE3' [FunctionalExchange]
 *     > Link 'FE3' to 'FOP41' [source], 'FIP22' [target], 
 * - Create 'Capabilities' [CapabilityRealizationPkg]
 *   - Create 'CR1' [CapabilityRealization]
 *     - Create '[Abstract Function Abstract Capability Involvement] to LF1' [AbstractFunctionAbstractCapabilityInvolvement]
 *       > Link '[Abstract Function Abstract Capability Involvement] to LF1' to 'CR1' [involver], 'LF1' [involved]
 *   - Create 'CR2' [CapabilityRealization]
 *     - Create '[Abstract Function Abstract Capability Involvement] to LF2' [AbstractFunctionAbstractCapabilityInvolvement]
 *       > Link '[Abstract Function Abstract Capability Involvement] to LF2' to 'CR2' [involver], 'LF2' [involved]
 *   - Create 'CR3' [CapabilityRealization]
 *     - Create '[Functional Chain Abstract Capability Involvement] to FC1' [FunctionalChainAbstractCapabilityInvolvement]
 *       > Link '[Functional Chain Abstract Capability Involvement] to FC1' to 'CR3' [involver], 'FC1' [involved]
 *   - Create 'CR4' [CapabilityRealization]
 *     - Create '[Functional Chain Abstract Capability Involvement] to FC2' [FunctionalChainAbstractCapabilityInvolvement]
 *       > Link '[Functional Chain Abstract Capability Involvement] to FC2' to 'CR4' [involver], 'FC2' [involved]
 *   - Create 'CR5' [CapabilityRealization]
 *     - Create 'S51' [Scenario]
 *       - Create 'IR511' [InstanceRole]
 *       - Create 'IR512' [InstanceRole]
 *       - Create 'FE2' [SequenceMessage]
 *       - Create 'FE2' [SequenceMessage]
 *       - Create 'ME511' [MessageEnd]
 *       - Create 'ME512' [MessageEnd]
 *       - Create 'ME513' [MessageEnd]
 *       - Create 'ME514' [MessageEnd]
 *       - Create 'E511' [Execution]
 *       - Create 'ESO511' [EventSentOperation]
 *       - Create 'ERO512' [EventReceiptOperation]
 *       - Create 'ESO513' [EventSentOperation]
 *       - Create 'ERO514' [EventReceiptOperation]
 *   - Create 'CR6' [CapabilityRealization]
 *     - Create 'S61' [Scenario]
 *       - Create 'IR611' [InstanceRole]
 *       - Create 'IR612' [InstanceRole]
 *       - Create 'FE3' [SequenceMessage]
 *       - Create 'FE3' [SequenceMessage]
 *       - Create 'ME611' [MessageEnd]
 *       - Create 'ME612' [MessageEnd]
 *       - Create 'ME613' [MessageEnd]
 *       - Create 'ME614' [MessageEnd]
 *       - Create 'E611' [Execution]
 *       - Create 'ESO611' [EventSentOperation]
 *       - Create 'ERO612' [EventReceiptOperation]
 *       - Create 'ESO613' [EventSentOperation]
 *       - Create 'ERO614' [EventReceiptOperation]
 *   - Create 'CR7' [CapabilityRealization]
 *     - Create 'S71' [Scenario]
 *       - Create 'LC1' [InstanceRole]
 *       - Create 'LC3' [InstanceRole]
 *       - Create 'CE1' [SequenceMessage]
 *       - Create 'CE1' [SequenceMessage]
 *       - Create 'ME711' [MessageEnd]
 *       - Create 'ME712' [MessageEnd]
 *       - Create 'ME713' [MessageEnd]
 *       - Create 'ME714' [MessageEnd]
 *       - Create 'E711' [Execution]
 *       - Create 'ESO711' [EventSentOperation]
 *       - Create 'ERO712' [EventReceiptOperation]
 *       - Create 'ESO713' [EventSentOperation]
 *       - Create 'ERO714' [EventReceiptOperation]
 *   - Create 'CR8' [CapabilityRealization]
 *     - Create 'S81' [Scenario]
 *       - Create 'LC2' [InstanceRole]
 *       - Create 'LC3' [InstanceRole]
 *       - Create 'CE2' [SequenceMessage]
 *       - Create 'CE2' [SequenceMessage]
 *       - Create 'ME811' [MessageEnd]
 *       - Create 'ME812' [MessageEnd]
 *       - Create 'ME813' [MessageEnd]
 *       - Create 'ME814' [MessageEnd]
 *       - Create 'E811' [Execution]
 *       - Create 'ESO811' [EventSentOperation]
 *       - Create 'ERO812' [EventReceiptOperation]
 *       - Create 'ESO813' [EventSentOperation]
 *       - Create 'ERO814' [EventReceiptOperation]
 *   - Create 'CR9' [CapabilityRealization]
 *     - Create '[Abstract Function Abstract Capability Involvement] to LF5' [AbstractFunctionAbstractCapabilityInvolvement]
 *       > Link '[Abstract Function Abstract Capability Involvement] to LF5' to 'CR9' [involver], 'LF5' [involved]
 * - Create 'Logical System' [LogicalComponent]
 *   - Create 'CE1' [ComponentExchange]
 *     > Link 'CE1' to 'CP11' [source], 'CP31' [target]
 *   - Create 'CE2' [ComponentExchange]
 *     > Link 'CE2' to 'CP21' [source], 'CP32' [target]
 *   - Create 'LC1: LC1' [Part]
 *     > Link 'LC1: LC1' to 'LC1' [abstractType]
 *   - Create 'LC2: LC2' [Part]
 *     > Link 'LC2: LC2' to 'LC2' [abstractType]
 *   - Create 'LC3: LC3' [Part]
 *     > Link 'LC3: LC3' to 'LC3' [abstractType]
 *   - Create 'LC1' [LogicalComponent]
 *     - Create '[Component Functional Allocation] to LF1' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to LF1' to 'LC1' [sourceElement], 'LF1' [targetElement]
 *     - Create '[Component Functional Allocation] to LF3' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to LF3' to 'LC1' [sourceElement], 'LF3' [targetElement]
 *     - Create 'CP11' [ComponentPort]
 *       > Set 'CP11' to 'FLOW' [kind], 'OUT' [orientation]
 *     - Create 'LC11: LC11' [Part]
 *       > Link 'LC11: LC11' to 'LC11' [abstractType]
 *     - Create 'LC11' [LogicalComponent]
 *       - Create '[Component Functional Allocation] to LF5' [ComponentFunctionalAllocation]
 *         > Link '[Component Functional Allocation] to LF5' to 'LC11' [sourceElement], 'LF5' [targetElement]
 *   - Create 'LC2' [LogicalComponent]
 *     - Create '[Component Functional Allocation] to LF2' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to LF2' to 'LC2' [sourceElement], 'LF2' [targetElement]
 *     - Create '[Component Functional Allocation] to LF4' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to LF4' to 'LC2' [sourceElement], 'LF4' [targetElement]
 *     - Create 'CP21' [ComponentPort]
 *       > Set 'CP21' to 'FLOW' [kind], 'OUT' [orientation]
 *   - Create 'LC3' [LogicalComponent]
 *     - Create 'CP31' [ComponentPort]
 *       > Set 'CP31' to 'FLOW' [kind], 'IN' [orientation]
 *     - Create 'CP32' [ComponentPort]
 *       > Set 'CP32' to 'FLOW' [kind], 'IN' [orientation]
 *   - Create '[System Realization] to System' [SystemRealization]
 *     > Link '[System Realization] to System' to 'Logical System' [sourceElement], 'System' [targetElement]
 */
//@formatter:on

public class CapabilityTest extends System2SubsystemTest implements Interphase, Crossphase {

  private String _id_fc1 = "c45fc20d-1d8f-40c1-bd9f-acd1650647d3"; //$NON-NLS-1$
  private String _id_fc2 = "598c30e3-592f-4b78-bf0a-b9ad6c16691b"; //$NON-NLS-1$
  private String _id_lf1 = "f3793446-1c16-4cb2-aff5-dc04cbe8cf89"; //$NON-NLS-1$
  private String _id_lf2 = "add4bacd-8159-48b3-acbc-589ba760357b"; //$NON-NLS-1$
  private String _id_lf3 = "cef5f278-fc89-451f-b8e8-019c9cd1da07"; //$NON-NLS-1$
  private String _id_lf4 = "130aeb31-fa0d-4812-a9b7-9de8645f3351"; //$NON-NLS-1$
  private String _id_fe1 = "6714aa83-2b33-44aa-9224-0aa883077448"; //$NON-NLS-1$
  private String _id_fe2 = "7427faad-32bb-4755-bb3a-d62e5aab4986"; //$NON-NLS-1$
  private String _id_fe3 = "78cb8a45-628d-4050-a9a2-3cab4e1135b6"; //$NON-NLS-1$
  private String _id_cr1 = "b66b2de9-fd73-4de6-8bba-fcb12bd949e0"; //$NON-NLS-1$
  private String _id_cr2 = "edda4a2b-2472-43db-a001-af5e066bad0f"; //$NON-NLS-1$
  private String _id_cr3 = "e350304e-5562-4315-86f7-bf4b7f27518c"; //$NON-NLS-1$
  private String _id_cr4 = "354e57a3-9840-45ca-9193-64e2d4f0432c"; //$NON-NLS-1$
  private String _id_cr5 = "c86d0349-83d1-4404-8a0e-ccbf83431088"; //$NON-NLS-1$
  private String _id_cr6 = "865bfacf-64bc-4119-94e5-20c96758c1aa"; //$NON-NLS-1$
  private String _id_cr7 = "d3715877-2241-404a-9e8d-29f363b565fe"; //$NON-NLS-1$
  private String _id_cr8 = "fa0faebc-9d2d-4c2c-a7f6-43e1b346781b"; //$NON-NLS-1$
  private String _id_lf5 = "a8e89af9-eca2-40da-be1a-b2f4449d9da1"; //$NON-NLS-1$
  private String _id_cr9 = "f265d55b-097c-42a4-bf3c-041a0d6ff57a"; //$NON-NLS-1$
  private String _id_lc1 = "cb1ece1b-2d97-47da-9cb7-03220c049ed3"; //$NON-NLS-1$

  private String _id_s51 = "dfd9b5b4-f455-4b47-a779-2ff484cdbf03"; //$NON-NLS-1$
  private String _id_s61 = "79ac66fc-385f-4d8d-8635-0e7294813370"; //$NON-NLS-1$
  private String _id_s71 = "ce0cc1f7-5a9c-41a1-9fd7-170dc6d91c08"; //$NON-NLS-1$
  private String _id_s81 = "c994a9bc-1f08-48b3-a27e-6d660ce45c72"; //$NON-NLS-1$

  @Override
  public void setUp() throws Exception {
    super.setUp();
    addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
    addSharedParameter(IOptionsConstants.FUNCTIONAL_CHAIN_EXPORT, Boolean.TRUE);
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_lc1);
  }

  @Override
  protected void verify() {

    mustBeTransitioned(_id_lf1);
    mustBeTransitioned(_id_cr1);
    mustBeTransitioned(_id_lf2);
    shouldNotBeTransitioned(_id_cr2);

    mustBeTransitioned(_id_fc1);
    shouldNotBeTransitioned(_id_fc2);
    mustBeTransitioned(_id_cr3);
    shouldNotBeTransitioned(_id_cr4);

    mustBeTransitioned(_id_cr5);
    mustBeTransitioned(_id_s51);
    shouldNotBeTransitioned(_id_cr6);
    shouldNotBeTransitioned(_id_s61);

    mustBeTransitioned(_id_cr7);
    mustBeTransitioned(_id_s71);
    shouldNotBeTransitioned(_id_cr8);
    shouldNotBeTransitioned(_id_s81);

    mustBeTransitioned(_id_lf5);
    mustBeTransitioned(_id_cr9);

  }
}
