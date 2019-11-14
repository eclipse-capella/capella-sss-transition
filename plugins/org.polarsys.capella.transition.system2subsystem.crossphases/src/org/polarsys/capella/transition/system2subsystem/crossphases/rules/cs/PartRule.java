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
package org.polarsys.capella.transition.system2subsystem.crossphases.rules.cs;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.core.data.capellacore.Structure;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.core.transition.common.handlers.selection.ISelectionContext;
import org.polarsys.capella.core.transition.common.handlers.selection.SelectionContextHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.transition.system2subsystem.crossphases.handlers.attachment.CrossPhasesAttachmentHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.selection.ExceptEClassSelectionContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * 
 */
public class PartRule extends org.polarsys.capella.transition.system2subsystem.rules.cs.PartRule {

  @Override
  protected EObject transformDirectElement(EObject element, IContext context) {
    if (element instanceof Component && BlockArchitectureExt.isRootComponent((Component) element)) {
      ISelectionContext sContext =
          SelectionContextHandlerHelper.getHandler(context).getSelectionContext(context, ITransitionConstants.SELECTION_CONTEXT__TRANSFORMATION);

      EObject target = TransformationHandlerHelper.getInstance(context).getBestTracedElement(((Part) element).getAbstractType(), context, sContext);
      Component tComponent = (Component) target;
      if ((tComponent != null) && (tComponent.getRepresentingParts() != null) && (!tComponent.getRepresentingParts().isEmpty())) {
        return tComponent.getRepresentingParts().get(0);
      }
    }
    return super.transformDirectElement(element, context);
  }

  @Override
  public IStatus transformRequired(EObject element, IContext context) {
    EClass target = TransformationHandlerHelper.getInstance(context).getTargetType(((Part) element).getAbstractType(), context);
    Component e = (Component) ((Part) element).getAbstractType();

    if (target instanceof Component && BlockArchitectureExt.isRootComponent((Component) target)) {
      Component d = CrossPhasesAttachmentHelper.getInstance(context).getRelatedComponent(e, context);
      if (d != e) {
        return new Status(IStatus.WARNING, Messages.Activity_Transformation, NLS.bind("Part ''{0}'' is typed by an element transitioned to the System.",
            LogHelper.getInstance().getText(element)));
      }
    }
    return super.transformRequired(element, context);
  }

  @Override
  protected void retrieveGoDeep(EObject source_p, List<EObject> result_p, IContext context_p) {
    super.retrieveGoDeep(source_p, result_p, context_p);

    if (!ContextScopeHandlerHelper.getInstance(context_p).contains(ITransitionConstants.SOURCE_SCOPE, source_p, context_p)) {
      result_p.addAll(((Part) source_p).getDeployingLinks());
    }

  }

  @Override
  protected EObject getDefaultContainer(EObject element, EObject result, IContext context) {
    EObject root = TransformationHandlerHelper.getInstance(context).getLevelElement(element, context);
    BlockArchitecture target =
        (BlockArchitecture) TransformationHandlerHelper.getInstance(context).getBestTracedElement(root, context, CsPackage.Literals.BLOCK_ARCHITECTURE,
            element, result);

    Component component = (Component) ((Part)element).getAbstractType();
    ISelectionContext sContext =
        SelectionContextHandlerHelper.getHandler(context).getSelectionContext(context, ITransitionConstants.SELECTION_CONTEXT__TRANSFORMATION, element,
            result);
    Component componentT = (Component) TransformationHandlerHelper.getInstance(context).getBestTracedElement(component, context, sContext);
    if (BlockArchitectureExt.isRootComponent(componentT) || (element.eContainer() instanceof Structure)) {
      return BlockArchitectureExt.getContext(target);

    } else if (!ComponentExt.isActor(componentT)) {
      return BlockArchitectureExt.getOrCreateSystem(target);
    }

    return BlockArchitectureExt.getContext(target);
  }

  @Override
  protected EObject getBestContainer(EObject element, EObject result, IContext context) {
    Component component = (Component) ((Part)element).getAbstractType();
    if (component instanceof Component && ComponentExt.isComponentRoot((Component) component)) {
      return null;
    }

    EObject container = getSourceContainer(element, result, context);
    EObject bestContainer = null;
    if (container != null) {
      ISelectionContext sContext =
          SelectionContextHandlerHelper.getHandler(context).getSelectionContext(context, ITransitionConstants.SELECTION_CONTEXT__TRANSFORMATION, element,
              result);
      sContext = new ExceptEClassSelectionContext(sContext, CsPackage.Literals.COMPONENT);
      bestContainer = TransformationHandlerHelper.getInstance(context).getBestTracedElement(container, context, sContext);
    }

    return bestContainer;
  }
}
