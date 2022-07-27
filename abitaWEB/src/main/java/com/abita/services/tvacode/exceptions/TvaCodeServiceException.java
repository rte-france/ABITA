/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.tvacode.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe gérant les exceptions des services de la gestion des codes TVA
 *
 * @author
 */
public class TvaCodeServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 4863512069075636020L;

  /**
   * Constructor
   */
  public TvaCodeServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public TvaCodeServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public TvaCodeServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public TvaCodeServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
