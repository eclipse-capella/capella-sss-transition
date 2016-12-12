/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.handlers.scope;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.capella.core.data.requirement.RequirementsPkg;
import org.polarsys.capella.core.transition.common.handlers.options.OptionsHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.scope.IScopeFilter;
import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * A scope filter on Requirements and RequirementsPkg An element is filtered if
 * it has been filtered or one of these parents have been filtered
 */
public class RequirementsScopeFilter implements IScopeFilter {

	public static final String REQUIREMENTS_SCOPE_FILTER = "REQUIREMENTS_SCOPE_FILTER"; //$NON-NLS-1$

	public static RequirementsScopeFilter getInstance(IContext context) {
		RequirementsScopeFilter handler = (RequirementsScopeFilter) context
				.get(RequirementsScopeFilter.REQUIREMENTS_SCOPE_FILTER);
		if (handler == null) {
			handler = new RequirementsScopeFilter();
			handler.init(context);
			context.put(RequirementsScopeFilter.REQUIREMENTS_SCOPE_FILTER, handler);
		}
		return handler;
	}

	public Collection<EObject> getElements(IContext context_p) {
		Collection<EObject> elements = OptionsHandlerHelper.getInstance(context_p).getCollectionValue(context_p,
				IOptionsConstants.SYSTEM2SUBSYSTEM_PREFERENCES, IOptionsConstants.REQUIREMENTS_ELEMENTS,
				IOptionsConstants.REQUIREMENTS_ELEMENTS_DEFAULT_VALUE);
		return elements;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isValidScopeElement(EObject element_p, IContext context_p) {
		if ((element_p instanceof Requirement) || (element_p instanceof RequirementsPkg)) {
			Collection<EObject> elements = getElements(context_p);
			EObject element = element_p;
			while ((element instanceof Requirement) || (element instanceof RequirementsPkg)) {
				if (elements.contains(element)) {
					return true;
				}
				element = element.eContainer();
			}
			return false;
		}
		return true;
	}

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

}
