/*******************************************************************************
 * Copyright (c) 2006, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.crossphases.rules.pa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.information.Partition;
import org.polarsys.capella.core.data.pa.AbstractPhysicalComponent;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.transition.system2subsystem.crossphases.handlers.attachment.CrossPhasesAttachmentHelper;
import org.polarsys.kitalpha.transposer.rules.handler.business.premises.ContainmentPremise;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IPremise;


public class PhysicalComponentRule extends Component2SARule {

  @Override
  protected EClass getSourceType() {
    return PaPackage.Literals.PHYSICAL_COMPONENT;
  }

protected void premicesContainement(EObject element, ArrayList<IPremise> needed) {
	  
	  ContainmentPremise<EObject> defaultPremise = createDefaultContainementPremice(element);
	  if (defaultPremise != null) {
		  needed.add(defaultPremise);

		  IContext context = getCurrentContext();
		  EObject bestContainer = CrossPhasesAttachmentHelper.getInstance(context).getRelatedComponent((Component) element, context);
		  if (!bestContainer.equals(element) && !defaultPremise.getFirstElement().equals(bestContainer)) {
			  IPremise currentPremise = createContainmentPremice(bestContainer);
			  if (currentPremise != null) {
				  needed.add(currentPremise);
			  }
		  }
	  }
  }


  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
@Override
  protected void premicesRelated(EObject element, ArrayList<IPremise> needed) {
    super.premicesRelated(element, needed);
    AbstractPhysicalComponent physicalElement = (AbstractPhysicalComponent) element;

    for (Partition partition : physicalElement.getRepresentingPartitions()) {
      Collection es = ComponentExt.getPartAncestors((Part) partition, true);
      needed.addAll(createDefaultPrecedencePremices(es, "part"));
    }
    needed.addAll(createDefaultPrecedencePremices(element, CsPackage.Literals.COMPONENT__ALLOCATING_COMPONENTS));
  }

  @SuppressWarnings("unchecked")
  @Override
  public IStatus transformRequired(EObject source, IContext context) {

    AbstractPhysicalComponent element = (AbstractPhysicalComponent) source;

    /*
     * Is it in selection?
     */
	Collection<EObject> transfoSources = (Collection<EObject>) context.get(ITransitionConstants.TRANSITION_SOURCES);
    if (transfoSources.contains(element)) {
      return Status.OK_STATUS;
    }

    /*
     * Is it going to be merged into a parent?
     */
    Component src = CrossPhasesAttachmentHelper.getInstance(context).getRelatedComponent(element, context);
    if (src != element) {
      return new Status(IStatus.WARNING, Messages.Activity_Transformation, NLS.bind("Component ''{0}'' will be merged into ''{1}''", LogHelper.getInstance()
          .getText(element), LogHelper.getInstance().getText(src)));
    }

    return super.transformRequired(source, context);
  }

  @Override
  protected void retrieveGoDeep(EObject source, List<EObject> result, IContext context) {
    super.retrieveGoDeep(source, result, context);

    if (ContextScopeHandlerHelper.getInstance(context).contains(ITransitionConstants.SOURCE_SCOPE, source, context)) {
      if (source instanceof Component) {
        Component element = (Component) source;
        for (Partition part : element.getOwnedPartitions()) {
          if (part instanceof Part) {
            result.add(part);
            ContextScopeHandlerHelper.getInstance(context).add(ITransitionConstants.SOURCE_SCOPE, part, context);
          }
        }
      }
    }
  }

}
