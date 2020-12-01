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
package org.polarsys.capella.transition.system2subsystem.crossphases.activities;

import org.polarsys.capella.core.transition.common.handlers.IHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.CompoundTraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.config.ITraceabilityConfiguration;
import org.polarsys.capella.transition.system2subsystem.crossphases.handlers.traceability.config.SourceConfiguration;
import org.polarsys.capella.transition.system2subsystem.crossphases.handlers.traceability.config.TargetConfiguration;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;


/**
 * 
 */
public class InitializeDiffMergeActivity extends org.polarsys.capella.transition.system2subsystem.activities.InitializeDiffMergeActivity {

  public static final String ID = "org.polarsys.capella.transition.system2subsystem.crossphases.activities.InitializeDiffMergeActivity"; //$NON-NLS-1$

  @Override
  protected IHandler createDefaultTraceabilitySourceHandler(IContext context_p) {
    ITraceabilityConfiguration configuration = new SourceConfiguration();
    return new CompoundTraceabilityHandler(configuration);
  }

  @Override
  protected IHandler createDefaultTraceabilityTargetHandler(IContext context_p) {
    ITraceabilityConfiguration configuration = new TargetConfiguration();
    return new CompoundTraceabilityHandler(configuration);
  }

}
