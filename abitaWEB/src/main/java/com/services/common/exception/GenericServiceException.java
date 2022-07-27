/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.common.exception;

/**
 * Service exception
 * @author
 *
 */
public class GenericServiceException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public GenericServiceException() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param message exception's description
	 */
	public GenericServiceException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause exception's cause
	 */
	public GenericServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 *
	 * @param message exception's description
	 * @param cause exception's cause
	 */
	public GenericServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
