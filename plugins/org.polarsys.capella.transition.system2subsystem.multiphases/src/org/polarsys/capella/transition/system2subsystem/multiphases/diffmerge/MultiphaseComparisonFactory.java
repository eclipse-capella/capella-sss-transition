/*******************************************************************************
 * Copyright (c) 2006, 2017 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.multiphases.diffmerge;

import org.eclipse.emf.diffmerge.ui.specification.IComparisonMethod;
import org.eclipse.emf.diffmerge.ui.specification.IModelScopeDefinition;
import org.polarsys.capella.core.compare.CapellaComparisonMethodFactory;

public class MultiphaseComparisonFactory extends CapellaComparisonMethodFactory {

	@Override
	public String getLabel() {
		return "Multiphase Capella Comparison"; //$NON-NLS-1$
	}

	/**
   * {@inheritDoc}
   */
  @Override
  public IComparisonMethod createComparisonMethod(IModelScopeDefinition leftScopeSpec_p, IModelScopeDefinition rightScopeSpec_p,
      IModelScopeDefinition ancestorScopeSpec_p) {
    return new MultiphaseCapellaComparisonSpecification(leftScopeSpec_p, rightScopeSpec_p, ancestorScopeSpec_p, this);
  }

}
