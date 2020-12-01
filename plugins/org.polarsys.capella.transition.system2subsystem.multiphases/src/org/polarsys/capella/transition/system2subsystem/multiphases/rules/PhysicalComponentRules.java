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
package org.polarsys.capella.transition.system2subsystem.multiphases.rules;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.la.LaPackage;
import org.polarsys.capella.core.data.la.LogicalArchitecture;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.core.transition.common.handlers.selection.EClassSelectionContext;
import org.polarsys.capella.core.transition.common.handlers.selection.ISelectionContext;
import org.polarsys.capella.core.transition.common.handlers.selection.SelectionContextHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.transition.system2subsystem.crossphases.rules.pa.PhysicalComponentRule;
import org.polarsys.capella.transition.system2subsystem.multiphases.MultiphasesContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class PhysicalComponentRules {

  public static class ToSA extends PhysicalComponentRule {

  }

  public static class ToLA extends PhysicalComponentRule {

    @Override
    protected EObject getBestContainer(EObject element, EObject result, IContext context) {
      if (!ContextScopeHandlerHelper.getInstance(context).contains(ITransitionConstants.SOURCE_SCOPE, element, context)) {
        return null;
      }

      if ((element instanceof PhysicalComponent) && ((PhysicalComponent) element).getDeployingPhysicalComponents().isEmpty()) {
        EObject sourceContainer = element.eContainer();
        ISelectionContext sContext =
            new EClassSelectionContext(SelectionContextHandlerHelper.getHandler(context).getSelectionContext(context,
                ITransitionConstants.SELECTION_CONTEXT__TRANSFORMATION, element, result), LaPackage.Literals.LOGICAL_COMPONENT);
        return TransformationHandlerHelper.getInstance(context).getBestTracedElement(sourceContainer, context, sContext);
      }

      MultiphasesContext multiphasesContext = (MultiphasesContext) context;
      SystemEngineering eng = multiphasesContext.getTempSystemEngineering();
      LogicalArchitecture la = eng.getContainedLogicalArchitectures().get(0);
      return BlockArchitectureExt.getOrCreateSystem(la);
    }

    @Override
    protected EObject transformDirectElement(EObject element_p, IContext context_p) {
      PhysicalComponent element = (PhysicalComponent) element_p;
      MultiphasesContext context = (MultiphasesContext) context_p;

      // node pcs in the selection and their children are mapped to the target root logical component
      if (element.getNature() == PhysicalComponentNature.NODE) {
        EObject tmp = element;
        while (tmp instanceof PhysicalComponent) {
          if (context.getSelectedPhysicalComponents().contains(tmp)) {
            return context.getTempRootComponent();
          }
          tmp = tmp.eContainer();
        }
      }

      EObject result = EcoreUtil.create(getTargetType(element_p, context_p));
      if ((element_p instanceof AbstractNamedElement) && (result instanceof AbstractNamedElement)) {
        ((AbstractNamedElement) result).setName(((AbstractNamedElement) element_p).getName());
      }
      return result;
    }
    
    @Override
    public EClass getTargetType(EObject element, IContext context) {
      return LaPackage.Literals.LOGICAL_COMPONENT;
    }

    @Override
    public IStatus transformRequired(EObject source_p, IContext context_p) {

      /*
       * Is it in the reference scope? (in LA, sub components in reference scope are transformed, not merged into a parent component)
       */
      if (ContextScopeHandlerHelper.getInstance(context_p).contains(ITransitionConstants.SOURCE_SCOPE, source_p, context_p)) {
        return Status.OK_STATUS;
      }

      IStatus result = super.transformRequired(source_p, context_p);

      return result;
    }

  }

  public static class ToPA extends PhysicalComponentRule {

    /**
     * @param element
     * @param result
     * @param context
     * @return
     */
    @Override
    protected EObject getBestContainer(EObject element, EObject result, IContext context) {
      if (!ContextScopeHandlerHelper.getInstance(context).contains(ITransitionConstants.SOURCE_SCOPE, element, context)) {
        return null;
      }

      EObject bestContainer = null;
      EObject container = getSourceContainer(element, result, context);

      ISelectionContext sContext =
          new EClassSelectionContext(SelectionContextHandlerHelper.getHandler(context).getSelectionContext(context,
              ITransitionConstants.SELECTION_CONTEXT__TRANSFORMATION, element, result), PaPackage.Literals.PHYSICAL_COMPONENT);
      if (container != null) {
        bestContainer = TransformationHandlerHelper.getInstance(context).getBestTracedElement(container, context, sContext);
      }

      // Case selected PC is contained in another PC (which is not traced) => Container should be the Physical System
      if (bestContainer == null) {
        Component component = (Component) element;
        EObject root = TransformationHandlerHelper.getInstance(context).getLevelElement(element, context);
        BlockArchitecture target = (BlockArchitecture) TransformationHandlerHelper.getInstance(context)
            .getBestTracedElement(root, context, CsPackage.Literals.BLOCK_ARCHITECTURE, element, result);
        Component componentT = (Component) TransformationHandlerHelper.getInstance(context)
            .getBestTracedElement(component, context, sContext);
        if (!ComponentExt.isActor(componentT) && BlockArchitectureExt.getFirstComponent(target, false) != null) {
          bestContainer = BlockArchitectureExt.getFirstComponent(target, false);
        }
      }
      return bestContainer;
    }

    @Override
    public EClass getTargetType(EObject element, IContext context) {
      return PaPackage.Literals.PHYSICAL_COMPONENT;
    }

    @Override
    public IStatus transformRequired(EObject source_p, IContext context_p) {

      IStatus result = super.transformRequired(source_p, context_p);

      if (source_p instanceof PhysicalComponent) {
        if (((PhysicalComponent) source_p).getNature() == PhysicalComponentNature.BEHAVIOR) {
          return new Status(IStatus.WARNING, Messages.Activity_Transformation, NLS.bind(
              "Component ''{0}'' behavior ''{1}'' is not transitioned in the Physical Layer", LogHelper.getInstance().getText(source_p)));
        }
      }

      /*
       * Is it in the reference scope? (in LA, sub components in reference scope are transformed, not merged into a parent component)
       */
      if (ContextScopeHandlerHelper.getInstance(context_p).contains(ITransitionConstants.SOURCE_SCOPE, source_p, context_p)) {
        return Status.OK_STATUS;
      }

      return result;
    }

  }

}
