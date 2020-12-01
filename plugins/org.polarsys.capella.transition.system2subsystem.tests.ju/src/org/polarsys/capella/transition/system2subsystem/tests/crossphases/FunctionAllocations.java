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
import org.polarsys.capella.core.data.capellacore.Allocation;

/**
 * Function Allocations: Test that allocation of sub components are merged into super components
 */
public class FunctionAllocations extends CrossPhasesTest {

  private String _id_pc1 = "86c71cd6-1967-4546-9200-92255b47d2b7"; //$NON-NLS-1$
  private String _id_cfa_to_pf11 = "0aaec903-3dda-4c0c-9ef4-39a19a96240a"; //$NON-NLS-1$
  private String _id_pc2 = "550f9f02-a885-416c-a607-3cd3ab09764f"; //$NON-NLS-1$
  private String _id_pc3 = "7ad12332-fa79-4213-a935-0afad86a1cc6"; //$NON-NLS-1$
  private String _id_cfa_to_pf13 = "952c073b-cf97-4def-909d-b2c7629d5028"; //$NON-NLS-1$
  private String _id_cfa_to_pf14 = "ed321669-d5e9-466a-82cc-f7b9f97f5dce"; //$NON-NLS-1$
  private String _id_pc4 = "e855237b-52bb-4c6a-a5f8-927bd8620481"; //$NON-NLS-1$
  private String _id_cfa_to_pf16 = "da49af92-51b6-4f57-a537-adb8dc810e39"; //$NON-NLS-1$
  private String _id_pc5 = "55380266-8a34-47c8-8cac-0df8afb795f2"; //$NON-NLS-1$
  private String _id_cfa_to_pf12 = "37016ee1-a6c2-4482-8c3f-05317c7ace10"; //$NON-NLS-1$
  private String _id_pc6 = "8c9ad0dc-eeda-4593-b176-5f90631fa919"; //$NON-NLS-1$
  private String _id_cfa_to_pf15 = "fee37bf2-421b-453a-b743-9bb4031fd54c"; //$NON-NLS-1$
  private String _id_cfa_to_pf17 = "4026c5bf-3a93-4b4b-9e53-51bb56f75560"; //$NON-NLS-1$
  private String _id_pf11 = "dabbfb16-6b0b-4a91-83e0-c350baca0c17"; //$NON-NLS-1$
  private String _id_pf12 = "8fe0695c-a12c-4cd3-87f9-59df9a0c9263"; //$NON-NLS-1$
  private String _id_pf13 = "d41a316c-9d9c-4723-beba-e7c66f342dd6"; //$NON-NLS-1$
  private String _id_pf14 = "abac9c11-4045-4777-a79d-b13eefac7598"; //$NON-NLS-1$
  private String _id_pf15 = "2a96b36f-d75b-4077-917a-a1e913e52bb1"; //$NON-NLS-1$
  private String _id_pf16 = "418ca470-2629-46c0-99cf-5496b250d9a6"; //$NON-NLS-1$
  private String _id_pf17 = "21acb1cf-9933-4d21-91b9-1fbea1a78699"; //$NON-NLS-1$
  private String _id_fe12 = "d674e67e-0f93-416c-a8d6-11a71cec9796"; //$NON-NLS-1$
  private String _id_fe13 = "bdd592f0-5e11-4277-acc2-a5de712ec77c"; //$NON-NLS-1$
  private String _id_fe14 = "fbc9cadb-33e0-4a3a-af17-e73074264877"; //$NON-NLS-1$
  private String _id_fe15 = "d220d252-8b52-4114-8383-21c4651907ef"; //$NON-NLS-1$
  private String _id_fe16 = "faa7f320-65cd-4970-8d46-534b2d4b6834"; //$NON-NLS-1$
  private String _id_fe17 = "f263c22c-b75d-45c7-b6a8-7380996e64fc"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_pc2);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(_id_pf12);
    mustBeTransitioned(_id_pf13);
    mustBeTransitioned(_id_pf14);
    mustBeTransitioned(_id_pf15);
    mustBeTransitioned(_id_pf16);
    shouldNotBeTransitioned(_id_pf11);
    shouldNotBeTransitioned(_id_pf17);

    mustBeTransitioned(_id_cfa_to_pf13);
    mustBeTransitioned(_id_cfa_to_pf14);
    mustBeTransitioned(_id_cfa_to_pf16);
    mustBeTransitioned(_id_cfa_to_pf12);
    mustBeTransitioned(_id_cfa_to_pf15);
    shouldNotBeTransitioned(_id_cfa_to_pf11);
    shouldNotBeTransitioned(_id_cfa_to_pf17);

    mustBeAllocatedBy(_id_cfa_to_pf13, _id_pc2);
    mustBeAllocatedBy(_id_cfa_to_pf14, _id_pc2);
    mustBeAllocatedBy(_id_cfa_to_pf16, _id_pc2);
    mustBeAllocatedBy(_id_cfa_to_pf12, _id_pc1);
    mustBeAllocatedBy(_id_cfa_to_pf15, _id_pc1);

    shouldNotBeTransitioned(_id_fe16);
    shouldNotBeTransitioned(_id_fe17);
    mustBeTransitioned(_id_fe12);
    mustBeTransitioned(_id_fe13);
    mustBeTransitioned(_id_fe14);
    mustBeTransitioned(_id_fe15);
  }

  public EObject mustBeAllocatedBy(String id, String idComponent) {
    EObject source = traceability.getTracedObject(id);
    EObject allocated = traceability.getTracedObject(idComponent);

    assertTrue(source != null);
    assertTrue(allocated != null);
    assertTrue(((Allocation) source).getSourceElement() == allocated);
    return source;
  }
}