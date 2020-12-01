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
package org.polarsys.capella.transition.system2subsystem.interphases.ui.handlers;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.polarsys.capella.common.ef.command.ICommand;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.IHandler;
import org.polarsys.capella.core.transition.common.ui.commands.CommandUIHandler;
import org.polarsys.capella.core.transition.common.ui.handlers.options.TransitionUIOptionsHandler;
import org.polarsys.capella.transition.system2subsystem.interphases.activities.InitializeDiffMergeActivity;
import org.polarsys.capella.transition.system2subsystem.interphases.activities.InitializeTransitionActivity;
import org.polarsys.capella.transition.system2subsystem.interphases.commands.InterPhasesCommand;
import org.polarsys.capella.transition.system2subsystem.ui.merge.SubSystemUIDifferencesHandler;
import org.polarsys.kitalpha.cadence.core.api.parameter.GenericParameter;


public class InterPhasesTransitionHandler extends CommandUIHandler {

  @Override
  protected ICommand createCommand(Collection<?> selection_p, IProgressMonitor progressMonitor_p) {
    InterPhasesCommand command = new InterPhasesCommand(selection_p, progressMonitor_p);

    command.addParameter(InitializeDiffMergeActivity.ID, new GenericParameter<IHandler>(ITransitionConstants.MERGE_DIFFERENCES_HANDLER,
        new SubSystemUIDifferencesHandler(), "Merge UI wizard")); //$NON-NLS-1$

    // Add UI Options handler
    GenericParameter<IHandler> optionsHandler = new GenericParameter<IHandler>(ITransitionConstants.OPTIONS_HANDLER, new TransitionUIOptionsHandler() {
      @Override
      protected String getTitle() {
        return "Horizontal " + super.getTitle(); //$NON-NLS-1$
      }
    }, "Transposer Options handler"); //$NON-NLS-1$
    command.addParameter(InitializeTransitionActivity.ID, optionsHandler);

    return command;

  }

}
