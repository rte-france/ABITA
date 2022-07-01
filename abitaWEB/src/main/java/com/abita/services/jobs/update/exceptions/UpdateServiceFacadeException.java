package com.abita.services.jobs.update.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception des jobs pour la mise à jour des données
 */
public class UpdateServiceFacadeException extends GenericServiceException {

  /**
   * Serial ID
   */
  private static final long serialVersionUID = -6362177531003748866L;

  /**
   * Constructor
   */
  public UpdateServiceFacadeException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public UpdateServiceFacadeException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public UpdateServiceFacadeException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public UpdateServiceFacadeException(String message, Exception cause) {
    super(message, cause);
  }

}
