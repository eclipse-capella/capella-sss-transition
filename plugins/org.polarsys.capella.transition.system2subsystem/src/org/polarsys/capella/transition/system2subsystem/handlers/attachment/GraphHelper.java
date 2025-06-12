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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;
import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph;
import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph.Edge;
import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph.Element;
import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph.Vertex;

public class GraphHelper {

  /**
   * From a fci, returns the list of all paths towards expected elements returned by isTheOne
   * 
   * @param path
   *          the current path computed.
   * @implNote at first, we add the current fci in it to allow cycle detection
   */
  public static Collection<Path> getShortestPathTowards(InvolvementHierarchyGraph graph, FunctionalChainInvolvement fci,
      FunctionalChainInvolvement expected) {
    Collection<Element> nodes = getVertices(graph, fci);
    Collection<Element> nextValids = getNextValids(nodes, n -> getInvolvment(n).equals(expected));

    Paths paths = new Paths();
    nextValids.stream().forEach(v -> paths.addPath(v, Path.of(v)));
    LinkedList<Element> toVisit = new LinkedList<>(getPrevious(nextValids));
    while (!toVisit.isEmpty()) {
      Element node = toVisit.removeFirst();
      if (paths.containsKey(node)) {
        // if already have a path to the node, we don't need to follow it again
        continue;
      }
      for (Element next : getNexts(Arrays.asList(node))) {
        paths.getPaths(next).stream().forEach(path -> paths.addPath(node, Path.combine(node, path)));
      }
      if (nodes.contains(node)) {
        // if we have found a path, stop it
        break;
      }
      toVisit.addAll(getPrevious(node));
    }

    return nodes.stream().map(x -> paths.getPaths(x)).flatMap(x -> x.stream()).collect(Collectors.toList());
  }

  public static FunctionalChainInvolvement getInvolvment(Element e) {
    if (e instanceof Edge) {
      return ((Edge) e).getLink();
    }
    return ((Vertex) e).getFunction();
  }

  public static Collection<Element> getPrevious(Element e) {
    return getPrevious(Arrays.asList(e));
  }

  public static Collection<Element> getPrevious(Collection<Element> elements) {
    return elements.stream().map(x -> {
      if (x instanceof Edge) {
        return Arrays.asList((Element) ((Edge) x).getSource());
      }
      return ((Vertex) x).getIncomingEdges();
    }).flatMap(x -> x.stream()).collect(Collectors.toList());
  }

  public static Collection<Element> getNexts(Element e) {
    return getNexts(Arrays.asList(e));
  }

  public static Collection<Element> getNexts(Collection<Element> elements) {
    return elements.stream().map(x -> {
      if (x instanceof Edge) {
        return Arrays.asList((Element) ((Edge) x).getTarget());
      }
      return ((Vertex) x).getOutgoingEdges();
    }).flatMap(x -> x.stream()).collect(Collectors.toList());
  }

  public static Collection<Element> getVertices(InvolvementHierarchyGraph graph, FunctionalChainInvolvement fci) {
    return graph.getVertices().keySet().stream().filter(v -> v.getFunction() == fci)
        .map(v -> (Element) graph.getVertices().get(v)).collect(Collectors.toList());
  }

  public static Collection<Element> getNextValids(Collection<Element> nodes, Function<Element, Boolean> isValid) {
    Collection<Element> result = new LinkedHashSet<>();
    LinkedList<Element> toVisit = new LinkedList<>(getNexts(nodes));
    HashSet<Element> visited = new HashSet<>();
    while (!toVisit.isEmpty()) {
      Element node = toVisit.removeFirst();
      if (!visited.contains(node)) {
        if (isValid.apply(node)) {
          result.add(node);
        } else {
          toVisit.addAll(getNexts(Arrays.asList(node)));
        }
        visited.add(node);
      }
    }
    return result;
  }

}
