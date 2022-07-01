package com.services.mailservice.exception;

import com.services.common.exception.GenericServiceException;

/**
 * An exception of mail's service.
 * @author
 *
 */
public class MailServiceException extends GenericServiceException {
    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     * Construct MailServiceException.
     */
    public MailServiceException() {
        //
    }
    /**
     *
     * Construct MailServiceException.
     * @param message exception message
     */
    public MailServiceException(String message) {
        super(message);
    }
    /**
     *
     * Construct MailServiceException.
     * @param message exception message
     * @param cause exception cause
     */
    public MailServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     *
     * Construct MailServiceException.
     * @param cause exception cause
     */
    public MailServiceException(Throwable cause) {
        super(cause);
    }
}
