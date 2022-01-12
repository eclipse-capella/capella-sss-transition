/*******************************************************************************
 * Copyright (c) 2006, 2019 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.handlers.traceability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.common.mdsofa.common.constant.ICommonConstants;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.transition.common.handlers.session.SessionHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.traceability.LinkTraceabilityHandler;
import org.polarsys.capella.transition.system2subsystem.rules.fa.FunctionalChainInvolvementRule;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 *
 */
public class SIDTraceabilityHandler extends LinkTraceabilityHandler {

  /**
   * The name of the property which holds the transformation UID
   */
  public static final EAttribute PROPERTY_SID = ModellingcorePackage.Literals.MODEL_ELEMENT__SID;

  public EAttribute getAttribute(IContext context_p) {
    return PROPERTY_SID;
  }

  public SIDTraceabilityHandler(String identifier_p) {
    super(identifier_p);
  }

  @Override
  protected List<EObject> getSourceAttachments(EObject targetElement_p, IContext context_p) {
    List<EObject> elements = new ArrayList<EObject>();
    EObject result = null;
    if (targetElement_p instanceof CapellaElement) {
      for (String id : getSourceIds(targetElement_p, context_p)) {
        result = SessionHandlerHelper.getInstance(context_p).getEObjectFromId(id, context_p);
        if (result != null) {
          elements.add(result);
        }
      }
    }

    return elements;
  }

  protected void fixIssue56FunctionalChainInvolvmentLinkId(FunctionalChainInvolvementLink link) {
    if (link.getInvolved() instanceof FunctionalExchange) {
      FunctionalExchange exchange = (FunctionalExchange) link.getInvolved();
      if (exchange.getSid() != null && exchange.getSid().startsWith(FunctionalChainInvolvementRule.ID_FAKE_FUNCTIONAL_EXCHANGE)) {
        if (link.getSid() != null && link.getSid().startsWith(FunctionalChainInvolvementRule.ID_FAKE_FUNCTIONAL_CHAIN_INVOLVEMENT) && !link.getSid().equals(link.getId())) {
            link.setId(link.getSid());
        }
      }
    }
  }
  
  protected List<String> getSourceIds(EObject targetElement_p, IContext context_p) {
    List<String> ids = new ArrayList<String>();

    if (targetElement_p instanceof CapellaElement) {
      EAttribute attribute = getAttribute(context_p);

      // Issue #56, the InvolvementLink doesn't have a correct Id.
      if (targetElement_p instanceof FunctionalChainInvolvementLink) {
        fixIssue56FunctionalChainInvolvmentLinkId((FunctionalChainInvolvementLink) targetElement_p);
      }

      String propertyValue = (String) targetElement_p.eGet(attribute);
      propertyValue = propertyValue == null ? ICommonConstants.EMPTY_STRING : propertyValue;
      String values[] = propertyValue.split(";");
      for (String value : values) {
        if ((value != null) && (value.length() > 0)) {
          ids.add(value);
        }
      }
    }
    return ids;
  }

  @Override
  public void attachTraceability(EObject sourceElement_p, EObject targetElement_p, IContext context_p) {
    EAttribute attribute = getAttribute(context_p);

    if (targetElement_p != null && targetElement_p.eClass().getEAllAttributes().contains(attribute)) { // we allow
                                                                                                       // transformation
                                                                                                       // one to nothing
      createAttachment(sourceElement_p, targetElement_p, context_p);
    }
  }

  protected void createAttachment(EObject sourceElement_p, EObject targetElement_p, IContext context_p) {
    EAttribute attribute = getAttribute(context_p);

    List<String> values = new ArrayList<String>();
    String propertyValue = (String) targetElement_p.eGet(attribute);
    if ((propertyValue != null) && (propertyValue.length() > 0)) {
      values.addAll(Arrays.asList(propertyValue.split(";")));
    }

    // Retrieve SID from sourceElement or ID if none
    List<String> ids = getSourceIds(sourceElement_p, context_p);
    if (ids.size() == 0) {
      String id = SessionHandlerHelper.getInstance(context_p).getId(sourceElement_p, context_p);
      ids.add(id);
      if (sourceElement_p.eGet(attribute) == null) {
        sourceElement_p.eSet(attribute, id);
      }
    }

    addMappings(sourceElement_p, targetElement_p, context_p);

    for (String id : ids) {
      if (!values.contains(id)) {
        values.add(id);
      } else {
        // return already attached !
        return;
      }
    }

    // Sort the sid string according to order of source elements
    values.sort(new Comparator<String>() {
      public int compare(String id1, String id2) {
        EObject eObjectFromId1 = SessionHandlerHelper.getInstance(context_p).getEObjectFromId(id1, context_p);
        EObject eObjectFromId2 = SessionHandlerHelper.getInstance(context_p).getEObjectFromId(id2, context_p);
        if (eObjectFromId1.eContainer() != null && eObjectFromId2.eContainer() != null
            && eObjectFromId1.eContainer() == eObjectFromId2.eContainer()) {
          Object obj = eObjectFromId1.eContainer().eGet(eObjectFromId1.eContainmentFeature());
          if (obj instanceof List) {
            return ((List) obj).indexOf(eObjectFromId1) < ((List) obj).indexOf(eObjectFromId2) ? -1 : 1;
          }
        }
        return 0;
      }
    });

    String result = ICommonConstants.EMPTY_STRING;
    int i = 0;
    for (String value : values) {
      if ((value != null) && (value.length() > 0)) {
        result += value;
        i++;
        if (i < values.size()) {
          result += ";";
        }
      }
    }
    targetElement_p.eSet(attribute, result);
  }
}
