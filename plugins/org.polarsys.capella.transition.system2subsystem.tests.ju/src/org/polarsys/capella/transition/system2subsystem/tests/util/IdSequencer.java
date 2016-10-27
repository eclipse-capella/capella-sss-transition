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

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.common.data.modellingcore.ModelElement;
import org.polarsys.capella.core.data.capellacore.Involvement;
import org.polarsys.capella.core.data.capellacore.KeyValue;
import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTestActivator;

public class IdSequencer extends AbstractHandler {
  static boolean _saveAfterModification = true;

  /**
   * Updates object IDs according to their order of appearance in resource.getAllContents().
   * 
   * @param res
   *          The Resource for Which IDs should be updated.
   */
  public static Command sequenceIds(final Resource res, final boolean saveAfterModification) {

    _saveAfterModification = saveAfterModification;

    AbstractCommand c = new AbstractCommand("Sequence IDs") {
      final Map<EObject, String> oldIDs = new HashMap<EObject, String>();

      @Override
      public void redo() {
        execute();
      }

      public void undo() {
        try {
          for (Map.Entry<EObject, String> entry : oldIDs.entrySet()) {
            EcoreUtil.setID(entry.getKey(), entry.getValue());
            ((XMLResource) entry.getKey().eResource()).setID(entry.getKey(), entry.getValue());
          }
        } finally {
          oldIDs.clear();
        }
      }

      private void updateId(EObject e, String id) {
        ((XMLResource) e.eResource()).setID(e, id);
        EcoreUtil.setID(e, id);
      }

      @Override
      public void execute() {

        // first pass, set randomly generated IDs on all objects
        for (TreeIterator<EObject> it = res.getAllContents(); it.hasNext();) {
          EObject next = it.next();
          oldIDs.put(next, EcoreUtil.getID(next));
          updateId(next, EcoreUtil.generateUUID());
        }

        // second pass, now use the predictable IDs
        for (TreeIterator<EObject> it = res.getAllContents(); it.hasNext();) {
          EObject next = it.next();
          StringBuilder builder = new StringBuilder("[" + next.eClass().getName() + "]");
          getStructuralId(next, builder);
          String newId = builder.toString();

          // something breaks if we don't remove whitespace
          newId = newId.replaceAll("\\s+", "");

          // avoid collisions by adding an incremental integer if required
          EObject collision = null;

          collision = ((XMLResource) next.eResource()).getEObject(newId);
          if (collision != null) {
            int i = 0;
            String newIdTmp = "";
            do {
              newIdTmp = newId + "::" + i;
              i = i + 1;
            } while (((XMLResource) next.eResource()).getEObject(newIdTmp) != null);
            newId = newIdTmp;
          }

          updateId(next, newId);
        }

        if (_saveAfterModification) {
          try {
            res.save(Collections.EMPTY_MAP);
          } catch (IOException e) {
            System2SubsystemTestActivator.getDefault().getLog()
                .log(new Status(IStatus.ERROR, System2SubsystemTestActivator.PLUGIN_ID, e.getMessage(), e));
          }
        }

      }

      private void getStructuralId(EObject next, StringBuilder builder) {

        if (next.eContainer() != null) {
          getStructuralId((ModelElement) next.eContainer(), builder);
        }

        if (builder.length() > 0) {
          builder.append("::");
        }

        if (next instanceof AbstractNamedElement) {
          AbstractNamedElement named = (AbstractNamedElement) next;
          if (named.getName() != null && named.getName().length() > 0) {
            builder.append(named.getName());
          } else {
            InternalEObject e = (InternalEObject) next.eContainer();
            builder.append(e.eURIFragmentSegment(next.eContainingFeature(), next));
          }
        } else if (next instanceof AbstractTrace) {
          AbstractTrace at = (AbstractTrace) next;
          builder.append("*trace*");
          if (at.getSourceElement() instanceof NamedElement) {
            builder.append(((NamedElement) at.getSourceElement()).getName());
          }
          builder.append("*to*");
          if (at.getTargetElement() instanceof NamedElement) {
            builder.append(((NamedElement) at.getTargetElement()).getName());
          }
        } else if (next instanceof KeyValue) {
          builder.append("*keyvalue*" + ((KeyValue) next).getKey());
        } else if (next instanceof Involvement) {
          Involvement involvement = (Involvement) next;
          builder.append("*to*");
          if (involvement.getInvolved() instanceof NamedElement) {
            builder.append(((AbstractNamedElement) (involvement.getInvolved())).getName());
          }
        }

      }

      public boolean prepare() {
        return true;
      }

    };

    return c;
  }

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IStructuredSelection sel = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
    EObject e = (EObject) sel.getFirstElement();
    EditingDomain ed = AdapterFactoryEditingDomain.getEditingDomainFor(e);
    Resource res = (((EObject) sel.getFirstElement())).eResource();
    ed.getCommandStack().execute(sequenceIds(res, _saveAfterModification));
    return null;
  }

}
