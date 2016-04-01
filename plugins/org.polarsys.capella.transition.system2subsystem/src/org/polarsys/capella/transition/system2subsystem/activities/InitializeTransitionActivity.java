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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.model.handler.helpers.HoldingResourceHelper;
import org.polarsys.capella.core.transition.common.constants.IOptionsConstants;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.IHandler;
import org.polarsys.capella.core.transition.common.handlers.options.OptionsHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.scope.CompoundScopeFilter;
import org.polarsys.capella.core.transition.common.handlers.scope.CompoundScopeRetriever;
import org.polarsys.capella.core.transition.common.handlers.scope.IScopeFilter;
import org.polarsys.capella.core.transition.common.handlers.scope.IScopeRetriever;
import org.polarsys.capella.core.transition.system.handlers.attachment.CapellaDefaultAttachmentHandler;
import org.polarsys.capella.transition.system2subsystem.constants.ISubSystemConstants;
import org.polarsys.capella.transition.system2subsystem.handlers.attachment.FunctionalChainAttachmentHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.attachment.PhysicalPathAttachmentHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.attachment.ScenarioAttachmentHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.scope.ExternalFunctionsScopeRetriever;
import org.polarsys.capella.transition.system2subsystem.handlers.scope.PropertyValuesScopeFilter;
import org.polarsys.capella.transition.system2subsystem.handlers.scope.PropertyValuesScopeRetriever;
import org.polarsys.capella.transition.system2subsystem.handlers.session.SubSystemSessionHandler;
import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;


/**
 * 
 */
public class InitializeTransitionActivity extends org.polarsys.capella.core.transition.system.activities.InitializeTransitionActivity {

  public static final String ID = "org.polarsys.capella.transition.system2subsystem.activities.InitializeTransitionActivity"; //$NON-NLS-1$

  @Override
  protected String getDefaultOptionsScope() {
    return "capella.core.transition.system2subsystem"; //$NON-NLS-1$
  }

  @Override
  protected IStatus initializeScopeFilterHandlers(IContext context, CompoundScopeFilter handler, ActivityParameters activityParams) {
    IScopeFilter filter = PropertyValuesScopeFilter.getInstance(context);
    handler.addScopeFilter(filter, context);

    return super.initializeScopeFilterHandlers(context, handler, activityParams);
  }

  @Override
  protected Collection<Object> adaptSelection(Collection<Object> selection) {
    Collection<Object> superCollection = super.adaptSelection(selection);
    Collection<Object> result = new ArrayList<Object>();
    for (Object item : superCollection) {
      if (item instanceof EObject) {
        if (item instanceof Part) {
          item = ((Part) item).getAbstractType();
        }
        result.add(item);
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IStatus initializeScopeRetrieverHandlers(IContext context, CompoundScopeRetriever handler, ActivityParameters activityParams) {
    IScopeRetriever retriever = new PropertyValuesScopeRetriever();
    handler.addScopeRetriever(retriever, context);
    
    handler.addScopeRetriever(new ExternalFunctionsScopeRetriever(), context);
    
    return super.initializeScopeRetrieverHandlers(context, handler, activityParams);
  }

  @Override
  protected IStatus initializeContext(IContext context, ActivityParameters activityParams) {

    IHandler handler = new FunctionalChainAttachmentHelper();
    handler.init(context);
    context.put(ISubSystemConstants.FUNCTIONAL_CHAIN_ATTACHMENT_HELPER, handler);

    handler = new PhysicalPathAttachmentHelper();
    handler.init(context);
    context.put(ISubSystemConstants.PHYSICAL_PATH_ATTACHMENT_HELPER, handler);

    handler = new ScenarioAttachmentHelper();
    handler.init(context);
    context.put(ISubSystemConstants.SCENARIO_ATTACHMENT_HELPER, handler);

    return super.initializeContext(context, activityParams);
  }

  @Override
  protected IStatus initializeTarget(IContext context, ActivityParameters activityParams) {

    String outputproject =
        OptionsHandlerHelper.getInstance(context).getStringValue(context, IOptionsConstants.TRANSITION_PREFERENCES, IOptionsConstants.OUTPUT_PROJECT,
            IOptionsConstants.OUTPUT_PROJECT_DEFAULT_VALUE);
    Resource outputResource = null;

    URI fileUri = URI.createPlatformResourceURI(outputproject, true);

    // we must use a single editing domain throughout the entire operation
    EditingDomain targetDomain = (EditingDomain) context.get(ITransitionConstants.TRANSITION_SOURCE_EDITING_DOMAIN);
    context.put(ITransitionConstants.TRANSITION_TARGET_EDITING_DOMAIN, targetDomain);

    outputResource = targetDomain.getResourceSet().getResource(fileUri, true);
    context.put(ITransitionConstants.TRANSITION_TARGET_RESOURCE, outputResource);

    Project project = (Project) outputResource.getContents().get(0);
    context.put(ITransitionConstants.TRANSITION_TARGET_ROOT, getEngineering(project, project.getName()));

    return Status.OK_STATUS;
  }

  /**
   * Create default session handler for common transition
   * @return
   */
  @Override
  protected IHandler createDefaultSessionHandler() {
    return new SubSystemSessionHandler();
  }

  /**
   * Return a customized attachment handler that
   * detaches elements from the holding resource 
   * as soon as they are attached to their destination container
   */
  @Override
  protected IHandler createDefaultAttachmentHandler() {
    return new CapellaDefaultAttachmentHandler(){
      @Override
      public boolean attachElementByReference(EObject sourceAttaching, EObject targetAttaching, EObject sourceAttached, EObject targetAttached,
          EReference sourceFeature, EReference targetFeature) {
    	  HoldingResourceHelper.ensureMoveElement(targetAttached, targetAttaching);
    	  return super.attachElementByReference(sourceAttaching, targetAttaching, sourceAttached, targetAttached, sourceFeature, targetFeature);
      }
    };
  }
  
}
