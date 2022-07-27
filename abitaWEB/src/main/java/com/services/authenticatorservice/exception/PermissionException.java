/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.authenticatorservice.exception;

/**
 * User: P.Rahmoune Date: 10 fevr. 2005 Time: 17:48:09
 */
public class PermissionException extends Exception {

    /**
    * serialVersionUID
    */
    private static final long serialVersionUID = -4777829958399846511L;

    /**
     *
     * Construct PermissionException.
     */
    public PermissionException() {
    //
    }

    /**
     *
     * Construct PermissionException.
     * @param message exception message
     */
    public PermissionException(String message) {
        super(message);
    }

    /**
     *
     * Construct PermissionException.
     * @param cause exception cause
     */
    public PermissionException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * Construct PermissionException.
     * @param message exception message
     * @param cause exception cause
     */
    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
