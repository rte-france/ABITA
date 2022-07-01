package com.services.authenticatorservice.exception;

import com.services.common.exception.AppException;

/**
 * An exception of login.
 * @author
 *
 */
public class LoginException extends AppException {

    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 6496672853794669477L;

    /**
     *
     * Construct LoginException.
     */
    public LoginException() {
        //
    }
    /**
     *
     * Construct LoginException.
     * @param message exception message
     */
    public LoginException(String message) {
        super(message);
    }
    /**
     *
     * Construct LoginException.
     * @param message exception message
     * @param cause exception cause
     */
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     *
     * Construct LoginException.
     * @param cause exception cause
     */
    public LoginException(Throwable cause) {
        super(cause);
    }
}
