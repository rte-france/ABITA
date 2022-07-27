/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.fieldofactivity.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service de l'adminstration des domaines d'activité
 *
 * @author
 */
public class FieldOfActivityServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = 4386557055207827230L;

  /**
   * Constructor
   */
  public FieldOfActivityServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public FieldOfActivityServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public FieldOfActivityServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public FieldOfActivityServiceException(String message, Exception cause) {
    super(message, cause);
  }
}
