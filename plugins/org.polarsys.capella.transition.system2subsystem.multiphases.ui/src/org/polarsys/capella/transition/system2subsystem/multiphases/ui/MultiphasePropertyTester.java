/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.multiphases.ui;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.ui.actions.ModelAdaptation;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;
import org.polarsys.capella.core.model.helpers.ComponentExt;

public class MultiphasePropertyTester extends PropertyTester {

  @Override
  public boolean test(Object object_p, String propertyName_p, Object[] args, Object testedValue_p) {

    if (propertyName_p.equals("transitionMode")) { //$NON-NLS-1$ 
      List<EObject> elements = (List) ModelAdaptation.adaptToCapellaElements(object_p);
      if ((!elements.isEmpty()) && (testedValue_p instanceof String)) {
        String value = (String) testedValue_p;

        if (value.equals("transitionMultiPhases")) { //$NON-NLS-1$

          for (EObject element : elements) {
            if (element instanceof Part) {
              element = ((Part) element).getType();
            }
            if ((element instanceof PhysicalComponent) && (((PhysicalComponent) element).getNature() == PhysicalComponentNature.NODE) && !ComponentExt.isActor(element)) {
              return true;
            }
          }
        }
      }
    }

    return false;

  }

}
