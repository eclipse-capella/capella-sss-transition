/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FunctionPkg;
import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

public class HierarchicalFunctionAndPkgs {

  public static final String LC_1 = "ac7c9b59-8325-49db-883f-272e979fd2f2"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_1 = "448203bf-78ea-4429-bf1b-602b261b5203"; //$NON-NLS-1$
  public static final String LOGICALFUNCTIONPKG_1 = "2780bfd5-63de-483e-a3c1-80ec034ce08f"; //$NON-NLS-1$

  /**
   * When a Functional Chain is involving components not related to the subsystem, ti will create Fake functional
   * exchange to repair it. If the functions are stored under a FunctionPkg, and this functionPkg is propagated, then
   * the Functional Exchange will have to be created in a parent function since we can"t store exchanges under a
   * FunctionPkg.
   */
  public static class Always extends System2SubsystemTest implements Interphase, Crossphase {

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.HIERARCHICAL_EXPORT, IOptionsConstants.HIERARCHICAL_EXPORT_ALWAYS_VALUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(LC_1);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(FUNCTIONALCHAIN_1);
      EObject pkg = mustBeTransitioned(LOGICALFUNCTIONPKG_1);
      assertTrue("A fake FE shall be created on the parent of the Pkg",
          ((AbstractFunction) (((FunctionPkg) pkg).eContainer())).getOwnedFunctionalExchanges().stream()
              .filter(x -> x.getName().contains("FakeFE")).findAny().isPresent());
    }

    @Override
    public List<String> getRequiredTestModels() {
      return Arrays.asList("AllHierarchyPkg", "output"); //$NON-NLS-1$ //$NON-NLS-2$
    }

  }

}
