/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.services.renttypology.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des typologies de loyer
 *
 * @author
 */
public class RentTypologyServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -6445020090674149504L;

  /**
   * Constructor
   */
  public RentTypologyServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public RentTypologyServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public RentTypologyServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public RentTypologyServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
