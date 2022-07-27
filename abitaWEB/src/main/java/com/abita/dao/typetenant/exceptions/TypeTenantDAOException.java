/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.typetenant.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des types d'occupant
 *
 * @author
 */
public class TypeTenantDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 4922419943006268690L;

  /**
   * Constructor
   */
  public TypeTenantDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public TypeTenantDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public TypeTenantDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public TypeTenantDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
