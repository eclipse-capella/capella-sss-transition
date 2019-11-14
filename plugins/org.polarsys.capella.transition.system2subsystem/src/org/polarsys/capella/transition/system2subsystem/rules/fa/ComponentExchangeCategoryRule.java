/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.rules.fa;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.ComponentPkg;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.core.transition.system.rules.AbstractCapellaElementRule;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class ComponentExchangeCategoryRule extends AbstractCapellaElementRule {

  @Override
  public EClass getTargetType(EObject element_p, IContext context_p) {
    return FaPackage.Literals.COMPONENT_EXCHANGE_CATEGORY;
  }

  @Override
  protected EClass getSourceType() {
    return FaPackage.Literals.COMPONENT_EXCHANGE_CATEGORY;
  }

  @Override
  protected void retrieveContainer(EObject element_p, List<EObject> result_p, IContext context_p) {
  }

  @Override
  protected EObject getDefaultContainer(EObject element_p, EObject result_p, IContext context_p) {
    EObject root = TransformationHandlerHelper.getInstance(context_p).getLevelElement(element_p, context_p);
    BlockArchitecture target =
        (BlockArchitecture) TransformationHandlerHelper.getInstance(context_p).getBestTracedElement(root, context_p, CsPackage.Literals.BLOCK_ARCHITECTURE,
            element_p, result_p);
    return BlockArchitectureExt.getComponentPkg(target);
  }
  
  @Override
  protected EObject getBestContainer(EObject element, EObject result, IContext context) {
    // If the Component Exchange is contained in the System, we cannot find its container just by traceability.
    if (BlockArchitectureExt.getRootBlockArchitecture(element).getSystem() == element.eContainer()) {
      return null;
    }

    return super.getBestContainer(element, result, context);
  }

  @Override
  protected EStructuralFeature getTargetContainementFeature(EObject element, EObject result, EObject container, IContext context) {
    if (container instanceof ComponentPkg) {
      return CsPackage.Literals.COMPONENT_PKG__OWNED_COMPONENT_EXCHANGE_CATEGORIES;
    }
    
    return super.getTargetContainementFeature(element, result, container, context);
  }
}
