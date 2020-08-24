/*******************************************************************************
 * Copyright (c) 2020 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests.crossphases;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Bug565895Test extends CrossPhasesTest {
  
  /** test */
  private String pc1_ID = "3e6f14e8-983b-42dc-9d5d-0065073c0315";
  private String interface1_ID = "46c1e1ed-9058-4d0c-a500-a6e78e3bd794";
  private String exchangeItem1_ID = "71c24fa0-6769-46f1-9987-83b7c837ceb7";
  private String exchangeItem2_ID = "52bde1bf-1ae1-49f5-ab47-b7c821d12da8";
  private String comLink_ID = "529fb0fb-6e84-408b-8e5d-a6f4899cbff4";
  private String generalization_ID = "5b9716b9-4a6e-48f3-b71a-758599d0d7d8";
  
  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("test", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(pc1_ID);
  }
  
  @Override
  protected void verify() throws Exception {
    mustBeTransitioned(pc1_ID);
    mustBeTransitioned(interface1_ID);
    mustBeTransitioned(exchangeItem1_ID);
    mustBeTransitioned(exchangeItem2_ID);
    mustBeTransitioned(generalization_ID);
    mustBeTransitioned(comLink_ID);
  }
}
