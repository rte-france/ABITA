/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.services.paymentmethod.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des methodes de paiement
 *
 * @author
 */
public class PaymentMethodServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -6445020090674149504L;

  /**
   * Constructor
   */
  public PaymentMethodServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public PaymentMethodServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public PaymentMethodServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public PaymentMethodServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
