/*******************************************************************************
 * Copyright (c) 2026 THALES GLOBAL SERVICES.
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
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

//@formatter:off
/**
 * InterfacePkg transition: Test if ExchangeItems from different phases are properly attached to InterfacePkg
 */
// @formatter:on

public class InterfacePkgTest extends System2SubsystemTest implements Interphase, Crossphase {

  private final String ei_from_la1 = "a86aeed3-084c-4618-b23a-bd385f92ecb0"; //$NON-NLS-1$
  private final String ei_from_la2 = "f1309e1f-e30b-47ed-8027-41f12017ef50"; //$NON-NLS-1$
  private final String ei_from_sa = "8b25cc2d-e9c4-4203-8000-05e2b9bf6612"; //$NON-NLS-1$
  private final String lc = "_nxYVII2dEd6Q36a6kYkaHQ"; //$NON-NLS-1$

  public InterfacePkgTest() {
    super();
    setKind(Kind.CROSS_PHASES);
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(lc);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(ei_from_la1);
    mustBeTransitioned(ei_from_la2);
    mustBeTransitioned(ei_from_sa);
  }

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("In-Flight Entertainment System", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }

}
