package com.abita.dao.agency.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d’exception spécifique aux DAO de l’administration des agences
 */
public class AgencyDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 2623686839978889652L;

  /**
   * Constructor
   */
  public AgencyDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public AgencyDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public AgencyDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public AgencyDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
