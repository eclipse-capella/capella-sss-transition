/*******************************************************************************
 * Copyright (c) 2019 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.context;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.polarsys.capella.common.libraries.LibraryReference;
import org.polarsys.capella.common.libraries.ModelInformation;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.transition.system.helpers.ContextHelper;
import org.polarsys.kitalpha.emde.model.ElementExtension;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * A temporary class and to be removed in newer Capella release.
 * @deprecated use org.polarsys.capella.core.transition.system.helpers.
 */
@Deprecated
public class SubSystemContextHelper {

  public static ModelInformation getSourceModelInformation(IContext context) {
    return getModelInformation(ContextHelper.getSourceProject(context));
  }

  public static ModelInformation getTransformedModelInformation(IContext context) {
    return getModelInformation(ContextHelper.getTransformedProject(context));
  }

  public static ModelInformation getTargetModelInformation(IContext context) {
    return getModelInformation(ContextHelper.getTargetProject(context));
  }
  
  public static Set<Resource> getLibraryResources(Project project) {
    return project.getOwnedExtensions().stream().filter(ModelInformation.class::isInstance)
        .map(ModelInformation.class::cast).flatMap(m -> m.getOwnedReferences().stream())
        .map(LibraryReference::getLibrary).map(EObject::eContainer).map(EObject::eResource).collect(Collectors.toSet());
  }

  public static Set<EObject> getLibraryRoots(Set<Resource> libraryResources) {
    return libraryResources.stream().map(r -> r.getContents().get(0)).collect(Collectors.toSet());
  }
  
  private static ModelInformation getModelInformation(Project project) {
    if (project != null) {
      for (ElementExtension elt : project.getOwnedExtensions()) {
        if (elt instanceof ModelInformation) {
          return (ModelInformation) elt;
        }
      }
    }
    return null;
  }
}
