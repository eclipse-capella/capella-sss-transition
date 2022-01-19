/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.tests.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementFunction;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.core.data.fa.FunctionalChainReference;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.context.TransitionContext;
import org.polarsys.capella.core.transition.common.handlers.scope.DefaultScopeHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.CompoundTraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.transformation.DefaultTransformationHandler;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class ChainHelper {

  /**
   * Returns whether the first list contains duplicates and all elements of second and only them, unordered
   */
  public static <T> boolean equalsUnordered(Collection<T> o, Collection<T> r) {
    HashSet<T> result = new HashSet<T>(o);
    result.retainAll(r);
    return result.size() == o.size() && result.size() == r.size();
  }

  /**
   * Return a list of all names of given objects
   */
  public static <T> Collection<String> toName(Collection<T> arrayList) {
    return arrayList.stream().map(fcr -> getName(fcr)).collect(Collectors.toList());
  }

  /**
   * Return a string of all names of the given objects separated by /
   */
  public static <T> String toListString(Collection<T> arrayList) {
    return arrayList.stream().map(fcr -> getName(fcr)).collect(Collectors.joining("/"));
  }

  public static <T> String getName(T obj) {
    if (obj instanceof FunctionalChain) {
      return ((FunctionalChain) obj).getName();
    }
    return ((FunctionalChain) ((FunctionalChainReference) obj).getInvolved()).getName();
  }

  /**
   * Returns whether the chain references the sub chain
   */
  public static boolean referenceSubChain(FunctionalChain chain, FunctionalChain sub) {
    return chain.getOwnedFunctionalChainInvolvements().stream().filter(x -> referenceSubChain(x, sub)).findFirst()
        .isPresent();
  }

  public static boolean referenceSubChain(FunctionalChainInvolvement f, FunctionalChain sub) {
    return f.getInvolved().equals(sub);
  }

  /**
   * Returns the first involvement referencing a fake exchange created while transition
   */
  public static List<FunctionalChainReference> referencingFunctionalChains(FunctionalChain chain, FunctionalChain referencedChain) {
    return chain.getOwnedFunctionalChainInvolvements().stream()
        .filter(FunctionalChainReference.class::isInstance).map(FunctionalChainReference.class::cast).filter(x -> x.getInvolved() == referencedChain).collect(Collectors.toList());
  }
  
  /**
   * Returns the first involvement referencing a fake exchange created while transition
   */
  public static FunctionalChainInvolvementLink referencingFakeExchange(FunctionalChain chain) {
    return (FunctionalChainInvolvementLink) chain.getOwnedFunctionalChainInvolvements().stream()
        .filter(ChainHelper::referenceFakeExchange).findFirst().get();
  }

  /**
   * Returns the involvements referencing a fake exchange created while transition
   */
  public static List<FunctionalChainInvolvementFunction> fcifReferencingFunctions(FunctionalChain chain, AbstractFunction fct) {
    return chain.getOwnedFunctionalChainInvolvements().stream().filter(x -> x.getInvolved() == fct).filter(FunctionalChainInvolvementFunction.class::isInstance)
        .map(FunctionalChainInvolvementFunction.class::cast).collect(Collectors.toList());
  }

  /**
   * Returns the involvements referencing a fake exchange created while transition
   */
  public static List<FunctionalChainInvolvementLink> fcilReferencingFunctions(FunctionalChain chain, AbstractFunction fct) {
    return chain.getOwnedFunctionalChainInvolvements().stream().filter(x -> x.getInvolved() == fct).filter(FunctionalChainInvolvementLink.class::isInstance)
        .map(FunctionalChainInvolvementLink.class::cast).collect(Collectors.toList());
  }
  
  /**
   * Returns the involvements referencing a fake exchange created while transition
   */
  public static List<FunctionalChainInvolvementLink> referencingFakeExchanges(FunctionalChain chain) {
    return chain.getOwnedFunctionalChainInvolvements().stream().filter(ChainHelper::referenceFakeExchange)
        .map(FunctionalChainInvolvementLink.class::cast).collect(Collectors.toList());
  }

  /**
   * Returns whether the chain references a fake exchange created while transition
   */
  public static boolean referenceFakeExchange(FunctionalChain chain) {
    return referencingFakeExchange(chain) != null;
  }

  public static boolean referenceFakeExchange(FunctionalChainInvolvement f) {
    return f instanceof FunctionalChainInvolvementLink && f.getInvolved() instanceof FunctionalExchange
        && ((FunctionalExchange) f.getInvolved()).getName().contains("Fake");
  }

  public static String fciToString(FunctionalChainInvolvement fci) {
    return ((AbstractNamedElement) fci.getInvolved()).getName();
  }
  
  public static String pathToString(Collection<FunctionalChainInvolvement> fcis) {
    return fcis.stream().map(ChainHelper::fciToString).collect(Collectors.joining("/"));
  }

  public static Collection<String> pathsToString(Collection<LinkedList<FunctionalChainInvolvement>> paths) {
    return paths.stream().map(ChainHelper::pathToString).collect(Collectors.toList());
  }

  /**
   * Create a simple transition context saying everything is in the scope, traced and transformed.
   */
  public static TransitionContext createBasicContext() {

    TransitionContext context = new TransitionContext();
    context.put(ITransitionConstants.SCOPE_HANDLER, new DefaultScopeHandler() {
      @Override
      public boolean isInScope(EObject element, IContext context) {
        return true;
      }
    });
    context.put(ITransitionConstants.TRANSFORMATION_HANDLER, new DefaultTransformationHandler() {
      @Override
      public IStatus isOrWillBeTransformed(EObject source, IContext context) {
        return Status.OK_STATUS;
      }
    });
    context.put(ITransitionConstants.TRACEABILITY_TRANSFORMATION_HANDLER, new CompoundTraceabilityHandler(null) {
      @Override
      public boolean isTraced(EObject element, IContext context) {
        return true;
      }
    });
    return context;
  }
}
