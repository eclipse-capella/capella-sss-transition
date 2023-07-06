/*******************************************************************************
 * Copyright (c) 2022, 2023 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.activities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.transition.common.activities.AbstractActivity;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.attachment.AttachmentHelper;
import org.polarsys.capella.core.transition.common.handlers.attachment.IAttachmentHandler;
import org.polarsys.capella.core.transition.common.handlers.options.OptionsHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.traceability.TraceabilityHandlerHelper;
import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.kitalpha.transposer.api.ITransposerWorkflow;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * Updates component management properties of the transformed element in relation to the selected element
 */
public class CopyManagementPropertiesActivity extends AbstractActivity implements ITransposerWorkflow {

  public static final String ID = "org.polarsys.capella.transition.system2subsystem.activities.CopyManagementPropertiesActivity"; //$NON-NLS-1$

  @Override
  protected IStatus _run(ActivityParameters activityParams) {
    IContext context = (IContext) activityParams.getParameter(TRANSPOSER_CONTEXT).getValue();

    boolean shouldExportStatus = OptionsHandlerHelper.getInstance(context).getBooleanValue(context,
        IOptionsConstants.SYSTEM2SUBSYSTEM_PREFERENCES, IOptionsConstants.MANAGEMENT_PROGRESS_STATUS_EXPORT,
        IOptionsConstants.MANAGEMENT_PROGRESS_STATUS_DEFAULT_VALUE);
    boolean shouldExportReview = OptionsHandlerHelper.getInstance(context).getBooleanValue(context,
        IOptionsConstants.SYSTEM2SUBSYSTEM_PREFERENCES, IOptionsConstants.MANAGEMENT_REVIEW_EXPORT,
        IOptionsConstants.MANAGEMENT_REVIEW_DEFAULT_VALUE);
    boolean shouldExportVisibleInDoc = OptionsHandlerHelper.getInstance(context).getBooleanValue(context,
        IOptionsConstants.SYSTEM2SUBSYSTEM_PREFERENCES, IOptionsConstants.MANAGEMENT_VISIBLE_IN_DOCUMENT_EXPORT,
        IOptionsConstants.MANAGEMENT_VISIBLE_IN_DOCUMENT_DEFAULT_VALUE);
    boolean shouldExportVisibleForTraceability = OptionsHandlerHelper.getInstance(context).getBooleanValue(context,
        IOptionsConstants.SYSTEM2SUBSYSTEM_PREFERENCES, IOptionsConstants.MANAGEMENT_VISIBLE_FOR_TRACEABILITY_EXPORT,
        IOptionsConstants.MANAGEMENT_VISIBLE_FOR_TRACEABILITY_DEFAULT_VALUE);

    IAttachmentHandler attachmentHelper = AttachmentHelper.getInstance(context);

    List<EObject> transformedElementsCollection = ((Collection<?>) context
        .get(ITransitionConstants.TRANSFORMED_ELEMENTS)).stream().map(EObject.class::cast).collect(Collectors.toList());

    for (EObject candidateComponent : transformedElementsCollection) {

      // the sources used for creating the new models
      Collection<EObject> candidateSourceElements = TraceabilityHandlerHelper.getInstance(context)
          .retrieveSourceElements(candidateComponent, context);

      if (candidateSourceElements.iterator().hasNext()) {

        EObject sourceElement = candidateSourceElements.iterator().next();

        EnumerationPropertyLiteral status = sourceElement instanceof CapellaElement ? ((CapellaElement) sourceElement).getStatus() : null;

        if (status != null) {
          Collection<EObject> tracedElements = TraceabilityHandlerHelper.getInstance(context)
              .retrieveTracedElements(status, context);

          if (tracedElements.iterator().hasNext()) {
            EObject tracedStatus = tracedElements.iterator().next();

            if (shouldExportStatus) {
              attachmentHelper.attachElementByReference(candidateComponent, tracedStatus,
                  CapellacorePackage.Literals.CAPELLA_ELEMENT__STATUS);
            }
          }
        }

        if (shouldExportReview) {
          attachmentHelper.updateElementAttribute(sourceElement, candidateComponent,
              CapellacorePackage.Literals.CAPELLA_ELEMENT__REVIEW, context);
        }
        if (shouldExportVisibleInDoc) {
          attachmentHelper.updateElementAttribute(sourceElement, candidateComponent,
              ModellingcorePackage.Literals.PUBLISHABLE_ELEMENT__VISIBLE_IN_DOC, context);
        }
        if (shouldExportVisibleForTraceability) {
          attachmentHelper.updateElementAttribute(sourceElement, candidateComponent,
              ModellingcorePackage.Literals.PUBLISHABLE_ELEMENT__VISIBLE_IN_LM, context);
        }
      }

    }

    return Status.OK_STATUS;
  }

}
