/*******************************************************************************
 * Copyright (c) 2006, 2015 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.crossphases.rules.core;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.transition.system2subsystem.context.SubSystemContextHelper;
import org.polarsys.capella.transition.system2subsystem.crossphases.handlers.attachment.CrossPhasesAttachmentHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;


public class PropertyValuePkgRule extends org.polarsys.capella.transition.system2subsystem.rules.core.PropertyValuePkgRule {

  @Override
  protected EObject getSourceContainer(EObject element_p, EObject result_p, IContext context_p) {
    if (element_p.eContainer() instanceof Component) {
      EObject bestContainer = CrossPhasesAttachmentHelper.getInstance(context_p).getRelatedComponent((Component) element_p.eContainer(), context_p);
      return bestContainer;
    }
    return super.getSourceContainer(element_p, result_p, context_p);
  }

  @Override
  protected EObject getBestContainer(EObject element, EObject result, IContext context) {
    if (element.eContainer() instanceof BlockArchitecture) {
      return SystemEngineeringExt.getOwnedSystemAnalysis(SubSystemContextHelper.getTransformedEngineering(context));
    }
    return super.getBestContainer(element, result, context);
  }

}
