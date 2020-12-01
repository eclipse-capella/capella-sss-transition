/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.activities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.polarsys.capella.core.data.capellacore.TypedElement;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.transition.common.activities.AbstractActivity;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.transition.system2subsystem.context.SubSystemContextHelper;
import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.kitalpha.transposer.api.ITransposerWorkflow;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * Updates root component names of the transformation result according to
 * <p>
 * The transition shall create a system named as the selected Node Physical Component, that has been selected to
 * initiate the transition, in the System Analysis phase of the target model.
 * </p>
 * and
 * <p>
 * The transition shall create a Logical System named as the selected Node Physical Component, that has been selected to
 * initiate the transition, in the System Analysis phase of the target model.
 * </p>
 */
public class RootComponentNameUpdater extends AbstractActivity implements ITransposerWorkflow {

  public static final String ID = "org.polarsys.capella.transition.system2subsystem.activities.RootComponentNameUpdater"; //$NON-NLS-1$

  @Override
  protected IStatus _run(ActivityParameters activityParams) {
    IContext context = (IContext) activityParams.getParameter(TRANSPOSER_CONTEXT).getValue();
    List<Component> selection = ((Collection<?>) context.get(ITransitionConstants.TRANSITION_SOURCES)).stream()
        .filter(Component.class::isInstance).map(Component.class::cast).collect(Collectors.toList());

    // only if one component was selected. otherwise keep default values.
    if (selection.size() == 1) {
      String newName = selection.get(0).getName();
      updateRootComponentNames(SubSystemContextHelper.getTransformedEngineering(context), newName);
    }

    return Status.OK_STATUS;
  }

  public void updateRootComponentNames(SystemEngineering se, String newName) {

    Component[] toRename = { getSystem(SystemEngineeringExt.getSystemAnalysis(se)),
        getSystem(SystemEngineeringExt.getLogicalArchitecture(se)) };

    for (Component rename : toRename) {
      if (rename != null) {
        rename.setName(newName);
        for (TypedElement e : rename.getTypedElements()) {
          e.setName(newName);
        }
      }
    }
  }

  private Component getSystem(BlockArchitecture architecture) {
    if (architecture != null) {
      return architecture.getSystem();
    }
    return null;
  }

}
