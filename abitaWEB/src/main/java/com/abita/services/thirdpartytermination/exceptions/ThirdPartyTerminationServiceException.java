/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.thirdpartytermination.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des motifs de résiliations
 *
 * @author
 */
public class ThirdPartyTerminationServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = 2977837326684509501L;

  /**
   * Constructor
   */
  public ThirdPartyTerminationServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ThirdPartyTerminationServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ThirdPartyTerminationServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ThirdPartyTerminationServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
