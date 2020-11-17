/*******************************************************************************
 * Copyright (c) 2020 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.rules.fa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.common.helpers.EObjectExt;
import org.polarsys.capella.core.data.fa.ControlNode;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementFunction;
import org.polarsys.capella.core.data.fa.SequenceLink;
import org.polarsys.capella.core.data.fa.SequenceLinkEnd;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.attachment.FunctionalChainAttachmentHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class ControlNodeRule extends org.polarsys.capella.core.transition.system.rules.fa.ControlNodeRule {
  @Override
  public IStatus transformRequired(EObject element, IContext context) {
    IStatus result = super.transformRequired(element, context);
    if (result.isOK()) {
      // A ControlNode is valid if all its source and target FCIFs are valid
      ControlNode controlNode = (ControlNode) element;
      FunctionalChainAttachmentHelper helper = FunctionalChainAttachmentHelper.getInstance(context);
      boolean invalidSource = getSourceFCIFs(controlNode).stream()
          .anyMatch(inv -> !helper.isValidElement(inv, context));
      boolean invalidTarget = getTargetFCIFs(controlNode).stream()
          .anyMatch(inv -> !helper.isValidElement(inv, context));
      if (invalidSource || invalidTarget) {
        result = new Status(IStatus.WARNING, Messages.Activity_Transformation,
            NLS.bind("Control Node ''{0}'' is not valid.", LogHelper.getInstance().getText(controlNode)));
      }
    }
    return result;
  }

  // TODO: Move me to Capella's code
  private Set<FunctionalChainInvolvementFunction> getSourceFCIFs(ControlNode controlNode) {
    Set<ControlNode> visited = new HashSet<>();
    return getSourceFCIFs(controlNode, visited);
  }
  
  private Set<FunctionalChainInvolvementFunction> getSourceFCIFs(ControlNode controlNode, Set<ControlNode> visited) {
    Set<FunctionalChainInvolvementFunction> sourceFCIFs = new HashSet<>();
    List<EObject> incomingSequenceLinks = EObjectExt.getReferencers(controlNode,
        FaPackage.Literals.SEQUENCE_LINK__TARGET);
    for (EObject eObj : incomingSequenceLinks) {
      if (eObj instanceof SequenceLink) {
        SequenceLink sequenceLink = (SequenceLink) eObj;
        SequenceLinkEnd source = sequenceLink.getSource();
        if (source instanceof FunctionalChainInvolvementFunction) {
          sourceFCIFs.add((FunctionalChainInvolvementFunction) source);
        } else if (source instanceof ControlNode && !visited.contains(source)) {
          visited.add((ControlNode) source);
          sourceFCIFs.addAll(getSourceFCIFs((ControlNode) source, visited));
        }
      }
    }
    return sourceFCIFs;
  }

  // TODO: Move me to Capella's code
  private Set<FunctionalChainInvolvementFunction> getTargetFCIFs(ControlNode controlNode) {
    Set<ControlNode> visited = new HashSet<>();
    return getTargetFCIFs(controlNode, visited);
  }
  
  private Set<FunctionalChainInvolvementFunction> getTargetFCIFs(ControlNode controlNode, Set<ControlNode> visited) {
    Set<FunctionalChainInvolvementFunction> targetFCIFs = new HashSet<>();
    List<EObject> outgoingSequenceLinks = EObjectExt.getReferencers(controlNode,
        FaPackage.Literals.SEQUENCE_LINK__SOURCE);
    for (EObject eObj : outgoingSequenceLinks) {
      if (eObj instanceof SequenceLink) {
        SequenceLink sequenceLink = (SequenceLink) eObj;
        SequenceLinkEnd target = sequenceLink.getTarget();
        if (target instanceof FunctionalChainInvolvementFunction) {
          targetFCIFs.add((FunctionalChainInvolvementFunction) target);
        } else if (target instanceof ControlNode && !visited.contains(target)) {
          visited.add((ControlNode) target);
          targetFCIFs.addAll(getTargetFCIFs((ControlNode) target, visited));
        }
      }
    }
    return targetFCIFs;
  }
}