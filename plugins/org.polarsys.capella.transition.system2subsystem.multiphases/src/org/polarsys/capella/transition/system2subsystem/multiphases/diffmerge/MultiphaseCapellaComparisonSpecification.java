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

import org.eclipse.emf.diffmerge.generic.api.IMatchPolicy;
import org.eclipse.emf.diffmerge.ui.specification.IComparisonMethodFactory;
import org.eclipse.emf.diffmerge.ui.specification.IModelScopeDefinition;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.compare.CapellaComparisonMethod;

public class MultiphaseCapellaComparisonSpecification extends
		CapellaComparisonMethod {

	public MultiphaseCapellaComparisonSpecification(IModelScopeDefinition leftScopeDef,
      IModelScopeDefinition rightScopeDef, IModelScopeDefinition ancestorScopeDef, IComparisonMethodFactory<EObject> factory) {
    super(leftScopeDef, rightScopeDef, ancestorScopeDef, factory);
  }

  @Override
	protected IMatchPolicy<EObject> createMatchPolicy() {
		return new MultiphasesMatchPolicy();
	}

}
