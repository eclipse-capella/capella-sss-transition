/*******************************************************************************
 * Copyright (c) 2020 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.crossphases.rules.information.communication;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.information.communication.CommunicationLink;
import org.polarsys.capella.transition.system2subsystem.crossphases.handlers.attachment.CrossPhasesAttachmentHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class CommunicationLinkRule extends org.polarsys.capella.transition.system2subsystem.rules.information.communication.CommunicationLinkRule {

  @Override
  protected EObject getSource(EObject source_p, IContext context_p) {
    CommunicationLink element = (CommunicationLink) source_p;
    return CrossPhasesAttachmentHelper.getInstance(context_p).getRelatedComponent((Component)element.eContainer(), context_p);
  }
  
}

