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
package org.polarsys.capella.transition.system2subsystem.launcher;

import org.polarsys.capella.core.transition.common.context.TransitionContext;
import org.polarsys.capella.core.transition.common.handlers.log.ILogHandler;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.core.transition.common.launcher.TransitionLauncher;
import org.polarsys.capella.core.transition.system.handlers.log.CapellaLogHandler;
import org.polarsys.capella.transition.system2subsystem.constants.Messages;


public class SubSystemLauncher extends TransitionLauncher {

  private final String MAPPING = "org.polarsys.capella.transition.system2subsystem"; //$NON-NLS-1$

  @Override
  protected void initializeLogHandler() {
    ILogHandler handler = new CapellaLogHandler(getReportComponent());
    handler.init(TransitionContext.EMPTY_CONTEXT);
    LogHelper.setInstance(handler);
  }

  @Override
  protected String getMapping() {
    return MAPPING;
  }

  @Override
  protected String getName() {
    return Messages.SubSystemLauncher_Title;
  }

}
