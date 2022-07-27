/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.airconditioner.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception du service des climatisations
 */
public class AirconditionerServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = 4812751580840206685L;

  /**
   * Constructor
   */
  public AirconditionerServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public AirconditionerServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public AirconditionerServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public AirconditionerServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
