package com.abita.services.extendeduser.exception;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des utilisateurs étendus
 *
 * @author
 */
public class ExtendedUserServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = 2831906589450899732L;

  /**
   * Constructor
   */
  public ExtendedUserServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ExtendedUserServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ExtendedUserServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ExtendedUserServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
