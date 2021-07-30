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
package org.polarsys.capella.transition.system2subsystem.handlers.filter;

import org.eclipse.emf.diffmerge.generic.api.IMatch;
import org.eclipse.emf.diffmerge.generic.api.Role;
import org.eclipse.emf.diffmerge.generic.api.diff.IDifference;
import org.eclipse.emf.diffmerge.generic.api.diff.IElementPresence;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementFunction;
import org.polarsys.capella.core.transition.common.handlers.merge.CategoryFilter;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class InvalidInvolvementLinks extends CategoryFilter {

  public InvalidInvolvementLinks(IContext context) {
    super(context, InvalidInvolvementLinks.class.getSimpleName(), null);
    setInFocusMode(false);
    setVisible(false);
    setActive(true);
  }

  @Override
  public boolean covers(IDifference difference) {
    if (difference instanceof IElementPresence) {
      IElementPresence presence = (IElementPresence) difference;
      EObject source = getInvolvment(presence);
      if (source != null) {
        FunctionalChainInvolvement fci = (FunctionalChainInvolvement) source;
        return fci.getNextFunctionalChainInvolvements().size() == 0
            && fci.getPreviousFunctionalChainInvolvements().size() == 0;
      }
    }
    return false;
  }

  private EObject getInvolvment(IElementPresence presence) {
    Object source = presence.getElementMatch().get(Role.REFERENCE);
    if (source instanceof FunctionalChainInvolvementFunction) {
      return (EObject) source;
    }
    IMatch match = presence.getOwnerMatch();
    if (match.getElementPresenceDifference() != null) {
      return getInvolvment(match.getElementPresenceDifference());
    }
    return null;
  }
}
