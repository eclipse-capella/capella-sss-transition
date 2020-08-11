/*******************************************************************************
 * Copyright (c) 2006, 2020 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.rules.cs;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.polarsys.capella.common.helpers.EObjectExt;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.interaction.InteractionPackage;
import org.polarsys.capella.core.data.la.LaPackage;
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.data.la.LogicalComponentPkg;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentPkg;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.contextscope.IContextScopeHandler;
import org.polarsys.capella.core.transition.common.handlers.options.OptionsHandlerHelper;
import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class ComponentRule extends org.polarsys.capella.core.transition.system.rules.cs.ComponentRule {
  
  @Override
  protected void retrieveGoDeep(EObject source, List<EObject> result, IContext context) {
    super.retrieveGoDeep(source, result, context);
    
    Component element = (Component)source;
    result.addAll(element.getOwnedCommunicationLinks());
    
    IContextScopeHandler handler = ContextScopeHandlerHelper.getInstance(context);
    if (handler.contains(ITransitionConstants.SOURCE_SCOPE, element, context)) {
      handler.addAll(ITransitionConstants.SOURCE_SCOPE, element.getOwnedCommunicationLinks(), context);
    }
  }

  @Override
  protected void retrieveComponentGoDeep(EObject source, List<EObject> result, IContext context) {

    Component element = (Component) source;
    super.retrieveComponentGoDeep(source, result, context);

    result.addAll(element.getRepresentingParts());
    result.addAll(element.getFunctionalAllocations());
    result.addAll(element.getUsedInterfaceLinks());
    result.addAll(element.getImplementedInterfaceLinks());

    IContextScopeHandler handler = ContextScopeHandlerHelper.getInstance(context);

    if (handler.contains(ITransitionConstants.SOURCE_SCOPE, element, context)) {

      // Retrieve state machines of reference components
      String value =
          OptionsHandlerHelper.getInstance(context).getStringValue(context, IOptionsConstants.SYSTEM2SUBSYSTEM_PREFERENCES,
              IOptionsConstants.STATE_MODES_EXPORT, IOptionsConstants.STATE_MODES_DEFAULT_VALUE);

      if (IOptionsConstants.STATE_MODES_ONLY_REFERENCES_VALUE.equals(value)) {
        result.addAll(element.getOwnedStateMachines());
        handler.addAll(ITransitionConstants.SOURCE_SCOPE, element.getOwnedStateMachines(), context);

      } else if (IOptionsConstants.STATE_MODES_HIERARCHICAL_VALUE.equals(value)) {
        result.addAll(element.getOwnedStateMachines());
        handler.addAll(ITransitionConstants.SOURCE_SCOPE, element.getOwnedStateMachines(), context);

        for (Component ancestor : getComponentAncestors(element)) {
          result.addAll(ancestor.getOwnedStateMachines());
          handler.addAll(ITransitionConstants.SOURCE_SCOPE, ancestor.getOwnedStateMachines(), context);
        }
      }

      // Add all instance roles
      if (OptionsHandlerHelper.getInstance(context).getBooleanValue(context, IOptionsConstants.SYSTEM2SUBSYSTEM_PREFERENCES,
          IOptionsConstants.SCENARIO_EXPORT, IOptionsConstants.SCENARIO_EXPORT_DEFAULT_VALUE)) {
        result.addAll(EObjectExt.getReferencers(element, InteractionPackage.Literals.INSTANCE_ROLE__REPRESENTED_INSTANCE));
      }
    }

  }

  @Override
  protected EStructuralFeature getTargetContainementFeature(EObject element, EObject result, EObject container, IContext context) {
    if (container instanceof LogicalComponent) {
      return LaPackage.Literals.LOGICAL_COMPONENT__OWNED_LOGICAL_COMPONENTS;

    } else if (container instanceof PhysicalComponent) {
      return PaPackage.Literals.PHYSICAL_COMPONENT__OWNED_PHYSICAL_COMPONENTS;

    } else if (container instanceof LogicalComponentPkg) {
      return LaPackage.Literals.LOGICAL_COMPONENT_PKG__OWNED_LOGICAL_COMPONENTS;

    } else if (container instanceof PhysicalComponentPkg) {
      return PaPackage.Literals.PHYSICAL_COMPONENT_PKG__OWNED_PHYSICAL_COMPONENTS;
    }

    return super.getTargetContainementFeature(element, result, container, context);
  }

  private Collection<Component> getComponentAncestors(Component component) {
    Collection<Component> result = new HashSet<>();

    for (Part part : component.getRepresentingParts()) {
      for (Part componentAncestor : ComponentExt.getPartAncestors(part)) {
        if (componentAncestor.getAbstractType() instanceof Component) {
          result.add((Component) componentAncestor.getAbstractType());
        }
      }
    }

    return result;

  }

}
