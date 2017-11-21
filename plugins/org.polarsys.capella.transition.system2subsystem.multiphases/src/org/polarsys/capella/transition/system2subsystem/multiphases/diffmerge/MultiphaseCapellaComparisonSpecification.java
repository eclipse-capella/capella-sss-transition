/*******************************************************************************
 * Copyright (c) 2006, 2017 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.multiphases.diffmerge;

import org.eclipse.emf.diffmerge.api.IMatchPolicy;
import org.eclipse.emf.diffmerge.ui.specification.IComparisonMethodFactory;
import org.eclipse.emf.diffmerge.ui.specification.IModelScopeDefinition;
import org.polarsys.capella.core.compare.CapellaComparisonMethod;

public class MultiphaseCapellaComparisonSpecification extends
		CapellaComparisonMethod {

	public MultiphaseCapellaComparisonSpecification(IModelScopeDefinition leftScopeDef,
      IModelScopeDefinition rightScopeDef, IModelScopeDefinition ancestorScopeDef, IComparisonMethodFactory factory) {
    super(leftScopeDef, rightScopeDef, ancestorScopeDef, factory);
  }

  @Override
	protected IMatchPolicy createMatchPolicy() {
		return new MultiphasesMatchPolicy();
	}

}
