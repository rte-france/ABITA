package com.services.common.exception;

/**
 * An Exception of  XMLUtil
 * @see XMLUtil
 * @author
 *
 */
public class XMLException extends RuntimeException {
    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     * Construct XMLException
     */
    public XMLException() {
        //
    }
  /**
   *
   * Construct XMLException
   * @param message exception message
   */
    public XMLException(String message) {
        super(message);
    }
    /**
     *
     * Construct XMLException
     * @param message exception message
     * @param cause exception cause
     */
    public XMLException(String message, Throwable cause) {
        super(message, cause);
    }
   /**
    *
    * Construct XMLException
    * @param cause exception cause
    */
    public XMLException(Throwable cause) {
        super(cause);
    }
}
