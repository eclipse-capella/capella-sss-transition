/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.tests.TraceabilityArchitectureSID;

public abstract class ManagementPropertiesTest extends MultiPhasesTest {
  public static final String PC_1 = "a3abe83b-203f-44e5-83ef-733d11ae1c3a"; //$NON-NLS-1$

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("testManagementProps", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(PC_1);
  }

  private void checkTargetNullValues(boolean checkReview, boolean checkStatus, boolean isVisibleDoc,
      boolean isVisibleLM) {
    CapellaElement target = (CapellaElement) retrieveTargetElement(PC_1);

    if (checkReview)
      assertNull(target.getReview());

    if (checkStatus)
      assertNull(target.getStatus());

    assertEquals(target.isVisibleInDoc(), isVisibleDoc);
    assertEquals(target.isVisibleInLM(), isVisibleLM);
  }

  private void matchAttributeValues(boolean checkReview, boolean checkStatus, boolean checkVisibleDoc,
      boolean checkVisibleLM) {
    EObject source = retrieveReferenceElement(PC_1);
    EObject target = retrieveTargetElement(PC_1);

    if (checkStatus)
      assertEquals(((CapellaElement) source).getStatus().getName(), ((CapellaElement) target).getStatus().getName());

    if (checkReview)
      testAttributeIdentity(source, target, CapellacorePackage.Literals.CAPELLA_ELEMENT__REVIEW);

    if (checkVisibleDoc)
      testAttributeIdentity(source, target, ModellingcorePackage.Literals.PUBLISHABLE_ELEMENT__VISIBLE_IN_DOC);

    if (checkVisibleLM)
      testAttributeIdentity(source, target, ModellingcorePackage.Literals.PUBLISHABLE_ELEMENT__VISIBLE_IN_LM);
  }

  // by default the management properties are not translated, as preference values are set to false
  // management properties on target should be null
  public static class TransitionDefaultFalsePreferences extends ManagementPropertiesTest {
    @Override
    protected void verify() throws Exception {
      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.SA);
      super.checkTargetNullValues(true, true, true, true);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.LA);
      super.checkTargetNullValues(true, true, true, true);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);
      super.checkTargetNullValues(true, true, true, true);
    }
  }

  // set preferences -> status
  // if not set isVisibleInDoc = true, isVisibleInLM = true
  public static class TransitionStatus extends ManagementPropertiesTest {

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.MANAGEMENT_PROGRESS_STATUS_EXPORT, Boolean.TRUE);
    }

    @Override
    protected void verify() throws Exception {
      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.SA);
      super.matchAttributeValues(false, true, false, false);
      super.checkTargetNullValues(true, false, true, true);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.LA);
      super.matchAttributeValues(false, true, false, false);
      super.checkTargetNullValues(true, false, true, true);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);
      super.matchAttributeValues(false, true, false, false);
      super.checkTargetNullValues(true, false, true, true);
    }

  }

  // set preferences -> review
  // if not set isVisibleInDoc = true, isVisibleInLM = true
  public static class TransitionReview extends ManagementPropertiesTest {

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.MANAGEMENT_REVIEW_EXPORT, Boolean.TRUE);
    }

    @Override
    protected void verify() throws Exception {
      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.SA);
      super.matchAttributeValues(true, false, false, false);
      super.checkTargetNullValues(false, true, true, true);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.LA);
      super.matchAttributeValues(true, false, false, false);
      super.checkTargetNullValues(false, true, true, true);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);
      super.matchAttributeValues(true, false, false, false);
      super.checkTargetNullValues(false, true, true, true);
    }

  }

  // set preferences -> isVisibleInDoc
  // if not set isVisibleInDoc = true, isVisibleInLM = true
  public static class TransitionVisibleInDoc extends ManagementPropertiesTest {

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.MANAGEMENT_VISIBLE_IN_DOCUMENT_EXPORT, Boolean.TRUE);
    }

    @Override
    protected void verify() throws Exception {
      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.SA);
      super.matchAttributeValues(false, false, true, false);
      super.checkTargetNullValues(false, false, false, true);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.LA);
      super.matchAttributeValues(false, false, true, false);
      super.checkTargetNullValues(false, false, false, true);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);
      super.matchAttributeValues(false, false, true, false);
      super.checkTargetNullValues(false, false, false, true);
    }

  }

  // set preferences -> isVisibleInLM
  // if not set isVisibleInDoc = true, isVisibleInLM = true
  public static class TransitionVisibleInLM extends ManagementPropertiesTest {

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.MANAGEMENT_VISIBLE_FOR_TRACEABILITY_EXPORT, Boolean.TRUE);
    }

    @Override
    protected void verify() throws Exception {
      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.SA);
      super.matchAttributeValues(false, false, false, true);
      super.checkTargetNullValues(false, false, true, false);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.LA);
      super.matchAttributeValues(false, false, false, true);
      super.checkTargetNullValues(false, false, true, false);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);
      super.matchAttributeValues(false, false, false, true);
      super.checkTargetNullValues(false, false, true, false);
    }

  }

  // set preferences and transition all management values
  public static class TransitionAll extends ManagementPropertiesTest {

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.MANAGEMENT_PROGRESS_STATUS_EXPORT, Boolean.TRUE);
      addSharedParameter(IOptionsConstants.MANAGEMENT_REVIEW_EXPORT, Boolean.TRUE);
      addSharedParameter(IOptionsConstants.MANAGEMENT_VISIBLE_FOR_TRACEABILITY_EXPORT, Boolean.TRUE);
      addSharedParameter(IOptionsConstants.MANAGEMENT_VISIBLE_IN_DOCUMENT_EXPORT, Boolean.TRUE);
    }

    @Override
    protected void verify() throws Exception {
      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.SA);
      super.matchAttributeValues(true, true, true, true);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.LA);
      super.matchAttributeValues(true, true, true, true);

      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);
      super.matchAttributeValues(true, true, true, true);
    }
  }

}
