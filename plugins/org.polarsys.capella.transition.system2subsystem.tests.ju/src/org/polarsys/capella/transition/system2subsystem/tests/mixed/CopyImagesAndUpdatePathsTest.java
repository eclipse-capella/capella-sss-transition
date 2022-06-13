/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;
import org.polarsys.capella.transition.system2subsystem.tests.TraceabilityArchitectureSID;
import org.polarsys.capella.transition.system2subsystem.tests.multiphases.MultiPhasesTest;

/**
 * Test that verifies the copy of images for all kind of transitions
 */
public class CopyImagesAndUpdatePathsTest {
    
    //TODO: modify the IDS to take elements from the actual correct SystemEngineering containing images

    private static final String ID_PC1 = "92e20a23-fb3f-45d8-aa2f-8aaecdafa7b4"; //$NON-NLS-1$

    private static final String ID_LC1 = "8da8b3c5-9ed3-4bf7-93da-f53cc4ba95ab";

    private static final String COPIED_IMAGE_PATH = "images/Capella128x128.png";
    
    private static final String COPIED_LIB_IMAGE_PATH = "output_lib/images/Capella64x64.png";
    
    // Hide default constructor
    CopyImagesAndUpdatePathsTest() {}
    
    /**
     * Test that verifies the copy of images during a Multiphases transition
     */
    public static class CopyImagesAndUpdatePathsMultiphasesTest extends MultiPhasesTest {

        public CopyImagesAndUpdatePathsMultiphasesTest() {
            super();
        }

        @Override
        protected Collection<?> getProjectionElements() {
            return getObjects(ID_PC1);
        }

        @Override
        protected void verify() throws Exception {
            ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.SA);
            testImageCopy(getRequiredTestModels().get(0), getRequiredTestModels().get(1), mustBeTransitioned(ID_PC1, ComponentType.SYSTEM));
            ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.LA);
            testImageCopy(getRequiredTestModels().get(0), getRequiredTestModels().get(1), mustBeTransitioned(ID_PC1, ComponentType.LOGICAL_COMPONENT));
            ((TraceabilityArchitectureSID) traceability).setArchitecture(BlockArchitectureExt.Type.PA);
            testImageCopy(getRequiredTestModels().get(0), getRequiredTestModels().get(1), mustBeTransitioned(ID_PC1));
        }
    }

    /**
     * Test that verifies the copy of images during an Interphases transition
     */
    public static class CopyImagesAndUpdatePathsInterphasesTest extends System2SubsystemTest implements Interphase {

        public CopyImagesAndUpdatePathsInterphasesTest() {
            super();
        }

        @Override
        protected Collection<?> getProjectionElements() {
            return getObjects(ID_PC1);
        }

        @Override
        protected void verify() {
            testImageCopy(getRequiredTestModels().get(0), getRequiredTestModels().get(1), mustBeTransitioned(ID_PC1));
        }

    }

    /**
     * Test that verifies the copy of images during a Crossphases transition
     */
    public static class CopyImagesAndUpdatePathsCrossphasesTest extends System2SubsystemTest implements Crossphase {

        public CopyImagesAndUpdatePathsCrossphasesTest() {
            super();
        }

        @Override
        protected Collection<?> getProjectionElements() {
            return getObjects(ID_LC1);
        }

        @Override
        protected void verify() throws Exception {
            testImageCopy(getRequiredTestModels().get(0), getRequiredTestModels().get(1), mustBeTransitioned(ID_LC1));
        }

    }

    public static void testImageCopy(String sourceProjectName, String targetProjectName, EObject targetObject) {
        IFile copiedImage = ResourcesPlugin.getWorkspace().getRoot().getProject(targetProjectName).getFile(COPIED_IMAGE_PATH);
        assertTrue("An image file should have been copied", copiedImage.exists());
        
        assertTrue("Target Object is a Capella element", targetObject instanceof CapellaElement);
        
        boolean containsExpectedModifiedPath = ((CapellaElement)targetObject).getDescription().contains(targetProjectName + "/" + COPIED_IMAGE_PATH);
        boolean containsOriginalPath = ((CapellaElement)targetObject).getDescription().contains(sourceProjectName + "/" + COPIED_IMAGE_PATH);
        
        boolean containsOriginalLibPath = ((CapellaElement)targetObject).getDescription().contains(COPIED_LIB_IMAGE_PATH);
        
        assertTrue("Target Object shall contain the modified image path", containsExpectedModifiedPath);
        assertFalse("Target Object shall not contain the original image path", containsOriginalPath);
        assertTrue("Target Object shall still contain the original lib image path", containsOriginalLibPath);
        
    }

}

