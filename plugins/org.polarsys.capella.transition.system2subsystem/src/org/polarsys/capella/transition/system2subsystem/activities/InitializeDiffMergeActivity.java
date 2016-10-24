/*******************************************************************************
 * Copyright (c) 2006, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.activities;

import org.eclipse.core.runtime.IStatus;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.interaction.InteractionPackage;
import org.polarsys.capella.core.transition.common.handlers.merge.IMergeHandler;
import org.polarsys.capella.core.transition.system.topdown.handlers.merge.RealizationLinkCategoryFilter;
import org.polarsys.capella.transition.system2subsystem.handlers.filter.UpdateOfCategoryFilter;
import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 *
 */
public class InitializeDiffMergeActivity extends org.polarsys.capella.core.transition.system.activities.InitializeDiffMergeActivity {

  @Override
  protected IStatus initializeCategoriesHandlers(IContext context, IMergeHandler handler,
      ActivityParameters activityParams) {

    handler.addCategory(new RealizationLinkCategoryFilter(context), context);
    handler.addCategory(new UpdateOfCategoryFilter(FaPackage.Literals.FUNCTIONAL_CHAIN, context), context);
    handler.addCategory(new UpdateOfCategoryFilter(CsPackage.Literals.PHYSICAL_PATH, context), context);
    handler.addCategory(new UpdateOfCategoryFilter(InteractionPackage.Literals.SCENARIO, context), context);

    return super.initializeCategoriesHandlers(context, handler, activityParams);
  }

}
