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
package org.polarsys.capella.transition.system2subsystem.activities;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.polarsys.capella.common.helpers.EcoreUtil2;
import org.polarsys.capella.core.transition.common.activities.FinalizeTransitionActivity;
import org.polarsys.capella.core.transition.common.constants.IOptionsConstants;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.transition.system2subsystem.Activator;
import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.kitalpha.cadence.core.api.parameter.GenericParameter;
import org.polarsys.kitalpha.transposer.api.ITransposerWorkflow;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * Save the result in Target if not cancelled.
 * <p>
 * Default implementation {@link FinalizeTransitionActivity} always saves the transformation.
 * </p>
 * 
 * @author nperansin@obeo.fr
 */
public class SsbsFinalizeTransitionActivity extends FinalizeTransitionActivity {

  public static final String ID = SsbsFinalizeTransitionActivity.class.getName();
   
  @Override
  public IStatus _run(ActivityParameters activityParams) {
    IContext context = (IContext) activityParams.getParameter(ITransposerWorkflow.TRANSPOSER_CONTEXT).getValue();

    boolean shouldSave = Boolean.TRUE.equals(context.get(ITransitionConstants.SAVE_REQUIRED));
    // Default Finalization saves result even if cancelled.
    // And fragments are not saved.
    
    if (!shouldSave) {
      // don't have anything to cleanup/save
      return Status.OK_STATUS;
    }

    // Maybe with an option of configuration ?
    // Save if it another resource than the source resource
    Resource targetResource = (Resource) context.get(ITransitionConstants.TRANSITION_TARGET_RESOURCE);

    if (targetResource == null) {
      // don't have anything to cleanup/save
      return Status.OK_STATUS;
    }

    Resource sourceResource = (Resource) context.get(ITransitionConstants.TRANSITION_SOURCE_RESOURCE);
    if (targetResource != sourceResource // on same resource, undo/redo is applicable.
        && !savingBySession(targetResource)) {
      saveTargetResource(targetResource);
    }
    notifyDryRun(activityParams);

    return Status.OK_STATUS;
  }
  
  private void notifyDryRun(ActivityParameters activityParams) {
    GenericParameter<?> parameter = activityParams.getParameter(IOptionsConstants.IS_DRY_RUN);
    if (parameter == null || !Boolean.valueOf(parameter.getValue().toString())) {
      LogHelper.getInstance().info("Operation has been successful.", Messages.Activity_Transition);
    }
  }
  
  private boolean savingBySession(Resource targetResource) {
    Session session = SessionManager.INSTANCE.getSession(targetResource);
    if (session == null) {
      return false;
    }
    if (session.isOpen()) {
      session.save(new NullProgressMonitor());
      LogHelper.getInstance().info(
          NLS.bind("Session for ''{0}'' has been saved automatically.", targetResource.getURI()),
          Messages.Activity_Transition);
    }
    return true;
  }
  
  private void saveTargetResource(Resource targetResource) {
    List<Resource> projectResources = getProjectRelatedResources(targetResource);
    
    for (Resource resource : projectResources) {
      try {
        resource.save(Collections.emptyMap());
      
      } catch (IOException exception) {
        LogHelper.getInstance().log(exception.getMessage(),
            new Status(IStatus.ERROR, Activator.PLUGIN_ID, exception.getMessage(), exception),
            Messages.Activity_Transformation);
      }
    }
  }
  
  private static boolean isProjectFragment(Resource resource, IProject project) {
    return resource.getURI().isPlatformResource() 
        && project.equals(EcoreUtil2.getFile(resource).getProject());
  }
  
  static List<Resource> getProjectRelatedResources(Resource targetResource) {
    IProject targetProject = EcoreUtil2.getFile(targetResource).getProject();
    ResourceSet set = targetResource.getResourceSet();
    
    return set.getResources().stream()
        .filter(r -> isProjectFragment(r, targetProject))
        .collect(Collectors.toList());
  }
  
}


