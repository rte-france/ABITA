package com.services.common.exception;
/**
 *
 * @author
 *
 */
public class AppException extends Exception {

    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * Construct AppException.
     */
    public AppException() {
        //
    }

    /**
     *
     * Construct AppException.
     * @param message exception message
     */
    public AppException(String message) {
        super(message);
    }

   /**
    *
    * Construct AppException.
    * @param message exception message
    * @param cause exception cause
    */
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

   /**
    *
    * Construct AppException.
    * @param cause exception cause
    */
    public AppException(Throwable cause) {
        super(cause);
    }
}
