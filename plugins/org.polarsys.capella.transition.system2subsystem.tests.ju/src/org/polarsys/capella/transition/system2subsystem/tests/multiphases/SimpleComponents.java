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
package org.polarsys.capella.transition.system2subsystem.tests.multiphases;

import java.util.Collection;

import org.polarsys.capella.core.data.ctx.CtxPackage;
import org.polarsys.capella.core.data.la.LaPackage;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.transition.system2subsystem.tests.TraceabilityArchitectureSID;

/**
 * Component Exchanges - Ports: Test that sub components exchanges are not transitioned, and ports related to them
 */
public class SimpleComponents extends MultiPhasesTest {

  public static String MP_SIMPLECOMPONENTS__PA__PC1 = "cdba4509-17f6-4cb2-a27f-9d7d0abfa83c"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC1__PC11 = "85c8634c-4399-4ad4-8afd-cd7099657abc"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC1__PC12 = "74ac2648-fc54-4fd1-9976-5d14472ac372"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC2 = "c8296995-0b3d-4270-84be-b101e2bdb08c"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC6 = "5238b6bf-d7fc-438f-8ad8-793d1d3c8956"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC3 = "a803603f-8d3c-4af9-bc94-489e2af303a7"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC4 = "6cfc7874-38e9-46d2-bf11-d831a1ad980d"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC5 = "d068b9e9-140a-4477-9265-9313649f1104"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC7 = "8e452243-6926-4aa9-a527-3a5b96a1cad3"; //$NON-NLS-1$

  public static String MP_SIMPLECOMPONENTS__PA__PART_PC1__PC1 = "e6f64c44-a779-47f3-bf7e-34b23f636278"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PART_PC2__PC2 = "a5c824e0-191b-415c-82f9-74b8aa0b1678"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC1__PART_PC11__PC11 = "4c20aa25-abaf-40da-87f4-38ffe1376840"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC1__PART_PC12__PC12 = "aecc1926-974e-41e8-ae86-2dc22b0f5ef3"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC1__PC11__PART_PC111__PC111 = "53483f21-870d-4d45-8310-a7e6163fb209"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PC1__PC11__PART_PC112__PC112 = "4a3ea7a3-daa8-466b-a7b3-f1d0a8051d6d"; //$NON-NLS-1$
  public static String MP_SIMPLECOMPONENTS__PA__PART_PC3__PC3 = "06eb081b-e0de-45d6-9bee-81e2602347f0"; //$NON-NLS-1$

  public SimpleComponents() {
    super();
  }

  public SimpleComponents(boolean withLibrary) {
    super(withLibrary);
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(MP_SIMPLECOMPONENTS__PA__PC1__PC11);
  }

  @Override
  protected void verify() {

    // System Architecture
    ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.SA);

    // mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC11, CtxPackage.Literals.SYSTEM); //no sid?
    // mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PC1__PART_PC11__PC11, CtxPackage.Literals.SYSTEM_CONTEXT); //no
    // sid?
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1, CtxPackage.Literals.ACTOR);
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC2, CtxPackage.Literals.ACTOR);
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC12, CtxPackage.Literals.ACTOR);

    mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PART_PC1__PC1, CtxPackage.Literals.SYSTEM_CONTEXT);
    mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PART_PC2__PC2, CtxPackage.Literals.SYSTEM_CONTEXT);
    mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PC1__PART_PC12__PC12, CtxPackage.Literals.SYSTEM_CONTEXT);

    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC6);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC3);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PART_PC3__PC3);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC4);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC5);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC7);

    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC11__PART_PC111__PC111);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC11__PART_PC112__PC112);

    // Logical Architecture

    ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.LA);

    // mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC11, CtxPackage.Literals.SYSTEM); //no sid?
    // mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PC1__PART_PC11__PC11, CtxPackage.Literals.SYSTEM_CONTEXT); //no
    // sid?
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC12, LaPackage.Literals.LOGICAL_ACTOR);
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1, LaPackage.Literals.LOGICAL_ACTOR);
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC2, LaPackage.Literals.LOGICAL_ACTOR);

    mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PART_PC1__PC1, LaPackage.Literals.LOGICAL_CONTEXT);
    mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PART_PC2__PC2, LaPackage.Literals.LOGICAL_CONTEXT);
    mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PC1__PART_PC12__PC12, LaPackage.Literals.LOGICAL_CONTEXT);

    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC6);
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC3, LaPackage.Literals.LOGICAL_COMPONENT);
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PART_PC3__PC3);

    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC4);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC5);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC7);

    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC11__PART_PC111__PC111);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC11__PART_PC112__PC112);

    // Physical Architecture

    ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);

    // mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC11, CtxPackage.Literals.SYSTEM); //no sid?
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC12, PaPackage.Literals.PHYSICAL_ACTOR);
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1, PaPackage.Literals.PHYSICAL_ACTOR);
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC2, PaPackage.Literals.PHYSICAL_ACTOR);
    // mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PC1__PART_PC11__PC11, CtxPackage.Literals.SYSTEM_CONTEXT); //no
    // sid?
    mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PC1__PART_PC12__PC12, PaPackage.Literals.PHYSICAL_CONTEXT);
    mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PART_PC1__PC1, PaPackage.Literals.PHYSICAL_CONTEXT);
    mustBeTransitionedInto(MP_SIMPLECOMPONENTS__PA__PART_PC2__PC2, PaPackage.Literals.PHYSICAL_CONTEXT);

    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC6);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC3);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC4);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC5);
    shouldNotBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC7);

    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC11__PART_PC111__PC111);
    mustBeTransitioned(MP_SIMPLECOMPONENTS__PA__PC1__PC11__PART_PC112__PC112);
  }
}
