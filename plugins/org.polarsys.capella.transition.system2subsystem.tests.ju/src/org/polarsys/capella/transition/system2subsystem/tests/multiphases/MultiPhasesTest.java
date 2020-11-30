/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests.multiphases;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.model.handler.command.CapellaResourceHelper;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt.Type;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.TraceabilityArchitectureSID;
import org.polarsys.capella.transition.system2subsystem.tests.TraceabilitySID;

public abstract class MultiPhasesTest extends System2SubsystemTest {

  private boolean withLibrary = false;
  
  public MultiPhasesTest() {
    setKind(Kind.MULTI_PHASES);
  }
  
  public MultiPhasesTest(boolean withLibrary) {
    this();
    this.withLibrary = withLibrary;
  }

  @Override
  public List<String> getRequiredTestModels() {
    if (withLibrary) {
      return Arrays.asList("Project_test_01", "output_lib"); //$NON-NLS-1$ //$NON-NLS-2$
    }
    return super.getRequiredTestModels();
  }
  
  @Override
  protected String getOutputModelPlatformURIString() {
    if (withLibrary) {
      return "/output_lib/output_lib."+CapellaResourceHelper.CAPELLA_MODEL_FILE_EXTENSION;
    }
    return super.getOutputModelPlatformURIString();
  }
  
  @Override
  protected TraceabilitySID createTraceability() {
    return new TraceabilityArchitectureSID();
  }

  protected EObject retrieveTargetElement(String id, Type architectureType_p) {
    BlockArchitectureExt.Type currentArchitecture = ((TraceabilityArchitectureSID) traceability).getArchitecture();

    try {
      ((TraceabilityArchitectureSID) traceability).setArchitecture(architectureType_p);
      EObject source = traceability.getTracedObject(id);
      assertTrue(source != null);
      return source;
    } finally {
      ((TraceabilityArchitectureSID) traceability).setArchitecture(currentArchitecture);
    }
  }
}
