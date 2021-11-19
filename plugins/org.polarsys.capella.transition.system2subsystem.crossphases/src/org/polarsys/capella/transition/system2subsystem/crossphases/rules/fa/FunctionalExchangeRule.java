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
package org.polarsys.capella.transition.system2subsystem.crossphases.rules.fa;

import static org.polarsys.capella.transition.system2subsystem.crossphases.Activator.PLUGIN_ID;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.business.queries.IBusinessQuery;
import org.polarsys.capella.core.business.queries.capellacore.BusinessQueriesProvider;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;;

public class FunctionalExchangeRule
    extends org.polarsys.capella.core.transition.system.rules.fa.FunctionalExchangeRule {

  @Override
  public IStatus transformRequired(EObject source, IContext context) {
    IStatus status = super.transformRequired(source, context);
    if (status.isOK()) {
      if ((source instanceof FunctionalExchange)) {
        FunctionalExchange functionalExchange = (FunctionalExchange) source;
        EList<ComponentExchange> allocatingExchanges = functionalExchange.getAllocatingComponentExchanges();
        // if no allocating exchanges found, no further check needed
        if (allocatingExchanges.size() < 1) {
          return status;
        }
        for (ComponentExchange componentExchange : allocatingExchanges) {
          IBusinessQuery query = BusinessQueriesProvider.getInstance().getContribution(
              componentExchange.eClass(),
              FaPackage.Literals.COMPONENT_EXCHANGE__OWNED_COMPONENT_EXCHANGE_FUNCTIONAL_EXCHANGE_ALLOCATIONS);
          List<EObject> availableFuncExhcnage = query.getAvailableElements(componentExchange);

          if (null != availableFuncExhcnage && !availableFuncExhcnage.isEmpty()) {
            if (!availableFuncExhcnage.contains(functionalExchange)) {
              return new Status(IStatus.ERROR, PLUGIN_ID,
                  componentExchange.getName() + " (" + componentExchange.eClass().getName() + ") should not allocate " //$NON-NLS-1$ //$NON-NLS-2$
                      + functionalExchange.getName() + " (" + functionalExchange.eClass().getName() + ")");
            }
          }
        }
      }
    }
    return status;
  }
}
