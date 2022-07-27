/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.common.exception;
/**
 *
 * @author
 */
public class NotEmptyException extends javax.persistence.PersistenceException {

    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     * Construct NotEmptyException.
     */
    public NotEmptyException() {
        //
    }
    /**
     *
     * Construct NotEmptyException.
     * @param message exception message
     */
    public NotEmptyException(String message) {
        super(message);
    }
    /**
     *
     * Construct NotEmptyException.
     * @param message exception message
     * @param cause exception cause
     */
    public NotEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     *
     * Construct NotEmptyException.
     * @param cause exception cause
     */
    public NotEmptyException(Throwable cause) {
        super(cause);
    }
}
