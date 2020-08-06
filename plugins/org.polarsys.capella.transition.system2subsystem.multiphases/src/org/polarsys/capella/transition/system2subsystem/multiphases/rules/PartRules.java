/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.multiphases.rules;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.selection.ISelectionContext;
import org.polarsys.capella.core.transition.common.handlers.selection.SelectionContextHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.transition.system2subsystem.crossphases.rules.cs.PartRule;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class PartRules {

  public static class ToSA extends PartRule {

    @Override
    protected EObject transformDirectElement(EObject element_p, IContext context_p) {
      ISelectionContext sContext =
          SelectionContextHandlerHelper.getHandler(context_p).getSelectionContext(context_p, ITransitionConstants.SELECTION_CONTEXT__TRANSFORMATION);
      EObject target = TransformationHandlerHelper.getInstance(context_p).getBestTracedElement(((Part) element_p).getAbstractType(), context_p, sContext);
      Component tComponent = (Component) target;
      if ((tComponent != null) && (tComponent.getRepresentingParts() != null) && (tComponent.getRepresentingParts().size() > 0)) {
        return tComponent.getRepresentingParts().get(0);
      }
      return super.transformDirectElement(element_p, context_p);
    }
  }

  public static class ToLA extends PartRule {

    @Override
    protected EObject transformDirectElement(EObject element_p, IContext context_p) {
      ISelectionContext sContext =
          SelectionContextHandlerHelper.getHandler(context_p).getSelectionContext(context_p, ITransitionConstants.SELECTION_CONTEXT__TRANSFORMATION);
      EObject target = TransformationHandlerHelper.getInstance(context_p).getBestTracedElement(((Part) element_p).getAbstractType(), context_p, sContext);
      Component tComponent = (Component) target;
      if ((tComponent != null) && (tComponent.getRepresentingParts() != null) && (tComponent.getRepresentingParts().size() > 0)) {
        return tComponent.getRepresentingParts().get(0);
      }
      return super.transformDirectElement(element_p, context_p);
    }

  }

  public static class ToPA extends PartRule {

    @Override
    protected EObject transformDirectElement(EObject element_p, IContext context_p) {
      ISelectionContext sContext =
          SelectionContextHandlerHelper.getHandler(context_p).getSelectionContext(context_p, ITransitionConstants.SELECTION_CONTEXT__TRANSFORMATION);
      EObject target = TransformationHandlerHelper.getInstance(context_p).getBestTracedElement(((Part) element_p).getAbstractType(), context_p, sContext);
      Component tComponent = (Component) target;
      if ((tComponent != null) && (tComponent.getRepresentingParts() != null) && (tComponent.getRepresentingParts().size() > 0)) {
        return tComponent.getRepresentingParts().get(0);
      }
      return super.transformDirectElement(element_p, context_p);
    }
    
    @Override
    protected EObject getBestContainer(EObject element, EObject result, IContext context) {
      EObject bestContainer = super.getBestContainer(element, result, context);
      // In case selected PC is contained in another PC (which is not traced) => Container should be the Physical System
      if (bestContainer == null) {
        Component component = (Component) ((Part) element).getAbstractType();
        EObject root = TransformationHandlerHelper.getInstance(context).getLevelElement(element, context);
        BlockArchitecture target = (BlockArchitecture) TransformationHandlerHelper.getInstance(context)
            .getBestTracedElement(root, context, CsPackage.Literals.BLOCK_ARCHITECTURE, element, result);
        ISelectionContext sContext = SelectionContextHandlerHelper.getHandler(context).getSelectionContext(context,
            ITransitionConstants.SELECTION_CONTEXT__TRANSFORMATION, element, result);
        Component componentT = (Component) TransformationHandlerHelper.getInstance(context)
            .getBestTracedElement(component, context, sContext);
        if (!ComponentExt.isActor(componentT) && BlockArchitectureExt.getFirstComponent(target, false) != null) {
          bestContainer = BlockArchitectureExt.getFirstComponent(target, false);
        }
      }
      return bestContainer;
    }

  }
}
