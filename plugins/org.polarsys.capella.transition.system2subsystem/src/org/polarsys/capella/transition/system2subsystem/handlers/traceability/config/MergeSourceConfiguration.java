/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.handlers.traceability.config;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.libraries.ModelInformation;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.transition.common.constants.ISchemaConstants;
import org.polarsys.capella.core.transition.common.handlers.traceability.ITraceabilityHandler;
import org.polarsys.capella.core.transition.common.handlers.traceability.config.ExtendedTraceabilityConfiguration;
import org.polarsys.capella.core.transition.system.handlers.traceability.RealizationLinkTraceabilityHandler;
import org.polarsys.capella.core.transition.system.handlers.traceability.ReconciliationTraceabilityHandler;
import org.polarsys.capella.core.transition.system.helpers.ContextHelper;
import org.polarsys.capella.transition.system2subsystem.context.SubSystemContextHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.traceability.SIDTraceabilityHandler;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 *
 */
public class MergeSourceConfiguration extends ExtendedTraceabilityConfiguration {

  protected class SourceReconciliationTraceabilityHandler extends ReconciliationTraceabilityHandler {

    /**
     * @param identifier
     */
    public SourceReconciliationTraceabilityHandler(String identifier) {
      super(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeBlockArchitecture(BlockArchitecture source, BlockArchitecture target, IContext context, LevelMappingTraceability map) {
      super.initializeBlockArchitecture(source, target, context, map);

      //We add a mapping between both root components
      Component sourceComponent = BlockArchitectureExt.getOrCreateSystem(source);
      Component targetComponent = BlockArchitectureExt.getOrCreateSystem(target);
      if ((sourceComponent != null) && (targetComponent != null)) {
        if ((!map.contains(sourceComponent)) && (!map.contains(targetComponent))) {
          addMapping(map, sourceComponent, targetComponent, context);
        }
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeRootMappings(IContext context) {
      super.initializeRootMappings(context);
      addMappings(ContextHelper.getSourceProject(context), ContextHelper.getTransformedProject(context), context);
      addMappings(ContextHelper.getSourceEngineering(context), ContextHelper.getTransformedEngineering(context), context);
      ModelInformation srcInfo = SubSystemContextHelper.getSourceModelInformation(context);
      if(srcInfo != null) {
        ModelInformation transformedInfo = SubSystemContextHelper.getTransformedModelInformation(context);
        if(transformedInfo != null) {
          addMappings(srcInfo, transformedInfo, context);
        }
      }
    }
  }

  protected class SourceSIDTraceabilityHandler extends SIDTraceabilityHandler {
    /**
     * @param identifier
     */
    public SourceSIDTraceabilityHandler(String identifier) {
      super(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeRootMappings(IContext context) {
      super.initializeRootMappings(context);
      initializeMappings(ContextHelper.getSourceProject(context), ContextHelper.getTransformedProject(context), context);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getExtensionIdentifier(IContext context) {
    return ISchemaConstants.SOURCE_TRACEABILITY_CONFIGURATION;
  }
  
  @Override
  protected void initHandlers(IContext context) {
    addHandler(context, new SourceReconciliationTraceabilityHandler(getIdentifier(context)));
    addHandler(context, new SourceSIDTraceabilityHandler(getIdentifier(context)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean useHandlerForAttachment(EObject source_p, EObject target_p, ITraceabilityHandler handler_p, IContext context_p) {

    //We disable Reconciliation for attachment
    if (handler_p instanceof ReconciliationTraceabilityHandler) {
      return false;
    }

    return super.useHandlerForAttachment(source_p, target_p, handler_p, context_p);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean useHandlerForTracedElements(EObject source_p, ITraceabilityHandler handler_p, IContext context_p) {

    boolean result = super.useHandlerForTracedElements(source_p, handler_p, context_p);
    if (result) {

      //We disable RealizationLinks for SystemEngineering and BlockArchitecture
      if (handler_p instanceof RealizationLinkTraceabilityHandler) {
        if (source_p instanceof SystemEngineering) {
          result = false;
        } else if (source_p instanceof BlockArchitecture) {
          result = false;
        }
      }
    }

    return result;

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean useHandlerForSourceElements(EObject source_p, ITraceabilityHandler handler_p, IContext context_p) {

    boolean result = super.useHandlerForSourceElements(source_p, handler_p, context_p);
    if (result) {

      //We disable RealizationLinks for SystemEngineering and BlockArchitecture
      if (handler_p instanceof RealizationLinkTraceabilityHandler) {
        if (source_p instanceof SystemEngineering) {
          result = false;
        } else if (source_p instanceof BlockArchitecture) {
          result = false;
        }
      }
    }

    return result;

  }

}
