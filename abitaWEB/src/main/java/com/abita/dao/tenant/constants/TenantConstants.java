/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.tenant.constants;

/**
 * Classe de constante des DAO des occupants
 *
 * @author
 */
public final class TenantConstants {

  /**
   * Constructeur privé
   */
  private TenantConstants() {
  }

  /**
   * ID de l'occupant
   */
  public static final String ID_TENANT = "idTenant";

  /**
   * ID de l'occupant
   */
  public static final String REFERENCE_TENANT = "referenceTenant";

  /** Requête de recherche des occupants sans critères
   * Attention! :
   * le 1=1 (condition toujours vraie) permet d'ajouter directement des clauses de recherche avec AND sans être obligé de tester les critères au préalable. */
  public static final String SEARCH_TENANT_WITHOUT_CRITERIA = "SELECT DISTINCT t FROM ContractEntity as c RIGHT JOIN c.tenant as t LEFT JOIN c.housing as h LEFT JOIN h.agency as agc LEFT JOIN h.rattachmentSite as sit WHERE 1 = 1";

  /**
   * CRITERES OCCUPANTS
   */

  /** Critère de recherche par le booléen employée ou non. */
  public static final String SEARCH_TENANT_BY_RTEEMPLOYEE = " and t.employee = :employee";

  /** Critère de recherche par le booléen cadre ou non. */
  public static final String SEARCH_TENANT_BY_MANAGERIAL = " and t.managerial = :managerial";

  /** Critère de recherche par le booléen cadre ou non à octobre N-1. */
  public static final String SEARCH_TENANT_BY_MANAGERIAL_LAST_YEAR = " and t.managerialLastYear = :managerialLastYear";

  /** Critère de recherche par le nombre de personnes dans le foyer. */
  public static final String SEARCH_TENANT_BY_HOUSE_HOLD_SIZE = " and t.householdSize = :householdSize";

  /** Critère de recherche par le nombre de personnes dans le foyer à octobre N-1. */
  public static final String SEARCH_TENANT_BY_HOUSE_HOLD_SIZE_LAST_YEAR = " and t.householdSizeLastYear = :householdSizeLastYear";

  /** Critère de recherche par le salaire actuel. */
  public static final String SEARCH_TENANT_BY_ACTUAL_SALARY = " and t.actualSalary = :actualSalary";

  /** Critère de recherche par le salaire actuel. */
  public static final String SEARCH_TENANT_BY_REFERENCE_GROSS_SALARY = " and t.referenceGrossSalary = :referenceGrossSalary";

  /** Critère de recherche par le type d'occupant. */
  public static final String SEARCH_TENANT_BY_TYPE_TENANT = " and t.typeTenant = :typeTenant";

  /** Ordre de tri par référence (id). */
  public static final String ORDER_BY_TENANT_REFERENCE = " order by t.reference";

}
