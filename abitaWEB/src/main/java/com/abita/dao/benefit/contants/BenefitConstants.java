/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.benefit.contants;

/**
 * Classe de constante des DAO des barèmes d'avantage en nature
 * @author
 *
 */
public final class BenefitConstants {

  /**
   * Constructeur privé
   */
  private BenefitConstants() {
  }

  /** Requête de recherche du barème correspond à une rémunération particulière */
  public static final String FIND_BENEFIT_BY_SALARY = "from SalaryLevelEntity where :salaryValue - minimumThreshold >=0 order by :salaryValue - minimumThreshold ";

  /** Requête de recherche du barème correspond à une rémunération particulière */
  public static final String FIND_ALL_SORTED_BENEFIT = "from SalaryLevelEntity order by minimumThreshold ";

}
