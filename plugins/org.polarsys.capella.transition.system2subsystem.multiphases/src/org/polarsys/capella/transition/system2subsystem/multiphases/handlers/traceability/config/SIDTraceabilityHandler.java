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
package org.polarsys.capella.transition.system2subsystem.multiphases.handlers.traceability.config;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.system.helpers.ContextHelper;
import org.polarsys.capella.transition.system2subsystem.multiphases.MultiphasesContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class SIDTraceabilityHandler extends org.polarsys.capella.transition.system2subsystem.handlers.traceability.SIDTraceabilityHandler {

  public SIDTraceabilityHandler(String identifier_p) {
    super(identifier_p);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeRootMappings(IContext context_p) {
    super.initializeRootMappings(context_p);
    addMappings(ContextHelper.getSourceProject(context_p), ContextHelper.getTransformedProject(context_p), context_p);
    addMappings(ContextHelper.getSourceEngineering(context_p), ((MultiphasesContext)context_p).getTempSystemEngineering(), context_p);
  }

}
