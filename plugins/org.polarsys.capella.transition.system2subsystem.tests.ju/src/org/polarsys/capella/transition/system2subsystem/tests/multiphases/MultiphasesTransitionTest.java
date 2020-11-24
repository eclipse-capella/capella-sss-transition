/*******************************************************************************
 * Copyright (c) 2016, 2018 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests.multiphases;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.diffmerge.generic.api.diff.IDifference;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTestActivator;
import org.polarsys.capella.transition.system2subsystem.tests.util.DiffHelper;
import org.polarsys.capella.transition.system2subsystem.tests.util.ProjectCreationHelper;
import org.polarsys.capella.transition.system2subsystem.tests.util.QualifiedMatchPolicy;

/**
 * This test ensure that a Multiphase transition of model init_transition into a new model has the same result than PC1PC2_result
 */
public class MultiphasesTransitionTest extends MultiPhasesTest {

  private static final String OUTPUT_PROJECT_NAME = "PC1PC2"; //$NON-NLS-1$
  private static final String OUTPUT_FILENAME = "PC1PC2.melodymodeller"; //$NON-NLS-1$
  private static final String PC1 = "eeed51b0-e875-4f01-b43c-7bda9d0a2699"; //$NON-NLS-1$
  private static final String PC2 = "0adec35f-010e-4281-8a61-7146263486e7"; //$NON-NLS-1$

  public MultiphasesTransitionTest() {
    super();
  }

  public MultiphasesTransitionTest(boolean withLibrary) {
    super(withLibrary);
  }
  
  @Override
  public void setUp() throws Exception {
    super.setUp();
    ProjectCreationHelper helper = new ProjectCreationHelper(true, true);
    IProject project = helper.createNewEclipseProject(OUTPUT_PROJECT_NAME, null, Collections.<IProject> emptyList(),
        new NullProgressMonitor());
    helper.createSemanticResource(project, new NullProgressMonitor());
  }

  @Override
  public void tearDown() {
    try {
      ResourcesPlugin.getWorkspace().getRoot().getProject(OUTPUT_PROJECT_NAME).delete(true, new NullProgressMonitor());
    } catch (CoreException e) {
      System2SubsystemTestActivator.getDefault().getLog()
          .log(new Status(e.getStatus().getSeverity(), System2SubsystemTestActivator.PLUGIN_ID, e.getMessage(), e));
    }
  }
  
  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("init_transition", "PC1PC2_result");
  }

  protected Collection<?> getProjectionElements() {
    return getObjects(PC1, PC2);
  }

  protected String getOutputModelPlatformURIString() {
    return NLS.bind("/{0}/{1}", OUTPUT_PROJECT_NAME, OUTPUT_FILENAME);
  }

  public void test() throws Exception {
    
    super.test(); //Perform the transition into the output

    IFile expectedResult = ResourcesPlugin.getWorkspace().getRoot().getProject("PC1PC2_result").getFile("PC1PC2.melodymodeller");
    IFile currentResult = ResourcesPlugin.getWorkspace().getRoot().getProject(OUTPUT_PROJECT_NAME).getFile(OUTPUT_FILENAME);
    
    Collection<IDifference> differences = new DiffHelper().setMatchPolicy(new QualifiedMatchPolicy()).getDifferences(currentResult, expectedResult);
    assertTrue("There shall have no differences between multiphases of 'init_transition' and 'PC1PC2_result'", differences.isEmpty());
  }

}
