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
package org.polarsys.capella.transition.system2subsystem.tests.multiphases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.polarsys.capella.core.platform.sirius.ui.project.operations.ProjectSessionCreationHelper;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTestActivator;
import org.polarsys.capella.transition.system2subsystem.tests.util.GitHelper;
import org.polarsys.capella.transition.system2subsystem.tests.util.IdSequencer;
import org.polarsys.capella.transition.system2subsystem.tests.util.ModelNormalizer;
import org.polarsys.capella.transition.system2subsystem.tests.util.SortCommand;

import com.google.common.io.ByteStreams;

public class MultiphasesTransitionTest extends MultiPhasesTest {

  static class Helper extends ProjectSessionCreationHelper {

    public Helper(boolean epbsSelected_p, boolean opaSelected_p) {
      super(epbsSelected_p, opaSelected_p);
    }

    @Override
    public IFile createSemanticResource(IProject eclipseProject, IProgressMonitor monitor) {
      return super.createSemanticResource(eclipseProject, monitor);
    }

  };

  private IProject outputProject;
  private final String inputProjectName = "init_transition"; //$NON-NLS-1$
  private final String outputProjectName = "PC1PC2"; //$NON-NLS-1$
  private final String outputFileName = "PC1PC2.melodymodeller"; //$NON-NLS-1$
  private final String outputPlatformURIString = "/PC1PC2/PC1PC2.melodymodeller"; //$NON-NLS-1$
  private final String idPC1 = "eeed51b0-e875-4f01-b43c-7bda9d0a2699"; //$NON-NLS-1$
  private final String idPC2 = "0adec35f-010e-4281-8a61-7146263486e7"; //$NON-NLS-1$

  @Override
  public void setUp() throws Exception {
    super.setUp();
    Helper helper = new Helper(true, true);
    outputProject = helper.createNewEclipseProject(outputProjectName, null, Collections.<IProject> emptyList(),
        new NullProgressMonitor());
    helper.createSemanticResource(outputProject, new NullProgressMonitor());
  }

  @Override
  public void tearDown() {
    try {
      outputProject.delete(true, new NullProgressMonitor());
    } catch (CoreException e) {
      System2SubsystemTestActivator.getDefault().getLog()
          .log(new Status(e.getStatus().getSeverity(), System2SubsystemTestActivator.PLUGIN_ID, e.getMessage(), e));
    }
  }

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList(inputProjectName);
  }

  protected Collection<?> getProjectionElements() {
    return getObjects(idPC1, idPC2);
  }

  protected String getOutputModelPlatformURIString() {
    return outputPlatformURIString;
  }

  public void test() throws Exception {
    super.test();

    final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(getOutputModelResource());

    // reassign ids
    domain.getCommandStack().execute(IdSequencer.sequenceIds(getOutputModelResource(), true));

    // sort references
    domain.getCommandStack().execute(new SortCommand(getOutputModelResource(), ModelNormalizer.DEFAULT_SORT_PREDICATE));

    getOutputModelResource().save(Collections.emptyMap());

    GitHelper helper = GitHelper
        .create(URI.createPlatformResourceURI(getOutputModelPlatformURIString(), true).trimSegments(1));
    URL referenceExportURL = System2SubsystemTestActivator.getDefault().getBundle()
        .getEntry(getTestCasesRootFolderName() + getOutputModelPlatformURIString());

    try (InputStream reference = referenceExportURL.openStream();
        OutputStream target = new FileOutputStream(
            new File(helper.getGit().getRepository().getWorkTree(), outputFileName));) {
      ByteStreams.copy(reference, target);
    }
    helper.verifyNoChange();
  }

}
