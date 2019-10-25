/*******************************************************************************
 * Copyright (c) 2006, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.crossphases.handlers.attachment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.transition.common.capellaHelpers.HashMapSet;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.options.OptionsHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.traceability.TraceabilityHandlerHelper;
import org.polarsys.capella.core.transition.common.rules.AbstractRule;
import org.polarsys.capella.core.transition.system.handlers.attachment.CapellaDefaultAttachmentHandler;
import org.polarsys.capella.transition.system2subsystem.crossphases.constants.IOptionsConstants;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IPremise;

/**
 * 
 */
public class CrossPhasesAttachmentHelper extends CapellaDefaultAttachmentHandler {

  public static final String CROSS_PHASES_ATTACHMENT_HELPER = "CrossPhasesAttachment"; //$NON-NLS-1$

  protected static final String CROSS_PHASES_ATTACHMENT_MAP = "CrossPhasesAttachmentMap"; //$NON-NLS-1$
  protected static final String CROSS_PHASES_ATTACHMENT_REVERSE_MAP = "CrossPhasesAttachmentReverseMap"; //$NON-NLS-1$

  public void computeRelatedComponent(Component element, IContext context_p) {
    Collection<EObject> transfoSources = (Collection<EObject>) context_p.get(ITransitionConstants.TRANSITION_SOURCES);

    EObject relatedComponent = element;
    Collection<Part> sourceAndAncestors = getSourceAndAncestors(transfoSources);

    if (element != null) {

      boolean shouldMerge = shouldMerge(element, context_p);

      // if element is an ancestor of transfoSources, we don't merge them into another component
      if (shouldMerge) {
        shouldMerge(element, context_p);
        for (Part part : element.getRepresentingParts()) {
          if (sourceAndAncestors.contains(part)) {
            shouldMerge = false;
          }
        }
      }

      // if element is a brother of an ancestor of transfoSources, we don't merge them into another component
      if (shouldMerge) {
        for (Part part : element.getRepresentingParts()) {
          if (shouldMerge) {
            for (Part source : sourceAndAncestors) {
              if (shouldMerge) {
                if (ComponentExt.isBrothers(part, source)) {
                  shouldMerge = false;
                }
              }
            }
          }
        }
      }

      // Otherwise, we find the related component from ancestors
      if (shouldMerge) {

        boolean componentFound = false;
        for (Part partElement : element.getRepresentingParts()) {
          if (!componentFound) {
            for (Part part : ComponentExt.getBestPartAncestors(partElement)) {
              if (!componentFound) {
                if (part.getAbstractType() instanceof Component) {
                  Component type = (Component) part.getAbstractType();
                  Component cache2 = getRelatedComponent(type, context_p);
                  if (cache2 != null) {
                    relatedComponent = cache2;
                    componentFound = true;
                    break;
                  }
                }
              }
            }
          }
        }
      }
    }

    // Register the default component
    if (relatedComponent != null) {
      CrossPhasesAttachmentHelper.getInstance(context_p).setRelatedComponent(element, (Component) relatedComponent, context_p);
    }

  }

  /**
   * @param element_p
   * @param context_p
   * @return
   */
  protected boolean shouldMerge(Component element_p, IContext context_p) {
    return (element_p instanceof PhysicalComponent)
           || OptionsHandlerHelper.getInstance(context_p).getBooleanValue(context_p, IOptionsConstants.SYSTEM2SUBSYSTEM_CROSSPHASES_PREFERENCES,
               IOptionsConstants.COMPONENT_MERGE, IOptionsConstants.COMPONENT_MERGE__DEFAULT_VALUE);
  }

  /**
   * @param transfoSources_p
   * @return
   */
  private Collection<Part> getSourceAndAncestors(Collection<EObject> transfoSources_p) {
    Collection<Part> sources = new HashSet<Part>();

    for (EObject eObject : transfoSources_p) {
      if (eObject instanceof Part) {
        sources.add((Part) eObject);
        sources.addAll(ComponentExt.getPartAncestors((Part) eObject));

      } else if (eObject instanceof Component) {
        for (Part part : ((Component) eObject).getRepresentingParts()) {
          sources.add(part);
          sources.addAll(ComponentExt.getPartAncestors(part));
        }
      }
    }

    return sources;
  }

  /**
   * @param element_p
   * @param transfoSources_p
   * @return
   */
  private boolean isBrotherWithSource(Component element_p, Collection<EObject> transfoSources_p) {

    Collection<Part> sources = new HashSet<Part>();

    for (EObject eObject : transfoSources_p) {
      if (eObject instanceof Part) {
        sources.add((Part) eObject);

      } else if (eObject instanceof Component) {
        for (Part part : ((Component) eObject).getRepresentingParts()) {
          sources.add(part);
        }
      }
    }

    for (Part part : element_p.getRepresentingParts()) {
      for (Part source : sources) {
        if (ComponentExt.isBrothers(part, source)) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * @param element_p
   * @param transfoSources_p
   * @return
   */
  private boolean isAncestorOf(Component element_p, Collection<EObject> transfoSources_p) {
    for (EObject source : transfoSources_p) {
      if (source instanceof Part) {
        for (Part part : ComponentExt.getPartAncestors((Part) source)) {
          if (element_p.equals(part.getAbstractType())) {
            return true;
          }
        }
      } else if (source instanceof Component) {
        for (Part partSource : ((Component) source).getRepresentingParts()) {
          if (partSource instanceof Part) {
            for (Part part : ComponentExt.getPartAncestors(partSource)) {
              if (element_p.equals(part.getAbstractType())) {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  public static CrossPhasesAttachmentHelper getInstance(IContext context_p) {
    CrossPhasesAttachmentHelper handler = (CrossPhasesAttachmentHelper) context_p.get(CROSS_PHASES_ATTACHMENT_HELPER);
    if (handler == null) {
      handler = new CrossPhasesAttachmentHelper();
      handler.init(context_p);
      context_p.put(CROSS_PHASES_ATTACHMENT_HELPER, handler);
    }
    return handler;
  }

  public Component getRelatedComponent(Component source, IContext context_p) {
    Component cache = (Component) getMap(context_p).get(source);
    if (cache == null) {
      computeRelatedComponent(source, context_p);
      cache = (Component) getMap(context_p).get(source);
    }
    if (cache == null) {
      cache = source;
    }
    computeRelatedComponent(source, context_p);
    return cache;
  }

  public void setRelatedComponent(Component source, Component target, IContext context_p) {
    getMap(context_p).put(source, target);
    getReverseMap(context_p).put(target, source);
  }

  @SuppressWarnings("unchecked")
  protected HashMapSet<EObject, EObject> getReverseMap(IContext context_p) {
    if (context_p.get(CROSS_PHASES_ATTACHMENT_REVERSE_MAP) == null) {
      context_p.put(CROSS_PHASES_ATTACHMENT_REVERSE_MAP, new HashMapSet<EObject, EObject>());
    }
    return (HashMapSet<EObject, EObject>) context_p.get(CROSS_PHASES_ATTACHMENT_REVERSE_MAP);
  }

  @SuppressWarnings("unchecked")
  public Map<EObject, EObject> getMap(IContext context_p) {
    if (context_p.get(CROSS_PHASES_ATTACHMENT_MAP) == null) {
      context_p.put(CROSS_PHASES_ATTACHMENT_MAP, new HashMap<EObject, EObject>());
    }
    return (Map<EObject, EObject>) context_p.get(CROSS_PHASES_ATTACHMENT_MAP);
  }

  public void clear(IContext context) {
    getMap(context).clear();
    getReverseMap(context).clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IStatus init(IContext context_p) {
    return Status.OK_STATUS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IStatus dispose(IContext context_p) {
    return Status.OK_STATUS;
  }

  public void attachRelatedElements(EObject source_p, EObject target_p, EReference feature_p, IContext context_p) {
    for (EObject traced : retrieveReferenceAsList(source_p, feature_p)) {
      if (traced instanceof Component) {
        traced = getRelatedComponent((Component) traced, context_p);
      }
      for (EObject related : TraceabilityHandlerHelper.getInstance(context_p).retrieveTracedElements(traced, context_p)) {
        attachElementByReference(source_p, target_p, traced, related, feature_p, feature_p);
      }
    }
  }

  public void attachRelatedOfMergedElements(EObject element_p, EObject result_p, EReference reference_p, IContext context_p) {
    Collection<EObject> values = getReverseMap(context_p).get(element_p);
    if ((values != null) && (values.size() > 0)) {
      for (EObject value : values) {
        if ((value != null) && !element_p.equals(value)) {
          attachRelatedElements(value, result_p, reference_p, context_p);
        }
      }
    }
  }

  public void addPremicesRelatedOfMergedComponent(EObject element_p, AbstractRule rule, EReference feature, ArrayList<IPremise> needed_p, IContext context_p) {
    Collection<EObject> values = getReverseMap(context_p).get(element_p);
    if ((values != null) && (values.size() > 0)) {
      for (EObject value : values) {
        if ((value != null) && !element_p.equals(value)) {
          needed_p.addAll(rule.createDefaultPrecedencePremices(value, feature));
        }
      }
    }
  }

}
