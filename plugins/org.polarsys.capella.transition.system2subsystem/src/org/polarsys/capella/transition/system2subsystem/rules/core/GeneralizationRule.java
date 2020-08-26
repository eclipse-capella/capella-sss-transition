/*******************************************************************************
 * Copyright (c) 2020 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.rules.core;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.capellacore.Generalization;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class GeneralizationRule extends org.polarsys.capella.core.transition.system.rules.core.GeneralizationRule {

  @Override
  protected void retrieveGoDeep(EObject source, List<EObject> result, IContext context) {
    super.retrieveGoDeep(source, result, context);

    Generalization element = (Generalization) source;
    EObject targetElement = element.getSuper();

    if (targetElement instanceof ExchangeItem) {
      result.add(targetElement);
    }
  }
}
