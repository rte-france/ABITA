/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.heatingtype.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception du service des agences
 */
public class HeatingTypeServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = 1523134600113747293L;

  /**
   * Constructor
   */
  public HeatingTypeServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HeatingTypeServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HeatingTypeServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HeatingTypeServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
