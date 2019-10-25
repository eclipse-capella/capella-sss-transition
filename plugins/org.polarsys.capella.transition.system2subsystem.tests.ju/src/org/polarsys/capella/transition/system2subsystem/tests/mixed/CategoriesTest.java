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
import java.util.Collections;

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;

public abstract class CategoriesTest extends System2SubsystemTest {

  public static String CATEGORIES__PA__PC_1 = "2be2c019-7ccb-4e52-8321-52e9cbb8d416"; //$NON-NLS-1$
  public static String CATEGORIES__PA__PHYSICAL_CONTEXT__PLC4 = "7ed175b5-2499-47a2-88ee-e8f6b6049065"; //$NON-NLS-1$
  public static String CATEGORIES__PA__CEC1 = "a2f46b67-54f2-44f8-9cc4-03515911b770"; //$NON-NLS-1$
  public static String CATEGORIES__PA__CEC2 = "b8253656-a9d6-42d0-af2a-27d163630b75"; //$NON-NLS-1$
  public static String CATEGORIES__PA__CEC3 = "3850f885-8004-4338-8ece-7b8c8ebfba8f"; //$NON-NLS-1$
  public static String CATEGORIES__PA__CEC4 = "42924573-f1a1-485b-8bb6-dd8b1bb1fb5e"; //$NON-NLS-1$
  public static String CATEGORIES__PA__PLC1 = "57c7ffbe-32ef-4785-8441-29795d2dff54"; //$NON-NLS-1$
  public static String CATEGORIES__PA__PLC2 = "351db26a-f658-47fe-89eb-073688216a42"; //$NON-NLS-1$
  public static String CATEGORIES__PA__PC_1__PLC3 = "5f60fbdd-ea32-470f-b353-f6cb366834a4"; //$NON-NLS-1$

  public static class Interphases extends CategoriesTest {

    public Interphases() {
      setKind(Kind.INTER_PHASES);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return Collections.singleton(getObject(CATEGORIES__PA__PC_1));
    }

    @Override
    protected void verify() {
      mustBeTransitioned(CATEGORIES__PA__PHYSICAL_CONTEXT__PLC4);
      mustBeTransitioned(CATEGORIES__PA__CEC1);
      mustBeTransitioned(CATEGORIES__PA__CEC3);
      mustBeTransitioned(CATEGORIES__PA__PLC1);

      mustBeTransitioned(CATEGORIES__PA__PC_1__PLC3);
      shouldNotBeTransitioned(CATEGORIES__PA__PLC2);
      mustBeTransitioned(CATEGORIES__PA__CEC2);
      shouldNotBeTransitioned(CATEGORIES__PA__CEC4);

    }
  }

  public static class Multiphase extends CategoriesTest {

    public Multiphase() {
      setKind(Kind.MULTI_PHASES);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return Collections.singleton(getObject(CATEGORIES__PA__PC_1));
    }

    @Override
    protected void verify() {

      // SA
      mustBeTransitionedInto(CATEGORIES__PA__PHYSICAL_CONTEXT__PLC4, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(CATEGORIES__PA__CEC1, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(CATEGORIES__PA__CEC3, ComponentType.SYSTEM_ANALYSIS);
      mustBeTransitionedInto(CATEGORIES__PA__PLC1, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(CATEGORIES__PA__PC_1__PLC3, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(CATEGORIES__PA__PLC2, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(CATEGORIES__PA__CEC2, ComponentType.SYSTEM_ANALYSIS);
      shouldNotBeTransitionedInto(CATEGORIES__PA__CEC4, ComponentType.SYSTEM_ANALYSIS);

      // LA
      mustBeTransitionedInto(CATEGORIES__PA__CEC2, ComponentType.LOGICAL_ARCHITECTURE); // Internal CEC
      mustBeTransitionedInto(CATEGORIES__PA__PHYSICAL_CONTEXT__PLC4, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(CATEGORIES__PA__CEC1, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(CATEGORIES__PA__CEC3, ComponentType.LOGICAL_ARCHITECTURE);
      mustBeTransitionedInto(CATEGORIES__PA__PLC1, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(CATEGORIES__PA__PC_1__PLC3, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(CATEGORIES__PA__PLC2, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(CATEGORIES__PA__CEC4, ComponentType.LOGICAL_ARCHITECTURE);

      // PA
      mustBeTransitionedInto(CATEGORIES__PA__PC_1__PLC3, ComponentType.PHYSICAL_ARCHITECTURE); // Internal PLC
      mustBeTransitionedInto(CATEGORIES__PA__PHYSICAL_CONTEXT__PLC4, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(CATEGORIES__PA__CEC1, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(CATEGORIES__PA__CEC3, ComponentType.PHYSICAL_ARCHITECTURE);
      mustBeTransitionedInto(CATEGORIES__PA__PLC1, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(CATEGORIES__PA__CEC2, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(CATEGORIES__PA__PLC2, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(CATEGORIES__PA__CEC4, ComponentType.PHYSICAL_ARCHITECTURE);
    }

  }

  public static class Crossphases extends CategoriesTest {

    public Crossphases() {
      setKind(Kind.CROSS_PHASES);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(CATEGORIES__PA__PC_1);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(CATEGORIES__PA__PHYSICAL_CONTEXT__PLC4);
      mustBeTransitioned(CATEGORIES__PA__CEC1);
      mustBeTransitioned(CATEGORIES__PA__CEC3);
      mustBeTransitioned(CATEGORIES__PA__PLC1);

      shouldNotBeTransitioned(CATEGORIES__PA__PC_1__PLC3);
      shouldNotBeTransitioned(CATEGORIES__PA__PLC2);
      shouldNotBeTransitioned(CATEGORIES__PA__CEC2);
      shouldNotBeTransitioned(CATEGORIES__PA__CEC4);
    }
  }

}
