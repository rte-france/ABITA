package com.abita.services.historybenefits.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service de l'historisation des avantages en nature
 * @author
 *
 */
public class HistoryBenefitsServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 6339909950952717059L;

  /**
   * Constructor
   */
  public HistoryBenefitsServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HistoryBenefitsServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HistoryBenefitsServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HistoryBenefitsServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
