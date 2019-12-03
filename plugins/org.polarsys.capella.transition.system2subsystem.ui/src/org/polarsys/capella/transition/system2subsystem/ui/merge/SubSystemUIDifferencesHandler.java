/*******************************************************************************
 * Copyright (c) 2019 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.ui.merge;

import org.eclipse.emf.diffmerge.ui.viewers.DefaultUserProperties;
import org.eclipse.emf.diffmerge.ui.viewers.EMFDiffNode;
import org.polarsys.capella.core.transition.common.ui.handlers.merge.MergeEMFDiffNode;
import org.polarsys.capella.core.transition.common.ui.handlers.merge.MergeUIDifferencesHandler;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * This class can be safely removed when addon is compatible with Capella 1.4.1+ (not 1.4.0)
 * 
 * Starting of 1.4.1, registerUserProperties is called by MergeUIDifferencesHandler.viewer.registerUserProperties
 */
public class SubSystemUIDifferencesHandler extends MergeUIDifferencesHandler {

  @Override
  protected void initializeCategories(IContext context, MergeEMFDiffNode diffNode) {
    super.initializeCategories(context, diffNode);
    registerUserProperties(diffNode);
  }

  protected void registerUserProperties(EMFDiffNode diffNode) {
    diffNode.setUserPropertyValue(DefaultUserProperties.P_DEFAULT_INCREMENTAL_MODE, true);
    diffNode.setUserPropertyValue(DefaultUserProperties.P_DEFAULT_COVER_CHILDREN, true);
    diffNode.setUserPropertyValue(DefaultUserProperties.P_DEFAULT_SHOW_MERGE_IMPACT, false);
    diffNode.setUserPropertyValue(DefaultUserProperties.P_SUPPORT_UNDO_REDO, false);
  }
}
