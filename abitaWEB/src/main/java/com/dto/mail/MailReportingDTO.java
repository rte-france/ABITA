/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto.mail;

import com.dto.AbstractDTO;

import java.util.Date;

/**
 * L'objet qui sera affiché dans la page de consultation des envois de mails
 *
 * @author
 */
public class MailReportingDTO extends AbstractDTO {
	/** Séparateur pour la concaténation des destinataires dans l'écran de reporting */
	public static final String COPY_RECIPIENTS_SEPARATOR = "; ";

	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = 8428801720105033823L;

	/** Le code d'envoi du serveur smtp */
	private Integer statusCode;

	/** Le sujet */
	private String subject;

	/** La date d'envoi */
	private Date dateSent;

	/** L'adresse de l'expéditeur */
	private String sender;

	/** Les adresses des destinataires */
	private String recipients;

	/** The copy recipients email addresses */
	private String copyRecipients;

	/** The blind copy recipients email addresses */
	private String blindCopyRecipients;

	/** Si le mail avait des pièces jointes */
	private Integer attachmentsCount;

	/** Le message d'erreur renvoyé par le serveur */
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
		return copyRecipients;
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
	 * @return the attachmentsCount
	 */
	public Integer getAttachmentsCount() {
		return this.attachmentsCount;
	}

	/**
	 * @param attachmentsCount the attachmentsCount to set
	 */
	public void setAttachmentsCount(Integer attachmentsCount) {
		this.attachmentsCount = attachmentsCount;
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

	/**
	 * Renvoie la liste des destinataires copie fusionnée avec celle des destinataires copie carbone
	 *
	 * @return les listes concaténées
	 */
	public String getAllCopyRecipients() {
		if (this.copyRecipients == null) {
			return this.blindCopyRecipients;
		} else if (this.blindCopyRecipients != null) {
			return this.copyRecipients.concat(COPY_RECIPIENTS_SEPARATOR).concat(this.blindCopyRecipients);
		} else {
			return this.copyRecipients;
		}
	}
}
