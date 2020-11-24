/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.handlers.filter;

import org.eclipse.emf.diffmerge.diffdata.EElementRelativePresence;
import org.eclipse.emf.diffmerge.generic.api.IMatch;
import org.eclipse.emf.diffmerge.generic.api.Role;
import org.eclipse.emf.diffmerge.generic.api.diff.IDifference;
import org.eclipse.emf.diffmerge.generic.api.diff.IElementPresence;
import org.eclipse.emf.diffmerge.generic.api.diff.IPresenceDifference;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.helpers.EcoreUtil2;
import org.polarsys.capella.core.transition.common.handlers.merge.CategoryFilter;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class UpdateOfCategoryFilter extends CategoryFilter {

  EClass source;

  public UpdateOfCategoryFilter(EClass source, IContext context) {
    super(context, UpdateOfCategoryFilter.class.getSimpleName(), null);
    setInFocusMode(false);
    setVisible(false);
    setActive(true);
    this.source = source;
  }

  @Override
  public boolean covers(IDifference difference) {
    return isUpdateOf(difference, source);
  }

  protected boolean isUpdateOf(IDifference diff, EClass clazz) {
    // any update of clazz is disabled
    if (diff instanceof EElementRelativePresence) {
      EElementRelativePresence presence = (EElementRelativePresence) diff;
      EObject source = presence.getElementMatch().get(Role.REFERENCE);

      if (source != null) {
        if (clazz.isInstance(source)) {
          if ((presence instanceof IPresenceDifference) && (presence.getElementMatch().get(Role.TARGET) != null)) {
            return true;
          }
        } else if (EcoreUtil2.isContainedBy(source, clazz)) {
          EObject container = EcoreUtil2.getFirstContainer(source, clazz);
          if ((presence instanceof IPresenceDifference) && (presence.getElementMatch().get(Role.TARGET) != null)) {
            return true;
          } else if (presence instanceof IElementPresence) {
            IElementPresence element = (IElementPresence) presence;
            IMatch match = getMatch(element, container, Role.REFERENCE);
            if (match != null) {
              return match.get(Role.TARGET) != null;
            }
          }
        }
      }
    }

    return false;
  }

  protected IMatch getMatch(IElementPresence presence, EObject object, Role role) {
    IMatch match = presence.getOwnerMatch();
    if (match.get(role) == object) {
      return match;
    }
    if (match.getElementPresenceDifference() != null) {
      return getMatch(match.getElementPresenceDifference(), object, role);
    }
    return null;
  }

}
