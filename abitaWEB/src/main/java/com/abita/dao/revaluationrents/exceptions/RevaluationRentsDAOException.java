package com.abita.dao.revaluationrents.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d’exception spécifique aux DAO de l’administration des revalorisations des loyers
 */
public class RevaluationRentsDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 2623686839978889652L;

  /**
   * Constructor
   */
  public RevaluationRentsDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public RevaluationRentsDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public RevaluationRentsDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public RevaluationRentsDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
