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
package org.polarsys.capella.transition.system2subsystem.tests.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

/**
 * A verifier that fails if there are uncommitted changes in a given git repository.
 */
public class GitHelper {

  private final Git git;

  public GitHelper(Git git_p) {
    git = git_p;
  }

  public Git getGit() {
    return git;
  }

  public void verifyNoChange() throws Exception {
    TreeFilter filter = TreeFilter.ALL;
    List<DiffEntry> differences = git.diff().setPathFilter(filter).setOutputStream(System.err).call();
    assertEquals("Test output is different from reference output", 0, differences.size());
  }

  public static GitHelper create(File localPath) throws IOException, NoFilepatternException, GitAPIException {

    File gitDir = new File(localPath, ".git");
    Repository repo = new FileRepositoryBuilder().setGitDir(gitDir).build();
    // repo = new FileRepository(gitDir);

    if (!gitDir.exists()) {
      repo.create();
    }

    Git git = new Git(repo);
    git.add().addFilepattern(".").call();
    git.commit().setMessage("Initial commit").call();

    return new GitHelper(git);
  }

  public static GitHelper create(URI platformResourceURI) throws IOException, NoFilepatternException, GitAPIException {
    String platformString = platformResourceURI.toPlatformString(true);
    IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(platformString));
    return create(container.getLocation().toFile());
  }

}
