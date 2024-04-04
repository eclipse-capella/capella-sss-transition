/*******************************************************************************
 * Copyright (c) 2022, 2024 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.ui.preferences;

import java.util.Optional;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.polarsys.capella.common.flexibility.wizards.schema.IRendererContext;
import org.polarsys.capella.common.helpers.EcoreUtil2;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.context.TransitionContext;
import org.polarsys.capella.core.transition.system.ui.dialog.ProjectSelectionDialog;
import org.polarsys.capella.core.transition.system.ui.preferences.OutputModelRenderer;

public class FilteredOutputModelRenderer extends OutputModelRenderer {
  
  class NoSourceProjectViewerFilter extends ViewerFilter {
    
    private Optional<IProject> toFilterCapellaProject;
    
    public NoSourceProjectViewerFilter(Resource toFilterCapellaProject) {
      this.toFilterCapellaProject = Optional.ofNullable(EcoreUtil2.getFile(toFilterCapellaProject)).map(f -> f.getProject());
    }
    
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
    return element instanceof IResource && !(this.toFilterCapellaProject.isPresent() && this.toFilterCapellaProject.get().equals(element));
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void proceedBrowse(Shell shell, IRendererContext context) {
    String targetCapellaProject = getTargetProject(shell, context);
    if (targetCapellaProject != null) {
      changeValue(context.getProperty(this), context, targetCapellaProject);
      updatedValue(context.getProperty(this), context, targetCapellaProject);
    }
  }

  protected String getTargetProject(Shell shell, IRendererContext context) {
    ProjectSelectionDialog dialog = new ProjectSelectionDialog(shell, new WorkbenchLabelProvider(),
        new BaseWorkbenchContentProvider());

    if (context.getPropertyContext().getSource() instanceof TransitionContext) {
      TransitionContext transitionContext = (TransitionContext) context.getPropertyContext().getSource();
      Resource sourceModelResource = (Resource) transitionContext.get(ITransitionConstants.TRANSITION_SOURCE_RESOURCE);
      dialog.addFilter(new NoSourceProjectViewerFilter(sourceModelResource));
    }

    dialog.open();
    Object obj = dialog.getFirstResult();
    if (obj instanceof String) {
      return (String) obj;
    }
    return null;
  }
  
}
