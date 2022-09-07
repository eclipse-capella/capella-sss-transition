/*******************************************************************************
 * Copyright (c) 2006, 2018 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.interphases.launcher;

import org.polarsys.capella.core.transition.common.activities.DifferencesComputingActivity;
import org.polarsys.capella.core.transition.common.activities.DifferencesMergingActivity;
import org.polarsys.capella.core.transition.common.activities.InitializeScopeActivity;
import org.polarsys.capella.core.transition.common.activities.PostTransformationActivity;
import org.polarsys.capella.transition.system2subsystem.activities.CopyImagesActivity;
import org.polarsys.capella.transition.system2subsystem.activities.CopyManagementPropertiesActivity;
import org.polarsys.capella.transition.system2subsystem.activities.FinalizeSubsystemTransitionActivity;
import org.polarsys.capella.transition.system2subsystem.activities.InitializeTransformationActivity;
import org.polarsys.capella.transition.system2subsystem.interphases.activities.InitializeDiffMergeActivity;
import org.polarsys.capella.transition.system2subsystem.interphases.activities.InitializeTransitionActivity;
import org.polarsys.capella.transition.system2subsystem.launcher.SubSystemLauncher;
import org.polarsys.kitalpha.cadence.core.api.parameter.GenericParameter;
import org.polarsys.kitalpha.cadence.core.api.parameter.WorkflowActivityParameter;
import org.polarsys.kitalpha.transposer.rules.handler.api.IRulesHandler;

public class HeadlessInterPhasesLauncher extends SubSystemLauncher {

  private final String MAPPING = "org.polarsys.capella.transition.system2subsystem.interphases"; //$NON-NLS-1$

  @Override
  protected String getMapping() {
    return MAPPING;
  }

  /**
   * Activities to be loaded in the workflow element of cadence "PRE ANALYSIS"
   * 
   * @return the associated workflow element
   */
  @Override
  protected WorkflowActivityParameter buildPreAnalysisActivities() {
    WorkflowActivityParameter parameter = new WorkflowActivityParameter();

    if (getTransposer() != null) {

      // InitializeTransitionActivity
      parameter.addActivity(InitializeTransitionActivity.ID);
      GenericParameter<IRulesHandler> param = new GenericParameter<IRulesHandler>(
          org.polarsys.capella.core.transition.common.activities.InitializeTransitionActivity.PARAMETER_RULE_HANDLER,
          getTransposer().getRulesHandler(), "Transposer Rule handler"); //$NON-NLS-1$
      parameter.addParameter(InitializeTransitionActivity.ID, param);

      // InitializeTransformationActivity
      parameter.addActivity(InitializeTransformationActivity.ID);

      // InitializeScopeActivity
      parameter.addActivity(InitializeScopeActivity.ID);

    }

    return parameter;
  }

  /**
   * Activities to be loaded in the workflow element of cadence "POST EXECUTION"
   * 
   * @return the associated workflow element
   */
  @Override
  protected WorkflowActivityParameter buildDiffMergeActivities() {
    WorkflowActivityParameter parameter = new WorkflowActivityParameter();

    if (getTransposer() != null) {

      // CopyManagementPropertiesActivity
      parameter.addActivity(getActivity(CopyManagementPropertiesActivity.ID));

      // PostTransformationActivity
      parameter.addActivity(getActivity(PostTransformationActivity.ID));

      // InitializeDiffMergeActivity
      parameter.addActivity(InitializeDiffMergeActivity.ID);

      // DifferencesComputingActivity
      parameter.addActivity(DifferencesComputingActivity.ID);

      // DifferencesMergingActivity
      parameter.addActivity(DifferencesMergingActivity.ID);

      // Copy images to the target project
      parameter.addActivity(CopyImagesActivity.ID);

    }

    return parameter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected WorkflowActivityParameter buildFinalizationActivities() {
    WorkflowActivityParameter parameter = super.buildFinalizationActivities();

    parameter.addActivity(FinalizeSubsystemTransitionActivity.ID);
    return parameter;
  }

}
