/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.multiphases.rules;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.transition.system2subsystem.crossphases.rules.core.PropertyValueRule;
import org.polarsys.capella.transition.system2subsystem.multiphases.MultiphasesActivator;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class PropertyValueRules {
  public static class ToSA extends PropertyValueRule{

  }
  
  public static class ToLA extends PropertyValueRule {

    @Override
    public IStatus transformRequired(EObject source_p, IContext context_p) {
      return new Status(IStatus.WARNING, MultiphasesActivator.PLUGIN_ID, "SKIPPED!");
    }
  }

  public static class ToPA extends PropertyValueRule {

    @Override
    public IStatus transformRequired(EObject source_p, IContext context_p) {
      return new Status(IStatus.WARNING, MultiphasesActivator.PLUGIN_ID, "SKIPPED!");
    }
  }
}
