/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.FunctionalChainExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.context.TransitionContext;
import org.polarsys.capella.core.transition.common.handlers.scope.DefaultScopeHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.CompoundTraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.transformation.DefaultTransformationHandler;
import org.polarsys.capella.transition.system2subsystem.handlers.attachment.FunctionalChainAttachmentHelper;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

import static org.polarsys.capella.transition.system2subsystem.tests.util.ChainHelper.*;

public class CyclePremices extends System2SubsystemTest implements Crossphase {

  public static final String FC1 = "9757b43d-88a6-419c-a530-198dfd11712b"; //$NON-NLS-1$
  public static final String FC2 = "a21fdc36-5f9c-4efa-bede-c29475c2754d"; //$NON-NLS-1$
  public static final String FC3 = "eb337843-955c-4b9a-a301-7af5eb8d940d"; //$NON-NLS-1$
  public static final String LC_1 = "ad104709-761a-44a8-8d17-d96bc90a423b"; //$NON-NLS-1$

  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_FE19 = "a6c4786d-0945-4626-bf62-9700273e5812"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF1 = "3617564a-af66-4b6a-ae0e-1b70e6b814fc"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF8 = "d54d04dc-6b83-4d04-a427-3c8dbc9373f6"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_FE17 = "3b86dfb7-f773-4f0a-909e-7102adb04d20"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF2 = "44b7cce6-fcca-475e-8dda-8e14573a351a"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_FE2 = "8450df92-b93d-4f51-ab45-a768204681ce"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF3 = "345a31d8-c596-4ec5-8858-d636558881c7"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_FE4 = "40a75bac-48f6-4e80-8009-1c9a54497531"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF4 = "1c4780ec-32a1-4d94-b331-890be6877fd8"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF6 = "88be76ed-0228-4cd9-9941-9d08904dd4b3"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_FE6 = "934f192e-ae81-4d85-85bc-36e1becfd416"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_FE8 = "f0433aee-7d3f-40c9-a5fd-5e2623c5c40a"; //$NON-NLS-1$
  
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF19 = "a31c11d1-1a34-4783-9d72-5a05410df558"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF17 = "1a5da1fe-b32f-4c1a-8e11-2c3c6ffca2e0"; //$NON-NLS-1$
  
  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("CyclePremices2", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return Collections.singleton(getObject(LC_1));
  }

  @Override
  protected void verify() {
    FunctionalChain fcsource = (FunctionalChain) getObject(FC3);
    FunctionalChain fc = (FunctionalChain) mustBeTransitioned(FC3);
    assertTrue(
        fc.getOwnedFunctionalChainInvolvements().size() == fcsource.getOwnedFunctionalChainInvolvements().size());

    TransitionContext context = createBasicContext();
    
    // Here we allow lookup on chains, as we test the helpers.
    FunctionalChainAttachmentHelper helper = new FunctionalChainAttachmentHelper() {

      protected Collection<FunctionalChain> getChainsToAnalyse(FunctionalChain element, IContext context_p) {
        BlockArchitecture architecture = BlockArchitectureExt.getRootBlockArchitecture(element);
        return FunctionalChainExt.getAllFunctionalChains(architecture);
      }

    };

    Collection<String> paths;

    // Basic test, two paths
    // one is valid, one invalid, the both are retrieved. We don't look for invalids here
    // Paths must start with the first one.
    // Paths must end with the last one.
    helper.setValidElement(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF2), false, context);
    paths = pathsToString(helper.getNextPathsTowards(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF8),
        getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF4), context));
    assertTrue(paths.contains("LF8/FE19/LF1/FE6/LF6/FE8/LF3/FE4/LF4"));
    assertTrue(paths.contains("LF8/FE19/LF1/FE17/LF2/FE2/LF3/FE4/LF4"));

    // This chain contains cycle. We exclude it and retrieve two paths.
    paths = pathsToString(helper.getNextPathsTowards(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF19),
        getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF17), context));
    assertTrue(paths.contains("LF19/FE20/LF14/FE7/LF18/FE9/LF16/FE5/LF17"));
    assertTrue(paths.contains("LF19/FE20/LF14/FE18/LF15/FE3/LF16/FE5/LF17"));

  }

}
