package com.abita.services.historytenant.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service de l'historisation des occupants
 * @author
 *
 */
public class HistoryTenantServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -363051443014373799L;

  /**
   * Constructor
   */
  public HistoryTenantServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryTenantServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryTenantServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryTenantServiceException(String message, Exception cause) {
    super(message, cause);
  }
}
