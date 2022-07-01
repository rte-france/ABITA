package com.abita.services.thirdpartycontract.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des contrats tiers
 *
 * @author
 */
public class ThirdPartyContractServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -4283633593478406518L;

  /**
   * Constructor
   */
  public ThirdPartyContractServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ThirdPartyContractServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ThirdPartyContractServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ThirdPartyContractServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
