/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.airconditioner.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d’exception spécifique aux DAO de l’administration des agences
 */
public class AirconditionerDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -502697819265578986L;

  /**
   * Constructor
   */
  public AirconditionerDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public AirconditionerDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public AirconditionerDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public AirconditionerDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
