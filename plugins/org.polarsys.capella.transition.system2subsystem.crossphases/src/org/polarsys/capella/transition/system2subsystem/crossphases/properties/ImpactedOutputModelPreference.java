/*******************************************************************************
 * Copyright (c) 2025, 2026 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.crossphases.properties;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.common.flexibility.properties.schema.IPropertyContext;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.model.handler.helpers.RepresentationHelper;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.context.TransitionContext;
import org.polarsys.capella.core.transition.system.preferences.OutputModelPreference;
import org.polarsys.capella.transition.system2subsystem.crossphases.constants.Messages;

/**
 * Field to select target model.
 * <p>
 * Target Model is analysis. If selection exists but is not the same, a message is displayed.
 * </p>
 * 
 * @author nicolas.peransin@obeo.fr
 */
public class ImpactedOutputModelPreference extends OutputModelPreference {

  @Override
  public IStatus validate(Object newValue, IPropertyContext context) {
    IStatus result = super.validate(newValue, context);
    if (!result.isOK()) {
      return result;
    }
    
    TransitionContext tContext = (TransitionContext) context.getSource();  
    Set<String> ids = getSourceIds(tContext);
    
    Set<String> existingIds = getPreviousIds((String) newValue);
    if (existingIds.isEmpty() || ids.equals(existingIds)) {
      return result;
    }
    
    return createSourceChangedStatus(existingIds, tContext);
  }
  
  private IStatus createSourceChangedStatus(Set<String> existingIds, TransitionContext context) {
    EObject root = (EObject) context.get(ITransitionConstants.TRANSITION_SOURCE_ROOT);
    Resource modelRes = root.eResource();
    
    String previousNames = existingIds.stream()
      .map(id -> toElementLabel(id, modelRes))
      .collect(Collectors.joining(", ")); //$NON-NLS-1$

    return new Status(IStatus.WARNING, getId(), Messages.ImpactedOutputModelPreference_SelectionChangeMessage 
        + "\n\t" //$NON-NLS-1$
        + previousNames);
  }
  
  private static Set<String> getSourceIds(TransitionContext context) {
      List<?> sources = (List<?>) context.get(ITransitionConstants.TRANSITION_SOURCES);
      
    return sources.stream()
      .filter(Component.class::isInstance)
      .map(Component.class::cast)
      .map(Component::getId)
      .collect(Collectors.toSet());
  }
  
  private static String toElementLabel(String id, Resource res) {
    EObject element = RepresentationHelper.getRepresentationDescriptorOrSemanticObject(res.getResourceSet(), id);
    if (element == null) {
      return NLS.bind(Messages.ImpactedOutputModelPreference_MissingElement, id);
    }
    
    if (element instanceof AbstractNamedElement) {
      String name = ((AbstractNamedElement) element).getName();
      
      if (name == null || name.isEmpty()) {
        name = "\"\""; //$NON-NLS-1$
      }
      return name; 
    }
    
    return NLS.bind(Messages.ImpactedOutputModelPreference_AnonymousElement, element.eClass().getName());
  }
  
  private static Set<String> getPreviousIds(String path) {
    Set<String> result = Optional.ofNullable(getProjectFromPath(path))
      .map(SystemEngineeringExt::getSystemEngineering)
      .map(SystemEngineeringExt::getSystemAnalysis)
      .map(SystemAnalysis::getSystem)
      // As in org.polarsys.capella.transition.system2subsystem.handlers.traceability.SIDTraceabilityHandler
      .map(Component::getSid)
      .map(values -> new HashSet<String>(Arrays.asList(values.split(";")))) //$NON-NLS-1$
      .orElse(new HashSet<String>());
    
    result.removeIf(String::isEmpty);
    return result;
  }


  private static Project getProjectFromPath(String path) {
    // Open model as in:
    // org.polarsys.capella.transition.system2subsystem.activities.InitializeTransitionActivity.initializeTarget(IContext, ActivityParameters)
    URI modelUri = URI.createPlatformResourceURI(path, true);
    
    ResourceSet set = new ResourceSetImpl();
    Resource res = set.getResource(modelUri, true);
    if (!res.getContents().isEmpty()) {
      EObject root = res.getContents().get(0);
      if (root instanceof Project) {
        return (Project) root;
      }
    }
    
    return null;
  }
  
}
