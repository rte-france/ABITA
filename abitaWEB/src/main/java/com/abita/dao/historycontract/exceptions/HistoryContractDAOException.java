/**
 *
 */
package com.abita.dao.historycontract.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO pour l'historisation des contrats occupant
 * @author
 *
 */
public class HistoryContractDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 3109192126893260189L;

  /**
   * Constructor
   */
  public HistoryContractDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryContractDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryContractDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryContractDAOException(String message, Exception cause) {
    super(message, cause);
  }
}
