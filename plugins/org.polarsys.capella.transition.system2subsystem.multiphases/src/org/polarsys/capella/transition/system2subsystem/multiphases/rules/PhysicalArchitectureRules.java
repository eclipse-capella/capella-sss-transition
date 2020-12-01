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
package org.polarsys.capella.transition.system2subsystem.multiphases.rules;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.la.LaPackage;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.transition.system2subsystem.crossphases.rules.pa.PhysicalArchitectureRule;
import org.polarsys.capella.transition.system2subsystem.multiphases.MultiphasesActivator;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;


public class PhysicalArchitectureRules {

	 public static class ToSystemAnalysis extends PhysicalArchitectureRule {
		@Override
		public IStatus applyRequired(EObject element_p, IContext context_p) {
			return new Status(IStatus.CANCEL, MultiphasesActivator.PLUGIN_ID, "BlockArchitectures must be present in target model");
		}
	 }

	  public static class ToLogicalArchitecture extends PhysicalArchitectureRule {
		@Override
		public EClass getTargetType(EObject element_p, IContext context_p) {
			return LaPackage.Literals.LOGICAL_ARCHITECTURE;
		}
	  }
	  
	  public static class ToPhysicalArchitecture extends PhysicalArchitectureRule {
		  @Override
		  public EClass getTargetType(EObject element_p, IContext context_p) {
			  return PaPackage.Literals.PHYSICAL_ARCHITECTURE;
		  }
	  }

}
