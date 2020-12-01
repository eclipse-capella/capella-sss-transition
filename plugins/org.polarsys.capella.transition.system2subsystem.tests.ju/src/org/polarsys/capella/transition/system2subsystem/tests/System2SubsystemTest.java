/*******************************************************************************
 * Copyright (c) 2016, 2017 THALES GLOBAL SERVICES.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.polarsys.capella.common.ef.command.ICommand;
import org.polarsys.capella.core.transition.common.commands.DefaultCommand;
import org.polarsys.capella.core.transition.common.transposer.SharedWorkflowActivityParameter;
import org.polarsys.capella.transition.system2subsystem.crossphases.commands.CrossPhasesCommand;
import org.polarsys.capella.transition.system2subsystem.interphases.commands.InterPhasesCommand;
import org.polarsys.capella.transition.system2subsystem.multiphases.commands.HeadlessMultiphasesCommand;

/**
 * Executes a system2subsystem transition for a reference model and commits the result to a temporary git repository.
 * Then, the transition is executed again to check if this changes the result. Then, executes a series of invariant
 * update/transformations to check again if the result changes. Subclasses perform additional verifications by
 * overriding {@link #verify()}
 */
public abstract class System2SubsystemTest extends AbstractSystem2SubsystemTest {

  public interface Crossphase {
  };

  public interface Interphase {
  };

  public interface Multiphase {
  };

  public enum Kind {

    INTER_PHASES, CROSS_PHASES, MULTI_PHASES;

    public ICommand getCommand(Collection<?> selection, SharedWorkflowActivityParameter parameters) {
      DefaultCommand command = null;
      switch (this) {
      case INTER_PHASES:
        command = new InterPhasesCommand(selection, new NullProgressMonitor());
        break;
      case CROSS_PHASES:
        command = new CrossPhasesCommand(selection, new NullProgressMonitor());
        break;
      case MULTI_PHASES:
        command = new HeadlessMultiphasesCommand(selection);
        break;
      }
      command.addParameters(parameters);
      return command;
    }
  }

  public static Collection<System2SubsystemTest> testsFor(Class<? extends System2SubsystemTest> clazz) {
    Collection<System2SubsystemTest> result = new ArrayList<System2SubsystemTest>();
    try {
      if (Crossphase.class.isAssignableFrom(clazz)) {
        System2SubsystemTest test = clazz.newInstance();
        test.setKind(Kind.CROSS_PHASES);
        result.add(test);
      }
      if (Interphase.class.isAssignableFrom(clazz)) {
        System2SubsystemTest test = clazz.newInstance();
        test.setKind(Kind.INTER_PHASES);
        result.add(test);
      }
      if (Multiphase.class.isAssignableFrom(clazz)) {
        System2SubsystemTest test = clazz.newInstance();
        test.setKind(Kind.MULTI_PHASES);
        result.add(test);
      }
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  protected Kind kind;

  public String getName() {
    return super.getName() + " [" + kind + "]"; //$NON-NLS-1$//$NON-NLS-2$
  }

  // test() is overwritten so performTest should never be used
  public final void performTest() {
    throw new UnsupportedOperationException();
  }

  /**
   * Executes the transition of kind {@link #kind} on the source model into the output model.
   */
  protected void executeTransition() {
    executeCommand(getTransitionCommand());
  }

  /**
   * Set the kind of transformation for this test
   */
  public void setKind(Kind kind) {
    this.kind = kind;
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    getProject();
  }

  /**
   * Implement a create and execute projection operation.
   */
  @Override
  public void test() throws Exception {
    executeTransition();
    traceability = initTraceability(modelResource, getOutputModelResource());
    verify();
  }

  /**
   * Verify the transformation result. This default implementation does nothing.
   * 
   * @throws Exception
   */
  protected void verify() throws Exception {
  }

  protected ICommand getTransitionCommand() {
    return kind.getCommand(getProjectionElements(), getHeadlessParameters());
  }

  /**
   * @return the projection elements on which the transition has to be applied
   */
  protected abstract Collection<?> getProjectionElements();

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("Project_test_01", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }

}