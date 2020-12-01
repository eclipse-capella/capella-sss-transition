/*******************************************************************************
 * Copyright (c) 2006, 2016 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.multiphases.launcher;

import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.polarsys.capella.common.tools.report.config.registry.ReportManagerRegistry;
import org.polarsys.capella.common.tools.report.util.IReportManagerDefaultComponents;
import org.polarsys.capella.core.transition.common.activities.DifferencesMergingActivity;
import org.polarsys.capella.core.transition.common.activities.FinalizeTransitionActivity;
import org.polarsys.capella.core.transition.common.activities.InitializeScopeActivity;
import org.polarsys.capella.core.transition.common.activities.InitializeTransitionActivity;
import org.polarsys.capella.core.transition.common.activities.PostTransformationActivity;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.transposer.ExtendedRulesHandler;
import org.polarsys.capella.core.transition.common.transposer.SharedWorkflowActivityParameter;
import org.polarsys.capella.transition.system2subsystem.activities.FinalizeSubsystemTransitionActivity;
import org.polarsys.capella.transition.system2subsystem.activities.RootComponentNameUpdater;
import org.polarsys.capella.transition.system2subsystem.crossphases.handlers.attachment.CrossPhasesAttachmentHelper;
import org.polarsys.capella.transition.system2subsystem.multiphases.MultiphasesContext;
import org.polarsys.capella.transition.system2subsystem.multiphases.activities.InitializeMultiphasesDiffMergeActivity;
import org.polarsys.capella.transition.system2subsystem.multiphases.activities.InitializeMultiphasesTransformationActivity;
import org.polarsys.capella.transition.system2subsystem.multiphases.activities.InitializeMultiphasesTransitionActivity;
import org.polarsys.capella.transition.system2subsystem.multiphases.activities.JustificationLinkPass;
import org.polarsys.capella.transition.system2subsystem.multiphases.activities.MultiphasesDifferencesComputingActivity;
import org.polarsys.capella.transition.system2subsystem.multiphases.activities.RealizationLinkPass;
import org.polarsys.capella.transition.system2subsystem.multiphases.handlers.attachment.LAAttachmentHelper;
import org.polarsys.capella.transition.system2subsystem.multiphases.handlers.attachment.PAAttachmentHelper;
import org.polarsys.capella.transition.system2subsystem.multiphases.handlers.attachment.SAAttachmentHelper;
import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.kitalpha.cadence.core.api.parameter.GenericParameter;
import org.polarsys.kitalpha.transposer.api.ITransposerWorkflow;
import org.polarsys.kitalpha.transposer.rules.handler.api.IRulesHandler;
import org.polarsys.kitalpha.transposer.rules.handler.exceptions.mappings.purposes.NonExistingPurposeException;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class HeadlessMultiphasesLauncher {

  private final SharedWorkflowActivityParameter headlessParameters;
  private final Logger logger;

  public HeadlessMultiphasesLauncher(SharedWorkflowActivityParameter parameters) {
    this.headlessParameters = parameters;
    this.logger = ReportManagerRegistry.getInstance().subscribe(IReportManagerDefaultComponents.DEFAULT);
  }


  public void launch(Collection<?> selection, IProgressMonitor monitor) {

    final MultiphasesContext context = new MultiphasesContext(selection);
    IStatus result = initializeMultiphasesTransition(context);

    if (result.isOK()) {
      try {

        context.put(CrossPhasesAttachmentHelper.CROSS_PHASES_ATTACHMENT_HELPER, new SAAttachmentHelper());
        new SA_Launcher(context).run(selection, true, monitor);

        context.put(CrossPhasesAttachmentHelper.CROSS_PHASES_ATTACHMENT_HELPER, new LAAttachmentHelper());
        new LA_Launcher(context).run(selection, true, monitor);

        context.put(CrossPhasesAttachmentHelper.CROSS_PHASES_ATTACHMENT_HELPER, new PAAttachmentHelper());
        new PA_Launcher(context).run(selection, true, monitor);

        new LostAndFoundPass().attachLostAndFound(context);
        new RealizationLinkPass().createRealizationLinks(context.getTempSystemEngineering(), context);
        new PostTransformationActivity().run(createActivityParameters(PostTransformationActivity.ID, context));
        new JustificationLinkPass().createJustificationLinks(context.getTempSystemEngineering(), context.getSelectedPhysicalComponents());
        new RootComponentNameUpdater().run(createActivityParameters(RootComponentNameUpdater.ID, context));
        
        new InitializeMultiphasesDiffMergeActivity().run(createActivityParameters(InitializeMultiphasesDiffMergeActivity.ID, context));
        new MultiphasesDifferencesComputingActivity().run(createActivityParameters(MultiphasesDifferencesComputingActivity.ID, context));
        new DifferencesMergingActivity().run(createActivityParameters(DifferencesMergingActivity.ID, context));
        new FinalizeTransitionActivity().run(createActivityParameters(FinalizeTransitionActivity.ID, context));
      } finally {
    	  try {
    		  ActivityParameters params = new ActivityParameters();
    		  params.addParameter(new GenericParameter<IContext>(ITransposerWorkflow.TRANSPOSER_CONTEXT, context, null));
    		  new FinalizeSubsystemTransitionActivity().run(params);
    	  } finally {
    		  context.fullReset();
    	  }
      }
    } else {
      if (result == Status.CANCEL_STATUS) {
        // the cancel status singleton doesn't carry a message, and we would like a message..
        logger.debug("Multiphase transition was cancelled"); //$NON-NLS-1$
      } else {
        logger.log(statusToLevel(result), result.getMessage());
      }
    }
  }

  private Level statusToLevel(IStatus status) {
    switch (status.getSeverity()) {
      case IStatus.ERROR:
        return Level.ERROR;
      case IStatus.INFO:
        return Level.INFO;
      case IStatus.CANCEL:
        return Level.DEBUG;
      case IStatus.WARNING:
        return Level.WARN;
      default:
        return Level.ERROR;
    }
  }

  private ActivityParameters createActivityParameters(String activityID, MultiphasesContext context) {
    ActivityParameters parameter = headlessParameters.getActivityParameters(activityID);
    parameter.addParameter(new GenericParameter<IContext>(ITransposerWorkflow.TRANSPOSER_CONTEXT, context, null));
    parameter.addParameter(new GenericParameter<IRulesHandler>(InitializeTransitionActivity.PARAMETER_RULE_HANDLER, null, null));
    return parameter;
  }

  // here we fake a cadence invocation to initialize the transition.
  // TODO refactor to allow to be called outside cadence without the use of generic parameters..
  private IStatus initializeMultiphasesTransition(MultiphasesContext context) {

    IStatus status = new InitializeMultiphasesTransitionActivity().run(createActivityParameters(InitializeMultiphasesTransitionActivity.ID, context));

    if (status.isOK()) {
      status = new InitializeMultiphasesTransformationActivity().run(createActivityParameters(InitializeMultiphasesTransformationActivity.ID, context));
    }

    if (status.isOK()) {
      // this initializes the scope with rules from SA.
      try {
        IRulesHandler handler = new ExtendedRulesHandler("org.polarsys.capella.core.transition", MultiphasesContext.Mapping.SA.getMappingId()); //$NON-NLS-1$
        context.put(ITransitionConstants.RULES_HANDLER, handler);
      } catch (NonExistingPurposeException e) {
        throw new IllegalStateException(e);
      }
      status = new InitializeScopeActivity().run(createActivityParameters(InitializeScopeActivity.ID, context));
    }

    return status;
  }

  protected class SA_Launcher extends AbstractHeadlessMultiphasesLauncher {
    public SA_Launcher(MultiphasesContext context) {
      super(context, MultiphasesContext.Mapping.SA);
    }
  }

  protected class LA_Launcher extends AbstractHeadlessMultiphasesLauncher {
    public LA_Launcher(MultiphasesContext context) {
      super(context, MultiphasesContext.Mapping.LA);
    }
  }

  protected class PA_Launcher extends AbstractHeadlessMultiphasesLauncher {
    public PA_Launcher(MultiphasesContext context) {
      super(context, MultiphasesContext.Mapping.PA);
    }
  }
}
