package com.abita.services.jobs.artesis.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception des jobs pour Artesis
 */
public class ArtesisServiceException extends GenericServiceException {

  /**
   * Serial ID
   */
  private static final long serialVersionUID = 1349119869554934622L;

  /**
   * Constructor
   */
  public ArtesisServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ArtesisServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ArtesisServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ArtesisServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
