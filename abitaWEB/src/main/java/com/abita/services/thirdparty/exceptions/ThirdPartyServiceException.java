/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.thirdparty.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception des services des tiers
 * @author
 *
 */
public class ThirdPartyServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -3281266897002280860L;

  /**
   * Constructor
   */
  public ThirdPartyServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ThirdPartyServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ThirdPartyServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ThirdPartyServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
