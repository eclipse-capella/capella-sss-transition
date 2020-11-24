/*******************************************************************************
 * Copyright (c) 2018 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests.util;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.diffmerge.diffdata.impl.EComparisonImpl;
import org.eclipse.emf.diffmerge.generic.api.IMatchPolicy;
import org.eclipse.emf.diffmerge.generic.api.Role;
import org.eclipse.emf.diffmerge.generic.api.diff.IDifference;
import org.eclipse.emf.diffmerge.impl.policies.DefaultDiffPolicy;
import org.eclipse.emf.diffmerge.impl.policies.DefaultMatchPolicy;
import org.eclipse.emf.diffmerge.impl.policies.DefaultMergePolicy;
import org.eclipse.emf.diffmerge.impl.scopes.RootedModelScope;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.polarsys.capella.common.ef.ExecutionManager;
import org.polarsys.capella.common.helpers.EcoreUtil2;

/**
 * A diffmerge helper allowing comparison of two files in the workspace.
 * 
 * Ordering differences are ignored in this context
 */
public class DiffHelper {

  IMatchPolicy matchPolicy = null;

  public IMatchPolicy getMatchPolicy() {
    if (matchPolicy == null) {
      matchPolicy = new DefaultMatchPolicy() {
        @Override
        public boolean keepMatchIDs() {
          return true;
        }
      };
    }
    return matchPolicy;
  }

  public DiffHelper setMatchPolicy(IMatchPolicy matchPolicy) {
    this.matchPolicy = matchPolicy;
    return this;
  }

  /**
   * Trigger a diffmerge between both given files and return computed differences
   */
  public Collection<IDifference> getDifferences(IFile file1, IFile file2) {

    ResourceSet set2 = new ExecutionManager().getEditingDomain().getResourceSet();
    Resource outputModelResource1 = set2.getResource(EcoreUtil2.getURI(file1), true);
    Resource outputModelResource2 = set2.getResource(EcoreUtil2.getURI(file2), true);
    RootedModelScope source = new RootedModelScope(outputModelResource1.getContents());
    RootedModelScope target = new RootedModelScope(outputModelResource2.getContents());

    EComparisonImpl impl = new EComparisonImpl(target, source);

    impl.compute(getMatchPolicy(), new DefaultDiffPolicy() {
      @Override
      protected boolean considerOrderedFeature(EStructuralFeature feature_p) {
        return false;
      }
    }, new DefaultMergePolicy(), new NullProgressMonitor());

    // Run in UI. Uncomment to debug
    //EMFDiffNode node = new EMFDiffNode(impl);
    //org.eclipse.emf.diffmerge.ui.util.DiffMergeDialog dialog = new DiffMergeDialog(
    //    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), DiffMergeDialog.class.getSimpleName(), node);
    //dialog.open();

    Collection<IDifference> differences = new HashSet<IDifference>();
    differences.addAll(impl.getDifferences(Role.REFERENCE));
    differences.addAll(impl.getDifferences(Role.TARGET));
    return differences;
  }

  /**
   * Create a copy of the given file into an arbitrary file
   */
  public static IFile copyFile(IFile file) {
    IFile file2 = file.getParent()
        .getFile(file.getProjectRelativePath().addFileExtension("bkp").addFileExtension(file.getFileExtension()));
    try {
      file2.create(file.getContents(), IFile.FORCE, new NullProgressMonitor());
    } catch (CoreException e) {
      e.printStackTrace();
    }

    return file2;
  }

}
