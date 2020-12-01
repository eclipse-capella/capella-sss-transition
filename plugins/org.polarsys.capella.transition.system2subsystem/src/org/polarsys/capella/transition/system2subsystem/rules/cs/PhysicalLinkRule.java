/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.rules.cs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.polarsys.capella.core.data.capellacore.Involvement;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.ComponentPkg;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.cs.PhysicalLink;
import org.polarsys.capella.core.data.cs.PhysicalPath;
import org.polarsys.capella.core.data.cs.PhysicalPathInvolvement;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.attachment.AttachmentHelper;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.core.transition.system.rules.AbstractCapellaElementRule;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IPremise;

public class PhysicalLinkRule extends AbstractCapellaElementRule {

  public PhysicalLinkRule() {
    super();
  }

  @Override
  protected EClass getSourceType() {
    return CsPackage.Literals.PHYSICAL_LINK;
  }

  @Override
  protected void retrieveContainer(EObject element_p, List<EObject> result_p, IContext context_p) {
    // Nothing here. We don't want to add container
  }

  @Override
  protected EObject getBestContainer(EObject element, EObject result, IContext context) {
    // If the PL is contained in the System, we cannot find its container just by traceability.
    if (BlockArchitectureExt.getRootBlockArchitecture(element).getSystem() == element.eContainer()) {
      return null;
    }

    return super.getBestContainer(element, result, context);
  }

  @Override
  protected EObject getDefaultContainer(EObject element_p, EObject result_p, IContext context_p) {
    EObject root = TransformationHandlerHelper.getInstance(context_p).getLevelElement(element_p, context_p);
    BlockArchitecture target =
        (BlockArchitecture) TransformationHandlerHelper.getInstance(context_p).getBestTracedElement(root, context_p, CsPackage.Literals.BLOCK_ARCHITECTURE,
            element_p, result_p);
    return BlockArchitectureExt.getContext(target);
  }

  @Override
  protected void retrieveGoDeep(EObject source_p, List<EObject> result_p, IContext context_p) {
    super.retrieveGoDeep(source_p, result_p, context_p);
    PhysicalLink element = (PhysicalLink) source_p;
    result_p.addAll(element.getCategories());
    result_p.addAll(element.getLinkEnds());
    result_p.addAll(element.getOwnedComponentExchangeAllocations());
    result_p.addAll(element.getOwnedComponentExchangeFunctionalExchangeAllocations());

    // Retrieve physical paths for all related physicalLinks
    if (ContextScopeHandlerHelper.getInstance(context_p).contains(ITransitionConstants.SOURCE_SCOPE, element, context_p)) {
      for (Involvement involvment : getRelatedPhysicalPathInvolvements(element)) {
        if (involvment instanceof PhysicalPathInvolvement) {
          PhysicalPath path = getPhysicalPath((PhysicalPathInvolvement) involvment);
          if (path != null) {
            result_p.add(path);
          }
        }
      }
    }

    // Retrieve all involvements
    result_p.addAll(getRelatedPhysicalPathInvolvements(element));
  }

  /**
   * @param element_p
   * @return
   */
  protected Collection<Involvement> getRelatedPhysicalPathInvolvements(PhysicalLink element_p) {
    return element_p.getInvolvingInvolvements();
  }

  protected PhysicalPath getPhysicalPath(PhysicalPathInvolvement element_p) {
    return (PhysicalPath) element_p.eContainer();
  }

  @Override
  protected void attachRelated(EObject element_p, EObject result_p, IContext context_p) {
    super.attachRelated(element_p, result_p, context_p);
    AttachmentHelper.getInstance(context_p).attachTracedElements(element_p, result_p, CsPackage.Literals.PHYSICAL_LINK__LINK_ENDS, context_p);
  }

  @Override
  protected void premicesRelated(EObject element_p, ArrayList<IPremise> needed_p) {
    super.premicesRelated(element_p, needed_p);
    PhysicalLink element = (PhysicalLink) element_p;
    needed_p.addAll(createDefaultPrecedencePremices(element, CsPackage.Literals.PHYSICAL_LINK__LINK_ENDS));
  }
  
  @Override
  protected EStructuralFeature getTargetContainementFeature(EObject element, EObject result, EObject container, IContext context) {
    if (container instanceof ComponentPkg) {
      return CsPackage.Literals.COMPONENT_PKG__OWNED_PHYSICAL_LINKS;
    }
    
    return element.eContainingFeature();
  }
}
