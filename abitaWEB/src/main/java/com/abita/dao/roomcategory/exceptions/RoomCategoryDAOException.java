/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.roomcategory.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des catégorie du local
 *
 * @author
 */
public class RoomCategoryDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 2623686839978889652L;

  /**
   * Constructor
   */
  public RoomCategoryDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public RoomCategoryDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public RoomCategoryDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public RoomCategoryDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
