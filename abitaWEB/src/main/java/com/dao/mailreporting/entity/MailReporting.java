/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.mailreporting.entity;

import com.dao.common.entity.AbstractEntity;

import java.util.Date;

/**
 * Entity representing the MAIL_REPORTING database table
 *
 * @author
 */
public class MailReporting extends AbstractEntity {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 2741553218379054642L;

	/** The smtp server status code */
	private Integer statusCode;

	/** The mail subjet. Must be enough detailed to be unique */
	private String subject;

	/** The date the mail was sent */
	private Date dateSent;

	/** The sender email address */
	private String sender;

	/** The recipients email addresses */
	private String recipients;

	/** The copy recipients email addresses */
	private String copyRecipients;

	/** The blind copy recipients email addresses */
	private String blindCopyRecipients;

	/** The number of attachments */
	private Integer attachmentsCount;

	/** The error message returned by the server */
	private String errorMessage;

	/**
	 * @return the statusCode
	 */
	public Integer getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the dateSent
	 */
	public Date getDateSent() {
		return dateSent;
	}

	/**
	 * @param dateSent the dateSent to set
	 */
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return the recipients
	 */
	public String getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	/**
	 * @return the copyRecipients
	 */
	public String getCopyRecipients() {
		return this.copyRecipients;
	}

	/**
	 * @param copyRecipients the copyRecipients to set
	 */
	public void setCopyRecipients(String copyRecipients) {
		this.copyRecipients = copyRecipients;
	}

	/**
	 * @return the blindCopyRecipients
	 */
	public String getBlindCopyRecipients() {
		return this.blindCopyRecipients;
	}

	/**
	 * @param blindCopyRecipients the blindCopyRecipients to set
	 */
	public void setBlindCopyRecipients(String blindCopyRecipients) {
		this.blindCopyRecipients = blindCopyRecipients;
	}

	/**
	 * @return the attachments
	 */
	public Integer getAttachmentsCount() {
		return attachmentsCount;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachmentsCount(Integer attachments) {
		this.attachmentsCount = attachments;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
