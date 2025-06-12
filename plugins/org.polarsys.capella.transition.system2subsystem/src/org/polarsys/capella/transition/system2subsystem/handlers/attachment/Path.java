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

import java.util.Objects;
import java.util.stream.Stream;

import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph.Element;

/**
 * Defines a Path in a graph.
 * Main benefits is to be composable with another Path and hashCode is computed once as the Path is not changeable.
 */
public class Path {
  final Element element;
  final Path next;
  int hash = -1;
  
  public Path(Element element) {
    this(element, null);
  }

  public Path(Element element, Path next) {
    this.element = element;
    this.next = next;
  }

  public Stream<Element> getAll() {
    if (next == null) return Stream.of(element);
    return Stream.concat(Stream.of(element), next.getAll());
  }
  
  public String toString() {
    String id = GraphHelper.getInvolvment(element).getInvolved().getLabel()+"@"+GraphHelper.getInvolvment(element).getInvolved().getId().substring(0, 5);
    return id+","+next.toString();
  }
  
  @Override
  public int hashCode() {
    if (hash == -1) hash = Objects.hash(element, next);
    return hash;
  }
  
  @Override
  public boolean equals(Object obj) {
    return ((Path)obj).element.equals(this.element) && ((Path)obj).next.equals(this.next);
  }

  public static Path of(Element element) {
    return new Path(element);
  }
  
  public static Path combine(Element element, Path next) {
    return new Path(element, next);
  }
  
}
