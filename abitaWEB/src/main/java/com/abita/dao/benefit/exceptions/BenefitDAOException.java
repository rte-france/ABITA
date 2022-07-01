package com.abita.dao.benefit.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO de l'administration des barèmes pour le calcul des avantages en nature
 * @author
 * @version 1.0
 */
public class BenefitDAOException extends GenericDAOException {

  /** SerialID */
  private static final long serialVersionUID = 8865541599590893634L;

  /**
   * Constructor
   */
  public BenefitDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public BenefitDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public BenefitDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public BenefitDAOException(String message, Exception cause) {
    super(message, cause);
  }
}
