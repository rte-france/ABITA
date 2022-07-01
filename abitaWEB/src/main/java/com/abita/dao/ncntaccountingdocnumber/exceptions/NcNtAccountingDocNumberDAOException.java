package com.abita.dao.ncntaccountingdocnumber.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public class NcNtAccountingDocNumberDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 6393643763867892433L;

  /**
   * Constructor
   */
  public NcNtAccountingDocNumberDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public NcNtAccountingDocNumberDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public NcNtAccountingDocNumberDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public NcNtAccountingDocNumberDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
