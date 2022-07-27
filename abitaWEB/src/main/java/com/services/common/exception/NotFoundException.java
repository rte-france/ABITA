/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.common.exception;


import com.services.common.exception.AppException;

/**
 *
 * @author
 *
 */
public class NotFoundException extends AppException {
    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     * Construct NotFoundException.
     */
    public NotFoundException() {
        //
    }
    /**
     *
     * Construct NotFoundException.
     * @param message exception message
     */
    public NotFoundException(String message) {
        super(message);
    }
    /**
     *
     * Construct NotFoundException.
     * @param message exception message
     * @param cause exception cause
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     *
     * Construct NotFoundException.
     * @param cause exception cause
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
