/**
 *
 */
package com.abita.services.historyamount.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service de l'historisation des montants générés
 * @author
 *
 */
public class HistoryAmountServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 8670946819764761501L;

  /**
   * Constructor
   */
  public HistoryAmountServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryAmountServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryAmountServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryAmountServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
