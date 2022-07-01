package com.abita.services.revaluationrents.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception du service de l’adminstration des revalorisations des loyers
 */
public class RevaluationRentsServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -4791680847022820172L;

  /** Constructor */
  public RevaluationRentsServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public RevaluationRentsServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public RevaluationRentsServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public RevaluationRentsServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
