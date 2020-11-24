/*******************************************************************************
 * Copyright (c) 2018 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests.util;

import org.eclipse.emf.diffmerge.generic.api.scopes.ITreeDataScope;
import org.eclipse.emf.diffmerge.impl.policies.DefaultMatchPolicy;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.core.data.capellacore.Involvement;
import org.polarsys.capella.core.data.capellacore.KeyValue;

public class QualifiedMatchPolicy extends DefaultMatchPolicy {

  @Override
  public boolean keepMatchIDs() {
    return true;
  }

  @Override
  public Object getMatchID(EObject next, ITreeDataScope<EObject> arg1) {
    if (next == null) {
      return "null";
    }

    String result = "[" + next.eClass().getName() + "]";

    if (next.eContainer() != null) {
      result += getMatchID(next.eContainer(), arg1);
    }

    if (!result.isEmpty()) {
      result += "::";
    }

    if (next instanceof AbstractTrace) {
      AbstractTrace at = (AbstractTrace) next;
      result += "*trace*";
      result += getMatchID(at.getSourceElement(), arg1);
      result += "*to*";
      result += getMatchID(at.getTargetElement(), arg1);

    } else if (next instanceof KeyValue) {
      result += "*keyvalue*";
      result += ((KeyValue) next).getKey();

    } else if (next instanceof Involvement) {
      Involvement involvement = (Involvement) next;
      result += "*to*";
      result += getMatchID(involvement.getInvolved(), arg1);

    } else if (next instanceof AbstractNamedElement) {
      AbstractNamedElement named = (AbstractNamedElement) next;
      if (named.getName() != null && named.getName().length() > 0) {
        result += (named.getName());
      } else {
        InternalEObject e = (InternalEObject) next.eContainer();
        result += e.eURIFragmentSegment(next.eContainingFeature(), next);
      }
    }

    return result;
  }

}
