/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.historyreporting.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d’exception spécifique aux DAO de l’historisation des reporting
 */
public class HistoryReportingDAOException extends GenericDAOException {

  /** serialVersionUID */
  private static final long serialVersionUID = 7052981854829767198L;

  /**
   * Constructor
   */
  public HistoryReportingDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryReportingDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryReportingDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryReportingDAOException(String message, Exception cause) {
    super(message, cause);
  }
}
