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
package org.polarsys.capella.transition.system2subsystem.crossphases.rules.cs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * 
 */
public class AbstractDeploymentLinkRule extends org.polarsys.capella.core.transition.system.rules.cs.AbstractDeploymentLinkRule {

  @Override
  public IStatus transformRequired(EObject source_p, IContext context_p) {
    // In SA, we don't need to transform any DeploymentLink.
    return new Status(IStatus.WARNING, Messages.Activity_Transformation, "DeploymentLink is never transitioned.");
  }

}
