package com.abita.services.costcenter.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service de l'adminstration des centres coûts
 *
 * @author
 */
public class CostCenterServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -6445020090674149504L;

  /**
   * Constructor
   */
  public CostCenterServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public CostCenterServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public CostCenterServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public CostCenterServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
