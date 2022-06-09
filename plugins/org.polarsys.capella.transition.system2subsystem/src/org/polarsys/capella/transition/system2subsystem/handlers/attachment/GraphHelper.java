/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;
import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph;
import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph.Edge;
import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph.Element;
import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph.Vertex;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class GraphHelper {

  /**
   * From a fci, returns the list of all paths towards expected elements returned by isTheOne
   * 
   * @param isTheOne:
   *          a method allowing to detect if the current element is the expected one.
   * 
   * @param getNexts: from an element, a method retrieving the connected ones
   * 
   * @return all the paths from fci towards the elements returned by isTheOne. paths are containing both source fci at first of
   *         the path and the expected one at the end.
   */
  public static <T> Collection<LinkedList<T>> getPathsTowards(T fci, Function<T, Boolean> isTheOne,
      IContext context_p, Function<T, Collection<T>> getNexts) {
    LinkedList<T> path = new LinkedList<>();
    path.add(fci);
    return getPathsTowards(fci, isTheOne, context_p, path, getNexts);
  }

  /**
   * From a fci, returns the list of all paths towards expected elements returned by isTheOne
   * 
   * @param path
   *          the current path computed.
   * @implNote at first, we add the current fci in it to allow cycle detection
   */
  protected static <T> Collection<LinkedList<T>> getPathsTowards(T fci, Function<T, Boolean> isTheOne,
      IContext context_p, LinkedList<T> path, Function<T, Collection<T>> getNexts) {
    Collection<LinkedList<T>> result = new ArrayList<LinkedList<T>>();

    for (T next : getNexts.apply(fci)) {
      if (path.contains(next)) {
        path.clear();
      } else if (isTheOne.apply(next)) {
        path.add(next);
        result.add(path);
      } else {
        LinkedList<T> newPath = new LinkedList<>();
        newPath.addAll(path);
        newPath.add(next);
        result.addAll(getPathsTowards(next, isTheOne, context_p, newPath, getNexts));
      }
    }
    return result;
  }


  public static FunctionalChainInvolvement getInvolvment(Element e) {
    if (e instanceof Edge) {
      return ((Edge) e).getLink();
    }
    return ((Vertex) e).getFunction();
  }
  
  public static Collection<Element> getPrevious(Element e) {
    if (e instanceof Edge) {
      return Arrays.asList((Element)((Edge)e).getSource());
    }
    return (Collection)((Vertex)e).getIncomingEdges();
  }
  
  public static Collection<Element> getNexts(Element e) {
    if (e instanceof Edge) {
      return Arrays.asList((Element)((Edge)e).getTarget());
    }
    return (Collection)((Vertex)e).getOutgoingEdges();
  }
  
  public static Collection<Vertex> getVertices(InvolvementHierarchyGraph graph, FunctionalChainInvolvement fci) {
    return graph.getVertices().keySet().stream().filter(v -> v.getFunction() == fci)
        .map(v -> graph.getVertices().get(v)).collect(Collectors.toList());
  }


}
