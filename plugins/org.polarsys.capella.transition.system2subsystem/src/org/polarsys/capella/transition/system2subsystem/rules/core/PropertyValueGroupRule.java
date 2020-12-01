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
package org.polarsys.capella.transition.system2subsystem.rules.core;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.capellacore.PropertyValuePkg;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class PropertyValueGroupRule extends org.polarsys.capella.core.transition.system.rules.core.PropertyValueGroupRule {

  @Override
  protected void retrieveContainer(EObject element_p, List<EObject> result_p, IContext context_p) {
    if (element_p.eContainer() instanceof PropertyValuePkg) {
      super.retrieveContainer(element_p, result_p, context_p);
    }
  }

}
