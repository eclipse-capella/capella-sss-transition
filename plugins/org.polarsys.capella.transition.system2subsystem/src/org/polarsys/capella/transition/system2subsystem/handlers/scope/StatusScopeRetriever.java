/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
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

package org.polarsys.capella.transition.system2subsystem.handlers.scope;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.transition.common.handlers.scope.IScopeRetriever;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class StatusScopeRetriever implements IScopeRetriever {

  @Override
  public IStatus init(IContext context) {
    return Status.OK_STATUS;
  }

  @Override
  public IStatus dispose(IContext context) {
    return Status.OK_STATUS;
  }

  @Override
  public Collection<? extends EObject> retrieveRelatedElements(EObject element, IContext context) {
    Collection<EObject> result = new LinkedList<EObject>();

    if (element instanceof CapellaElement) {
      result.add(((CapellaElement) element).getStatus());
    }

    return result;
  }

  @Override
  public Collection<? extends EObject> retrieveSharedElements(IContext context) {
    HashSet<EObject> result = new HashSet<EObject>();
    return result;
  }

}
