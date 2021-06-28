/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests.mixed;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FunctionPkg;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

public class FakeChainInvolvementLink {

  public static final String LC_1 = "ac7c9b59-8325-49db-883f-272e979fd2f2"; //$NON-NLS-1$
  
  public static final String FCIL_TO_FAKEFE_LOGICALFUNCTION_2_LOGICALFUNCTION_3 = "ID_FakeFunctionalChainInvolvement_System_a8784ea3-f4ac-48d9-b923-ba25fbd4ac65_f7f8cf2f-e142-48af-bee1-70fc20b9c030"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_1 = "448203bf-78ea-4429-bf1b-602b261b5203"; //$NON-NLS-1$
  
  public static final String FAKEFE_LOGICALFUNCTION_2_LOGICALFUNCTION_3 = "ID_FakeFunctionalExchange_System_91e6e9c6-05df-4a16-8df4-12a01bb6ea48_419ac5e5-3d7a-45c9-bc42-664c88ddb054"; //$NON-NLS-1$
  
  /**
   * When a Functional Chain is involving components not related to the subsystem, ti will create Fake functional
   * exchange to repair it. If the functions are stored under a FunctionPkg, and this functionPkg is propagated, then
   * the Functional Exchange will have to be created in a parent function since we can"t store exchanges under a
   * FunctionPkg.
   */
  public static class Always extends System2SubsystemTest implements Interphase, Crossphase {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(LC_1);
    }

    @Override
    protected void verify() {
      FunctionalChain chain = (FunctionalChain) mustBeTransitioned(FUNCTIONALCHAIN_1);
      EObject e = getOutputModelResource().getEObject(FCIL_TO_FAKEFE_LOGICALFUNCTION_2_LOGICALFUNCTION_3);
      assertTrue(((FunctionalChainInvolvementLink)e).getId().equals(((FunctionalChainInvolvementLink)e).getSid()));
      assertTrue(chain.getOwnedFunctionalChainInvolvements().size() == 7);
    }

    protected String getOutputModelPlatformURIString() {
      return "/FakeChainInvolvementLinkSubsystem/FakeChainInvolvementLinkSubsystem.melodymodeller";
    }
    
    @Override
    public List<String> getRequiredTestModels() {
      return Arrays.asList("FakeChainInvolvementLink", "FakeChainInvolvementLinkSubsystem"); //$NON-NLS-1$ //$NON-NLS-2$
    }

  }

}
