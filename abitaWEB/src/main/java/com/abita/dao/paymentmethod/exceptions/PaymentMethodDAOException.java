/**
 *
 */
package com.abita.dao.paymentmethod.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des méthodes de paiement
 * @author
 *
 */
public class PaymentMethodDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -7802532438100394814L;

  /**
   * Constructor
   */
  public PaymentMethodDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public PaymentMethodDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public PaymentMethodDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public PaymentMethodDAOException(String message, Exception cause) {
    super(message, cause);
  }
}
