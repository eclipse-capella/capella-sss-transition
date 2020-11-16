/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
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
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.transition.common.constants.ISchemaConstants;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.traceability.ITraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.config.ExtendedTraceabilityConfiguration;
import org.polarsys.capella.core.transition.system.handlers.traceability.LibraryTraceabilityHandler;
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
      protected void initializeComponent(Component source_p, Component target_p, IContext context_p,
          LevelMappingTraceability map_p) {
        // Nothing here, we don't want to play with matching though stateMachines size while transformation
      }

      /**
       * {@inheritDoc}
       */
      @Override
      protected void initializeRootMappings(IContext context_p) {
        super.initializeRootMappings(context_p);
        addMappings(ContextHelper.getSourceProject(context_p), ContextHelper.getTransformedProject(context_p),
            context_p);
        addMappings(ContextHelper.getSourceEngineering(context_p), ContextHelper.getTransformedEngineering(context_p),
            context_p);
      }

      /**
       * @param sourceRoot_p
       * @param targetRoot_p
       * @param context
       * @param map
       */
      protected void initializeBlockArchitecture(BlockArchitecture source, BlockArchitecture target, IContext context,
          LevelMappingTraceability map) {
        EObject sourceChild = BlockArchitectureExt.getFunctionPkg(source, false);
        if (sourceChild != null) {
          addMapping(map, sourceChild, BlockArchitectureExt.getFunctionPkg(target, true), context);
        }
        sourceChild = BlockArchitectureExt.getRootFunction(source, false);
        if (sourceChild != null) {
          addMapping(map, sourceChild, BlockArchitectureExt.getRootFunction(target, true), context);
        }
        sourceChild = BlockArchitectureExt.getDataPkg(source, false);
        if (sourceChild != null) {
          addMapping(map, sourceChild, BlockArchitectureExt.getDataPkg(target, true), context);
        }
        sourceChild = BlockArchitectureExt.getActorPkg(source, false);
        if (sourceChild != null) {
          addMapping(map, sourceChild, BlockArchitectureExt.getActorPkg(target, true), context);
        }
        sourceChild = BlockArchitectureExt.getContext(source, false);
        if (sourceChild != null) {
          addMapping(map, sourceChild, BlockArchitectureExt.getContext(target, true), context);
        }
        sourceChild = BlockArchitectureExt.getInterfacePkg(source, false);
        if (sourceChild != null) {
          addMapping(map, sourceChild, BlockArchitectureExt.getInterfacePkg(target, true), context);
        }
        sourceChild = BlockArchitectureExt.getRequirementsPkg(source, false);
        if (sourceChild != null) {
          addMapping(map, sourceChild, BlockArchitectureExt.getRequirementsPkg(target, true), context);
        }
        sourceChild = BlockArchitectureExt.getAbstractCapabilityPkg(source, false);
        if (sourceChild != null) {
          addMapping(map, sourceChild, BlockArchitectureExt.getAbstractCapabilityPkg(target, true), context);
        }
      }

    });

    addHandler(fContext_p, new SIDTraceabilityHandler(getIdentifier(fContext_p)) {

      /**
       * {@inheritDoc}
       */
      @Override
      protected void initializeRootMappings(IContext context_p) {
        super.initializeRootMappings(context_p);
        initializeMappings(ContextHelper.getSourceProject(context_p), ContextHelper.getTransformedProject(context_p),
            context_p);
      }

      @Override
      protected void createAttachment(EObject sourceElement_p, EObject targetElement_p, IContext context_p) {
        EAttribute attribute = getAttribute(context_p);
        if (!targetElement_p.eClass().getEAllAttributes().contains(attribute)) {
          return;
        }
        super.createAttachment(sourceElement_p, targetElement_p, context_p);
      }

    });

    addHandler(fContext_p, new LibraryTraceabilityHandler());

  }

  public SystemEngineering getSourceEngineering(IContext context) {
    Collection<EObject> selection = (Collection<EObject>) context.get(ITransitionConstants.TRANSITION_SOURCES);
    if (!selection.isEmpty()) {
      EObject sourceSelection = (EObject) selection.toArray()[0];
      return SystemEngineeringExt.getSystemEngineering((CapellaElement) sourceSelection);
    }
    return null;
  }

  @Override
  public boolean useHandlerForSourceElements(EObject source, ITraceabilityHandler handler, IContext context) {
    if (LibraryTraceabilityHandler.isLibraryElement(source, context)) {
      return handler instanceof LibraryTraceabilityHandler;
    }
    return super.useHandlerForSourceElements(source, handler, context);
  }

  @Override
  public boolean useHandlerForTracedElements(EObject source, ITraceabilityHandler handler, IContext context) {
    if (LibraryTraceabilityHandler.isLibraryElement(source, context)) {
      return handler instanceof LibraryTraceabilityHandler;
    }
    return super.useHandlerForTracedElements(source, handler, context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean useHandlerForAttachment(EObject source_p, EObject target_p, ITraceabilityHandler handler_p,
      IContext context_p) {
    if (LibraryTraceabilityHandler.isLibraryElement(source_p, context_p)) {
      return handler_p instanceof LibraryTraceabilityHandler;
    }

    boolean result = super.useHandlerForAttachment(source_p, target_p, handler_p, context_p);
    if (result) {
      // We disable Reconciliation for attachment
      if (handler_p instanceof ReconciliationTraceabilityHandler) {
        result = false;
      }
    }

    return result;
  }

}
