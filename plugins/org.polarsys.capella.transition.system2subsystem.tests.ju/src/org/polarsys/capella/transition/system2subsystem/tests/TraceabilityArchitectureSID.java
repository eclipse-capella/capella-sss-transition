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
package org.polarsys.capella.transition.system2subsystem.tests;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;

/**
 * A traceability allowing to filter according to selected architecture.
 */
public class TraceabilityArchitectureSID extends TraceabilitySID {

  BlockArchitectureExt.Type type = null;

  protected boolean upperLevelAllowed = false;

  public void setArchitecture(BlockArchitectureExt.Type type) {
    this.type = type;
  }

  public BlockArchitectureExt.Type getArchitecture() {
    return type;
  }

  @Override
  public Collection<EObject> getTracedObjects(String sourceId) {
    Collection<EObject> result = new ArrayList<EObject>();
    for (EObject current : targetMap.get(sourceId)) {
      if (match(current)) {
        result.add(current);
      }
    }
    return result;
  }

  @Override
  public Collection<EObject> getSourceObjects(String sourceId) {
    Collection<EObject> result = new ArrayList<EObject>();
    for (EObject current : sourceMap.get(sourceId)) {
      if (match(current)) {
        result.add(current);
      }
    }
    return result;
  }

  private boolean match(EObject current) {
    if (type == null) {
      return true;
    }

    BlockArchitectureExt.Type currentType = BlockArchitectureExt
        .getBlockArchitectureType(BlockArchitectureExt.getRootBlockArchitecture(current));
    if (type.equals(currentType)) {
      return true;
    }
    if (isUpperLevelAllowed()) {
      if (currentType.ordinal() < type.ordinal()) {
        return true;
      }
    }
    return false;
  }

  public boolean isUpperLevelAllowed() {
    return false;
  }

  public void setUpperLevelAllowed(boolean upperLevelAllowed) {
    this.upperLevelAllowed = upperLevelAllowed;
  }

}
