/*******************************************************************************
 * Copyright (c) 2016, 2019 THALES GLOBAL SERVICES.
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

import static org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Kind.CROSS_PHASES;

import java.util.Collection;

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

/**
 * Constraint transition: Root constraint should not create a root actor
 */
public class ConstraintTest extends System2SubsystemTest implements Crossphase, Interphase {

  private String _id_PHYSICAL_SYSTEM = "81d518a0-09b2-41da-8be6-7c81ad0233c2"; //$NON-NLS-1$
  private String _id_CONSTRAINT = "0a61df1b-47ad-44b2-bba1-fa094c7545bd"; //$NON-NLS-1$
  private String _id_PC_1 = "4dc46f7c-b442-434b-9dc5-b36b63ca4bb7"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_PC_1);
  }

  @Override
  protected void verify() {
    mustBeTransitioned(_id_PC_1);
    if (kind == CROSS_PHASES) {
      shouldNotBeTransitioned(_id_PHYSICAL_SYSTEM);
    }
    mustBeTransitioned(_id_CONSTRAINT);
    mustBeTransitionedOutside(_id_CONSTRAINT, ComponentType.ACTOR);
  }

}
