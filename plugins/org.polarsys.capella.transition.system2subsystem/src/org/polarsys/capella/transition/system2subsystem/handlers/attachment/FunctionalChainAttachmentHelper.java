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
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.helpers.EObjectExt;
import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementFunction;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.fa.SequenceLink;
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

  public Boolean isValidElement(EObject source, IContext context_p) {
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
   * Returns whether the involvement can be transitioned (in the scope and involved is transitioned)
   */
  public boolean isValidInvolvement(FunctionalChainInvolvement element_p, IContext context_p) {
    IContextScopeHandler scope = ContextScopeHandlerHelper.getInstance(context_p);
    NamedElement involvedElement = (NamedElement) element_p.getInvolved();

    IStatus willBeTransformed = TransformationHandlerHelper.getInstance(context_p)
        .isOrWillBeTransformed(involvedElement, context_p);

    // First we check the primary scope.
    boolean inScope;

    if (involvedElement instanceof AbstractFunction) {
      inScope = ExternalFunctionsScopeRetriever.isPrimaryFunction((AbstractFunction) involvedElement, context_p);
    } else {
      inScope = (!scope.contains(ISubSystemConstants.SCOPE_SECONDARY_ELEMENT, involvedElement, context_p))
          && ScopeHandlerHelper.getInstance(context_p).isInScope(involvedElement, context_p);
    }

    // If functional exchange is in scope, the next FunctionalChainInvolvement must be scoped.
    if ((involvedElement instanceof FunctionalExchange) && inScope
        && (!element_p.getNextFunctionalChainInvolvements().isEmpty())) {
      FunctionalChainInvolvement nextFCI = element_p.getNextFunctionalChainInvolvements().get(0);
      boolean nextInScope = isValidInvolvement(nextFCI, context_p);
      if (!nextInScope) {
        return false;
      }
    }

    // If not in initial scope, we check the neightbors involvedElements
    if (!inScope) {

      if (involvedElement instanceof AbstractFunction) {
        // The function is secondary scoped if it is concerned by a scoped FunctionalExchange
        inScope = ExternalFunctionsScopeRetriever.isLinkToPrimaryFunction((AbstractFunction) involvedElement,
            context_p);

      } else if (involvedElement instanceof FunctionalExchange) {
        // A Functional Exchange will be scoped only if its functions are secondary scoped
        boolean prevScoped = false;
        boolean nextScoped = false;

        for (FunctionalChainInvolvement n : element_p.getNextFunctionalChainInvolvements()) {
          if (ExternalFunctionsScopeRetriever.isLinkToPrimaryFunction((AbstractFunction) n.getInvolved(), context_p)) {
            nextScoped = true;
            break;
          }
        }
        for (FunctionalChainInvolvement p : element_p.getPreviousFunctionalChainInvolvements()) {
          if (ExternalFunctionsScopeRetriever.isLinkToPrimaryFunction((AbstractFunction) p.getInvolved(), context_p)) {
            prevScoped = true;
            break;
          }
        }

        if (prevScoped && nextScoped) {
          inScope = true;
        }
      }

    }

    return inScope && willBeTransformed.isOK();
  }

  /**
   * Put in the map true for all valid functional chain involvement (stop browsing the chain after the first invalid
   * involvement)
   */
  public void computeChain(FunctionalChain element_p, IContext context_p) {
    for (FunctionalChainInvolvement next : element_p.getFirstFunctionalChainInvolvements()) {
      computeChainInternal(next, context_p, null);
    }

  }

  public void computeChainInternal(FunctionalChainInvolvement involmt, IContext context_p,
      FunctionalChainInvolvement lastValid) {

    // If not already computed
    if (getValidityMap(context_p).get(involmt) != null) {
      return;
    }

    boolean isValid = isValidInvolvement(involmt, context_p);
    setValidElement(involmt, Boolean.valueOf(isValid), context_p);
    if (isValid) {
      lastValid = involmt;
    }

    try {
      for (FunctionalChainInvolvement next : involmt.getNextFunctionalChainInvolvements()) {
        computeChainInternal(next, context_p, lastValid);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public Collection<FunctionalChainInvolvement> getNextValid(FunctionalChainInvolvement fci, IContext context_p) {
    Collection<FunctionalChainInvolvement> res = new ArrayList<FunctionalChainInvolvement>();

    getNextValidInternal(fci, fci, context_p, res);

    return res;
  }

  private void getNextValidInternal(FunctionalChainInvolvement fci, FunctionalChainInvolvement sc, IContext context_p,
      Collection<FunctionalChainInvolvement> res) {
    if ((fci.getNextFunctionalChainInvolvements() != null) && (!fci.getNextFunctionalChainInvolvements().isEmpty())) {

      for (FunctionalChainInvolvement next : fci.getNextFunctionalChainInvolvements()) {
        if (next != null) {
          if (isValidElement(next, context_p)) {
            res.add(next);
          } else {
            getNextValidInternal(next, sc, context_p, res);
          }
        }
      }
    }
  }

  public Collection<FunctionalChainInvolvement> getPreviousValid(FunctionalChainInvolvement fci, IContext context_p) {
    Collection<FunctionalChainInvolvement> res = new ArrayList<FunctionalChainInvolvement>();

    getPreviousValidInternal(fci, context_p, res);

    return res;
  }

  private void getPreviousValidInternal(FunctionalChainInvolvement fci, IContext context_p,
      Collection<FunctionalChainInvolvement> res) {
    if ((fci.getPreviousFunctionalChainInvolvements() != null)
        && (!fci.getPreviousFunctionalChainInvolvements().isEmpty())) {
      for (FunctionalChainInvolvement prev : fci.getPreviousFunctionalChainInvolvements()) {
        if (prev != null) {
          if (isValidElement(prev, context_p)) {
            res.add(prev);
          } else {
            getPreviousValidInternal(prev, context_p, res);
          }
        }
      }
    }
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
        for (EObject o : EObjectExt.getReferencers(array[i], FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__SOURCE)) {
          ((FunctionalChainInvolvementLink) o).setSource(array[0]);
        }
        for (EObject o : EObjectExt.getReferencers(array[i], FaPackage.Literals.FUNCTIONAL_CHAIN_INVOLVEMENT_LINK__TARGET)) {
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
}
