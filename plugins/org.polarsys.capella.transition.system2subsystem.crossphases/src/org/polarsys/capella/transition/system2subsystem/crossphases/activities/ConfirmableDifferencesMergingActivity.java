/*******************************************************************************
 * Copyright (c) 2025 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.crossphases.activities;

import org.eclipse.core.runtime.IStatus;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.system.activities.DifferencesMergingActivity;
import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * Activity to merge content.
 * <p>
 * Default implementation always says 'false' to 'SAVE_REQUIRED' property. 
 * </p>
 * <p>
 * FinalizeTransitionActivity is not driven by the execution flow as it in Finalize phase.
 * </p>
 * 
 * @author nicolas.peransin@obeo.fr
 */
public class ConfirmableDifferencesMergingActivity extends DifferencesMergingActivity {

  public static final String ID = ConfirmableDifferencesMergingActivity.class.getName();
  
  
  @Override
  public IStatus _run(ActivityParameters activityParams) {
    IStatus result = super._run(activityParams);
    
    IContext context = (IContext) activityParams.getParameter(TRANSPOSER_CONTEXT).getValue();
    
    context.put(ITransitionConstants.SAVE_REQUIRED, result.isOK());
    return result;
  }
  
}
