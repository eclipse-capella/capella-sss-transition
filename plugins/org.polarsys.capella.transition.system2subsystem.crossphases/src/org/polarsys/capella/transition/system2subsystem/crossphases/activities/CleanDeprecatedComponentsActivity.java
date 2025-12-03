/*******************************************************************************
 * Copyright (c) 2025 THALES GLOBAL SERVICES.
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
package org.polarsys.capella.transition.system2subsystem.crossphases.activities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.diffmerge.diffdata.EComparison;
import org.eclipse.emf.diffmerge.diffdata.EMapping;
import org.eclipse.emf.diffmerge.generic.api.IComparison;
import org.eclipse.emf.diffmerge.generic.api.IMatch;
import org.eclipse.emf.diffmerge.generic.api.Role;
import org.eclipse.emf.diffmerge.generic.api.diff.IDifference;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.transition.common.activities.AbstractActivity;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.transition.system2subsystem.crossphases.constants.Crossphases;
import org.polarsys.kitalpha.cadence.core.api.parameter.ActivityParameters;
import org.polarsys.kitalpha.transposer.api.ITransposerWorkflow;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

/**
 * Activity to remove deprecated components in target System.
 * <p>
 * This class mimics the "remove" button of Diff/Merge UI.
 * </p>
 * 
 * @author nicolas.peransin@obeo.fr
 */
public class CleanDeprecatedComponentsActivity extends AbstractActivity implements ITransposerWorkflow {

  public static final String ID = CleanDeprecatedComponentsActivity.class.getName();

  
  @Override
  public IStatus _run(ActivityParameters activityParams) {
    IContext context = (IContext) activityParams.getParameter(TRANSPOSER_CONTEXT).getValue();

    removeDeprecatedComponents(context);

    return Status.OK_STATUS;
  }
  
  protected void removeDeprecatedComponents(IContext context) {
    EComparison comparison = (EComparison) context.get(ITransitionConstants.MERGE_COMPARISON);
    
    // We remove previous external components.
    // It would be too difficult for the user to detect which come from previous transformation.
    List<IMatch<EObject>> toRemoveMatches = getMatchingOfDeprecatedElements(comparison.getMapping(), context);
    List<IDifference<EObject>> targetDifferences = collectTargetDifferences(toRemoveMatches);
    if (!targetDifferences.isEmpty()) {
      comparison.merge(targetDifferences, Role.TARGET, true, new NullProgressMonitor());
    }
  }
  
  
  private static List<IMatch<EObject>> getMatchingOfDeprecatedElements(EMapping mapping, IContext context) {
    return mapping.getContents().stream()
      .filter(match -> Crossphases.isDeprecatedTarget(match.get(Role.TARGET), context))
      .collect(Collectors.toList());
  }

  private static List<IDifference<EObject>> collectTargetDifferences(List<IMatch<EObject>> matches) {
    // See org.eclipse.emf.diffmerge.ui.viewers.ComparisonViewer
    // #getDifferencesToMerge(List<IMatch<?>>, Role.Target, coverChildren, !incrementalMode)
    List<IDifference<EObject>> result = new ArrayList<IDifference<EObject>>();
    matches.stream()
      .map(CleanDeprecatedComponentsActivity::collectAllTargetDifferences)
      .forEach(result::addAll);
    
    return result;
  }
  
  private static List<IDifference<EObject>> collectAllTargetDifferences(IMatch<EObject> match) {
    // See org.eclipse.emf.diffmerge.ui.viewers.ComparisonViewer
    // #addDifferencesToMergeRec(List<IDifference<?>>, IMatch<?>, Role.Target, true)
    List<IDifference<EObject>> result = new ArrayList<IDifference<EObject>>();
    
    result.addAll(match.getAllDifferences()); // When not incremental, all diff are needed.
    for (IMatch<EObject> child : getChildrenForMerge(match)) {
      result.addAll(collectAllTargetDifferences(child));
    }
    
    return result;
  }

  private static List<IMatch<EObject>> getChildrenForMerge(IMatch<EObject> match) {
    // See org.eclipse.emf.diffmerge.ui.viewers.CategoryManager
    // #getChildrenForMerge(IMatch<?>)
    IComparison<EObject> comparison = match.getMapping().getComparison();
    return comparison.getContentsOf(match).stream()
        .filter(CleanDeprecatedComponentsActivity::isMoveOrigin)
        // Skip differenceNumber: always > 0 when inner match
        .collect(Collectors.toList());
  }
  
  private static boolean isMoveOrigin(IMatch<EObject> match) {  
    // See org.eclipse.emf.diffmerge.ui.viewers.CategoryManager#isMoveOrigin(IMatch<?>, IMatch<?>)
    IComparison<EObject> comparison = match.getMapping().getComparison();
    IMatch<EObject> parentMatch = comparison.getContainerOf(match, Role.TARGET);
    
    // See org.eclipse.emf.diffmerge.ui.viewers.CategoryManager#isMove(IMatch<?>, boolean)
    boolean isMove = !match.isPartial() 
        && match.getElementPresenceDifference() == null
        && (match.getOwnershipDifference(Role.TARGET) != null 
          || match.getOwnershipDifference(Role.REFERENCE) != null);
    
    
    return !isMove
        && comparison.getContainerOf(match, Role.TARGET.opposite()) == parentMatch 
        && comparison.getContainerOf(match, Role.TARGET) != parentMatch;
  }

}
