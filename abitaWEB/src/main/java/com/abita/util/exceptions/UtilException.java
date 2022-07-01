/**
 *
 */
package com.abita.util.exceptions;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception générique pour les services utilitaires
 * @author
 */
public class UtilException extends GenericServiceException {

  /** SerialID */
  private static final long serialVersionUID = -4029774380155144948L;

  /**
   * Classe d'exception pour les services utilitaires
   * Utilisée pour indiquer que le seuil minimum de barème est déjà présent dans la liste des barèmes
   * @author
   */
  public static class ThresholdAlreadyUsed extends UtilException {

    /** SerialID */
    private static final long serialVersionUID = -6297631085738250574L;

    /**
     * Constructor
     */
    public ThresholdAlreadyUsed() {
      super();
    }

    /**
     * Constructor
     * @param message exception's description
     */
    public ThresholdAlreadyUsed(String message) {
      super(message);
    }

    /**
     * Constructor
     * @param cause exception's cause
     */
    public ThresholdAlreadyUsed(Throwable cause) {
      super(cause);
    }

    /**
     * Constructor
     * @param message exception's description
     * @param cause exception's cause
     */
    public ThresholdAlreadyUsed(String message, Throwable cause) {
      super(message, cause);
    }
  }

  /**
   * Constructor
   */
  public UtilException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message exception's description
   */
  public UtilException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause exception's cause
   */
  public UtilException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructor
   *
   * @param message exception's description
   * @param cause exception's cause
   */
  public UtilException(String message, Throwable cause) {
    super(message, cause);
  }

}
