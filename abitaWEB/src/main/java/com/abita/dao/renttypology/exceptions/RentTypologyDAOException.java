/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.dao.renttypology.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des typologies de loyer
 *
 * @author
 */
public class RentTypologyDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -39990772132925758L;

  /**
   * Constructor
   */
  public RentTypologyDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public RentTypologyDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public RentTypologyDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public RentTypologyDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
