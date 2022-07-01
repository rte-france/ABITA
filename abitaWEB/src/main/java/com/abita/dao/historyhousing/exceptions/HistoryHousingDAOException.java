/**
 *
 */
package com.abita.dao.historyhousing.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO pour l'historisation des logements
 * @author
 *
 */
public class HistoryHousingDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1544623822339535985L;

  /**
   * Constructor
   */
  public HistoryHousingDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryHousingDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryHousingDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryHousingDAOException(String message, Exception cause) {
    super(message, cause);
  }
}
