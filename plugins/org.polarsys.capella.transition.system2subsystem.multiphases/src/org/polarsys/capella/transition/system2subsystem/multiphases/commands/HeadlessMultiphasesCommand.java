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
package org.polarsys.capella.transition.system2subsystem.multiphases.commands;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.transition.common.commands.DefaultCommand;
import org.polarsys.capella.transition.system2subsystem.constants.Messages;
import org.polarsys.capella.transition.system2subsystem.multiphases.launcher.HeadlessMultiphasesLauncher;

public class HeadlessMultiphasesCommand extends DefaultCommand {

  public HeadlessMultiphasesCommand(Collection<?> selection_p) {
    super(selection_p, new NullProgressMonitor());
  }

  @Override
  protected Collection<Object> retrieveRelatedElements(Object rootElement_p) {
    Object rootElement = rootElement_p;
    if (rootElement instanceof Part) {
      rootElement = ((Part) rootElement).getAbstractType();
    }
    return Collections.singleton(rootElement);
  }
  
  @Override
  public String getName() {
    return Messages.SubSystemLauncher_Title;
  }
  
  @Override
  protected void performTransformation(Collection<?> elements_p) {
    new HeadlessMultiphasesLauncher(getParameters()).launch(elements_p, getProgressMonitor());
  }

}
