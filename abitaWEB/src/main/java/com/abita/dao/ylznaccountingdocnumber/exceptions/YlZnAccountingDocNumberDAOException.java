/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.ylznaccountingdocnumber.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public class YlZnAccountingDocNumberDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -2887039363326865241L;

  /**
   * Constructor
   */
  public YlZnAccountingDocNumberDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public YlZnAccountingDocNumberDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public YlZnAccountingDocNumberDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public YlZnAccountingDocNumberDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
