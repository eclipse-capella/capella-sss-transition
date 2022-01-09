/*******************************************************************************
 * Copyright (c) 2006, 2015 THALES GLOBAL SERVICES.
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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.helpers.EObjectExt;
import org.polarsys.capella.core.data.capellacore.InvolvedElement;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementFunction;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.fa.SequenceLink;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.FunctionalChainExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.IHandler;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.contextscope.IContextScopeHandler;
import org.polarsys.capella.core.transition.common.handlers.notify.INotifyChangeEvent;
import org.polarsys.capella.core.transition.common.handlers.notify.INotifyListener;
import org.polarsys.capella.core.transition.common.handlers.notify.NotifyHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.scope.ScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.transition.system2subsystem.constants.ISubSystemConstants;
import org.polarsys.capella.transition.system2subsystem.handlers.scope.ExternalFunctionsScopeRetriever;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * 
 */
public class FunctionalChainAttachmentHelper implements IHandler, INotifyListener {

  protected static final String FUNCTIONAL_CHAIN_ATTACHMENT_MAP = "FCAttachmentMap"; //$NON-NLS-1$

  protected static final String MERGE_MAP = "MERGE_MAP"; //$NON-NLS-1$

  protected static final String IS_COMPUTED = "FunctionalChainAttachmentHelper.IS_COMPUTED"; //$NON-NLS-1$

  public static FunctionalChainAttachmentHelper getInstance(IContext context_p) {
    FunctionalChainAttachmentHelper handler = (FunctionalChainAttachmentHelper) context_p
        .get(ISubSystemConstants.FUNCTIONAL_CHAIN_ATTACHMENT_HELPER);
    if (handler == null) {
      handler = new FunctionalChainAttachmentHelper();
      handler.init(context_p);
      context_p.put(ISubSystemConstants.FUNCTIONAL_CHAIN_ATTACHMENT_HELPER, handler);
    }
    return handler;
  }

  public Boolean isValidElement(FunctionalChainInvolvement source, IContext context_p) {
    computeChains((FunctionalChain) source.getInvolver(), context_p);
    Boolean cache = getValidityMap(context_p).get(source);
    if (cache == null) {
      cache = Boolean.FALSE;
    }
    return cache;
  }

  public void setValidElement(EObject source, Boolean target, IContext context_p) {
    getValidityMap(context_p).put(source, target);
  }

  @SuppressWarnings("unchecked")
  protected SubSets<FunctionalChainInvolvementFunction> getMergeSets(IContext context_p) {
    SubSets<FunctionalChainInvolvementFunction> res = (SubSets<FunctionalChainInvolvementFunction>) context_p
        .get(MERGE_MAP);
    if (res == null) {
      res = new SubSets<FunctionalChainInvolvementFunction>();
      context_p.put(MERGE_MAP, res);
    }
    return res;
  }

  protected Map<EObject, Boolean> getValidityMap(IContext context_p) {
    Map<EObject, Boolean> res = (Map<EObject, Boolean>) context_p.get(FUNCTIONAL_CHAIN_ATTACHMENT_MAP);
    if (res == null) {
      res = new HashMap<EObject, Boolean>();
      context_p.put(FUNCTIONAL_CHAIN_ATTACHMENT_MAP, res);
    }
    return res;
  }

  /**
   * {@inheritDoc}
   */
  public IStatus init(IContext context_p) {
    NotifyHandlerHelper.getInstance(context_p).addListener(ITransitionConstants.NOTIFY__END_TRANSFORMATION, this,
        context_p);
    return Status.OK_STATUS;
  }

  /**
   * {@inheritDoc}
   */
  public IStatus dispose(IContext context_p) {
    return Status.OK_STATUS;
  }

  /**
   * For all sub involvements nodes (FunctionalChainInvolvmentFunction and FunctionalChainReference) from the chain,
   * compute if they are valid or not.
   */
  public void computeChainNodes(FunctionalChain element_p, IContext context_p) {
    IContextScopeHandler scope = ContextScopeHandlerHelper.getInstance(context_p);

    Collection<FunctionalChainInvolvement> allInvolvements = FunctionalChainExt.getFlatInvolvements(element_p);

    // First, for all Function and FunctionalChains, we define if valid or not.
    for (FunctionalChainInvolvement involvment : allInvolvements) {
      if (isAlreadyCached(involvment, context_p)) {
        continue;
      }

      InvolvedElement involvedElement = involvment.getInvolved();
      if (involvedElement instanceof FunctionalExchange) {
        // We will do it afterwards for readability
        continue;
      }

      IStatus willBeTransformed = TransformationHandlerHelper.getInstance(context_p)
          .isOrWillBeTransformed(involvedElement, context_p);

      if (!willBeTransformed.isOK()) {
        setValidElement(involvment, false, context_p);

      } else if (involvedElement instanceof AbstractFunction) {
        boolean inScope = ExternalFunctionsScopeRetriever.isPrimaryFunction((AbstractFunction) involvedElement,
            context_p)
            || ExternalFunctionsScopeRetriever.isLinkToPrimaryFunction((AbstractFunction) involvedElement, context_p);
        setValidElement(involvment, inScope, context_p);

      } else if (involvedElement instanceof FunctionalChain) {
        boolean inScope = (!scope.contains(ISubSystemConstants.SCOPE_SECONDARY_ELEMENT, involvedElement, context_p))
            && ScopeHandlerHelper.getInstance(context_p).isInScope(involvedElement, context_p);
        setValidElement(involvment, inScope, context_p);

      } else if (involvedElement instanceof FunctionalExchange) {
        if (involvment.getNextFunctionalChainInvolvements().isEmpty()) {
          setValidElement(involvment, false, context_p);
        }
      }
    }

  }

  /**
   * For all FunctionalChainInvolvmentLink involving exchanges, compute if they are valid or not.
   */
  public void computeChainLinks(FunctionalChain element_p, IContext context_p) {

    Collection<FunctionalChainInvolvement> allInvolvements = FunctionalChainExt.getFlatInvolvementsOf(element_p,
        FaPackage.Literals.FUNCTIONAL_EXCHANGE);

    // Second, we look for all exchanges
    for (FunctionalChainInvolvement involvment : allInvolvements) {
      if (isAlreadyCached(involvment, context_p)) {
        continue;
      }

      FunctionalExchange exchange = (FunctionalExchange) involvment.getInvolved();
      IStatus willBeTransformed = TransformationHandlerHelper.getInstance(context_p).isOrWillBeTransformed(exchange,
          context_p);

      if (!willBeTransformed.isOK()) {
        setValidElement(involvment, false, context_p);

      } else if (involvment.getNextFunctionalChainInvolvements().isEmpty()) {
        setValidElement(involvment, false, context_p);

      } else {
        // A Functional Exchange will be scoped only if its targeted functions are scoped
        boolean prevScoped = false;
        boolean nextScoped = false;

        for (FunctionalChainInvolvement n : involvment.getNextFunctionalChainInvolvements()) {
          if (getValidityMap(context_p).get(n)) {
            nextScoped = true;
            break;
          }
        }

        for (FunctionalChainInvolvement p : involvment.getPreviousFunctionalChainInvolvements()) {
          if (getValidityMap(context_p).get(p)) {
            prevScoped = true;
            break;
          }
        }

        setValidElement(involvment, prevScoped && nextScoped, context_p);
      }
    }
  }

  /**
   * For an fci, return the next fci that are valids following getNextFunctionalChainInvolvements paths.
   * 
   * @implNote those are the lasts of the computed paths
   */
  public Collection<FunctionalChainInvolvement> getNextValid(FunctionalChainInvolvement fci, IContext context) {
    Collection<FunctionalChainInvolvement> lasts = new LinkedHashSet<FunctionalChainInvolvement>();
    Collection<LinkedList<FunctionalChainInvolvement>> result = getNextPathsTowards(fci, null, context);
    for (LinkedList<FunctionalChainInvolvement> list : result) {
      if (list.getLast() != null && list.getLast() != fci) {
        lasts.add(list.getLast());
      }
    }
    return lasts;
  }

  /**
   * For an fci, return the previous fci that are valids following getPreviousFunctionalChainInvolvements paths.
   * 
   * @implNote those are the lasts of the computed paths
   */
  public Collection<FunctionalChainInvolvement> getPreviousValid(FunctionalChainInvolvement fci, IContext context) {
    Collection<FunctionalChainInvolvement> lasts = new LinkedHashSet<FunctionalChainInvolvement>();
    Collection<LinkedList<FunctionalChainInvolvement>> result = getPreviousPathsTowards(fci, null, context);
    for (LinkedList<FunctionalChainInvolvement> list : result) {
      if (list.getLast() != null && list.getLast() != fci) {
        lasts.add(list.getLast());
      }
    }
    return lasts;
  }

  /**
   * getPathsTowardsValidElements using FunctionalChainInvolvement::getNextFunctionalChainInvolvements
   */
  public Collection<LinkedList<FunctionalChainInvolvement>> getNextPathsTowards(FunctionalChainInvolvement fci,
      FunctionalChainInvolvement expected, IContext context) {
    return getPathsTowardsValidElements(fci, expected, context,
        FunctionalChainInvolvement::getNextFunctionalChainInvolvements);
  }

  /**
   * getPathsTowardsValidElements using FunctionalChainInvolvement::getPreviousFunctionalChainInvolvements
   */
  public Collection<LinkedList<FunctionalChainInvolvement>> getPreviousPathsTowards(FunctionalChainInvolvement fci,
      FunctionalChainInvolvement expected, IContext context) {
    return getPathsTowardsValidElements(fci, expected, context,
        FunctionalChainInvolvement::getPreviousFunctionalChainInvolvements);
  }

  /**
   * From a fci, returns the list of all paths towards the expected one or the firsts valid elements, following
   * fci.getNexts method.
   * 
   * @param expectedValidElement:
   *          the targeted valid fci that we want to compute paths towards to, or null to retrieve all paths towards the
   *          first valid elements
   * 
   * @param getNexts:
   *          accordingly FunctionalChainInvolvement::getNextFunctionalChainInvolvements or
   *          FunctionalChainInvolvement::getPreviousFunctionalChainInvolvements
   * 
   * @return all the paths from fci towards the expectedValidElements. paths are containing both source fci at first of
   *         the path and the expected one at the end.
   */
  public Collection<LinkedList<FunctionalChainInvolvement>> getPathsTowardsValidElements(FunctionalChainInvolvement fci,
      FunctionalChainInvolvement expectedValidElement, IContext context_p,
      Function<FunctionalChainInvolvement, Collection<FunctionalChainInvolvement>> getNexts) {
    LinkedList<FunctionalChainInvolvement> path = new LinkedList<>();
    path.add(fci);
    return getPathsTowardsValidElements(fci, expectedValidElement, context_p, path, getNexts);
  }

  /**
   * From a fci, returns the list of all paths towards the expected one or the firsts valid elements, following
   * fci.getNexts method.
   * 
   * @param path
   *          the current path computed.
   * @implNote at first, we add the current fci in it to allow cycle detection
   */
  protected Collection<LinkedList<FunctionalChainInvolvement>> getPathsTowardsValidElements(
      FunctionalChainInvolvement fci, FunctionalChainInvolvement expectedValidElement, IContext context_p,
      LinkedList<FunctionalChainInvolvement> path,
      Function<FunctionalChainInvolvement, Collection<FunctionalChainInvolvement>> getNexts) {
    Collection<LinkedList<FunctionalChainInvolvement>> result = new ArrayList<LinkedList<FunctionalChainInvolvement>>();

    for (FunctionalChainInvolvement next : getNexts.apply(fci)) {
      if (path.contains(next)) {
        path.clear();
      } else if ((expectedValidElement == null && isValidElement(next, context_p))
          || next.equals(expectedValidElement)) {
        path.add(next);
        result.add(path);
      } else {
        LinkedList<FunctionalChainInvolvement> newPath = new LinkedList<>();
        newPath.addAll(path);
        newPath.add(next);
        result.addAll(getPathsTowardsValidElements(next, expectedValidElement, context_p, newPath, getNexts));
      }
    }

    return result;
  }

  public void merge(FunctionalChainInvolvementFunction tSrc, FunctionalChainInvolvementFunction tTgt,
      IContext context) {
    getMergeSets(context).merge(tSrc, tTgt);
  }

  @Override
  public void notifyChanged(INotifyChangeEvent event, IContext context) {
    for (Collection<FunctionalChainInvolvementFunction> set : getMergeSets(context).getSets()) {
      FunctionalChainInvolvementFunction[] array = set.toArray(new FunctionalChainInvolvementFunction[0]);
      for (int i = 1; i < array.length; i++) {
        for (EObject o : EObjectExt.getReferencers(array[i],
            FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__SOURCE)) {
          ((FunctionalChainInvolvementLink) o).setSource(array[0]);
        }
        for (EObject o : EObjectExt.getReferencers(array[i],
            FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__TARGET)) {
          ((FunctionalChainInvolvementLink) o).setTarget(array[0]);
        }
        for (EObject o : EObjectExt.getReferencers(array[i], FaPackage.Literals.SEQUENCE_LINK__SOURCE)) {
          ((SequenceLink) o).setSource(array[0]);
        }
        for (EObject o : EObjectExt.getReferencers(array[i], FaPackage.Literals.SEQUENCE_LINK__TARGET)) {
          ((SequenceLink) o).setTarget(array[0]);
        }
      }
    }
  }

  protected boolean isAlreadyCached(FunctionalChainInvolvement fci, IContext context) {
    return getValidityMap(context).get(fci) != null;
  }

  public void computeChains(FunctionalChain element, IContext context_p) {
    if (context_p.get(IS_COMPUTED) == null) {
      BlockArchitecture architecture = BlockArchitectureExt.getRootBlockArchitecture(element);
      Collection<FunctionalChain> validChainsInScope = FunctionalChainExt.getAllFunctionalChains(architecture).stream()
          .filter(fc -> ScopeHandlerHelper.getInstance(context_p).isInScope(fc, context_p))
          .filter(FunctionalChainExt::isFunctionalChainValid).collect(Collectors.toList());

      for (FunctionalChain chain : validChainsInScope) {
        computeChainNodes(chain, context_p);
      }
      for (FunctionalChain chain : validChainsInScope) {
        computeChainLinks(chain, context_p);
      }
      context_p.put(IS_COMPUTED, Boolean.TRUE);
    }
  }
}
