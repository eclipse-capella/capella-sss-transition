/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.crossphases.rules.la;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.la.LaPackage;
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.core.transition.common.handlers.options.OptionsHandlerHelper;
import org.polarsys.capella.transition.system2subsystem.crossphases.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.crossphases.handlers.attachment.CrossPhasesAttachmentHelper;
import org.polarsys.capella.transition.system2subsystem.crossphases.rules.pa.Component2SARule;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IPremise;


public class LogicalComponentRule extends Component2SARule {

  @Override
  protected EClass getSourceType() {
    return LaPackage.Literals.LOGICAL_COMPONENT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void premicesRelated(EObject element, ArrayList<IPremise> needed) {
    super.premicesRelated(element, needed);

    // Disable merge of components if option is disabled
    if (!OptionsHandlerHelper.getInstance(getCurrentContext()).getBooleanValue(getCurrentContext(),
        IOptionsConstants.SYSTEM2SUBSYSTEM_CROSSPHASES_PREFERENCES, IOptionsConstants.COMPONENT_MERGE, IOptionsConstants.COMPONENT_MERGE__DEFAULT_VALUE)) {
      return;
    }
    LogicalComponent component = (LogicalComponent) element;

    for (Part part : component.getRepresentingParts()) {
      needed.addAll(createDefaultPrecedencePremices((Collection) ComponentExt.getPartAncestors(part, true), "part"));
    }
  }

  @Override
  protected void retrieveGoDeep(EObject source, List<EObject> result, IContext context) {
    super.retrieveGoDeep(source, result, context);

    // Disable merge of components if option is disabled
    if (!OptionsHandlerHelper.getInstance(getCurrentContext()).getBooleanValue(getCurrentContext(),
        IOptionsConstants.SYSTEM2SUBSYSTEM_CROSSPHASES_PREFERENCES, IOptionsConstants.COMPONENT_MERGE, IOptionsConstants.COMPONENT_MERGE__DEFAULT_VALUE)) {
      return;
    }

    if (ContextScopeHandlerHelper.getInstance(context).contains(ITransitionConstants.SOURCE_SCOPE, source, context)) {
      if (source instanceof LogicalComponent) {
        LogicalComponent element = (LogicalComponent) source;
        for (Part part : element.getContainedParts()) {
          result.add(part);
          ContextScopeHandlerHelper.getInstance(context).add(ITransitionConstants.SOURCE_SCOPE, part, context);
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public IStatus transformRequired(EObject source, IContext context) {
    LogicalComponent element = (LogicalComponent) source;

    // Disable merge of components if option is disabled
    if (!OptionsHandlerHelper.getInstance(context).getBooleanValue(context, IOptionsConstants.SYSTEM2SUBSYSTEM_CROSSPHASES_PREFERENCES,
        IOptionsConstants.COMPONENT_MERGE, IOptionsConstants.COMPONENT_MERGE__DEFAULT_VALUE)) {
      return super.transformRequired(source, context);
    }

    Collection<EObject> transfoSources = (Collection<EObject>) context.get(ITransitionConstants.TRANSITION_SOURCES);
    if (transfoSources.contains(element)) {
      return Status.OK_STATUS;
    }

    Component src = CrossPhasesAttachmentHelper.getInstance(context).getRelatedComponent(element, context);
    if (src != element) {
      return new Status(IStatus.WARNING, Messages.Activity_Transformation, NLS.bind("Component ''{0}'' will be merged into ''{1}''", LogHelper.getInstance()
          .getText(element), LogHelper.getInstance().getText(src)));
    }

    return super.transformRequired(source, context);
  }
}
