/*******************************************************************************
 * Copyright (c) 2025 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.crossphases.constants;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.ComponentPkg;
import org.polarsys.capella.core.data.cs.CsFactory;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.data.ctx.SystemComponent;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * Common methods for Crossphase transformations.
 * 
 * @author nicolas.peransin@obeo.fr
 */
/**
 * @author nperansin
 *
 */
public class Crossphases {

  /**
   * Evaluates if an element is in the source scope.
   * 
   * @param element to evaluate
   * @param context of evaluation
   * @return true if in scope
   */
  public static boolean isSourceScope(AbstractType element, IContext context) {
    return ContextScopeHandlerHelper.getInstance(context).contains(ITransitionConstants.SOURCE_SCOPE, element, context);
  }
  
  /**
   * Evaluates if an part is in the source scope.
   * <p>
   * Only part type can be in the source scope.
   * </p>
   * 
   * @param element to evaluate
   * @param context of evaluation
   * @return true if in scope
   */
  public static boolean isSourceScope(Part element, IContext context) {
    return isSourceScope(element.getAbstractType(), context);
  }
  
  
  /**
   * Gets or creates the system of SystemAnalysis.
   * 
   * @param element of model
   * @param context of execution
   * @return a {@link SystemAnalysis}
   */
  public static Component obtainSystemAnalysisSystem(EObject element, IContext context) {
    EObject root = TransformationHandlerHelper.getInstance(context).getLevelElement(element, context);

    BlockArchitecture target = (BlockArchitecture) TransformationHandlerHelper.getInstance(context)
        .getBestTracedElement(root, context, CsPackage.Literals.BLOCK_ARCHITECTURE);
    if (!(target instanceof SystemAnalysis)) {
      return null;
    }
    SystemAnalysis analysis = (SystemAnalysis) target;
    return BlockArchitectureExt.getOrCreateSystem(analysis);
  }
  
  /**
   * Gets or creates the part of the System of SystemAnalysis.
   * 
   * @param element of model
   * @param context of execution
   * @return a {@link SystemAnalysis}
   */
  public static Part obtainSystemAnalysisSystemPart(EObject element, IContext context) {
    Component systemComponent = obtainSystemAnalysisSystem(element, context);
    
    if (systemComponent == null) {
      return null;
    }
    
    // Cast is safe by design of BlockArchitectureExt#getOrCreateSystem
    ComponentPkg container = (ComponentPkg) systemComponent.eContainer();
    Part result = container.getOwnedParts().stream()
      .filter(part -> part.getAbstractType() == systemComponent)
      .findFirst()
      .orElse(null);
    if (result == null) {
      result = CsFactory.eINSTANCE.createPart();
      result.setAbstractType(systemComponent);
      result.setName(systemComponent.getName());
      container.getOwnedParts().add(result);
    }
    return result;
  }

  public static Set<String> getSourceScopeIds(IContext context) {
    return ContextScopeHandlerHelper.getInstance(context).getCollection(ITransitionConstants.SOURCE_SCOPE, context)
      .stream()
      .filter(Component.class::isInstance)
      .map(Component.class::cast)
      .map(Component::getId)
      .collect(Collectors.toSet());
  }
  
  /**
   * Evaluates if a element is a deprecated element of the target.
   * <p>
   * A deprecated if element that was a system actor and is now a part of the system.
   * </p>
   * 
   * @param element to evaluate
   * @param context of evaluation
   * @return true if deprecated
   */
  public static boolean isDeprecatedTarget(EObject element, IContext context) {
    if (element instanceof Part) {
      return isDeprecatedTarget(((Part) element).getAbstractType(), context);
    }
    if (!(element instanceof SystemComponent)) {
      // its content is also deprecated. 
      Component container = ComponentExt.getRootComponent(element);     
      return container != null && isDeprecatedTarget(container, context);
    }

    SystemComponent component = (SystemComponent) element;
    return component.isActor() && getSourceScopeIds(context).contains(component.getSid());
  }
  
}
