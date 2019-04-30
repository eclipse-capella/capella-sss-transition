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
package org.polarsys.capella.transition.system2subsystem.multiphases.handlers.traceability.config;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.libraries.ModelInformation;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.epbs.EPBSArchitecture;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.system.handlers.traceability.ReconciliationTraceabilityHandler;
import org.polarsys.capella.core.transition.system.helpers.ContextHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.traceability.config.MergeSourceConfiguration;
import org.polarsys.capella.transition.system2subsystem.multiphases.MultiphasesContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;


/**
 *
 */
public class MultiphasesSourceConfiguration extends MergeSourceConfiguration {

  protected class MultiPhasesSourceReconciliationTraceabilityHandler extends ReconciliationTraceabilityHandler {

    public MultiPhasesSourceReconciliationTraceabilityHandler(String identifier_p) {
      super(identifier_p);
    }

    @Override
    protected void initializeBlockArchitecture(BlockArchitecture source_p, BlockArchitecture target_p, IContext context_p, LevelMappingTraceability map_p) {
      super.initializeBlockArchitecture(source_p, target_p, context_p, map_p);
      //we don't add mapping to first component (new root system component can be a sub physical component)
    }

    @Override
    protected void initializeSystemEngineering(SystemEngineering source_p, SystemEngineering target_p, IContext context_p, LevelMappingTraceability map_p) {
      addMapping(map_p, getSourceArchitecture(source_p, context_p), SystemEngineeringExt.getOwnedSystemAnalysis(target_p), context_p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeRootMappings(IContext context) {
      super.initializeRootMappings(context);
      addMappings(ContextHelper.getSourceProject(context), ContextHelper.getTransformedProject(context), context);
      addMappings(ContextHelper.getSourceEngineering(context), ((MultiphasesContext)context).getTempSystemEngineering(), context);
    }
  }

  protected BlockArchitecture getSourceArchitecture(SystemEngineering source_p, IContext context_p) {
    BlockArchitecture architecture = SystemEngineeringExt.getOwnedPhysicalArchitecture(source_p);
    Collection<EObject> selection = (Collection<EObject>) context_p.get(ITransitionConstants.TRANSITION_SOURCES);
    if (selection.size() > 0) {
      // Initialize source of transition
      EObject source = (EObject) selection.toArray()[0];
      architecture = BlockArchitectureExt.getRootBlockArchitecture(source);
      if (architecture instanceof EPBSArchitecture) {
        architecture = SystemEngineeringExt.getOwnedPhysicalArchitecture(source_p);
      }
    }
    return architecture;
  }

  @Override
  protected void initHandlers(IContext fContext_p) {
    addHandler(fContext_p, new MultiPhasesSourceReconciliationTraceabilityHandler(getIdentifier(fContext_p)));
    addHandler(fContext_p, new SourceSIDTraceabilityHandler(getIdentifier(fContext_p)));
  }
}
