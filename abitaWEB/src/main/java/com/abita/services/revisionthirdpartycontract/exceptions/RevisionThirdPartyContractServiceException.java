package com.abita.services.revisionthirdpartycontract.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d’exception du service des révisions des contrats tiers
 */
public class RevisionThirdPartyContractServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -4791680847022820172L;

  /** Constructor */
  public RevisionThirdPartyContractServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public RevisionThirdPartyContractServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public RevisionThirdPartyContractServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public RevisionThirdPartyContractServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
