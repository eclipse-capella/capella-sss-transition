/*******************************************************************************
 * Copyright (c) 2016, 2018 THALES GLOBAL SERVICES.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.session.Session;
import org.junit.Assert;
import org.polarsys.capella.common.data.modellingcore.ModelElement;
import org.polarsys.capella.common.flexibility.properties.loader.PropertiesLoader;
import org.polarsys.capella.common.flexibility.properties.property.PropertyContext;
import org.polarsys.capella.common.flexibility.properties.schema.IProperties;
import org.polarsys.capella.common.flexibility.properties.schema.IPropertyContext;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.ctx.SystemComponent;
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.context.TransitionContext;
import org.polarsys.capella.test.framework.api.BasicTestCase;
import org.polarsys.capella.test.framework.context.SessionContext;
import org.polarsys.capella.transition.system2subsystem.constants.IOptionsConstants;
import org.polarsys.capella.transition.system2subsystem.preferences.PropertyValuesPreference;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;
import org.polarsys.capella.transition.system2subsystem.tests.TraceabilityArchitectureSID;
import org.polarsys.capella.transition.system2subsystem.tests.multiphases.MultiPhasesTest;
import org.polarsys.kitalpha.cadence.core.api.parameter.GenericParameter;

//@formatter:off
/**
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

public class PropertyValuesTest {

  public static final String RPVP = "8aa94cf2-1f69-4ce1-adf6-3722b58ecef7"; //$NON-NLS-1$
  public static final String RPVP__BPV1 = "656df886-b164-48d2-b829-10c97c2a7425"; //$NON-NLS-1$
  public static final String COPY_OF_RPVP = "b165051e-e5ff-49b1-873e-bbb249e0a8e2"; //$NON-NLS-1$
  public static final String COPY_OF_RPVP__BPV1 = "c4226366-056b-4dba-8098-2c97fef45416"; //$NON-NLS-1$
  public static final String OA__OPVP = "f5c2cd05-edee-4751-a620-a6bc2b9d1ea4"; //$NON-NLS-1$
  public static final String OA__OPVP__BPV1 = "107053e9-1bc5-4955-886b-3e06eb3dec37"; //$NON-NLS-1$
  public static final String OA__OPVP2 = "a529f56b-ed3e-4407-8a71-4176d7a69bb1"; //$NON-NLS-1$
  public static final String OA__OPVP2__BPV1 = "cd94bb0d-fcd3-4230-8124-e5f9d75d5509"; //$NON-NLS-1$
  public static final String SA__SPVP = "54cacae4-7c7f-4ac7-9df2-96fa913e1ef6"; //$NON-NLS-1$
  public static final String SA__SPVP__BPV1 = "1228fb98-7660-4384-aa93-36c1289f51f9"; //$NON-NLS-1$
  public static final String SA__SPVP2 = "f7c7fbb9-5fae-495d-b7b4-dc553af6466f"; //$NON-NLS-1$
  public static final String SA__SPVP2__BPV1 = "744f10e9-48eb-4bb5-a151-5bcdd4fee4eb"; //$NON-NLS-1$
  public static final String LA__LPVP = "b305c3b5-18af-42b8-819c-a97f6880b487"; //$NON-NLS-1$
  public static final String LA__LPVP__BPV1 = "a1205b44-7545-428e-8e60-acbca3ebc1e6"; //$NON-NLS-1$
  public static final String LA__LPVP2 = "4bcde41c-0254-4180-b737-ea5698054e88"; //$NON-NLS-1$
  public static final String LA__LPVP2__BPV1 = "f9614b1f-847f-4f23-ac41-1b0a898d18a3"; //$NON-NLS-1$
  public static final String LA__INTERFACES__E1 = "c261d2aa-c641-428a-bc5b-6ea70471db88"; //$NON-NLS-1$
  public static final String PC_11__PV1 = "f4b40f3e-b15c-4397-9997-3d1a743702c4"; //$NON-NLS-1$
  public static final String PVG_11 = "82178d7e-72ad-4207-8074-091626958f95"; //$NON-NLS-1$
  public static final String PVG_12 = "ea213073-de56-4447-9d30-bba2ff814798"; //$NON-NLS-1$
  public static final String EPV_2111 = "bec77078-66bb-40f8-af61-7b7fe9c2d4c1"; //$NON-NLS-1$
  public static final String IPV_2112 = "e662ea2e-5e62-4d03-919c-6cfb5a27657e"; //$NON-NLS-1$
  public static final String IPV_2113 = "1fc7da80-1ec9-4706-8776-a81b7e0ce21a"; //$NON-NLS-1$
  public static final String PVG_11111 = "0cdae9cf-cc39-4d9a-935b-16481711e92c"; //$NON-NLS-1$
  public static final String PVG_11112 = "b337cf76-9484-4c48-a4e1-4ace00ac7d92"; //$NON-NLS-1$
  public static final String PVG_11113 = "c89d3099-c662-463e-9a7b-dceae1444b5d"; //$NON-NLS-1$

  /**
   * PropertyValues transition: Test that property values are correctly imported (according to scope)
   */
  public static class Test1 extends System2SubsystemTest implements Interphase, Crossphase {

    private final String _id_pvp1 = "2958d369-0525-4bb0-95e3-e5a5fc0bc1c0"; //$NON-NLS-1$
    private final String _id_bpv11 = "9da356c0-e2cc-4853-8f71-f3a64cce7dca"; //$NON-NLS-1$
    private final String _id_epv111 = "3a8170cb-0d8d-4824-bbce-8ba87944b1fb"; //$NON-NLS-1$
    private final String _id_ipv112 = "c034ba71-0a35-4ebc-8626-6dbfad6506d1"; //$NON-NLS-1$
    private final String _id_ipv113 = "35ed0e48-5ab2-456e-810b-cb8f4fe70333"; //$NON-NLS-1$
    private final String _id_epv121 = "9dbb0488-38bb-4907-90a2-d18bcbf7a8af"; //$NON-NLS-1$
    private final String _id_ipv122 = "240fcadd-0172-4902-8460-fd5ec3166318"; //$NON-NLS-1$
    private final String _id_ipv123 = "4b53fef8-9f81-4dad-b2a4-ddd33d270185"; //$NON-NLS-1$
    private final String _id_pvp2 = "a4e563c0-361b-4768-b6e3-62befc9fb8de"; //$NON-NLS-1$
    private final String _id_ipv21 = "9e1631ab-94f2-4794-9f3f-9157597eb63e"; //$NON-NLS-1$
    private final String _id_pvg21 = "fe1093f0-dad1-462a-bf7f-024b44833a50"; //$NON-NLS-1$
    private final String _id_epv211 = "7fbd8ded-7a97-4d17-be31-a85d8000a911"; //$NON-NLS-1$
    private final String _id_pvp21 = "50936eab-96fd-41e8-8b3e-1d2319f0d471"; //$NON-NLS-1$
    private final String _id_bpv211 = "14152666-9450-491d-9cd1-f9cb97ea55b2"; //$NON-NLS-1$
    private final String _id_pvg211 = "4045cb96-744e-4046-9665-5614999f419d"; //$NON-NLS-1$
    private final String _id_pvg212 = "a6f4eee6-77e8-4e93-a140-54767e14428f"; //$NON-NLS-1$
    private final String _id_epv2121 = "3441d4aa-e7cb-4768-b981-ee01f9fdc0c3"; //$NON-NLS-1$
    private final String _id_fpv2122 = "40bddd17-caed-49cd-a96d-4936b7a6776e"; //$NON-NLS-1$
    private final String _id_pvg2121 = "ae03f42c-6668-4a8f-97b4-209c39ef73a3"; //$NON-NLS-1$
    private final String _id_bpv21211 = "694e22f7-0da4-446c-a92e-097c066ba9ff"; //$NON-NLS-1$
    private final String _id_bpv21212 = "ebdffc78-9728-431d-8f1a-d17488253e3c"; //$NON-NLS-1$
    private final String _id_pf11 = "b1a57082-48d9-498e-9726-02b0842687d8"; //$NON-NLS-1$
    private final String _id_pf111 = "ce53ac7d-ab7b-4bfe-a3ff-db7e897e7412"; //$NON-NLS-1$
    private final String _id_pc1 = "c1bb49ad-1259-471d-a992-3144670dff62"; //$NON-NLS-1$
    private final String _id_pc11 = "30ed9216-9b63-4120-88bd-9f1234c7897f"; //$NON-NLS-1$
    private final String _id_bpv111 = "087c57b8-39cd-483d-92d4-cd4fc0b4768f"; //$NON-NLS-1$
    private final String _id_pvg111 = "21272212-a655-4a48-bf94-0310f39c2bd9"; //$NON-NLS-1$
    private final String _id_bpv1111 = "5cd769b9-0495-4963-9950-71d93d39eded"; //$NON-NLS-1$
    private final String _id_bpv1112 = "e40519bd-c5a3-4275-90e1-3bdbb7a4fd12"; //$NON-NLS-1$
    private final String _id_pvg1111 = "6ade04ca-26df-449e-9916-615189809d7c"; //$NON-NLS-1$
    private final String _id_bpv11111 = "aa063629-77b5-43eb-915a-fad93968c1c8"; //$NON-NLS-1$
    private final String _id_bpv11112 = "edff2b9c-5b14-4cb1-b1ba-c201c48b4d2d"; //$NON-NLS-1$
    private final String _id_bpv111111 = "bca5f08a-801c-4048-9d60-9039022e1140"; //$NON-NLS-1$
    private final String _id_bpv111112 = "51441522-28c1-473b-b9d4-26a505701a31"; //$NON-NLS-1$
    private final String _id_pvg111111 = "3d879f67-0014-45e6-a4ea-0774b057d4ea"; //$NON-NLS-1$
    private final String _id_bpv1111111 = "68e84860-26fd-4f0a-8011-6faac9386bca"; //$NON-NLS-1$
    private final String _id_bpv1111112 = "144fc667-f218-44a8-ae20-d41a86913d89"; //$NON-NLS-1$
    private final String _id_bpv111121 = "bbccc670-5281-4f4c-8630-1b94c8feb7c7"; //$NON-NLS-1$
    private final String _id_bpv111122 = "a6b8f9b1-8714-4cf7-885e-9b8044800879"; //$NON-NLS-1$
    private final String _id_pvg111121 = "deb2c2d4-f7c1-4561-9d19-c3287909cf95"; //$NON-NLS-1$
    private final String _id_bpv1111211 = "0101ee0d-52d6-4c71-9fd3-0ea846295c57"; //$NON-NLS-1$
    private final String _id_bpv1111212 = "738079d0-fda3-47cb-b3a1-3dcad6620c46"; //$NON-NLS-1$
    private final String _id_bpv111131 = "dd2ea859-f55c-409f-b534-7d807c835950"; //$NON-NLS-1$
    private final String _id_bpv111132 = "908dd3e6-fd8c-4040-9262-6c462d180d7a"; //$NON-NLS-1$
    private final String _id_pvg111131 = "9f123f40-ea1d-46a1-87b3-6af021891ba0"; //$NON-NLS-1$
    private final String _id_bpv1111311 = "c144f839-af93-404c-a089-c73f5f58702b"; //$NON-NLS-1$
    private final String _id_bpv1111312 = "62fa84ee-d617-4ae9-83bb-fc4c007feb4a"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      Collection<EObject> collection = new ArrayList<EObject>();
      collection.add(getObject(_id_pvp21));

      collection.add(getObject(_id_bpv111));// "087c57b8-39cd-483d-92d4-cd4fc0b4768f";
      collection.add(getObject(_id_pvg111));// "21272212-a655-4a48-bf94-0310f39c2bd9";
      collection.add(getObject(_id_bpv1111));// "5cd769b9-0495-4963-9950-71d93d39eded";
      collection.add(getObject(_id_bpv1112));// "e40519bd-c5a3-4275-90e1-3bdbb7a4fd12";
      collection.add(getObject(_id_pvg1111));// "6ade04ca-26df-449e-9916-615189809d7c";
      collection.add(getObject(_id_bpv11111));// "aa063629-77b5-43eb-915a-fad93968c1c8";
      collection.add(getObject(_id_bpv11112));// "edff2b9c-5b14-4cb1-b1ba-c201c48b4d2d";
      collection.add(getObject(PVG_11111));// "0cdae9cf-cc39-4d9a-935b-16481711e92c";
      collection.add(getObject(_id_bpv111111));// "bca5f08a-801c-4048-9d60-9039022e1140";
      collection.add(getObject(_id_bpv111112));// "51441522-28c1-473b-b9d4-26a505701a31";
      collection.add(getObject(_id_pvg111111));// "3d879f67-0014-45e6-a4ea-0774b057d4ea";
      collection.add(getObject(_id_bpv1111111));// "68e84860-26fd-4f0a-8011-6faac9386bca";
      collection.add(getObject(_id_bpv1111112));// "144fc667-f218-44a8-ae20-d41a86913d89";
      collection.add(getObject(PVG_11112));// "b337cf76-9484-4c48-a4e1-4ace00ac7d92";
      collection.add(getObject(_id_bpv111121));// "bbccc670-5281-4f4c-8630-1b94c8feb7c7";
      collection.add(getObject(_id_bpv111122));// "a6b8f9b1-8714-4cf7-885e-9b8044800879";
      collection.add(getObject(_id_pvg111121));// "deb2c2d4-f7c1-4561-9d19-c3287909cf95";
      collection.add(getObject(_id_bpv1111211));// "0101ee0d-52d6-4c71-9fd3-0ea846295c57";
      collection.add(getObject(_id_bpv1111212));// "738079d0-fda3-47cb-b3a1-3dcad6620c46";
      collection.add(getObject(PVG_11113));// "c89d3099-c662-463e-9a7b-dceae1444b5d";
      collection.add(getObject(_id_bpv111131));// "dd2ea859-f55c-409f-b534-7d807c835950";
      collection.add(getObject(_id_bpv111132));// "908dd3e6-fd8c-4040-9262-6c462d180d7a";
      collection.add(getObject(_id_pvg111131));// "9f123f40-ea1d-46a1-87b3-6af021891ba0";
      collection.add(getObject(_id_bpv1111311));// "c144f839-af93-404c-a089-c73f5f58702b";
      collection.add(getObject(_id_bpv1111312));// "62fa84ee-d617-4ae9-83bb-fc4c007feb4a";
      collection.add(getObject(SA__SPVP2__BPV1));

      addSharedParameter(IOptionsConstants.PROPERTY_VALUES_ELEMENTS, collection);
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {
      // Check that no Sys analysis was added during transition
      assertTrue(
          SystemEngineeringExt.getSystemEngineering(retrieveTargetSystem()).getContainedSystemAnalysis().size() == 1);

      mustBeTransitioned(_id_pc11);
      mustBeTransitioned(_id_pf111);

      // PV from SA level should be transitioned
      mustBeTransitioned(SA__SPVP2__BPV1);

      // All properties not included into selected elements should not be transitioned
      shouldNotBeTransitioned(_id_bpv11);
      shouldNotBeTransitioned(PVG_11);
      shouldNotBeTransitioned(_id_epv111);
      shouldNotBeTransitioned(_id_ipv112);
      shouldNotBeTransitioned(_id_ipv113);
      shouldNotBeTransitioned(PVG_12);
      shouldNotBeTransitioned(_id_epv121);
      shouldNotBeTransitioned(_id_ipv122);
      shouldNotBeTransitioned(_id_ipv123);
      shouldNotBeTransitioned(_id_ipv21);
      shouldNotBeTransitioned(_id_pvg21);
      shouldNotBeTransitioned(_id_epv211);

      // All properties included into selected elements should not be transitioned
      mustBeTransitioned(_id_bpv211);
      mustBeTransitioned(_id_pvg212);
      mustBeTransitioned(_id_epv2121);
      mustBeTransitioned(_id_fpv2122);
      mustBeTransitioned(_id_pvg2121);
      mustBeTransitioned(_id_bpv21211);
      mustBeTransitioned(_id_bpv21212);

      // Parent package is not selected
      shouldNotBeTransitioned(_id_pvp2);
      // Package is selected
      mustBeTransitioned(_id_pvp21);

      mustBeTransitioned(IPV_2112);
      // Not sure
      mustBeTransitioned(EPV_2111);
      mustBeTransitioned(IPV_2113);

      mustBeTransitioned(_id_pvg111);
      mustBeTransitioned(_id_bpv1111);
      mustBeTransitioned(_id_bpv1112);
      mustBeTransitioned(_id_pvg1111);
      mustBeTransitioned(_id_bpv11111);
      mustBeTransitioned(_id_bpv11112);
      mustBeTransitioned(PVG_11111);
      mustBeTransitioned(_id_bpv111111);
      mustBeTransitioned(_id_bpv111112);
      mustBeTransitioned(_id_pvg111111);
      mustBeTransitioned(_id_bpv1111111);
      mustBeTransitioned(_id_bpv1111112);

      mustBeTransitioned(_id_bpv111122);
      mustBeTransitioned(PVG_11112);
      mustBeTransitioned(_id_bpv111121);
      mustBeTransitioned(_id_pvg111121);
      mustBeTransitioned(_id_bpv1111211);
      mustBeTransitioned(_id_bpv1111212);

      mustBeTransitioned(_id_bpv1111312);
      mustBeTransitioned(_id_pvg111131);
      mustBeTransitioned(_id_bpv1111311);
      shouldNotBeTransitioned(PVG_11113);
      shouldNotBeTransitioned(_id_bpv111131);
      shouldNotBeTransitioned(_id_bpv111132);

    }

  }

  /**
   * PropertyValues transition: Test that property values are correctly imported (according to scope)
   * 
   * This test doesn't cover a usecase doable directly by the user since only PropertyValuePkg from SystemEngineering and
   * current architecture are selectable. (but it may be covering the usecase where an extension of the addon is
   * contributing another property values to the scope)
   * 
   * To be able to choice OA/SA/LA pv in UI, in org.polarsys.capella.transition.system2subsystem.preferences.getChoiceValues, add also a visit on
   * SystemEnginerring.ownedArchitectures to be able to test it manually.
   */
  public static class Test2 extends System2SubsystemTest implements Interphase, Crossphase {

    private String _id_pc11 = "30ed9216-9b63-4120-88bd-9f1234c7897f"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      Collection<EObject> collection = new ArrayList<EObject>();
      collection.add(getObject(RPVP));
      collection.add(getObject(OA__OPVP));
      collection.add(getObject(SA__SPVP));
      collection.add(getObject(LA__LPVP));

      getHeadlessParameters().addSharedParameter(new GenericParameter<Collection<EObject>>(
          IOptionsConstants.PROPERTY_VALUES_ELEMENTS, collection, IOptionsConstants.PROPERTY_VALUES_ELEMENTS));
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {

      mustBeTransitioned(_id_pc11);

      // All properties not included into selected elements should not be transitioned
      // All properties included into selected elements must be transitioned

      // SystemEngineering elements
      mustBeTransitioned(RPVP);
      mustBeTransitioned(RPVP__BPV1);
      shouldNotBeTransitioned(COPY_OF_RPVP);
      shouldNotBeTransitioned(COPY_OF_RPVP__BPV1);
      mustBeTransitionedAndLinkedTo(LA__INTERFACES__E1, RPVP__BPV1,
          CapellacorePackage.Literals.CAPELLA_ELEMENT__APPLIED_PROPERTY_VALUES);

      // OA elements
      mustBeTransitioned(OA__OPVP);
      mustBeTransitioned(OA__OPVP__BPV1);
      mustBeTransitionedAndLinkedTo(LA__INTERFACES__E1, OA__OPVP__BPV1,
          CapellacorePackage.Literals.CAPELLA_ELEMENT__APPLIED_PROPERTY_VALUES);


      shouldNotBeTransitioned(OA__OPVP2);
      shouldNotBeTransitioned(OA__OPVP2__BPV1);

      // SA elements
      mustBeTransitioned(SA__SPVP);
      mustBeTransitioned(SA__SPVP__BPV1);
      shouldNotBeTransitioned(SA__SPVP2);
      shouldNotBeTransitioned(SA__SPVP2__BPV1);

      // LA elements
      mustBeTransitioned(LA__LPVP);
      mustBeTransitioned(LA__LPVP__BPV1);
      shouldNotBeTransitioned(LA__LPVP2);
      shouldNotBeTransitioned(LA__LPVP2__BPV1);

      // Package is selected
      mustBeTransitioned(LA__INTERFACES__E1);
      mustBeTransitionedAndLinkedTo(LA__INTERFACES__E1, SA__SPVP__BPV1,
          CapellacorePackage.Literals.CAPELLA_ELEMENT__APPLIED_PROPERTY_VALUES);
      mustBeTransitionedAndLinkedTo(LA__INTERFACES__E1, LA__LPVP__BPV1,
          CapellacorePackage.Literals.CAPELLA_ELEMENT__APPLIED_PROPERTY_VALUES);

    }
  }
  
  /**
   * 
   *
   */
  public static class Test3 extends System2SubsystemTest implements Interphase, Crossphase {

    private String _id_pc11 = "30ed9216-9b63-4120-88bd-9f1234c7897f"; //$NON-NLS-1$

    @Override
    public void setUp() throws Exception {
      super.setUp();
      Collection<EObject> collection = new ArrayList<EObject>();
      collection.add(getObject(PC_11__PV1));

      getHeadlessParameters().addSharedParameter(new GenericParameter<Collection<EObject>>(
          IOptionsConstants.PROPERTY_VALUES_ELEMENTS, collection, IOptionsConstants.PROPERTY_VALUES_ELEMENTS));
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(_id_pc11);
    }

    @Override
    protected void verify() {
      mustBeTransitioned(_id_pc11);
      mustBeTransitioned(PC_11__PV1);
      EObject transformedPC11 = retrieveTargetElement(_id_pc11);
      assertTrue(transformedPC11 instanceof Component);
      Part part = ((Component) transformedPC11).getRepresentingParts().get(0);
      assertTrue(part != null);
      assertTrue(transformedPC11.eContainer() == part.eContainer());
    }
  }

  /*
   * Test if the proper amount of property values (at the element's or above architecture level, as well as system
   * engineering) is being offered as choice in the transition ui, by using
   * org.polarsys.capella.transition.system2subsystem.preferences.PropertyValuesPreference.getChoiceValues.
   */
  public static class Test4 extends BasicTestCase {

    private static final String PC_1 = "9a05fc5a-09c4-4d93-98a6-7d127e0b93bf";
    private static final String LC_1 = "c25b25b1-fcd3-4da8-a9ea-c1da76789438";
    private static final List<String> PROPERTY_VALUES_LC = Arrays.asList("6a231c1a-fa3f-4197-890c-a0d8ad80aedd",
        "391bf2a5-e244-4550-bbb5-01d09af23639", "39fbf71a-d3e7-40c0-9063-a4483ed56df2",
        "1b5ee26a-0f73-4b0f-b33b-02843e7dd53f", "42d778cb-3fa0-4ada-927b-c4ce5666bc52",
        "6880be2a-b093-4df2-86eb-722adee4cc1f");
    private static final List<String> PROPERTY_VALUES_PC = new ArrayList<>(PROPERTY_VALUES_LC);
    private IPropertyContext propertyContext;
    private PropertyValuesPreference pvPreference;
    private SessionContext context;

    static {
      PROPERTY_VALUES_PC
          .addAll(Arrays.asList("22659098-4f4d-499f-8586-9f0497534353", "7467b8e2-0ca0-4443-951d-7e145ad7d814",
              "32235a8c-94b4-4415-8a88-44a0cc067e92", "356f8d58-956c-4afd-8424-797c897b6685"));
    }

    @Override
    public void setUp() throws Exception {
      super.setUp();
      IProperties properties = new PropertiesLoader().getProperties(IOptionsConstants.SYSTEM2SUBSYSTEM_PREFERENCES);
      propertyContext = new PropertyContext(properties);
      pvPreference = new PropertyValuesPreference();
      Session session = getSessionForTestModel(getRequiredTestModels().get(0));
      context = new SessionContext(session);
    }

    private void checkIfAllValidPVsAreProvided(List<String> actualPropertyValues, Collection<EObject> selection) {
      TransitionContext transitionContext = new TransitionContext();
      transitionContext.put(ITransitionConstants.TRANSITION_SOURCES, selection);
      propertyContext.setSource(transitionContext);
      Collection<Object> providedPvs = pvPreference.getChoiceValues(propertyContext);
      Assert.assertEquals(actualPropertyValues.size(), providedPvs.size());
      providedPvs.stream().forEach(pv -> assertTrue(actualPropertyValues.contains(((ModelElement) pv).getId())));
    }

    @Override
    public void test() throws Exception {
      checkIfAllValidPVsAreProvided(PROPERTY_VALUES_PC, getObjects(PC_1));
      checkIfAllValidPVsAreProvided(PROPERTY_VALUES_LC, getObjects(LC_1));
      checkIfAllValidPVsAreProvided(PROPERTY_VALUES_PC, getObjects(PC_1, LC_1));
    }

    @Override
    public List<String> getRequiredTestModels() {
      return Arrays.asList("testPVPreference");
    }

    protected <T extends EObject> T getObject(String id) {
      return (T) context.getSemanticElement(id);
    }

    protected Collection<EObject> getObjects(String... idsp) {
      Collection<EObject> objects = new ArrayList<>();
      for (String id : idsp) {
        objects.add(getObject(id));
      }
      return objects;
    }
  }

  public static class Test5 extends MultiPhasesTest {

    private static final String PC_15 = "a674da5d-4edd-480b-a93a-048e0d7eea41";
    private static final String OA_PV_NESTED = "9c9b6706-150a-4aa1-9785-210eb9eb5afc";
    private List<PropertyValueGroup> propertyValueGroups = new ArrayList<>();
    private List<AbstractPropertyValue> propertyValues = new ArrayList<>();

    @Override
    public void setUp() throws Exception {
      super.setUp();
      Collection<EObject> collection = new ArrayList<EObject>();
      collection.add(getObject(PVG_11111));
      collection.add(getObject(PVG_11112));
      collection.add(getObject(PVG_11));
      collection.add(getObject(PVG_12));
      collection.add(getObject(EPV_2111));
      collection.add(getObject(IPV_2113));
      collection.add(getObject(IPV_2112));
      collection.add(getObject(OA_PV_NESTED));
      collection.add(getObject(COPY_OF_RPVP));
      
      // in a practical scenario only the package containing the pv's can be selected
      collection.add(getObject(LA__LPVP));

      getHeadlessParameters().addSharedParameter(new GenericParameter<Collection<EObject>>(
          IOptionsConstants.PROPERTY_VALUES_ELEMENTS, collection, IOptionsConstants.PROPERTY_VALUES_ELEMENTS));
    }

    @Override
    protected void verify() throws Exception {
      super.verify();
      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.SA);
      SystemComponent systemComponent = (SystemComponent) mustBeTransitioned(PC_15, ComponentType.SYSTEM);

      // selected during transition, but not applied to component
      shouldNotBeTransitioned(PVG_11112);
      // applied to component, but not selected during transition
      shouldNotBeTransitioned(PVG_11113);
      shouldNotBeTransitioned(LA__LPVP2__BPV1);

      propertyValueGroups.add((PropertyValueGroup) mustBeTransitionedInto(PVG_11111, ComponentType.SYSTEM_ANALYSIS));
      propertyValueGroups.add((PropertyValueGroup) mustBeTransitionedInto(PVG_11, ComponentType.SYSTEM_ANALYSIS));
      propertyValueGroups.add((PropertyValueGroup) mustBeTransitionedInto(PVG_12, ComponentType.SYSTEM_ANALYSIS));
      propertyValues.add((AbstractPropertyValue) mustBeTransitionedInto(EPV_2111, ComponentType.SYSTEM_ANALYSIS));
      propertyValues.add((AbstractPropertyValue) mustBeTransitionedInto(IPV_2112, ComponentType.SYSTEM_ANALYSIS));
      propertyValues.add((AbstractPropertyValue) mustBeTransitionedInto(IPV_2113, ComponentType.SYSTEM_ANALYSIS));
      propertyValues.add((AbstractPropertyValue) mustBeTransitionedInto(LA__LPVP__BPV1, ComponentType.SYSTEM_ANALYSIS));
      // pv deeply nested within oa data pkg, should be transitioned into sys analysis root
      propertyValues.add((AbstractPropertyValue) mustBeTransitionedInto(OA_PV_NESTED, ComponentType.SYSTEM_ANALYSIS));

      ((TraceabilityArchitectureSID) traceability).setArchitecture(null);
      propertyValues.add((AbstractPropertyValue) mustBeTransitioned(COPY_OF_RPVP__BPV1));
      
      checkIfPvsAreApplied(systemComponent);
      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.LA);
      LogicalComponent logicalComponent = (LogicalComponent) mustBeTransitioned(PC_15, ComponentType.LOGICAL_COMPONENT);

      shouldNotBeTransitionedInto(PVG_11111, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(PVG_11, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(PVG_12, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(EPV_2111, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(IPV_2112, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(IPV_2113, ComponentType.LOGICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(LA__LPVP__BPV1, ComponentType.LOGICAL_ARCHITECTURE);

      checkIfPvsAreApplied(logicalComponent);
      ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);
      PhysicalComponent physicalComponent = (PhysicalComponent) mustBeTransitioned(PC_15);

      shouldNotBeTransitionedInto(PVG_11111, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(PVG_11, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(PVG_12, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(EPV_2111, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(IPV_2112, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(IPV_2113, ComponentType.PHYSICAL_ARCHITECTURE);
      shouldNotBeTransitionedInto(LA__LPVP__BPV1, ComponentType.PHYSICAL_ARCHITECTURE);

      checkIfPvsAreApplied(physicalComponent);
    }

    private void checkIfPvsAreApplied(CapellaElement component) {
      assertEquals(propertyValueGroups.size(), component.getAppliedPropertyValueGroups().size());
      assertEquals(propertyValues.size(), component.getAppliedPropertyValues().size());
      
      assertTrue((new HashSet<AbstractPropertyValue>(propertyValues))
          .equals(new HashSet<AbstractPropertyValue>(component.getAppliedPropertyValues())));
      assertTrue((new HashSet<PropertyValueGroup>(propertyValueGroups))
          .equals(new HashSet<PropertyValueGroup>(component.getAppliedPropertyValueGroups())));
    }

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(PC_15);
    }

  }
}
