/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.mailreporting.exception;

import com.services.common.exception.GenericServiceException;

/**
 * Classe d'exception pour le service correspondant
 *
 * @author
 */
public class MailReportingException extends GenericServiceException {
	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = 1960576303658121052L;

	/**
	 * Constructeur
	 */
	public MailReportingException() {
		super();
	}

	/**
	 * @param message le message de l'exception
	 * @param cause la cause de l'anomalie
	 */
	public MailReportingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message le message de l'exception
	 */
	public MailReportingException(String message) {
		super(message);
	}

	/**
	 * @param cause la cause de l'anomalie
	 */
	public MailReportingException(Throwable cause) {
		super(cause);
	}
}
