package com.abita.dao.fieldofactivity.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO de l'administration des domaines d'activité
 *
 * @author
 */
public class FieldOfActivityDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1530466488768995752L;

  /**
   * Constructor
   */
  public FieldOfActivityDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public FieldOfActivityDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public FieldOfActivityDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public FieldOfActivityDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
