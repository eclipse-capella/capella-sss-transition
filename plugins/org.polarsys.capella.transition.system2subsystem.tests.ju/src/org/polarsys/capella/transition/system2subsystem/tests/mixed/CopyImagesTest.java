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

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;
import org.polarsys.capella.transition.system2subsystem.tests.multiphases.MultiPhasesTest;

/**
 * Test that verifies the copy of images for all kind of transitions
 */
public class CopyImagesTest {

    private static final String MP_ORDERED_PA_PC_4 = "b271ea03-5360-43a3-823f-1f4aa88f83ba"; //$NON-NLS-1$

    private static String _id_pc1 = "4d32c631-16c0-40cf-afb6-4493c895ab56"; //$NON-NLS-1$

    private static final String LC_1 = "aa75d1f7-8bd9-49ff-8e61-a3462115e04e";

    private static final String COPIED_IMAGE_PATH = "images/Capella128x128.png";
    
    /**
     * Test that verifies the copy of images during a Multiphases transition
     */
    public static class CopyImagesInMultiphasesTest extends MultiPhasesTest {

        public CopyImagesInMultiphasesTest() {
            super();
        }

        @Override
        protected Collection<?> getProjectionElements() {
            return getObjects(MP_ORDERED_PA_PC_4);
        }

        @Override
        protected void verify() throws Exception {
            testImageCopy(getRequiredTestModels().get(1));
        }
    }

    /**
     * Test that verifies the copy of images during an Interphases transition
     */
    public static class CopyImagesInInterphasesTest extends System2SubsystemTest implements Interphase {

        public CopyImagesInInterphasesTest() {
            super();
        }

        @Override
        protected Collection<?> getProjectionElements() {
            return getObjects(_id_pc1);
        }

        @Override
        protected void verify() {
            testImageCopy(getRequiredTestModels().get(1));
        }

    }

    /**
     * Test that verifies the copy of images during a Crossphases transition
     */
    public static class CopyImagesInCrossphasesTest extends System2SubsystemTest implements Crossphase {

        public CopyImagesInCrossphasesTest() {
            super();
        }

        @Override
        protected Collection<?> getProjectionElements() {
            return getObjects(LC_1);
        }

        @Override
        protected void verify() throws Exception {
            testImageCopy(getRequiredTestModels().get(1));
        }

    }

    public static void testImageCopy(String outputProjectName) {
        IFile copiedImage = ResourcesPlugin.getWorkspace().getRoot().getProject(outputProjectName).getFile(COPIED_IMAGE_PATH);
        assertTrue("An image file should have been copied", copiedImage.exists());
    }

}
