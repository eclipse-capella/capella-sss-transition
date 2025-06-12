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
package org.polarsys.capella.transition.system2subsystem.handlers.attachment;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph.Element;

/**
 * A more readable Map<Element, Collection<Path>
 */
public class Paths {
  
  private Map<Element, Collection<Path>> paths = new HashMap<>();
  
  public Collection<Path> getPaths(Element element) {
    if (!paths.containsKey(element)) {
      paths.put(element, new LinkedHashSet<>());
    }
    return paths.get(element);
  }

  public boolean containsKey(Element node) {
    return paths.containsKey(node);
  }
  
  public void addPath(Element element, Path path) {
    getPaths(element).add(path);
  }

}