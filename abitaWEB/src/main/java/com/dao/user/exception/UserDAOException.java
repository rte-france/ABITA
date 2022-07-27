/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.user.exception;

import com.dao.common.exception.GenericDAOException;

/**
 * @author
 *
 */
public class UserDAOException extends GenericDAOException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6237835184973730095L;

	/**
	 * Constructor
	 *
	 * @param e exception
	 */
	public UserDAOException(final Exception e) {
		super(e);
	}

	/**
	 * Constructor
	 *
	 * @param message the error message
	 * @param cause the error cause
	 */
	public UserDAOException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message the error message
	 */
	public UserDAOException(final String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause the error cause
	 */
	public UserDAOException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 */
	public UserDAOException() {
		super();
	}
}
