package com.abita.services.jobs.gcp.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception des jobs pour GCP
 */
public class GcpServiceException extends GenericServiceException {

  /**
   * Serial ID
   */
  private static final long serialVersionUID = 3080499184232568658L;

  /**
   * Constructor
   */
  public GcpServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public GcpServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public GcpServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public GcpServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
