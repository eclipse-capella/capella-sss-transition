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
package org.polarsys.capella.transition.system2subsystem.handlers.traceability.config;

import java.util.Collection;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellamodeller.Library;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.transition.common.constants.ISchemaConstants;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.traceability.ITraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.config.ExtendedTraceabilityConfiguration;
import org.polarsys.capella.core.transition.system.handlers.traceability.ReconciliationTraceabilityHandler;
import org.polarsys.capella.core.transition.system.helpers.ContextHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.traceability.SIDTraceabilityHandler;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 *
 */
public class TransformationConfiguration extends ExtendedTraceabilityConfiguration {

  @Override
  protected String getExtensionIdentifier(IContext context_p) {
    return ISchemaConstants.TRANSFORMATION_TRACEABILITY_CONFIGURATION;
  }

  @Override
  protected void initHandlers(IContext fContext_p) {
    addHandler(fContext_p, new ReconciliationTraceabilityHandler(getIdentifier(fContext_p)) {

      @Override
      protected void initializeComponent(Component source_p, Component target_p, IContext context_p, LevelMappingTraceability map_p) {
        //Nothing here, we don't want to play with matching though stateMachines size while transformation
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected void initializeRootMappings(IContext context_p) {
        super.initializeRootMappings(context_p);
        addMappings(ContextHelper.getSourceProject(context_p), ContextHelper.getTransformedProject(context_p), context_p);
        addMappings(ContextHelper.getSourceEngineering(context_p), ContextHelper.getTransformedEngineering(context_p), context_p);
      }

    });

    addHandler(fContext_p, new SIDTraceabilityHandler(getIdentifier(fContext_p)) {

      /**
       * {@inheritDoc}
       */
      @Override
      protected void initializeRootMappings(IContext context_p) {
        super.initializeRootMappings(context_p);
        initializeMappings(ContextHelper.getSourceProject(context_p), ContextHelper.getTransformedProject(context_p), context_p);
      }
      
      @Override
     	protected void createAttachment(EObject sourceElement_p, EObject targetElement_p, IContext context_p) {
     	    EAttribute attribute = getAttribute(context_p);
     	    if (! targetElement_p.eClass().getEAllAttributes().contains(attribute))
     	    {
     	    	return ;
     	    }
     		super.createAttachment(sourceElement_p, targetElement_p, context_p);
     	}

    });

  }
  
  public SystemEngineering getSourceEngineering(IContext context) {
    Collection<EObject> selection = (Collection<EObject>) context.get(ITransitionConstants.TRANSITION_SOURCES);
    if (!selection.isEmpty()) {
      EObject sourceSelection = (EObject) selection.toArray()[0];
      return SystemEngineeringExt.getSystemEngineering((CapellaElement)sourceSelection);
    }
    return null;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean useHandlerForAttachment(EObject source_p, EObject target_p, ITraceabilityHandler handler_p, IContext context_p) {

    boolean result = super.useHandlerForAttachment(source_p, target_p, handler_p, context_p);
    if (result) {
      //We disable Reconciliation for attachment
      if (handler_p instanceof ReconciliationTraceabilityHandler) {
        result = false;
      }
    }

    return result;
  }

}
