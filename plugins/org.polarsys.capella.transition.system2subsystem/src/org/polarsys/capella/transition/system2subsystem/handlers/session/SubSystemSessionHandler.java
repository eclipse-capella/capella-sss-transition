/*******************************************************************************
 * Copyright (c) 2006, 2015 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.handlers.session;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.polarsys.capella.common.data.modellingcore.ModelElement;
import org.polarsys.capella.core.transition.common.capellaHelpers.HashMapSet;
import org.polarsys.capella.core.transition.common.handlers.session.DefaultSessionHandler;
import org.polarsys.capella.transition.system2subsystem.context.SubSystemContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 *
 */
public class SubSystemSessionHandler extends DefaultSessionHandler {

  private static final String DEFAULT_EOBJECT = "DEFAULT_EOBJECT"; //$NON-NLS-1$
  
  private static final String SID_MAP = "SID_MAP"; //$NON-NLS-1$

  /**
   * {@inheritDoc}
   */
  @Override
  public IStatus dispose(IContext context) {
    return Status.OK_STATUS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EObject getEObjectFromId(String id, IContext context) {

    EObject element = null;

    if (id != null) {
      Collection<Resource> resources = getRelatedResources(context);
      Iterator<Resource> it = resources.iterator();
      while (it.hasNext() && (element == null)) {
        Resource resource = it.next();
        element = resource.getEObject(id);
      }

      if (element == null) {
        if (!context.exists(SID_MAP)) {
          context.put(SID_MAP, createSidMap(resources));
        }
        HashMapSet<String, EObject> map = (HashMapSet<String, EObject>) context.get(SID_MAP);
        Collection<EObject> elements = map.get(id);
        if (!elements.isEmpty()) {
          element = elements.iterator().next();
        }
      }
    }
    if (element != null) {
      return element;
    }
    if (context instanceof SubSystemContext) {
      return getDefaultEObject(context);
    }
    return element;
  }

  protected HashMapSet<String, EObject> createSidMap(Collection<Resource> resources) {
    HashMapSet<String, EObject> map = new HashMapSet<String, EObject>();
    for (Resource resource : resources) {
      Iterator<EObject> childs = resource.getAllContents();
      while (childs.hasNext()) {
        EObject child = childs.next();
        if (child instanceof ModelElement) {
          String sid = ((ModelElement) child).getSid();
          if (sid != null) {
            map.put(sid, child);
          }
        }
      }
    }
    return map;
  }

  protected EObject getDefaultEObject(IContext context_p) {
    if (!context_p.exists(DEFAULT_EOBJECT)) {
      context_p.put(DEFAULT_EOBJECT, EcorePackage.Literals.ECLASS);
    }
    return (EObject) context_p.get(DEFAULT_EOBJECT);

  }
}
