/*******************************************************************************
 * Copyright (c) 2006, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.activities;

import org.polarsys.capella.core.transition.common.handlers.IHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.CompoundTraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.config.ITraceabilityConfiguration;
import org.polarsys.capella.transition.system2subsystem.handlers.traceability.config.TransformationConfiguration;

/**
 *
 */
public class InitializeTransformationActivity extends org.polarsys.capella.core.transition.system.activities.InitializeTransformationActivity {

  public static final String ID = "org.polarsys.capella.transition.system2subsystem.activities.InitializeTransformationActivity"; //$NON-NLS-1$

  /**
   * {@inheritDoc}
   */
  @Override
  protected IHandler createDefaultTraceabilityTransformationHandler() {
    ITraceabilityConfiguration configuration = new TransformationConfiguration();
    return new CompoundTraceabilityHandler(configuration);
  }

}
