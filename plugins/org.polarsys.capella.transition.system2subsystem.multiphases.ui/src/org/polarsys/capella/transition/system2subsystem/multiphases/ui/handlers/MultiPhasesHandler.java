/*******************************************************************************
 * Copyright (c) 2006, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.multiphases.ui.handlers;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.polarsys.capella.common.ef.command.ICommand;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.IHandler;
import org.polarsys.capella.core.transition.common.transposer.SharedWorkflowActivityParameter;
import org.polarsys.capella.core.transition.common.ui.commands.CommandUIHandler;
import org.polarsys.capella.core.transition.common.ui.handlers.options.TransitionUIOptionsHandler;
import org.polarsys.capella.transition.system2subsystem.multiphases.activities.InitializeMultiphasesDiffMergeActivity;
import org.polarsys.capella.transition.system2subsystem.multiphases.activities.InitializeMultiphasesTransitionActivity;
import org.polarsys.capella.transition.system2subsystem.multiphases.commands.HeadlessMultiphasesCommand;
import org.polarsys.capella.transition.system2subsystem.ui.merge.SubSystemUIDifferencesHandler;
import org.polarsys.kitalpha.cadence.core.api.parameter.GenericParameter;


public class MultiPhasesHandler extends CommandUIHandler {

  @Override
  protected ICommand createCommand(Collection<?> selection, IProgressMonitor progressMonitor) {

    SharedWorkflowActivityParameter parameter = new SharedWorkflowActivityParameter();
    parameter.addParameter(InitializeMultiphasesTransitionActivity.ID,  new GenericParameter<IHandler>(ITransitionConstants.OPTIONS_HANDLER, new TransitionUIOptionsHandler() {
      @Override
      protected String getTitle() {
        return "SA-LA-PA " + super.getTitle(); //$NON-NLS-1$
      }

    }, "Transposer Options handler")); //$NON-NLS-1$

    parameter.addParameter(InitializeMultiphasesDiffMergeActivity.ID, new GenericParameter<IHandler>(ITransitionConstants.MERGE_DIFFERENCES_HANDLER,
        new SubSystemUIDifferencesHandler(), "Merge UI wizard")); //$NON-NLS-1$

    HeadlessMultiphasesCommand c = new HeadlessMultiphasesCommand(selection);
    c.addParameters(parameter);

    return c;
  }

}
