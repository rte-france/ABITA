package com.services.common.exception;

import javax.persistence.PersistenceException;
import java.io.Serializable;

/**
 * An exception of wrapper of unique key USER.NAME field.
 * @author
 *
 */
public class NameAlreadyExistsException extends PersistenceException implements Serializable {
    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * Construct NameAlreadyExistsException.
     */
    public NameAlreadyExistsException() {
        //
    }
    /**
     *
     * Construct NameAlreadyExistsException.
     * @param message exception message
     */
    public NameAlreadyExistsException(String message) {
        super(message);
    }
    /**
     *
     * Construct NameAlreadyExistsException.
     * @param message exception message
     * @param cause exception cause
     */
    public NameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     *
     * Construct NameAlreadyExistsException.
     * @param cause exception cause
     */
    public NameAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
