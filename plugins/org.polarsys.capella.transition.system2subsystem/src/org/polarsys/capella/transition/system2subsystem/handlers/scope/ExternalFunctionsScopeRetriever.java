/*******************************************************************************
 * Copyright (c) 2006, 2022 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.handlers.scope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.activity.ActivityEdge;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.helpers.fa.services.FunctionExt;
import org.polarsys.capella.core.model.helpers.FunctionalExchangeExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.contextscope.IContextScopeHandler;
import org.polarsys.capella.core.transition.common.handlers.scope.IScopeRetriever;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * A scope retriever to retrieve all exchanges that can be reused as a FakeExchange while FunctionalChain processing
 */
public class ExternalFunctionsScopeRetriever implements IScopeRetriever {

  /**
   * {@inheritDoc}
   */
  public IStatus init(IContext context_p) {
    return Status.OK_STATUS;
  }

  /**
   * {@inheritDoc}
   */
  public IStatus dispose(IContext context_p) {
    return Status.OK_STATUS;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<? extends EObject> retrieveRelatedElements(EObject source_p, IContext context_p) {
    HashSet<EObject> result = new HashSet<EObject>();
    IContextScopeHandler scope = ContextScopeHandlerHelper.getInstance(context_p);
    if (source_p instanceof AbstractFunction) {
      AbstractFunction element = (AbstractFunction) source_p;
      for (FunctionalExchange exchange : getFE(element, context_p)) {
        AbstractFunction sourceFunction = (AbstractFunction) exchange.getSource().eContainer();
        AbstractFunction targetFunction = (AbstractFunction) exchange.getTarget().eContainer();
        AbstractFunction oppositeFunction = element.equals(targetFunction) ? sourceFunction : targetFunction;
        if (isLinkToPrimaryFunction(oppositeFunction, context_p)) {
          for (FunctionalExchange fe : getFE(oppositeFunction, context_p)) {
            if (!scope.contains(ITransitionConstants.SOURCE_SCOPE, fe, context_p)) {
              AbstractFunction source = (AbstractFunction) fe.getSource().eContainer();
              AbstractFunction target = (AbstractFunction) fe.getTarget().eContainer();
              AbstractFunction opposite = oppositeFunction.equals(target) ? source : target;

              if (isLinkToPrimaryFunction(opposite, context_p)) {

                Collection<FunctionalExchange> srcFes = getFE(source, context_p);
                Collection<FunctionalExchange> trgFes = getFE(target, context_p);

                Collection<FunctionalChain> srcFcs = getFunctionalChains(srcFes);
                Collection<FunctionalChain> trgFcs = getFunctionalChains(trgFes);
                Collection<FunctionalChain> feFcs = fe.getInvolvingFunctionalChains();

                Collection<FunctionalChain> retained = new ArrayList<FunctionalChain>();
                retained.addAll(feFcs);
                retained.retainAll(srcFcs);
                retained.retainAll(trgFcs);

                if (!retained.isEmpty()) {
                  if (!scope.contains(ITransitionConstants.SOURCE_SCOPE, fe, context_p)) {
                    scope.add(ITransitionConstants.SOURCE_SCOPE, fe, context_p);
                    result.add(fe);
                  }
                }
              }
            }
          }
        }
      }
    }
    return result;
  }

  protected Collection<FunctionalChain> getFunctionalChains(Collection<FunctionalExchange> srcs) {
    Collection<FunctionalChain> res = new ArrayList<FunctionalChain>();

    for (FunctionalExchange fe : srcs) {
      res.addAll(fe.getInvolvingFunctionalChains());
    }
    return res;
  }

  /**
   * @param function
   * @param context_p
   * @return scoped FE linked to this function
   */
  protected Collection<FunctionalExchange> getScopedFE(AbstractFunction function, IContext context_p) {
    IContextScopeHandler scope = ContextScopeHandlerHelper.getInstance(context_p);
    Collection<FunctionalExchange> res = new ArrayList<FunctionalExchange>();

    Collection<ActivityEdge> edges = new ArrayList<ActivityEdge>();
    edges.addAll(function.getIncoming());
    edges.addAll(function.getOutgoing());

    for (ActivityEdge edge : edges) {
      if (edge instanceof FunctionalExchange) {
        if (scope.contains(ITransitionConstants.SOURCE_SCOPE, edge, context_p)) {
          res.add((FunctionalExchange) edge);
        }
      }
    }

    return res;
  }

  protected Collection<FunctionalExchange> getFE(AbstractFunction function, IContext context_p) {
    Collection<FunctionalExchange> res = new ArrayList<FunctionalExchange>();
    res.addAll(FunctionExt.getIncomingExchange(function));
    res.addAll(FunctionExt.getOutGoingExchange(function));
    return res;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<? extends EObject> retrieveSharedElements(IContext context_p) {
    return Collections.emptyList();
  }

  public static boolean isLinkToPrimaryFunction(AbstractFunction function, IContext context_p) {
    Collection<ActivityEdge> edges = new ArrayList<ActivityEdge>();
    edges.addAll(function.getIncoming());
    edges.addAll(function.getOutgoing());
    IContextScopeHandler scope = ContextScopeHandlerHelper.getInstance(context_p);

    for (ActivityEdge edge : edges) {
      if (edge instanceof FunctionalExchange) {
        FunctionalExchange fe = (FunctionalExchange) edge;

        AbstractFunction opposite = null;
        if (fe.getSource().eContainer().equals(function)) {
          opposite = (AbstractFunction) fe.getTarget().eContainer();
        } else {
          opposite = (AbstractFunction) fe.getSource().eContainer();
        }

        if (scope.contains(ITransitionConstants.SOURCE_SCOPE, opposite, context_p)) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * @param exchange_p
   * @param context_p
   * @return
   */
  public static boolean isLinkToPrimaryFunction(FunctionalExchange exchange_p, IContext context_p) {
    AbstractFunction source = FunctionalExchangeExt.getSourceFunction(exchange_p);
    AbstractFunction target = FunctionalExchangeExt.getTargetFunction(exchange_p);

    if (isPrimaryFunction(source, context_p)) {
      return !isPrimaryFunction(target, context_p);
    }
    if (isPrimaryFunction(target, context_p)) {
      return !isPrimaryFunction(source, context_p);
    }
    return false;
  }

  public static boolean isPrimaryExchange(FunctionalExchange exchange_p, IContext context_p) {
    AbstractFunction source = FunctionalExchangeExt.getSourceFunction(exchange_p);
    AbstractFunction target = FunctionalExchangeExt.getTargetFunction(exchange_p);

    return isPrimaryFunction(source, context_p) && isPrimaryFunction(target, context_p);
  }

  /**
   * @param involvedElement_p
   * @param context_p
   * @return
   */
  public static boolean isPrimaryFunction(AbstractFunction function, IContext context_p) {
    return ContextScopeHandlerHelper.getInstance(context_p).contains(ITransitionConstants.SOURCE_SCOPE, function,
        context_p);
  }

}
