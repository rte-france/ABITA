package com.services.access.exception;

import com.services.common.exception.GenericServiceException;

/**
 * Exception pour la façade de gestion de la connexion
 *
 * @author
 */
public class AccessServiceFacadeException extends GenericServiceException {

	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = -7206828850961249280L;

	/**
	 * Constructeur
	 */
	public AccessServiceFacadeException() {
		super();
	}

	/**
	 * Constructeur
	 *
	 * @param message le message de l'erreur
	 * @param cause la cause de l'erreur
	 */
	public AccessServiceFacadeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructeur
	 *
	 * @param message le message de l'erreur
	 */
	public AccessServiceFacadeException(String message) {
		super(message);
	}

	/**
	 * Constructeur
	 *
	 * @param cause la cause de l'erreur
	 */
	public AccessServiceFacadeException(Throwable cause) {
		super(cause);
	}
}
