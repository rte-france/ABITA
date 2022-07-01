package com.dto.mail;

import java.io.Serializable;
import java.util.Date;

/**
 * Objet contenant les différents critères de recherche
 *
 * @author
 */
public class MailSearchFilterDTO implements Serializable {
	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = -51829015422113469L;

	/** Date à partir de laquelle les mails doivent être inclus */
	private Date sentAfter;

	/** Date jusqu'à laquelle les mails doivent être inclus */
	private Date sentBefore;

	/** Les mails qui doivent être retournés */
	private MailSendStatus status = MailSendStatus.ALL;

	/** Adresse mail de l'expéditeur */
	private String sender;

	/** Adresses mail des destinataires principaux */
	private String recipients;

	/** Adresses mail des destinataires copy et blind copy */
	private String otherRecipients;

	/** Si le mail avait une ou plusieurs pièce(s) jointe(s) */
	private boolean attachments;

	/** Le sujet du mail qui correspond */
	private String subject;

	/** Le code d'erreur lorsque l'envoi de mail a échoué */
	private Integer errorCode;

	/** Le message d'erreur à checher */
	private String errorMessage;

	/**
	 * @return the sentAfter
	 */
	public Date getSentAfter() {
		return sentAfter;
	}

	/**
	 * @param sentAfter the sentAfter to set
	 */
	public void setSentAfter(Date sentAfter) {
		this.sentAfter = sentAfter;
	}

	/**
	 * @return the sentBefore
	 */
	public Date getSentBefore() {
		return sentBefore;
	}

	/**
	 * @param sentBefore the sentBefore to set
	 */
	public void setSentBefore(Date sentBefore) {
		this.sentBefore = sentBefore;
	}

	/**
	 * @return the status
	 */
	public MailSendStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(MailSendStatus status) {
		this.status = status;
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
	 * @return the otherRecipients
	 */
	public String getOtherRecipients() {
		return otherRecipients;
	}

	/**
	 * @param otherRecipients the otherRecipients to set
	 */
	public void setOtherRecipients(String otherRecipients) {
		this.otherRecipients = otherRecipients;
	}

	/**
	 * @return the attachments
	 */
	public boolean getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(boolean attachments) {
		this.attachments = attachments;
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
	 * @return the errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
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
