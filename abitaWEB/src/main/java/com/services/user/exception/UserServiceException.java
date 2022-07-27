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
public class UserServiceException extends GenericServiceException {

	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = -6010596066623226081L;

	/**
	 * Constructeur
	 */
	public UserServiceException() {
		super();
	}

	/**
	 * Constructeur
	 *
	 * @param message le message de l'erreur
	 * @param cause la cause de l'erreur
	 */
	public UserServiceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur
	 *
	 * @param message le message de l'erreur
	 */
	public UserServiceException(final String message) {
		super(message);
	}

	/**
	 * Constructeur
	 *
	 * @param cause la cause de l'erreur
	 */
	public UserServiceException(final Throwable cause) {
		super(cause);
	}
}
