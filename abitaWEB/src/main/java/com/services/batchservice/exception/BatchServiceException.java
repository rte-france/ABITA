/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.services.batchservice.exception;

/**
 * The Class BatchServiceException.
 *
 * @author
 */
public class BatchServiceException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 312079594890230523L;

    /**
     * Instantiates a new batch service exception.
     */
    public BatchServiceException() {
    }

    /**
     * Instantiates a new batch service exception.
     *
     * @param message the message
     */
    public BatchServiceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new batch service exception.
     *
     * @param cause the cause
     */
    public BatchServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new batch service exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public BatchServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
