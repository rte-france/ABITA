package com.services.theme.exception;

import com.services.common.exception.GenericServiceException;

/**
 * Exception for IUserService
 *
 * @author
 */
public class ThemeServiceException extends GenericServiceException {

	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = -8259878321039680644L;

	/**
	 * Constructeur
	 */
	public ThemeServiceException() {
		super();
	}

	/**
	 * @param message the error message
	 * @param cause the error cause
	 */
	public ThemeServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message the error message
	 */
	public ThemeServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause the error cause
	 */
	public ThemeServiceException(Throwable cause) {
		super(cause);
	}
}
