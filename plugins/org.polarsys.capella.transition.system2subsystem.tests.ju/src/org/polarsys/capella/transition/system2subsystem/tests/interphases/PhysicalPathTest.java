/*******************************************************************************
 * Copyright (c) 2016, 2019 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.tests.interphases;

import java.util.Collection;

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

//@formatter:off
/**
 * Physical Path transition: Test if Physical Path are correctly imported
 * identifier:name:'Physical System',id=#051137e3-97b9-4c85-af9d-0ce77c8fca8d

 * - Create 'Physical System' [PhysicalComponent]
 *   > Set 'Physical System' to 'UNSET' [kind], 'UNSET' [nature]
 *   - Create 'C 5' [ComponentExchange]
 *     > Link 'C 5' to 'CP 1' [source], 'CP 1' [target]
 *   - Create 'C 3' [ComponentExchange]
 *     > Link 'C 3' to 'CP 1' [source], 'CP 2' [target]
 *   - Create 'C 2' [ComponentExchange]
 *     > Link 'C 2' to 'CP 2' [source], 'CP 1' [target]
 *   - Create 'C 4' [ComponentExchange]
 *     > Link 'C 4' to 'CP 1' [source], 'CP 1' [target]
 *   - Create 'C 1' [ComponentExchange]
 *     > Link 'C 1' to 'CP 3' [source], 'CP 2' [target]
 *   - Create 'C 6' [ComponentExchange]
 *     > Link 'C 6' to 'CP 3' [source], 'CP 2' [target]
 *   - Create 'C 7' [ComponentExchange]
 *     > Link 'C 7' to 'CP 4' [source], 'CP 4' [target]
 *   - Create 'PC 12: PC 12' [Part]
 *     > Link 'PC 12: PC 12' to 'PC 12' [abstractType]
 *     - Create '[Part Deployment Link] to PC 13' [PartDeploymentLink]
 *   - Create 'PC 4: PC 4' [Part]
 *     > Link 'PC 4: PC 4' to 'PC 4' [abstractType]
 *     - Create '[Part Deployment Link] to PC 9' [PartDeploymentLink]
 *   - Create 'A transiter: A transiter' [Part]
 *     > Link 'A transiter: A transiter' to 'A transiter' [abstractType]
 *     - Create '[Part Deployment Link] to PC 7' [PartDeploymentLink]
 *   - Create 'PC 3: PC 3' [Part]
 *     > Link 'PC 3: PC 3' to 'PC 3' [abstractType]
 *     - Create '[Part Deployment Link] to PC 11' [PartDeploymentLink]
 *   - Create 'PC 6: PC 6' [Part]
 *     > Link 'PC 6: PC 6' to 'PC 6' [abstractType]
 *     - Create '[Part Deployment Link] to PC 10' [PartDeploymentLink]
 *   - Create 'PC 5: PC 5' [Part]
 *     > Link 'PC 5: PC 5' to 'PC 5' [abstractType]
 *     - Create '[Part Deployment Link] to PC 8' [PartDeploymentLink]
 *   - Create 'Connexion 1: Connexion 1' [Part]
 *     > Link 'Connexion 1: Connexion 1' to 'Connexion 1' [abstractType]
 *   - Create 'Connexion 2: Connexion 2' [Part]
 *     > Link 'Connexion 2: Connexion 2' to 'Connexion 2' [abstractType]
 *   - Create 'PC 13: PC 13' [Part]
 *     > Link 'PC 13: PC 13' to 'PC 13' [abstractType]
 *   - Create 'PC 9: PC 9' [Part]
 *     > Link 'PC 9: PC 9' to 'PC 9' [abstractType]
 *   - Create 'PC 7: PC 7' [Part]
 *     > Link 'PC 7: PC 7' to 'PC 7' [abstractType]
 *   - Create 'PC 11: PC 11' [Part]
 *     > Link 'PC 11: PC 11' to 'PC 11' [abstractType]
 *   - Create 'PC 10: PC 10' [Part]
 *     > Link 'PC 10: PC 10' to 'PC 10' [abstractType]
 *   - Create 'PC 8: PC 8' [Part]
 *     > Link 'PC 8: PC 8' to 'PC 8' [abstractType]
 *   - Create 'PC 15: PC 15' [Part]
 *     > Link 'PC 15: PC 15' to 'PC 15' [abstractType]
 *   - Create 'PC 12' [PhysicalComponent]
 *     > Set 'PC 12' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PP 1' [PhysicalPort]
 *     - Create 'PP 1' [PhysicalPort]
 *     - Create 'PL 8' [PhysicalLink]
 *   - Create 'PC 4' [PhysicalComponent]
 *     > Set 'PC 4' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PP 1' [PhysicalPort]
 *     - Create 'PP 2' [PhysicalPort]
 *   - Create 'A transiter' [PhysicalComponent]
 *     > Set 'A transiter' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PP 1' [PhysicalPort]
 *     - Create 'PP 2' [PhysicalPort]
 *     - Create 'PP 3' [PhysicalPort]
 *   - Create 'PC 3' [PhysicalComponent]
 *     > Set 'PC 3' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PP 1' [PhysicalPort]
 *     - Create 'PP 2' [PhysicalPort]
 *   - Create 'PC 6' [PhysicalComponent]
 *     > Set 'PC 6' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PP 1' [PhysicalPort]
 *   - Create 'PC 5' [PhysicalComponent]
 *     > Set 'PC 5' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PP 1' [PhysicalPort]
 *   - Create 'Connexion 1' [PhysicalComponent]
 *     > Set 'Connexion 1' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PP 1' [PhysicalPort]
 *     - Create 'PP 2' [PhysicalPort]
 *   - Create 'Connexion 2' [PhysicalComponent]
 *     > Set 'Connexion 2' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PP 1' [PhysicalPort]
 *     - Create 'PP 2' [PhysicalPort]
 *   - Create 'PC 13' [PhysicalComponent]
 *     > Set 'PC 13' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP 2' [ComponentPort]
 *       > Set 'CP 2' to 'FLOW' [kind], 'IN' [orientation]
 *     - Create 'CP 4' [ComponentPort]
 *       > Set 'CP 4' to 'FLOW' [kind], 'IN' [orientation]
 *     - Create 'CP 1' [ComponentPort]
 *       > Set 'CP 1' to 'FLOW' [kind], 'IN' [orientation]
 *   - Create 'PC 9' [PhysicalComponent]
 *     > Set 'PC 9' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP 1' [ComponentPort]
 *       > Set 'CP 1' to 'FLOW' [kind], 'OUT' [orientation]
 *     - Create 'CP 2' [ComponentPort]
 *       > Set 'CP 2' to 'FLOW' [kind], 'IN' [orientation]
 *     - Create 'CP 3' [ComponentPort]
 *       > Set 'CP 3' to 'FLOW' [kind], 'OUT' [orientation]
 *     - Create 'CP 4' [ComponentPort]
 *       > Set 'CP 4' to 'FLOW' [kind], 'OUT' [orientation]
 *   - Create 'PC 7' [PhysicalComponent]
 *     > Set 'PC 7' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP 1' [ComponentPort]
 *       > Set 'CP 1' to 'FLOW' [kind], 'OUT' [orientation]
 *     - Create 'CP 2' [ComponentPort]
 *       > Set 'CP 2' to 'FLOW' [kind], 'OUT' [orientation]
 *     - Create 'CP 3' [ComponentPort]
 *       > Set 'CP 3' to 'FLOW' [kind], 'OUT' [orientation]
 *   - Create 'PC 11' [PhysicalComponent]
 *     > Set 'PC 11' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP 1' [ComponentPort]
 *       > Set 'CP 1' to 'FLOW' [kind], 'IN' [orientation]
 *   - Create 'PC 10' [PhysicalComponent]
 *     > Set 'PC 10' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP 1' [ComponentPort]
 *       > Set 'CP 1' to 'FLOW' [kind], 'OUT' [orientation]
 *   - Create 'PC 8' [PhysicalComponent]
 *     > Set 'PC 8' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create 'CP 1' [ComponentPort]
 *       > Set 'CP 1' to 'FLOW' [kind], 'IN' [orientation]
 *     - Create 'CP 2' [ComponentPort]
 *       > Set 'CP 2' to 'FLOW' [kind], 'IN' [orientation]
 *   - Create 'PC 15' [PhysicalComponent]
 *     > Set 'PC 15' to 'UNSET' [kind], 'NODE' [nature]
 *     - Create 'PP 2' [PhysicalPort]
 *   - Create '[Logical Component Realization] to Logical System' [LogicalComponentRealization]
 *     > Link '[Logical Component Realization] to Logical System' to 'Physical System' [sourceElement], 'Logical System' [targetElement]
 *   - Create 'PhysicalPath 1' [PhysicalPath]
 *     - Create '[Component Exchange Allocation] to C 2' [ComponentExchangeAllocation]
 *       > Link '[Component Exchange Allocation] to C 2' to 'PhysicalPath 1' [sourceElement], 'C 2' [targetElement]
 *     - Create '[Component Exchange Allocation] to C 3' [ComponentExchangeAllocation]
 *       > Link '[Component Exchange Allocation] to C 3' to 'PhysicalPath 1' [sourceElement], 'C 3' [targetElement]
 *     - Create '[Component Exchange Allocation] to C 7' [ComponentExchangeAllocation]
 *       > Link '[Component Exchange Allocation] to C 7' to 'PhysicalPath 1' [sourceElement], 'C 7' [targetElement]
 *     - Create '[Component Exchange Allocation] to C 5' [ComponentExchangeAllocation]
 *       > Link '[Component Exchange Allocation] to C 5' to 'PhysicalPath 1' [sourceElement], 'C 5' [targetElement]
 *     - Create '[Physical Path Involvement] to PC 4' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 4' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PC 3' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 3' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to A transiter' [PhysicalPathInvolvement]
 *   - Create 'PhysicalPath 2' [PhysicalPath]
 *     - Create '[Physical Path Involvement] to A transiter' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 1' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to Connexion 1' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 2' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to Connexion 2' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 7' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PC 5' [PhysicalPathInvolvement]
 *   - Create 'PhysicalPath 3' [PhysicalPath]
 *     - Create '[Component Exchange Allocation] to C 4' [ComponentExchangeAllocation]
 *       > Link '[Component Exchange Allocation] to C 4' to 'PhysicalPath 3' [sourceElement], 'C 4' [targetElement]
 *     - Create '[Component Exchange Allocation] to C 1' [ComponentExchangeAllocation]
 *       > Link '[Component Exchange Allocation] to C 1' to 'PhysicalPath 3' [sourceElement], 'C 1' [targetElement]
 *     - Create '[Physical Path Involvement] to A transiter' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 5' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PC 6' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 1' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to Connexion 1' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 2' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to Connexion 2' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 7' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PC 5' [PhysicalPathInvolvement]
 *   - Create 'PhysicalPath 4' [PhysicalPath]
 *     - Create '[Physical Path Involvement] to PC 15' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 8' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PC 12' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PL 6' [PhysicalPathInvolvement]
 *     - Create '[Physical Path Involvement] to PC 4' [PhysicalPathInvolvement]
 *   - Create 'PL 6' [PhysicalLink]
 *   - Create 'PL 4' [PhysicalLink]
 *   - Create 'PL 3' [PhysicalLink]
 *   - Create 'PL 5' [PhysicalLink]
 *   - Create 'PL 1' [PhysicalLink]
 *   - Create 'PL 2' [PhysicalLink]
 *   - Create 'PL 7' [PhysicalLink]
 */
//@formatter:on

public class PhysicalPathTest extends System2SubsystemTest implements Interphase {

  private String _id_c_5 = "6dbecd55-0645-40bd-9a79-dfc90bcf243d"; //$NON-NLS-1$
  private String _id_c_3 = "610fe8d4-26d7-4c99-9113-ac082f8705a7"; //$NON-NLS-1$
  private String _id_c_2 = "96b17786-2ca5-465d-8586-ff1208cbe53e"; //$NON-NLS-1$
  private String _id_c_4 = "9692640d-d748-417c-9001-e0a47e00296f"; //$NON-NLS-1$
  private String _id_c_1 = "30dd93be-f717-41b6-b028-dcaa1e9cc5fe"; //$NON-NLS-1$
  private String _id_pc_12 = "b8c02f07-d8cb-4fca-a762-8c283e23c876"; //$NON-NLS-1$
  private String _id_pc_4 = "23b3f765-2851-46df-a3f3-d4df296dd31c"; //$NON-NLS-1$
  private String _id_a_transiter = "0c9fba98-ef62-49d2-b7ee-345d8e2f10e0"; //$NON-NLS-1$
  private String _id_pc_3 = "9300c634-3b3c-46ce-a81f-bc3ef05f2aef"; //$NON-NLS-1$
  private String _id_pc_6 = "c76d536e-a498-4604-88ff-d722c9cc42ad"; //$NON-NLS-1$
  private String _id_pc_5 = "a40d8e1a-f157-4f17-a61b-5db7d4790082"; //$NON-NLS-1$
  private String _id_connexion_1 = "2bd43943-32ea-4f8a-9b47-9743a70a1ff1"; //$NON-NLS-1$
  private String _id_connexion_2 = "6a3117d1-2c4d-4756-ad4e-486f33ed35f8"; //$NON-NLS-1$
  private String _id_pc_13 = "c724a0e3-4469-4ab9-8a0a-92baf0c25c44"; //$NON-NLS-1$
  private String _id_pc_9 = "2ccb7c61-aa2b-438e-a433-5164d8009cd6"; //$NON-NLS-1$
  private String _id_pc_7 = "05261e77-050b-4a55-acdf-7aeb5a0d7ba8"; //$NON-NLS-1$
  private String _id_pc_11 = "1afbc71a-9a09-4f4b-afe5-ee7ba9bf3e04"; //$NON-NLS-1$
  private String _id_pc_10 = "2bb11395-27b6-47b0-afc2-4c8af44af720"; //$NON-NLS-1$
  private String _id_pc_8 = "e72d1277-f9b7-487d-9a74-35f7bc949aa2"; //$NON-NLS-1$
  private String _id_physicalpath_1 = "f05da34b-c494-42da-9823-261bac0fc050"; //$NON-NLS-1$
  private String _id_physicalpath_2 = "5b51ef84-d9ff-439b-a76d-ee39bf1c8444"; //$NON-NLS-1$
  private String _id_physicalpath_3 = "b1d9ca9c-1919-4dfa-91d2-c8b9a1a9d955"; //$NON-NLS-1$
  private String _id_pl_6 = "360f956a-2b35-4dd5-bdff-dcc2e5b099fb"; //$NON-NLS-1$
  private String _id_pl_4 = "0a8ffb8d-07fa-42cf-b8ba-3c38e21e4404"; //$NON-NLS-1$
  private String _id_pl_3 = "0208876b-c348-41a8-9d2f-c966735a99b5"; //$NON-NLS-1$
  private String _id_pl_5 = "ac0043d0-bb34-4a09-97bd-953d6b9782db"; //$NON-NLS-1$
  private String _id_pl_1 = "3c209925-dd67-4d06-9566-51262edd5ff4"; //$NON-NLS-1$
  private String _id_pl_2 = "ce90fcda-5c10-4c75-8833-3dddd8232d3f"; //$NON-NLS-1$
  private String _id_pl_7 = "89a6d73d-9edf-4dd0-9786-c986bc7c5eff"; //$NON-NLS-1$

  private String _id_cea_to_c_2 = "a24481e9-ef14-4177-971e-e24b8d2e26ba"; //$NON-NLS-1$
  private String _id_cea_to_c_3 = "59752c4a-9fcb-4df9-bcb1-587ebf5c36a8"; //$NON-NLS-1$
  private String _id_cea_to_c_7 = "5489c1a7-c6e7-4172-8457-519a519046cf"; //$NON-NLS-1$
  private String _id_cea_to_c_5 = "347c9640-f631-4761-8d84-f1793f1634fe"; //$NON-NLS-1$
  private String _id_cea_to_c_4 = "b8c30b5b-78fb-448c-a392-2b9e982a7f77"; //$NON-NLS-1$
  private String _id_cea_to_c_1 = "fa1adca6-fa2a-4989-8508-7cf71402fd8a"; //$NON-NLS-1$

  private String _id_c_6 = "944d590d-0665-4cfb-bd5f-ce84367b1724"; //$NON-NLS-1$
  private String _id_c_7 = "6cca2f3e-2451-4890-8cd7-ae089af5f4bc"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_a_transiter);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(_id_c_3);
    mustBeTransitioned(_id_c_2);
    mustBeTransitioned(_id_c_1);
    mustBeTransitioned(_id_pc_4);
    mustBeTransitioned(_id_a_transiter);
    mustBeTransitioned(_id_pc_3);
    mustBeTransitioned(_id_pc_6);
    mustBeTransitioned(_id_pc_5);
    mustBeTransitioned(_id_connexion_1);
    mustBeTransitioned(_id_connexion_2);
    mustBeTransitioned(_id_pc_9);
    mustBeTransitioned(_id_pc_7);
    mustBeTransitioned(_id_pc_11);
    mustBeTransitioned(_id_pc_8);
    mustBeTransitioned(_id_physicalpath_1);
    mustBeTransitioned(_id_physicalpath_2);
    mustBeTransitioned(_id_physicalpath_3);
    mustBeTransitioned(_id_pl_4);
    mustBeTransitioned(_id_pl_3);
    mustBeTransitioned(_id_pl_5);
    mustBeTransitioned(_id_pl_1);
    mustBeTransitioned(_id_pl_2);
    mustBeTransitioned(_id_pl_7);
    shouldNotBeTransitioned(_id_c_5);
    shouldNotBeTransitioned(_id_c_4);
    shouldNotBeTransitioned(_id_pc_13);
    shouldNotBeTransitioned(_id_pc_10);
    mustBeTransitioned(_id_pl_6);

    shouldNotBeTransitioned(_id_c_5);
    shouldNotBeTransitioned(_id_c_6);
    shouldNotBeTransitioned(_id_c_7);
    shouldNotBeTransitioned(_id_cea_to_c_7);
    shouldNotBeTransitioned(_id_cea_to_c_5);
    shouldNotBeTransitioned(_id_cea_to_c_4);
    mustBeTransitioned(_id_cea_to_c_2);
    mustBeTransitioned(_id_cea_to_c_3);
    mustBeTransitioned(_id_cea_to_c_1);
  }
}
