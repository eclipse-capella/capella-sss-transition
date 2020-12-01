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
package org.polarsys.capella.transition.system2subsystem.crossphases.commands;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.polarsys.capella.core.transition.common.launcher.DefaultLauncher;
import org.polarsys.capella.transition.system2subsystem.commands.SubSystemCommand;
import org.polarsys.capella.transition.system2subsystem.crossphases.launcher.HeadlessCrossPhasesLauncher;


public class CrossPhasesCommand extends SubSystemCommand {

  /**
   * @param selection
   * @param progressMonitor
   */
  public CrossPhasesCommand(Collection<?> selection, IProgressMonitor progressMonitor) {
    super(selection, progressMonitor);
  }

  @Override
  protected DefaultLauncher createLauncher() {
    return new HeadlessCrossPhasesLauncher();
  }

}
