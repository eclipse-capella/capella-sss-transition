/*******************************************************************************
 * Copyright (c) 2026 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.rules.cs;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.core.data.capellacore.Structure;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class InterfacePkgRule extends org.polarsys.capella.core.transition.system.rules.cs.InterfacePkgRule {

  public InterfacePkgRule() {
    super();
  }

  @Override
  protected EObject transformDirectElement(EObject element, IContext context) {
    if (element.eContainer() instanceof BlockArchitecture) {
      EObject root = TransformationHandlerHelper.getInstance(context).getLevelElement(element, context);
      BlockArchitecture target = (BlockArchitecture) TransformationHandlerHelper.getInstance(context)
          .getBestTracedElement(root, context, CsPackage.Literals.BLOCK_ARCHITECTURE);

      Structure result = BlockArchitectureExt.getInterfacePkg(target, true);
      if (result != null) {
        if (!BlockArchitectureExt.isDefaultNameInterfacePkg((AbstractNamedElement) element)) {
          ((AbstractNamedElement) result).setName(((AbstractNamedElement) element).getName());
        }
        return result;
      }

    }
    return super.transformDirectElement(element, context);
  }

}
