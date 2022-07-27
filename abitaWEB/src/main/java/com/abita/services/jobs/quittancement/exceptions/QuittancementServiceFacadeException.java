/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.quittancement.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception de la facade pour les PDF
 * @author
 *
 */
public class QuittancementServiceFacadeException extends GenericServiceException {

  /** serial ID */
  private static final long serialVersionUID = -1337964074965172018L;

  /**
   * Constructor
   */
  public QuittancementServiceFacadeException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public QuittancementServiceFacadeException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public QuittancementServiceFacadeException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public QuittancementServiceFacadeException(String message, Exception cause) {
    super(message, cause);
  }
}
