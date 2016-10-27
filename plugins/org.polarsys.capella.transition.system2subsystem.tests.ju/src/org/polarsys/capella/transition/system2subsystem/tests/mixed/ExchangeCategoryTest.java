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

import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

//@formatter:off
/**
 * Exchange category transition: Test if exchange category are correctly transitioned
 * 
 * identifier:name:'Physical Functions',id=#05cf849f-9db1-401b-8c4b-96825155a66e
 * identifier:name:'PS',id=#3f454e9a-effa-4907-804c-2fd02018add5

 * - Create 'Physical Functions' [PhysicalFunctionPkg]
 *   - Create 'EC1' [ExchangeCategory]
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
 *       - Create 'FOP131' [FunctionOutputPort]
 *     - Create 'PF14' [PhysicalFunction]
 *       - Create 'FIP141' [FunctionInputPort]
 *     - Create 'FE11' [FunctionalExchange]
 *       > Link 'FE11' to 'FOP111' [source], 'FIP121' [target]
 *       > Link 'FE11' to '{EC1111, EC1}' [categories]
 *     - Create 'FE12' [FunctionalExchange]
 *       > Link 'FE12' to 'FOP112' [source], 'FIP131' [target]
 *       > Link 'FE12' to '{EC1111}' [categories]
 *     - Create 'FE13' [FunctionalExchange]
 *       > Link 'FE13' to 'FOP131' [source], 'FIP141' [target]
 *       > Link 'FE13' to '{EC1121}' [categories]
 *     - Create 'PFP11' [PhysicalFunctionPkg]
 *       - Create 'PFP111' [PhysicalFunctionPkg]
 *         - Create 'EC1111' [ExchangeCategory]
 *       - Create 'PFP112' [PhysicalFunctionPkg]
 *         - Create 'EC1121' [ExchangeCategory]
 * - Create 'PS' [PhysicalComponent]
 *   > Set 'PS' to 'UNSET' [kind], 'UNSET' [nature]
 *   - Create 'PC 1: PC 1' [Part]
 *     > Link 'PC 1: PC 1' to 'PC 1' [abstractType]
 *     - Create '[Part Deployment Link] to PC 3' [PartDeploymentLink]
 *   - Create 'PC 2: PC 2' [Part]
 *     > Link 'PC 2: PC 2' to 'PC 2' [abstractType]
 *     - Create '[Part Deployment Link] to PC 4' [PartDeploymentLink]
 *   - Create 'PC 3: PC 3' [Part]
 *     > Link 'PC 3: PC 3' to 'PC 3' [abstractType]
 *   - Create 'PC 4: PC 4' [Part]
 *     > Link 'PC 4: PC 4' to 'PC 4' [abstractType]
 *   - Create 'PC 1' [PhysicalComponent]
 *     > Set 'PC 1' to 'UNSET' [kind], 'NODE' [nature]
 *   - Create 'PC 2' [PhysicalComponent]
 *     > Set 'PC 2' to 'UNSET' [kind], 'NODE' [nature]
 *   - Create 'PC 3' [PhysicalComponent]
 *     > Set 'PC 3' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create '[Component Functional Allocation] to PF11' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to PF11' to 'PC 3' [sourceElement], 'PF11' [targetElement]
 *     - Create '[Component Functional Allocation] to PF12' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to PF12' to 'PC 3' [sourceElement], 'PF12' [targetElement]
 *   - Create 'PC 4' [PhysicalComponent]
 *     > Set 'PC 4' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create '[Component Functional Allocation] to PF13' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to PF13' to 'PC 4' [sourceElement], 'PF13' [targetElement]
 *   - Create '[Logical Component Realization] to Logical System' [LogicalComponentRealization]
 *     > Link '[Logical Component Realization] to Logical System' to 'PS' [sourceElement], 'Logical System' [targetElement]
 */
//@formatter:on

public class ExchangeCategoryTest extends System2SubsystemTest implements Interphase, Crossphase {

  private String _id_ec1 = "ecbe53dc-f66d-4e19-8850-4a80433e6a22"; //$NON-NLS-1$
  private String _id_fe11 = "86d261ca-08a6-4c14-a5bc-1145ba127993"; //$NON-NLS-1$
  private String _id_fe12 = "3a69c4da-4d82-4825-907b-22ee680429da"; //$NON-NLS-1$
  private String _id_fe13 = "c8d798aa-c4fa-41bb-9e0a-d2f7a651cab0"; //$NON-NLS-1$
  private String _id_ec1111 = "36f6df92-4f7f-4cff-b2ca-ff718ea021cf"; //$NON-NLS-1$
  private String _id_ec1121 = "d0d0f6ea-53be-44c5-9a6a-32f26cdf1a25"; //$NON-NLS-1$
  private String _id_pc_1 = "1c00b6c0-67f2-47de-93e2-8745fd93045e"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_pc_1);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(_id_ec1);
    mustBeTransitioned(_id_fe11);
    mustBeTransitioned(_id_fe12);
    mustBeTransitioned(_id_ec1111);
    shouldNotBeTransitioned(_id_ec1121);

    mustBeTransitionedAndLinkedTo(_id_fe11, _id_ec1, FaPackage.Literals.FUNCTIONAL_EXCHANGE__CATEGORIES);
    mustBeTransitionedAndLinkedTo(_id_fe11, _id_ec1111, FaPackage.Literals.FUNCTIONAL_EXCHANGE__CATEGORIES);
    mustBeTransitionedAndLinkedTo(_id_fe12, _id_ec1111, FaPackage.Literals.FUNCTIONAL_EXCHANGE__CATEGORIES);

  }
}
