/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.agency.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception du service des agences
 */
public class AgencyServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -6445020090674149504L;

  /**
   * Constructor
   */
  public AgencyServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public AgencyServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public AgencyServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public AgencyServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
