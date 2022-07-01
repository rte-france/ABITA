package com.services.authenticatorservice.exception;

/**
 * An account state exception.
 * @author
 *
 */
public class InvalidAccountStateException extends Exception {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * expired code
     */
    public static final int ACCOUNT_EXPIRED = 0;
    /**
     * locked code
     */
    public static final int ACCOUNT_LOCKED = 1;
    /**
     * disabeled code
     */
    public static final int ACCOUNT_DISABLED = 2;

    /** Error code */
    private int errorCode;

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }
    /**
     *
     * Construct InvalidAccountStateException.
     * @param errorCode error code
     */
    public InvalidAccountStateException(int errorCode) {
        this.errorCode = errorCode;
    }
    /**
     *
     * Construct InvalidAccountStateException.
     * @param message exception message
     * @param errorCode error code
     */
    public InvalidAccountStateException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    /**
     *
     * Construct InvalidAccountStateException.
     * @param message exception message
     * @param cause exception cause
     * @param errorCode exception error code
     */
    public InvalidAccountStateException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    /**
     *
     * Construct InvalidAccountStateException.
     * @param cause exception cause
     * @param errorCode exception error code
     */
    public InvalidAccountStateException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

}
