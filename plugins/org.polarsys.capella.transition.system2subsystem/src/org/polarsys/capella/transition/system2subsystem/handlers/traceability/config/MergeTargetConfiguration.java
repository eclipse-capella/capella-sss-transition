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

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.libraries.ModelInformation;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.transition.common.constants.ISchemaConstants;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
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
public class MergeTargetConfiguration extends ExtendedTraceabilityConfiguration {

  protected class TargetReconciliationTraceabilityHandler extends ReconciliationTraceabilityHandler {

    public TargetReconciliationTraceabilityHandler(String identifier_p) {
      super(identifier_p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeBlockArchitecture(BlockArchitecture source_p, BlockArchitecture target_p, IContext context_p, LevelMappingTraceability map_p) {
      super.initializeBlockArchitecture(source_p, target_p, context_p, map_p);

      Component sourceComponent = BlockArchitectureExt.getOrCreateSystem(source_p);
      Component targetComponent = BlockArchitectureExt.getOrCreateSystem(target_p);
      if ((sourceComponent != null) && (targetComponent != null)) {
        if ((!map_p.contains(sourceComponent)) && (!map_p.contains(targetComponent))) {
          addMapping(map_p, sourceComponent, targetComponent, context_p);
        }
      }

      if ((sourceComponent != null) && (sourceComponent.getRepresentingParts().size() == 1)) {
        if ((targetComponent != null) && (targetComponent.getRepresentingParts().size() == 1)) {
          Part sourcePart = sourceComponent.getRepresentingParts().get(0);
          Part targetPart = targetComponent.getRepresentingParts().get(0);
          addMapping(map_p, sourcePart, targetPart, context_p);
        }
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeRootMappings(IContext context) {
      super.initializeRootMappings(context);
      addMappings(ContextHelper.getSourceProject(context), ContextHelper.getTargetProject(context), context);
      addMappings(ContextHelper.getSourceEngineering(context), getTargetEngineering(context), context);
      ModelInformation srcInfo = SubSystemContextHelper.getSourceModelInformation(context);
      if(srcInfo != null) {
        ModelInformation targetInfo = SubSystemContextHelper.getTargetModelInformation(context);
        if(targetInfo != null) {
          addMappings(srcInfo, targetInfo, context);
        }
      }
    }
  }

  protected class TargetSIDTraceabilityHandler extends SIDTraceabilityHandler {

    /**
     * @param identifier
     */
    public TargetSIDTraceabilityHandler(String identifier) {
      super(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeRootMappings(IContext context_p) {
      super.initializeRootMappings(context_p);
      EObject source = (EObject) context_p.get(ITransitionConstants.TRANSITION_SOURCE_ROOT);
      EObject target = (EObject) context_p.get(ITransitionConstants.TRANSITION_TARGET_ROOT);
      initializeMappings(source, target, context_p);
    }
    
  }

  public static EObject getTargetEngineering(IContext context) {
    //In Transition Subsystem, target engineering is the one on the subsystem project.
    return SystemEngineeringExt.getSystemEngineering(ContextHelper.getTargetProject(context));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getExtensionIdentifier(IContext context_p) {
    return ISchemaConstants.TARGET_TRACEABILITY_CONFIGURATION;
  }

  @Override
  protected void initHandlers(IContext fContext_p) {
    addHandler(fContext_p, new TargetReconciliationTraceabilityHandler(getIdentifier(fContext_p)));
    addHandler(fContext_p, new TargetSIDTraceabilityHandler(getIdentifier(fContext_p)));
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
      if (handler_p instanceof SIDTraceabilityHandler) {

        if (source_p instanceof SystemEngineering) {
          result = false;
        }
        if (source_p instanceof BlockArchitecture) {
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
        }
        if (source_p instanceof BlockArchitecture) {
          result = false;
        }
      }
    }

    return result;
  }

}
