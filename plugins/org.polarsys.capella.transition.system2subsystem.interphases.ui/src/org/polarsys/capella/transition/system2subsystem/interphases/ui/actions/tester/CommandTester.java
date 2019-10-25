/*******************************************************************************
 * Copyright (c) 2006, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.interphases.ui.actions.tester;

import java.util.List;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.common.ui.actions.ModelAdaptation;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.ctx.SystemComponent;
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.model.helpers.ComponentExt;

public class CommandTester extends PropertyTester {

  /**
   * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
   */
  public boolean test(Object object, String propertyName, Object[] params, Object testedValue) {
    if (propertyName.equals("transitionMode")) { //$NON-NLS-1$ 
      List<EObject> elements = (List) ModelAdaptation.adaptToCapellaElements(object);
      if ((!elements.isEmpty()) && (testedValue instanceof String)) {
        String value = (String) testedValue;

        if (value.equals("transitionInterPhases")) { //$NON-NLS-1$
          for (EObject element : elements) {
            AbstractType type = null;
            if (element instanceof Component) {
              type = (Component) element;
            } else if (element instanceof Part) {
              type = ((Part) element).getAbstractType();
            }
            return (type != null) && ((type instanceof SystemComponent) || (type instanceof LogicalComponent)
                || (type instanceof PhysicalComponent)) && !ComponentExt.isActor(type);
          }
        }
      }
    }

    return false;
  }
}
