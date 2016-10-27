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
package org.polarsys.capella.transition.system2subsystem.tests.util;

import java.util.Arrays;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.polarsys.capella.common.data.activity.InputPin;
import org.polarsys.capella.common.data.activity.OutputPin;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.data.capellacore.Feature;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.ComponentPort;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.interaction.InteractionPackage;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTestActivator;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class ModelNormalizer extends AbstractHandler {

  public static final Predicate<EStructuralFeature> DEFAULT_SORT_PREDICATE = Predicates.not(Predicates.in(Arrays
      .asList(new EStructuralFeature[] { CapellacorePackage.Literals.ABSTRACT_MODELLING_STRUCTURE__OWNED_ARCHITECTURES,
          InteractionPackage.Literals.SCENARIO__OWNED_MESSAGES })));

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    CompoundCommand cc = new CompoundCommand("Normalize Model");
    Resource res = ((EObject) ((IStructuredSelection) HandlerUtil.getCurrentSelection(event)).getFirstElement())
        .eResource();
    EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(res.getResourceSet());
    if (domain == null) {
      Shell shell = HandlerUtil.getActiveShell(event);
      ErrorDialog.openError(shell, "Cannot Execute Command", "Could not aquire editing domain",
          new Status(IStatus.ERROR, System2SubsystemTestActivator.getDefault().getBundle().getSymbolicName(),
              "Could not find editing domain"));
      return null;
    }

    /**
     * Rename elements
     */
    for (TreeIterator<EObject> it = res.getAllContents(); it.hasNext();) {
      EObject next = it.next();
      EditingDomain ed = AdapterFactoryEditingDomain.getEditingDomainFor(next);

      if (next instanceof FunctionalExchange) {
        AbstractFunction sf = (AbstractFunction) ((FunctionalExchange) next).getSourceFunctionOutputPort().eContainer();
        AbstractFunction tf = (AbstractFunction) ((FunctionalExchange) next).getTargetFunctionInputPort().eContainer();
        cc.append(SetCommand.create(ed, next, ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME,
            sf.getName() + "_" + tf.getName()));
      }
      if (next instanceof AbstractFunction) {
        int index = 1;
        for (InputPin p : ((AbstractFunction) next).getInputs()) {
          cc.append(SetCommand.create(ed, p, ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME,
              ((AbstractFunction) next).getName() + "_" + "FIP" + index++));
        }
        index = 1;
        for (OutputPin p : ((AbstractFunction) next).getOutputs()) {
          cc.append(SetCommand.create(ed, p, ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME,
              ((AbstractFunction) next).getName() + "_" + "FOP" + index++));
        }
      }
      if (next instanceof Component) {
        int index = 1;
        for (Feature f : ((Component) next).getOwnedFeatures()) {
          if (f instanceof ComponentPort) {
            cc.append(SetCommand.create(ed, f, ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME,
                ((Component) next).getName() + "_C" + index++));
          }
        }
      }
    }

    domain.getCommandStack().execute(cc);
    return null;
  }

}
