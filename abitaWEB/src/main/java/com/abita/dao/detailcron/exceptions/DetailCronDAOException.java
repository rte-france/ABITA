package com.abita.dao.detailcron.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO de l'administration du détail des cron
 *
 * @author
 */
public class DetailCronDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1366095800767840930L;

  /**
   * Constructor
   */
  public DetailCronDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public DetailCronDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public DetailCronDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public DetailCronDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
