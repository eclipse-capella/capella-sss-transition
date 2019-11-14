/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.rules.fa;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class ComponentExchangeRule extends org.polarsys.capella.core.transition.system.rules.fa.ComponentExchangeRule {

  @Override
  protected void retrieveGoDeep(EObject source_p, List<EObject> result_p, IContext context_p) {
    super.retrieveGoDeep(source_p, result_p, context_p);

    if (ContextScopeHandlerHelper.getInstance(context_p).contains(ITransitionConstants.SOURCE_SCOPE, source_p, context_p)) {
      ComponentExchange element = (ComponentExchange) source_p;
      result_p.addAll(element.getCategories());
    }
  }
  
  @Override
  protected EObject getBestContainer(EObject element, EObject result, IContext context) {
    // If the CE is contained in the System, we cannot find its container just by traceability.
    if (BlockArchitectureExt.getRootBlockArchitecture(element).getSystem() == element.eContainer()) {
      return null;
    }

    return super.getBestContainer(element, result, context);
  }
}
