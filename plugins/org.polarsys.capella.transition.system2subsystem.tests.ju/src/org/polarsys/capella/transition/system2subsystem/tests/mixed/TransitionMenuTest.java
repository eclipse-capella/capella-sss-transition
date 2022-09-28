/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.tests.mixed;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.commands.Command;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.PopupMenuExtender;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.NavigatorActionService;
import org.eclipse.ui.services.IServiceLocator;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;
import org.polarsys.capella.core.data.pa.PhysicalFunction;
import org.polarsys.capella.core.data.pa.PhysicalFunctionPkg;
import org.polarsys.capella.core.libraries.model.CapellaModel;
import org.polarsys.capella.core.platform.sirius.ui.navigator.view.CapellaCommonNavigator;
import org.polarsys.capella.test.framework.api.BasicTestCase;

public class TransitionMenuTest extends BasicTestCase {

  protected final String CAPELLA_PERSPECTIVE_ID = "capella.sirius.perspective";

  protected final String TRANSITION_MENU_ID = "capella.core.transition.system2subsystem";
  protected final String VERTICAL_TRANSITION_MENU_ID = "capella.core.transition.system2subsystem.vertical";
  protected final String MULTIPHASES_TRANSITION_COMMAND_ID = "org.polarsys.capella.transition.system2subsystem.multiphases.ui";
  protected final String CROSSPHASES_TRANSITION_COMMAND_ID = "org.polarsys.capella.transition.system2subsystem.crossphases.ui";
  protected final String INTERPHASES_TRANSITION_COMMAND_ID = "org.polarsys.capella.transition.system2subsystem.interphases.ui";

  protected NavigatorActionService _actionService;

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("Project_test_01");
  }

  // This test ensure that the system subsystem transition commands are in the popup menu
  @Override
  public void test() throws Exception {
    IStructuredSelection navigatorSelection1 = null;
    IStructuredSelection navigatorSelection2 = null;
    IStructuredSelection navigatorSelection3 = null;

    try {

      CapellaModel model = getTestModel(getRequiredTestModels().get(0));
      Session session = getSession(getRequiredTestModels().get(0));
      Project project = model.getProject(session.getTransactionalEditingDomain());
      SystemEngineering eng = ((SystemEngineering) (project.getOwnedModelRoots().get(5)));

      final PhysicalArchitecture physicalArchitecturePkg = eng.getContainedPhysicalArchitectures().get(0);

      PhysicalFunctionPkg physicalFunctionsPkg = (PhysicalFunctionPkg) physicalArchitecturePkg.getOwnedFunctionPkg();
      PhysicalFunction rootFunction = physicalFunctionsPkg.getOwnedPhysicalFunctions().get(0);

      PhysicalComponent systemComponent = (PhysicalComponent) physicalArchitecturePkg.getSystem();

      Optional<PhysicalComponent> aNodeComponent = null;
      Optional<PhysicalComponent> aBehaviourComponent = null;

      aBehaviourComponent = systemComponent.getOwnedPhysicalComponents().stream()
          .filter(t -> t.getNature() == PhysicalComponentNature.BEHAVIOR).findFirst();
      aNodeComponent = systemComponent.getOwnedPhysicalComponents().stream()
          .filter(t -> t.getNature() == PhysicalComponentNature.NODE).findFirst();

      if (!aBehaviourComponent.isPresent() || !aNodeComponent.isPresent()) {
        fail("Couldn't find model elements");
      }

      navigatorSelection1 = new StructuredSelection(
          Arrays.asList(aNodeComponent.get(), aBehaviourComponent.get(), rootFunction));
      navigatorSelection2 = new StructuredSelection(
          Arrays.asList(rootFunction, aNodeComponent.get(), aBehaviourComponent.get()));
      navigatorSelection3 = new StructuredSelection(
          Arrays.asList(aBehaviourComponent.get(), rootFunction, aNodeComponent.get()));

    } catch (Exception e) {
      fail("Couldn't find model elements");
    }

    IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

    if (activeWorkbenchWindow == null) {
      fail("No Active workbench window");
    }

    try {
      // Ensure that the welcome page is closed
      closeIntro();
    } catch (Exception E) {
      fail("Could not close intro page");
    }

    try {
      PlatformUI.getWorkbench().showPerspective(CAPELLA_PERSPECTIVE_ID, activeWorkbenchWindow);
    } catch (Exception e) {
      fail("Could not open Capella perspective");
    }

    ensureCommandExists(MULTIPHASES_TRANSITION_COMMAND_ID);
    ensureCommandExists(INTERPHASES_TRANSITION_COMMAND_ID);
    ensureCommandExists(CROSSPHASES_TRANSITION_COMMAND_ID);

    // Retrieve the command Service
    ICommandService commandService = (ICommandService) ((IServiceLocator) PlatformUI.getWorkbench())
        .getService(ICommandService.class);

    ensureCommandIsHandled(commandService, MULTIPHASES_TRANSITION_COMMAND_ID);
    ensureCommandIsHandled(commandService, INTERPHASES_TRANSITION_COMMAND_ID);
    ensureCommandIsHandled(commandService, CROSSPHASES_TRANSITION_COMMAND_ID);

    testTransitionMenus(navigatorSelection1);
    testTransitionMenus(navigatorSelection2);
    testTransitionMenus(navigatorSelection3);

  }

  /**
   * @return
   */
  protected CommonViewer getViewer() {
    final CommonViewer[] viewer = new CommonViewer[1];
    IViewPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
        .findView(CapellaCommonNavigator.ID);

    viewer[0] = ((CapellaCommonNavigator) part).getCommonViewer();
    return viewer[0];
  }

  protected void closeIntro() {
    IIntroPart introPart = PlatformUI.getWorkbench().getIntroManager().getIntro();
    PlatformUI.getWorkbench().getIntroManager().closeIntro(introPart);
  }

  protected void testTransitionMenus(IStructuredSelection selection) {
    // Set Selection t viewer
    CommonViewer viewer = getViewer();
    CapellaCommonNavigator navigator = (CapellaCommonNavigator) viewer.getCommonNavigator();
    navigator.selectReveal(selection);
    viewer.setSelection(selection);

    MenuManager menuMgr = new MenuManager();
    navigator.getNavigatorActionService().fillContextMenu(menuMgr);

    // Create PopupMenuExtender to populate the contextual menu
    PopupMenuExtender extender = new PopupMenuExtender("capella.project.explorer#PopupMenu", menuMgr, viewer, navigator,
        ((PartSite) navigator.getSite()).getContext());

    Menu contextMenu = menuMgr.createContextMenu(viewer.getControl());
    contextMenu.notifyListeners(SWT.Show, null);

    IContributionItem item = null;
    for (IContributionItem contribution : menuMgr.getItems()) {
      if (TRANSITION_MENU_ID.equals(contribution.getId())) {
        item = contribution;
        break;
      }
    }
    if (item == null) {
      fail("Couldn't find transition menu in contextual menu");
    }

    MenuManager transitionMenu = (MenuManager) item;
    IContributionItem interphaseMenu = null;
    IContributionItem verticalTransitionMenu = null;
    for (IContributionItem contribution : transitionMenu.getItems()) {
      if (INTERPHASES_TRANSITION_COMMAND_ID.equals(contribution.getId())) {
        interphaseMenu = contribution;
      } else if (VERTICAL_TRANSITION_MENU_ID.equals(contribution.getId())) {
        verticalTransitionMenu = contribution;
        break;
      }
    }
    if (interphaseMenu == null) {
      fail("Couldn't find interphase transition menu in contextual menu");
    }
    if (verticalTransitionMenu == null) {
      fail("Couldn't find vertical transition menu in contextual menu");
    }

    IContributionItem crossPhaseTransition = null;
    IContributionItem multiphaseTransition = null;

    for (IContributionItem contribution : ((MenuManager) verticalTransitionMenu).getItems()) {
      if (MULTIPHASES_TRANSITION_COMMAND_ID.equals(contribution.getId())) {
        multiphaseTransition = contribution;
      } else if (CROSSPHASES_TRANSITION_COMMAND_ID.equals(contribution.getId())) {
        crossPhaseTransition = contribution;
      }
    }
    if (multiphaseTransition == null) {
      fail("Couldn't find multiphase transition menu in contextual menu");
    }
    if (crossPhaseTransition == null) {
      fail("Couldn't find crossphase transition menu in contextual menu");
    }

  }

  protected void ensureCommandExists(String commandId) {
    IConfigurationElement extension = null;
    IConfigurationElement[] config = Platform.getExtensionRegistry()
        .getConfigurationElementsFor("org.eclipse.ui.commands");
    for (IConfigurationElement element : config) {
      String id = element.getAttribute("id");
      if (id != null) {
        if (id.equals(commandId)) {
          extension = element;
          break;
        }
      }
    }
    if (extension == null) {
      fail("Couldn't find command " + commandId);
    }
  }

  protected void ensureCommandIsHandled(ICommandService commandService, String commandId) {
    // Retrieve the command
    Command command = commandService.getCommand(commandId);

    if (command == null) {
      fail("Couldn't get command " + commandId + " from service");
    }

  }
}
