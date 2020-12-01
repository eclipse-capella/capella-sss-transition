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
package org.polarsys.capella.transition.system2subsystem.tests.crossphases;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;

//@formatter:off
/**
 * CI to System: Test if System have same name than CI
 * identifier:name:'[CSCI] ciName',id=#49aabac9-01b1-4b6e-8d97-22a458c5583d
 * - Create '[CSCI] ciName' [ConfigurationItem]
 *   - Create '[Physical Artifact Realization] to PC11' [PhysicalArtifactRealization]
 *     > Link '[Physical Artifact Realization] to PC11' to '[CSCI] ciName' [sourceElement], 'PC11' [targetElement]
 *   - Create '[Physical Artifact Realization] to PC12' [PhysicalArtifactRealization]
 *     > Link '[Physical Artifact Realization] to PC12' to '[CSCI] ciName' [sourceElement], 'PC12' [targetElement]
 */
//@formatter:on
public class CItoSystemName extends CrossPhasesTest {

  private String _id_csci_ciname = "49aabac9-01b1-4b6e-8d97-22a458c5583d"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_csci_ciname);
  }

  @Override
  protected void verify() {
    EObject source = getObject(_id_csci_ciname);
    EObject target = mustBeTransitioned(_id_csci_ciname);
    testAttributeIdentity(source, target, ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME);
    testAttributeIdentity(source, target, CapellacorePackage.Literals.CAPELLA_ELEMENT__DESCRIPTION);
    testAttributeIdentity(source, target, CapellacorePackage.Literals.CAPELLA_ELEMENT__SUMMARY);
  }

}
