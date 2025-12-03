/*******************************************************************************
 * Copyright (c) 2006, 2025 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.crossphases.launcher;

import org.polarsys.capella.core.transition.common.activities.DifferencesComputingActivity;
import org.polarsys.capella.core.transition.common.activities.DifferencesMergingActivity;
import org.polarsys.capella.core.transition.common.activities.FinalizeTransitionActivity;
import org.polarsys.capella.core.transition.common.activities.InitializeDiffMergeFromTransformationActivity;
import org.polarsys.capella.core.transition.common.activities.InitializeTransformationActivity;
import org.polarsys.capella.core.transition.common.activities.PostTransformationActivity;
import org.polarsys.capella.transition.system2subsystem.activities.CopyImagesActivity;
import org.polarsys.capella.transition.system2subsystem.activities.CopyManagementPropertiesActivity;
import org.polarsys.capella.transition.system2subsystem.activities.RootComponentNameUpdater;
import org.polarsys.capella.transition.system2subsystem.activities.SsbsFinalizeTransitionActivity;
import org.polarsys.capella.transition.system2subsystem.activities.UnloadSubsystemResourcesActivity;
import org.polarsys.capella.transition.system2subsystem.crossphases.activities.CleanDeprecatedComponentsActivity;
import org.polarsys.capella.transition.system2subsystem.crossphases.activities.ConfirmableDifferencesMergingActivity;
import org.polarsys.capella.transition.system2subsystem.crossphases.activities.InitializeCrossPhasesTransformationActivity;
import org.polarsys.capella.transition.system2subsystem.crossphases.activities.InitializeDiffMergeActivity;
import org.polarsys.capella.transition.system2subsystem.crossphases.activities.InitializeTransitionActivity;
import org.polarsys.capella.transition.system2subsystem.launcher.SubSystemLauncher;
import org.polarsys.kitalpha.cadence.core.api.parameter.WorkflowActivityParameter;

/**
 * Launcher for CrossPhases transformation.
 */
public class HeadlessCrossPhasesLauncher extends SubSystemLauncher {

  private final String MAPPING = "org.polarsys.capella.transition.system2subsystem.crossphases"; //$NON-NLS-1$

  @Override
  protected String getMapping() {
    return MAPPING;
  }
  
  @Override
  protected void initOverrides() {
    super.initOverrides();

    // override buildInitializationActivities
    addOverrides(org.polarsys.capella.core.transition.common.activities.InitializeTransitionActivity.ID, InitializeTransitionActivity.ID);
    addOverrides(InitializeTransformationActivity.ID, InitializeCrossPhasesTransformationActivity.ID);

    // override buildDiffMergeActivities
    addOverrides(InitializeDiffMergeFromTransformationActivity.ID, InitializeDiffMergeActivity.ID);
    addOverrides(DifferencesMergingActivity.ID, ConfirmableDifferencesMergingActivity.ID);
    
    addOverrides(FinalizeTransitionActivity.ID, SsbsFinalizeTransitionActivity.ID);
  }

  /**
   * Defines Activities to be loaded in the workflow element of cadence "Diff Merge".
   * 
   * @return the associated workflow element
   */
  @Override
  protected WorkflowActivityParameter buildDiffMergeActivities() {
    WorkflowActivityParameter parameter = new WorkflowActivityParameter();

    if (getTransposer() != null) {

      // RootComponentNameUpdater (Sys/SubSys specific)
      parameter.addActivity(getActivity(RootComponentNameUpdater.ID));

      // CopyManagementPropertiesActivity (Sys/SubSys specific)
      parameter.addActivity(getActivity(CopyManagementPropertiesActivity.ID));

      // PostTransformationActivity
      parameter.addActivity(getActivity(PostTransformationActivity.ID));

      // InitializeDiffMergeActivity (Sys/SubSys override)
      parameter.addActivity(getActivity(InitializeDiffMergeFromTransformationActivity.ID));

      // DifferencesComputingActivity
      parameter.addActivity(getActivity(DifferencesComputingActivity.ID));
      
      // Remove deprecated components from target (components merged into the System)
      // (Sys/SubSys specific)
      parameter.addActivity(CleanDeprecatedComponentsActivity.ID);

      // DifferencesMergingActivity
      parameter.addActivity(getActivity(DifferencesMergingActivity.ID));

      // Copy images to the target project (Sys/SubSys specific)
      parameter.addActivity(CopyImagesActivity.ID);

    }

    return parameter;
  }


  @Override
  protected WorkflowActivityParameter buildFinalizationActivities() {
    WorkflowActivityParameter parameter = super.buildFinalizationActivities();

    parameter.addActivity(UnloadSubsystemResourcesActivity.ID);
    return parameter;
  }

}
