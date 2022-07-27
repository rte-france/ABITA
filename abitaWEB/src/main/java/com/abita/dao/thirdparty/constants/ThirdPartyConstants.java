/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.thirdparty.constants;

/**
 * Classe de constante des DAO des tiers
 * @author
 *
 */
public final class ThirdPartyConstants {

  /**
   * Constructeur privé
   */
  private ThirdPartyConstants() {
  }

  /**
   * Constante de la référence des tiers
   */
  public static final String GCPREF = "gcpReference";

  /**
   * ID du ties
   */
  public static final String ID_THIRD_PARTY = "idThirdParty";

  /**
   * ID du logement
   */
  public static final String ID_HOUSING = "idHousing";

  /** Requête de recherche des tiers sans critères
   * Attention! :
   * le 1=1 (condition toujours vraie) permet d'ajouter directement des clauses de recherche avec AND sans être obligé de tester les critères au préalable. */
  public static final String SEARCH_THIRD_PARTY_WITHOUT_CRITERIA = "from ThirdPartyEntity tp where 1=1";

  /** Critère de recherche par référence (id). */
  public static final String SEARCH_THIRD_PARTY_BY_REFERENCE = " and tp.id = :id";

  /** Ordre de tri par référence (id). */
  public static final String ORDER_BY_THIRD_PARTY_REFERENCE = " order by tp.id";

}
