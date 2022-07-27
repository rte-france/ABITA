/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.reporting.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception des jobs pour GCP
 */
public class ReportingServiceFacadeException extends GenericServiceException {

  /**
   * Serial ID
   */
  private static final long serialVersionUID = 3080499184232568658L;

  /**
   * Constructor
   */
  public ReportingServiceFacadeException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ReportingServiceFacadeException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ReportingServiceFacadeException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ReportingServiceFacadeException(String message, Exception cause) {
    super(message, cause);
  }

}
