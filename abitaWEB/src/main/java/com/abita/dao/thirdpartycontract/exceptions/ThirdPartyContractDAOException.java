package com.abita.dao.thirdpartycontract.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des contrat tiers
 *
 * @author
 */
public class ThirdPartyContractDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 9204961616463205856L;

  /**
   * Constructor
   */
  public ThirdPartyContractDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ThirdPartyContractDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ThirdPartyContractDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ThirdPartyContractDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
