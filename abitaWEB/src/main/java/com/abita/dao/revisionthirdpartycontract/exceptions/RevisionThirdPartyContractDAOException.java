/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.revisionthirdpartycontract.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d’exception spécifique aux DAO des révisions des contrats tiers
 */
public class RevisionThirdPartyContractDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 2623686839978889652L;

  /**
   * Constructor
   */
  public RevisionThirdPartyContractDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public RevisionThirdPartyContractDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public RevisionThirdPartyContractDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public RevisionThirdPartyContractDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
