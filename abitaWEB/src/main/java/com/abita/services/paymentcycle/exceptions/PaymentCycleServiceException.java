package com.abita.services.paymentcycle.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des périodicités de paiement
 *
 * @author
 */
public class PaymentCycleServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -5418383452654026360L;

  /**
   * Constructor
   */
  public PaymentCycleServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public PaymentCycleServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public PaymentCycleServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public PaymentCycleServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
