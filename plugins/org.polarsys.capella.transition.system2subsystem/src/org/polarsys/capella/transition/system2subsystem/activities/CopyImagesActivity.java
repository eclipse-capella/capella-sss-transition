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
package org.polarsys.capella.transition.system2subsystem.activities;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.sirius.ui.helper.ResourceHelper;
import org.polarsys.capella.core.transition.common.activities.AbstractActivity;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.core.transition.system.helpers.ContextHelper;
import org.polarsys.capella.transition.system2subsystem.constants.Messages;
import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * This activity copies all images from the Transition Context Source Project into the Transition Context Target
 * Project.
 * 
 * @author <a href="mailto:arnaud.dieumegard@obeo.fr">Arnaud Dieumegard</a>
 */
public class CopyImagesActivity extends AbstractActivity {

    public static final String ID = "org.polarsys.capella.transition.system2subsystem.activities.CopyImagesActivity"; //$NON-NLS-1$

    /**
     * {@inheritDoc}
     */
    @Override
    protected IStatus _run(ActivityParameters activityParams_p) {

        IContext transformationContext = getContext(activityParams_p);
        Project sourceProject = ContextHelper.getSourceProject(transformationContext);
        Project targetProject = ContextHelper.getTargetProject(transformationContext);

        IProject sourceEclipseProject = ResourcesPlugin.getWorkspace().getRoot().getProject(sourceProject.getName());
        IProject targetEclipseProject = ResourcesPlugin.getWorkspace().getRoot().getProject(targetProject.getName());

        copyImagesToTargetProject(sourceEclipseProject, targetEclipseProject);

        return Status.OK_STATUS;
    }

    private void copyImagesToTargetProject(IProject sourceEclipseProject, IProject targetEclipseProject) {
        // Copy image
        try {
            for (IResource resource : ResourceHelper.collectImageFiles(sourceEclipseProject)) {
                // Prevent the copy of already existing resources
                if (!targetEclipseProject.exists(resource.getProjectRelativePath())) {
                    // Calculate newPath with the same file name
                    IPath newPath = targetEclipseProject.getFullPath();
                    newPath = newPath.addTrailingSeparator();
                    newPath = newPath.append(resource.getProjectRelativePath());
                    // Create folder hierarchy
                    boolean mkdirs = targetEclipseProject.getLocation().addTrailingSeparator().append(resource.getProjectRelativePath()).removeLastSegments(1).toFile().mkdirs();
                    if (mkdirs) {
                        targetEclipseProject.refreshLocal(newPath.segmentCount() - 1, null);
                    }
                    // Copy the file
                    resource.copy(newPath, true, new NullProgressMonitor());
                }
            }
        } catch (CoreException e) {
            LogHelper.getInstance().log(e.getMessage(), new Status(IStatus.ERROR, org.polarsys.capella.transition.system2subsystem.Activator.PLUGIN_ID, e.getMessage(), e),
                    Messages.Activity_CopyImages);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getActivityIdentifier() {
        return ID;
    }
}
