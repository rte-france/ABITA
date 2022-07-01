package com.abita.dao.historybenefits.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d’exception spécifique aux DAO de l’historisation des avantages en nature
 */
public class HistoryBenefitsDAOException extends GenericDAOException {

  /** Serial ID */
  private static final long serialVersionUID = 3084117981871042714L;

  /**
   * Constructor
   */
  public HistoryBenefitsDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryBenefitsDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryBenefitsDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryBenefitsDAOException(String message, Exception cause) {
    super(message, cause);
  }
}
