package com.services.common.exception;

import javax.persistence.PersistenceException;

/**
 *
 * @author
 *
 */
public class DirtyDataException extends PersistenceException {
    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     * Construct DirtyDataException.
     */
    public DirtyDataException() {
        //
    }
    /**
     *
     * Construct DirtyDataException.
     * @param message exception message
     */
    public DirtyDataException(String message) {
        super(message);
    }
    /**
     *
     * Construct DirtyDataException.
     * @param message exception message
     * @param cause exception cause
     */
    public DirtyDataException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     *
     * Construct DirtyDataException.
     * @param cause exception cause
     */
    public DirtyDataException(Throwable cause) {
        super(cause);
    }
}
