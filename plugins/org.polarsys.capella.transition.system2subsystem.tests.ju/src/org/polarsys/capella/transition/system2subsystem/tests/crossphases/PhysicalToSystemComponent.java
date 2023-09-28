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
package org.polarsys.capella.transition.system2subsystem.tests.crossphases;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.polarsys.capella.common.data.modellingcore.ModelElement;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.ComponentPkg;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.ctx.CtxPackage;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.data.ctx.SystemComponentPkg;
import org.polarsys.capella.core.model.handler.command.CapellaResourceHelper;
import org.polarsys.capella.core.model.helpers.ProjectExt;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;

public class PhysicalToSystemComponent {

  public static final String PC1 = "581d4e58-c3fb-40ac-8c27-b0b31bf63431";
  public static final String PC11 = "461435b7-5c4c-4080-92d6-89d8401252ab";
  public static final String PC2 = "a5c9fb66-c6ec-4213-b93c-c52d6f9da50f"; //$NON-NLS-1$
  public static final String A = "d88b8928-bb26-4047-b071-b5a4aa6edace"; //$NON-NLS-1$
  public static final String B = "8bc10921-c766-42ea-9455-ccb597a4ca88"; //$NON-NLS-1$
  public static final String PARENT = "5a2e2eb1-9780-4d6d-a25a-0f58bad29a11"; //$NON-NLS-1$
  public static final String PART_A = "7d8ab67a-99d2-4646-aab9-842ae69e1d96"; //$NON-NLS-1$
  public static final String PART_B = "8a2199a5-9b2c-4dd4-a95d-a586496c3e81"; //$NON-NLS-1$
  public static final String PART_PARENT = "730d1e95-531f-4966-9f82-d208c7951c14"; //$NON-NLS-1$


  /**
   * PC to System: Test if a PC is correctly transitionned to a System
   */
  public static class Test1 extends CrossPhasesTest {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(PC11);
    }

    @Override
    protected void verify() {

      retrieveReferenceElement(PC11);
      testInstanceOf(retrieveTargetElement(PC11), ComponentType.SYSTEM);

      retrieveReferenceElement(PC2);
      testInstanceOf(retrieveTargetElement(PC2), ComponentType.ACTOR);
    }
    
  }

  /**
   * System name: Test if transitioned System has same name than Reference component
   */
  public static class Test2 extends CrossPhasesTest {

    @Override
    protected Collection<?> getProjectionElements() {
      return getObjects(PC11);
    }

    @Override
    protected void verify() {
      retrieveReferenceElement(PC11);
      retrieveReferenceElement(PC2);
      testAttributeIdentity(retrieveReferenceElement(PC11), retrieveTargetElement(PC11),
          ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME);
    }
  }
  
  /**
   * PC to System: Test if a PC is correctly transitionned to a fragmented System
   */
  public static class Test3 extends CrossPhasesTest {
	  
	  
	@Override
	public List<String> getRequiredTestModels() {
		return Arrays.asList("Project_test_01", "fragmentedOutput"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	  
	@Override
	protected String getOutputModelPlatformURIString() {
		return "/fragmentedOutput/fragmentedOutput."+CapellaResourceHelper.CAPELLA_MODEL_FILE_EXTENSION;
	}

	@Override
	protected Collection<?> getProjectionElements() {
		return getObjects(PC11);
	}
	  
	@Override
	protected void verify() {
		  
		retrieveReferenceElement(PC11);
		testInstanceOf(retrieveTargetElement(PC11), ComponentType.SYSTEM);
		  
		retrieveReferenceElement(PC2);
		testInstanceOf(retrieveTargetElement(PC2), ComponentType.ACTOR);
	}
	  
  }
  /**
   * PC to System: Test if a PC Part is properly transitionned under the SystemComponentPkg
   */
  public static class Test4 extends CrossPhasesTest {
	  
	  
	@Override
	protected Collection<?> getProjectionElements() {
		return getObjects(B);
	}
	  
	@Override
	protected void verify() {		
		testInstanceOf(retrieveTargetElement(A), ComponentType.ACTOR);
		testInstanceOf(retrieveTargetElement(B), ComponentType.SYSTEM);		
		testInstanceOf(retrieveTargetElement(PARENT), ComponentType.ACTOR);
		
		SystemAnalysis targetSystemAnalysis = retrieveTargetSystemAnalysis();
		SystemComponentPkg pkg = targetSystemAnalysis.getOwnedSystemComponentPkg();
		assertTrue(pkg.getOwnedParts().contains(retrieveTargetElement(PART_PARENT)));
		assertTrue(pkg.getOwnedParts().contains(retrieveTargetElement(PART_A)));
		assertTrue(pkg.getOwnedParts().contains(retrieveTargetElement(PART_B)));
		
		assertTrue(pkg.getOwnedSystemComponents().contains(retrieveTargetElement(A)));
		assertTrue(pkg.getOwnedSystemComponents().contains(retrieveTargetElement(B)));
		assertTrue(pkg.getOwnedSystemComponents().contains(retrieveTargetElement(PARENT)));
		}
	  
  }
}

