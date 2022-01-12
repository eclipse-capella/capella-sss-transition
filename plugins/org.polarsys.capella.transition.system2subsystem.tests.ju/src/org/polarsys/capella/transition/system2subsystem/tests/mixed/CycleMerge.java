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
package org.polarsys.capella.transition.system2subsystem.tests.mixed;

import static org.polarsys.capella.transition.system2subsystem.tests.util.ChainHelper.createBasicContext;
import static org.polarsys.capella.transition.system2subsystem.tests.util.ChainHelper.pathsToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.FunctionalChainExt;
import org.polarsys.capella.core.transition.common.context.TransitionContext;
import org.polarsys.capella.transition.system2subsystem.handlers.attachment.FunctionalChainAttachmentHelper;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class CycleMerge extends System2SubsystemTest implements Interphase, Crossphase {

  public static final String FC2 = "d4f83a9f-552f-4f17-bf1c-6821d36d7ba3"; //$NON-NLS-1$
  public static final String PF6 = "e6e6352b-0879-42ad-96d2-f61eb6cba194"; //$NON-NLS-1$
  public static final String PC_1 = "402726dd-45f4-4ab6-b910-9061e6b37d81"; //$NON-NLS-1$
  
  
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PF3 = "c5fc215b-ea7b-4b1f-98b5-60dd73dc70a5"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PF4 = "d2762ac2-d5a2-4ed6-9848-290e488eb435"; //$NON-NLS-1$
  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("CycleMerge", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return Collections.singleton(getObject(PC_1));
  }

  @Override
  protected void verify() {
    FunctionalChain fc = (FunctionalChain) mustBeTransitioned(FC2);
    assertTrue(
        fc.getOwnedFunctionalChainInvolvements().size() == 15);

    TransitionContext context = createBasicContext();
    
    FunctionalChainAttachmentHelper helper = new FunctionalChainAttachmentHelper() {

      protected Collection<FunctionalChain> getChainsToAnalyse(FunctionalChain element, IContext context_p) {
        BlockArchitecture architecture = BlockArchitectureExt.getRootBlockArchitecture(element);
        return FunctionalChainExt.getAllFunctionalChains(architecture);
      }

    };
    // helper.setValidElement(fc, null, context);

    Collection<String> paths;

    // Basic test, two paths
    // one is valid, one invalid, the both are retrieved. We don't look for invalids here
    // Paths must start with the first one.
    // Paths must end with the last one.
    //helper.setValidElement(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF2), false, context);
    paths = pathsToString(helper.getNextPathsTowards(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PF3),
        getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PF4), context));
    
    assertTrue(paths.contains("PF3/FE2/PF4/FE3/PF6/FE4/PF1/FE5/PF6/FE6/PF5/FE8/PF4/FE3/PF6/FE4/PF1/FE5/PF6/FE6/PF5/FE8/PF4"));
    assertTrue(!paths.contains("PF3/FE2/PF4/FE3/PF6/FE4/PF1/FE5/PF6/FE6/PF5/FE8/PF4")); //which is the merged path that is invalid
    assertTrue(paths.size() == 1);
  }

}
