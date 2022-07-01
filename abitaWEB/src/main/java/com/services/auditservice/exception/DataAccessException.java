package com.services.auditservice.exception;

/**
 * Superclass for all exceptions related to the data access tier
 * @author
 *
 */
public class DataAccessException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = 8461015925505465944L;

	/**
	 * Constructor
	 * @param message exception's message
	 * @param e exception
	 */
	public DataAccessException(String message, Exception e) {
		super(message, e);
	}

}
