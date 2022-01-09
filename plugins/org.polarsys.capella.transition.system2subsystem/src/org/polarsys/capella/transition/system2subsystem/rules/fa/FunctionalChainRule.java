/*******************************************************************************
 * Copyright (c) 2006, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.rules.fa;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.common.helpers.EObjectExt;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.data.capellacore.Involvement;
import org.polarsys.capella.core.data.capellacore.InvolverElement;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainReference;
import org.polarsys.capella.core.data.interaction.AbstractCapability;
import org.polarsys.capella.core.data.interaction.FunctionalChainAbstractCapabilityInvolvement;
import org.polarsys.capella.core.model.helpers.FunctionalChainExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.contextscope.IContextScopeHandler;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.attachment.FunctionalChainAttachmentHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;


public class FunctionalChainRule extends org.polarsys.capella.core.transition.system.rules.fa.FunctionalChainRule {

  @Override
  public IStatus transformRequired(EObject element_p, IContext context_p) {
    // transforms if bounds are transformed or will be transformed
    FunctionalChain element = (FunctionalChain) element_p;

    if (!(FunctionalChainExt.isFunctionalChainValid(element))) {
      return new Status(IStatus.WARNING, Messages.Activity_Transformation, NLS.bind("Functional Chain ''{0}'' is not valid.",
          LogHelper.getInstance().getText(element_p)));
    }
    if (!isRelatedToPrimaryFunction(element, context_p)) {
      return new Status(IStatus.WARNING, Messages.Activity_Transformation, NLS.bind("Functional Chain ''{0}'' is not related to a primary function.",
          LogHelper.getInstance().getText(element_p)));
    }
    return Status.OK_STATUS;
  }

  @Override
  public void apply(EObject element_p, IContext context_p) throws Exception {
    // Perform a computation of valid involvement
    if (applyRequired(element_p, context_p).isOK()) {
      FunctionalChain element = (FunctionalChain) element_p;
      FunctionalChainAttachmentHelper.getInstance(context_p).computeChains(element, context_p);
    }

    super.apply(element_p, context_p);
  }

  @Override
  protected void retrieveGoDeep(EObject source_p, List<EObject> result_p, IContext context_p) {
    super.retrieveGoDeep(source_p, result_p, context_p);
    FunctionalChain element = (FunctionalChain) source_p;

    if (FunctionalChainExt.isFunctionalChainValid(element) && isRelatedToPrimaryFunction(element, context_p)) {
      retrieveDeepValidChain(element, result_p, context_p);
    }
  }

  @Override
  protected void retrieveDeepValidChain(EObject source_p, List<EObject> result_p, IContext context_p) {
    FunctionalChain element = (FunctionalChain) source_p;

    super.retrieveDeepValidChain(source_p, result_p, context_p);
    for (Involvement involvement : element.getInvolvingInvolvements()) {
      InvolverElement involver = involvement.getInvolver();

      // Add all involving Capabilities
      if (involvement instanceof FunctionalChainAbstractCapabilityInvolvement) {
        result_p.add(involvement);
        if ((involver != null) && (involver instanceof AbstractCapability)) {
          result_p.add(involver);
        }
      }
    }
    
    result_p.addAll(getAllReferencingChains((FunctionalChain) element));

    // Add all Owning Capabilities.
    EObject parent = element.eContainer();
    if (parent instanceof AbstractCapability) {
      result_p.add(parent);
    }
  }

  /**
   * For a given chain, retrieve recursively all chains referencing it.
   */
  public Collection<FunctionalChain> getAllReferencingChains(FunctionalChain chain) {
    LinkedList<FunctionalChain> chains = new LinkedList<FunctionalChain>();
    LinkedList<FunctionalChain> visited = new LinkedList<FunctionalChain>();
    chains.add(chain);

    while (!chains.isEmpty()) {
      FunctionalChain ch = chains.removeFirst();
      visited.add(ch);
      for (EObject ref : EObjectExt.getReferencers(ch, CapellacorePackage.Literals.INVOLVEMENT__INVOLVED)) {
        if (ref instanceof FunctionalChainReference) {
          FunctionalChainReference reference = (FunctionalChainReference) ref;
          if (!visited.contains((reference.getInvolver()))) {
            chains.add((FunctionalChain) reference.getInvolver());
          }
        }
      }
    }
    visited.remove(chain);
    return visited;
  }

  private boolean isPrimary(AbstractFunction function, IContext context_p) {
    IContextScopeHandler scope = ContextScopeHandlerHelper.getInstance(context_p);
    return scope.contains(ITransitionConstants.SOURCE_SCOPE, function, context_p);
  }
  
  private boolean isRelatedToPrimaryFunction(FunctionalChain element, IContext context_p) {
    Collection<FunctionalChain> chains = getAllReferencingChains(element);
    chains.add(element);
    return chains.stream().map(x -> FunctionalChainExt.getFlatFunctions(x)).flatMap(x -> x.stream()).filter(f -> isPrimary(f, context_p)).findAny().isPresent();
  }

}
