/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.constants;

/**
 * Classe de constante générique aux DAO
 * @author
 *
 */
public final class GenericConstantsDAO {

  /**
   * Constructeur privé
   */
  private GenericConstantsDAO() {
  }

  /**
   * Charactères non accentué pour transformation dans les chaines de requête des DAO
   */
  public static final String UNACCENTED_CHARACTER = "'eeaecu'";

  /**
   * Charactères accentué pour transformation dans les chaines de requête des DAO
   */
  public static final String ACCENTED_CHARACTER = "'eeaecu'";

}
