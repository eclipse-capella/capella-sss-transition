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
package org.polarsys.capella.transition.system2subsystem.tests.multiphases;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.transition.system2subsystem.tests.TraceabilityArchitectureSID;

/**
 * Test that Interfaces are transitioned to SA and implemented/used or required/provided at right levels (LA/PA)
 */
public class Interfaces extends MultiPhasesTest {

  public static String MP_INTERFACES__PA__PC_1 = "10262e38-98d5-46cc-9881-8bfd7f2b7234"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__PC_2 = "71365c40-4d44-4449-a8b3-bb40b2c7f6a7"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__PC_3 = "8a765c34-93de-4710-8c8d-a55ac71cded0"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__PC_4 = "d9d9d865-a7f1-4ba7-ba67-2b35ebb017f3"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__PC_5 = "ffba5234-1b79-439d-9196-ff846408b226"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__PC_6 = "137ec266-bacf-4d11-aced-fb45e2e1bb60"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__PC_7 = "e3776ba7-1625-4537-a09e-ca5be28396bd"; //$NON-NLS-1$

  public static String MP_INTERFACES__PA__PHYSICAL_ACTORS__PA_1 = "8e9d2170-2caf-486c-8f70-0f08fea040b4"; //$NON-NLS-1$

  public static String MP_INTERFACES__PA__PC_4__CP_2 = "e2a3daad-47a1-4c88-b94b-23f9b0bc0ec6"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__PC_5__CP_1 = "ab5a8dbc-11f7-48ef-9678-1d03223b7524"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__PC_4__CP_1 = "36cdc6c7-82c1-4d96-8c4c-3af72654b2d4"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__PHYSICAL_ACTORS__PA_1__CP_1 = "e4b29c3b-0728-4aa0-ab32-68706ed8a2c0"; //$NON-NLS-1$

  public static String MP_INTERFACES__PA__INTERFACES__INTERFACE_1 = "f727c909-7d05-416e-bfab-c4485b5099b2"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__INTERFACES__INTERFACE_2 = "50c600a2-dbad-4d7d-ae85-e6e838e6e877"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__INTERFACES__INTERFACE_3 = "f12c0427-0a77-48cb-ac9e-47fd7fb8de46"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__INTERFACES__INTERFACE_4 = "8812fcee-950f-4e51-b5f7-e58d1a141da5"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__INTERFACES__INTERFACE_5 = "627712c6-c7bb-4562-a4b9-4c70bfd136c6"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__INTERFACES__INTERFACE_6 = "caf03b6d-35f8-4ca6-92ad-27958056129c"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__INTERFACES__INTERFACE_7 = "71672f91-529b-4ed7-9a31-5c3b4ed17229"; //$NON-NLS-1$
  public static String MP_INTERFACES__PA__INTERFACES__INTERFACE_8 = "f6d77a1b-9d6a-4091-8730-16b03e41aca9"; //$NON-NLS-1$

  public Interfaces() {
    super();
  }

  public Interfaces(boolean withLibrary) {
    super(withLibrary);
  }

  @Override
  protected Collection<Object> getProjectionElements() {
    Collection<Object> selectedElements = new ArrayList<Object>();
    selectedElements.add(getObject(MP_INTERFACES__PA__PC_1));
    selectedElements.add(getObject(MP_INTERFACES__PA__PC_2));
    return selectedElements;
  }

  /*
   * protected EObject retrieveTargetElement(String id, Type architectureType_p) { BlockArchitectureExt.Type
   * currentArchitecture = getTraceability().getArchitecture();
   * 
   * try { getTraceability().setArchitecture(architectureType_p); EObject source =
   * getTraceability().getTracedObject(id); assertTrue(source != null); return source; } finally {
   * getTraceability().setArchitecture(currentArchitecture); } }
   */
  @Override
  protected void verify() {
    // --------------------
    // System Architecture
    // --------------------
    ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.SA);

    // mustBeTransitioned(MP_INTERFACES__PA__PC_1, CtxPackage.Literals.SYSTEM); //no sid for the system object?
    // mustBeTransitionedInto(MP_INTERFACES__PA__PC_1, CtxPackage.Literals.SYSTEM_CONTEXT); //no sid?
    // mustBeTransitioned(MP_INTERFACES__PA__PC_2, CtxPackage.Literals.SYSTEM); //no sid for the system object?
    // mustBeTransitionedInto(MP_INTERFACES__PA__PC_2, CtxPackage.Literals.SYSTEM_CONTEXT); //no sid?
    // mustBeTransitioned(MP_INTERFACES__PA__PC_4, CtxPackage.Literals.SYSTEM); //no sid for the system object?
    // mustBeTransitionedInto(MP_INTERFACES__PA__PC_4, CtxPackage.Literals.SYSTEM_CONTEXT); //no sid?
    // mustBeTransitioned(MP_INTERFACES__PA__PC_5, CtxPackage.Literals.SYSTEM); //no sid for the system object?
    // mustBeTransitionedInto(MP_INTERFACES__PA__PC_5, CtxPackage.Literals.SYSTEM_CONTEXT); //no sid?
    // mustBeTransitioned(MP_INTERFACES__PA__PC_6, CtxPackage.Literals.SYSTEM); //no sid for the system object?
    // mustBeTransitionedInto(MP_INTERFACES__PA__PC_6, CtxPackage.Literals.SYSTEM_CONTEXT); //no sid?
    // mustBeTransitioned(MP_INTERFACES__PA__PC_7, CtxPackage.Literals.SYSTEM); //no sid for the system object?
    // mustBeTransitionedInto(MP_INTERFACES__PA__PC_7, CtxPackage.Literals.SYSTEM_CONTEXT); //no sid?

    mustBeTransitioned(MP_INTERFACES__PA__PC_3, ComponentType.ACTOR);
    mustBeTransitioned(MP_INTERFACES__PA__PHYSICAL_ACTORS__PA_1, ComponentType.ACTOR);

    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_1);

    mustBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_2);
    mustBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_3);
    mustBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_4);
    mustBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_5);
    mustBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_6);
    mustBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_7);
    mustBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_8);

    // Retrieve target elements to test Interfaces usage
    Component targetSystem = retrieveTargetSystem();
    EObject targetPC5inLA = retrieveTargetElement(MP_INTERFACES__PA__PC_5, BlockArchitectureExt.Type.LA);
    EObject targetPC4inLA = retrieveTargetElement(MP_INTERFACES__PA__PC_4, BlockArchitectureExt.Type.LA);
    EObject targetPC3inSA = retrieveTargetElement(MP_INTERFACES__PA__PC_3, BlockArchitectureExt.Type.SA);
    EObject targetPC3inLA = retrieveTargetElement(MP_INTERFACES__PA__PC_3, BlockArchitectureExt.Type.LA);
    EObject targetPC3inPA = retrieveTargetElement(MP_INTERFACES__PA__PC_3, BlockArchitectureExt.Type.PA);
    EObject targetPC7inLA = retrieveTargetElement(MP_INTERFACES__PA__PC_7, BlockArchitectureExt.Type.LA);
    LogicalComponent targetLogicalSystem = retrieveTargetLogicalSystem();
    EObject targetPC1inPA = retrieveTargetElement(MP_INTERFACES__PA__PC_1, BlockArchitectureExt.Type.PA);
    EObject targetPC2inPA = retrieveTargetElement(MP_INTERFACES__PA__PC_2, BlockArchitectureExt.Type.PA);
    EObject targetPA1inSA = retrieveTargetElement(MP_INTERFACES__PA__PHYSICAL_ACTORS__PA_1,
        BlockArchitectureExt.Type.SA);
    EObject targetPA1inLA = retrieveTargetElement(MP_INTERFACES__PA__PHYSICAL_ACTORS__PA_1,
        BlockArchitectureExt.Type.LA);
    EObject targetPA1inPA = retrieveTargetElement(MP_INTERFACES__PA__PHYSICAL_ACTORS__PA_1,
        BlockArchitectureExt.Type.PA);
    EObject targetPC4_CP2inLA = retrieveTargetElement(MP_INTERFACES__PA__PC_4__CP_2, BlockArchitectureExt.Type.LA);
    EObject targetPC4_CP1inSA = retrieveTargetElement(MP_INTERFACES__PA__PC_4__CP_1, BlockArchitectureExt.Type.SA);
    EObject targetPC4_CP1inLA = retrieveTargetElement(MP_INTERFACES__PA__PC_4__CP_1, BlockArchitectureExt.Type.LA);
    EObject targetPC5_CP1inLA = retrieveTargetElement(MP_INTERFACES__PA__PC_5__CP_1, BlockArchitectureExt.Type.LA);
    EObject targetPA1_CP1inSA = retrieveTargetElement(MP_INTERFACES__PA__PHYSICAL_ACTORS__PA_1__CP_1,
        BlockArchitectureExt.Type.SA);
    EObject targetPA1_CP1inLA = retrieveTargetElement(MP_INTERFACES__PA__PHYSICAL_ACTORS__PA_1__CP_1,
        BlockArchitectureExt.Type.LA);
    EObject targetPA1_CP1inPA = retrieveTargetElement(MP_INTERFACES__PA__PHYSICAL_ACTORS__PA_1__CP_1,
        BlockArchitectureExt.Type.PA);

    // Test interfaces usage
    Collection<EObject> candidatesList = new ArrayList<EObject>();
    candidatesList.add(targetSystem);
    candidatesList.add(targetPC5inLA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_2, candidatesList,
        CsPackage.Literals.INTERFACE__USER_COMPONENTS);

    candidatesList.clear();
    candidatesList.add(targetSystem);
    candidatesList.add(targetPC4inLA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_3, candidatesList,
        CsPackage.Literals.INTERFACE__IMPLEMENTOR_COMPONENTS);

    candidatesList.clear();
    candidatesList.add(targetPC3inSA);
    candidatesList.add(targetPC3inLA);
    candidatesList.add(targetPC3inPA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_4, candidatesList,
        CsPackage.Literals.INTERFACE__IMPLEMENTOR_COMPONENTS);

    candidatesList.clear();
    candidatesList.add(targetSystem);
    candidatesList.add(targetPC7inLA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_4, candidatesList,
        CsPackage.Literals.INTERFACE__USER_COMPONENTS);

    candidatesList.clear();
    candidatesList.add(targetSystem);
    candidatesList.add(targetLogicalSystem);
    candidatesList.add(targetPC1inPA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_5, candidatesList,
        CsPackage.Literals.INTERFACE__IMPLEMENTOR_COMPONENTS);

    candidatesList.clear();
    candidatesList.add(targetSystem);
    candidatesList.add(targetLogicalSystem);
    candidatesList.add(targetPC2inPA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_5, candidatesList,
        CsPackage.Literals.INTERFACE__USER_COMPONENTS);

    candidatesList.clear();
    candidatesList.add(targetPC4_CP2inLA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_6, candidatesList,
        CsPackage.Literals.INTERFACE__PROVIDING_COMPONENT_PORTS);

    candidatesList.clear();
    candidatesList.add(targetPC5_CP1inLA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_6, candidatesList,
        CsPackage.Literals.INTERFACE__REQUIRING_COMPONENT_PORTS);

    candidatesList.clear();
    candidatesList.add(targetPC4_CP1inSA);
    candidatesList.add(targetPC4_CP1inLA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_7, candidatesList,
        CsPackage.Literals.INTERFACE__PROVIDING_COMPONENT_PORTS);

    candidatesList.clear();
    candidatesList.add(targetPA1_CP1inSA);
    candidatesList.add(targetPA1_CP1inLA);
    candidatesList.add(targetPA1_CP1inPA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_7, candidatesList,
        CsPackage.Literals.INTERFACE__REQUIRING_COMPONENT_PORTS);

    candidatesList.clear();
    candidatesList.add(targetSystem);
    candidatesList.add(targetLogicalSystem);
    candidatesList.add(targetPC1inPA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_8, candidatesList,
        CsPackage.Literals.INTERFACE__IMPLEMENTOR_COMPONENTS);

    candidatesList.clear();
    candidatesList.add(targetPA1inSA);
    candidatesList.add(targetPA1inLA);
    candidatesList.add(targetPA1inPA);
    mustBeTransitionedAndOnlyLinkedTo(MP_INTERFACES__PA__INTERFACES__INTERFACE_8, candidatesList,
        CsPackage.Literals.INTERFACE__USER_COMPONENTS);

    // Logical Architecture

    ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.LA);
    // mustBeTransitioned(MP_INTERFACES__PA__PC_1, LaPackage.Literals.SYSTEM); //no sid for the system object?
    // mustBeTransitionedInto(MP_INTERFACES__PA__PC_1, CtxPackage.Literals.SYSTEM_CONTEXT); //no sid?
    // mustBeTransitioned(MP_INTERFACES__PA__PC_2, CtxPackage.Literals.SYSTEM); //no sid for the system object?
    // mustBeTransitionedInto(MP_INTERFACES__PA__PC_2, CtxPackage.Literals.SYSTEM_CONTEXT); //no sid?

    mustBeTransitioned(MP_INTERFACES__PA__PC_4, ComponentType.LOGICAL_COMPONENT);
    mustBeTransitioned(MP_INTERFACES__PA__PC_5, ComponentType.LOGICAL_COMPONENT);
    shouldNotBeTransitioned(MP_INTERFACES__PA__PC_6);
    mustBeTransitioned(MP_INTERFACES__PA__PC_7, ComponentType.LOGICAL_COMPONENT);

    mustBeTransitioned(MP_INTERFACES__PA__PC_3, ComponentType.LOGICAL_ACTOR);
    mustBeTransitioned(MP_INTERFACES__PA__PHYSICAL_ACTORS__PA_1, ComponentType.LOGICAL_ACTOR);

    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_1);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_2);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_3);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_4);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_5);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_6);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_7);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_8);

    // Physical Architecture

    ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);

    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_1);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_2);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_3);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_4);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_5);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_6);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_7);
    shouldNotBeTransitioned(MP_INTERFACES__PA__INTERFACES__INTERFACE_8);

  }
}
