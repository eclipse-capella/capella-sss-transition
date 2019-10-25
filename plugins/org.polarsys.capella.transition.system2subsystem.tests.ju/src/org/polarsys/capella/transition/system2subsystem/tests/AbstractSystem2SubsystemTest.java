/*******************************************************************************
 * Copyright (c) 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.polarsys.capella.common.data.modellingcore.ModelElement;
import org.polarsys.capella.common.ef.ExecutionManager;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.capellamodeller.SystemEngineering;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.data.ctx.SystemComponentPkg;
import org.polarsys.capella.core.data.la.LogicalArchitecture;
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.data.la.LogicalComponentPkg;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalComponentPkg;
import org.polarsys.capella.core.model.helpers.BlockArchitectureExt;
import org.polarsys.capella.core.model.helpers.ComponentExt;
import org.polarsys.capella.core.model.helpers.ComponentPkgExt;
import org.polarsys.capella.core.model.helpers.ModelQueryHelper;
import org.polarsys.capella.core.model.helpers.ProjectExt;
import org.polarsys.capella.core.model.helpers.SystemEngineeringExt;
import org.polarsys.capella.core.transition.common.constants.IOptionsConstants;
import org.polarsys.capella.core.transition.common.transposer.SharedWorkflowActivityParameter;
import org.polarsys.capella.test.framework.api.BasicCommandTestCase;
import org.polarsys.kitalpha.cadence.core.api.parameter.GenericParameter;

public abstract class AbstractSystem2SubsystemTest extends BasicCommandTestCase {

  public enum ComponentType {

    SYSTEM, ACTOR,

    SYSTEM_ANALYSIS, SYSTEM_STRUCTURE,

    LOGICAL_ARCHITECTURE, LOGICAL_STRUCTURE, LOGICAL_COMPONENT, LOGICAL_ACTOR,

    PHYSICAL_ARCHITECTURE, PHYSICAL_STRUCTURE, PHYSICAL_ACTOR;

    public boolean isInstanceOf(EObject source) {
      switch (this) {
      case ACTOR:
        return ComponentExt.isActor(source);
      case SYSTEM:
        return source instanceof Component && BlockArchitectureExt.isRootComponent((Component) source);
      case SYSTEM_ANALYSIS:
        return source instanceof SystemAnalysis;
      case SYSTEM_STRUCTURE:
        return source instanceof SystemComponentPkg && ComponentPkgExt.isRootComponentPkg(source);
      case LOGICAL_ARCHITECTURE:
        return source instanceof LogicalArchitecture;
      case LOGICAL_STRUCTURE:
        return source instanceof LogicalComponentPkg && ComponentPkgExt.isRootComponentPkg(source);
      case LOGICAL_COMPONENT:
        return !ComponentExt.isActor(source)
            && BlockArchitectureExt.getRootBlockArchitecture(source) instanceof LogicalArchitecture;
      case LOGICAL_ACTOR:
        return ComponentExt.isActor(source)
            && BlockArchitectureExt.getRootBlockArchitecture(source) instanceof LogicalArchitecture;
      case PHYSICAL_ARCHITECTURE:
        return source instanceof PhysicalArchitecture;
      case PHYSICAL_STRUCTURE:
        return source instanceof PhysicalComponentPkg && ComponentPkgExt.isRootComponentPkg(source);
      case PHYSICAL_ACTOR:
        return ComponentExt.isActor(source)
            && BlockArchitectureExt.getRootBlockArchitecture(source) instanceof PhysicalArchitecture;
      default:
        return false;
      }
    }
  }

  private Object getFirstContainer(EObject element, ComponentType type) {
    EObject container = null;

    if (element != null) {
      container = element.eContainer();
    }

    if (container == null) {
      return null;
    }

    if (type.isInstanceOf(container)) {
      return container;
    }

    return getFirstContainer(container, type);
  }

  protected TraceabilitySID traceability;
  private final SharedWorkflowActivityParameter parameters = new SharedWorkflowActivityParameter();
  private Resource outputModelResource;

  protected void addSharedParameter(String name, Object value) {
    getHeadlessParameters().addSharedParameter(new GenericParameter<Object>(name, value, name));
  }

  public EObject retrieveReferenceElement(String id) {
    EObject source = getObject(id);
    assertTrue(source != null);
    return source;
  }

  public EObject mustBeTransitionedAndLinkedTo(String id, String id2, EStructuralFeature feature) {
    EObject source = retrieveTargetElement(id);
    EObject target = retrieveTargetElement(id2);
    testReferenceLinked(source, target, feature);
    return source;
  }

  public EObject mustBeTransitionedAndOnlyLinkedTo(String id, Collection<EObject> targetObjectsList,
      EStructuralFeature feature) {
    EObject source = retrieveTargetElement(id);

    @SuppressWarnings("unchecked")
    EList<EObject> linkedObjects = (EList<EObject>) source.eGet(feature);
    assertTrue((linkedObjects.size() == targetObjectsList.size()) && linkedObjects.containsAll(targetObjectsList));

    return source;
  }

  public EObject mustBeTransitionedAndLinkedTo(String id, EObject target, EStructuralFeature feature) {
    EObject source = retrieveTargetElement(id);
    testReferenceLinked(source, target, feature);
    return source;
  }

  public EObject mustBeTransitionedAndNotLinkedTo(String id, String id2, EStructuralFeature feature) {
    EObject source = retrieveTargetElement(id);
    EObject target = traceability.getTracedObject(id2);
    testReferenceNotLinked(source, target, feature);
    return source;
  }

  public EObject shouldNotBeTransitioned(String id) {
    EObject source = traceability.getTracedObject(id);
    assertTrue(source == null);
    return source;
  }

  public EObject mustBeTransitioned(String id) {
    EObject source = traceability.getTracedObject(id);
    assertTrue(source != null);
    return source;
  }

  public EObject mustBeTransitioned(String id, ComponentType asType) {
    EObject source = traceability.getTracedObject(id);
    assertTrue((source != null) && asType.isInstanceOf(source));
    return source;
  }

  public EObject mustBeTransitionedInto(String id, ComponentType container_p) {
    boolean is = false;
    for (EObject source : traceability.getTracedObjects(id)) {
      assertTrue(source != null);
      if (getFirstContainer(source, container_p) != null) {
        is = true;
        break;
      }
    }
    assertTrue(is);
    return null;
  }

  public EObject shouldNotBeTransitionedInto(String id, ComponentType container_p) {
    for (EObject source : traceability.getTracedObjects(id)) {
      assertTrue(source != null);
      if (getFirstContainer(source, container_p) != null) {
        assertFalse(true);
        break;
      }
    }
    return null;
  }

  public EObject mustBeTransitionedOutside(String id, ComponentType container) {
    EObject source = traceability.getTracedObject(id);
    assertTrue(source != null);
    assertTrue(getFirstContainer(source, container) == null);
    return source;
  }

  public EObject mustBeTransitionedOnce(String id) {
    Collection<EObject> sources = traceability.getTracedObjects(id);
    assertTrue((sources != null) && (sources.size() == 1));
    return null;
  }

  public EObject retrieveTargetElement(String id) {
    EObject source = traceability.getTracedObject(id);
    assertTrue(source != null);
    return source;
  }

  public Component retrieveTargetSystem() {
    ModelElement dummyTargetElement = (ModelElement) getOutputModelResource().getContents().get(0);
    Project targetProject = ProjectExt.getProject(dummyTargetElement);
    SystemEngineering targetSystemEngineering = SystemEngineeringExt.getSystemEngineering(targetProject);
    Component targetSystem = SystemEngineeringExt.getSystem(targetSystemEngineering);
    assertTrue(targetSystem != null);
    return targetSystem;
  }

  public LogicalComponent retrieveTargetLogicalSystem() {
    ModelElement dummyTargetElement = (ModelElement) getOutputModelResource().getContents().get(0);
    Project targetProject = ProjectExt.getProject(dummyTargetElement);
    LogicalComponent targetLogicalSystem = ModelQueryHelper.getLogicalSystem(targetProject);
    assertTrue(targetLogicalSystem != null);
    return targetLogicalSystem;
  }

  public void testReferenceLinked(EObject source, EObject target, EStructuralFeature feature) {

    if (feature.isMany()) {
      assertTrue(((List<?>) source.eGet(feature)).contains(target));
    } else {
      assertTrue(source.eGet(feature).equals(target));
    }

  }

  public void testReferenceNotLinked(EObject source, EObject target, EStructuralFeature feature) {
    if (target == null) {
      if (feature.isMany()) {
        assertTrue((source.eGet(feature) == null) || !((List<?>) source.eGet(feature)).contains(null));
      } else {
        assertTrue(source.eGet(feature) == null);
      }

    } else {
      if (feature.isMany()) {
        assertTrue(!source.eGet(feature).equals(target) && !((List<?>) source.eGet(feature)).contains(target));
      } else {
        assertTrue(!source.eGet(feature).equals(target));
      }
    }
  }

  public void testAttributeIdentity(EObject source, EObject target, EAttribute attribute) {
    Object sourceValue = source.eGet(attribute);
    Object targetValue = target.eGet(attribute);
    assertTrue((sourceValue == targetValue) || ((sourceValue != null) && sourceValue.equals(targetValue)));
  }

  public void testInstanceOf(EObject source, ComponentType clazz) {
    assertTrue(clazz.isInstanceOf(source));
  }

  protected SharedWorkflowActivityParameter getHeadlessParameters() {
    return parameters;
  }

  protected String getOutputModelPlatformURIString() {
    return "/output/output.melodymodeller";
  }

  protected Resource getOutputModelResource() {
    if (outputModelResource == null) {
      ResourceSet set = new ExecutionManager().getEditingDomain().getResourceSet();
      outputModelResource = set.getResource(URI.createPlatformResourceURI(getOutputModelPlatformURIString(), true),
          true);
    }
    return outputModelResource;
  }

  /**
   * Creates and initializes a TraceabilitySID for this test. The creation is delegated to {@link #createTraceability()}
   */
  protected final TraceabilitySID initTraceability(Resource modelResource, Resource outputModelResource) {
    TraceabilitySID result = createTraceability();
    result.init(modelResource, outputModelResource);
    return result;
  }

  /**
   * Creates an empty TraceabilitySID, subclasses may override.
   */
  protected TraceabilitySID createTraceability() {
    return new TraceabilitySID();
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    getHeadlessParameters().addSharedParameter(new GenericParameter<String>(IOptionsConstants.OUTPUT_PROJECT,
        getOutputModelPlatformURIString(), "The output model path"));
  }

}