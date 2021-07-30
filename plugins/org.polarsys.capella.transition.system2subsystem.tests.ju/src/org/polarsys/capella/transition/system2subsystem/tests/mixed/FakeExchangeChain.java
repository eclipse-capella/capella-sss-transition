/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.tests.mixed;

import static org.polarsys.capella.transition.system2subsystem.tests.util.ChainHelper.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.core.data.fa.SequenceLink;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

public class FakeExchangeChain {

  public static final String FUNCTIONALCHAIN_1 = "bfec6f2e-1b98-4d2e-baae-2b19bb7c86df"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_2 = "4fae1c9d-e0ff-4ad4-8f88-90fa17d6ef93"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_3 = "288283c5-ebe8-43c7-ac56-a8e01032af5e"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_4 = "5fd43e8a-4507-4f9f-9741-97d52219f984"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_5 = "7c366a6a-8a73-43d4-976a-384be6fe40fe"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_6 = "0cf7e508-f578-4f36-b557-aad49d3bc3cb"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_7 = "f2d48136-0afe-449e-bb49-2f646708ab6f"; //$NON-NLS-1$
  public static final String LC_1 = "4e1113c6-2eec-4861-b9c1-1e5da7645fa1"; //$NON-NLS-1$

  public static final String LOGICALFUNCTION_1 = "55a8311e-5670-4260-b8d1-c2264214c7a9"; //$NON-NLS-1$
  public static final String LOGICALFUNCTION_2 = "261bddac-a1d3-4a4c-a412-c2b9fa501c8d"; //$NON-NLS-1$
  public static final String LOGICALFUNCTION_3 = "b7172cd5-dfaa-4ce1-9d62-68b4cc9972c9"; //$NON-NLS-1$
  public static final String LOGICALFUNCTION_4 = "8f5ea4b1-ae0b-481c-9c29-31a13181dd3c"; //$NON-NLS-1$
  public static final String LOGICALFUNCTION_5 = "cc5fd15e-353f-4a3a-b53e-c64a7e89c7ba"; //$NON-NLS-1$
  public static final String LOGICALFUNCTION_6 = "fd3892e7-c2ba-41a9-b11b-63a8e6555317"; //$NON-NLS-1$

  public static final String SEQUENCE_LINK_FC4_1 = "d1a4d963-20bf-4a0f-a519-b2ad4dcfb42e"; //$NON-NLS-1$
  public static final String SEQUENCE_LINK_FC4_2 = "8c9ce71b-0fcd-411e-b181-4ac0899a1c79"; //$NON-NLS-1$

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
      FunctionalChain chain1 = (FunctionalChain) mustBeTransitioned(FUNCTIONALCHAIN_1);
      FunctionalChain chain2 = (FunctionalChain) mustBeTransitioned(FUNCTIONALCHAIN_2);
      FunctionalChain chain3 = (FunctionalChain) mustBeTransitioned(FUNCTIONALCHAIN_3);
      FunctionalChain chain4 = (FunctionalChain) mustBeTransitioned(FUNCTIONALCHAIN_4);
      FunctionalChain chain5 = (FunctionalChain) mustBeTransitioned(FUNCTIONALCHAIN_5);
      FunctionalChain chain6 = (FunctionalChain) mustBeTransitioned(FUNCTIONALCHAIN_6);
      FunctionalChain chain7 = (FunctionalChain) mustBeTransitioned(FUNCTIONALCHAIN_7);

      AbstractFunction LF1 = (AbstractFunction) mustBeTransitioned(LOGICALFUNCTION_1);
      AbstractFunction LF2 = (AbstractFunction) mustBeTransitioned(LOGICALFUNCTION_2);
      AbstractFunction LF5 = (AbstractFunction) mustBeTransitioned(LOGICALFUNCTION_5);
      
      // Chain1 shall have only one FakeExchange rather than 2 (one has been merged)
      assertTrue(referencingFakeExchanges(chain1).size() == 1);
      FunctionalChainInvolvementLink link = referencingFakeExchange(chain1);
      assertTrue(link.getSource().getInvolved() == LF2);
      assertTrue(link.getTarget().getInvolved() == LF5);

      // Chain2 shall have only one FakeExchange rather than 2 (one has been merged)
      assertTrue(referencingFakeExchanges(chain2).size() == 1);
      FunctionalChainInvolvementLink link2 = referencingFakeExchange(chain2);
      assertTrue(link2.getSource().getInvolved() == LF2);
      assertTrue(link2.getTarget().getInvolved() == LF5);

      // Chain3 shall have a sequencelink where its source and target have been merged to one involvement
      assertTrue((((SequenceLink) mustBeTransitioned(SEQUENCE_LINK_FC4_1)).getTarget()) 
          == ((((SequenceLink) mustBeTransitioned(SEQUENCE_LINK_FC4_2)).getTarget())));
      
      // Chain4 shall contains 3 FCR (there might have an hidden filter hiding it)
      assertTrue(referencingFunctionalChains(chain4, chain5).size() == 1);
      assertTrue(referencingFunctionalChains(chain4, chain6).size() == 1);
      assertTrue(referencingFunctionalChains(chain4, chain7).size() == 1);
      List<FunctionalChainInvolvementLink> links = referencingFakeExchanges(chain4);
      assertTrue(links.size() == 2);
      assertTrue(links.get(0).getSource().getInvolved() == LF2);
      assertTrue(links.get(0).getTarget().getInvolved() == LF5);
      assertTrue(links.get(1).getSource().getInvolved() == LF2);
      assertTrue(links.get(1).getTarget().getInvolved() == LF5);
      assertTrue(fcilReferencingFunctions(chain4, LF2).size() == 1);
      
      //Chain5 shall have two FCI to LF2
      assertTrue(fcifReferencingFunctions(chain5, LF2).size() == 2);

      //Chain6 shall have only one FCI
      //the FCI(LF2) shall be merged to one and since it is linked to other invovlements in parent chain, shall not be filtered
      assertTrue(chain6.getOwnedFunctionalChainInvolvements().size() == 1);
      assertTrue(fcifReferencingFunctions(chain6, LF2).size() == 1);
      
      //Chain7 shall have 3 FCI only
      assertTrue(chain7.getOwnedFunctionalChainInvolvements().size() == 3);
    }

    @Override
    public List<String> getRequiredTestModels() {
      return Arrays.asList("FakeExchangeChain", "output"); //$NON-NLS-1$ //$NON-NLS-2$
    }

  }

}
