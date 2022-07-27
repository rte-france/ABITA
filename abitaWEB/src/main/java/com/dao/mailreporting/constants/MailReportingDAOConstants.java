/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.mailreporting.constants;

/**
 * Constants for the MailReportingDao implementation
 *
 * @author
 */
public final class MailReportingDAOConstants {
	/**
	 * Constructeur privé
	 */
	private MailReportingDAOConstants() {
	}

	/* *************************************************************************
	 * PROPERTIES OF MailReporting
	 ************************************************************************ */

	/** value of the name of the properties dateSent */
	public static final String MAIL_REPORTING_PROPERTY_DATE_SENT = "dateSent";

	/** value of the name of the properties subject */
	public static final String MAIL_REPORTING_PROPERTY_SUBJECT = "subject";

	/** value of the name of the properties sender */
	public static final String MAIL_REPORTING_PROPERTY_SENDER = "sender";

	/** value of the name of the properties recipients */
	public static final String MAIL_REPORTING_PROPERTY_RECIPIENTS = "recipients";

	/** value of the name of the properties copy recipients */
	public static final String MAIL_REPORTING_PROPERTY_COPY_RECIPIENTS = "copyRecipients";

	/** value of the name of the properties blind copy recipients */
	public static final String MAIL_REPORTING_PROPERTY_BLIND_COPY_RECIPIENTS = "blindCopyRecipients";

	/** value of the name of the properties attachment count */
	public static final String MAIL_REPORTING_PROPERTY_ATTACHMENTS = "attachmentsCount";

	/** value of the name of the properties error message */
	public static final String MAIL_REPORTING_PROPERTY_ERROR_MESSAGE = "errorMessage";

    /** value of the name of the properties statusCode */
    public static final String MAIL_REPORTING_PROPERTY_STATUS = "statusCode";

	/* *************************************************************************
	 * OTHER CONSTANTS
	 ************************************************************************ */

	/** Le The name of the query used to filter the entities */
	public static final String QUERY_FIND_WITH_FILTER = "filter";

	/** The name of the query parameter representing the status */
	public static final String QUERY_FILTER_PARAMETER_STATUS = "status";

	/** The name of the query parameter representing the date from which the entities should be included */
	public static final String QUERY_FILTER_PARAMETER_DATE_FROM = "dateAfter";

	/** The name of the query parameter representing the date before which the entities should be included */
	public static final String QUERY_FILTER_PARAMETER_DATE_TO = "dateBefore";

	/** The name of the query parameter representing the sender email address */
	public static final String QUERY_FILTER_PARAMETER_SENDER = "sender";

	/** The name of the query parameter representing the email subject */
	public static final String QUERY_FILTER_PARAMETER_SUBJECT = "subject";

	/** The name of the query used to retrieve the error codes */
	public static final String QUERY_GET_ERROR_MESSAGES = "getErrorMessages";

	/** The name of the query parameter representing the email recipients */
	public static final String QUERY_FILTER_PARAMETER_RECIPIENTS = "recipients";

	/** The name of the query parameter representing the email copy recipients and blind copy recipients */
	public static final String QUERY_FILTER_PARAMETER_OTHER_RECIPIENTS = "otherRecipients";

}
