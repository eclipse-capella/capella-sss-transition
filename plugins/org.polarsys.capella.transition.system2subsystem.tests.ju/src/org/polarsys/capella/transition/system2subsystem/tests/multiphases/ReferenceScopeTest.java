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

import static com.google.common.base.Predicates.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.polarsys.capella.core.data.cs.Interface;
import org.polarsys.capella.core.data.cs.PhysicalLink;
import org.polarsys.capella.core.data.cs.PhysicalPort;
import org.polarsys.capella.core.data.fa.ComponentPort;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.capella.core.data.information.ExchangeItemElement;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class ReferenceScopeTest {

  /**
   * @return the reference scope under test, computed from the initial reference scope.
   */
  public Collection<Object> getActualReferenceScope() {
    return null;
  }

  @SuppressWarnings("unchecked")
  public <T> Collection<T> getActualReferenceScope(Class<T> clazz) {
    return (Collection<T>) Collections2.filter(getActualReferenceScope(), instanceOf(clazz));
  }

  private Object[] getSelectedNodePhysicalComponents() {
    return new EObject[0];
  }

  /**
   * The selected Node Physical Components shall be included in the reference scope.
   */
  @Test
  public void selectedNodePhysicalComponents() {
    assertThat(getActualReferenceScope(), hasItems(getSelectedNodePhysicalComponents()));
  }

  /**
   * The Reference Scope shall also contain the children Physical Components of the Physical Components already present
   * in the Reference Scope.
   */
  @Test
  public void childPhysicalComponents() {
    for (PhysicalComponent pc : getActualReferenceScope(PhysicalComponent.class)) {
      for (Object child : pc.getOwnedPhysicalComponents()) {
        assertThat(getActualReferenceScope(), hasItem(child));
      }
    }
  }

  /**
   * The Reference Scope shall also contain the Physical Components that are deployed on any Physical Component already
   * present in the Reference Scope.
   */
  @Test
  public void deployedPhysicalComponents() {
    for (PhysicalComponent pc : getActualReferenceScope(PhysicalComponent.class)) {
      for (Object deployed : pc.getDeployedPhysicalComponents()) {
        assertThat(getActualReferenceScope(), hasItem(deployed));
      }
    }
  }

  /**
   * The Reference Scope shall also contain the Physical Functions allocated on the Behavior Physical Components already
   * present in the Reference Scope.
   */
  @Test
  public void allocatedPhysicalFunctions() {
    for (PhysicalComponent pc : getActualReferenceScope(PhysicalComponent.class)) {
      for (Object allocatedFunction : pc.getAllocatedPhysicalFunctions()) {
        assertThat(getActualReferenceScope(), hasItem(allocatedFunction));
      }
    }
  }

  /**
   * For each Node Physical Component of the Reference Scope, the Physical Ports shall be added to the Reference Scope.
   */
  @Test
  public void physicalPorts() {
    for (PhysicalComponent pc : getActualReferenceScope(PhysicalComponent.class)) {
      for (Object physicalPort : pc.getContainedPhysicalPorts()) {
        assertThat(getActualReferenceScope(), hasItem(physicalPort));
      }
    }
  }

  /**
   * For each physical port of the reference Scope, the involved physical links shall be added to the Reference Scope.
   */
  @Test
  public void physicalLinks() {
    for (PhysicalPort port : getActualReferenceScope(PhysicalPort.class)) {
      for (Object physicalLink : port.getInvolvedLinks()) {
        assertThat(getActualReferenceScope(), hasItem(physicalLink));
      }
    }
  }

  /**
   * For each Physical Link of the Reference Scope, the Physical Link Categories shall be added to the Reference Scope.
   */
  @Test
  public void physicalLinkCategory() {
    for (PhysicalLink link : getActualReferenceScope(PhysicalLink.class)) {
      for (Object category : link.getCategories()) {
        assertThat(getActualReferenceScope(), hasItem(category));
      }
    }
  }

  /**
   * For each Behavior Physical Component of the Reference Scope, the Component Ports and [...] shall be added to the
   * Reference Scope.
   */
  @Test
  public void componentPorts() {
    for (PhysicalComponent pc : getActualReferenceScope(PhysicalComponent.class)) {
      for (Object cp : pc.getContainedComponentPorts()) {
        assertThat(getActualReferenceScope(), hasItem(cp));
      }
    }
  }

  /**
   * For each Behavior Physical Component of the Reference Scope, the [...] and connected Component Exchanges shall be
   * added to the Reference Scope.
   */
  @Test
  public void componentExchange() {
    for (ComponentPort cp : getActualReferenceScope(ComponentPort.class)) {
      for (Object ce : cp.getComponentExchanges()) {
        assertThat(getActualReferenceScope(), hasItem(ce));
      }
    }
  }

  /**
   * For each Behavior Physical Component the implemented and used Interfaces shall be added to the Reference Scope.
   */
  @Test
  public void behaviourComponentImplementedAndUsedInterfaces() {
    for (PhysicalComponent pc : getActualReferenceScope(PhysicalComponent.class)) {
      if (pc.getNature() == PhysicalComponentNature.BEHAVIOR) {
        for (Object implementedInterface : pc.getImplementedInterfaces()) {
          assertThat(getActualReferenceScope(), hasItem(implementedInterface));
        }
        for (Object usedInterface : pc.getUsedInterfaces()) {
          assertThat(getActualReferenceScope(), hasItem(usedInterface));
        }
      }
    }
  }

  /**
   * For each Component Port the provided and required Interfaces shall be added to the Reference Scope.
   */
  @Test
  public void componentPortRequiredAndProvidedInterfaces() {
    for (ComponentPort cp : getActualReferenceScope(ComponentPort.class)) {
      for (Object requiredInterface : cp.getRequiredInterfaces()) {
        assertThat(getActualReferenceScope(), hasItem(requiredInterface));
      }
      for (Object providedInterface : cp.getProvidedInterfaces()) {
        assertThat(getActualReferenceScope(), hasItem(providedInterface));
      }
    }
  }

  /**
   * For each Interface, its exchange items shall be added to the reference scope
   */
  public void interfaceExchangeItems() {
    for (Interface iface : getActualReferenceScope(Interface.class)) {
      for (Object exchangeItem : iface.getExchangeItems()) {
        assertThat(getActualReferenceScope(), hasItem(exchangeItem));
      }
    }
  }

  /**
   * For each exchange item, its exchange item elements shall be added to the reference scope
   * 
   * @return
   */
  @Test
  public void exchangeItemElements() {
    for (ExchangeItem ei : getActualReferenceScope(ExchangeItem.class)) {
      for (Object exchangeItemElement : ei.getOwnedElements()) {
        assertThat(getActualReferenceScope(), hasItem(exchangeItemElement));
      }
    }
  }

  /**
   * For each exchange item element, its type shall be added to the reference scope
   * 
   * @return
   */
  @Test
  public void exchangeItemElementType() {
    for (ExchangeItemElement eie : getActualReferenceScope(ExchangeItemElement.class)) {
      assertThat(getActualReferenceScope(), hasItem((Object) eie.getType()));
    }
  }

  private Predicate<? super EObject> isNodePhysicalComponent() {
    return new Predicate<EObject>() {
      @Override
      public boolean apply(EObject arg0) {
        return arg0 instanceof PhysicalComponent
            && ((PhysicalComponent) arg0).getNature() == PhysicalComponentNature.NODE;
      }
    };
  }

  private Predicate<? super EObject> isBehaviorPhysicalComponent() {
    return new Predicate<EObject>() {
      @Override
      public boolean apply(EObject arg0) {
        return arg0 instanceof PhysicalComponent
            && ((PhysicalComponent) arg0).getNature() == PhysicalComponentNature.BEHAVIOR;
      }
    };
  }

}