package com.abita.services.housing.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des logements
 *
 * @author
 */
public class HousingServiceFacadeException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = 1990694281858638920L;

  /**
   * Constructor
   */
  public HousingServiceFacadeException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HousingServiceFacadeException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HousingServiceFacadeException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HousingServiceFacadeException(String message, Exception cause) {
    super(message, cause);
  }

}
