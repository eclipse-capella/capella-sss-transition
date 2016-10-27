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
package org.polarsys.capella.transition.system2subsystem.tests.crossphases;

import java.util.Collection;
import java.util.Collections;

//@formatter:off
/**
 * identifier:name:'[CSCI] ciName',id=#49aabac9-01b1-4b6e-8d97-22a458c5583d
 * 
 * - Create '[CSCI] ciName' [ConfigurationItem] 
 * - Create '[Physical Artifact Realization] to PC11' [PhysicalArtifactRealization] 
 *  > Link '[Physical Artifact Realization] to PC11' to '[CSCI] ciName' [sourceElement], 'PC11' [targetElement] 
 * - Create '[Physical Artifact Realization] to PC12' [PhysicalArtifactRealization] 
 *  > Link '[Physical Artifact Realization] to PC12' to '[CSCI] ciName' [sourceElement], 'PC12' [targetElement]
 */
//@formatter:on
public class ComponentMerge {

  /**
   * Merge of components: A test for merge of sub components
   */
  public static class Test1 extends CrossPhasesTest {

    private String _id_lc1 = "34e34d4d-8752-481c-b6a2-7743dbb84716"; //$NON-NLS-1$
    private String _id_lc11 = "e2cba002-bca6-4f67-ad80-6707703f6a0a"; //$NON-NLS-1$
    private String _id_lc111 = "03c4e4ab-b35f-4002-a322-0b220b3783f9"; //$NON-NLS-1$
    private String _id_lc112 = "e3553f77-5c43-4b7d-9a5c-54d81059335b"; //$NON-NLS-1$
    private String _id_lc12 = "7e73586e-1b4e-47a5-babe-e11185732048"; //$NON-NLS-1$
    private String _id_lc121 = "b666a716-203c-4901-880e-799ea849d9dc"; //$NON-NLS-1$
    private String _id_lc2 = "a09d7ea8-904c-49b5-aabc-c7702c4dbf9c"; //$NON-NLS-1$
    private String _id_lc21 = "7f311516-78a2-43ca-8323-ae2ff55bac30"; //$NON-NLS-1$
    private String _id_lc211 = "91330d92-35f3-402a-ad5e-787d229c3558"; //$NON-NLS-1$
    private String _id_la_1 = "2762f3b2-82a9-408d-8063-1f7524202f09"; //$NON-NLS-1$
    private String _id_la_2 = "7e95456b-5761-4ee4-82f1-299e0e0a3db4"; //$NON-NLS-1$

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_lc11);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_lc1);
      mustBeTransitioned(_id_lc11);
      mustBeTransitioned(_id_lc12);
      mustBeTransitioned(_id_lc2);
      mustBeTransitioned(_id_la_1);
      mustBeTransitioned(_id_la_2);
      shouldNotBeTransitioned(_id_lc111);
      shouldNotBeTransitioned(_id_lc112);
      shouldNotBeTransitioned(_id_lc121);
      shouldNotBeTransitioned(_id_lc21);
      shouldNotBeTransitioned(_id_lc211);
    }

  }

  /**
   * Merge of components: A test for merge of sub components
   */
  public static class Test2 extends CrossPhasesTest {

    private String _id_lc1 = "34e34d4d-8752-481c-b6a2-7743dbb84716"; //$NON-NLS-1$
    private String _id_lc11 = "e2cba002-bca6-4f67-ad80-6707703f6a0a"; //$NON-NLS-1$
    private String _id_lc111 = "03c4e4ab-b35f-4002-a322-0b220b3783f9"; //$NON-NLS-1$
    private String _id_lc112 = "e3553f77-5c43-4b7d-9a5c-54d81059335b"; //$NON-NLS-1$
    private String _id_lc12 = "7e73586e-1b4e-47a5-babe-e11185732048"; //$NON-NLS-1$
    private String _id_lc121 = "b666a716-203c-4901-880e-799ea849d9dc"; //$NON-NLS-1$
    private String _id_lc2 = "a09d7ea8-904c-49b5-aabc-c7702c4dbf9c"; //$NON-NLS-1$
    private String _id_lc21 = "7f311516-78a2-43ca-8323-ae2ff55bac30"; //$NON-NLS-1$
    private String _id_lc211 = "91330d92-35f3-402a-ad5e-787d229c3558"; //$NON-NLS-1$
    private String _id_la_1 = "2762f3b2-82a9-408d-8063-1f7524202f09"; //$NON-NLS-1$
    private String _id_la_2 = "7e95456b-5761-4ee4-82f1-299e0e0a3db4"; //$NON-NLS-1$

    @Override
    protected Collection<?> getProjectionElements() {
      return Collections.singleton(getObject(_id_lc1));
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_lc1);
      mustBeTransitioned(_id_lc2);
      mustBeTransitioned(_id_la_1);
      mustBeTransitioned(_id_la_2);
      shouldNotBeTransitioned(_id_lc11);
      shouldNotBeTransitioned(_id_lc111);
      shouldNotBeTransitioned(_id_lc112);
      shouldNotBeTransitioned(_id_lc12);
      shouldNotBeTransitioned(_id_lc121);
      mustBeTransitioned(_id_lc2);
      shouldNotBeTransitioned(_id_lc21);
      shouldNotBeTransitioned(_id_lc211);
    }

  }

  /**
   * Merge of components: A test for merge of sub components
   */
  public static class Test3 extends CrossPhasesTest {

    private String _id_lc1 = "34e34d4d-8752-481c-b6a2-7743dbb84716"; //$NON-NLS-1$
    private String _id_lc11 = "e2cba002-bca6-4f67-ad80-6707703f6a0a"; //$NON-NLS-1$
    private String _id_lc111 = "03c4e4ab-b35f-4002-a322-0b220b3783f9"; //$NON-NLS-1$
    private String _id_lc112 = "e3553f77-5c43-4b7d-9a5c-54d81059335b"; //$NON-NLS-1$
    private String _id_lc12 = "7e73586e-1b4e-47a5-babe-e11185732048"; //$NON-NLS-1$
    private String _id_lc121 = "b666a716-203c-4901-880e-799ea849d9dc"; //$NON-NLS-1$
    private String _id_lc2 = "a09d7ea8-904c-49b5-aabc-c7702c4dbf9c"; //$NON-NLS-1$
    private String _id_lc21 = "7f311516-78a2-43ca-8323-ae2ff55bac30"; //$NON-NLS-1$
    private String _id_lc211 = "91330d92-35f3-402a-ad5e-787d229c3558"; //$NON-NLS-1$
    private String _id_la_1 = "2762f3b2-82a9-408d-8063-1f7524202f09"; //$NON-NLS-1$
    private String _id_la_2 = "7e95456b-5761-4ee4-82f1-299e0e0a3db4"; //$NON-NLS-1$

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_lc111);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_lc1);
      mustBeTransitioned(_id_lc11);
      mustBeTransitioned(_id_lc111);
      mustBeTransitioned(_id_lc112);
      mustBeTransitioned(_id_lc12);
      mustBeTransitioned(_id_lc2);
      mustBeTransitioned(_id_la_1);
      mustBeTransitioned(_id_la_2);

      shouldNotBeTransitioned(_id_lc121);
      shouldNotBeTransitioned(_id_lc21);
      shouldNotBeTransitioned(_id_lc211);
    }

  }

  /**
   * Merge of components: A test for merge of sub components
   */
  public static class Test4 extends CrossPhasesTest {

    private String _id_lc1 = "bd501b67-6130-4f44-adcf-e69292b68be1"; //$NON-NLS-1$
    private String _id_lc11 = "76a1ed7f-8d18-4de6-96e3-a4505b52b586"; //$NON-NLS-1$
    private String _id_lc111 = "340c720d-44dd-4277-a433-6347fe6473ea"; //$NON-NLS-1$
    private String _id_lc112 = "3f94128e-9406-459d-9c9e-3a4cf61062f1"; //$NON-NLS-1$
    private String _id_lc12 = "a889fecc-0ea8-409f-a80e-bad52fa411f5"; //$NON-NLS-1$
    private String _id_lc121 = "cb97b526-44fd-42bc-aba7-2ed11f538059"; //$NON-NLS-1$
    private String _id_lc2 = "f5a8f6cf-0f2d-46f3-a955-ef0b8bff12f6"; //$NON-NLS-1$
    private String _id_lc21 = "19e14f10-8c04-49ce-a166-fe1aa01583fc"; //$NON-NLS-1$
    private String _id_lc211 = "3bd2bd9e-9f90-43b6-9897-d78a7b3d0769"; //$NON-NLS-1$

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_lc111);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_lc111);
      mustBeTransitioned(_id_lc112);
      mustBeTransitioned(_id_lc12);
      mustBeTransitioned(_id_lc2);
      shouldNotBeTransitioned(_id_lc1);
      shouldNotBeTransitioned(_id_lc11);
      shouldNotBeTransitioned(_id_lc121);
      shouldNotBeTransitioned(_id_lc21);
      shouldNotBeTransitioned(_id_lc211);
    }

  }

  /**
   * Merge of components: A test for merge of sub components
   */
  public static class Test5 extends CrossPhasesTest {

    private String _id_pc1 = "c427e1ff-8964-438a-86f4-11c37a8a121d"; //$NON-NLS-1$
    private String _id_pc11 = "f1792137-1a4d-4255-8482-d25db682e8a6"; //$NON-NLS-1$
    private String _id_pc12 = "1320fc17-feb7-4be9-b9a3-635f2e314c93"; //$NON-NLS-1$
    private String _id_pc2 = "618baa2f-98fe-4676-988e-95b05b1b0974"; //$NON-NLS-1$
    private String _id_pc21 = "8d63edbb-f911-4dad-9245-da40a6131891"; //$NON-NLS-1$
    private String _id_pc3 = "32ed2872-a495-40d4-b8b1-e52821918af3"; //$NON-NLS-1$
    private String _id_pc4 = "2d04973a-624b-4de9-8121-11c634cbdfc7"; //$NON-NLS-1$
    private String _id_pc5 = "8bc1f8aa-2949-4b6b-aa2b-d4391e1388f2"; //$NON-NLS-1$
    private String _id_pc6 = "73324f22-08f5-48fd-a91f-086769f2f1bf"; //$NON-NLS-1$
    private String _id_pc7 = "17d8bb1f-fe58-4e68-9e68-f7e5cc149c42"; //$NON-NLS-1$
    private String _id_pc8 = "e42063e9-35be-46c5-b9bc-240db1989d93"; //$NON-NLS-1$

    private String _id_pa_1 = "f550f2fb-7f42-4066-9182-e322a6ffda1b"; //$NON-NLS-1$

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_pc12);
      mustBeTransitioned(_id_pc2);
      mustBeTransitioned(_id_pa_1);
      mustBeTransitioned(_id_pc11);
      shouldNotBeTransitioned(_id_pc1);
      shouldNotBeTransitioned(_id_pc3);
      shouldNotBeTransitioned(_id_pc4);
      shouldNotBeTransitioned(_id_pc5);
      shouldNotBeTransitioned(_id_pc6);
      shouldNotBeTransitioned(_id_pc7);
      shouldNotBeTransitioned(_id_pc8);
    }

  }

  /**
   * Merge of components: A test for merge of sub components
   */
  public static class Test6 extends CrossPhasesTest {

    private String _id_pc1 = "cbe06422-d8af-45ea-8c88-831eb356eb4d"; //$NON-NLS-1$
    private String _id_pc11 = "6b8c30cb-b25d-4147-ae73-dce37b380ead"; //$NON-NLS-1$
    private String _id_pc111 = "91b09277-4fa1-4efa-b9d0-c0ea8b4d11e7"; //$NON-NLS-1$
    private String _id_pc2 = "0deac9f8-568b-4c8b-b449-19d774fa9cfe"; //$NON-NLS-1$
    private String _id_pc3 = "2a0a432e-d82e-4494-81d8-4512d1b10b03"; //$NON-NLS-1$
    private String _id_pc4 = "00b7a729-61be-484d-a1ff-cf79293166be"; //$NON-NLS-1$
    private String _id_pc5 = "f4f7dd8b-3b95-4bc9-b257-77fb2bce88be"; //$NON-NLS-1$
    private String _id_pc6 = "166c4815-98d1-44a7-84bf-d8c03852582a"; //$NON-NLS-1$

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {
      shouldNotBeTransitioned(_id_pc2);
      shouldNotBeTransitioned(_id_pc3);
      shouldNotBeTransitioned(_id_pc6);
      mustBeTransitioned(_id_pc1);
      mustBeTransitioned(_id_pc11);
      mustBeTransitioned(_id_pc5);
      shouldNotBeTransitioned(_id_pc111);
      mustBeTransitioned(_id_pc4); // i guess it's ok
    }

  }

}
