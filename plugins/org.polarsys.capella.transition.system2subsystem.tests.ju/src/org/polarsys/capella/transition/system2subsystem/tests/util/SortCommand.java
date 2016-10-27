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

import java.util.ArrayDeque;
import java.util.Comparator;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.polarsys.capella.core.data.capellacore.NamedElement;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class SortCommand extends AbstractCommand {

  private final Resource resource;
  private final EObject owner;
  private final Predicate<EStructuralFeature> sort;

  public SortCommand(Resource resource_p, Predicate<EStructuralFeature> sort_p) {
    resource = resource_p;
    sort = sort_p;
    owner = null;
  }

  public SortCommand(EObject owner_p, EStructuralFeature feature) {
    sort = Predicates.equalTo(feature);
    resource = owner_p.eResource();
    owner = owner_p;
  }

  public boolean prepare() {
    return true;
  }

  @Override
  public void execute() {

    ArrayDeque<EObject> toSort = new ArrayDeque<EObject>();

    if (owner != null) {
      toSort.add(owner);
    } else {
      toSort.addAll(resource.getContents());
    }

    while (!toSort.isEmpty()) {

      EObject next = toSort.pop();

      if (owner == null) {
        toSort.addAll(next.eContents());
      }

      for (EReference ref : next.eClass().getEAllReferences()) {

        if (!ref.isDerived() && ref.isMany() && sort.apply(ref)) {

          @SuppressWarnings("unchecked")
          EList<EObject> contents = (EList<EObject>) next.eGet(ref);
          ECollections.sort(contents, new Comparator<EObject>() {
            @Override
            public int compare(EObject o1, EObject o2) {
              String n1 = EcoreUtil.getID(o1);
              String n2 = EcoreUtil.getID(o2);

              if (o1 instanceof NamedElement && ((NamedElement) o1).getName() != null) {
                n1 = ((NamedElement) o1).getName();
              }
              if (o2 instanceof NamedElement && ((NamedElement) o2).getName() != null) {
                n2 = ((NamedElement) o2).getName();
              }
              return n1.compareTo(n2);
            }
          });
        }
      }
    }

  }

  public boolean canUndo() {
    return false;
  }

  @Override
  public void redo() {
  }

}
