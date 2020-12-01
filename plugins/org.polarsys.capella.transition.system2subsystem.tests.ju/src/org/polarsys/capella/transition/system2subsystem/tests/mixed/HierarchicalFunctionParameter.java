/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
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

import java.util.Collection;

import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;
import org.polarsys.kitalpha.cadence.core.api.parameter.GenericParameter;

public class HierarchicalFunctionParameter {

  public static final String _id_lf1 = "57adac82-c4ff-40c4-8fbc-0ed45e82112e"; //$NON-NLS-1$
  public static final String _id_lf2 = "c14d7f97-e886-4e7d-8ee6-483bb5c05ef9"; //$NON-NLS-1$
  public static final String _id_lf21 = "e660eb17-c808-4cc4-b6ad-65ff91714b0a"; //$NON-NLS-1$
  public static final String _id_lf211 = "7d933c26-d0af-4c8a-906f-7dcb8827f085"; //$NON-NLS-1$
  public static final String _id_lf212 = "01e630b4-bbb3-44af-9534-90206eef484a"; //$NON-NLS-1$
  public static final String _id_lf22 = "32f5a2cf-c8bb-4644-9d1f-89195eff9b02"; //$NON-NLS-1$
  public static final String _id_lf221 = "d05d1b4d-b657-49bf-aa82-211e8278954e"; //$NON-NLS-1$
  public static final String _id_lf222 = "bcbfdd5c-c26b-47f0-97ae-8e3799f1fe6f"; //$NON-NLS-1$
  public static final String _id_lc1 = "a0df95d4-5682-47e0-a4e0-4a5c1c496f59"; //$NON-NLS-1$
  public static final String _id_lc2 = "b2a59163-d426-49a8-81b4-7a70f143a2dc"; //$NON-NLS-1$

  /**
   * Hierarchical Functions ALWAYS, Test all hierarchy functional transition
   */
  public static class Always extends System2SubsystemTest implements Interphase, Crossphase {
    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.HIERARCHICAL_EXPORT, IOptionsConstants.HIERARCHICAL_EXPORT_ALWAYS_VALUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_lc1);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_lf1);
      mustBeTransitioned(_id_lf2);
      mustBeTransitioned(_id_lf21);
      mustBeTransitioned(_id_lf211);
      mustBeTransitioned(_id_lf212);
      mustBeTransitioned(_id_lf22);
      mustBeTransitioned(_id_lf221);
      shouldNotBeTransitioned(_id_lf222);
    }
  }

  /**
   * Hierarchical Funtions LEAF, Test leaf functional transition
   */
  public static class Leaf extends System2SubsystemTest implements Interphase, Crossphase {

    @Override
    public void setUp() throws Exception {
      super.setUp();
      getHeadlessParameters().addSharedParameter(new GenericParameter<String>(IOptionsConstants.HIERARCHICAL_EXPORT,
          IOptionsConstants.HIERARCHICAL_EXPORT_LEAF_VALUE, IOptionsConstants.HIERARCHICAL_EXPORT));
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_lc1);
    }

    @SuppressWarnings("synthetic-access")
    @Override
    protected void verify() {

      mustBeTransitioned(_id_lf1);
      mustBeTransitioned(_id_lf211);
      mustBeTransitioned(_id_lf212);
      mustBeTransitioned(_id_lf221);
      shouldNotBeTransitioned(_id_lf2);
      shouldNotBeTransitioned(_id_lf21);
      shouldNotBeTransitioned(_id_lf22);
      shouldNotBeTransitioned(_id_lf222);
    }

  }

  /**
   * Hierarchical Funtions RESTRICTED: Test restricted value functional transition
   */
  public static class Restricted extends System2SubsystemTest implements Interphase, Crossphase {

    @Override
    public void setUp() throws Exception {
      super.setUp();
      getHeadlessParameters().addSharedParameter(new GenericParameter<String>(IOptionsConstants.HIERARCHICAL_EXPORT,
          IOptionsConstants.HIERARCHICAL_EXPORT_RESTRICTED_VALUE, IOptionsConstants.HIERARCHICAL_EXPORT));
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_lc1);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_lf1);
      mustBeTransitioned(_id_lf21);
      mustBeTransitioned(_id_lf211);
      mustBeTransitioned(_id_lf212);
      mustBeTransitioned(_id_lf221);
      shouldNotBeTransitioned(_id_lf2);
      shouldNotBeTransitioned(_id_lf22);
      shouldNotBeTransitioned(_id_lf222);
    }
  }
}
