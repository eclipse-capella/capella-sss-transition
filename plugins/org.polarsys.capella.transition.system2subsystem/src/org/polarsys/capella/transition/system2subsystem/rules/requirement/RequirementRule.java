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
package org.polarsys.capella.transition.system2subsystem.rules.requirement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.capella.core.data.requirement.RequirementPackage;
import org.polarsys.capella.core.data.requirement.RequirementsPkg;
import org.polarsys.capella.core.data.requirement.SystemFunctionalInterfaceRequirement;
import org.polarsys.capella.core.data.requirement.SystemFunctionalRequirement;
import org.polarsys.capella.core.data.requirement.SystemNonFunctionalInterfaceRequirement;
import org.polarsys.capella.core.data.requirement.SystemNonFunctionalRequirement;
import org.polarsys.capella.core.data.requirement.SystemUserRequirement;
import org.polarsys.capella.core.transition.common.handlers.attachment.AttachmentHelper;
import org.polarsys.capella.core.transition.common.handlers.traceability.TraceabilityHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.core.transition.system.rules.AbstractCapellaElementRule;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IPremise;

/**
 * Manage transition of requirements
 */
public class RequirementRule extends AbstractCapellaElementRule {
	/**
	 * Constructor
	 */
	public RequirementRule() {
		super();
		registerUpdatedAttribute(RequirementPackage.Literals.REQUIREMENT__ADDITIONAL_INFORMATION);
		registerUpdatedAttribute(RequirementPackage.Literals.REQUIREMENT__FEATURE);
		registerUpdatedAttribute(RequirementPackage.Literals.REQUIREMENT__IMPLEMENTATION_VERSION);
		registerUpdatedAttribute(RequirementPackage.Literals.REQUIREMENT__IS_OBSOLETE);
		registerUpdatedAttribute(RequirementPackage.Literals.REQUIREMENT__REQUIREMENT_ID);
		registerUpdatedAttribute(RequirementPackage.Literals.REQUIREMENT__VERIFICATION_METHOD);
		registerUpdatedAttribute(RequirementPackage.Literals.REQUIREMENT__VERIFICATION_PHASE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.polarsys.capella.core.transition.system.rules.core.PropertyValueRule#
	 * attachRelated(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EObject,
	 * org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext)
	 */
	@Override
	protected void attachRelated(EObject element_p, EObject result_p, IContext context_p) {
		super.attachRelated(element_p, result_p, context_p);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.polarsys.capella.core.transition.system.rules.core.PropertyValueRule#
	 * premicesRelated(org.eclipse.emf.ecore.EObject, java.util.ArrayList)
	 */
	@Override
	protected void premicesRelated(EObject element, ArrayList<IPremise> needed) {
		super.premicesRelated(element, needed);
		needed.addAll(createDefaultPrecedencePremices(element,
				RequirementPackage.Literals.REQUIREMENT__RELATED_CAPELLA_ELEMENTS));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.polarsys.capella.core.transition.system.rules.core.PropertyValueRule#
	 * retrieveContainer(org.eclipse.emf.ecore.EObject, java.util.List,
	 * org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext)
	 */
	@Override
	protected void retrieveContainer(EObject element, List<EObject> result, IContext context) {
		if ((element.eContainer() instanceof RequirementsPkg)) {
			super.retrieveContainer(element, result, context);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.polarsys.capella.core.transition.system.rules.
	 * AbstractCapellaElementRule#isOrderedContainment(org.eclipse.emf.ecore.
	 * EObject)
	 */
	@Override
	protected boolean isOrderedContainment(EObject element) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.polarsys.capella.core.transition.system.rules.
	 * AbstractCapellaElementRule#getSourceType()
	 */
	@Override
	protected EClass getSourceType() {
		return RequirementPackage.Literals.REQUIREMENT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.polarsys.capella.core.transition.common.rules.AbstractRule#
	 * getDefaultContainer(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EObject,
	 * org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext)
	 */
	@Override
	protected EObject getDefaultContainer(EObject element, EObject result, IContext context) {
		EObject root = TransformationHandlerHelper.getInstance(context).getLevelElement(element, context);
		BlockArchitecture target = (BlockArchitecture) TransformationHandlerHelper.getInstance(context)
				.getBestTracedElement(root, context, CsPackage.Literals.BLOCK_ARCHITECTURE, element, result);
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.polarsys.capella.core.transition.system.rules.
	 * AbstractCapellaElementRule#retrieveGoDeep(org.eclipse.emf.ecore.EObject,
	 * java.util.List,
	 * org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext)
	 */
	@Override
	protected void retrieveGoDeep(EObject source, List<EObject> result, IContext context) {
		super.retrieveGoDeep(source, result, context);
	}
}
