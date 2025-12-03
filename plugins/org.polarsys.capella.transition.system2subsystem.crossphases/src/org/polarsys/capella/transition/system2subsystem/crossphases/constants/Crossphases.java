package org.polarsys.capella.transition.system2subsystem.crossphases.constants;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.ComponentPkg;
import org.polarsys.capella.core.data.cs.CsFactory;
import org.polarsys.capella.core.data.cs.CsPackage;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.transition.common.constants.ITransitionConstants;
import org.polarsys.capella.core.transition.common.handlers.contextscope.ContextScopeHandlerHelper;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;

public class Crossphases {

  public static boolean isSourceScope(AbstractType element, IContext context) {
    return ContextScopeHandlerHelper.getInstance(context).contains(ITransitionConstants.SOURCE_SCOPE, element, context);
  }
  
  public static boolean isSourceScope(Part element, IContext context) {
    return isSourceScope(element.getAbstractType(), context);
  }
  
  public static Component obtainSystemAnalysisSystem(EObject element, IContext context) {
    EObject root = TransformationHandlerHelper.getInstance(context).getLevelElement(element, context);

    BlockArchitecture target = (BlockArchitecture) TransformationHandlerHelper.getInstance(context).getBestTracedElement(root, context, CsPackage.Literals.BLOCK_ARCHITECTURE);
    if (!(target instanceof SystemAnalysis)) {
      return null;
    }
    SystemAnalysis analysis = (SystemAnalysis) target;
    return BlockArchitectureExt.getOrCreateSystem(analysis);
  }
  
  public static Part obtainSystemAnalysisSystemPart(EObject element, IContext context) {
    Component systemComponent = obtainSystemAnalysisSystem(element, context);
    
    if (systemComponent == null) {
      return null;
    }
    
    // Cast is safe by design of BlockArchitectureExt#getOrCreateSystem
    ComponentPkg container = (ComponentPkg) systemComponent.eContainer();
    Part result = container.getOwnedParts().stream()
      .filter(part -> part.getAbstractType() == systemComponent)
      .findFirst()
      .orElse(null);
    if (result == null) {
      result = CsFactory.eINSTANCE.createPart();
      result.setAbstractType(systemComponent);
      result.setName(systemComponent.getName());
      container.getOwnedParts().add(result);
    }
    return result;
  }
  
}
