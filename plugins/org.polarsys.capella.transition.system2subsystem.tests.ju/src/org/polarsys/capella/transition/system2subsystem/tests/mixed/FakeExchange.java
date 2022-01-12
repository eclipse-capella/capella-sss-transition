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
import static org.polarsys.capella.transition.system2subsystem.tests.util.ChainHelper.referencingFakeExchanges;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.context.TransitionContext;
import org.polarsys.capella.core.transition.common.handlers.scope.DefaultScopeHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.CompoundTraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.transformation.DefaultTransformationHandler;
import org.polarsys.capella.transition.system2subsystem.rules.fa.FunctionalChainInvolvementRule;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class FakeExchange extends System2SubsystemTest implements Crossphase {
  public static final String FC1 = "8baf312d-4602-4e9d-b3d1-117fd4570991"; //$NON-NLS-1$
  public static final String LC_1 = "ad104709-761a-44a8-8d17-d96bc90a423b"; //$NON-NLS-1$
  
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF2 = "9c3862a7-b540-4505-8135-b3d768c8b7cf"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_FE3 = "35f970ad-c132-4cbf-b347-dc0795d1da65"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF5 = "2ec1eed9-1ea4-41be-ad77-7cb536713e4a"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_FE4 = "7d8a9e3f-c5da-4b53-b885-8bfdbe925fa6"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF3 = "646a49ae-53c6-4aa8-9fda-922334aa5cee"; //$NON-NLS-1$
  
  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("FakeExchange", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return Collections.singleton(getObject(LC_1));
  }

  @Override
  protected void verify() {
    FunctionalChain fc = (FunctionalChain) mustBeTransitioned(FC1);
    List<FunctionalChainInvolvementLink> links = referencingFakeExchanges(fc);
    assertTrue(links.size() == 1);
    TransitionContext context = createBasicContext();
    Collection<FunctionalChainInvolvement> fcis = new FunctionalChainInvolvementRule().listInvolvments(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF2), getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF3), context);
    assertTrue(!fcis.contains(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF2)));
    assertTrue(!fcis.contains(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF3)));
    assertTrue(fcis.contains(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_FE3)));
    assertTrue(fcis.contains(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_LF5)));
    assertTrue(fcis.contains(getObject(FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_FE4)));
  }

  protected TransitionContext createContext() {

    TransitionContext context = new TransitionContext();
    context.put(ITransitionConstants.SCOPE_HANDLER, new DefaultScopeHandler() {
      @Override
      public boolean isInScope(EObject element, IContext context) {
        return true;
      }
    });
    context.put(ITransitionConstants.TRANSFORMATION_HANDLER, new DefaultTransformationHandler() {
      @Override
      public IStatus isOrWillBeTransformed(EObject source, IContext context) {
        return Status.OK_STATUS;
      }
    });
    context.put(ITransitionConstants.TRACEABILITY_TRANSFORMATION_HANDLER, new CompoundTraceabilityHandler(null) {
      @Override
      public boolean isTraced(EObject element, IContext context) {
        return true;
      }
    });
    return context;
  }
}
