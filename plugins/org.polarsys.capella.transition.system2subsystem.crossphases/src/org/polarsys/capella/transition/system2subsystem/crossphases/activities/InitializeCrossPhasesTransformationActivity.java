/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.ctx.CtxFactory;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.transition.system.helpers.ContextHelper;
import org.polarsys.capella.transition.system2subsystem.activities.InitializeTransformationActivity;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class InitializeCrossPhasesTransformationActivity extends InitializeTransformationActivity {
  public static final String ID = "org.polarsys.capella.transition.system2subsystem.crossphases.activities.InitializeCrossPhasesTransformationActivity";

  // create and attach a system analysis to the created project's system engineering, so certain elements that use sys
  // analysis as a container can retrieve it
  @Override
  protected EObject createTargetTransformationContainer(Resource source, IContext context) {
    Project targetProject = (Project) super.createTargetTransformationContainer(source, context);

    SystemEngineering sourceEngineering = ContextHelper.getSourceEngineering(context);
    SystemAnalysis sourceSA = SystemEngineeringExt.getSystemAnalysis(sourceEngineering);

    SystemEngineering targetEngineering = SystemEngineeringExt.getSystemEngineering(targetProject);
    SystemAnalysis targetSA = CtxFactory.eINSTANCE.createSystemAnalysis(sourceSA.getName());
    targetSA.setId(sourceSA.getId());
    targetEngineering.getOwnedArchitectures().add(targetSA);

    return targetProject;
  }
}
