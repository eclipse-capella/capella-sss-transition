/*******************************************************************************
 * Copyright (c) 2023 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.tests.crossphases;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;

public class MergedComponentTest extends CrossPhasesTest {

  /** test */

  // Node Components
  public static final String TO_TRANSITION = "e5c3b7dc-eace-481f-b289-d93185e2a901"; //$NON-NLS-1$
  public static final String PC1 = "15a0e593-d6b1-4311-9297-ddcd54fd19ae"; //$NON-NLS-1$
  public static final String PC2 = "2925a98c-e206-44b2-bc32-11344e9bce15"; //$NON-NLS-1$
  public static final String INNER_PC2 = "7e61df23-19de-43c0-b414-97d2b4a5f83d"; //$NON-NLS-1$
  public static final String INNER_INNER_PC2 = "9fa1be42-1b35-4056-adba-10e1e33d8131"; //$NON-NLS-1$
  public static final String ACTOR = "c2c68abb-004d-4202-a70b-afc988539e60"; //$NON-NLS-1$
  public static final String INNER_ACTOR = "25ce4f56-7de6-45b2-965a-b5cdf393b087"; //$NON-NLS-1$
  
  // Parts
  public static final String TO_TRANSITION_P = "c5aee15a-7c1f-4c25-815e-3031b2c9e503"; //$NON-NLS-1$
  public static final String PC1_P = "096b65be-7088-4be3-bcba-207ba0124541"; //$NON-NLS-1$
  public static final String PC2_P = "fdc7b134-7c34-4d60-8d53-03f37031454b"; //$NON-NLS-1$
  public static final String INNER_PC2_P = "91c37953-11e0-445d-ad92-0240318e6f57"; //$NON-NLS-1$
  public static final String INNER_INNER_PC2_P = "f905d528-ba40-40e4-9693-7fea075b1ebe"; //$NON-NLS-1$
  public static final String ACTOR_P = "8fb11aab-6123-4e68-bf44-e59ec6b5d478"; //$NON-NLS-1$
  public static final String INNER_ACTOR_P = "771f2d79-def8-4aa1-969c-fa14ee839d39"; //$NON-NLS-1$
  
  //Physical Links
  public static final String PL_INNER_INNER_PC2 = "b4b928d3-0cb5-426c-8d60-bdb4f7ef2056"; //$NON-NLS-1$
  public static final String PL_INNER_ACTOR = "6fde1105-417e-4d16-8fb1-357369d56965"; //$NON-NLS-1$
  public static final String PL_PC1 = "bda76842-92a3-43e2-b540-c6031157e0ba"; //$NON-NLS-1$
  

  @Override
  protected Collection<?> getProjectionElements() {
    return Arrays.asList(getObject(TO_TRANSITION));
  }

  @Override
  protected void verify() {
    mustBeTransitioned(TO_TRANSITION);
    mustBeTransitioned(PC1);
    mustBeTransitioned(PC1_P);
    mustBeTransitioned(PC2);
    mustBeTransitioned(PC2_P);
    mustBeTransitioned(ACTOR);
    mustBeTransitioned(ACTOR_P);
    mustBeTransitioned(PL_INNER_INNER_PC2);
    mustBeTransitioned(PL_INNER_ACTOR);
    mustBeTransitioned(PL_PC1);

    shouldNotBeTransitioned(INNER_PC2);
    shouldNotBeTransitioned(INNER_PC2_P);
    shouldNotBeTransitioned(INNER_INNER_PC2_P);
    shouldNotBeTransitioned(INNER_INNER_PC2);
    shouldNotBeTransitioned(INNER_ACTOR_P);
    shouldNotBeTransitioned(INNER_ACTOR);

  }
  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("MergedComponents", "output"); //$NON-NLS-1$
  }
}
