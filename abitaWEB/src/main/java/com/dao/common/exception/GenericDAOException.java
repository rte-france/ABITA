/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.common.exception;

/**
 * Exception de DAO générique
 * @author
 *
 */
public class GenericDAOException extends Exception {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 3192582627269535152L;

	/**
	 * Constructor
	 */
	public GenericDAOException() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param message exception's description
	 */
	public GenericDAOException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause exception's cause
	 */
	public GenericDAOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 *
	 * @param message exception's description
	 * @param cause exception's cause
	 */
	public GenericDAOException(String message, Throwable cause) {
		super(message, cause);
	}

}
