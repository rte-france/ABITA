/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.historyreporting.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception du service de l’historisation des reporting
 * @author
 *
 */
public class HistoryReportingServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 7881167312238203877L;

  /**
   * Constructor
   */
  public HistoryReportingServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryReportingServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryReportingServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryReportingServiceException(String message, Exception cause) {
    super(message, cause);
  }
}
