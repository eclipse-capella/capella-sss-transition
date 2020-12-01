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
package org.polarsys.capella.transition.system2subsystem.crossphases.rules.fa;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentPort;
import org.polarsys.capella.core.model.helpers.ComponentExchangeExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.transition.system2subsystem.crossphases.handlers.attachment.CrossPhasesAttachmentHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;


public class ComponentPortRule extends org.polarsys.capella.core.transition.system.rules.fa.ComponentPortRule {

  @Override
  public IStatus transformRequired(EObject source, IContext context) {

    ComponentPort cp = (ComponentPort) source;

    if (cp.getComponentExchanges().isEmpty()) {
      //we transform not connected ports
      return super.transformRequired(source, context);
    }

    for (ComponentExchange exchange : cp.getComponentExchanges()) {

      Component sourceComponent = ComponentExchangeExt.getSourceComponent(exchange);
      boolean sourceComponentIncluded =
          ContextScopeHandlerHelper.getInstance(context).contains(ITransitionConstants.SOURCE_SCOPE, sourceComponent, context);

      Component targetComponent = ComponentExchangeExt.getTargetComponent(exchange);
      boolean targetComponentIncluded =
          ContextScopeHandlerHelper.getInstance(context).contains(ITransitionConstants.SOURCE_SCOPE, targetComponent, context);

      if (targetComponentIncluded ^ sourceComponentIncluded) {
        return super.transformRequired(source, context);
      }
    }

    return new Status(IStatus.WARNING, Messages.Activity_Transformation, NLS.bind("ComponentPort ''{0}'' is linked to internal elements", LogHelper
        .getInstance().getText(cp)));
  }

  @Override
  protected EObject getSourceContainer(EObject element, EObject result, IContext context) {
    EObject bestContainer = CrossPhasesAttachmentHelper.getInstance(context).getRelatedComponent((Component) element.eContainer(), context);
    return bestContainer;
  }
  
	  

  @Override
  protected void updateElement(EObject element, EObject result, IContext context) {
    super.updateElement(element, result, context);
  }
}
