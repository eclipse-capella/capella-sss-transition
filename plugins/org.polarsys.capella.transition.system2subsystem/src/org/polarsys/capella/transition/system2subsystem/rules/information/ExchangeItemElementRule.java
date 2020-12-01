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
package org.polarsys.capella.transition.system2subsystem.rules.information;

import org.polarsys.capella.core.data.information.InformationPackage;

public class ExchangeItemElementRule extends org.polarsys.capella.core.transition.system.rules.information.ExchangeItemElementRule {

  public ExchangeItemElementRule() {
    registerUpdatedAttribute(InformationPackage.Literals.EXCHANGE_ITEM_ELEMENT__KIND);
    registerUpdatedAttribute(InformationPackage.Literals.EXCHANGE_ITEM_ELEMENT__DIRECTION);
    registerUpdatedAttribute(InformationPackage.Literals.EXCHANGE_ITEM_ELEMENT__COMPOSITE);
  }

}
