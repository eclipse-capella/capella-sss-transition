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

import org.polarsys.capella.core.data.ctx.CtxPackage;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

//@formatter:off
/**
 * DataValues transition: Test that Data values are correctly imported (according to scope)"
 * 
 * identifier:name:'PF1',id=#3faafaed-b16f-4875-9aec-cac27a31d576
 * identifier:name:'Data',id=#f7157f10-821f-40d1-9fe4-db709ca785c8
 * identifier:name:'PS',id=#e3529bbc-0c8d-48aa-a31e-d93af0cb75e9
 
 * - Create 'PF1' [PhysicalFunction]
 *   - Create '[Function Realization] to Root Logical Function' [FunctionRealization]
 *     > Link '[Function Realization] to Root Logical Function' to 'PF1' [sourceElement], 'Root Logical Function' [targetElement]
 *   - Create 'PF11' [PhysicalFunction]
 *     - Create 'FOP111' [FunctionOutputPort]
 *   - Create 'PF12' [PhysicalFunction]
 *     - Create 'FIP121' [FunctionInputPort]
 *   - Create 'FE11' [FunctionalExchange]
 *     > Link 'FE11' to 'FOP111' [source], 'FIP121' [target]
 *     > Link 'FE11' to '{EI211}' [exchangedItems]
 * - Create 'Data' [DataPkg]
 *   - Create 'DP1' [DataPkg]
 *     - Create 'DP11' [DataPkg]
 *       - Create 'C111' [Class]
 *     - Create 'DP12' [DataPkg]
 *     - Create 'BT11' [BooleanType]
 *   - Create 'DP2' [DataPkg]
 *     - Create 'EI21' [ExchangeItem]
 *     - Create 'DP21' [DataPkg]
 *       - Create 'EI211' [ExchangeItem]
 *         - Create 'EIE2111: C111' [ExchangeItemElement]
 *           > Link 'EIE2111: C111' to 'C111' [abstractType]
 *           - Create '[minCard] LNV21111 = 1' [LiteralNumericValue]
 *           - Create '[maxCard] LNV21111 = 1' [LiteralNumericValue]
 *       - Create 'EI212' [ExchangeItem]
 *         - Create 'EIE2121: BT11' [ExchangeItemElement]
 *           > Link 'EIE2121: BT11' to 'BT11' [abstractType]
 *           - Create '[minCard] LNV21211 = 1' [LiteralNumericValue]
 *           - Create '[maxCard] LNV21211 = 1' [LiteralNumericValue]
 *       - Create 'DP211' [DataPkg]
 *     - Create 'DP22' [DataPkg]
 * - Create 'PS' [PhysicalComponent]
 *   > Set 'PS' to 'UNSET' [kind], 'UNSET' [nature]
 *   - Create 'PC1: PC1' [Part]
 *     > Link 'PC1: PC1' to 'PC1' [abstractType]
 *     - Create '[Part Deployment Link] to PC3' [PartDeploymentLink]
 *   - Create 'PC2: PC2' [Part]
 *     > Link 'PC2: PC2' to 'PC2' [abstractType]
 *     - Create '[Part Deployment Link] to PC4' [PartDeploymentLink]
 *   - Create 'PC3: PC3' [Part]
 *     > Link 'PC3: PC3' to 'PC3' [abstractType]
 *   - Create 'PC4: PC4' [Part]
 *     > Link 'PC4: PC4' to 'PC4' [abstractType]
 *   - Create 'PC1' [PhysicalComponent]
 *     > Set 'PC1' to 'UNSET' [kind], 'NODE' [nature]
 *   - Create 'PC2' [PhysicalComponent]
 *     > Set 'PC2' to 'UNSET' [kind], 'NODE' [nature]
 *   - Create 'PC3' [PhysicalComponent]
 *     > Set 'PC3' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create '[Component Functional Allocation] to PF11' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to PF11' to 'PC3' [sourceElement], 'PF11' [targetElement]
 *   - Create 'PC4' [PhysicalComponent]
 *     > Set 'PC4' to 'UNSET' [kind], 'BEHAVIOR' [nature]
 *     - Create '[Component Functional Allocation] to PF12' [ComponentFunctionalAllocation]
 *       > Link '[Component Functional Allocation] to PF12' to 'PC4' [sourceElement], 'PF12' [targetElement]
 *   - Create '[Logical Component Realization] to Logical System' [LogicalComponentRealization]
 *     > Link '[Logical Component Realization] to Logical System' to 'PS' [sourceElement], 'Logical System' [targetElement]
 */
//@formatter:on

public class DataValuesTest extends System2SubsystemTest implements Interphase, Crossphase {

  private String _id_pc11 = "5e89ce85-ec92-4f47-92ed-d780ba00d3e1"; //$NON-NLS-1$
  private String _id_hexadecimal = "6cd87841-2313-4ba7-b8c3-218b3b3473e4"; //$NON-NLS-1$
  private String _id_min_ = "95fc1d74-b07d-45fa-bcaa-1262f0f831a0"; //$NON-NLS-1$
  private String _id_max_ = "bb686818-d4ba-4f0f-8184-c67c7b68c149"; //$NON-NLS-1$
  private String _id_nt13 = "a5a8b483-eb0d-40db-a4ed-8624e5683a30"; //$NON-NLS-1$
  private String _id_generalization_to_hexadecimal = "42cef0f1-dc98-4720-8f90-0d797f92206e"; //$NON-NLS-1$
  private String _id_nr131 = "8ccdfc5f-1e87-4f42-85ab-70610f230a94"; //$NON-NLS-1$
  private String _id_lnv132 = "bff6d6e5-63c0-4957-8aca-13561d494fc8"; //$NON-NLS-1$
  private String _id_ue133 = "21732957-19a7-4a6c-91f6-8f9390c323d8"; //$NON-NLS-1$
  private String _id_be134 = "6a7e9a1e-e860-45e6-9237-d25e66524108"; //$NON-NLS-1$
  private String _id_lsv135 = "09ee1806-c3cf-499c-8429-5c11dfb0233f"; //$NON-NLS-1$
  private String _id_cvr136 = "8777a323-8108-4add-ad6f-c008639497c9"; //$NON-NLS-1$
  private String _id_cv137 = "69093e26-e225-4128-887f-0e3880aa4bcb"; //$NON-NLS-1$
  private String _id_nt15 = "928919ae-73c2-4e8f-9f91-8af5723af526"; //$NON-NLS-1$
  private String _id_generalization_to_nt13 = "75a1ce70-5c7e-44e0-80b1-6435c2773d25"; //$NON-NLS-1$
  private String _id_lsv151 = "4edb1bb5-e4d2-472a-8aed-de8eb29cd2e5"; //$NON-NLS-1$

  private String _id_bt11 = "2797ded9-8fd7-4063-b6c3-165f5b840ffe"; //$NON-NLS-1$
  private String _id_bt17 = "78794098-a333-465f-9e52-e50e716a391c"; //$NON-NLS-1$
  private String _id_bt18 = "1c6553e1-87f9-46fd-8769-1296f1b89271"; //$NON-NLS-1$
  private String _id_bt19 = "d8758cc8-5322-49ef-9f2a-1215d2205a07"; //$NON-NLS-1$

  private String _id_generalization_to_bt18 = "06452b78-63b3-4ca4-a819-4354d3921de0"; //$NON-NLS-1$
  private String _id_lbv111 = "392cfbcc-0e2f-48b1-a993-884d303c0be0"; //$NON-NLS-1$
  private String _id_lbv171 = "4025701b-f8f8-452e-918d-24348c2ae3e2"; //$NON-NLS-1$
  private String _id_generalization_to_bt17 = "82975796-0947-4699-95e9-70a0a19c09f2"; //$NON-NLS-1$
  private String _id_lbv181 = "5ffc8b42-18c2-45ec-9779-d54b8eabfec8"; //$NON-NLS-1$
  private String _id_lbv182 = "071326a9-31b0-4e80-a808-0afa59a9162f"; //$NON-NLS-1$

  private String _id_c11 = "2aac66e1-7167-4d7d-8e46-3915be8e72c9"; //$NON-NLS-1$
  private String _id_s111 = "16508cf1-adba-4b5c-8f43-430fa0c9a646"; //$NON-NLS-1$
  private String _id_mincard_lnv111 = "d0fee987-b1c5-4b05-a23c-a08e7fc4018b"; //$NON-NLS-1$
  private String _id_maxcard_lnv111 = "63c5a2fb-7ed8-4d0e-8e89-5498924c61ac"; //$NON-NLS-1$
  private String _id_cv111 = "784e56ae-643f-4de3-969d-11832283cb98"; //$NON-NLS-1$
  private String _id_cvr112 = "260bd5b2-2907-4aab-8701-de8682dccd2e"; //$NON-NLS-1$
  private String _id_cv113 = "3e62e36a-a6ee-4b08-92a5-19a0fd13735c"; //$NON-NLS-1$
  private String _id_c12 = "56a5c76c-9541-44e5-921f-f01f10536b38"; //$NON-NLS-1$
  private String _id_mincard_lnv121 = "0cbb2771-ed46-4d49-9bc9-7185dd440b38"; //$NON-NLS-1$
  private String _id_maxcard_lnv121 = "34547b42-743b-4976-84a7-ec629ab5684f"; //$NON-NLS-1$
  private String _id_nr121 = "c7cf295e-69b6-48ed-82e1-9899541f4ddf"; //$NON-NLS-1$

  private String _id_u13 = "4061a972-6632-4cde-99d3-d3bcefbc2cdd"; //$NON-NLS-1$
  private String _id_up131 = "8117e6be-4793-4399-a559-c5686efe816d"; //$NON-NLS-1$
  private String _id_mincard_lnv1311 = "318e4817-0690-409c-8eab-37a6749746f3"; //$NON-NLS-1$
  private String _id_maxcard_lnv1311 = "576e2a8c-9546-404a-afcd-b61ef4c09f9b"; //$NON-NLS-1$
  private String _id_u11 = "2dfbac72-3d51-4418-9f21-f4795c2324fc"; //$NON-NLS-1$
  private String _id_e12 = "c87ad436-1254-4234-9172-480c5dc25ff7"; //$NON-NLS-1$
  private String _id_el121 = "a9c1942a-db06-4899-9ea7-0ff82ba894b4"; //$NON-NLS-1$
  private String _id_st14 = "3ea0dd4f-8c94-4f1b-bb10-67eb449f5b94"; //$NON-NLS-1$
  private String _id_be141 = "42ffa15a-e203-432d-8841-803eef0b059e"; //$NON-NLS-1$
  private String _id_default_lsv141 = "3e411a8d-fd89-4a82-b152-3e908d825aa4"; //$NON-NLS-1$
  private String _id_pq16_u11 = "ff5ed2cc-42cd-490d-9d34-9be2b1547d15"; //$NON-NLS-1$
  private String _id_default_nr161 = "a5f20704-71c3-4914-9083-ea81502f14f6"; //$NON-NLS-1$
  private String _id_null_nr161 = "5379fdc6-66c4-42a7-8d1e-a90442412678"; //$NON-NLS-1$
  private String _id_min_nr161 = "a65e492c-10d2-4f84-b7a7-2b6ff5cf92b2"; //$NON-NLS-1$
  private String _id_max_nr161 = "68895b8c-eae5-496a-8964-728e220eb61c"; //$NON-NLS-1$

  private String _id_a11 = "4473d465-63e4-4c35-a5dc-16eae2b6f46d"; //$NON-NLS-1$
  private String _id_p111_c11 = "7ace49bb-e601-403e-9641-1e40a1107652"; //$NON-NLS-1$
  private String _id_p111_mincard_lnv1111 = "b2f0615b-d999-415a-b303-8cbe6d93d2ed"; //$NON-NLS-1$
  private String _id_p111_maxcard_lnv1111 = "bf198f99-e228-48c0-b647-ce44cd27fc13"; //$NON-NLS-1$
  private String _id_class_c11 = "6fd4cbbf-3141-4c0a-9bf6-6a1cf82f7b18"; //$NON-NLS-1$
  private String _id_p111 = "aa018584-ea95-434d-8542-ce3a8db65cbb"; //$NON-NLS-1$
  private String _id_mincard_lnv1111 = "374c1fd8-4bb7-42bd-a2b5-1b899843721f"; //$NON-NLS-1$
  private String _id_maxcard_lnv1111 = "f005d8b0-7d33-4867-8093-7029e830c171"; //$NON-NLS-1$
  private String _id_class_c12 = "fa327be4-40e8-4347-9636-ae8822cfd223"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(_id_pc11);
  }

  @Override
  protected void verify() {

    mustBeTransitioned(_id_pc11);

    // NumericValue
    mustBeTransitioned(_id_hexadecimal);
    mustBeTransitioned(_id_nt13);
    mustBeTransitioned(_id_nt15);
    mustBeTransitioned(_id_generalization_to_nt13);
    mustBeTransitioned(_id_lsv151);
    mustBeTransitioned(_id_generalization_to_hexadecimal);
    mustBeTransitioned(_id_nr131);
    mustBeTransitioned(_id_lnv132);
    mustBeTransitioned(_id_ue133);
    mustBeTransitioned(_id_be134);
    mustBeTransitioned(_id_lsv135);
    mustBeTransitioned(_id_cvr136);
    mustBeTransitioned(_id_cv137);
    mustBeTransitioned(_id_min_);
    mustBeTransitioned(_id_max_);

    // BooleanType
    mustBeTransitioned(_id_bt11);
    mustBeTransitioned(_id_bt17);
    mustBeTransitioned(_id_bt18);
    shouldNotBeTransitioned(_id_bt19);

    mustBeTransitioned(_id_generalization_to_bt18);
    mustBeTransitioned(_id_lbv111);
    mustBeTransitioned(_id_lbv171);
    mustBeTransitioned(_id_generalization_to_bt17);
    mustBeTransitioned(_id_lbv181);
    mustBeTransitioned(_id_lbv182);

    // CollectionType
    mustBeTransitioned(_id_c11);
    mustBeTransitioned(_id_s111);
    mustBeTransitioned(_id_mincard_lnv111);
    mustBeTransitioned(_id_maxcard_lnv111);
    mustBeTransitioned(_id_cv111);
    mustBeTransitioned(_id_cvr112);
    mustBeTransitioned(_id_cv113);
    mustBeTransitioned(_id_c12);
    mustBeTransitioned(_id_mincard_lnv121);
    mustBeTransitioned(_id_maxcard_lnv121);
    mustBeTransitioned(_id_nr121);

    // Union
    mustBeTransitioned(_id_u13);
    mustBeTransitioned(_id_up131);
    mustBeTransitioned(_id_mincard_lnv1311);
    mustBeTransitioned(_id_maxcard_lnv1311);

    // PhysicalQuantity
    mustBeTransitioned(_id_pq16_u11);
    mustBeTransitioned(_id_default_nr161);
    mustBeTransitioned(_id_null_nr161);
    mustBeTransitioned(_id_min_nr161);
    mustBeTransitioned(_id_max_nr161);
    mustBeTransitioned(_id_u11);

    // StringType
    mustBeTransitioned(_id_st14);
    mustBeTransitioned(_id_be141);
    mustBeTransitioned(_id_default_lsv141);

    // Enum
    mustBeTransitioned(_id_e12);
    mustBeTransitioned(_id_el121);

    mustBeTransitioned(_id_a11);
    mustBeTransitioned(_id_p111_c11);
    mustBeTransitioned(_id_p111_mincard_lnv1111);
    mustBeTransitioned(_id_p111_maxcard_lnv1111);
    mustBeTransitioned(_id_class_c11);
    mustBeTransitioned(_id_p111);
    mustBeTransitioned(_id_mincard_lnv1111);
    mustBeTransitioned(_id_maxcard_lnv1111);
    mustBeTransitioned(_id_class_c12);
  }

  static class Crossphase extends System2SubsystemTest {

    public Crossphase() {
      setKind(Kind.CROSS_PHASES);
    }

    private String _id_pc11 = "5e89ce85-ec92-4f47-92ed-d780ba00d3e1"; //$NON-NLS-1$
    private String _id_hexadecimal = "6cd87841-2313-4ba7-b8c3-218b3b3473e4"; //$NON-NLS-1$

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {
      mustBeTransitionedInto(_id_hexadecimal, CtxPackage.Literals.SYSTEM_ANALYSIS);
    }

  }

  public static class Interphase extends System2SubsystemTest {

    public Interphase() {
      setKind(Kind.INTER_PHASES);
    }

    private String _id_pc11 = "5e89ce85-ec92-4f47-92ed-d780ba00d3e1"; //$NON-NLS-1$
    private String _id_hexadecimal = "6cd87841-2313-4ba7-b8c3-218b3b3473e4"; //$NON-NLS-1$

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {
      mustBeTransitionedInto(_id_hexadecimal, CtxPackage.Literals.SYSTEM_ANALYSIS);
    }

  }

}
