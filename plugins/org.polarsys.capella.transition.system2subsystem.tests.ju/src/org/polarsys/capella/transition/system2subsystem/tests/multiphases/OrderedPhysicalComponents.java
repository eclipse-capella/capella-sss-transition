/*******************************************************************************
 * Copyright (c) 2016, 2018 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests.multiphases;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.diffmerge.generic.api.diff.IDifference;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.transition.system2subsystem.tests.util.DiffHelper;

/**
 * Test that verifies the invariant output between two multiphase transitions when two components are reordered
 */
public class OrderedPhysicalComponents extends MultiPhasesTest {

  public static final String MP_ORDERED_PA_PC_4 = "b271ea03-5360-43a3-823f-1f4aa88f83ba"; //$NON-NLS-1$
  public static final String MP_ORDERED_PA_PC_5 = "3161c9e8-a19f-49eb-98e2-ed3e867a9a08"; //$NON-NLS-1$

  public OrderedPhysicalComponents() {
    super();
  }
  
  public OrderedPhysicalComponents(boolean withLibrary) {
    super(withLibrary);
  }
  
  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(MP_ORDERED_PA_PC_4);
  }

  @Override
  public void test() throws Exception {
    
    super.test(); //Perform the transition into the output

    IFile outputModel = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(getOutputModelPlatformURIString()));
    IFile backupOriginal = DiffHelper.copyFile(outputModel);

    reorderSourcePCs();
    executeTransition();

    Collection<IDifference> differences = new DiffHelper().getDifferences(outputModel, backupOriginal);
    assertTrue("There shall have no differences when some source component are reordered", differences.isEmpty());
  }

  private void reorderSourcePCs() {
    TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(getProject());
    domain.getCommandStack().execute(new RecordingCommand(domain) {
      @Override
      protected void doExecute() {
        // Rearrange PC4 and PC5 as first components
        PhysicalComponent pc4 = (PhysicalComponent) getObject(MP_ORDERED_PA_PC_4);
        PhysicalComponent pc5 = (PhysicalComponent) getObject(MP_ORDERED_PA_PC_5);
        EList<PhysicalComponent> physicalComponents = ((PhysicalComponent) pc4.eContainer())
            .getOwnedPhysicalComponents();
        physicalComponents.move(0, pc4);
        physicalComponents.move(0, pc5);
      }
    });
  }

}
