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

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.interaction.Scenario;
import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

//@formatter:off
/**
 * identifier:name:'Physical Functions',id=#adcf3857-102d-4f39-8b82-d340a4ffd7e5
 * identifier:name:'Physical System',id=#0b814053-f06e-469c-a450-4633e11a57ca

 * - Create 'Physical Functions' [PhysicalFunctionPkg]
 *   - Create 'Root Physical Function' [PhysicalFunction]
 *     - Create '[Function Realization] to Root Logical Function' [FunctionRealization]
 *       > Link '[Function Realization] to Root Logical Function' to 'Root Physical Function' [sourceElement], 'Root Logical Function' [targetElement]
 *     - Create '[Physical Function]' [PhysicalFunction]
 * - Create 'Physical System' [PhysicalComponent]
 *   > Set 'Physical System' to 'UNSET' [kind], 'UNSET' [nature]
 *   - Create '[Part]: [Physical Component]' [Part]
 *     > Link '[Part]: [Physical Component]' to '[Physical Component]' [abstractType]
 *     - Create '[Part Deployment Link] to [Part]' [PartDeploymentLink]
 *   - Create '[Part]: [Physical Component]' [Part]
 *     > Link '[Part]: [Physical Component]' to '[Physical Component]' [abstractType]
 *   - Create '[Physical Component]' [PhysicalComponent]
 *     > Set '[Physical Component]' to 'UNSET' [kind], 'NODE' [nature]
 *   - Create '[Physical Component]' [PhysicalComponent]
 *     > Set '[Physical Component]' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create '[Component Functional Allocation] to [PhysicalFunction]' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to [PhysicalFunction]' to '[Physical Component]' [sourceElement], '[Physical Function]' [targetElement]
 *   - Create '[Logical Component Realization] to Logical System' [LogicalComponentRealization]
 *     > Link '[Logical Component Realization] to Logical System' to 'Physical System' [sourceElement], 'Logical System' [targetElement]
 */
//@formatter:on

public class ScenarioTest {

  /**
   * Scenario transition preference scope: Scenarios should not be transitioned if preference is set to false
   */
  public static class Disabled extends System2SubsystemTest implements Interphase, Crossphase {

    private String _id_scenario_1_fs = "3d03f7c7-fc83-4984-9497-9e4aea29f710"; //$NON-NLS-1$
    private String _id_scenario_2_es_fe = "8a04d830-c592-4574-a16d-96661112efa5"; //$NON-NLS-1$
    private String _id_scenario_3_es_ce = "1cccbdc5-c754-4bea-beb5-b038e8c437d4"; //$NON-NLS-1$
    private String _id_scenario_4_is = "32802fcf-3640-4b39-83ce-206a8f290357"; //$NON-NLS-1$
    private String _id_pc1 = "0837ea01-5825-424f-b4af-f1e388fc6292"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.FALSE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      shouldNotBeTransitioned(_id_scenario_1_fs);
      shouldNotBeTransitioned(_id_scenario_2_es_fe);
      shouldNotBeTransitioned(_id_scenario_3_es_ce);
      shouldNotBeTransitioned(_id_scenario_4_is);
    }

  }

  /**
   * Scenario transition preference scope: All scenario should be transitioned if preference is set to true
   */
  public static class Enabled extends System2SubsystemTest implements Interphase, Crossphase {

    private String _id_scenario_1_fs = "3d03f7c7-fc83-4984-9497-9e4aea29f710"; //$NON-NLS-1$
    private String _id_scenario_2_es_fe = "8a04d830-c592-4574-a16d-96661112efa5"; //$NON-NLS-1$
    private String _id_scenario_3_es_ce = "1cccbdc5-c754-4bea-beb5-b038e8c437d4"; //$NON-NLS-1$
    private String _id_scenario_4_is = "32802fcf-3640-4b39-83ce-206a8f290357"; //$NON-NLS-1$
    private String _id_pc1 = "0837ea01-5825-424f-b4af-f1e388fc6292"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_scenario_1_fs);
      mustBeTransitioned(_id_scenario_2_es_fe);
      mustBeTransitioned(_id_scenario_3_es_ce);
      mustBeTransitioned(_id_scenario_4_is);

    }
  }

  /**
   * Scenario transition, scope: A test about scope for scenario transition
   */
  public static class Scope1 extends System2SubsystemTest implements Interphase, Crossphase {

    private String _id_s1 = "8001168f-b437-46cf-afea-8963d52765ca"; //$NON-NLS-1$
    private String _id_ir11 = "56145fbc-e74d-400d-bb5b-970b7210a3ce"; //$NON-NLS-1$
    private String _id_ir12 = "19a07e79-f2c5-40c0-84b9-50efd217f494"; //$NON-NLS-1$
    private String _id_ir13 = "69a24631-6367-4100-9678-1b86e29a9233"; //$NON-NLS-1$
    private String _id_ir14 = "a7d9c37c-29af-4508-b89d-f5155f513a3c"; //$NON-NLS-1$
    private String _id_ir15 = "b3676830-8e37-47d0-bdc5-f894bdee0174"; //$NON-NLS-1$
    private String _id_fe11 = "54825c34-1297-4531-a510-fc5d51e5bc3b"; //$NON-NLS-1$
    private String _id_io12 = "3b3fb741-3eaa-4942-9cfd-791582c2880a"; //$NON-NLS-1$
    private String _id_me13 = "aa75469f-19b1-4ff1-874b-b143c0c32a14"; //$NON-NLS-1$
    private String _id_me14 = "681a27c6-2ed5-4504-8cab-960e0134a3e4"; //$NON-NLS-1$
    private String _id_me15 = "294fb1e8-63a7-4918-ae4f-579c8aa343bb"; //$NON-NLS-1$
    private String _id_me16 = "e5f6582d-3bad-43a3-bfd4-bd6ff9e421f2"; //$NON-NLS-1$
    private String _id_is17 = "ce49a788-a8b9-42a9-802d-8f3992f7584b"; //$NON-NLS-1$
    private String _id_is18 = "a2ed68cf-61c9-43c3-bec1-8886d7b0e43f"; //$NON-NLS-1$
    private String _id_me19 = "dfe306f1-0c28-45bb-9a3d-bdf36e56e92f"; //$NON-NLS-1$
    private String _id_me110 = "09abbfd1-903a-4c47-8254-a83c3e03e6ec"; //$NON-NLS-1$
    private String _id_me111 = "e4efb62e-921f-49cc-b7d7-e62af2059c05"; //$NON-NLS-1$
    private String _id_me112 = "ad804e6f-41c7-4807-a2b3-0e8f9f9baa58"; //$NON-NLS-1$
    private String _id_fe113 = "e2d3fd60-452f-45f6-be31-5294186169c4"; //$NON-NLS-1$
    private String _id_is114 = "b97d7261-7bb8-47cb-a7c6-8106b46c46b1"; //$NON-NLS-1$
    private String _id_me115 = "38b52983-cecf-4b94-9f0c-13f50fc40394"; //$NON-NLS-1$
    private String _id_me116 = "17f2562a-0f46-484a-a6cb-dbd53e8e1a90"; //$NON-NLS-1$
    private String _id_is117 = "81414dd3-2f0d-4e83-83c1-87d5df32347f"; //$NON-NLS-1$
    private String _id_me118 = "0a74d033-a165-47d1-ad00-5cef4e743ac5"; //$NON-NLS-1$
    private String _id_me119 = "97d7f444-36c5-49d6-a266-6d4a47afdbd5"; //$NON-NLS-1$
    private String _id_fe120 = "db8b4703-4694-4fc2-958b-f49d4d6dcf3c"; //$NON-NLS-1$
    private String _id_me121 = "a93b1feb-35ec-4a28-9f06-b9860a05712c"; //$NON-NLS-1$
    private String _id_me122 = "a3420ce0-5e20-4865-9279-23afc0441d53"; //$NON-NLS-1$
    private String _id_me123 = "4cf87bce-4e6b-410b-ba3d-19639590c20e"; //$NON-NLS-1$
    private String _id_me124 = "07f8c21c-423a-41c1-8e74-6d6715fbd1aa"; //$NON-NLS-1$
    private String _id_ee125 = "f87fd14e-b270-440a-bed2-a1b780546bca"; //$NON-NLS-1$
    private String _id_me126 = "2ae59b5a-eb85-401b-92ff-e50c3aa956ba"; //$NON-NLS-1$
    private String _id_me127 = "58aa8b52-8018-4e45-a1f3-3630baec8cea"; //$NON-NLS-1$
    private String _id_fe128 = "de451f57-7c9f-46cb-b697-43a5fef9b59a"; //$NON-NLS-1$
    private String _id_e11 = "e123cd4e-2368-47fa-a05a-132c2c0ce2e1"; //$NON-NLS-1$
    private String _id_e12 = "701138ae-0d73-4277-9cc8-cf7bb45ed56a"; //$NON-NLS-1$
    private String _id_e13 = "3e1b0663-9231-4097-9335-ef1b8839d7ca"; //$NON-NLS-1$
    private String _id_e14 = "8c5a0f01-0369-4db0-8c1b-35d3d255ce03"; //$NON-NLS-1$
    private String _id_cf15 = "3f05e484-4fd0-43ed-af45-f1d27613efbe"; //$NON-NLS-1$
    private String _id_sf16 = "3714b6d0-2627-4008-96c5-cd20ce63b985"; //$NON-NLS-1$
    private String _id_sf17 = "8cf7d651-e82c-4a4d-9f9e-6a6df9620016"; //$NON-NLS-1$
    private String _id_iu18 = "4d23b717-4809-42fb-b027-4462e160260d"; //$NON-NLS-1$
    private String _id_e19 = "9c733706-43a3-46af-a60b-ee1e163caeca"; //$NON-NLS-1$
    private String _id_eso11 = "275af994-6e0e-497e-92e4-c95d34da4d16"; //$NON-NLS-1$
    private String _id_ero12 = "4266c4c0-2754-4b07-b32a-cf892e67673e"; //$NON-NLS-1$
    private String _id_eso13 = "706aab68-7d9d-4ec4-b20a-2965bffe58cb"; //$NON-NLS-1$
    private String _id_ero14 = "0843782b-3f1e-4053-be88-ed625806a260"; //$NON-NLS-1$
    private String _id_eso15 = "230c542b-6a13-4987-8317-df38d38e365b"; //$NON-NLS-1$
    private String _id_ero16 = "374006cb-9cdc-4cb4-9b24-96d7f1f5063c"; //$NON-NLS-1$
    private String _id_eso17 = "0c5ff5b6-c55b-4d4c-b951-b44b956397ee"; //$NON-NLS-1$
    private String _id_ero18 = "db047ff8-01a6-4494-bdd0-d1600bb01f79"; //$NON-NLS-1$
    private String _id_eso19 = "b3209c0e-a755-47be-be02-803708bda548"; //$NON-NLS-1$
    private String _id_ero110 = "874461b9-8647-4f7d-92d2-c84ef933c4b2"; //$NON-NLS-1$
    private String _id_eso111 = "00164d91-88b5-412e-b1a3-93edae0db1f2"; //$NON-NLS-1$
    private String _id_ero112 = "d4bd2526-99a8-4bf5-951d-e2be49d01756"; //$NON-NLS-1$
    private String _id_eso113 = "dc220a9b-9b79-4b29-9296-95dd0b631574"; //$NON-NLS-1$
    private String _id_ero114 = "7158a4d2-49b8-4438-af00-051e4ce413bc"; //$NON-NLS-1$
    private String _id_ee115 = "9342dee9-86a3-45ff-ad71-7c9e216408e9"; //$NON-NLS-1$
    private String _id_eso116 = "e89f7481-c442-475d-aa49-e21b0bd6ae61"; //$NON-NLS-1$
    private String _id_ero117 = "6d933969-e3ab-4cb5-900c-ea24526417f8"; //$NON-NLS-1$
    private String _id_eso118 = "93e65acb-4f6d-48b7-b23b-ae49376cd7c9"; //$NON-NLS-1$
    private String _id_ero119 = "7bbd57e0-db1b-4cf5-a65a-6925796ecd5b"; //$NON-NLS-1$

    private String _id_pc1 = "1cf7a811-e608-472d-9dcf-2afdf1c9bee2"; //$NON-NLS-1$
    private String _id_fs1111 = "0cea4934-c0bc-4d87-9c86-a5d51b053ec9"; //$NON-NLS-1$
    private String _id_fs6111 = "f1934e9d-62b7-4c3e-95be-85f341b41a4d"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      shouldNotBeTransitioned(_id_e14);
      shouldNotBeTransitioned(_id_e19);
      mustBeTransitioned(_id_cf15);
      mustBeTransitioned(_id_iu18);

      shouldNotBeTransitioned(_id_sf17);
      mustBeTransitioned(_id_sf16);

      shouldNotBeTransitioned(_id_ir12);

      shouldNotBeTransitioned(_id_fs6111);
      mustBeTransitioned(_id_fs1111);
    }

  }

  /**
   * Scenario transition, scope: A test about scope for scenario transition
   */
  public static class Scope2 extends System2SubsystemTest implements Interphase, Crossphase {
    private String _id_s3 = "ed5abed8-2185-44ee-9566-73dd1edecb6b"; //$NON-NLS-1$
    private String _id_pc5 = "89d694c6-0b89-4654-8cf5-ae928e2e5a4d"; //$NON-NLS-1$
    private String _id_pc3 = "53234aab-0a40-4562-b4ca-bda8bf74a7a8"; //$NON-NLS-1$
    private String _id_pc6 = "2b8fac3a-5850-41f1-9b51-849c44473cfa"; //$NON-NLS-1$
    private String _id_pc4 = "ce965456-b415-4988-872e-bb9a476b9b69"; //$NON-NLS-1$
    private String _id_fe3 = "f5e1ea66-6d96-48bd-a14e-091ee920db84"; //$NON-NLS-1$
    private String _id_me31 = "48404792-3709-4349-a7ff-3bf61eca6db3"; //$NON-NLS-1$
    private String _id_me32 = "fd97f25a-10f5-40e3-8a8d-7bb6504c6aa6"; //$NON-NLS-1$
    private String _id_me33 = "ea5e9fd4-0b25-46be-9b0c-44c9c9cc3f87"; //$NON-NLS-1$
    private String _id_me34 = "4df66d41-9223-47c8-8bf5-91e1e79c7a7d"; //$NON-NLS-1$
    private String _id_me35 = "7a3f6269-a467-4aa8-ac94-e1a68ade159a"; //$NON-NLS-1$
    private String _id_me36 = "894ea358-dcc0-4983-87f5-ff5fe52ef661"; //$NON-NLS-1$
    private String _id_fe37 = "0cf05f72-069b-4c65-8530-33a324be2702"; //$NON-NLS-1$
    private String _id_io38 = "3ee82faf-a523-4eed-8aa7-6135b64fe9f6"; //$NON-NLS-1$
    private String _id_fe39 = "5848f4de-4729-4fdc-b2a2-1591f04c8424"; //$NON-NLS-1$
    private String _id_fe310 = "6bda50fa-8148-4377-a946-fb75299a256e"; //$NON-NLS-1$
    private String _id_me311 = "38d73c82-9a5f-46bb-a8a1-b7fa6e7e051c"; //$NON-NLS-1$
    private String _id_me312 = "60d79d0c-15eb-447b-8d55-92aef40b1351"; //$NON-NLS-1$
    private String _id_me313 = "ef7d481b-3c6e-45c7-95d3-619d66fa5241"; //$NON-NLS-1$
    private String _id_me314 = "2d573f5d-d6a5-431f-a698-cf8c64240425"; //$NON-NLS-1$
    private String _id_fe315 = "878953e3-2e63-4f31-bd9a-c8ef8bff11c9"; //$NON-NLS-1$
    private String _id_me316 = "01b2cb35-87aa-4d98-9b37-ac5c22a19ad5"; //$NON-NLS-1$
    private String _id_me317 = "4d4e28de-c8fd-4691-8bfe-c472c96133d0"; //$NON-NLS-1$
    private String _id_me318 = "526d123f-c32d-491d-b3bf-49e6133dbd57"; //$NON-NLS-1$
    private String _id_me319 = "dfa622d7-4932-4ac8-baf8-cfc89f24e1a8"; //$NON-NLS-1$
    private String _id_me320 = "e77abb0f-466f-4b41-a310-9c6343ceac16"; //$NON-NLS-1$
    private String _id_is321 = "bfb7fe35-1939-40cd-a012-52ac08ad9397"; //$NON-NLS-1$
    private String _id_is322 = "f82518e7-ea76-419a-812b-86f3e27d6c2e"; //$NON-NLS-1$
    private String _id_is323 = "a4740d64-ed5e-4fec-b640-40bf1b1242ae"; //$NON-NLS-1$
    private String _id_is324 = "bd50e056-d0f3-4214-8725-88d349ab702e"; //$NON-NLS-1$
    private String _id_is325 = "9f542603-150e-493b-a33a-e148a90d8803"; //$NON-NLS-1$
    private String _id_is326 = "e659a679-ceaa-46a1-94f3-7d8e4d6848ec"; //$NON-NLS-1$
    private String _id_me327 = "96cb3f60-2b21-4395-ac59-856b94721538"; //$NON-NLS-1$
    private String _id_me328 = "8c0a9acb-1d26-4421-94ac-fb0f3e569806"; //$NON-NLS-1$
    private String _id_is329 = "69f5ec3c-257a-4ce1-9081-0236e8f17162"; //$NON-NLS-1$
    private String _id_is330 = "06ad964e-75e6-4166-b155-436d02f511a6"; //$NON-NLS-1$
    private String _id_is331 = "284ef60b-d49a-497a-a4fa-92b33c1ccea7"; //$NON-NLS-1$
    private String _id_is332 = "65ca8f97-c086-415b-9186-9570257b0e26"; //$NON-NLS-1$
    private String _id_ee333 = "63dfc733-7ed1-470b-8153-3e1c0de9cce1"; //$NON-NLS-1$
    private String _id_me334 = "093adcf3-10b2-4d2a-856b-1e59d5e2846c"; //$NON-NLS-1$
    private String _id_me335 = "68d81338-a4d6-44bc-b564-fd0e6abc7bc5"; //$NON-NLS-1$
    private String _id_me336 = "d35fd132-14ab-4cb5-b4d4-3e8e6d2a065f"; //$NON-NLS-1$
    private String _id_is337 = "a7ec81f5-9784-4f2d-95ce-5432c9846cd2"; //$NON-NLS-1$
    private String _id_me338 = "736ca59e-f4b5-4281-a6cd-050495d6a0ee"; //$NON-NLS-1$
    private String _id_is339 = "3226572f-1e2b-4ba7-a315-d06e0ae2b2b3"; //$NON-NLS-1$
    private String _id_me340 = "48ce2223-acdf-494b-9042-0f386d9df027"; //$NON-NLS-1$
    private String _id_me341 = "70b5cffe-643d-468e-8485-8f66e377809e"; //$NON-NLS-1$
    private String _id_me342 = "dd4a403f-79dc-425b-a0d9-4376c58f4820"; //$NON-NLS-1$
    private String _id_ee343 = "fa01daca-9b1d-4d18-a0ce-7689446f795a"; //$NON-NLS-1$
    private String _id_e31 = "90470b3a-38b2-47ae-8371-325282a637ac"; //$NON-NLS-1$
    private String _id_e32 = "a3c0b1aa-b30c-4437-99f4-018229872906"; //$NON-NLS-1$
    private String _id_e33 = "9887c775-1792-4c80-bd1c-4c5e0a28b1b9"; //$NON-NLS-1$
    private String _id_e34 = "f402ee8c-290d-4416-9879-66dd5edd29dc"; //$NON-NLS-1$
    private String _id_e35 = "886ed150-bb46-4efb-ac35-b4d8fcd32659"; //$NON-NLS-1$
    private String _id_e36 = "9a97f623-f7b0-4a18-b146-3812ebe90208"; //$NON-NLS-1$
    private String _id_e37 = "f6952113-9a2a-47a1-a65a-e4f868f2ff61"; //$NON-NLS-1$
    private String _id_sf38 = "33e7e4d2-7e20-4531-a665-4949b92e552b"; //$NON-NLS-1$
    private String _id_sf39 = "4655482f-b665-45cb-b5a8-8eaa55116c40"; //$NON-NLS-1$
    private String _id_sf310 = "5813bfc6-7be2-4218-aae0-0d699acd50a4"; //$NON-NLS-1$
    private String _id_sf311 = "07680504-1d02-4d4b-b121-47f50f049a6e"; //$NON-NLS-1$
    private String _id_sf312 = "5f53e95d-73f4-4868-8cc9-fda7a53352f8"; //$NON-NLS-1$
    private String _id_cf313 = "4f44218e-050b-4020-9fea-154fd58fc4eb"; //$NON-NLS-1$
    private String _id_sf314 = "7b51121d-6159-4570-bfc5-6202619745ff"; //$NON-NLS-1$
    private String _id_iu315 = "940d851d-7521-4719-9672-a35c63d52f4b"; //$NON-NLS-1$
    private String _id_eso31 = "5358dcbe-1ed7-4c68-a80b-a5abb0eda809"; //$NON-NLS-1$
    private String _id_ero32 = "1595925b-9ade-498a-9e0b-35c5f913d8fe"; //$NON-NLS-1$
    private String _id_eso33 = "1d5ce24e-33d0-4560-9063-8f0fa4a56493"; //$NON-NLS-1$
    private String _id_ero34 = "e1657fc9-58bc-458b-b983-bdaaa1d44bdb"; //$NON-NLS-1$
    private String _id_eso35 = "09cf2b29-955a-40cb-b931-e143bf792a5d"; //$NON-NLS-1$
    private String _id_ero36 = "6acbe04c-403b-495f-85d4-b01fce3d0a83"; //$NON-NLS-1$
    private String _id_eso37 = "87817dd8-1519-42c7-9477-b5ff8b210c71"; //$NON-NLS-1$
    private String _id_ero38 = "2eaaa9cd-e758-4734-a624-8f03ab84f893"; //$NON-NLS-1$
    private String _id_eso39 = "81094515-36ed-4831-b025-12be3267bf72"; //$NON-NLS-1$
    private String _id_ero310 = "646ef0a2-1549-4564-9f9f-02931ecd174c"; //$NON-NLS-1$
    private String _id_eso311 = "b48b531f-6b4f-4cf3-9df6-d6987d907459"; //$NON-NLS-1$
    private String _id_ero312 = "31cf13ad-0de6-41ec-9898-e143d338a3a8"; //$NON-NLS-1$
    private String _id_ero313 = "557baf33-fdda-4aaf-a652-dcb8bd69d215"; //$NON-NLS-1$
    private String _id_ee314 = "1036bbab-de42-4aaf-8b85-174a46e828c0"; //$NON-NLS-1$
    private String _id_eso315 = "28aedd28-6942-42c0-8d73-5f180fdfc4ba"; //$NON-NLS-1$
    private String _id_eso316 = "302f0278-1b4e-4822-b9aa-f86f3be963e9"; //$NON-NLS-1$
    private String _id_ero317 = "ca392cc2-8024-46d5-8fe7-01b525975fc1"; //$NON-NLS-1$
    private String _id_eso318 = "7d5c3172-e023-4536-9cd0-11f5a99fa7d9"; //$NON-NLS-1$
    private String _id_ero319 = "1eca2be8-7796-4a2c-be42-71c4fd1b0eb4"; //$NON-NLS-1$
    private String _id_eso320 = "6718bbf2-02af-4e33-8971-72c89a00195e"; //$NON-NLS-1$
    private String _id_ero321 = "78a5f3bc-bde9-49a1-9718-930daaaf061d"; //$NON-NLS-1$
    private String _id_eso322 = "263c90fe-8205-4e37-b811-b2cb3f70f15f"; //$NON-NLS-1$
    private String _id_ero323 = "9d4ebd39-30ec-4e44-afc6-60e4be1081ae"; //$NON-NLS-1$
    private String _id_eso324 = "849ba6e8-3143-4c7e-87e6-1809a47d38f4"; //$NON-NLS-1$
    private String _id_ero325 = "0cf5673a-eaf0-40ed-8379-4dc14db81e00"; //$NON-NLS-1$
    private String _id_ee326 = "dec865fc-7eb8-470f-9c79-ab629a60b206"; //$NON-NLS-1$

    private String _id_pc1 = "1cf7a811-e608-472d-9dcf-2afdf1c9bee2"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {

      shouldNotBeTransitioned(_id_e32);
      shouldNotBeTransitioned(_id_e33);
      shouldNotBeTransitioned(_id_sf311);
      shouldNotBeTransitioned(_id_sf312);
      shouldNotBeTransitioned(_id_sf314);
      shouldNotBeTransitioned(_id_e36);
      shouldNotBeTransitioned(_id_e34);
      mustBeTransitioned(_id_sf38);
      mustBeTransitioned(_id_sf310);
      mustBeTransitioned(_id_sf39);

    }
  }

  /**
   * Scenario transition, scope: A test about scope for scenario transition
   */
  public static class Scope3 extends System2SubsystemTest implements Interphase, Crossphase {

    private String _id_s5 = "a973a99f-fdbc-4bdd-a474-2ab19b4bd212"; //$NON-NLS-1$
    private String _id_pc5 = "df9fff6e-2d3d-4974-9d2f-b97ef4465ccc"; //$NON-NLS-1$
    private String _id_pc3 = "89b9ef51-edbe-4322-a9cb-ef06aaeac751"; //$NON-NLS-1$
    private String _id_pc6 = "6b26a6d2-8691-48e0-a728-9a9e844187d3"; //$NON-NLS-1$
    private String _id_pc4 = "f52e28ce-07ee-4ec9-a2da-573524701ab8"; //$NON-NLS-1$
    private String _id_ce3 = "414a4f08-26f5-4eee-bfbd-5845160c38e1"; //$NON-NLS-1$
    private String _id_me51 = "75802697-206c-4dff-a15d-d365e68a292f"; //$NON-NLS-1$
    private String _id_me52 = "8b23382b-8c5c-4172-8404-f804dbfd327d"; //$NON-NLS-1$
    private String _id_me53 = "10ebac75-81d7-4399-a49a-33d1cc79e99e"; //$NON-NLS-1$
    private String _id_fe54 = "68ffda50-301a-4707-ba47-001886f8a603"; //$NON-NLS-1$
    private String _id_is55 = "a329ba50-5163-4c58-a481-0d8a9bf45ba5"; //$NON-NLS-1$
    private String _id_is56 = "1176f86b-2393-4493-806b-ebfdfae05acc"; //$NON-NLS-1$
    private String _id_is57 = "558673ce-b879-4c82-aef5-1f9f141c942a"; //$NON-NLS-1$
    private String _id_is58 = "5cbc2602-4427-41aa-a63e-f09caccf7ab5"; //$NON-NLS-1$
    private String _id_fe59 = "34840bdf-c61c-4dbd-9ee1-9cf0e8454016"; //$NON-NLS-1$
    private String _id_me510 = "9637d600-7ed7-43a2-ba92-85ec161a0fd4"; //$NON-NLS-1$
    private String _id_is511 = "3155144f-a0fd-4034-ae3b-a48121528671"; //$NON-NLS-1$
    private String _id_is512 = "97055b04-31f8-43b3-a868-65893d682528"; //$NON-NLS-1$
    private String _id_ee513 = "cd0a0dfc-2cd0-4ab3-8d08-2dc50b4d5f21"; //$NON-NLS-1$
    private String _id_me514 = "9d2a50ec-2f3d-4d72-8806-d0fe6af862fc"; //$NON-NLS-1$
    private String _id_me515 = "d4cd2d6b-6f8e-444c-9cc0-da6f17ac1b62"; //$NON-NLS-1$
    private String _id_me516 = "b00d80a6-8fe9-4574-976e-c590d0b17893"; //$NON-NLS-1$
    private String _id_me517 = "ce36b5c2-cacf-41af-b6e8-f7194c0c2350"; //$NON-NLS-1$
    private String _id_me518 = "58eef0cc-1149-4e3a-83d8-60d6728fae26"; //$NON-NLS-1$
    private String _id_me519 = "d6bf13a2-716e-4750-853e-1792a7e4bdf9"; //$NON-NLS-1$
    private String _id_me520 = "2ca418d5-5c8d-4c69-b16d-7e30498dcea0"; //$NON-NLS-1$
    private String _id_me521 = "61e97bb9-c80f-4fe7-8e0e-057356c9668e"; //$NON-NLS-1$
    private String _id_is522 = "290fe9b3-946e-4659-94d4-b3187eb592e9"; //$NON-NLS-1$
    private String _id_is523 = "6dd548a1-6c39-402c-b1ea-787a2341b65b"; //$NON-NLS-1$
    private String _id_me524 = "4a7868fa-583c-4ce6-93be-a07c599ecbdb"; //$NON-NLS-1$
    private String _id_me525 = "c72346b7-5b5b-489a-b56c-15b744746d9d"; //$NON-NLS-1$
    private String _id_fe526 = "4ac2e100-31cb-4199-a0b2-b051726e3fb8"; //$NON-NLS-1$
    private String _id_io527 = "ae32b42e-ab7f-4b6c-9630-0307f3469a83"; //$NON-NLS-1$
    private String _id_me528 = "15bd40bb-8bb3-4263-aaf2-01f98cfcb9b6"; //$NON-NLS-1$
    private String _id_me529 = "8dd010e4-ed58-4d77-828d-bf9bf45a366a"; //$NON-NLS-1$
    private String _id_is530 = "62890d9f-2eb7-49c1-9015-5c84fe2048ef"; //$NON-NLS-1$
    private String _id_is531 = "ebcc62ea-e681-4570-b365-474b0924c68b"; //$NON-NLS-1$
    private String _id_ee532 = "6d965808-8a40-422c-ad82-8fe33c826499"; //$NON-NLS-1$
    private String _id_fe533 = "9fee045d-9f92-4427-b30f-8c78ef5a08ed"; //$NON-NLS-1$
    private String _id_e51 = "6f07f2b7-7375-4f2f-a375-d85a86be25bc"; //$NON-NLS-1$
    private String _id_e52 = "f16179c3-9e3c-4260-9b52-32b8cfe0828d"; //$NON-NLS-1$
    private String _id_e53 = "e8f0ec44-cee2-40ff-a06a-d4069572a419"; //$NON-NLS-1$
    private String _id_e54 = "45a4a7ca-e1cb-4aa5-8637-c79637959e3d"; //$NON-NLS-1$
    private String _id_e55 = "ad905122-3e9e-4c0e-be21-62b50f5c3046"; //$NON-NLS-1$
    private String _id_iu56 = "ef560ce7-abb1-47df-a503-65bdcdcb62c4"; //$NON-NLS-1$
    private String _id_sf57 = "1160c0ec-2101-4ac3-86ee-1bf7f555cf85"; //$NON-NLS-1$
    private String _id_sf58 = "68d7958d-7aa7-4b71-b66f-c64290a722f8"; //$NON-NLS-1$
    private String _id_sf59 = "8fd6cdee-4221-42dc-91ba-99795e1e2c50"; //$NON-NLS-1$
    private String _id_sf510 = "200c2d6d-caa9-4bfc-a36f-39006e1e555a"; //$NON-NLS-1$
    private String _id_cf511 = "fa73d99f-1847-485d-b66b-79e275137819"; //$NON-NLS-1$
    private String _id_sf512 = "bc42470d-a8bc-40f3-9248-05cd00363990"; //$NON-NLS-1$
    private String _id_eso51 = "f16f4aef-d762-44a7-b26a-b06bb03c0862"; //$NON-NLS-1$
    private String _id_ero52 = "ca0f40f2-7f63-4d46-ab9b-d4969570d529"; //$NON-NLS-1$
    private String _id_eso53 = "6b99503c-0d9d-4b8a-a6b8-78e04bbfbda7"; //$NON-NLS-1$
    private String _id_ero54 = "95a7526c-6450-4c56-802c-a49eeda78ccf"; //$NON-NLS-1$
    private String _id_eso55 = "6f53584e-c6f3-431f-ade7-608696693204"; //$NON-NLS-1$
    private String _id_ero56 = "5afc714d-e17e-4f61-a55a-61e503760708"; //$NON-NLS-1$
    private String _id_eso57 = "f798fd06-7517-4dc4-ad80-76837a693e85"; //$NON-NLS-1$
    private String _id_ero58 = "22b74a84-cf34-4b7b-b3bb-34c1bdeccead"; //$NON-NLS-1$
    private String _id_eso59 = "8c52cd09-fde3-423b-ac4d-2ae3e5550f80"; //$NON-NLS-1$
    private String _id_ero510 = "5de28d25-4c60-4492-af89-f80a2a7837bf"; //$NON-NLS-1$
    private String _id_eso511 = "5b6ee378-8257-4310-b23b-56f5c46d55c9"; //$NON-NLS-1$
    private String _id_ero512 = "c3cc55e2-2ecb-4d00-bd85-d7a997da78d5"; //$NON-NLS-1$
    private String _id_ero513 = "0ca31622-4c6d-4f85-8b3d-e9ea84fa0fef"; //$NON-NLS-1$
    private String _id_ee514 = "62927362-f757-454d-a0b0-8eed025c4ed8"; //$NON-NLS-1$
    private String _id_eso515 = "028cedeb-fa23-4e3e-92d7-cd522ab9c83b"; //$NON-NLS-1$
    private String _id_eso516 = "b3c1c39b-71e3-4b71-8ff1-a3b6427dd839"; //$NON-NLS-1$
    private String _id_ero517 = "81a0b8ba-94ef-4dbd-bc09-bdc3f82fe5ab"; //$NON-NLS-1$
    private String _id_ee518 = "44315063-b66e-4190-a826-bd5cfda20a7b"; //$NON-NLS-1$

    private String _id_pc1 = "1cf7a811-e608-472d-9dcf-2afdf1c9bee2"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      shouldNotBeTransitioned(_id_e54);
      shouldNotBeTransitioned(_id_sf510);
      shouldNotBeTransitioned(_id_sf59);
      mustBeTransitioned(_id_sf512);
      mustBeTransitioned(_id_e55);
      mustBeTransitioned(_id_sf58);
      mustBeTransitioned(_id_sf57);
      mustBeTransitioned(_id_e52);
    }
  }

  /**
   * Scenario transition, scope: A test about scope for scenario transition
   */
  public static class Scope4 extends System2SubsystemTest implements Interphase {

    private String _id_s5 = "a973a99f-fdbc-4bdd-a474-2ab19b4bd212"; //$NON-NLS-1$
    private String _id_pc5 = "df9fff6e-2d3d-4974-9d2f-b97ef4465ccc"; //$NON-NLS-1$
    private String _id_pc3 = "89b9ef51-edbe-4322-a9cb-ef06aaeac751"; //$NON-NLS-1$
    private String _id_pc6 = "6b26a6d2-8691-48e0-a728-9a9e844187d3"; //$NON-NLS-1$
    private String _id_pc4 = "f52e28ce-07ee-4ec9-a2da-573524701ab8"; //$NON-NLS-1$
    private String _id_ce3 = "414a4f08-26f5-4eee-bfbd-5845160c38e1"; //$NON-NLS-1$
    private String _id_me51 = "75802697-206c-4dff-a15d-d365e68a292f"; //$NON-NLS-1$
    private String _id_me52 = "8b23382b-8c5c-4172-8404-f804dbfd327d"; //$NON-NLS-1$
    private String _id_me53 = "10ebac75-81d7-4399-a49a-33d1cc79e99e"; //$NON-NLS-1$
    private String _id_fe54 = "68ffda50-301a-4707-ba47-001886f8a603"; //$NON-NLS-1$
    private String _id_is55 = "a329ba50-5163-4c58-a481-0d8a9bf45ba5"; //$NON-NLS-1$
    private String _id_is56 = "1176f86b-2393-4493-806b-ebfdfae05acc"; //$NON-NLS-1$
    private String _id_is57 = "558673ce-b879-4c82-aef5-1f9f141c942a"; //$NON-NLS-1$
    private String _id_is58 = "5cbc2602-4427-41aa-a63e-f09caccf7ab5"; //$NON-NLS-1$
    private String _id_fe59 = "34840bdf-c61c-4dbd-9ee1-9cf0e8454016"; //$NON-NLS-1$
    private String _id_me510 = "9637d600-7ed7-43a2-ba92-85ec161a0fd4"; //$NON-NLS-1$
    private String _id_is511 = "3155144f-a0fd-4034-ae3b-a48121528671"; //$NON-NLS-1$
    private String _id_is512 = "97055b04-31f8-43b3-a868-65893d682528"; //$NON-NLS-1$
    private String _id_ee513 = "cd0a0dfc-2cd0-4ab3-8d08-2dc50b4d5f21"; //$NON-NLS-1$
    private String _id_me514 = "9d2a50ec-2f3d-4d72-8806-d0fe6af862fc"; //$NON-NLS-1$
    private String _id_me515 = "d4cd2d6b-6f8e-444c-9cc0-da6f17ac1b62"; //$NON-NLS-1$
    private String _id_me516 = "b00d80a6-8fe9-4574-976e-c590d0b17893"; //$NON-NLS-1$
    private String _id_me517 = "ce36b5c2-cacf-41af-b6e8-f7194c0c2350"; //$NON-NLS-1$
    private String _id_me518 = "58eef0cc-1149-4e3a-83d8-60d6728fae26"; //$NON-NLS-1$
    private String _id_me519 = "d6bf13a2-716e-4750-853e-1792a7e4bdf9"; //$NON-NLS-1$
    private String _id_me520 = "2ca418d5-5c8d-4c69-b16d-7e30498dcea0"; //$NON-NLS-1$
    private String _id_me521 = "61e97bb9-c80f-4fe7-8e0e-057356c9668e"; //$NON-NLS-1$
    private String _id_is522 = "290fe9b3-946e-4659-94d4-b3187eb592e9"; //$NON-NLS-1$
    private String _id_is523 = "6dd548a1-6c39-402c-b1ea-787a2341b65b"; //$NON-NLS-1$
    private String _id_me524 = "4a7868fa-583c-4ce6-93be-a07c599ecbdb"; //$NON-NLS-1$
    private String _id_me525 = "c72346b7-5b5b-489a-b56c-15b744746d9d"; //$NON-NLS-1$
    private String _id_fe526 = "4ac2e100-31cb-4199-a0b2-b051726e3fb8"; //$NON-NLS-1$
    private String _id_io527 = "ae32b42e-ab7f-4b6c-9630-0307f3469a83"; //$NON-NLS-1$
    private String _id_me528 = "15bd40bb-8bb3-4263-aaf2-01f98cfcb9b6"; //$NON-NLS-1$
    private String _id_me529 = "8dd010e4-ed58-4d77-828d-bf9bf45a366a"; //$NON-NLS-1$
    private String _id_is530 = "62890d9f-2eb7-49c1-9015-5c84fe2048ef"; //$NON-NLS-1$
    private String _id_is531 = "ebcc62ea-e681-4570-b365-474b0924c68b"; //$NON-NLS-1$
    private String _id_ee532 = "6d965808-8a40-422c-ad82-8fe33c826499"; //$NON-NLS-1$
    private String _id_fe533 = "9fee045d-9f92-4427-b30f-8c78ef5a08ed"; //$NON-NLS-1$
    private String _id_e51 = "6f07f2b7-7375-4f2f-a375-d85a86be25bc"; //$NON-NLS-1$
    private String _id_e52 = "f16179c3-9e3c-4260-9b52-32b8cfe0828d"; //$NON-NLS-1$
    private String _id_e53 = "e8f0ec44-cee2-40ff-a06a-d4069572a419"; //$NON-NLS-1$
    private String _id_e54 = "45a4a7ca-e1cb-4aa5-8637-c79637959e3d"; //$NON-NLS-1$
    private String _id_e55 = "ad905122-3e9e-4c0e-be21-62b50f5c3046"; //$NON-NLS-1$
    private String _id_iu56 = "ef560ce7-abb1-47df-a503-65bdcdcb62c4"; //$NON-NLS-1$
    private String _id_sf57 = "1160c0ec-2101-4ac3-86ee-1bf7f555cf85"; //$NON-NLS-1$
    private String _id_sf58 = "68d7958d-7aa7-4b71-b66f-c64290a722f8"; //$NON-NLS-1$
    private String _id_sf59 = "8fd6cdee-4221-42dc-91ba-99795e1e2c50"; //$NON-NLS-1$
    private String _id_sf510 = "200c2d6d-caa9-4bfc-a36f-39006e1e555a"; //$NON-NLS-1$
    private String _id_cf511 = "fa73d99f-1847-485d-b66b-79e275137819"; //$NON-NLS-1$
    private String _id_sf512 = "bc42470d-a8bc-40f3-9248-05cd00363990"; //$NON-NLS-1$
    private String _id_eso51 = "f16f4aef-d762-44a7-b26a-b06bb03c0862"; //$NON-NLS-1$
    private String _id_ero52 = "ca0f40f2-7f63-4d46-ab9b-d4969570d529"; //$NON-NLS-1$
    private String _id_eso53 = "6b99503c-0d9d-4b8a-a6b8-78e04bbfbda7"; //$NON-NLS-1$
    private String _id_ero54 = "95a7526c-6450-4c56-802c-a49eeda78ccf"; //$NON-NLS-1$
    private String _id_eso55 = "6f53584e-c6f3-431f-ade7-608696693204"; //$NON-NLS-1$
    private String _id_ero56 = "5afc714d-e17e-4f61-a55a-61e503760708"; //$NON-NLS-1$
    private String _id_eso57 = "f798fd06-7517-4dc4-ad80-76837a693e85"; //$NON-NLS-1$
    private String _id_ero58 = "22b74a84-cf34-4b7b-b3bb-34c1bdeccead"; //$NON-NLS-1$
    private String _id_eso59 = "8c52cd09-fde3-423b-ac4d-2ae3e5550f80"; //$NON-NLS-1$
    private String _id_ero510 = "5de28d25-4c60-4492-af89-f80a2a7837bf"; //$NON-NLS-1$
    private String _id_eso511 = "5b6ee378-8257-4310-b23b-56f5c46d55c9"; //$NON-NLS-1$
    private String _id_ero512 = "c3cc55e2-2ecb-4d00-bd85-d7a997da78d5"; //$NON-NLS-1$
    private String _id_ero513 = "0ca31622-4c6d-4f85-8b3d-e9ea84fa0fef"; //$NON-NLS-1$
    private String _id_ee514 = "62927362-f757-454d-a0b0-8eed025c4ed8"; //$NON-NLS-1$
    private String _id_eso515 = "028cedeb-fa23-4e3e-92d7-cd522ab9c83b"; //$NON-NLS-1$
    private String _id_eso516 = "b3c1c39b-71e3-4b71-8ff1-a3b6427dd839"; //$NON-NLS-1$
    private String _id_ero517 = "81a0b8ba-94ef-4dbd-bc09-bdc3f82fe5ab"; //$NON-NLS-1$
    private String _id_ee518 = "44315063-b66e-4190-a826-bd5cfda20a7b"; //$NON-NLS-1$

    private String _id_pc1 = "1cf7a811-e608-472d-9dcf-2afdf1c9bee2"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_e51);
    }

  }

  /**
   * Scenario transition, scope: A test about scope for scenario transition
   */
  public static class Scope5 extends System2SubsystemTest implements Crossphase {

    private String _id_s5 = "a973a99f-fdbc-4bdd-a474-2ab19b4bd212"; //$NON-NLS-1$
    private String _id_pc5 = "df9fff6e-2d3d-4974-9d2f-b97ef4465ccc"; //$NON-NLS-1$
    private String _id_pc3 = "89b9ef51-edbe-4322-a9cb-ef06aaeac751"; //$NON-NLS-1$
    private String _id_pc6 = "6b26a6d2-8691-48e0-a728-9a9e844187d3"; //$NON-NLS-1$
    private String _id_pc4 = "f52e28ce-07ee-4ec9-a2da-573524701ab8"; //$NON-NLS-1$
    private String _id_ce3 = "414a4f08-26f5-4eee-bfbd-5845160c38e1"; //$NON-NLS-1$
    private String _id_me51 = "75802697-206c-4dff-a15d-d365e68a292f"; //$NON-NLS-1$
    private String _id_me52 = "8b23382b-8c5c-4172-8404-f804dbfd327d"; //$NON-NLS-1$
    private String _id_me53 = "10ebac75-81d7-4399-a49a-33d1cc79e99e"; //$NON-NLS-1$
    private String _id_fe54 = "68ffda50-301a-4707-ba47-001886f8a603"; //$NON-NLS-1$
    private String _id_is55 = "a329ba50-5163-4c58-a481-0d8a9bf45ba5"; //$NON-NLS-1$
    private String _id_is56 = "1176f86b-2393-4493-806b-ebfdfae05acc"; //$NON-NLS-1$
    private String _id_is57 = "558673ce-b879-4c82-aef5-1f9f141c942a"; //$NON-NLS-1$
    private String _id_is58 = "5cbc2602-4427-41aa-a63e-f09caccf7ab5"; //$NON-NLS-1$
    private String _id_fe59 = "34840bdf-c61c-4dbd-9ee1-9cf0e8454016"; //$NON-NLS-1$
    private String _id_me510 = "9637d600-7ed7-43a2-ba92-85ec161a0fd4"; //$NON-NLS-1$
    private String _id_is511 = "3155144f-a0fd-4034-ae3b-a48121528671"; //$NON-NLS-1$
    private String _id_is512 = "97055b04-31f8-43b3-a868-65893d682528"; //$NON-NLS-1$
    private String _id_ee513 = "cd0a0dfc-2cd0-4ab3-8d08-2dc50b4d5f21"; //$NON-NLS-1$
    private String _id_me514 = "9d2a50ec-2f3d-4d72-8806-d0fe6af862fc"; //$NON-NLS-1$
    private String _id_me515 = "d4cd2d6b-6f8e-444c-9cc0-da6f17ac1b62"; //$NON-NLS-1$
    private String _id_me516 = "b00d80a6-8fe9-4574-976e-c590d0b17893"; //$NON-NLS-1$
    private String _id_me517 = "ce36b5c2-cacf-41af-b6e8-f7194c0c2350"; //$NON-NLS-1$
    private String _id_me518 = "58eef0cc-1149-4e3a-83d8-60d6728fae26"; //$NON-NLS-1$
    private String _id_me519 = "d6bf13a2-716e-4750-853e-1792a7e4bdf9"; //$NON-NLS-1$
    private String _id_me520 = "2ca418d5-5c8d-4c69-b16d-7e30498dcea0"; //$NON-NLS-1$
    private String _id_me521 = "61e97bb9-c80f-4fe7-8e0e-057356c9668e"; //$NON-NLS-1$
    private String _id_is522 = "290fe9b3-946e-4659-94d4-b3187eb592e9"; //$NON-NLS-1$
    private String _id_is523 = "6dd548a1-6c39-402c-b1ea-787a2341b65b"; //$NON-NLS-1$
    private String _id_me524 = "4a7868fa-583c-4ce6-93be-a07c599ecbdb"; //$NON-NLS-1$
    private String _id_me525 = "c72346b7-5b5b-489a-b56c-15b744746d9d"; //$NON-NLS-1$
    private String _id_fe526 = "4ac2e100-31cb-4199-a0b2-b051726e3fb8"; //$NON-NLS-1$
    private String _id_io527 = "ae32b42e-ab7f-4b6c-9630-0307f3469a83"; //$NON-NLS-1$
    private String _id_me528 = "15bd40bb-8bb3-4263-aaf2-01f98cfcb9b6"; //$NON-NLS-1$
    private String _id_me529 = "8dd010e4-ed58-4d77-828d-bf9bf45a366a"; //$NON-NLS-1$
    private String _id_is530 = "62890d9f-2eb7-49c1-9015-5c84fe2048ef"; //$NON-NLS-1$
    private String _id_is531 = "ebcc62ea-e681-4570-b365-474b0924c68b"; //$NON-NLS-1$
    private String _id_ee532 = "6d965808-8a40-422c-ad82-8fe33c826499"; //$NON-NLS-1$
    private String _id_fe533 = "9fee045d-9f92-4427-b30f-8c78ef5a08ed"; //$NON-NLS-1$
    private String _id_e51 = "6f07f2b7-7375-4f2f-a375-d85a86be25bc"; //$NON-NLS-1$
    private String _id_e52 = "f16179c3-9e3c-4260-9b52-32b8cfe0828d"; //$NON-NLS-1$
    private String _id_e53 = "e8f0ec44-cee2-40ff-a06a-d4069572a419"; //$NON-NLS-1$
    private String _id_e54 = "45a4a7ca-e1cb-4aa5-8637-c79637959e3d"; //$NON-NLS-1$
    private String _id_e55 = "ad905122-3e9e-4c0e-be21-62b50f5c3046"; //$NON-NLS-1$
    private String _id_iu56 = "ef560ce7-abb1-47df-a503-65bdcdcb62c4"; //$NON-NLS-1$
    private String _id_sf57 = "1160c0ec-2101-4ac3-86ee-1bf7f555cf85"; //$NON-NLS-1$
    private String _id_sf58 = "68d7958d-7aa7-4b71-b66f-c64290a722f8"; //$NON-NLS-1$
    private String _id_sf59 = "8fd6cdee-4221-42dc-91ba-99795e1e2c50"; //$NON-NLS-1$
    private String _id_sf510 = "200c2d6d-caa9-4bfc-a36f-39006e1e555a"; //$NON-NLS-1$
    private String _id_cf511 = "fa73d99f-1847-485d-b66b-79e275137819"; //$NON-NLS-1$
    private String _id_sf512 = "bc42470d-a8bc-40f3-9248-05cd00363990"; //$NON-NLS-1$
    private String _id_eso51 = "f16f4aef-d762-44a7-b26a-b06bb03c0862"; //$NON-NLS-1$
    private String _id_ero52 = "ca0f40f2-7f63-4d46-ab9b-d4969570d529"; //$NON-NLS-1$
    private String _id_eso53 = "6b99503c-0d9d-4b8a-a6b8-78e04bbfbda7"; //$NON-NLS-1$
    private String _id_ero54 = "95a7526c-6450-4c56-802c-a49eeda78ccf"; //$NON-NLS-1$
    private String _id_eso55 = "6f53584e-c6f3-431f-ade7-608696693204"; //$NON-NLS-1$
    private String _id_ero56 = "5afc714d-e17e-4f61-a55a-61e503760708"; //$NON-NLS-1$
    private String _id_eso57 = "f798fd06-7517-4dc4-ad80-76837a693e85"; //$NON-NLS-1$
    private String _id_ero58 = "22b74a84-cf34-4b7b-b3bb-34c1bdeccead"; //$NON-NLS-1$
    private String _id_eso59 = "8c52cd09-fde3-423b-ac4d-2ae3e5550f80"; //$NON-NLS-1$
    private String _id_ero510 = "5de28d25-4c60-4492-af89-f80a2a7837bf"; //$NON-NLS-1$
    private String _id_eso511 = "5b6ee378-8257-4310-b23b-56f5c46d55c9"; //$NON-NLS-1$
    private String _id_ero512 = "c3cc55e2-2ecb-4d00-bd85-d7a997da78d5"; //$NON-NLS-1$
    private String _id_ero513 = "0ca31622-4c6d-4f85-8b3d-e9ea84fa0fef"; //$NON-NLS-1$
    private String _id_ee514 = "62927362-f757-454d-a0b0-8eed025c4ed8"; //$NON-NLS-1$
    private String _id_eso515 = "028cedeb-fa23-4e3e-92d7-cd522ab9c83b"; //$NON-NLS-1$
    private String _id_eso516 = "b3c1c39b-71e3-4b71-8ff1-a3b6427dd839"; //$NON-NLS-1$
    private String _id_ero517 = "81a0b8ba-94ef-4dbd-bc09-bdc3f82fe5ab"; //$NON-NLS-1$
    private String _id_ee518 = "44315063-b66e-4190-a826-bd5cfda20a7b"; //$NON-NLS-1$

    private String _id_pc1 = "1cf7a811-e608-472d-9dcf-2afdf1c9bee2"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      shouldNotBeTransitioned(_id_e51);
    }
  }

  /**
   * Scenario transition, scope", "A test about scope for scenario transition") {
   */
  public static class Scope6 extends System2SubsystemTest implements Interphase, Crossphase {

    private String _id_s6 = "8c4ce96a-99f4-44d1-bd32-08fb3b4142bb"; //$NON-NLS-1$
    private String _id_ir61 = "62cb9c2b-d687-4489-8b7e-885113c8f4c3"; //$NON-NLS-1$
    private String _id_ir62 = "de9ea2f9-b708-4af8-bc7d-7c9cfe1d8d42"; //$NON-NLS-1$
    private String _id_ir63 = "9c425e6f-d5e0-45bb-9c68-2de0564ad5f0"; //$NON-NLS-1$
    private String _id_ir64 = "e277632a-8c4f-424e-90fc-eac2aac5e01f"; //$NON-NLS-1$
    private String _id_sm61 = "a331f18a-0384-4fe5-bea6-d286c1802fd0"; //$NON-NLS-1$
    private String _id_sm62 = "49fee0e1-4a5c-40b0-870d-8664440c1976"; //$NON-NLS-1$
    private String _id_sm63 = "456b6111-d487-4e43-8ab0-e56b795831c2"; //$NON-NLS-1$
    private String _id_sm64 = "2595948e-8e8f-4dc1-b3d3-7b66182a607e"; //$NON-NLS-1$
    private String _id_sm65 = "959312a7-b5de-4712-b68e-1cffbf34c9c3"; //$NON-NLS-1$
    private String _id_me61 = "1c783a78-916a-408e-b006-47078f6139ae"; //$NON-NLS-1$
    private String _id_me62 = "ef037ac2-38f2-4bb6-8b23-b46d9fbb58ee"; //$NON-NLS-1$
    private String _id_me63 = "288fccf8-09f2-4943-98f8-436a7a756c9e"; //$NON-NLS-1$
    private String _id_me64 = "ee3e6bc9-4740-4ceb-bc0e-a468cf5a878d"; //$NON-NLS-1$
    private String _id_me65 = "9d235194-94aa-4fcd-8c63-a54480b7ece7"; //$NON-NLS-1$
    private String _id_me66 = "a8124efc-b6ba-4fbc-badf-1a1cdeb0c2c9"; //$NON-NLS-1$
    private String _id_is68 = "b4784543-ee9c-41e1-b433-c52099e6dee3"; //$NON-NLS-1$
    private String _id_is69 = "dcba6bc9-9b68-4a58-88ec-a744370293b7"; //$NON-NLS-1$
    private String _id_is610 = "271fd90f-2d5f-466d-b694-4aa3099fa94d"; //$NON-NLS-1$
    private String _id_is611 = "edf2dfbc-a374-4bb2-b47e-2125668eea0a"; //$NON-NLS-1$
    private String _id_is612 = "4766c181-d6f4-4c8f-8f51-08088f648d24"; //$NON-NLS-1$
    private String _id_fe613 = "0c03d3ab-a36e-4a8c-b43c-54250ad2e88f"; //$NON-NLS-1$
    private String _id_fe614 = "895f3d4f-92e7-4c24-9207-fc1d246f473a"; //$NON-NLS-1$
    private String _id_fe615 = "18aaae26-992c-48cf-b9bb-81fb9a55dc3c"; //$NON-NLS-1$
    private String _id_fe616 = "b16df449-4c48-4304-b71d-7189d4236fcb"; //$NON-NLS-1$
    private String _id_is617 = "7affc64b-0761-4805-8059-0716265f1912"; //$NON-NLS-1$
    private String _id_is618 = "21cd9f69-38b1-4453-99f0-f9080f774132"; //$NON-NLS-1$
    private String _id_fe619 = "c8a32cfa-4824-4f5c-87ed-5dde1501eb11"; //$NON-NLS-1$
    private String _id_fe620 = "23cea701-11c2-4663-87d3-ee5fb20d5ee4"; //$NON-NLS-1$
    private String _id_is621 = "6c373dc4-1e0d-4f41-986c-fd42d81b466a"; //$NON-NLS-1$
    private String _id_fe622 = "31fdf3c5-c5fe-4089-8a10-5576c97c4974"; //$NON-NLS-1$
    private String _id_io623 = "8690d961-c5c2-4f0d-87e4-fb8b89d2e2c4"; //$NON-NLS-1$
    private String _id_me624 = "39e56c62-7b64-4ba7-b598-5e11c9105dd5"; //$NON-NLS-1$
    private String _id_is625 = "a7b3f7ff-79b9-42d6-8e99-74107740e57f"; //$NON-NLS-1$
    private String _id_is626 = "18c385da-2a64-4f98-ac11-29a7c8a276a9"; //$NON-NLS-1$
    private String _id_me627 = "fd70a29c-e93e-40e2-97e5-a2f6c109db3c"; //$NON-NLS-1$
    private String _id_is628 = "cc502ee1-40e3-411e-bfdf-491803a67910"; //$NON-NLS-1$
    private String _id_is629 = "7a57713c-69f7-4ca2-ab99-184a043a40a9"; //$NON-NLS-1$
    private String _id_ee630 = "b31c0ad0-02ae-4357-849a-93e1fb6b41e1"; //$NON-NLS-1$
    private String _id_fe631 = "78a5135d-d2a1-494e-9b99-fcc40eced3e5"; //$NON-NLS-1$
    private String _id_fe632 = "83d7c6ff-1116-44f3-9be2-302a1ecaaab4"; //$NON-NLS-1$
    private String _id_fe633 = "4b1027e9-8a91-4169-865d-d1691b9e9e8e"; //$NON-NLS-1$
    private String _id_e61 = "787a5d4c-d990-48f2-ad8a-6e239620ac86"; //$NON-NLS-1$
    private String _id_e62 = "5b27c9e1-1816-4f1e-ac0d-7ff9f3812b4b"; //$NON-NLS-1$
    private String _id_sf63 = "cc1f5404-6b15-43b0-be0f-771e2753fa8d"; //$NON-NLS-1$
    private String _id_sf64 = "6a01d363-f6a7-48aa-86e0-de1d064dc186"; //$NON-NLS-1$
    private String _id_sf65 = "1c9ba981-1388-4dfc-b9c5-b1dd399a038e"; //$NON-NLS-1$
    private String _id_sf66 = "6d3c6cb3-f821-4b62-87c5-e33d2e4c21a6"; //$NON-NLS-1$
    private String _id_iu67 = "8557c750-5568-4497-9fd4-b7c31c575de8"; //$NON-NLS-1$
    private String _id_cf68 = "2cd9942b-d354-4141-9293-eb057e7ded64"; //$NON-NLS-1$
    private String _id_e69 = "c8219ca5-ddac-45a0-a94c-676aafa3d6a0"; //$NON-NLS-1$
    private String _id_iu610 = "1809b0fb-c340-4b8d-8c0e-654aa582a624"; //$NON-NLS-1$
    private String _id_iu611 = "818989b8-156c-4204-b2de-03b9685fb91d"; //$NON-NLS-1$
    private String _id_iu612 = "142c8a21-ec39-442e-9e59-14f14a0e7b13"; //$NON-NLS-1$
    private String _id_sf613 = "cf999733-9df9-4015-9c52-539e31d7fe21"; //$NON-NLS-1$
    private String _id_sf614 = "31e180f2-c745-41df-9b47-66648b5c446c"; //$NON-NLS-1$
    private String _id_eso61 = "1455721f-3f4c-441c-aa85-a5285638abc3"; //$NON-NLS-1$
    private String _id_ero62 = "976099b0-45ce-4727-86a6-e507f171ad10"; //$NON-NLS-1$
    private String _id_eso63 = "202c3cc4-56d1-4149-b811-cec1fcfd3e91"; //$NON-NLS-1$
    private String _id_ero64 = "eabc04c4-53d8-4e79-b320-eab36f00bf5a"; //$NON-NLS-1$
    private String _id_eso65 = "17261a41-74e3-4ba1-af90-4c0a3e881038"; //$NON-NLS-1$
    private String _id_ero66 = "2353c5a1-b15f-463f-9cff-4658ca52c74c"; //$NON-NLS-1$
    private String _id_ee67 = "3776baaf-e1e9-482b-9e45-43eed4ffb651"; //$NON-NLS-1$
    private String _id_ero68 = "177c1798-4b66-4ba2-a20d-fd3b1f9b09dc"; //$NON-NLS-1$
    private String _id_ee69 = "edd44b5c-a2f1-4e83-ba6f-754fbb16e560"; //$NON-NLS-1$
    private String _id_eso610 = "106b895c-d229-4e32-bfcd-03125f38c7e3"; //$NON-NLS-1$

    private String _id_pc1 = "1cf7a811-e608-472d-9dcf-2afdf1c9bee2"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      shouldNotBeTransitioned(_id_e62);
      shouldNotBeTransitioned(_id_sf64);
      shouldNotBeTransitioned(_id_sf63);
      shouldNotBeTransitioned(_id_iu67);
      shouldNotBeTransitioned(_id_cf68);
      mustBeTransitioned(_id_iu611);
      mustBeTransitioned(_id_iu612); // maybe not
      shouldNotBeTransitioned(_id_sf65);
      shouldNotBeTransitioned(_id_iu67);
      shouldNotBeTransitioned(_id_sf66);

      shouldNotBeTransitioned(_id_cf68);
      shouldNotBeTransitioned(_id_e69);
      shouldNotBeTransitioned(_id_iu610);
      mustBeTransitioned(_id_sf613);
      shouldNotBeTransitioned(_id_sf614);
      shouldNotBeTransitioned(_id_ir61);
      shouldNotBeTransitioned(_id_ir63);
    }
  }

  // TODO
  // tests.add(new SystemSubSystemTest(CROSS_PHASES, "Scenario transition, no duplication", "A test about scope for
  // scenario transition") {
  //
  // private String _id_s6 = "8c4ce96a-99f4-44d1-bd32-08fb3b4142bb"; //$NON-NLS-1$
  // private String _id_pc1 = "1cf7a811-e608-472d-9dcf-2afdf1c9bee2"; //$NON-NLS-1$
  //
  // @Override
  // protected Collection<GenericParameter<?>> getHeadlessParameters() {
  // Collection<GenericParameter<?>> result = super.getHeadlessParameters();
  // result.add(new GenericParameter<Boolean>(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE,
  // IOptionsConstants.SCENARIO_EXPORT));
  // return result;
  // }
  //
  // @Override
  // protected ModelElement getProjectionElement() {
  // return (ModelElement) getObject(_id_pc1);
  // }
  //
  // @Override
  // protected void verify(Kind kind) {
  // EObject object = mustBeTransitioned(_id_s6);
  // AbstractCapability capability = (AbstractCapability) object.eContainer();
  // assertTrue(capability.getOwnedScenarios().size() == 4);
  // }
  //
  // });
  //
  // tests.add(new SystemSubSystemTest(CROSS_PHASES, "Scenario transition, no duplication", "A test about scope for
  // scenario transition") {
  //
  // private String _id_s6 = "8c4ce96a-99f4-44d1-bd32-08fb3b4142bb"; //$NON-NLS-1$
  // private String _id_pc1 = "1cf7a811-e608-472d-9dcf-2afdf1c9bee2"; //$NON-NLS-1$
  //
  // @Override
  // protected Collection<GenericParameter<?>> getHeadlessParameters() {
  // Collection<GenericParameter<?>> result = super.getHeadlessParameters();
  // result.add(new GenericParameter<Boolean>(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE,
  // IOptionsConstants.SCENARIO_EXPORT));
  // return result;
  // }
  //
  // @Override
  // protected ModelElement getProjectionElement() {
  // return (ModelElement) getObject(_id_pc1);
  // }
  //
  // @Override
  // protected void verify(Kind kind) {
  // EObject object = mustBeTransitioned(_id_s6);
  // AbstractCapability capability = (AbstractCapability) object.eContainer();
  // assertTrue(capability.getOwnedScenarios().size() == 4);
  // }
  //
  // });

  /**
   * Scenario transition on PC when cycle A test to check if incomplete rules on functions lead to complete scenario
   * transition
   */
  public static class Cycle1 extends System2SubsystemTest implements Interphase {

    private String _id_es = "cb8f5b6c-dbf4-4088-a124-be4b3e1c5bea"; //$NON-NLS-1$
    private String _id_bc4 = "a209eec6-c9c2-4633-9e7e-b554fc89e913"; //$NON-NLS-1$
    private String _id_bc1 = "13cf94ff-8beb-4ff2-a7be-e63fe486e0d8"; //$NON-NLS-1$
    private String _id_bc3 = "c22a5790-61cc-4f65-99df-b0aece56de2c"; //$NON-NLS-1$
    private String _id_bc2 = "35ad34a5-059d-4928-9e05-6b2aacd9bd98"; //$NON-NLS-1$
    private String _id_fe5 = "f6acabc7-eef8-4a5c-b8b6-3b457395e2a4"; //$NON-NLS-1$
    private String _id_fe4 = "ff768ff2-2003-4ac0-b58c-6f6187e5c7a4"; //$NON-NLS-1$
    private String _id_fe1 = "39225e82-c60d-4739-8add-cc9e4228df4e"; //$NON-NLS-1$
    private String _id_fe3 = "3e12a01d-49ca-4b55-ad8a-b2bfa34e835c"; //$NON-NLS-1$
    private String _id_fe2 = "f39e96e2-141f-4653-8c8d-e9e0cef95d5f"; //$NON-NLS-1$
    private String _id_sf1 = "6886c921-9d85-4815-8ec6-cdcfe599694a"; //$NON-NLS-1$
    private String _id_sf2 = "aa08a8b3-3af1-41d1-b0c6-c961a62837fb"; //$NON-NLS-1$
    private String _id_sf3 = "0f207f00-211c-4883-b422-5bd63ec1fdb6"; //$NON-NLS-1$
    private String _id_sf4 = "73962458-2ba4-4534-9a68-e809775aa9e9"; //$NON-NLS-1$
    private String _id_pc = "79a6d4eb-ec2a-45c8-8968-df9389297588"; //$NON-NLS-1$
    private String _id_pc1 = "f4ad0e1a-205a-4788-9940-ab6bc68f7c04"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
      addSharedParameter(IOptionsConstants.STATE_MODES_EXPORT, IOptionsConstants.STATE_MODES_HIERARCHICAL_VALUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc);
    }

    @Override
    protected void verify() {
      EObject object = mustBeTransitioned(_id_es);

      assertTrue(((Scenario) object).getOwnedInstanceRoles().size() == 4);
      mustBeTransitioned(_id_fe5);
      mustBeTransitioned(_id_fe4);
      mustBeTransitioned(_id_fe1);
      mustBeTransitioned(_id_fe3);
      mustBeTransitioned(_id_fe2);
      mustBeTransitioned(_id_sf1);
      mustBeTransitioned(_id_sf2);
      mustBeTransitioned(_id_sf3);
      mustBeTransitioned(_id_sf4);
      mustBeTransitioned(_id_pc);
    }

  }

  /**
   * Scenario transition on PC when cycle A test to check if incomplete rules on functions lead to complete scenario
   * transition
   */
  public static class Cycle2 extends System2SubsystemTest implements Crossphase {

    private String _id_es = "cb8f5b6c-dbf4-4088-a124-be4b3e1c5bea"; //$NON-NLS-1$
    private String _id_bc4 = "a209eec6-c9c2-4633-9e7e-b554fc89e913"; //$NON-NLS-1$
    private String _id_bc1 = "13cf94ff-8beb-4ff2-a7be-e63fe486e0d8"; //$NON-NLS-1$
    private String _id_bc3 = "c22a5790-61cc-4f65-99df-b0aece56de2c"; //$NON-NLS-1$
    private String _id_bc2 = "35ad34a5-059d-4928-9e05-6b2aacd9bd98"; //$NON-NLS-1$
    private String _id_fe5 = "f6acabc7-eef8-4a5c-b8b6-3b457395e2a4"; //$NON-NLS-1$
    private String _id_fe4 = "ff768ff2-2003-4ac0-b58c-6f6187e5c7a4"; //$NON-NLS-1$
    private String _id_fe1 = "39225e82-c60d-4739-8add-cc9e4228df4e"; //$NON-NLS-1$
    private String _id_fe3 = "3e12a01d-49ca-4b55-ad8a-b2bfa34e835c"; //$NON-NLS-1$
    private String _id_fe2 = "f39e96e2-141f-4653-8c8d-e9e0cef95d5f"; //$NON-NLS-1$
    private String _id_sf1 = "6886c921-9d85-4815-8ec6-cdcfe599694a"; //$NON-NLS-1$
    private String _id_sf2 = "aa08a8b3-3af1-41d1-b0c6-c961a62837fb"; //$NON-NLS-1$
    private String _id_sf3 = "0f207f00-211c-4883-b422-5bd63ec1fdb6"; //$NON-NLS-1$
    private String _id_sf4 = "73962458-2ba4-4534-9a68-e809775aa9e9"; //$NON-NLS-1$
    private String _id_pc = "79a6d4eb-ec2a-45c8-8968-df9389297588"; //$NON-NLS-1$
    private String _id_pc1 = "f4ad0e1a-205a-4788-9940-ab6bc68f7c04"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
      addSharedParameter(IOptionsConstants.STATE_MODES_EXPORT, IOptionsConstants.STATE_MODES_HIERARCHICAL_VALUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc);
    }

    @Override
    protected void verify() {
      EObject object = mustBeTransitioned(_id_es);

      assertTrue(((Scenario) object).getOwnedInstanceRoles().size() == 2);
      mustBeTransitioned(_id_fe5);
      mustBeTransitioned(_id_fe4);
      mustBeTransitioned(_id_fe1);
      mustBeTransitioned(_id_fe3);
      mustBeTransitioned(_id_fe2);
      mustBeTransitioned(_id_sf1);
      mustBeTransitioned(_id_sf2);
      mustBeTransitioned(_id_sf3);
      mustBeTransitioned(_id_sf4);
      mustBeTransitioned(_id_pc);

    }
  }

  /**
   * Scenario transition on PC1 when cycle: A test to check if incomplete rules on functions lead to complete scenario
   * transition
   */
  public static class Cycle3 extends System2SubsystemTest implements Interphase, Crossphase {

    private String _id_es = "cb8f5b6c-dbf4-4088-a124-be4b3e1c5bea"; //$NON-NLS-1$
    private String _id_bc4 = "a209eec6-c9c2-4633-9e7e-b554fc89e913"; //$NON-NLS-1$
    private String _id_bc1 = "13cf94ff-8beb-4ff2-a7be-e63fe486e0d8"; //$NON-NLS-1$
    private String _id_bc3 = "c22a5790-61cc-4f65-99df-b0aece56de2c"; //$NON-NLS-1$
    private String _id_bc2 = "35ad34a5-059d-4928-9e05-6b2aacd9bd98"; //$NON-NLS-1$
    private String _id_fe5 = "f6acabc7-eef8-4a5c-b8b6-3b457395e2a4"; //$NON-NLS-1$
    private String _id_fe4 = "ff768ff2-2003-4ac0-b58c-6f6187e5c7a4"; //$NON-NLS-1$
    private String _id_fe1 = "39225e82-c60d-4739-8add-cc9e4228df4e"; //$NON-NLS-1$
    private String _id_fe3 = "3e12a01d-49ca-4b55-ad8a-b2bfa34e835c"; //$NON-NLS-1$
    private String _id_fe2 = "f39e96e2-141f-4653-8c8d-e9e0cef95d5f"; //$NON-NLS-1$
    private String _id_sf1 = "6886c921-9d85-4815-8ec6-cdcfe599694a"; //$NON-NLS-1$
    private String _id_sf2 = "aa08a8b3-3af1-41d1-b0c6-c961a62837fb"; //$NON-NLS-1$
    private String _id_sf3 = "0f207f00-211c-4883-b422-5bd63ec1fdb6"; //$NON-NLS-1$
    private String _id_sf4 = "73962458-2ba4-4534-9a68-e809775aa9e9"; //$NON-NLS-1$
    private String _id_pc = "79a6d4eb-ec2a-45c8-8968-df9389297588"; //$NON-NLS-1$
    private String _id_pc1 = "f4ad0e1a-205a-4788-9940-ab6bc68f7c04"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      addSharedParameter(IOptionsConstants.SCENARIO_EXPORT, Boolean.TRUE);
      addSharedParameter(IOptionsConstants.STATE_MODES_EXPORT, IOptionsConstants.STATE_MODES_HIERARCHICAL_VALUE);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc1);
    }

    @Override
    protected void verify() {
      EObject object = mustBeTransitioned(_id_es);

      assertTrue(((Scenario) object).getOwnedInstanceRoles().size() == 3);

      mustBeTransitioned(_id_bc1);
      mustBeTransitioned(_id_bc3);
      mustBeTransitioned(_id_bc4);

      mustBeTransitioned(_id_fe5);
      mustBeTransitioned(_id_fe4);
      shouldNotBeTransitioned(_id_fe1);
      shouldNotBeTransitioned(_id_fe3);
      shouldNotBeTransitioned(_id_fe2);
      mustBeTransitioned(_id_sf1);
      mustBeTransitioned(_id_sf2);
      mustBeTransitioned(_id_sf3);
      mustBeTransitioned(_id_sf4);
      mustBeTransitioned(_id_pc1);
    }
  }
}
