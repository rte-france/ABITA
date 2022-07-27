/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.tenant.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des occupants
 *
 * @author
 */
public class TenantServiceFacadeException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = 3592469441449466476L;

  /**
   * Constructor
   */
  public TenantServiceFacadeException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public TenantServiceFacadeException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public TenantServiceFacadeException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public TenantServiceFacadeException(String message, Exception cause) {
    super(message, cause);
  }

}
