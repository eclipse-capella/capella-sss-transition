/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.tests.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;
import org.polarsys.capella.core.model.handler.command.CapellaResourceHelper;
import org.polarsys.capella.core.transition.common.context.TransitionContext;
import org.polarsys.capella.test.framework.api.BasicTestCase;
import org.polarsys.capella.transition.system2subsystem.handlers.session.SubSystemSessionHandler;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class SubSystemSessionHandlerTest extends BasicTestCase {

  private static final String MODEL = "pvmtmodel";
  private static final String PV_SID = "_888rIE1HEeyJnY6sGJmWlA";
  
  @Override
  public void test() throws Exception {

    //Ensure that our test model contains an AFM as semantic resource
    assertTrue(getSession(MODEL).getSemanticResources().stream().map(r -> r.getURI().fileExtension())
        .collect(Collectors.toList()).contains(CapellaResourceHelper.AFM_FILE_EXTENSION));

    SubSystemSessionHandler handler = new SubSystemSessionHandler() {
      @Override
      protected Collection<Resource> getRelatedResources(IContext context) {
        return getSemanticResourcesWithAFMFirst(getSession(MODEL).getSemanticResources());
      }
    };
    assertNotNull(handler.getEObjectFromId(PV_SID, new TransitionContext()));
  }

  // We retrieve the semantic resources (.afm, .capella) sorted by extensions. (afm first!)
  protected List<Resource> getSemanticResourcesWithAFMFirst(Collection<Resource> semanticResources) {
    List<Resource> result = new ArrayList<>(semanticResources);
    Collections.sort(result, (o1, o2) -> o1.getURI().fileExtension().compareTo(o2.getURI().fileExtension()));
    return result;
  }

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList(MODEL);
  }
}
