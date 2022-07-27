/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.housing.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des logements
 *
 * @author
 */
public class HousingDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 3153791287701542353L;

  /**
   * Constructor
   */
  public HousingDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HousingDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HousingDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HousingDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
