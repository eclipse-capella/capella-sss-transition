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
package org.polarsys.capella.transition.system2subsystem.interphases.activities;

import org.polarsys.capella.core.transition.common.handlers.IHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.CompoundTraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.config.ITraceabilityConfiguration;
import org.polarsys.capella.transition.system2subsystem.interphases.handlers.traceability.config.TargetConfiguration;

/**
 * 
 */
public class InitializeTransitionActivity extends org.polarsys.capella.transition.system2subsystem.activities.InitializeTransitionActivity {

  public static final String ID = "org.polarsys.capella.transition.system2subsystem.interphases.activities.InitializeTransitionActivity"; //$NON-NLS-1$

  @Override
  protected String getDefaultOptionsScope() {
    return "capella.core.transition.system2subsystem"; //$NON-NLS-1$
  }

  @Override
  protected IHandler createDefaultTraceabilityTargetHandler() {
    ITraceabilityConfiguration configuration = new TargetConfiguration();
    return new CompoundTraceabilityHandler(configuration);
  }

}
