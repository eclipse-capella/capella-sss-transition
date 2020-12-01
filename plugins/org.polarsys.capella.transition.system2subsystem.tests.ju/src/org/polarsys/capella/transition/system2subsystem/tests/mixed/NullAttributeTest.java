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

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

//@formatter:off
/**
 * Null Attribute transition: A null attribute should not be transionned to 'null' string value
 * 
 * identifier:name:'Physical Functions',id=#adcf3857-102d-4f39-8b82-d340a4ffd7e5
 * identifier:name:'Physical System',id=#0b814053-f06e-469c-a450-4633e11a57ca

 * - Create 'Physical Functions' [PhysicalFunctionPkg]
 *   - Create 'Root Physical Function' [PhysicalFunction]
 *     - Create '[Function Realization] to Root Logical Function' [FunctionRealization]
 *       > Link '[Function Realization] to Root Logical Function' to 'Root Physical Function' [sourceElement], 'Root Logical Function' [targetElement]
 *     - Create '[Physical Function]' [PhysicalFunction]
 * - Create 'Physical System' [PhysicalComponent]
 *   > Set 'Physical System' to 'UNSET' [kind], 'UNSET' [nature]
 *   - Create '[Part]: [Physical Component]' [Part]
 *     > Link '[Part]: [Physical Component]' to '[Physical Component]' [abstractType]
 *     - Create '[Part Deployment Link] to [Part]' [PartDeploymentLink]
 *   - Create '[Part]: [Physical Component]' [Part]
 *     > Link '[Part]: [Physical Component]' to '[Physical Component]' [abstractType]
 *   - Create '[Physical Component]' [PhysicalComponent]
 *     > Set '[Physical Component]' to 'UNSET' [kind], 'NODE' [nature]
 *   - Create '[Physical Component]' [PhysicalComponent]
 *     > Set '[Physical Component]' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create '[Component Functional Allocation] to [PhysicalFunction]' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to [PhysicalFunction]' to '[Physical Component]' [sourceElement], '[Physical Function]' [targetElement]
 *   - Create '[Logical Component Realization] to Logical System' [LogicalComponentRealization]
 *     > Link '[Logical Component Realization] to Logical System' to 'Physical System' [sourceElement], 'Logical System' [targetElement]
 */
//@formatter:on

public class NullAttributeTest extends System2SubsystemTest implements Interphase, Crossphase {

  private String _id_pf = "2a7ba9a6-0db8-4db8-9f7e-a7fa42da1654"; //$NON-NLS-1$
  private String _id_pcn = "a30615e1-72e2-40c9-8d8a-d7fc4ef24cf3"; //$NON-NLS-1$
  private String _id_pcb = "9e97adcc-4062-4132-b505-73136a0a2af5"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_pcn);
  }

  @Override
  protected void verify() {

    EObject source = getObject(_id_pf);
    EObject target = mustBeTransitioned(_id_pf);
    testAttributeIdentity(source, target, ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME);
    testAttributeIdentity(source, target, CapellacorePackage.Literals.CAPELLA_ELEMENT__DESCRIPTION);
    testAttributeIdentity(source, target, CapellacorePackage.Literals.CAPELLA_ELEMENT__SUMMARY);

    source = getObject(_id_pcn);
    target = mustBeTransitioned(_id_pcn);
    testAttributeIdentity(source, target, ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME);
    testAttributeIdentity(source, target, CapellacorePackage.Literals.CAPELLA_ELEMENT__DESCRIPTION);
    testAttributeIdentity(source, target, CapellacorePackage.Literals.CAPELLA_ELEMENT__SUMMARY);

  }

}
