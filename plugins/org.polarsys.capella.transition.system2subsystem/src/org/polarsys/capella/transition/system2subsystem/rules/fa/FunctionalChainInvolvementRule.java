/*******************************************************************************
 * Copyright (c) 2006, 2021 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.rules.fa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.polarsys.capella.common.data.activity.AbstractAction;
import org.polarsys.capella.common.data.activity.InputPin;
import org.polarsys.capella.common.data.activity.OutputPin;
import org.polarsys.capella.common.helpers.EObjectExt;
import org.polarsys.capella.common.helpers.EcoreUtil2;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FaFactory;
import org.polarsys.capella.core.data.fa.FaPackage;
import org.polarsys.capella.core.data.fa.FunctionInputPort;
import org.polarsys.capella.core.data.fa.FunctionOutputPort;
import org.polarsys.capella.core.data.fa.FunctionPort;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementFunction;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.core.data.fa.FunctionalChainReference;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.transition.common.constants.Messages;
import org.polarsys.capella.core.transition.common.handlers.log.LogHelper;
import org.polarsys.capella.core.transition.common.handlers.transformation.TransformationHandlerHelper;
import org.polarsys.capella.transition.system2subsystem.handlers.attachment.FunctionalChainAttachmentHelper;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IContext;
import org.polarsys.kitalpha.transposer.rules.handler.rules.api.IPremise;

public class FunctionalChainInvolvementRule extends org.polarsys.capella.core.transition.system.rules.fa.FunctionalChainInvolvementRule {

  public static final String ID_FAKE_FUNCTIONAL_EXCHANGE = "ID_FakeFunctionalExchange";
  public static final String ID_FAKE_FUNCTIONAL_CHAIN_INVOLVEMENT = "ID_FakeFunctionalChainInvolvement";
  
  @Override
  public IStatus transformRequired(EObject element_p, IContext context_p) {
    FunctionalChainInvolvement element = (FunctionalChainInvolvement) element_p;
    // InvolvedElement must be transitioned

    NamedElement involved = (NamedElement) element.getInvolved();

    IStatus result = TransformationHandlerHelper.getInstance(context_p).isOrWillBeTransformed(element.getInvolver(), context_p);
    if (!result.isOK()) {
      return result;
    }

    result = TransformationHandlerHelper.getInstance(context_p).isOrWillBeTransformed(involved, context_p);
    FunctionalChainAttachmentHelper helper = FunctionalChainAttachmentHelper.getInstance(context_p);

    if (result.isOK()) {
      if (!(helper.isValidElement(element, context_p) == Boolean.TRUE)) {
        result =
            new Status(IStatus.WARNING, Messages.Activity_Transformation, NLS.bind("Functional Chain Involvement ''{0}'' is not valid.", LogHelper
                .getInstance().getText(element_p)));
      }
    } else {
      if (helper.isValidElement(element, context_p) == Boolean.TRUE) {
        result = Status.OK_STATUS;
      }
    }

    if (result.isOK() && (involved instanceof FunctionalExchange)) {
      boolean res = true;
      Collection<FunctionalChainInvolvement> nextsValid = helper.getNextValid(element, context_p);
      // If an FE FCI has no valid next or valid previous (typically if this FCI ends with itself),
      // then it shall not be exported.
      if (nextsValid.isEmpty()) {
        res = false;
      } else {
        // If there is a valid next, it shall be direct (no jump)
        if (!FCIsCollectionMatching(element.getNextFunctionalChainInvolvements(), nextsValid)) {
          res = false;
        }
      }

      Collection<FunctionalChainInvolvement> prevsValid = helper.getPreviousValid(element, context_p);
      if (prevsValid.isEmpty()) {
        res = false;
      } else {
        // If there is a valid previous, it shall be direct (no jump)
        if (!FCIsCollectionMatching(element.getPreviousFunctionalChainInvolvements(), prevsValid)) {
          res = false;
        }
      }

      if (!res) {
        result = new Status(IStatus.WARNING, Messages.Activity_Transformation,
            NLS.bind("Functional Chain Involvement ''{0}'' is not valid.", LogHelper.getInstance().getText(element_p)));
      }
    }

    return result;
  }

  private boolean FCIsCollectionMatching(Collection<FunctionalChainInvolvement> fci1, Collection<FunctionalChainInvolvement> fci2) {
    boolean containOne = false;
    for (FunctionalChainInvolvement fci : fci1) {
      for (FunctionalChainInvolvement nextValid : fci2) {
        if ((nextValid.getInvolved() != null) && nextValid.getInvolved().equals(fci.getInvolved())) {
          containOne = true;
          break;
        }
      }
      if (containOne) {
        break;
      }
    }

    return containOne;
  }

  @Override
  protected void attachRelated(EObject src, EObject trg, IContext context) {
    super.attachRelated(src, trg, context);
    FunctionalChainAttachmentHelper helper = FunctionalChainAttachmentHelper.getInstance(context);
    FunctionalChainInvolvement fSrc = (FunctionalChainInvolvement) src;
    FunctionalChainInvolvement fTrg = (FunctionalChainInvolvement) trg;

    if (!fSrc.getNextFunctionalChainInvolvements().isEmpty()) {
      for (FunctionalChainInvolvement nextSrcValid : helper.getNextValid(fSrc, context)) {
        if (nextSrcValid instanceof FunctionalChainInvolvementLink) {
          // If jump and if try to link through a FE => redirection to FE's target.
          if (!isDirectJump(nextSrcValid, fSrc)) {
            nextSrcValid = nextSrcValid.getNextFunctionalChainInvolvements().get(0);
          }
        }
        Collection<EObject> nextTrgs = retrieveTracedElements(nextSrcValid, context);
        if (nextTrgs != null && !nextTrgs.isEmpty()) {
          FunctionalChainInvolvement nextTrgValid = (FunctionalChainInvolvement) nextTrgs.iterator().next();

          // If it's a direct jump, it's OK to link the target FCIF to the target FCIL
          if (fSrc.getNextFunctionalChainInvolvements().contains(nextSrcValid)) {
            if (fSrc instanceof FunctionalChainInvolvementFunction
                && nextSrcValid instanceof FunctionalChainInvolvementLink)
              ((FunctionalChainInvolvementLink) nextTrgValid).setSource((FunctionalChainInvolvementFunction) fTrg);
            else if (fSrc instanceof FunctionalChainInvolvementLink
                && nextSrcValid instanceof FunctionalChainInvolvementFunction)
              ((FunctionalChainInvolvementLink) fTrg).setTarget((FunctionalChainInvolvementFunction) nextTrgValid);
          }

          // If next valid FCI is a FCIL, it's OK to link the target FCIF to the target FCIL
          else if (nextSrcValid instanceof FunctionalChainInvolvementLink
              && fSrc instanceof FunctionalChainInvolvementFunction) {
            ((FunctionalChainInvolvementLink) nextTrgValid).setSource((FunctionalChainInvolvementFunction) fTrg);
          }

          // If there is a jump to next and that this jump concern two Functions => creation of a fake FE
          else if (fSrc instanceof FunctionalChainInvolvementFunction
              && nextSrcValid instanceof FunctionalChainInvolvementFunction) {
            // Creation of a fake FE
            Collection<FunctionalChainInvolvement> involvments = listInvolvments(
                (FunctionalChainInvolvementFunction) fSrc, (FunctionalChainInvolvementFunction) nextSrcValid);
            String description = buildDescription(involvments);

            Collection<EObject> nextTrgFcts = retrieveTracedElements(nextSrcValid.getInvolved(), context);
            AbstractFunction nextTrgFct = (AbstractFunction) nextTrgFcts.iterator().next();
            String idDiff = nextTrgFct.getClass().getSimpleName().replaceAll("Function", "").replaceAll("Impl", "");
            FunctionalExchange fakeFE = createFakeFE(fTrg, nextTrgFct, context, description, idDiff);

            createInvolvements((FunctionalChainInvolvementFunction) fSrc,
                (FunctionalChainInvolvementFunction) nextSrcValid, context, fakeFE, idDiff);
          }
        }
      }
    }
  }

  private boolean isDirectJump(FunctionalChainInvolvement nextSrcValid, FunctionalChainInvolvement fSrc) {
    for (FunctionalChainInvolvement fci : nextSrcValid.getPreviousFunctionalChainInvolvements()) {
      if (fSrc.getInvolved().equals(fci.getInvolved())) {
        return fSrc.getNextFunctionalChainInvolvements().contains(nextSrcValid);
      }
    }
    return false;
  }

  /**
   * 
   * When using composite chains, a functional chain can be used in another chains.
   * 
   * For an involvement INV1 retrieve all paths (hierarchy of FunctionalChainReferences) where INV is used.
   * 
   * ChainA --FCR1--> SubChainA --FCR2--> SubSubChainA containing INV1
   * 
   * ChainA2 --FCR3--> SubChainA2 --FCR4--> SubSubChainA containing INV1
   * 
   * ChainA2 --FCR5--> SubSubChainA containing INV1
   * 
   * A contains FCR1(AA) composed by AAA A2 is composed by AA2 composed by AAA
   * 
   * For INV1: {FCR2, FCR1}, {FCR4, FCR3} and {FCR5}
   * 
   * With this information, we know that INV1 is used in three paths, {ChainA, SubChainA, SubSubChainA}, {ChainA2,
   * SubChainA2, SubSubChainA} and {ChainA2, SubSubChainA}
   */
  public Collection<List<FunctionalChainReference>> getPaths(FunctionalChainInvolvement invs) {
    Map<FunctionalChainInvolvement, ArrayList<FunctionalChainReference>> paths = new HashMap<>();
    Collection<List<FunctionalChainReference>> result = new ArrayList<>();
    Function<FunctionalChainInvolvement, ArrayList<FunctionalChainReference>> defaultValue = x -> new ArrayList<>();

    LinkedList<FunctionalChainInvolvement> toVisit = new LinkedList<>();
    toVisit.add(invs);
    
    while (!toVisit.isEmpty()) {
      FunctionalChainInvolvement currentInv = toVisit.removeFirst();
      List<FunctionalChainReference> currentPath = paths.computeIfAbsent(currentInv, defaultValue);
      
      FunctionalChain chain = (FunctionalChain) currentInv.getInvolver();
      Collection<FunctionalChainReference> referencingRefs = getReferencingChainReferences(chain);
      
      if (!referencingRefs.isEmpty()) {
        for (FunctionalChainReference ref : referencingRefs) {
          paths.computeIfAbsent(ref, x -> new ArrayList<>(currentPath)).add(ref);
        }
        toVisit.addAll(referencingRefs);
        
      } else {
        result.add(currentPath);
      }
    }
    return result;
  }

  /**
   * Retrieve all FunctionalChainReference referencing the given chain
   */
  public Collection<FunctionalChainReference> getReferencingChainReferences(FunctionalChain chain) {
    List<FunctionalChainReference> result = new ArrayList<>();
    for (EObject ref : EObjectExt.getReferencers(chain, CapellacorePackage.Literals.INVOLVEMENT__INVOLVED)) {
      if (ref instanceof FunctionalChainReference) {
        result.add((FunctionalChainReference) ref);
      }
    }
    return result;
  }
  
  private Collection<FunctionalChainInvolvement> listInvolvments(FunctionalChainInvolvement startFci,
      FunctionalChainInvolvement endFci) {
    Collection<FunctionalChainInvolvement> res = new ArrayList<>();
    FunctionalChainInvolvement current = startFci;

    while (!current.getNextFunctionalChainInvolvements().isEmpty()) {
      FunctionalChainInvolvement next = current.getNextFunctionalChainInvolvements().get(0);
      if (next.equals(endFci) == false) {
        current = next;
        res.add(current);
      } else {
        break;
      }
    }

    return res;
  }

  public ArrayList<EObject> createFullPath(FunctionalChainInvolvementFunction startFci,
      List<FunctionalChainReference> sourcePath) {
    // We also add the container of the last chainReference (the top most chain)
    ArrayList<EObject> fullSourcePath = new ArrayList<>(sourcePath);
    if (sourcePath.isEmpty()) {
      fullSourcePath.add(startFci.eContainer());
    } else {
      fullSourcePath.add(sourcePath.get(sourcePath.size() - 1).eContainer());
    }
    return fullSourcePath;
  }

  /**
   * Create involvement links between startFci and endFci. As an involvement can be part of several composite chain,
   * there might have several involvement created in owning composite chains.
   */
  private void createInvolvements(FunctionalChainInvolvementFunction startFci,
      FunctionalChainInvolvementFunction endFci, IContext context, FunctionalExchange ex, String id) {

    // Main idea:
    // We retrieve for both source and target involvements where they are used.
    // (ie list of all functional chain references from FCI to the topmost composite chain)
    // For each valid combination (having at least one common parent), we create a involvmentLink.

    // The new link is owned by the first common parent
    // The sourceHirarchy is the subList: sourcePaths(0, indexOf(commonParent)) (resp. targetHierarchy)
    Collection<List<FunctionalChainReference>> sourcePaths = getPaths(startFci);
    Collection<List<FunctionalChainReference>> targetPaths = getPaths(endFci);

    for (List<FunctionalChainReference> sourcePath : sourcePaths) {
      List<EObject> fullSourcePath = createFullPath(startFci, sourcePath);

      for (List<FunctionalChainReference> targetPath : targetPaths) {
        List<EObject> fullTargetPath = createFullPath(endFci, targetPath);

        List<EObject> commonParents = new ArrayList<EObject>(fullSourcePath);
        commonParents.retainAll(fullTargetPath);
        sort(commonParents, fullSourcePath); // We sort back the commonParents list (as list.retainAll doesn't imply the
                                             // list keep the same order)

        // If there is a common parent between both paths, then we create an involvement
        if (!commonParents.isEmpty()) {

          EObject firstCommon = commonParents.get(0);
          EObject container = firstCommon instanceof FunctionalChainReference
              ? ((FunctionalChainReference) firstCommon).getInvolved()
              : firstCommon;

          // we compute hierarchy. which is subList of Paths(0, indexOf(commonParent)). (the common parent may be the
          // main chain, then the hierarchy is all the path)
          Collection<FunctionalChainReference> sHierarchy = firstCommon instanceof FunctionalChainReference
              ? sourcePath.subList(0, sourcePath.indexOf(firstCommon))
              : sourcePath;
          Collection<FunctionalChainReference> tHierarchy = firstCommon instanceof FunctionalChainReference
              ? targetPath.subList(0, targetPath.indexOf(firstCommon))
              : targetPath;
          createFakeFunctionalChainInvolvementLink(startFci, endFci, ex, context, id, (FunctionalChain) container,
              sHierarchy, tHierarchy);
        }
      }
    }

  }

  private void sort(List<EObject> commonParents, List<EObject> fullSourcePath) {
    commonParents.sort(new Comparator<EObject>() {
      @Override
      public int compare(EObject o1, EObject o2) {
        return fullSourcePath.indexOf(o1) - fullSourcePath.indexOf(o2);
      }
    });
  }

  private String buildDescription(Collection<FunctionalChainInvolvement> involvedElements) {
    StringBuilder descriptionBuilder = new StringBuilder();

    for (FunctionalChainInvolvement current : involvedElements) {
      NamedElement iv = (NamedElement) current.getInvolved();
      descriptionBuilder.append(String.format("%s(%s);<br/>", iv.getName(), iv.getId()));
    }

    return descriptionBuilder.toString();
  }

  @Override
  protected void premicesRelated(EObject element_p, ArrayList<IPremise> needed_p) {
    super.premicesRelated(element_p, needed_p);

    if (element_p instanceof FunctionalChainInvolvement) {
      FunctionalChainInvolvement src = (FunctionalChainInvolvement) element_p;

      Collection<EObject> nexts = new ArrayList<EObject>();

      while ((src.getNextFunctionalChainInvolvements() != null) && (src.getNextFunctionalChainInvolvements().isEmpty() == false)) {
        src = src.getNextFunctionalChainInvolvements().get(0);
        nexts.add(src);

      }
      needed_p.addAll(createDefaultPrecedencePremices(nexts, "nextsInvolvements"));
    }

    if (element_p instanceof FunctionalChainReference) {
      Collection<EObject> fcis = new ArrayList<EObject>();
      FunctionalChain fc = ((FunctionalChainReference) element_p).getReferencedFunctionalChain();
      fcis.addAll(fc.getOwnedFunctionalChainInvolvements());

      needed_p.addAll(createDefaultCriticalPremices(fcis, "referencedInvolvements"));
    }

  }

  private <T extends EObject> T tracedOf(T e, IContext context) {
    Collection<EObject> values = retrieveTracedElements(e, context);
    return (T) values.stream().findFirst().orElse(null);
  }

  private <T extends EObject> Collection<T> tracedOf(Collection<T> e, IContext context) {
    return e.stream().map(x -> tracedOf(x, context)).collect(Collectors.toList());
  }

  private FunctionalChainInvolvementLink createFakeFunctionalChainInvolvementLink(
      FunctionalChainInvolvementFunction src, FunctionalChainInvolvementFunction trg, FunctionalExchange fe,
      IContext context_p, String idPrefix, FunctionalChain parent, Collection<FunctionalChainReference> sH,
      Collection<FunctionalChainReference> tH) {

    FunctionalChainInvolvementFunction tSrc = tracedOf(src, context_p);
    FunctionalChainInvolvementFunction tTgt = tracedOf(trg, context_p);
    FunctionalChain tParent = tracedOf(parent, context_p);
    Collection<FunctionalChainReference> tsH = tracedOf(sH, context_p);
    Collection<FunctionalChainReference> ttH = tracedOf(tH, context_p);
 
    String id = String.format("%s_%s_%s_%s", ID_FAKE_FUNCTIONAL_CHAIN_INVOLVEMENT, idPrefix, tSrc.getSid(), tTgt.getSid());
    if (!sH.isEmpty()) {
      id += sH.stream().map(x -> x.getSid()).collect(Collectors.joining("_"));
    }
    if (!tH.isEmpty()) {
      id += tH.stream().map(x -> x.getSid()).collect(Collectors.joining("_"));
    }

    FunctionalChainInvolvementLink res = null;

    for (FunctionalChainInvolvement fci : tSrc.getNextFunctionalChainInvolvements()) {
      if (fci instanceof FunctionalChainInvolvementLink && fci.getSid().equals(id)) {
        res = (FunctionalChainInvolvementLink) fci;
        break;
      }
    }
    for (FunctionalChainInvolvement fci : tTgt.getPreviousFunctionalChainInvolvements()) {
      if (fci instanceof FunctionalChainInvolvementLink && fci.getSid().equals(id)) {
        res = (FunctionalChainInvolvementLink) fci;
        break;
      }
    }

    if (res == null) {
      res = FaFactory.eINSTANCE.createFunctionalChainInvolvementLink();
      res.setId(id);
      res.setSid(id);
      res.getSourceReferenceHierarchy().addAll(tsH);
      res.getTargetReferenceHierarchy().addAll(ttH);
      res.setSource(tSrc);
      res.setTarget(tTgt);
      res.setInvolved(fe);
      tParent.getOwnedFunctionalChainInvolvements().add(res);
    }

    return res;
  }

  private FunctionalExchange createFakeFE(FunctionalChainInvolvement srcFci, AbstractFunction targetFunction,
      IContext context_p, String description, String idPrefix) {

    FunctionalExchange fe = null;

    if (srcFci.getInvolved() instanceof AbstractFunction) {
      AbstractFunction src = (AbstractFunction) srcFci.getInvolved();
      AbstractFunction trg = targetFunction;

      String id = String.format("%s_%s_%s_%s", ID_FAKE_FUNCTIONAL_EXCHANGE, idPrefix, src.getSid(), trg.getSid());
      String srcPortId = String.format("ID_FakeFunctionPortOut_%s_%s", src.getSid(), id);
      String trgPortId = String.format("ID_FakeFunctionPortIn_%s_%s", trg.getSid(), id);

      AbstractFunction container = (AbstractFunction) EcoreUtil2.getFirstContainer(src, FaPackage.Literals.ABSTRACT_FUNCTION);
      for (FunctionalExchange existingFE : container.getOwnedFunctionalExchanges()) {
        if (existingFE.getSid().equals(id)) {
          fe = existingFE;
          break;
        }
      }

      if (fe == null) {
        String feName = String.format("FakeFE_%s_%s", src.getName(), trg.getName());
        String outPortName = "out_" + feName;
        String inPortName = "in_" + feName;
        FunctionOutputPort srcPort = (FunctionOutputPort) getOrCreateFakePort(srcPortId, outPortName, src, false, context_p);
        FunctionInputPort trgPort = (FunctionInputPort) getOrCreateFakePort(trgPortId, inPortName, trg, true, context_p);

        fe = FaFactory.eINSTANCE.createFunctionalExchange();
        fe.setSid(id);
        fe.setId(id);
        fe.setDescription(description);
        fe.setName(feName);

        fe.setSource(srcPort);
        fe.setTarget(trgPort);
        container.getOwnedFunctionalExchanges().add(fe);
      }
    }
    return fe;
  }

  private FunctionPort getOrCreateFakePort(String id, String name, AbstractAction fct, boolean input, IContext context_p) {
    FunctionPort res = null;

    if (res == null) {
      if (input) {
        for (InputPin port : fct.getInputs()) {
          if (port.getId().equals(id)) {
            res = (FunctionPort) port;
            break;
          }
        }

        if (res == null) {
          res = FaFactory.eINSTANCE.createFunctionInputPort();
          fct.getInputs().add((FunctionInputPort) res);
        }
      } else {
        for (OutputPin port : fct.getOutputs()) {
          if (port.getId().equals(id)) {
            res = (FunctionPort) port;
            break;
          }
        }

        if (res == null) {
          res = FaFactory.eINSTANCE.createFunctionOutputPort();
          fct.getOutputs().add((FunctionOutputPort) res);
        }
      }
      res.setSid(id);
      res.setId(id);
      res.setName(name);
    }

    return res;
  }
}
