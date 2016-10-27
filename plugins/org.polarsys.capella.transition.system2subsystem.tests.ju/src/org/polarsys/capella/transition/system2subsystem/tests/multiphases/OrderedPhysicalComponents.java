/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests.multiphases;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.transition.system2subsystem.tests.util.GitHelper;

/**
 * Test that verifies the invariant output when two components are reordered
 */
public class OrderedPhysicalComponents extends MultiPhasesTest {

  public static String MP_ORDERED__PA__PC_4 = "b271ea03-5360-43a3-823f-1f4aa88f83ba"; //$NON-NLS-1$
  public static String MP_ORDERED__PA__PC_5 = "3161c9e8-a19f-49eb-98e2-ed3e867a9a08"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(MP_ORDERED__PA__PC_4);
  }

  @Override
  public void test() throws Exception {
    super.test();

    TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(getProject());
    domain.getCommandStack().execute(new RecordingCommand(domain) {
      @Override
      protected void doExecute() {
        // Rearrange PC4 and PC5 as first components
        PhysicalComponent pc4 = (PhysicalComponent) getObject(MP_ORDERED__PA__PC_4);
        PhysicalComponent pc5 = (PhysicalComponent) getObject(MP_ORDERED__PA__PC_5);
        EList<PhysicalComponent> physicalComponents = ((PhysicalComponent) pc4.eContainer())
            .getOwnedPhysicalComponents();
        physicalComponents.move(0, pc4);
        physicalComponents.move(0, pc5);
      }
    });

    GitHelper helper = GitHelper
        .create(URI.createPlatformResourceURI(getOutputModelPlatformURIString(), true).trimSegments(1));
    executeTransition();
    helper.verifyNoChange();
  }

}
