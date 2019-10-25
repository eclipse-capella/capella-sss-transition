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
package org.polarsys.capella.transition.system2subsystem.tests.interphases;

import java.util.Collection;

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

//@formatter:off
/**
 * Component Exchange allocation transition: Test if Component Exchange allocation are correctly imported
 * 
 * identifier:name:'Physical Context',id=#dadadfea-ded3-4a51-8ae3-2f63fd7d985d
 * identifier:name:'PS',id=#06666331-26d0-45b1-84a6-70a5d8668f3b
 * identifier:name:'Actors',id=#321668d0-49c1-4331-8633-7943ba95ce27

 * - Create 'Physical Context' [PhysicalContext]
 *   - Create 'C 1' [ComponentExchange]
 *     > Link 'C 1' to 'CP33' [source], 'CP81' [target]
 *   - Create 'PS: PS' [Part]
 *     > Link 'PS: PS' to 'PS' [abstractType]
 *   - Create 'PA1: PA1' [Part]
 *     > Link 'PA1: PA1' to 'PA1' [abstractType]
 *     - Create '[Part Deployment Link] to PC8' [PartDeploymentLink]
 * - Create 'PS' [PhysicalComponent]
 *   > Set 'PS' to 'UNSET' [kind], 'UNSET' [nature]
 *   - Create 'CE1' [ComponentExchange]
 *     > Link 'CE1' to 'CP31' [source], 'CP41' [target]
 *   - Create 'CE2' [ComponentExchange]
 *     > Link 'CE2' to 'CP32' [source], 'CP51' [target]
 *   - Create 'CE3' [ComponentExchange]
 *     > Link 'CE3' to 'CP61' [source], 'CP71' [target]
 *   - Create 'PC1: PC1' [Part]
 *     > Link 'PC1: PC1' to 'PC1' [abstractType]
 *     - Create '[Part Deployment Link] to PC6' [PartDeploymentLink]
 *   - Create 'PC2: PC2' [Part]
 *     > Link 'PC2: PC2' to 'PC2' [abstractType]
 *     - Create '[Part Deployment Link] to PC7' [PartDeploymentLink]
 *   - Create 'PC3: PC3' [Part]
 *     > Link 'PC3: PC3' to 'PC3' [abstractType]
 *   - Create 'PC4: PC4' [Part]
 *     > Link 'PC4: PC4' to 'PC4' [abstractType]
 *   - Create 'PC5: PC5' [Part]
 *     > Link 'PC5: PC5' to 'PC5' [abstractType]
 *   - Create 'PC6: PC6' [Part]
 *     > Link 'PC6: PC6' to 'PC6' [abstractType]
 *   - Create 'PC7: PC7' [Part]
 *     > Link 'PC7: PC7' to 'PC7' [abstractType]
 *   - Create 'PC8: PC8' [Part]
 *     > Link 'PC8: PC8' to 'PC8' [abstractType]
 *   - Create 'PC1' [PhysicalComponent]
 *     > Set 'PC1' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PC11: PC11' [Part]
 *       > Link 'PC11: PC11' to 'PC11' [abstractType]
 *       - Create '[Part Deployment Link] to PC3' [PartDeploymentLink]
 *     - Create 'PP12' [PhysicalPort]
 *     - Create 'PC11' [PhysicalComponent]
 *       > Set 'PC11' to 'UNSET' [kind], 'NODE' [nature]
 *       - Create 'PP111' [PhysicalPort]
 *       - Create 'PP112' [PhysicalPort]
 *   - Create 'PC2' [PhysicalComponent]
 *     > Set 'PC2' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PC21: PC21' [Part]
 *       > Link 'PC21: PC21' to 'PC21' [abstractType]
 *       - Create '[Part Deployment Link] to PC4' [PartDeploymentLink]
 *       - Create '[Part Deployment Link] to PC5' [PartDeploymentLink]
 *     - Create 'PP22' [PhysicalPort]
 *     - Create 'PC21' [PhysicalComponent]
 *       > Set 'PC21' to 'UNSET' [kind], 'NODE' [nature]
 *       - Create 'PP211' [PhysicalPort]
 *   - Create 'PC3' [PhysicalComponent]
 *     > Set 'PC3' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP31' [ComponentPort]
 *       > Set 'CP31' to 'FLOW' [kind], 'OUT' [orientation]
 *     - Create 'CP32' [ComponentPort]
 *       > Set 'CP32' to 'FLOW' [kind], 'OUT' [orientation]
 *     - Create 'CP33' [ComponentPort]
 *       > Set 'CP33' to 'FLOW' [kind], 'OUT' [orientation]
 *   - Create 'PC4' [PhysicalComponent]
 *     > Set 'PC4' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP41' [ComponentPort]
 *       > Set 'CP41' to 'FLOW' [kind], 'IN' [orientation]
 *   - Create 'PC5' [PhysicalComponent]
 *     > Set 'PC5' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP51' [ComponentPort]
 *       > Set 'CP51' to 'FLOW' [kind], 'IN' [orientation]
 *   - Create 'PC6' [PhysicalComponent]
 *     > Set 'PC6' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP61' [ComponentPort]
 *       > Set 'CP61' to 'FLOW' [kind], 'OUT' [orientation]
 *   - Create 'PC7' [PhysicalComponent]
 *     > Set 'PC7' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP71' [ComponentPort]
 *       > Set 'CP71' to 'FLOW' [kind], 'IN' [orientation]
 *   - Create 'PC8' [PhysicalComponent]
 *     > Set 'PC8' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP81' [ComponentPort]
 *       > Set 'CP81' to 'FLOW' [kind], 'IN' [orientation]
 *   - Create '[Logical Component Realization] to Logical System' [LogicalComponentRealization]
 *     > Link '[Logical Component Realization] to Logical System' to 'PS' [sourceElement], 'Logical System' [targetElement]
 *   - Create 'PL1' [PhysicalLink]
 *     - Create '[Component Exchange Allocation] to CE1' [ComponentExchangeAllocation]
 *       > Link '[Component Exchange Allocation] to CE1' to 'PL1' [sourceElement], 'CE1' [targetElement]
 *     - Create '[Component Exchange Allocation] to CE2' [ComponentExchangeAllocation]
 *       > Link '[Component Exchange Allocation] to CE2' to 'PL1' [sourceElement], 'CE2' [targetElement]
 *   - Create 'PL2' [PhysicalLink]
 *     - Create '[Component Exchange Allocation] to CE3' [ComponentExchangeAllocation]
 *       > Link '[Component Exchange Allocation] to CE3' to 'PL2' [sourceElement], 'CE3' [targetElement]
 *   - Create 'PL3' [PhysicalLink]
 *     - Create '[Component Exchange Allocation] to C 1' [ComponentExchangeAllocation]
 *       > Link '[Component Exchange Allocation] to C 1' to 'PL3' [sourceElement], 'C 1' [targetElement]
 * - Create 'Actors' [PhysicalActorPkg]
 *   - Create 'PA1' [PhysicalActor]
 *     - Create 'PP 1' [PhysicalPort]
 */
//@formatter:on
public class ComponentExchangeAllocationTest extends System2SubsystemTest implements Interphase {

  private String _id_c_1 = "dd42536a-d292-4187-8f21-29f7678e91b9"; //$NON-NLS-1$
  private String _id_ce1 = "8cd61262-4b3a-4ae6-9371-5dd123d3f5a0"; //$NON-NLS-1$
  private String _id_ce2 = "3758c3e3-289f-4c2f-9a01-4dd46b194cac"; //$NON-NLS-1$
  private String _id_ce3 = "fc2a9e59-c064-48b7-baf4-2f5cbfceae5a"; //$NON-NLS-1$
  private String _id_pc1 = "4d32c631-16c0-40cf-afb6-4493c895ab56"; //$NON-NLS-1$
  private String _id_pc2 = "c94b17dc-1e05-4856-9e34-09ac982248a8"; //$NON-NLS-1$
  private String _id_pc3 = "d6035c03-79b6-46f6-ae48-860f533bcb7a"; //$NON-NLS-1$
  private String _id_pc4 = "c95f1081-6e48-4ffa-9a1e-7c46568fe26b"; //$NON-NLS-1$
  private String _id_pc5 = "4743096b-f0a2-4658-903b-bdae0f3e7869"; //$NON-NLS-1$
  private String _id_pc6 = "b557d40f-4b5f-44e3-a78c-90defc666889"; //$NON-NLS-1$
  private String _id_pc7 = "c9c32619-1db7-48c2-80ff-2d5f5ae23afa"; //$NON-NLS-1$
  private String _id_pc8 = "ade0a50b-fef4-4fca-bc57-24ef81cd9254"; //$NON-NLS-1$
  private String _id_pl1 = "4a959c31-e335-4402-9ff5-feaa5b8766ff"; //$NON-NLS-1$
  private String _id_pl2 = "8b5b1a23-ae35-4976-a5f0-a81999e38f0b"; //$NON-NLS-1$
  private String _id_pl3 = "50eb0573-a66b-4740-a83a-6381ed73258d"; //$NON-NLS-1$
  private String _id_pa1 = "79ced10f-34dc-4dd9-b42d-bf4b0850914a"; //$NON-NLS-1$
  private String _id_pc11 = "3d381750-02af-487b-af99-a6998c82e3f9"; //$NON-NLS-1$
  private String _id_pc21 = "87b4c0eb-62de-49d7-a45b-96f0fcaf1138"; //$NON-NLS-1$

  private String _id_cea_to_ce1 = "e08547a5-02ce-4a0a-b1e0-91264a49ef97"; //$NON-NLS-1$
  private String _id_cea_to_ce2 = "45f38956-7d9a-4e2e-8a91-21628cb26fe5"; //$NON-NLS-1$
  private String _id_cea_to_ce3 = "37169317-fe20-4645-83bf-43760d3170b6"; //$NON-NLS-1$
  private String _id_cea_to_c_1 = "b1d72c6a-0db9-4751-a9c3-f2907fe4d565"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_pc1);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(_id_c_1);
    mustBeTransitioned(_id_ce1);
    mustBeTransitioned(_id_ce2);
    mustBeTransitioned(_id_ce3);
    mustBeTransitioned(_id_pl1);
    mustBeTransitioned(_id_cea_to_ce1);
    mustBeTransitioned(_id_cea_to_ce2);
    mustBeTransitioned(_id_pl2);
    mustBeTransitioned(_id_cea_to_ce3);
    mustBeTransitioned(_id_pl3);
    mustBeTransitioned(_id_cea_to_c_1);
  }

}