package com.abita.services.historyhousing.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service de l'historisation des logements
 * @author
 *
 */
public class HistoryHousingServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 7881167312238203877L;

  /**
   * Constructor
   */
  public HistoryHousingServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryHousingServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryHousingServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryHousingServiceException(String message, Exception cause) {
    super(message, cause);
  }
}
