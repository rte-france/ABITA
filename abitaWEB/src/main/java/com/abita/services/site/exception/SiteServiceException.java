package com.abita.services.site.exception;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception du service des sites
 */
public class SiteServiceException extends GenericServiceException {

  private static final long serialVersionUID = 4860599475151006742L;

  /**
   * Constructor
   */
  public SiteServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public SiteServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public SiteServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause   exception's cause
   */
  public SiteServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
