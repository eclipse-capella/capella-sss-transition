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
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementFunction;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
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

public class CycleOnMerge extends System2SubsystemTest implements Crossphase {

  public static final String FC2 = "bf929ddd-fa16-4be7-9961-2c1e2ef3e01c"; //$NON-NLS-1$
  public static final String PF2 = "7dde20f2-34c1-4e55-bfbb-736ade022ca6"; //$NON-NLS-1$

  public static final String PC_1 = "7f68434c-885a-4f5d-a47c-0282f639a7a9"; //$NON-NLS-1$

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("TestDoubleNext", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return Collections.singleton(getObject(PC_1));
  }

  @Override
  protected void verify() {
    FunctionalChain fc = (FunctionalChain) mustBeTransitioned(FC2);
    AbstractFunction fct = (AbstractFunction) mustBeTransitioned(PF2);
    assertTrue(FunctionalChainExt.isFunctionalChainValid(fc));

    List<FunctionalChainInvolvementLink> links = referencingFakeExchanges(fc);
    assertTrue(links.size() == 2);
    long countInvolvmentsOfFunction = fc.getOwnedFunctionalChainInvolvements().stream()
        .filter(x -> x instanceof FunctionalChainInvolvementFunction)
        .map(FunctionalChainInvolvementFunction.class::cast).map(x -> x.getInvolved()).filter(x -> fct.equals(x))
        .count();
    assertTrue(countInvolvmentsOfFunction == 2);
  }

}
