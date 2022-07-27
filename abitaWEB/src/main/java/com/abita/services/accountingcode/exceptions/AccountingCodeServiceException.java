/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.accountingcode.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe gérant les exceptions des services de la gestion des codes comptables
 * @author
 *
 */
public class AccountingCodeServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -6004353792523216451L;

  /**
   * Constructor
   */
  public AccountingCodeServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public AccountingCodeServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public AccountingCodeServiceException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public AccountingCodeServiceException(String message, Throwable cause) {
    super(message, cause);
  }

}
