/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.field.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des domaines d'activité
 *
 * @author
 */

public class FieldDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -2830057980639182387L;

  /**
   * Constructor
   */
  public FieldDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public FieldDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public FieldDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public FieldDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
