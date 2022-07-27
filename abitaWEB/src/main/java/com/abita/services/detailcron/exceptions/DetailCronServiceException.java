/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.detailcron.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service de l'adminstration des détails cron
 *
 * @author
 */
public class DetailCronServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = 7288100707939178648L;

  /**
   * Constructor
   */
  public DetailCronServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public DetailCronServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public DetailCronServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public DetailCronServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
