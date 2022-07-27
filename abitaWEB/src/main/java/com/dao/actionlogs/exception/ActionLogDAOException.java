/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.actionlogs.exception;

import com.dao.common.exception.GenericDAOException;

/**
 * Class of the exception thrown by the IActionLogDAO
 *
 * @author
 */
public class ActionLogDAOException extends GenericDAOException {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 4867842137418485971L;

	/**
	 * Constructor with exception
	 *
	 * @param e The exception thrown
	 */
	public ActionLogDAOException(Exception e) {
		super(e);
	}

	/**
	 * Default constructor
	 */
	public ActionLogDAOException() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param message the exception's message
	 * @param cause the exception's cause
	 */
	public ActionLogDAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message the exception's message
	 */
	public ActionLogDAOException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause the exception's cause
	 */
	public ActionLogDAOException(Throwable cause) {
		super(cause);
	}
}
