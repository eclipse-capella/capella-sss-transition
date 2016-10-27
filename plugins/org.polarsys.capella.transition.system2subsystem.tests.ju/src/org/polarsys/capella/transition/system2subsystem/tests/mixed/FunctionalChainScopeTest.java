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
package org.polarsys.capella.transition.system2subsystem.tests.mixed;

import java.util.Collection;

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

/**
 * Functional Chain: Test functional chain transition
 */
public class FunctionalChainScopeTest extends System2SubsystemTest implements Interphase, Crossphase {

  public static String FUNCTIONALCHAINSCOPE__PA__ROOT_PF__FC1 = "267c9f19-23b5-43ef-895b-ed38c8b1a04d"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINSCOPE__PA__ROOT_PF__FC1__FUNCTIONAL_CHAIN_INVOLVEMENT_TO_FE4 = "c7ebcd24-9f60-4e54-9386-16b6a2aec0ef"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINSCOPE__PA__ROOT_PF__PF4 = "28372abc-2877-4b2d-9eff-8d82e6dc78f3"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINSCOPE__PA__ROOT_PF__PF5 = "e465afa7-4dab-48e1-88de-2f53af219a20"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINSCOPE__PA__ROOT_PF__PF7 = "f07bf507-7a60-4ad5-90ce-563275c3b9c3"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINSCOPE__PA__ROOT_PF__FE4 = "ca02dce3-6084-442a-ad17-8be7b2cc86fc"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINSCOPE__PA__ROOT_PF__FE7 = "64a590be-2d99-491e-85ed-dda5344e1a8d"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINSCOPE__PA__ROOT_PF__FE8 = "8cefe6ae-4175-4427-85d2-e760dafe45a2"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINSCOPE__PA__PC1 = "d5d229cf-35e1-4889-87c3-35116c427473"; //$NON-NLS-1$
  public static String FUNCTIONALCHAINSCOPE__PA__PC2 = "c2ecdb44-91c3-4428-a8d3-3e5174cfe71b"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(FUNCTIONALCHAINSCOPE__PA__PC1, FUNCTIONALCHAINSCOPE__PA__PC2);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(FUNCTIONALCHAINSCOPE__PA__ROOT_PF__FC1);
    mustBeTransitioned(FUNCTIONALCHAINSCOPE__PA__ROOT_PF__PF4);
    mustBeTransitioned(FUNCTIONALCHAINSCOPE__PA__ROOT_PF__PF5);
    mustBeTransitioned(FUNCTIONALCHAINSCOPE__PA__ROOT_PF__FE4);
    mustBeTransitioned(FUNCTIONALCHAINSCOPE__PA__ROOT_PF__FC1__FUNCTIONAL_CHAIN_INVOLVEMENT_TO_FE4);

    shouldNotBeTransitioned(FUNCTIONALCHAINSCOPE__PA__ROOT_PF__PF7);
    shouldNotBeTransitioned(FUNCTIONALCHAINSCOPE__PA__ROOT_PF__FE7);
    shouldNotBeTransitioned(FUNCTIONALCHAINSCOPE__PA__ROOT_PF__FE8);
  }

}
