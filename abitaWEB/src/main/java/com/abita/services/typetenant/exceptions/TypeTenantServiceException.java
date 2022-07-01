package com.abita.services.typetenant.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des types d'occupant
 *
 * @author
 */
public class TypeTenantServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -3670234245385959272L;

  /**
   * Constructor
   */
  public TypeTenantServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public TypeTenantServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public TypeTenantServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public TypeTenantServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
