/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.preferences;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.polarsys.capella.common.flexibility.properties.property.PropertyPreference;
import org.polarsys.capella.common.flexibility.properties.schema.IPropertyContext;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.transition.system2subsystem.context.SubSystemContextHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * 
 */
public class SelectionPreference extends PropertyPreference {
  /**
   * {@inheritDoc}
   */
  @Override
  public IStatus validate(Object newValue_p, IPropertyContext context_p) {
    Object sourceContext = context_p.getSource();

    if (sourceContext == null || !(sourceContext instanceof IContext)) {
      return Status.OK_STATUS;
    }
    context_p.getProperties();

    IContext transitionContext = (IContext) context_p.getSource();
    Object mapping = transitionContext.get(ITransitionConstants.TRANSPOSER_MAPPING);
    // If mapping is null, the transition is multiphase
    if (mapping != null && SubSystemContextHelper.isMixedSelection(transitionContext)) {
      return new Status(IStatus.WARNING, getId(), Messages.MixedSelection_warningMessage);
    }
    return Status.OK_STATUS;
  }

}
