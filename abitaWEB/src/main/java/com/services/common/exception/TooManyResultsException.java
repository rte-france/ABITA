package com.services.common.exception;

import com.services.common.exception.AppException;

/**
 * Class that indicate that the service would return too many rows
 *
 * @author
 */
public class TooManyResultsException extends AppException {
	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = 5959099309774738456L;

	/**
	 * Constructeur par défaut
	 */
	public TooManyResultsException() {
		super();
	}

	/**
	 * Constructeur
	 *
	 * @param message message de l'exception
	 * @param cause cause de l'exception
	 */
	public TooManyResultsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur
	 *
	 * @param message message de l'exception
	 */
	public TooManyResultsException(String message) {
		super(message);
	}

	/**
	 * Constructeur
	 *
	 * @param cause cause de l'exception
	 */
	public TooManyResultsException(Throwable cause) {
		super(cause);
	}
}
