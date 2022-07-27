/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.documentgenerationservice.exception;

/**
 * Exception for the document generation module
 *
 * @author
 */
public class DocumentGenerationException extends Exception {

	/** Serial Version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 * @param message message d'erreur
	 */
	public DocumentGenerationException(String message) {
		super(message);
	}

	/**
	 * Default Constructor
	 * @param message message d'erreur
	 * @param cause cause de l'exception
	 */
	public DocumentGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Default Constructor
	 * @param cause cause de l'exception
	 */
	public DocumentGenerationException(Throwable cause) {
		super(cause);
	}
}
