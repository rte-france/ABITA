/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.termination.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service de l'adminstration des motifs de résiliation
 *
 * @author
 */
public class TerminationServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -6445020090674149504L;

  /**
   * Constructor
   */
  public TerminationServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public TerminationServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public TerminationServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public TerminationServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
