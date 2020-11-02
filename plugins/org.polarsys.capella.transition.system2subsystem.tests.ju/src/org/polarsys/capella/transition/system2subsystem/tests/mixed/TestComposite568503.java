/*******************************************************************************
 * Copyright (c) 2020 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *    Thales - initial API and implementation
 *******************************************************************************/
package org.polarsys.capella.transition.system2subsystem.tests.mixed;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvement;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementFunction;
import org.polarsys.capella.core.data.fa.FunctionalChainInvolvementLink;
import org.polarsys.capella.core.data.fa.FunctionalChainReference;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.transition.system2subsystem.rules.fa.FunctionalChainInvolvementRule;
import org.polarsys.capella.transition.system2subsystem.rules.fa.FunctionalChainRule;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Crossphase;
import org.polarsys.capella.transition.system2subsystem.tests.System2SubsystemTest.Interphase;

public class TestComposite568503 extends System2SubsystemTest implements Interphase, Crossphase {

  public static final String SUBCUTTED1 = "f310b2ee-c8a7-4038-a644-bb4d40627065"; //$NON-NLS-1$
  public static final String MAIN1 = "2436a10d-7ed4-4049-8840-5444ae27ccc5"; //$NON-NLS-1$
  public static final String MAIN2NOPRIMARYFUNCTIONS = "8c560adc-d219-45f6-a7dc-b270332ee2ac"; //$NON-NLS-1$
  public static final String SUB2 = "b83b0a73-ccf4-4e02-8009-a130d4e3c3d3"; //$NON-NLS-1$
  public static final String MAINDROPPEDSUB = "2491bf3e-e1fe-41e6-84bc-5a131f0b27d6"; //$NON-NLS-1$
  public static final String DROPPEDSUB = "a1e38021-673b-4e2a-8391-42bf9215c5e7"; //$NON-NLS-1$
  public static final String SUB4 = "c3ebee6d-8935-4cf6-9e62-43cc170c3f0c"; //$NON-NLS-1$
  public static final String SUB5CUTTED = "037256e8-f75d-4f19-978e-50173cd7f8bc"; //$NON-NLS-1$
  public static final String MAINSUB45 = "06570814-3b3d-4898-8ee0-a0bd975b156c"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_13 = "d6540774-7eba-4781-819c-61c823b61b26"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_12 = "80084302-e796-4be8-abd9-5e3d5d789d1a"; //$NON-NLS-1$
  public static final String MAININVFUNC = "68b33122-a672-48f1-802d-7269eacaa03d"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_14 = "37d97a54-8a76-40b7-8213-7cddea3de868"; //$NON-NLS-1$
  public static final String FUNCTIONALCHAIN_15 = "140ce902-c323-4a56-b2ab-f2023759c7b0"; //$NON-NLS-1$
  public static final String MAINFAKEFE = "beecfd69-5202-4b28-b8dd-3b43e37c927b"; //$NON-NLS-1$
  public static final String PHYSICALFUNCTION_7 = "55526991-3928-412a-8177-868d0bcb8755"; //$NON-NLS-1$
  public static final String PHYSICALFUNCTION_12 = "aac9c870-60ea-4eb2-8c16-4a5dd8e161f6"; //$NON-NLS-1$
  public static final String PHYSICALFUNCTION_13 = "043c2e24-34e7-402f-95a6-c99d3887d279"; //$NON-NLS-1$
  public static final String PHYSICALFUNCTION_20 = "b42793ad-f387-4245-a6bf-c03c1950adca"; //$NON-NLS-1$
  public static final String PHYSICALFUNCTION_19 = "30ce0602-7f15-4100-9a2e-e1ad70f268b1"; //$NON-NLS-1$
  public static final String PHYSICALFUNCTION_28 = "35cb76cd-c6d5-4d13-9037-48dcbdfcd71c"; //$NON-NLS-1$
  public static final String PC_2 = "3cef204b-79ac-41b1-8130-a1123736ea48"; //$NON-NLS-1$

  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_LINK_TO_PHYSICALFUNCTION_28 = "c8413d63-9f94-4e88-88f9-271de9950130"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_REFERENCE_TO_FUNCTIONALCHAIN_14 = "8fd2cd4b-d588-4073-b37d-525a5f3617d5"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_REFERENCE_TO_FUNCTIONALCHAIN_15 = "883800ee-65a4-40db-a48d-be88a26070cc"; //$NON-NLS-1$

  public static final String B = "dad4f386-b6f9-4691-a493-5ea4cb3c8285"; //$NON-NLS-1$
  public static final String BB = "0e48d080-84b4-4a4b-99bc-b8b2166497d9"; //$NON-NLS-1$
  public static final String BBB = "cb4d5b60-e0a1-45ed-b766-2903fac94aa4"; //$NON-NLS-1$
  public static final String B2 = "3e2edab9-daa2-4d07-8d0f-13c47f09c367"; //$NON-NLS-1$
  public static final String BB2 = "58fcdb66-166f-4731-846a-fe869b2f8a83"; //$NON-NLS-1$
  public static final String CC = "551a196b-fd63-47b2-af42-caa80c5cf23e"; //$NON-NLS-1$
  public static final String FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PHYSICALFUNCTION_33 = "34c170ea-fde6-4d29-80c0-b823455fb4f7"; //$NON-NLS-1$

  public static final String BBB_FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PHYSICALFUNCTION_32 = "d34d496c-ca36-4ccd-8df0-30dbb12c0ebc"; //$NON-NLS-1$
  public static final String CC_FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PHYSICALFUNCTION_34 = "b6be540f-ae43-4377-9029-17acbdbeb7a0"; //$NON-NLS-1$
  public static final String BB_FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PHYSICALFUNCTION_34 = "23522280-e9d1-4729-9863-27aaabe57940"; //$NON-NLS-1$
  public static final String BB_FUNCTIONAL_CHAIN_REFERENCE_TO_BBB = "411900fb-dcff-4581-a7fd-ba5e206c6373"; //$NON-NLS-1$

  public static final String B2_FUNCTIONAL_CHAIN_REFERENCE_TO_BBB = "2e22b218-e610-4b4d-ab57-e0888f802ed5"; //$NON-NLS-1$
  public static final String BB2_FUNCTIONAL_CHAIN_REFERENCE_TO_CC = "3704f9c0-2f6e-49da-b709-dd66dbc03cab"; //$NON-NLS-1$
  public static final String B2_FUNCTIONAL_CHAIN_REFERENCE_TO_BB2 = "404106f6-8f72-4284-8caa-36eeb56ed0e8"; //$NON-NLS-1$
  public static final String BB2_FUNCTIONAL_CHAIN_REFERENCE_TO_BBB = "26934ac6-7341-4d0e-b3ce-65bb8d2e6f5d"; //$NON-NLS-1$

  @Override
  protected Collection<?> getProjectionElements() {
    return getObjects(PC_2);
  }

  @Override
  protected void verify() {

    checkHelpers();

    shouldNotBeTransitioned(PHYSICALFUNCTION_7);
    shouldNotBeTransitioned(PHYSICALFUNCTION_12);
    shouldNotBeTransitioned(PHYSICALFUNCTION_13);
    shouldNotBeTransitioned(PHYSICALFUNCTION_20);
    shouldNotBeTransitioned(PHYSICALFUNCTION_19);
    shouldNotBeTransitioned(PHYSICALFUNCTION_28);

    // A sub chain involving unrelated functions shall be cut and reference a "fake exchange" between cuts
    FunctionalChain subCutted = (FunctionalChain) mustBeTransitioned(SUBCUTTED1);
    assertTrue(referenceFakeExchange(subCutted));

    // A main chain involving sub chain shall be propagated even if main chain doesn't reference no other primary
    // functions
    FunctionalChain mainNoPrimary = (FunctionalChain) mustBeTransitioned(MAIN2NOPRIMARYFUNCTIONS);
    assertTrue(referenceSubChain(mainNoPrimary, (FunctionalChain) mustBeTransitioned(SUB2)));

    // A sub chain involving only unrelated function shall be avoided, and the main chain doesn't refer to it
    shouldNotBeTransitioned(DROPPEDSUB);
    FunctionalChain mainDroppedSub = (FunctionalChain) mustBeTransitioned(MAINDROPPEDSUB);
    assertTrue(referenceFakeExchange(mainDroppedSub));

    // A sub chain shall be created even if there is only one function in it.
    FunctionalChain sub5cutted = (FunctionalChain) mustBeTransitioned(SUB5CUTTED);
    assertTrue(sub5cutted.getOwnedFunctionalChainInvolvements().size() == 1);

    // A main chain involving 2 chains with a related FunctionLinkFunction between them shall still contain it
    // afterwards
    FunctionalChain mainInvFunc = (FunctionalChain) mustBeTransitioned(MAININVFUNC);
    assertTrue(mainInvFunc.getOwnedFunctionalChainInvolvements().size() == 3);

    // A main chain involving 2 chains with an unrelated FunctionLinkFunction between them shall refer to a fake
    // exchange instead, using the same hierarchy
    FunctionalChain mainFakeFE = (FunctionalChain) mustBeTransitioned(MAINFAKEFE);
    assertTrue(referenceFakeExchange(mainFakeFE));
    FunctionalChainInvolvementLink link = referencingFakeExchange(mainFakeFE);
    assertTrue(link.getSourceReferenceHierarchy()
        .contains(mustBeTransitioned(FUNCTIONAL_CHAIN_REFERENCE_TO_FUNCTIONALCHAIN_14)));
    assertTrue(link.getTargetReferenceHierarchy()
        .contains(mustBeTransitioned(FUNCTIONAL_CHAIN_REFERENCE_TO_FUNCTIONALCHAIN_15)));

    ensureHierarchy((FunctionalChain) mustBeTransitioned(MAIN1));
    ensureHierarchy((FunctionalChain) mustBeTransitioned(MAINDROPPEDSUB));
    ensureHierarchy((FunctionalChain) mustBeTransitioned(MAININVFUNC));
    ensureHierarchy((FunctionalChain) mustBeTransitioned(MAINFAKEFE));

    // When a chain is used in several location, if one of its function is not propagated and a fakeExchange is created,
    // then we need to create several involvement links which correct referenceHierarchies
    mustBeTransitioned(BBB);
    shouldNotBeTransitioned(FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PHYSICALFUNCTION_33);

    // BB, B2 and BB2 shall contain each a new involvement link
    FunctionalChain bb = (FunctionalChain) mustBeTransitioned(BB);
    assertTrue(referenceFakeExchange(bb));
    assertTrue(referencingFakeExchanges(bb).size() == 1);
    link = referencingFakeExchange(bb);

    assertTrue(link.getSourceReferenceHierarchy().contains(mustBeTransitioned(BB_FUNCTIONAL_CHAIN_REFERENCE_TO_BBB)));
    assertTrue(toString(link.getSourceReferenceHierarchy()).equals("BBB"));
    assertTrue(link.getTargetReferenceHierarchy().isEmpty());

    FunctionalChain b2 = (FunctionalChain) mustBeTransitioned(B2);
    assertTrue(referenceFakeExchange(b2));
    assertTrue(referencingFakeExchanges(b2).size() == 1);
    link = referencingFakeExchange(b2);
    assertTrue(link.getSourceReferenceHierarchy().contains(mustBeTransitioned(B2_FUNCTIONAL_CHAIN_REFERENCE_TO_BBB)));
    assertTrue(toString(link.getSourceReferenceHierarchy()).equals("BBB"));
    assertTrue(link.getTargetReferenceHierarchy().contains(mustBeTransitioned(BB2_FUNCTIONAL_CHAIN_REFERENCE_TO_CC)));
    assertTrue(link.getTargetReferenceHierarchy().contains(mustBeTransitioned(B2_FUNCTIONAL_CHAIN_REFERENCE_TO_BB2)));
    assertTrue(toString(link.getTargetReferenceHierarchy()).equals("CC/BB2"));

    FunctionalChain bb2 = (FunctionalChain) mustBeTransitioned(BB2);
    assertTrue(referenceFakeExchange(bb2));
    assertTrue(referencingFakeExchanges(bb2).size() == 1);
    link = referencingFakeExchange(bb2);
    assertTrue(link.getSourceReferenceHierarchy().contains(mustBeTransitioned(BB2_FUNCTIONAL_CHAIN_REFERENCE_TO_BBB)));
    assertTrue(toString(link.getSourceReferenceHierarchy()).equals("BBB"));
    assertTrue(link.getTargetReferenceHierarchy().contains(mustBeTransitioned(BB2_FUNCTIONAL_CHAIN_REFERENCE_TO_CC)));
    assertTrue(toString(link.getTargetReferenceHierarchy()).equals("CC"));

  }

  /**
   * Check rule helpers
   * 
   * FunctionalChainInvolvementRule.getPaths, FunctionalChainInvolvementRule.createFullPath,
   * AbstractFunctionRule.getAllReferencingChains
   */
  protected void checkHelpers() {

    final FunctionalChainInvolvementRule rule = new FunctionalChainInvolvementRule();
    final FunctionalChainInvolvementFunction inv = getObject(
        BBB_FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PHYSICALFUNCTION_32);
    Collection<String> paths = rule.getPaths(inv).stream().map(x -> toString(rule.createFullPath(inv, x)))
        .collect(Collectors.toList());
    assertTrue(paths.size() == 3);
    assertTrue(paths.contains("BBB/B2"));
    assertTrue(paths.contains("BBB/BB2/B2"));
    assertTrue(paths.contains("BBB/BB/B"));

    final FunctionalChainInvolvementFunction inv2 = getObject(
        CC_FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PHYSICALFUNCTION_34);
    paths = rule.getPaths(inv2).stream().map(x -> toString(rule.createFullPath(inv2, x))).collect(Collectors.toList());
    assertTrue(paths.size() == 1);
    assertTrue(paths.contains("CC/BB2/B2"));

    final FunctionalChainInvolvementFunction inv3 = getObject(
        BB_FUNCTIONAL_CHAIN_INVOLVEMENT_FUNCTION_TO_PHYSICALFUNCTION_34);
    paths = rule.getPaths(inv3).stream().map(x -> toString(rule.createFullPath(inv3, x))).collect(Collectors.toList());
    assertTrue(paths.size() == 1);
    assertTrue(paths.contains("BB/B"));

    assertTrue(equalsUnordered(toName(new FunctionalChainRule().getAllReferencingChains(getObject(BBB))),
        Arrays.asList("BB", "B", "B2", "BB2")));
    assertTrue(equalsUnordered(toName(new FunctionalChainRule().getAllReferencingChains(getObject(CC))),
        Arrays.asList("B2", "BB2")));
  }

  /**
   * Returns whether the first list contains duplicates and all elements of second and only them, unordered
   */
  private <T> boolean equalsUnordered(Collection<T> o, Collection<T> r) {
    HashSet<T> result = new HashSet<T>(o);
    result.retainAll(r);
    return result.size() == o.size() && result.size() == r.size();
  }

  /**
   * Return a list of all names of given objects
   */
  private <T> Collection<String> toName(Collection<T> arrayList) {
    return arrayList.stream().map(fcr -> getName(fcr)).collect(Collectors.toList());
  }

  /**
   * Return a string of all names of the given objects separated by /
   */
  private <T> String toString(Collection<T> arrayList) {
    return arrayList.stream().map(fcr -> getName(fcr)).collect(Collectors.joining("/"));
  }

  private <T> String getName(T obj) {
    if (obj instanceof FunctionalChain) {
      return ((FunctionalChain) obj).getName();
    }
    return ((FunctionalChain) ((FunctionalChainReference) obj).getInvolved()).getName();
  }

  /**
   * Ensure that all links referring to sub chains have a source/target hierarchy if necessary
   */
  private void ensureHierarchy(FunctionalChain chain) {
    for (FunctionalChainInvolvement inv : chain.getOwnedFunctionalChainInvolvements()) {
      if (inv instanceof FunctionalChainInvolvementLink) {
        FunctionalChainInvolvementLink link = (FunctionalChainInvolvementLink) inv;
        if (link.getSource().eContainer() != link.getTarget().eContainer()) {
          if (link.getSource().eContainer() != chain) {
            assertTrue(!link.getSourceReferenceHierarchy().isEmpty());
          }
          if (link.getTarget().eContainer() != chain) {
            assertTrue(!link.getTargetReferenceHierarchy().isEmpty());
          }
        }
      }
    }
  }

  /**
   * Returns whether the chain references the sub chain
   */
  private boolean referenceSubChain(FunctionalChain chain, FunctionalChain sub) {
    return chain.getOwnedFunctionalChainInvolvements().stream().filter(x -> referenceSubChain(x, sub)).findFirst()
        .isPresent();
  }

  private boolean referenceSubChain(FunctionalChainInvolvement f, FunctionalChain sub) {
    return f.getInvolved().equals(sub);
  }

  /**
   * Returns the first involvement referencing a fake exchange created while transition
   */
  private FunctionalChainInvolvementLink referencingFakeExchange(FunctionalChain chain) {
    return (FunctionalChainInvolvementLink) chain.getOwnedFunctionalChainInvolvements().stream()
        .filter(this::referenceFakeExchange).findFirst().get();
  }

  /**
   * Returns the involvements referencing a fake exchange created while transition
   */
  private List<FunctionalChainInvolvementLink> referencingFakeExchanges(FunctionalChain chain) {
    return chain.getOwnedFunctionalChainInvolvements().stream().filter(this::referenceFakeExchange)
        .map(FunctionalChainInvolvementLink.class::cast).collect(Collectors.toList());
  }

  /**
   * Returns whether the chain references a fake exchange created while transition
   */
  private boolean referenceFakeExchange(FunctionalChain chain) {
    return referencingFakeExchange(chain) != null;
  }

  private boolean referenceFakeExchange(FunctionalChainInvolvement f) {
    return f instanceof FunctionalChainInvolvementLink && f.getInvolved() instanceof FunctionalExchange
        && ((FunctionalExchange) f.getInvolved()).getName().contains("Fake");
  }

  @Override
  public List<String> getRequiredTestModels() {
    return Arrays.asList("568503", "output"); //$NON-NLS-1$ //$NON-NLS-2$
  }
}
