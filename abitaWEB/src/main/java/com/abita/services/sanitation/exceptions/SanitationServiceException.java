package com.abita.services.sanitation.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception du service des agences
 */
public class SanitationServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -6319441075995845845L;

  /**
   * Constructor
   */
  public SanitationServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public SanitationServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public SanitationServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public SanitationServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
