/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.contract.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des contrat occupant
 *
 * @author
 */
public class ContractDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 9204961616463205856L;

  /**
   * Constructor
   */
  public ContractDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ContractDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ContractDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ContractDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
