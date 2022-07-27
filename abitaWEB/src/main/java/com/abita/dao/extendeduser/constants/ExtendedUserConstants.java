/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.extendeduser.constants;

/**
 * Classe de constante des DAO des utilisateurs étendus
 * @author
 *
 */
public final class ExtendedUserConstants {

  /**
   * Constructeur privé
   */
  private ExtendedUserConstants() {
  }

  /** Requête de recherche des tiers sans critères
   * Attention! :
   * le 1=1 (condition toujours vraie) permet d'ajouter directement des clauses de recherche avec AND sans être obligé de tester les critères au préalable. */
  public static final String SEARCH_USER_WITHOUT_CRITERIA = "SELECT DISTINCT eu from ExtendedUser as eu left join eu.agencies as ag where 1=1";

  /** Critère de recherche par statut du compte utilisateur. */
  public static final String SEARCH_USER_BY_ACTIVATION_STATUS = " and eu.activated = :activated";

  /** Critère de recherche par statut de manager */
  public static final String SEARCH_USER_BY_TPC_MANAGER_STATUS = " and eu.isThirdPartyContractManager = :isThirdPartyContractManager";

  /** Critère de recherche par statut de manager */
  public static final String SEARCH_USER_BY_AGENCY = " and ag.id = :idAgency";

  /** Critère de recherche par groupes, ouverture de la condition. */
  public static final String SEARCH_USER_BY_GROUPS_START_CLAUSE = " and (0=1";

  /** Critère de recherche par groupes, clause de comparaison. */
  public static final String SEARCH_USER_BY_GROUPS_OR = " or eu.groups like :group";

  /** Critère de recherche par groupes, fermeture de la condition. */
  public static final String SEARCH_USER_BY_GROUPS_END_CLAUSE = ")";
}
