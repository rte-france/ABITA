package com.abita.services.roomcategory.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception du service des catégories de local
 *
 * @author
 */
public class RoomCategoryServiceException extends GenericServiceException {

  /** serialVersionUID */
  private static final long serialVersionUID = -8354682041124710906L;

  /**
   * Constructor
   */
  public RoomCategoryServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public RoomCategoryServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public RoomCategoryServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public RoomCategoryServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
