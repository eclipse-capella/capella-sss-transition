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

public class ComponentExchangesAndPorts {

  /**
   * Component Exchanges - Ports: Test that sub components exchanges are not transitioned, and ports related to them
   */
  public static class Test1 extends CrossPhasesTest {

    private String _id_physical_system = "b2d84305-24c6-4def-8fe9-0a55231a6c62"; //$NON-NLS-1$
    private String _id_ce1 = "f86ec8c2-7fca-4ae5-9ce8-78ae3100011b"; //$NON-NLS-1$
    private String _id_ce2 = "3c68fa29-f16f-4c98-a05f-7e454359aa10"; //$NON-NLS-1$
    private String _id_ce3 = "36dfe2af-59c6-465e-9727-61eacc45c363"; //$NON-NLS-1$
    private String _id_sm1 = "6d35fac2-b9df-4fad-80ab-a64a9b8ab0af"; //$NON-NLS-1$
    private String _id_pc1 = "4d3e90bf-de01-403b-9e0f-3d3881a4e389"; //$NON-NLS-1$
    private String _id_ce11 = "cbffb615-45d6-40d2-bdb4-f0df0c759c0a"; //$NON-NLS-1$
    private String _id_ce12 = "f823b2fb-16ae-45bc-9d68-2539435a4104"; //$NON-NLS-1$
    private String _id_sm11 = "fb6677f2-fbaf-4b60-a15f-b33add8ee0f8"; //$NON-NLS-1$
    private String _id_pc11 = "17d5c35f-25c6-4fab-8273-727f6d791e65"; //$NON-NLS-1$
    private String _id_ce111 = "ee13488a-1917-462e-b6b2-0c8a9da08656"; //$NON-NLS-1$
    private String _id_pp111 = "3e5e3d33-44ed-448f-a742-188f4a79b121"; //$NON-NLS-1$
    private String _id_pp112 = "1151d51c-0647-4015-ba43-eee6496f2587"; //$NON-NLS-1$
    private String _id_pp113 = "14d13c25-674e-4dfe-a50d-8225eaf7a4e1"; //$NON-NLS-1$
    private String _id_pc12 = "5ad4a7e0-7369-4099-b88d-4854265ec3dd"; //$NON-NLS-1$
    private String _id_ce121 = "f45c4c75-3741-43ed-81d7-fc2b0ca84349"; //$NON-NLS-1$
    private String _id_pp121 = "43032582-d948-4133-9fc7-db44a6a54e6f"; //$NON-NLS-1$
    private String _id_pc2 = "44dd39bf-9bcd-4c61-ba2f-58f41b12cdab"; //$NON-NLS-1$
    private String _id_pc21 = "5c2dbdd0-f5fb-4ad0-926c-4bcb6d187bee"; //$NON-NLS-1$
    private String _id_pp211 = "13bb144c-f585-4bc0-a9eb-5f4fe60593a3"; //$NON-NLS-1$
    private String _id_pp212 = "7cdfb5c1-4314-43d3-9a92-92d0539eb03e"; //$NON-NLS-1$
    private String _id_pc22 = "d243ffc2-9c3f-4530-a4c7-6e1ad96cd101"; //$NON-NLS-1$
    private String _id_pp221 = "8cd0b19a-144a-4b15-8bfd-d58e09de9b42"; //$NON-NLS-1$
    private String _id_pp222 = "a87ea3d8-ff9f-4cd3-b9b1-a21f18ae99d0"; //$NON-NLS-1$
    private String _id_pc3 = "3395feb6-133a-4010-b704-087d552f16d6"; //$NON-NLS-1$
    private String _id_cp31 = "adbd4534-3bce-4b80-ad17-fa4e38de2923"; //$NON-NLS-1$
    private String _id_cp32 = "d78f99a4-620f-4034-9733-935b4b271c38"; //$NON-NLS-1$
    private String _id_cp33 = "4386e9d3-2e2d-4f31-9c5b-d86185e78bd9"; //$NON-NLS-1$
    private String _id_pc4 = "52249b27-24ac-4413-9ff0-629255cb6c04"; //$NON-NLS-1$
    private String _id_cp41 = "b65c4b1c-2df5-46ee-945a-3214a074fbf9"; //$NON-NLS-1$
    private String _id_cp42 = "6ee85010-4e0c-459d-856b-7d8df306dd9e"; //$NON-NLS-1$
    private String _id_pc5 = "4e872ac2-85d1-4ac3-a0d3-f01108f24c5f"; //$NON-NLS-1$
    private String _id_cp51 = "ad54ea97-5958-4d4f-8471-4b64c75885a1"; //$NON-NLS-1$
    private String _id_cp52 = "1560b73b-71ae-4fd0-8053-4ceafef9acd8"; //$NON-NLS-1$
    private String _id_pc6 = "386a6905-ac6b-4604-b87f-3044a871b79a"; //$NON-NLS-1$
    private String _id_cp61 = "c70bee86-6bd4-449a-830d-412e76bf5054"; //$NON-NLS-1$
    private String _id_cp62 = "923dc140-191c-4046-b05f-82256468768a"; //$NON-NLS-1$
    private String _id_pc7 = "0afa9fa3-5a4f-4136-8ff5-2561f9baea26"; //$NON-NLS-1$
    private String _id_pc8 = "6ffcdbb5-6041-4e0e-b198-acbc68375a71"; //$NON-NLS-1$
    private String _id_cp81 = "15c70a51-540f-4f8e-97a6-0d66ebb515bf"; //$NON-NLS-1$
    private String _id_cp82 = "b67394e1-e0e5-4cb8-9605-bc383ce97060"; //$NON-NLS-1$
    private String _id_cp83 = "1ce2c378-e7c2-4507-89d4-d7e8903dc280"; //$NON-NLS-1$

    @Override
    protected Collection<?> getProjectionElements() {
      return Collections.singleton(getObject(_id_pc11));
    }

    @Override
    protected void verify() {

      mustBeTransitioned(_id_ce1);
      mustBeTransitioned(_id_ce3);
      mustBeTransitioned(_id_ce11);
      mustBeTransitioned(_id_ce12);
      shouldNotBeTransitioned(_id_ce2);
      shouldNotBeTransitioned(_id_ce111);
      shouldNotBeTransitioned(_id_ce121);
      mustBeTransitioned(_id_cp83);
      mustBeTransitioned(_id_cp81);
      shouldNotBeTransitioned(_id_cp32);
      shouldNotBeTransitioned(_id_cp82);
      shouldNotBeTransitioned(_id_cp51);
      shouldNotBeTransitioned(_id_cp42);
      shouldNotBeTransitioned(_id_cp62);
      mustBeTransitioned(_id_cp81);
      mustBeTransitioned(_id_cp52);
      shouldNotBeTransitioned(_id_pc8);
      shouldNotBeTransitioned(_id_pc3);
    }
  }

  /**
   * Component Exchanges - Ports Test that sub components exchanges are not transitioned, and ports related to them
   */
  public static class Test2 extends CrossPhasesTest {

    private String _id_physical_system = "5f0b143a-9233-4983-9d22-fbce134f5eea"; //$NON-NLS-1$
    private String _id_ce1 = "2c98867e-a0df-4f53-826d-be38c05a3ce4"; //$NON-NLS-1$
    private String _id_ce2 = "bc51f332-63f6-4fe9-95bc-635d9382df84"; //$NON-NLS-1$
    private String _id_ce3 = "7a86f4b3-f1cc-44b3-b7a2-33eed53b71be"; //$NON-NLS-1$
    private String _id_pc1 = "f19a5f54-9303-4c37-9864-cc2f4092998a"; //$NON-NLS-1$
    private String _id_pc2 = "e208e139-a7f2-4301-8484-a30822127cc7"; //$NON-NLS-1$
    private String _id_ce21 = "476e6cdc-2418-44dd-921e-8abd3e9b3852"; //$NON-NLS-1$
    private String _id_ce22 = "0df28b7e-5bff-4a99-b897-892e998b30df"; //$NON-NLS-1$
    private String _id_cp21 = "d180ae1c-7a90-474b-bf02-2e95ff6efdb0"; //$NON-NLS-1$
    private String _id_cp22 = "d48fb388-c9ea-43a3-bb00-1af7f3bc4b34"; //$NON-NLS-1$
    private String _id_pc3 = "a43817ee-494e-4f90-861e-8034d1280b55"; //$NON-NLS-1$
    private String _id_ce31 = "b4b6bb0f-19bf-407b-b043-31e7de90035b"; //$NON-NLS-1$
    private String _id_ce32 = "da4a03a6-46fb-4f2f-ae62-02f49520d6de"; //$NON-NLS-1$
    private String _id_cp31 = "f10472d8-6386-4125-81aa-25a638bdfd12"; //$NON-NLS-1$
    private String _id_cp32 = "8412a94e-be6f-4cc3-a258-cec7dea7cecb"; //$NON-NLS-1$
    private String _id_pc4 = "db8028e7-5903-418a-8cd0-5edce1a54b76"; //$NON-NLS-1$
    private String _id_cp41 = "ed4b40ef-9945-436b-a9e4-106e9dd3c521"; //$NON-NLS-1$
    private String _id_cp42 = "7239c38d-b567-4970-aceb-979199dee3d9"; //$NON-NLS-1$
    private String _id_cp43 = "d7e86613-3e15-495b-8dc3-81c751de474e"; //$NON-NLS-1$
    private String _id_pc5 = "a8b14daa-96be-44d4-9299-018b17259f79"; //$NON-NLS-1$
    private String _id_cp51 = "9b7b7971-16a9-4764-9da3-90f7af33db83"; //$NON-NLS-1$
    private String _id_pc6 = "cc8ada61-d647-450f-9767-5e6a6cd2282e"; //$NON-NLS-1$
    private String _id_pc7 = "ce806cf6-9efa-4c0b-8c13-d89c55a01a88"; //$NON-NLS-1$
    private String _id_cp71 = "d1d470c7-635f-449f-a36a-98fb52896976"; //$NON-NLS-1$
    private String _id_pc8 = "69a5d3e6-cb68-4de0-9574-6555884b8f0a"; //$NON-NLS-1$
    private String _id_cp81 = "f724d863-a7d2-45d8-b801-e41355d4e347"; //$NON-NLS-1$

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      shouldNotBeTransitioned(_id_ce1);
      shouldNotBeTransitioned(_id_ce3);
      mustBeTransitioned(_id_ce2);
      shouldNotBeTransitioned(_id_cp81);
      shouldNotBeTransitioned(_id_cp43);
      shouldNotBeTransitioned(_id_cp41);
      shouldNotBeTransitioned(_id_cp42);
      shouldNotBeTransitioned(_id_cp31);
      shouldNotBeTransitioned(_id_cp32);
      mustBeTransitioned(_id_cp22);
      shouldNotBeTransitioned(_id_cp21);
      shouldNotBeTransitioned(_id_cp51);
      mustBeTransitioned(_id_cp71);
      shouldNotBeTransitioned(_id_pc5);
      shouldNotBeTransitioned(_id_pc2);
      shouldNotBeTransitioned(_id_pc3);
      shouldNotBeTransitioned(_id_pc8);
      shouldNotBeTransitioned(_id_pc4);
      mustBeTransitioned(_id_pc6);
      mustBeTransitioned(_id_pc1);
    }
  }
}
