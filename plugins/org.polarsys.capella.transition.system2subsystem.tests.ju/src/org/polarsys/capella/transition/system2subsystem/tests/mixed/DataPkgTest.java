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

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

/**
 * <pre>
 * DataPkg transition: Test that hierarchical DataPkg are correctly imported
 * 
 * identifier:name:'PF1',id=#3faafaed-b16f-4875-9aec-cac27a31d576
 * identifier:name:'Data',id=#f7157f10-821f-40d1-9fe4-db709ca785c8
 * identifier:name:'PS',id=#e3529bbc-0c8d-48aa-a31e-d93af0cb75e9
 
 * - Create 'PF1' [PhysicalFunction]
 *   - Create '[Function Realization] to Root Logical Function' [FunctionRealization]
 *     > Link '[Function Realization] to Root Logical Function' to 'PF1' [sourceElement], 'Root Logical Function' [targetElement]
 *   - Create 'PF11' [PhysicalFunction]
 *     - Create 'FOP111' [FunctionOutputPort]
 *   - Create 'PF12' [PhysicalFunction]
 *     - Create 'FIP121' [FunctionInputPort]
 *   - Create 'FE11' [FunctionalExchange]
 *     > Link 'FE11' to 'FOP111' [source], 'FIP121' [target]
 *     > Link 'FE11' to '{EI211}' [exchangedItems]
 * - Create 'Data' [DataPkg]
 *   - Create 'DP1' [DataPkg]
 *     - Create 'DP11' [DataPkg]
 *       - Create 'C111' [Class]
 *     - Create 'DP12' [DataPkg]
 *     - Create 'BT11' [BooleanType]
 *   - Create 'DP2' [DataPkg]
 *     - Create 'EI21' [ExchangeItem]
 *     - Create 'DP21' [DataPkg]
 *       - Create 'EI211' [ExchangeItem]
 *         - Create 'EIE2111: C111' [ExchangeItemElement]
 *           > Link 'EIE2111: C111' to 'C111' [abstractType]
 *           - Create '[minCard] LNV21111 = 1' [LiteralNumericValue]
 *           - Create '[maxCard] LNV21111 = 1' [LiteralNumericValue]
 *       - Create 'EI212' [ExchangeItem]
 *         - Create 'EIE2121: BT11' [ExchangeItemElement]
 *           > Link 'EIE2121: BT11' to 'BT11' [abstractType]
 *           - Create '[minCard] LNV21211 = 1' [LiteralNumericValue]
 *           - Create '[maxCard] LNV21211 = 1' [LiteralNumericValue]
 *       - Create 'DP211' [DataPkg]
 *     - Create 'DP22' [DataPkg]
 * - Create 'PS' [PhysicalComponent]
 *   > Set 'PS' to 'UNSET' [kind], 'UNSET' [nature]
 *   - Create 'PC1: PC1' [Part]
 *     > Link 'PC1: PC1' to 'PC1' [abstractType]
 *     - Create '[Part Deployment Link] to PC3' [PartDeploymentLink]
 *   - Create 'PC2: PC2' [Part]
 *     > Link 'PC2: PC2' to 'PC2' [abstractType]
 *     - Create '[Part Deployment Link] to PC4' [PartDeploymentLink]
 *   - Create 'PC3: PC3' [Part]
 *     > Link 'PC3: PC3' to 'PC3' [abstractType]
 *   - Create 'PC4: PC4' [Part]
 *     > Link 'PC4: PC4' to 'PC4' [abstractType]
 *   - Create 'PC1' [PhysicalComponent]
 *     > Set 'PC1' to 'UNSET' [kind], 'NODE' [nature]
 *   - Create 'PC2' [PhysicalComponent]
 *     > Set 'PC2' to 'UNSET' [kind], 'NODE' [nature]
 *   - Create 'PC3' [PhysicalComponent]
 *     > Set 'PC3' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create '[Component Functional Allocation] to PF11' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to PF11' to 'PC3' [sourceElement], 'PF11' [targetElement]
 *   - Create 'PC4' [PhysicalComponent]
 *     > Set 'PC4' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create '[Component Functional Allocation] to PF12' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to PF12' to 'PC4' [sourceElement], 'PF12' [targetElement]
 *   - Create '[Logical Component Realization] to Logical System' [LogicalComponentRealization]
 *     > Link '[Logical Component Realization] to Logical System' to 'PS' [sourceElement], 'Logical System' [targetElement]
 * </pre>
 */

public class DataPkgTest extends System2SubsystemTest implements Interphase, Crossphase {

  private String _id_data = "f7157f10-821f-40d1-9fe4-db709ca785c8"; //$NON-NLS-1$
  private String _id_dp1 = "64602844-6e34-4ee1-9225-67fa83dd679c"; //$NON-NLS-1$
  private String _id_dp11 = "344c87e5-85ad-43b7-80c2-a5b2730a2193"; //$NON-NLS-1$
  private String _id_c111 = "70bb39da-accc-4726-872e-5809e3510b22"; //$NON-NLS-1$
  private String _id_bt11 = "dc0bdecc-1f30-4fec-a07a-87bc3123f657"; //$NON-NLS-1$
  private String _id_dp2 = "d8664b73-695f-4af7-a970-d199188858fd"; //$NON-NLS-1$
  private String _id_ei21 = "f6cbbb38-52cb-4694-a5e4-062ae9972415"; //$NON-NLS-1$
  private String _id_dp21 = "48342344-f690-4c08-be53-dcea16a14444"; //$NON-NLS-1$
  private String _id_ei211 = "2d59542b-a502-4124-be15-aec1ff4ab286"; //$NON-NLS-1$
  private String _id_ei212 = "53c4e530-2016-4d99-99f9-cb17e52eabe6"; //$NON-NLS-1$
  private String _id_ps = "e3529bbc-0c8d-48aa-a31e-d93af0cb75e9"; //$NON-NLS-1$
  private String _id_pc1 = "59bda89b-9e0d-4267-8cff-bf11a4c53d92"; //$NON-NLS-1$

  private String _id_dp12 = "e6086959-ab05-4250-8665-4348ca1406de"; //$NON-NLS-1$
  private String _id_dp211 = "2d47f318-9305-484f-b839-c15801e0254d"; //$NON-NLS-1$
  private String _id_dp22 = "73812e7a-012c-43d4-a300-b1efd35a2969"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_pc1);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(_id_dp1);
    mustBeTransitioned(_id_dp11);
    mustBeTransitioned(_id_c111);
    shouldNotBeTransitioned(_id_bt11);

    mustBeTransitioned(_id_dp2);
    shouldNotBeTransitioned(_id_ei21);
    mustBeTransitioned(_id_dp21);
    mustBeTransitioned(_id_ei211);
    shouldNotBeTransitioned(_id_ei212);

    shouldNotBeTransitioned(_id_dp12);
    shouldNotBeTransitioned(_id_dp211);
    shouldNotBeTransitioned(_id_dp22);
  }
}
