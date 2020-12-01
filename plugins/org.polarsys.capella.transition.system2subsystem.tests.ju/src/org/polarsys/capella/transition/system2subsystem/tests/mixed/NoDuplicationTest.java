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

import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;

//@formatter:off
/**
 * identifier:name:'Logical Functions',id=#b42524f9-0fb3-42a3-ad75-af6971f8db82
 * identifier:name:'Logical System',id=#536d4e6d-1212-4576-a7a6-f98760db7ff5

 * - Create 'Logical Functions' [LogicalFunctionPkg]
 *   - Create 'LF1' [LogicalFunction]
 *     - Create 'LF11' [LogicalFunction]
 *       - Create 'FIP111' [FunctionInputPort]
 *     - Create 'LF12' [LogicalFunction]
 *       - Create 'FIP121' [FunctionInputPort]
 *       - Create 'FOP121' [FunctionOutputPort]
 *     - Create 'LF13' [LogicalFunction]
 *       - Create 'FOP131' [FunctionOutputPort]
 *     - Create '[Function Realization] to Root System Function' [FunctionRealization]
 *       > Link '[Function Realization] to Root System Function' to 'LF1' [sourceElement], 'Root System Function' [targetElement]
 *     - Create 'FE11' [FunctionalExchange]
 *       > Link 'FE11' to 'FOP131' [source], 'FIP121' [target], 
 *     - Create 'FE12' [FunctionalExchange]
 *       > Link 'FE12' to 'FOP121' [source], 'FIP111' [target], 
 * - Create 'Logical System' [LogicalComponent]
 *   - Create 'CE1' [ComponentExchange]
 *     > Link 'CE1' to 'CP111' [source], 'CP21' [target]
 *     - Create '[Component Exchange Functional Exchange Allocation] to FE12' [ComponentExchangeFunctionalExchangeAllocation]
 *       > Link '[Component Exchange Functional Exchange Allocation] to FE12' to 'CE1' [sourceElement], 'FE12' [targetElement]
 *   - Create 'CE2' [ComponentExchange]
 *     > Link 'CE2' to 'CP12' [source], 'CP22' [target]
 *   - Create 'LC1: LC1' [Part]
 *     > Link 'LC1: LC1' to 'LC1' [abstractType]
 *   - Create 'LC2: LC2' [Part]
 *     > Link 'LC2: LC2' to 'LC2' [abstractType]
 *   - Create 'LC1' [LogicalComponent]
 *     - Create '[Component Functional Allocation] to LF13' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to LF13' to 'LC1' [sourceElement], 'LF13' [targetElement]
 *     - Create 'LC11: LC11' [Part]
 *       > Link 'LC11: LC11' to 'LC11' [abstractType]
 *     - Create 'CP12' [ComponentPort]
 *       > Set 'CP12' to 'FLOW' [kind], 'OUT' [orientation]
 *     - Create 'LC11' [LogicalComponent]
 *       - Create '[Component Functional Allocation] to LF12' [ComponentFunctionalAllocation]
 *         > Link '[Component Functional Allocation] to LF12' to 'LC11' [sourceElement], 'LF12' [targetElement]
 *       - Create 'CP111' [ComponentPort]
 *         > Set 'CP111' to 'FLOW' [kind], 'OUT' [orientation]
 *         - Create '[Port Allocation] to FOP121' [PortAllocation]
 *           > Link '[Port Allocation] to FOP121' to 'CP111' [sourceElement], 'FOP121' [targetElement]
 *   - Create 'LC2' [LogicalComponent]
 *     - Create '[Component Functional Allocation] to LF11' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to LF11' to 'LC2' [sourceElement], 'LF11' [targetElement]
 *     - Create 'CP21' [ComponentPort]
 *       > Set 'CP21' to 'FLOW' [kind], 'IN' [orientation]
 *       - Create '[Port Allocation] to FIP111' [PortAllocation]
 *         > Link '[Port Allocation] to FIP111' to 'CP21' [sourceElement], 'FIP111' [targetElement]
 *     - Create 'CP22' [ComponentPort]
 *       > Set 'CP22' to 'FLOW' [kind], 'IN' [orientation]
 *   - Create '[System Realization] to System' [SystemRealization]
 *     > Link '[System Realization] to System' to 'Logical System' [sourceElement], 'System' [targetElement]
 */
//@formatter:on

public abstract class NoDuplicationTest extends System2SubsystemTest {

  private static String _id_lf11 = "b36e1d36-ebdf-4759-a612-ce6adecc0f42"; //$NON-NLS-1$
  private static String _id_lf12 = "944d82de-0d88-420a-ba91-d81fd7c9ce3e"; //$NON-NLS-1$
  private static String _id_lf13 = "fb91e997-87ee-4460-a325-53549af3bdde"; //$NON-NLS-1$
  private static String _id_lc1 = "737839eb-8c35-4559-a645-24fe9273889e"; //$NON-NLS-1$
  private static String _id_lc2 = "06bcfd2f-ec9b-4443-a4e9-d2b4e42b4d04"; //$NON-NLS-1$

  private static String _id_pf11 = "936ba46f-1895-4ea1-822c-25739de0fbef"; //$NON-NLS-1$
  private static String _id_pf12 = "0e179f45-314d-4cdf-9cf5-7c65744d2283"; //$NON-NLS-1$
  private static String _id_pf13 = "d0bad3bb-cb73-4931-afdf-c06132f76ca4"; //$NON-NLS-1$
  private static String _id_pc1__pc1 = "a0828e0b-3bbc-44ae-916e-86f7bfc26b90"; //$NON-NLS-1$
  private static String _id_pc2__pc2 = "10066f86-19ba-4ce9-9e75-64d3ebd5d69a"; //$NON-NLS-1$
  private static String _id_pc3__pc3 = "3a7169bd-48d1-4049-8934-4b3c76f2bd4b"; //$NON-NLS-1$
  private static String _id_pc4__pc4 = "27fe009a-b238-411c-912d-b3a329bd88c8"; //$NON-NLS-1$
  private static String _id_pc1 = "d9105e79-93de-4d86-8f5a-8586a54f1b80"; //$NON-NLS-1$
  private static String _id_pc2 = "4061be6e-c7c0-45ed-9593-ab47c2dda968"; //$NON-NLS-1$
  private static String _id_pc3 = "fb7da194-1bbe-46e8-8dbc-73c36709c99f"; //$NON-NLS-1$
  private static String _id_pc4 = "4b72ed07-1aa4-48ef-96cf-756e15a152c3"; //$NON-NLS-1$
  private static String _id_pa1 = "24199687-6483-468a-9288-6fd96dc5dbac"; //$NON-NLS-1$

  public void test() throws Exception {
    super.test();
    super.test();
  }

  public static class Test1 extends NoDuplicationTest implements Interphase {
    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      mustBeTransitionedOnce(_id_pf11);
      mustBeTransitionedOnce(_id_pf12);
      mustBeTransitionedOnce(_id_pf13);
      mustBeTransitionedOnce(_id_pc1__pc1);
      mustBeTransitionedOnce(_id_pc2__pc2);
      mustBeTransitionedOnce(_id_pc3__pc3);
      mustBeTransitionedOnce(_id_pc4__pc4);
      mustBeTransitionedOnce(_id_pc1);
      mustBeTransitionedOnce(_id_pc2);
      mustBeTransitionedOnce(_id_pc3);
      mustBeTransitionedOnce(_id_pc4);
      mustBeTransitionedOnce(_id_pa1);
    }
  }

  public static class Test2 extends NoDuplicationTest implements Interphase {
    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_lc1);
    }

    @Override
    protected void verify() {
      mustBeTransitionedOnce(_id_lf11);
      mustBeTransitionedOnce(_id_lf12);
      mustBeTransitionedOnce(_id_lf13);
      mustBeTransitionedOnce(_id_lc1);
      mustBeTransitionedOnce(_id_lc2);
    }
  }

  // TODO compare to Test2
  public static class Test3 extends NoDuplicationTest implements Crossphase {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_lc1);
    }

    @Override
    protected void verify() {
      mustBeTransitionedOnce(_id_lf11);
      mustBeTransitionedOnce(_id_lf13);
      mustBeTransitionedOnce(_id_lc1);
      mustBeTransitionedOnce(_id_lc2);
    }
  }

  public static class Test4 extends NoDuplicationTest implements Crossphase {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      mustBeTransitionedOnce(_id_pf11);
      mustBeTransitionedOnce(_id_pf12);
      mustBeTransitionedOnce(_id_pf13);
      mustBeTransitionedOnce(_id_pc1__pc1);
      mustBeTransitionedOnce(_id_pc2__pc2);
      shouldNotBeTransitioned(_id_pc3__pc3);
      shouldNotBeTransitioned(_id_pc4__pc4);
      mustBeTransitionedOnce(_id_pc1);
      mustBeTransitionedOnce(_id_pc2);
      shouldNotBeTransitioned(_id_pc3);
      shouldNotBeTransitioned(_id_pc4);
      mustBeTransitionedOnce(_id_pa1);
    }
  }
}
