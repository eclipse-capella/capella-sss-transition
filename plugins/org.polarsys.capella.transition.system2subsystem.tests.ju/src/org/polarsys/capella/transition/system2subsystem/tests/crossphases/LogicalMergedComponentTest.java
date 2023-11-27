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

public class LogicalMergedComponentTest extends CrossPhasesTest {

  /** test */

  public static final String LA_2_P = "7fd279fd-a01a-4f64-91b6-45c7cc1276db"; //$NON-NLS-1$
  public static final String LC_1_P = "01e781ea-2ddd-44fe-83d3-43defef6d43d"; //$NON-NLS-1$
  public static final String LC_3_P = "b1e05dd8-0af9-448f-b6f1-66178d37405f"; //$NON-NLS-1$
  public static final String LC_2_P = "0bc6251a-2dc5-4b5e-b630-3c87fc4182e3"; //$NON-NLS-1$

  public static final String LA_2 = "d0831762-9d80-4c97-9998-69d49dde8839"; //$NON-NLS-1$
  public static final String LC_1 = "f839e8c6-1731-4282-b49e-0e9cf29aba02"; //$NON-NLS-1$
  public static final String LC_3 = "8d53ed78-3bde-43c1-be30-951091791022"; //$NON-NLS-1$
  public static final String LC_2 = "ebe13e17-6f58-4a61-854d-ed1dd1b7fea7"; //$NON-NLS-1$
  
  public static final String C_1 = "e86192d4-58c6-4c22-9df9-7a83180894c4"; //$NON-NLS-1$
  public static final String C_3 = "e9f568c3-38ec-42a9-bd14-966a99043ed3"; //$NON-NLS-1$
  public static final String C_2 = "8b7b81af-081e-4a78-baf5-986561d5996f"; //$NON-NLS-1$
  
  public static final String INNER_LC_LC = "f1fbbd3f-e977-4c22-b2db-60313f61b185"; //$NON-NLS-1$
  public static final String INNER_ACTOR = "44021642-9163-4834-bd92-41a3456ed546"; //$NON-NLS-1$
  public static final String INNER_LC = "03f9d6d7-fa5f-479e-a6b9-7db99d3eabc2"; //$NON-NLS-1$
  public static final String INNER_LC_P = "752b4e61-b59b-40a0-8581-5600fcdf8f8b"; //$NON-NLS-1$

  
  
  @Override
  protected Collection<?> getProjectionElements() {
    return Arrays.asList(getObject(LC_3));
  }

  @Override
  protected void verify() {
    mustBeTransitioned(LC_3);
    
    mustBeTransitioned(LA_2_P);
    mustBeTransitioned(LC_3_P);
    mustBeTransitioned(LC_2_P);
    mustBeTransitioned(LA_2);
    mustBeTransitioned(LC_2);
    mustBeTransitioned(C_1);
    mustBeTransitioned(C_3);
    mustBeTransitioned(C_2);

    shouldNotBeTransitioned(INNER_LC_LC);
    shouldNotBeTransitioned(INNER_ACTOR);
    shouldNotBeTransitioned(INNER_LC);
    shouldNotBeTransitioned(INNER_LC_P);

  }
  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("MergedComponents", "output"); //$NON-NLS-1$
  }
}
