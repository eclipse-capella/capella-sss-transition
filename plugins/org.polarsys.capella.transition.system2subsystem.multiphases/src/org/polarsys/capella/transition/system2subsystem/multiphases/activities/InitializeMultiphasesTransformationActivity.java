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
package org.polarsys.capella.transition.system2subsystem.multiphases.activities;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.ui.business.api.viewpoint.ViewpointSelection;
import org.eclipse.ui.statushandlers.StatusManager;
import org.polarsys.capella.common.libraries.ModelInformation;
import org.polarsys.capella.core.data.capellamodeller.Library;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.libraries.ui.wizard.newLibrary.LibrarySessionCreationHelper;
import org.polarsys.capella.core.model.handler.command.CapellaResourceHelper;
import org.polarsys.capella.core.model.helpers.ProjectExt;
import org.polarsys.capella.core.platform.sirius.ui.project.operations.ProjectSessionCreationHelper;
import org.polarsys.capella.core.sirius.ui.helper.SessionHelper;
import org.polarsys.capella.core.transition.common.constants.ISchemaConstants;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.IHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.ITraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.config.ExtendedTraceabilityConfiguration;
import org.polarsys.capella.core.transition.system.handlers.traceability.LibraryTraceabilityHandler;
import org.polarsys.capella.core.transition.system.helpers.ContextHelper;
import org.polarsys.capella.transition.system2subsystem.activities.FinalizeSubsystemTransitionActivity;
import org.polarsys.capella.transition.system2subsystem.activities.InitializeTransformationActivity;
import org.polarsys.capella.transition.system2subsystem.multiphases.handlers.traceability.config.RecTraceabilityHandler;
import org.polarsys.capella.transition.system2subsystem.multiphases.handlers.traceability.config.SIDTraceabilityHandler;
import org.polarsys.kitalpha.emde.model.ElementExtension;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public final class InitializeMultiphasesTransformationActivity extends InitializeTransformationActivity {

  @Override
  protected IHandler createDefaultTraceabilityTransformationHandler() {

    ExtendedTraceabilityConfiguration configuration = new ExtendedTraceabilityConfiguration() {

      @Override
      protected String getExtensionIdentifier(IContext context) {
        return ISchemaConstants.TRANSFORMATION_TRACEABILITY_CONFIGURATION;
      }

      @Override
      protected void initHandlers(IContext fContext_p) {
        addHandler(fContext_p, new SIDTraceabilityHandler(getIdentifier(fContext_p)));
        addHandler(fContext_p, new RecTraceabilityHandler());
        addHandler(fContext_p, new LibraryTraceabilityHandler());
      }

      @Override
      public boolean useHandlerForAttachment(EObject source_p, EObject target_p, ITraceabilityHandler handler_p,
          IContext context_p) {
        if (LibraryTraceabilityHandler.isLibraryElement(source_p, context_p)) {
          return handler_p instanceof LibraryTraceabilityHandler;
        }

        if (handler_p instanceof RecTraceabilityHandler) {
          return false;
        }
        return super.useHandlerForAttachment(source_p, target_p, handler_p, context_p);
      }

      @Override
      public boolean useHandlerForSourceElements(EObject source, ITraceabilityHandler handler, IContext context) {
        if (LibraryTraceabilityHandler.isLibraryElement(source, context)) {
          return handler instanceof LibraryTraceabilityHandler;
        }
        return super.useHandlerForSourceElements(source, handler, context);
      }

      @Override
      public boolean useHandlerForTracedElements(EObject source, ITraceabilityHandler handler, IContext context) {
        if (LibraryTraceabilityHandler.isLibraryElement(source, context)) {
          return handler instanceof LibraryTraceabilityHandler;
        }
        return super.useHandlerForTracedElements(source, handler, context);
      }

    };
    return new MultiphaseTraceabilityHandler(configuration);
  }

  @Override
  protected EObject createTargetTransformationContainer(Resource targetResource, IContext context) {
    Project targetTemporaryProject = null;
    Project targetProject = ProjectExt.getProject(targetResource);
    if (context.get(ITransitionConstants.DIFFMERGE_DISABLE) == Boolean.TRUE) {
      // elements are created directly in the target model
      targetTemporaryProject = targetProject;
    } else {

      // Create a temporary project and reload its model through the target editing domain
      // The temporary project is removed later in FinalizeSubsystemTransitionActivity
      try {
        String temporaryProjectName = getTemporaryProjectName();
        Session session = null;
        if (targetProject instanceof Library) {
          session = new LibrarySessionCreationHelper(true, true).createFullProject(temporaryProjectName, null,
              Collections.<IProject> emptyList(),
              ViewpointSelection.getViewpoints(CapellaResourceHelper.CAPELLA_MODEL_FILE_EXTENSION),
              new NullProgressMonitor());
        } else {
          session = new ProjectSessionCreationHelper(true, true).createFullProject(temporaryProjectName, null,
              Collections.<IProject> emptyList(),
              ViewpointSelection.getViewpoints(CapellaResourceHelper.CAPELLA_MODEL_FILE_EXTENSION),
              new NullProgressMonitor());
        }
        Project project = SessionHelper.getCapellaProject(session);
        session.close(new NullProgressMonitor());
        targetTemporaryProject = (Project) ((EditingDomain) context.get(ITransitionConstants.TRANSITION_TARGET_EDITING_DOMAIN))
            .getResourceSet().getEObject(EcoreUtil.getURI(project), true);
        context.put(FinalizeSubsystemTransitionActivity.PARAM__DELETE_PROJECT,
            ResourcesPlugin.getWorkspace().getRoot().getProject(temporaryProjectName));
        
        
        Project sourceProject = ContextHelper.getSourceProject(context);

        // Copy Library information from source project to target project if any
        for (ElementExtension extension : sourceProject.getOwnedExtensions()) {
          if (extension instanceof ModelInformation) {
            ElementExtension copy = EcoreUtil.copy(extension);
            targetTemporaryProject.getOwnedExtensions().add(copy);
          }
        }
      } catch (InvocationTargetException exception_p) {
        StatusManager.getManager()
            .handle(new Status(IStatus.ERROR, org.polarsys.capella.transition.system2subsystem.Activator.PLUGIN_ID,
                exception_p.getMessage(), exception_p));
      } catch (InterruptedException exception_p) {
        StatusManager.getManager()
            .handle(new Status(IStatus.ERROR, org.polarsys.capella.transition.system2subsystem.Activator.PLUGIN_ID,
                exception_p.getMessage(), exception_p));
      }
    }
    return targetTemporaryProject;
  }

  private String getTemporaryProjectName() {
    return "MultiphaseTransformation_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); //$NON-NLS-1$ //$NON-NLS-2$
  }

}