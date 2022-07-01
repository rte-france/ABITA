package com.abita.dao.site.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d’exception spécifique aux DAO de l’administration des sites
 */
public class SiteDAOException extends GenericDAOException {

  private static final long serialVersionUID = 8876197331592944924L;

  /**
   * Constructor
   */
  public SiteDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public SiteDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public SiteDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause   exception's cause
   */
  public SiteDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
