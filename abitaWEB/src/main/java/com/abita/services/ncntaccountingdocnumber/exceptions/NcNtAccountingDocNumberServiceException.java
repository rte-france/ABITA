package com.abita.services.ncntaccountingdocnumber.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception spécifique aux services des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public class NcNtAccountingDocNumberServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 3073678589874385221L;

  /**
   * Constructor
   */
  public NcNtAccountingDocNumberServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public NcNtAccountingDocNumberServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public NcNtAccountingDocNumberServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public NcNtAccountingDocNumberServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
