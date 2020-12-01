/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.tests;

import org.osgi.framework.BundleContext;
import org.polarsys.capella.common.mdsofa.common.activator.AbstractActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class System2SubsystemTestActivator extends AbstractActivator {

  public static final String PLUGIN_ID = "org.polarsys.capella.transition.system2subsystem.tests.ju"; //$NON-NLS-1$
  private static System2SubsystemTestActivator __instance;

  /**
   * Get the singleton instance.
   * 
   * @return
   */
  public static System2SubsystemTestActivator getDefault() {
    return __instance;
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context_p) throws Exception {
    super.start(context_p);
    __instance = this;

    // Modify the extension registry in order to perform test
    // without any UI "interactions" expected from users.
  }

  /**
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context_p) throws Exception {
    __instance = null;
    super.stop(context_p);

    // Restore the extension registry to its initial state
  }

}
