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
package org.polarsys.capella.transition.system2subsystem.handlers.attachment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A class allowing to define set of elements considered as equals.
 * 
 * Example:
 * 
 * add(A), add(B), add(C), getSets will retrieve three independent sets {A}, {B} and {C}
 * 
 * merge(A,B), getSets will retrieve two sets {A,B} and {C}
 * 
 * merge(B,C), getSets will retrieve one set {A,B,C} (as C is merged with B, it is also merged with A)
 */
public class SubSets<T> {
  Collection<Set<T>> map = new ArrayList<Set<T>>();

  /**
   * Add an element. This method will add the element in a new subset if not yet added.
   */
  public void add(T element) {
    Optional<Set<T>> set = map.stream().filter(x -> x.contains(element)).findAny();
    if (!set.isPresent()) {
      HashSet<T> s = new HashSet<T>();
      s.add(element);
      map.add(s);
    }
  }

  /**
   * Mark both elements to be equals.
   * 
   * If one of element is already in a set, add the other element into it.
   * 
   * If both elements are in different sets, merge them as a unitary set
   * 
   * If none are present, add them both in a new subset.
   */
  public void merge(T element1, T element2) {
    Collection<T> containingSource = map.stream().filter(x -> x.contains(element1)).findAny().orElse(null);
    Collection<T> containingTarget = map.stream().filter(x -> x.contains(element2)).findAny().orElse(null);

    if (containingSource != null && containingTarget == null) {
      containingSource.add(element2);

    } else if (containingSource == null && containingTarget != null) {
      containingTarget.add(element1);

    } else if (containingSource != null && containingTarget != null && containingSource != containingTarget) {
      containingSource.addAll(containingTarget);
      map.remove(containingTarget);

    } else {
      HashSet<T> both = new HashSet<T>();
      both.add(element1);
      both.add(element2);
      map.add(both);
    }
  }

  public Collection<Set<T>> getSets() {
    return map;
  }
}
