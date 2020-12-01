/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.handlers.scope;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.requirement.RequirementsPkg;
import org.polarsys.capella.core.data.requirement.RequirementsTrace;
import org.polarsys.capella.core.transition.common.handlers.scope.IScopeRetriever;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * A scope retriever to retrieve all applied requirements for all scope's
 * Capella elements
 */
public class RequirementsScopeRetriever implements IScopeRetriever {

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

	/**
	 * {@inheritDoc}
	 */
	public Collection<? extends EObject> retrieveRelatedElements(EObject source_p, IContext context_p) {
		Collection<EObject> result = new LinkedList<EObject>();

		if ((source_p instanceof CapellaElement) && !(source_p instanceof RequirementsPkg)) {
			CapellaElement element = (CapellaElement) source_p;
			for (AbstractTrace trace : element.getOutgoingTraces()) {
				if (trace instanceof RequirementsTrace) {
					result.add(trace);
					result.add(trace.getTargetElement());
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<? extends EObject> retrieveSharedElements(IContext context_p) {
		return Collections.emptyList();
	}

}
