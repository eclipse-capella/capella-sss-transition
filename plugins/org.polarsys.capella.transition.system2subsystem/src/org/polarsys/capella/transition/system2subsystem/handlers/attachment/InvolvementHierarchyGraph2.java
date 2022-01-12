/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.handlers.attachment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementFunction;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.core.data.fa.FunctionalChainReference;
import org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph;

/**
 * A graph for a functional chain on which getNexts() and getPrevious() for an involvement doesn't retrieve unrelated 
 * functional chains like FCI.getNextFunctionalChainInvolvements or FCI.getPreviousFunctionalChainInvolvements
 * 
 * Due to missing helpers or protected fields in InvolvementHierarchyGraph, we need to add some getters here. We add
 * also inversed edges on a vertex to be able to retrieve getPrevious
 */
public class InvolvementHierarchyGraph2 extends InvolvementHierarchyGraph {

  public static interface Element {
    
    public List<Element> getNexts();

    public List<Element> getPrevious();

    public FunctionalChainInvolvement getElement();

  }

  public class VertexKey extends InvolvementHierarchyGraph.VertexKey {

    public VertexKey(FunctionalChainInvolvementFunction function, List<FunctionalChainReference> referenceHierarchy) {
      super(function, referenceHierarchy);
    }

    public FunctionalChainInvolvementFunction getFunction() {
      return function;
    }
  }

  public class Vertex extends InvolvementHierarchyGraph.Vertex implements Element {

    protected List<InvolvementHierarchyGraph2.Edge> incomings;

    public Vertex(FunctionalChainInvolvementFunction function, List<FunctionalChainReference> referenceHierarchy) {
      super(function, referenceHierarchy);
      incomings = new ArrayList<>();
    }

    public FunctionalChainInvolvementFunction getFunction() {
      return function;
    }

    public FunctionalChainInvolvement getElement() {
      return function;
    }

    public List<InvolvementHierarchyGraph2.Edge> getEdges() {
      return (List) edges;
    }

    public List<Edge> getIncomings() {
      return incomings;
    }

    public List<Element> getNexts() {
      return (List) ((Vertex) this).getEdges();
    }

    public List<Element> getPrevious() {
      return (List) ((Vertex) this).getIncomings();
    }
  }

  public class Edge extends InvolvementHierarchyGraph.Edge implements Element {

    public Edge(FunctionalChainInvolvementLink link, Vertex source, Vertex target) {
      super(link, source, target);
    }

    public FunctionalChainInvolvement getElement() {
      return link;
    }

    public FunctionalChainInvolvementLink getLink() {
      return link;
    }

    public InvolvementHierarchyGraph2.Vertex getSource() {
      return (InvolvementHierarchyGraph2.Vertex) source;
    }

    public InvolvementHierarchyGraph2.Vertex getTarget() {
      return (InvolvementHierarchyGraph2.Vertex) target;
    }

    public List<Element> getNexts() {
      return Arrays.asList(((Edge) this).getTarget());
    }

    public List<Element> getPrevious() {
      return Arrays.asList(((Edge) this).getSource());
    }

  }

  public InvolvementHierarchyGraph2(FunctionalChain rootFunctionalChain) {
    super(rootFunctionalChain);
  }

  public Collection<Vertex> getVertexs(FunctionalChainInvolvement fci) {
    return (Collection) vertices.keySet().stream().filter(v -> ((VertexKey) v).getFunction() == fci)
        .map(v -> vertices.get(v)).collect(Collectors.toList());
  }

  protected boolean createVertex(FunctionalChainInvolvementFunction function,
      List<FunctionalChainReference> referenceHierarchy) {

    List<FunctionalChainReference> functionHierarchy = new ArrayList<>(referenceHierarchy);
    VertexKey vertexKey = new VertexKey(function, functionHierarchy);

    if (vertices.containsKey(vertexKey)) {
      return false;
    }

    Vertex vertex = new Vertex(function, functionHierarchy);
    vertices.put(vertexKey, vertex);

    return true;
  }

  @Override
  protected void createEdge(FunctionalChainInvolvementLink link,
      org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph.Vertex sourceVertex,
      org.polarsys.capella.core.model.helpers.graph.InvolvementHierarchyGraph.Vertex targetVertex) {
    Edge edge = new Edge(link, (Vertex) sourceVertex, (Vertex) targetVertex);
    ((Vertex) sourceVertex).getEdges().add(edge);
    ((Vertex) targetVertex).getIncomings().add(edge);
  }

  protected Vertex getVertex(FunctionalChainInvolvementFunction function,
      List<FunctionalChainReference> functionReferenceHierarchy) {
    VertexKey vertexKey = new VertexKey(function, functionReferenceHierarchy);

    return (Vertex) vertices.get(vertexKey);
  }

}