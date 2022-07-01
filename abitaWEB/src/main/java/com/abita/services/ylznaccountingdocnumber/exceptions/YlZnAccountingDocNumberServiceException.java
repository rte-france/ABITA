package com.abita.services.ylznaccountingdocnumber.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception spécifique aux services des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public class YlZnAccountingDocNumberServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 557945606275340034L;

  /**
   * Constructor
   */
  public YlZnAccountingDocNumberServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public YlZnAccountingDocNumberServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public YlZnAccountingDocNumberServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public YlZnAccountingDocNumberServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
