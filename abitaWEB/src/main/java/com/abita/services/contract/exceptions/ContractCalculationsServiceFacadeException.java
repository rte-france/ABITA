package com.abita.services.contract.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception du service de calcul des contrats occupants
 */
public class ContractCalculationsServiceFacadeException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -453996671758868402L;

  /**
   * Constructor
   */
  public ContractCalculationsServiceFacadeException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ContractCalculationsServiceFacadeException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ContractCalculationsServiceFacadeException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ContractCalculationsServiceFacadeException(String message, Exception cause) {
    super(message, cause);
  }

}
