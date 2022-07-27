/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.actionlogs.entity;

import com.dao.common.entity.AbstractEntity;

import java.util.Date;

/**
 * Classe représentant un message important à tracer
 *
 * @author
 */
public class ActionLogMessage extends AbstractEntity {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 7411098140849748808L;

	/** La date du message (utile sur de longs traitements) */
	private Date date;

	/** Gravité du message */
	private String severity;

	/** Le message */
	private String message;

	/**
	 * Constructeur par défaut
	 */
	public ActionLogMessage() {
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
