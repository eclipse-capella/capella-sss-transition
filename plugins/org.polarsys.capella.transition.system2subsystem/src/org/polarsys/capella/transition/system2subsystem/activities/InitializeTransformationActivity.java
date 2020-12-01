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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.polarsys.capella.common.libraries.ModelInformation;
import org.polarsys.capella.core.data.capellamodeller.CapellamodellerFactory;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.transition.common.handlers.IHandler;
import org.polarsys.capella.core.transition.common.handlers.attachment.AttachmentHelper;
import org.polarsys.capella.core.transition.common.handlers.traceability.CompoundTraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.config.ITraceabilityConfiguration;
import org.polarsys.capella.core.transition.system.helpers.ContextHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.traceability.config.TransformationConfiguration;
import org.polarsys.kitalpha.emde.model.ElementExtension;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 *
 */
public class InitializeTransformationActivity
    extends org.polarsys.capella.core.transition.system.activities.InitializeTransformationActivity {

  public static final String ID = "org.polarsys.capella.transition.system2subsystem.activities.InitializeTransformationActivity"; //$NON-NLS-1$

  /**
   * {@inheritDoc}
   */
  @Override
  protected IHandler createDefaultTraceabilityTransformationHandler() {
    ITraceabilityConfiguration configuration = new TransformationConfiguration();
    return new CompoundTraceabilityHandler(configuration);
  }

  @Override
  protected EObject createTargetTransformationContainer(Resource source, IContext context) {

    Project sourceProject = ContextHelper.getSourceProject(context);
    SystemEngineering sourceEngineering = ContextHelper.getSourceEngineering(context);
    Project targetProject = CapellamodellerFactory.eINSTANCE.createProject(sourceProject.getName());
    
    SystemEngineering targetEngineering = CapellamodellerFactory.eINSTANCE.createSystemEngineering(sourceEngineering.getName());
    targetEngineering.setId(sourceEngineering.getId());
    targetProject.getOwnedModelRoots().add(targetEngineering);

    // Copy Library information from source project to target project if any
    for (ElementExtension extension : sourceProject.getOwnedExtensions()) {
      if (extension instanceof ModelInformation) {
        ElementExtension copy = EcoreUtil.copy(extension);
        targetProject.getOwnedExtensions().add(copy);
      }
    }

    AttachmentHelper.getInstance(context).createdElement(null, targetProject, context);
    return targetProject;
  }
}
