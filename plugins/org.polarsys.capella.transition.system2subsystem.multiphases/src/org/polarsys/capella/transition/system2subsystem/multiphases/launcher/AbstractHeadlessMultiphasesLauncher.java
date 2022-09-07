/*******************************************************************************
 * Copyright (c) 2006, 2017 THALES GLOBAL SERVICES.
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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.exception.TransitionException;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.core.transition.common.transposer.ExtendedTransposer;
import org.polarsys.capella.transition.system2subsystem.activities.CopyManagementPropertiesActivity;
import org.polarsys.capella.transition.system2subsystem.launcher.SubSystemLauncher;
import org.polarsys.capella.transition.system2subsystem.multiphases.MultiphasesContext;
import org.polarsys.capella.transition.system2subsystem.transposer.SubsystemRuleHandler;
import org.polarsys.kitalpha.cadence.core.api.parameter.WorkflowActivityParameter;
import org.polarsys.kitalpha.transposer.TransposerCorePlugin;
import org.polarsys.kitalpha.transposer.api.ITransposerWorkflow;
import org.polarsys.kitalpha.transposer.rules.handler.exceptions.mappings.purposes.NonExistingPurposeException;

class AbstractHeadlessMultiphasesLauncher extends SubSystemLauncher {

  private final MultiphasesContext context;
  private final MultiphasesContext.Mapping mapping;

  public AbstractHeadlessMultiphasesLauncher(MultiphasesContext context_p, MultiphasesContext.Mapping mapping_p) {
    context = context_p;
    mapping = mapping_p;
  }

  @Override
  protected final String getMapping() {
    return mapping.getMappingId();
  }

  @Override
  public final void run(Collection<?> selection_p, boolean save, IProgressMonitor monitor_p) {
    context.setMapping(mapping);
    super.run(selection_p, save, monitor_p);
  }

  @Override
  // overridden to update rules handler in context
  public final void launch(Collection<?> selection_p, String purpose_p, String mappingId_p,
      IProgressMonitor monitor_p) {
    try {
      initializeLogHandler();
      transposer = createTransposer(purpose_p, mappingId_p);
      context.put(ITransitionConstants.TRANSPOSER_INSTANCE, transposer);
      context.put(ITransitionConstants.TRANSPOSER_SELECTION, selection_p);
      context.put(ITransposerWorkflow.TRANSPOSER_ANALYSIS_SOURCES, new ArrayList<Object>());
      context.put(ITransitionConstants.RULES_HANDLER, transposer.getRulesHandler());
      context.put(ITransitionConstants.TRANSPOSER_PURPOSE, purpose_p);
      context.put(ITransitionConstants.COMMAND_NAME, getName());
      context.put(ITransitionConstants.TRANSPOSER_MAPPING, mappingId_p);
      context.put(ITransposerWorkflow.TRANSPOSER_ANALYSIS_SOURCES, new ArrayList<Object>());

      initializeParameters();
      triggerActivities(selection_p, getWorkflow(), monitor_p);

    } catch (OperationCanceledException e) {
      processCancel();
      throw e;

    } catch (TransitionException e) {
      LogHelper.getInstance().error(e.getMessage(), Messages.Activity_Transition);
      e.printStackTrace();
      throw e;

    } catch (Exception e) {
      LogHelper.getInstance().error(e.getMessage(), Messages.Activity_Transition);
      e.printStackTrace();
      throw new TransitionException(e);

    } finally {
      dispose();
    }
  }

  @Override
  /**
   * Initialization is performed only once in the HeadlessMultiphasesLauncher, so this must be overridden to prevent
   * re-initialisation when a new phase transition starts.
   */
  protected final WorkflowActivityParameter buildInitializationActivities() {
    return new WorkflowActivityParameter();
  }

  @Override
  // context is shared across different transposer invocations
  // this requires some initialization tricks since this kind of usage
  // was never predicted in transposer
  protected ExtendedTransposer createTransposer(String purpose_p, String mappingId_p) {
    return new ExtendedTransposer(purpose_p, mappingId_p) {
      @Override
      public void initCadence() {
        extendedCadenceLauncher = AbstractHeadlessMultiphasesLauncher.this.cadenceLauncher;
      }

      @Override
      public void initRulesHandler(String purpose_p, String mappingId_p) {
        try {
          _rulesHandler = new SubsystemRuleHandler(purpose_p, mappingId_p);
          _rulesHandler.setContext(context);
          initContext();
        } catch (NonExistingPurposeException e) {
          TransposerCorePlugin.getDefault().logError(TransposerCorePlugin.PLUGIN_ID, e.getMessage(), e);
        }
      }
    };
  }

  @Override
  /**
   * Diffmerge is invoked manually after all phases transitions have been completed, so this must be overridden to
   * prevent the launch of diffmerge after a phase transistion ends.
   */
  protected final WorkflowActivityParameter buildDiffMergeActivities() {
    WorkflowActivityParameter parameter = new WorkflowActivityParameter();

    if (getTransposer() != null) {

      // CopyManagementPropertiesActivity
      parameter.addActivity(getActivity(CopyManagementPropertiesActivity.ID));
    }

    return parameter;
  }

  /**
   * Finalization is invoked manually after all phases transitions have been completed, so this must be overridden to
   * prevent the launch of finalization after a phase transistion ends.
   */
  @Override
  protected WorkflowActivityParameter buildFinalizationActivities() {
    return new WorkflowActivityParameter();
  }
}
