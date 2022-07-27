/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.dto;

import java.util.Date;


/**
 * Le message d'une trace important
 *
 * @see ActionLogDTO#getMessages()
 *
 * @author
 */
public class ActionLogMessageDTO extends AbstractDTO {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 7961960857283190369L;

	/** La date du message (utile sur de longs traitements) */
	private Date date;

	/** Gravité du message */
	private String severity;

	/** Le message */
	private String message;

	/**
	 * Constructeur
	 */
	public ActionLogMessageDTO() {
		super();
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the severity
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
