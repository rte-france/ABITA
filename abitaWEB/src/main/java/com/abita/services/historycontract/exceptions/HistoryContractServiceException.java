package com.abita.services.historycontract.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service de l'historisation des contrats occupant
 * @author
 *
 */
public class HistoryContractServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1816137963960073437L;

  /**
   * Constructor
   */
  public HistoryContractServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryContractServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryContractServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryContractServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
