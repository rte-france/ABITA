package com.services.common.exception;
/**
 * An exception of developpement faillure.
 * @author
 *
 */
public class UnexpectedException extends RuntimeException {
    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     * Construct UnexpectedException.
     */
    public UnexpectedException() {
        //
    }
    /**
     *
     * Construct UnexpectedException.
     * @param message exception message
     */
    public UnexpectedException(String message) {
        super(message);
    }
    /**
     *
     * Construct UnexpectedException.
     * @param message exception message
     * @param cause exception cause
     */
    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     *
     * Construct UnexpectedException.
     * @param cause exception cause
     */
    public UnexpectedException(Throwable cause) {
        super(cause);
    }
}
