/*******************************************************************************
 * Copyright (c) 2020 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.tests.multiphases;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentPkg;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.transition.system2subsystem.tests.TraceabilityArchitectureSID;

/**
 * 
 * Test that the transition of sub PC into PA level in a multiphase transition does not create two Physical Systems
 */
public class SubPhyscialComponent extends MultiPhasesTest {

  public static String MULTIPHASES_COMPONENTS_PC_1_PC_11 = "1bfd2244-ab74-4116-b5a1-20623d973634"; //$NON-NLS-1$

  public SubPhyscialComponent() {
    super();
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(MULTIPHASES_COMPONENTS_PC_1_PC_11);
  }

  @Override
  protected void verify() {
    ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);
    EObject transitionedPC11 = mustBeTransitioned(MULTIPHASES_COMPONENTS_PC_1_PC_11);
    BlockArchitecture rootBlockArchitecture = BlockArchitectureExt.getRootBlockArchitecture(transitionedPC11);
    assertTrue(transitionedPC11 instanceof PhysicalComponent
        && ((PhysicalComponentPkg) BlockArchitectureExt.getComponentPkg(rootBlockArchitecture))
            .getOwnedPhysicalComponents().size() == 1
        && ((PhysicalComponent) rootBlockArchitecture.getSystem()).getOwnedPhysicalComponents()
            .get(0) == transitionedPC11);
  }
}
