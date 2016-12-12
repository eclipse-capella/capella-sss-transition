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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.common.ui.services.helper.EObjectLabelProviderHelper;
import org.polarsys.capella.core.data.capellacore.Involvement;
import org.polarsys.capella.core.data.requirement.RequirementPackage;
import org.polarsys.capella.core.data.requirement.RequirementsPkg;
import org.polarsys.capella.core.data.requirement.RequirementsTrace;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.attachment.AttachmentHelper;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.core.transition.system.rules.AbstractCapellaElementRule;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IPremise;

/**
 */
public class RequirementsTraceRule extends AbstractCapellaElementRule {

  /**
   * {@inheritDoc}
   */
  @Override
  protected EClass getSourceType() {
    return RequirementPackage.Literals.REQUIREMENTS_TRACE;
  }

  @Override
  protected void retrieveRequired(EObject element, List<EObject> result, IContext context) {
    super.retrieveRequired(element, result, context);
    result.add(((RequirementsTrace) element).getSourceElement());
    result.add(((RequirementsTrace) element).getTargetElement());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void premicesRelated(EObject eObject1, ArrayList<IPremise> needed) {
    super.premicesRelated(eObject1, needed);
    RequirementsTrace element = (RequirementsTrace) eObject1;
    needed.addAll(createDefaultPrecedencePremices(element, ModellingcorePackage.Literals.ABSTRACT_TRACE__SOURCE_ELEMENT));
    needed.addAll(createDefaultPrecedencePremices(element, ModellingcorePackage.Literals.ABSTRACT_TRACE__TARGET_ELEMENT));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void retrieveContainer(EObject element, List<EObject> result, IContext context) {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void attachRelated(EObject element, EObject result, IContext context) {
    super.attachRelated(element, result, context);
    AttachmentHelper.getInstance(context).attachTracedElements(element, result, ModellingcorePackage.Literals.ABSTRACT_TRACE__SOURCE_ELEMENT, context);
    AttachmentHelper.getInstance(context).attachTracedElements(element, result, ModellingcorePackage.Literals.ABSTRACT_TRACE__TARGET_ELEMENT, context);
  }

  @Override
  public IStatus transformRequired(EObject element, IContext context) {
    IStatus result = super.transformRequired(element, context);
    if (result.isOK()) {
    	RequirementsTrace ce = (RequirementsTrace) element;

      if (ce.getSourceElement() == null) {
        return new Status(IStatus.WARNING, Messages.Activity_Transformation, org.polarsys.capella.core.transition.system.constants.Messages.SourceNull);
      }
      if (ce.getTargetElement() == null) {
        return new Status(IStatus.WARNING, Messages.Activity_Transformation, org.polarsys.capella.core.transition.system.constants.Messages.TargetNull);
      }
      if (!TransformationHandlerHelper.getInstance(context).isOrWillBeTransformed(ce.getSourceElement(), context).isOK()) {
        return new Status(IStatus.WARNING, Messages.Activity_Transformation, NLS.bind(
            org.polarsys.capella.core.transition.system.constants.Messages.SourceBoundNotTransitioned, EObjectLabelProviderHelper.getText(ce.getSourceElement())));
      }
      if (!TransformationHandlerHelper.getInstance(context).isOrWillBeTransformed(ce.getTargetElement(), context).isOK()) {
        return new Status(IStatus.WARNING, Messages.Activity_Transformation, NLS.bind(
            org.polarsys.capella.core.transition.system.constants.Messages.TargetBoundNotTransitioned, EObjectLabelProviderHelper.getText(ce.getTargetElement())));
      }
    }
    return result;
  }
}
