/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.termination.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des résiliation de contrat
 *
 * @author
 */
public class TerminationDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -39990772132925758L;

  /**
   * Constructor
   */
  public TerminationDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public TerminationDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public TerminationDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public TerminationDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
