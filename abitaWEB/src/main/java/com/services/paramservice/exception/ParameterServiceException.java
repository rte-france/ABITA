/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.paramservice.exception;
/**
 * An exception of parameter's service
 * @author
 *
 */
public class ParameterServiceException extends Exception {

    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     * Construct ParameterServiceException.
     */
    public ParameterServiceException() {
        //
    }
    /**
     *
     * Construct ParameterServiceException.
     * @param message exception message
     */
     public ParameterServiceException(String message) {
        super(message);
    }
     /**
      *
      * Construct ParameterServiceException.
      * @param message exception message
      * @param cause exception cause
      */
    public ParameterServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     *
     * Construct ParameterServiceException.
     * @param cause exception cause
     */
    public ParameterServiceException(Throwable cause) {
        super(cause);
    }
}
