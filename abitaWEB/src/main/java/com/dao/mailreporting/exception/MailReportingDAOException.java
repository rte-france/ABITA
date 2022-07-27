/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.mailreporting.exception;

import com.dao.common.exception.GenericDAOException;

/**
 * Exception class for MailReportingDao
 *
 * @author
 */
public class MailReportingDAOException extends GenericDAOException {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 8796679678076606113L;

	/**
	 * Constructor
	 */
	public MailReportingDAOException() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param message the exception's message
	 * @param cause the exception's cause
	 */
	public MailReportingDAOException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 *
	 * @param message the exception's message
	 */
	public MailReportingDAOException(final String message) {
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @param cause the exception's cause
	 */
	public MailReportingDAOException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor.
	 * Must exist because the AbstractGenericEntityDAO's exception generator takes an exception as argument and not a throwable.
	 *
	 * @param exception the exception's cause
	 */
	public MailReportingDAOException(final Exception exception) {
		super(exception);
	}
}
