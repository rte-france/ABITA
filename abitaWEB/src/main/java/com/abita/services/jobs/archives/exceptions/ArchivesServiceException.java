/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.archives.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception des jobs pour l'archivage
 *
 * @author
 */
public class ArchivesServiceException extends GenericServiceException {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 8475530682543113256L;

  /**
   * Constructor
   */
  public ArchivesServiceException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public ArchivesServiceException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public ArchivesServiceException(Exception cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public ArchivesServiceException(String message, Exception cause) {
    super(message, cause);
  }

}
