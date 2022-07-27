/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.housingnature.exceptions;

import com.dao.common.exception.GenericDAOException;

/**
 * Classe d'exception spécifique aux DAO des natures
 *
 * @author
 */
public class HousingNatureDAOException extends GenericDAOException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 2623686839978889652L;

  /**
   * Constructor
   */
  public HousingNatureDAOException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public HousingNatureDAOException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public HousingNatureDAOException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public HousingNatureDAOException(String message, Exception cause) {
    super(message, cause);
  }

}
