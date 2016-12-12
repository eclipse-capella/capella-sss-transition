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

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.capellacore.Structure;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;
import org.polarsys.capella.core.data.requirement.RequirementPackage;
import org.polarsys.capella.core.data.requirement.RequirementsPkg;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.core.transition.system.helpers.PackageHelper;
import org.polarsys.capella.core.transition.system.rules.AbstractCapellaElementRule;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * Manage transition of requirements packages
 */
public class RequirementsPkgRule extends AbstractCapellaElementRule {
	/**
	 * Constructor
	 */
	public RequirementsPkgRule() {
		super();
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
	 * @see
	 * org.polarsys.capella.core.transition.common.rules.AbstractRule#apply(org.
	 * eclipse.emf.ecore.EObject,
	 * org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext)
	 */
	@Override
	public void apply(EObject element, IContext context) throws Exception {
		super.apply(element, context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.polarsys.capella.core.transition.system.rules.
	 * AbstractCapellaElementRule#getSourceType()
	 */
	@Override
	protected EClass getSourceType() {
		return RequirementPackage.Literals.REQUIREMENTS_PKG;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.polarsys.capella.core.transition.system.rules.
	 * AbstractCapellaElementRule#attachRelated(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EObject,
	 * org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext)
	 */
	@Override
	protected void attachRelated(EObject element, EObject result, IContext context) {
		super.attachRelated(element, result, context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.polarsys.capella.core.transition.common.rules.AbstractRule#
	 * retrieveContainer(org.eclipse.emf.ecore.EObject, java.util.List,
	 * org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext)
	 */
	@Override
	protected void retrieveContainer(EObject element, List<EObject> result, IContext context) {
		if (PackageHelper.isPackage(element.eContainer(), context)) {
			super.retrieveContainer(element, result, context);
		}
	}

	@Override
	protected EObject transformDirectElement(EObject element, IContext context) {

		//If there is already a RequirementPkg in the target Architecture, we transform the first RequirementPkg to it.
		if (element.eContainer() instanceof BlockArchitecture) {
			EObject root = TransformationHandlerHelper.getInstance(context).getLevelElement(element, context);
			BlockArchitecture source = (BlockArchitecture) root;
			BlockArchitecture target = (BlockArchitecture) TransformationHandlerHelper.getInstance(context)
					.getBestTracedElement(root, context, CsPackage.Literals.BLOCK_ARCHITECTURE);
			if (target != null && source.getOwnedRequirementPkgs().indexOf(element) == 0) {
				Structure pkg = BlockArchitectureExt.getRequirementsPkg(target, false);
				if (pkg != null) {
					return pkg;
				}
			}
		}

		return super.transformDirectElement(element, context);
	}

}
