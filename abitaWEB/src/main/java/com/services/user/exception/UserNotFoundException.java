/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.user.exception;

import com.services.common.exception.GenericServiceException;

/**
 * Exception pour le service IUserService
 *
 * @author
 */
public class UserNotFoundException extends GenericServiceException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1864415077565135042L;

    /**
	 * Constructeur
	 */
	public UserNotFoundException() {
		super();
	}

	/**
	 * Constructeur
	 *
	 * @param message le message de l'erreur
	 * @param cause la cause de l'erreur
	 */
	public UserNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur
	 *
	 * @param message le message de l'erreur
	 */
	public UserNotFoundException(final String message) {
		super(message);
	}

	/**
	 * Constructeur
	 *
	 * @param cause la cause de l'erreur
	 */
	public UserNotFoundException(final Throwable cause) {
		super(cause);
	}
}
