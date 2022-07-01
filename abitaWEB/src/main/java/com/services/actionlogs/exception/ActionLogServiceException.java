/**
 *
 */
package com.services.actionlogs.exception;

import com.services.common.exception.GenericServiceException;

/**
 * Exception class for IActionLogService
 *
 * @author
 */
public class ActionLogServiceException extends GenericServiceException {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 6975886724013285065L;

	/**
	 * Constructor
	 *
	 * @param cause The cause exception
	 */
	public ActionLogServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor
	 *
	 * @param message Exception's message
	 */
	public ActionLogServiceException(String message) {
		super(message);
	}

	/**
	 * Constuctor
	 *
	 * @param message The exception mssage
	 * @param cause The exception cause
	 */
	public ActionLogServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
