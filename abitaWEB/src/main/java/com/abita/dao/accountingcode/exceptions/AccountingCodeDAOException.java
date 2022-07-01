package com.abita.dao.accountingcode.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception des DAO de la gestion des codes comptables
 * @author
 *
 */
public class AccountingCodeDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 4449445004401323174L;

  /**
   * Constructor
   */
  public AccountingCodeDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public AccountingCodeDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public AccountingCodeDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public AccountingCodeDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
