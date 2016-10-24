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
package org.polarsys.capella.transition.system2subsystem.interphases.commands;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.polarsys.capella.core.transition.common.launcher.DefaultLauncher;
import org.polarsys.capella.transition.system2subsystem.commands.SubSystemCommand;
import org.polarsys.capella.transition.system2subsystem.interphases.launcher.HeadlessInterPhasesLauncher;


public class InterPhasesCommand extends SubSystemCommand {

  public InterPhasesCommand(Collection<?> selection, IProgressMonitor progressMonitor) {
    super(selection, progressMonitor);
  }

  @Override
  protected DefaultLauncher createLauncher() {
    return new HeadlessInterPhasesLauncher();
  }
}