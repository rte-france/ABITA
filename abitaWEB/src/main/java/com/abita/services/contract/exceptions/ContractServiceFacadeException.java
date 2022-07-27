/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.contract.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des contrats Occupants
 *
 * @author
 */
public class ContractServiceFacadeException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -4283633593478406518L;

  /**
   * Constructor
   */
  public ContractServiceFacadeException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ContractServiceFacadeException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ContractServiceFacadeException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ContractServiceFacadeException(String message, Exception cause) {
    super(message, cause);
  }

}
