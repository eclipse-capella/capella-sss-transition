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
package org.polarsys.capella.transition.system2subsystem.multiphases.handlers.attachment;

import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.options.OptionsHandlerHelper;
import org.polarsys.capella.transition.system2subsystem.crossphases.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.crossphases.handlers.attachment.CrossPhasesAttachmentHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 *
 */
public class PAAttachmentHelper extends CrossPhasesAttachmentHelper {

  @Override
  protected boolean shouldMerge(Component element_p, IContext context_p) {

    if (ContextScopeHandlerHelper.getInstance(context_p).contains(ITransitionConstants.SOURCE_SCOPE, element_p, context_p)) {
      if ((element_p instanceof PhysicalComponent) && (((PhysicalComponent) element_p).getNature() == PhysicalComponentNature.NODE)) {
        return false;
      }
      if ((element_p instanceof PhysicalComponent) && (((PhysicalComponent) element_p).getNature() == PhysicalComponentNature.BEHAVIOR)) {
        return true;
      }
    }
    return (element_p instanceof PhysicalComponent)
           || OptionsHandlerHelper.getInstance(context_p).getBooleanValue(context_p, IOptionsConstants.SYSTEM2SUBSYSTEM_CROSSPHASES_PREFERENCES,
               IOptionsConstants.COMPONENT_MERGE, IOptionsConstants.COMPONENT_MERGE__DEFAULT_VALUE);
  }

}
