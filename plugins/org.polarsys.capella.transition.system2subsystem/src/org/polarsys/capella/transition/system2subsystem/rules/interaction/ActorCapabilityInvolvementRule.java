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
package org.polarsys.capella.transition.system2subsystem.rules.interaction;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.polarsys.capella.core.data.capellacommon.CapabilityRealizationInvolvement;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.data.ctx.CapabilityInvolvement;
import org.polarsys.capella.core.data.ctx.CtxPackage;
import org.polarsys.capella.core.data.la.LaPackage;
import org.polarsys.capella.core.data.oa.EntityOperationalCapabilityInvolvement;
import org.polarsys.capella.core.data.oa.OaPackage;
import org.polarsys.capella.core.transition.system.rules.common.InvolvementRule;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * 
 */
public class ActorCapabilityInvolvementRule extends InvolvementRule {

  @Override
  protected EClass getSourceType() {
    return CapellacorePackage.Literals.INVOLVEMENT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EClass getTargetType(EObject element, IContext context) {
    return super.getTargetType(element, context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected EStructuralFeature getTargetContainementFeature(EObject element, EObject result, EObject container, IContext context) {
    
    if (result instanceof CapabilityRealizationInvolvement) {
      return LaPackage.Literals.CAPABILITY_REALIZATION__OWNED_CAPABILITY_REALIZATION_INVOLVEMENTS;

    } else if (result instanceof CapabilityInvolvement) {
      return CtxPackage.Literals.CAPABILITY__OWNED_CAPABILITY_INVOLVEMENTS;

    } else if (result instanceof EntityOperationalCapabilityInvolvement) {
      return OaPackage.Literals.OPERATIONAL_CAPABILITY__OWNED_ENTITY_OPERATIONAL_CAPABILITY_INVOLVEMENTS;
    }

    return super.getTargetContainementFeature(element, result, container, context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void attachContainement(EObject element, EObject result, IContext context) {
    super.attachContainement(element, result, context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(EObject element, IContext context) throws Exception {
    super.apply(element, context);
  }

  @Override
  public boolean isApplicableOn(EObject element) {
    if (super.isApplicableOn(element)) {
      return (element instanceof EntityOperationalCapabilityInvolvement)
          || (element instanceof CapabilityInvolvement) || (element instanceof CapabilityRealizationInvolvement);
    }
    return false;
  }

}
