package com.abita.dao.extendeduser.exception;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO de l'administration des utilisateurs étendus
 *
 * @author
 */
public class ExtendedUserDAOException extends GenericDAOException {

  /** SerialID */
  private static final long serialVersionUID = 8067790705186266632L;

  /**
   * Constructor
   */
  public ExtendedUserDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ExtendedUserDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ExtendedUserDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ExtendedUserDAOException(String message, Exception cause) {
    super(message, cause);
  }
}
