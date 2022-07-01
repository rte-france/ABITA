package com.abita.dao.paymentcycle.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des périodicités de paiement
 *
 * @author
 */
public class PaymentCycleDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 4836855617602933914L;

  /**
   * Constructor
   */
  public PaymentCycleDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public PaymentCycleDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public PaymentCycleDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public PaymentCycleDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
