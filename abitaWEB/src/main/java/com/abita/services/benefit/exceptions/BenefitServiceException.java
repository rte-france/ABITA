package com.abita.services.benefit.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * @author
 * @version 1.0
 */
public class BenefitServiceException extends GenericServiceException {

  /** SerialID */
  private static final long serialVersionUID = -5317007133845834393L;

  /**
   * Constructor
   */
  public BenefitServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public BenefitServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public BenefitServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public BenefitServiceException(String message, Exception cause) {
    super(message, cause);
  }
}
