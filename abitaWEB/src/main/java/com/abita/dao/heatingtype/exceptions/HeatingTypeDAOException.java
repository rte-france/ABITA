/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.heatingtype.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d’exception spécifique aux DAO de l’administration des types de chauffage
 */
public class HeatingTypeDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -2917436262569409503L;

  /**
   * Constructor
   */
  public HeatingTypeDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HeatingTypeDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HeatingTypeDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HeatingTypeDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
