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
package org.polarsys.capella.transition.system2subsystem.interphases.handlers.traceability.config;

import org.polarsys.capella.transition.system2subsystem.handlers.traceability.config.MergeTargetConfiguration;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 *
 */
public class TargetConfiguration extends MergeTargetConfiguration {

  @Override
  protected void initHandlers(IContext fContext_p) {
    addHandler(fContext_p, new TargetReconciliationTraceabilityHandler(getIdentifier(fContext_p)));
    addHandler(fContext_p, new TargetSIDTraceabilityHandler(getIdentifier(fContext_p)));
  }
}
