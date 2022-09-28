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
package org.polarsys.capella.transition.system2subsystem.tests.interphases;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

public class BehaviorComponentTest extends System2SubsystemTest implements Interphase {

  /** test */

  // Behavior Components
  public static final String PC3 = "3395feb6-133a-4010-b704-087d552f16d6"; //$NON-NLS-1$
  public static final String PC4 = "52249b27-24ac-4413-9ff0-629255cb6c04"; //$NON-NLS-1$
  public static final String PC5 = "4e872ac2-85d1-4ac3-a0d3-f01108f24c5f"; //$NON-NLS-1$
  public static final String PC6 = "386a6905-ac6b-4604-b87f-3044a871b79a"; //$NON-NLS-1$
  public static final String PC7 = "0afa9fa3-5a4f-4136-8ff5-2561f9baea26"; //$NON-NLS-1$
  public static final String PC8 = "6ffcdbb5-6041-4e0e-b198-acbc68375a71"; //$NON-NLS-1$

  // Node Components
  public static final String PC1 = "4d3e90bf-de01-403b-9e0f-3d3881a4e389"; //$NON-NLS-1$
  public static final String PC2 = "44dd39bf-9bcd-4c61-ba2f-58f41b12cdab"; //$NON-NLS-1$
  public static final String PC11 = "17d5c35f-25c6-4fab-8273-727f6d791e65"; //$NON-NLS-1$
  public static final String PC12 = "5ad4a7e0-7369-4099-b88d-4854265ec3dd"; //$NON-NLS-1$
  public static final String PC21 = "5c2dbdd0-f5fb-4ad0-926c-4bcb6d187bee"; //$NON-NLS-1$
  public static final String PC22 = "d243ffc2-9c3f-4530-a4c7-6e1ad96cd101"; //$NON-NLS-1$

  // Actor
  public static final String PA2 = "f260bb57-7652-4541-9f15-65498acc003a"; //$NON-NLS-1$

  // Actor Part
  public static final String PA2_PART = "3bd548de-2849-4672-a3b6-aa4e6f797458"; //$NON-NLS-1$

  // Behavior Components Parts
  public static final String PC3_PART = "29c64d47-1432-4d34-b9d8-994a89393786"; //$NON-NLS-1$
  public static final String PC4_PART = "4b2ca85e-490d-4317-bb9b-4d7d3163a243"; //$NON-NLS-1$
  public static final String PC5_PART = "956f727e-85f5-4f60-abb3-83a8e7d9267d"; //$NON-NLS-1$
  public static final String PC6_PART = "e6c824f1-c61c-4b88-863d-ed0cb495b539"; //$NON-NLS-1$
  public static final String PC7_PART = "2bb92b38-5144-4f6b-bc7b-229a0bfb78fd"; //$NON-NLS-1$
  public static final String PC8_PART = "08ffc4da-5b9b-4b68-8f89-5200bf764508"; //$NON-NLS-1$

  // Node Components Parts
  public static final String PC1_PART = "f0e343dd-5be7-4430-b073-5b6e6e39de95"; //$NON-NLS-1$
  public static final String PC2_PART = "3c13f170-f38a-4798-8c51-9926fafb0279"; //$NON-NLS-1$
  public static final String PC11_PART = "5fb6f116-be14-4737-af2b-4dc2e94a053e"; //$NON-NLS-1$
  public static final String PC12_PART = "60ea3137-71c7-40f2-9d8a-54c395fe37fc"; //$NON-NLS-1$
  public static final String PC21_PART = "4ce563b3-c1d5-489d-8ab1-b8ad052c42d6"; //$NON-NLS-1$
  public static final String PC22_PART = "270557bd-e056-4d0f-a94b-f8c26835edd0"; //$NON-NLS-1$

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("Project_test_01", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return Arrays.asList(getObject(PC3), getObject(PC8));
  }

  @Override
  protected void verify() {
    mustBeTransitioned(PA2);
    mustBeTransitioned(PA2_PART);
    mustBeTransitioned(PC3);
    mustBeTransitioned(PC3_PART);
    mustBeTransitioned(PC4);
    mustBeTransitioned(PC4_PART);
    mustBeTransitioned(PC5);
    mustBeTransitioned(PC5_PART);
    mustBeTransitioned(PC6);
    mustBeTransitioned(PC6_PART);
    mustBeTransitioned(PC7);
    mustBeTransitioned(PC7_PART);
    mustBeTransitioned(PC8);
    mustBeTransitioned(PC8_PART);

    shouldNotBeTransitioned(PC1);
    shouldNotBeTransitioned(PC1_PART);
    shouldNotBeTransitioned(PC2);
    shouldNotBeTransitioned(PC2_PART);
    shouldNotBeTransitioned(PC11);
    shouldNotBeTransitioned(PC11_PART);
    shouldNotBeTransitioned(PC12);
    shouldNotBeTransitioned(PC12_PART);
    shouldNotBeTransitioned(PC21);
    shouldNotBeTransitioned(PC21_PART);
    shouldNotBeTransitioned(PC22);
    shouldNotBeTransitioned(PC22_PART);

  }
}
