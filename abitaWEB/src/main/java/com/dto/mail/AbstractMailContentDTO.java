package com.dto.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.Email;

import java.io.Serializable;

/**
 * Mail content
 *
 * @author
 */
public abstract class AbstractMailContentDTO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1021376750381369696L;

    /**
     * Logger
     */
    protected static final Log LOG = LogFactory.getLog(AbstractMailContentDTO.class);

    /**
     * to email
     */
    private MailRecipientDTO tos;

    /**
     * carbon's copy email's
     */
    private MailRecipientDTO ccs;

    /**
     * blind carbon's copy email's
     */
    private MailRecipientDTO bccs;

    /**
     * from email
     */
    private String from;

    /**
     * subject of mail
     */
    private String subject;

    /**
     * is text message
     */
    private String message;

    /**
     * mails attachments, could be null
     */
    private MailAttachmentDTO[] attachments;

    /**
     * Constructor
     *
     * @param tos         to email.Class MailRecipientDTO.
     * @param ccs         carbon's copy email's. Class MailRecipientDTO.
     * @param bccs        blind carbon's copy email's. Class MailRecipientDTO.
     * @param from        from email.
     * @param subject     subject of mail.
     * @param message     is text message.
     * @param attachments mails attachments, could be null
     */
    public AbstractMailContentDTO(MailRecipientDTO tos, MailRecipientDTO ccs, MailRecipientDTO bccs, String from,
                                  String subject, String message, MailAttachmentDTO[] attachments) {
        super();
        this.tos = tos;
        this.ccs = ccs;
        this.bccs = bccs;
        this.from = from;
        this.subject = subject;
        this.message = message;
        this.attachments = attachments;
    }

    /**
     * methods that returns the Email object based on the concrete type of the class
     *
     * @return an email of the good shape based on the concrete class
     */
    public abstract Email createMail();

    /**
     * @return the tos
     */
    public MailRecipientDTO getTos() {
        return tos;
    }

    /**
     * @param tos the tos to set
     */
    public void setTos(MailRecipientDTO tos) {
        this.tos = tos;
    }

    /**
     * @return the ccs
     */
    public MailRecipientDTO getCcs() {
        return ccs;
    }

    /**
     * @param ccs the ccs to set
     */
    public void setCcs(MailRecipientDTO ccs) {
        this.ccs = ccs;
    }

    /**
     * @return the bccs
     */
    public MailRecipientDTO getBccs() {
        return bccs;
    }

    /**
     * @param bccs the bccs to set
     */
    public void setBccs(MailRecipientDTO bccs) {
        this.bccs = bccs;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
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

    /**
     * @return the attachments
     */
    public MailAttachmentDTO[] getAttachments() {
        return attachments;
    }

    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(MailAttachmentDTO[] attachments) {
        this.attachments = attachments;
    }

}
