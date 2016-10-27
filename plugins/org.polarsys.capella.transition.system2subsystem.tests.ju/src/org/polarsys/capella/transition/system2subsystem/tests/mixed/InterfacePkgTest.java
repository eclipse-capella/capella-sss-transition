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

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

//@formatter:off
/**
 * InterfacePkg transition: Test if hierarchical InterfacePkg are correctly imported
 * identifier:name:'PF1',id=#bffacf92-1758-4884-85b3-7c1a241a87ad
 * identifier:name:'Interfaces',id=#1cc5c0fd-f4f1-4102-bc1b-3cd29536eeac
 * identifier:name:'PS',id=#4abf8d44-bf4d-48f0-bcbf-426e586cff6c

 * - Create 'PF1' [PhysicalFunction]
 *   - Create '[Function Realization] to Root Logical Function' [FunctionRealization]
 *     > Link '[Function Realization] to Root Logical Function' to 'PF1' [sourceElement], 'Root Logical Function' [targetElement]
 *   - Create 'PF11' [PhysicalFunction]
 *     - Create 'FOP111' [FunctionOutputPort]
 *   - Create 'PF12' [PhysicalFunction]
 *     - Create 'FIP121' [FunctionInputPort]
 *   - Create 'FE11' [FunctionalExchange]
 *     > Link 'FE11' to 'FOP111' [source], 'FIP121' [target]
 *     > Link 'FE11' to '{EI212, EI111}' [exchangedItems]
 * - Create 'Interfaces' [InterfacePkg]
 *   - Create 'IP1' [InterfacePkg]
 *     - Create 'IP11' [InterfacePkg]
 *       - Create 'EI111' [ExchangeItem]
 *       - Create 'IP111' [InterfacePkg]
 *     - Create 'IP12' [InterfacePkg]
 *   - Create 'IP2' [InterfacePkg]
 *     - Create 'IP21' [InterfacePkg]
 *       - Create 'EI211' [ExchangeItem]
 *       - Create 'EI212' [ExchangeItem]
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
 */
//@formatter:on

public class InterfacePkgTest extends System2SubsystemTest implements Interphase, Crossphase {

  private final String _id_ip1 = "45ff088e-4fb4-4751-a1c2-15c591dbf59f"; //$NON-NLS-1$
  private final String _id_ip11 = "e9b6183e-491a-47d9-92a7-9bf889cefc98"; //$NON-NLS-1$
  private final String _id_ei111 = "8cc1889a-8948-4e45-9f80-cc75a36581ea"; //$NON-NLS-1$
  private final String _id_ip111 = "7cf9bc03-4a67-4bc5-9880-eb23ce6e6e3b"; //$NON-NLS-1$
  private final String _id_ip12 = "863fc17f-0a2e-4681-9778-07ac125ed80a"; //$NON-NLS-1$
  private final String _id_ip2 = "2cfd37d0-e0ea-4563-ad5b-a0ded682c08b"; //$NON-NLS-1$
  private final String _id_ip21 = "e460a7e9-53df-4821-9cac-9ab3bdc11e75"; //$NON-NLS-1$
  private final String _id_ei211 = "1b22a535-6449-4e2f-ac9f-b424eeac629c"; //$NON-NLS-1$
  private final String _id_ei212 = "c3e291af-03bf-49b9-918b-5eb4a6b89fdf"; //$NON-NLS-1$
  private final String _id_pc1 = "594a09ce-ff3b-4e81-9f84-bac1321efa4f"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_pc1);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(_id_ip1);
    mustBeTransitioned(_id_ip11);
    mustBeTransitioned(_id_ei111);
    mustBeTransitioned(_id_ip2);
    mustBeTransitioned(_id_ip21);
    mustBeTransitioned(_id_ei212);
    shouldNotBeTransitioned(_id_ip111);
    shouldNotBeTransitioned(_id_ip12);
    shouldNotBeTransitioned(_id_ei211);
  }

}
