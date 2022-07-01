/**
 *
 */
package com.abita.dao.historyamount.exceptions;

import com.dao.common.exception.GenericDAOException;

/** Classe d’exception spécifique aux DAO de l’historisation des montants générés
 * @author
 *
 */
public class HistoryAmountDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -2244404494667981315L;

  /**
   * Constructor
   */
  public HistoryAmountDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryAmountDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryAmountDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryAmountDAOException(String message, Exception cause) {
    super(message, cause);
  }
}
