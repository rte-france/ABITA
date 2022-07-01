package com.abita.services.housingnature.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des natures
 *
 * @author
 */
public class HousingNatureServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -1680865521340336363L;

  /**
   * Constructor
   */
  public HousingNatureServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HousingNatureServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HousingNatureServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HousingNatureServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
