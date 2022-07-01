package com.abita.dao.thirdpartytermination.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des motifs de résiliations
 *
 * @author
 */
public class ThirdPartyTerminationDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 4922419943006268690L;

  /**
   * Constructor
   */
  public ThirdPartyTerminationDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ThirdPartyTerminationDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ThirdPartyTerminationDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ThirdPartyTerminationDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
