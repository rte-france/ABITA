/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.tenant.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des occupants
 *
 * @author
 */
public class TenantDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 929001133140093932L;

  /**
   * Constructor
   */
  public TenantDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public TenantDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public TenantDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public TenantDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
