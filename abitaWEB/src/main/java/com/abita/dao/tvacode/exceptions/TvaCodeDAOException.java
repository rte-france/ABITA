/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.tvacode.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception des DAO de la gestion des codes TVA
 *
 * @author
 */
public class TvaCodeDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 4449445004401323174L;

  /**
   * Constructor
   */
  public TvaCodeDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public TvaCodeDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public TvaCodeDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public TvaCodeDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
