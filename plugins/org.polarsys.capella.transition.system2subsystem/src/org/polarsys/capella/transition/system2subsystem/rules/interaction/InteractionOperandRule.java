/*******************************************************************************
 * Copyright (c) 2006, 2015 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.rules.interaction;

import org.eclipse.emf.ecore.EClass;
import org.polarsys.capella.core.data.interaction.InteractionPackage;

/**
 * 
 */
public class InteractionOperandRule extends InteractionFragmentRule {

  /**
   * {@inheritDoc}
   */
  @Override
  protected EClass getSourceType() {
    return InteractionPackage.Literals.INTERACTION_OPERAND;
  }

  /**
   * @param eclass_p
   */
  public InteractionOperandRule() {
    super();
  }

}
