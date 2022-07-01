package com.abita.dao.historytenant.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d’exception spécifique aux DAO de l’historisation des occupants
 */
public class HistoryTenantDAOException extends GenericDAOException {

  /** Serial ID */
  private static final long serialVersionUID = -261656283951944967L;

  /**
   * Constructor
   */
  public HistoryTenantDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryTenantDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryTenantDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryTenantDAOException(String message, Exception cause) {
    super(message, cause);
  }
}
