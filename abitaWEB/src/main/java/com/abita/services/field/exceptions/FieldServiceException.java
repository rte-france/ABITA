/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.services.field.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des domaines d'activité
 *
 * @author
 */
public class FieldServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -6445020090674149504L;

  /**
   * Constructor
   */
  public FieldServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public FieldServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public FieldServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public FieldServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
