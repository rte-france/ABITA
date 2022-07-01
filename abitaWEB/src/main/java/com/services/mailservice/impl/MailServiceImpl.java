package com.services.mailservice.impl;

import com.dto.mail.AbstractMailContentDTO;
import com.dto.mail.MailAttachmentDTO;
import com.dto.mail.MailRecipientDTO;
import com.dto.mail.MailReportingDTO;
import com.dto.mail.MailSendStatus;
import com.services.mailreporting.MailReportingService;
import com.services.mailreporting.exception.MailReportingException;
import com.services.mailservice.IMailService;
import com.services.mailservice.exception.MailServiceException;
import com.services.paramservice.ParameterService;
import com.services.paramservice.constants.ParamServiceConstants;
import com.services.paramservice.exception.ParameterServiceException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import javax.activation.DataSource;
import java.io.IOException;
import java.io.Serializable;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.Future;

/**
 * A mail's service implementation.
 *
 * @author
 */
public class MailServiceImpl implements IMailService, Serializable {

    /**
     * Mails bundle
     */
    protected static final String MAIL_BUNDLE = "com.mail.mails";
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -7298867856969460769L;
    /**
     * Log
     */
    private static final Log LOG = LogFactory.getLog(com.services.mailservice.impl.MailServiceImpl.class);
    /**
     * Parameter key allowing to get hostname value
     */
    private static final String MAIL_SERVER_KEY = "mailServer";
    /**
     * Parameter key allowing to get hostname value
     */
    private static final String MAIL_SMTP_PORT_KEY = "mailServerPort";
    /**
     * Parameter key allowing to get socket timeout value
     */
    private static final String MAIL_SERVER_KEY_SOCKET_TIMEOUT = "mailServerSocketTimeout";
    /**
     * Parameter key allowing to get socket connection timeout value
     */
    private static final String MAIL_SERVER_KEY_SOCKET_CONNECTION_TIMEOUT = "mailServerConnectionTimeout";
    /**
     * Default timeout value used if other values not found
     */
    private static final int TIMEOUT_DEFAULT_VALUE = 60000;
    /**
     * Default smtp port value used if other values not found
     */
    private static final int SMTP_PORT_DEFAULT_VALUE = 25;
    /**
     * parameterService
     */
    private ParameterService parameterService;
    /**
     * mailReportingService
     */
    private MailReportingService mailReportingService;

    /**
     * This method allows the user to send a mail based on the content in parameter
     * This is a synchronous method
     *
     * @param mailContent mail content (subject, tos, ...)
     * @return status of the mail sent
     * @throws MailServiceException signals an exception during mail send
     */
    @Override
    public MailSendStatus sendMail(final AbstractMailContentDTO mailContent) throws MailServiceException {

        MailReportingDTO reporting = null;
        try {
            reporting = mailReportingService.initReporting(mailContent);
        } catch (MailReportingException e) {
            // In this very special case, we just log the fact that reporting has not been performed, but we send the mail anyway
            LOG.error("Mail reporting cannot be created. But send mail anyway.", e);
        }

        try {
            prepareMail(mailContent).send();
        } catch (EmailException e) {
            LOG.error("Mail send was KO: " + e.getMessage());
            try {
                if (reporting != null) {
                    reporting.setErrorMessage(e.getMessage());
                    mailReportingService.endReporting(MailSendStatus.ERROR, reporting);
                }
            } catch (MailReportingException e1) {
                // In this very special case, we just log the fact that reporting has not been performed, but we send the mail anyway
                LOG.error("Mail reporting cannot be updated...", e1);
            }
            throw new MailServiceException(e);
        }

        try {
            if (reporting != null) {
                mailReportingService.endReporting(MailSendStatus.SENT_OK, reporting);
            }
        } catch (MailReportingException e) {
            // In this very special case, we just log the fact that reporting has not been performed, but we send the mail anyway
            LOG.error("Mail reporting cannot be created...", e);
        }

        return MailSendStatus.SENT_OK;
    }

    /**
     * prepare the mail, setting smtp hostname, timeouts, ...
     *
     * @param mailContent the content of the mail
     * @return an email prepared
     * @throws MailServiceException signals an exception during mail preparation
     */
    private Email prepareMail(final AbstractMailContentDTO mailContent) throws MailServiceException {
        String hostname, socketTimeout, socketConnectionTimeout, smtpPort;
        Email sendingEmail;

        /* first generate the email using the concrete class */
        try {
            sendingEmail = generateMail(mailContent);
        } catch (EmailException e) {
            LOG.error("Cannot create mail using content: " + e.getMessage());
            throw new MailServiceException(e);
        }

        try {
            /* set the hostname of the SMTP server */
            hostname = parameterService.getParameterValue(ParamServiceConstants.NET_DOMAIN_KEY, MAIL_SERVER_KEY);
            sendingEmail.setHostName(hostname);
        } catch (ParameterServiceException e) {
            /* Here we throw an Exception, because we need the hostname in order to send the mail. */
            throw new MailServiceException(e);
        }

		sendingEmail.setSmtpPort(SMTP_PORT_DEFAULT_VALUE);
		try {
            /* set the port of the SMTP server */
			smtpPort = parameterService.getParameterValue(ParamServiceConstants.NET_DOMAIN_KEY, MAIL_SMTP_PORT_KEY);
			sendingEmail.setSmtpPort(Integer.parseInt(smtpPort));
		} catch (ParameterServiceException e) {
			LOG.warn("Exception was catched while accessing to value of " + MAIL_SMTP_PORT_KEY + ". Using defaults.");
		} catch (NumberFormatException e) {
			LOG.warn("Exception was catched while accessing to value of " + MAIL_SMTP_PORT_KEY + ". Using defaults.");
		}


        /* set the maximum timeouts */
        sendingEmail.setSocketTimeout(TIMEOUT_DEFAULT_VALUE);
        try {
            socketTimeout = parameterService.getParameterValue(ParamServiceConstants.NET_DOMAIN_KEY, MAIL_SERVER_KEY_SOCKET_TIMEOUT);
            if (NumberUtils.isNumber(socketTimeout)) {
                sendingEmail.setSocketTimeout(Integer.parseInt(socketTimeout));
            }
        } catch (ParameterServiceException e) {
            LOG.warn("Exception was catched while accessing to value of " + MAIL_SERVER_KEY_SOCKET_TIMEOUT + ". Using defaults.");
        } catch (NumberFormatException e) {
            LOG.warn("Exception was catched while parsing value of " + MAIL_SERVER_KEY_SOCKET_TIMEOUT + ". Using defaults.");
        }

        sendingEmail.setSocketConnectionTimeout(TIMEOUT_DEFAULT_VALUE);
        try {
            socketConnectionTimeout = parameterService.getParameterValue(ParamServiceConstants.NET_DOMAIN_KEY, MAIL_SERVER_KEY_SOCKET_CONNECTION_TIMEOUT);
            if (NumberUtils.isNumber(socketConnectionTimeout)) {
                sendingEmail.setSocketConnectionTimeout(Integer.parseInt(socketConnectionTimeout));
            }
        } catch (ParameterServiceException e) {
            LOG.warn("Exception was catched while accessing to value of " + MAIL_SERVER_KEY_SOCKET_CONNECTION_TIMEOUT + ". Using defaults.");
        } catch (NumberFormatException e) {
            LOG.warn("Exception was catched while parsing value of " + MAIL_SERVER_KEY_SOCKET_CONNECTION_TIMEOUT + ". Using defaults.");
        }

        return sendingEmail;
    }

    /**
     * method to be used in order to create the correct email object to be sent
     *
     * @param mailContent the content of the mail to be prepared to be send
     * @return an email ready to be sent
     * @throws EmailException signals an exception during mail generation
     */
    public Email generateMail(AbstractMailContentDTO mailContent) throws EmailException {
        Email email = mailContent.createMail();

        ResourceBundle rs = ResourceBundle.getBundle(MAIL_BUNDLE);

        // Process attachments if they are some
        if (mailContent.getAttachments() != null && mailContent.getAttachments().length != 0) {
            FileNameMap fileNameMap;
            String mimeType;
            DataSource source;
            // Specific attachments treatments

            for (MailAttachmentDTO file : mailContent.getAttachments()) {
                fileNameMap = URLConnection.getFileNameMap();
                mimeType = fileNameMap.getContentTypeFor(file.getFileName());
                if (StringUtils.isEmpty(mimeType)) {
                    try {
                        mimeType = rs.getString("mime." + StringUtils.substringAfterLast(file.getFileName(), "."));
                    } catch (MissingResourceException missingResourceException) {
                        LOG.error("Unregistred type" + StringUtils.substringAfter(file.getFileName(), "."));
                    }
                }

                try {
                    source = new ByteArrayDataSource(file.getContents(), mimeType);
                    ((MultiPartEmail) email).attach(source, file.getFileName(), file.getFileName());
                } catch (IOException e) {
                    LOG.error("IOException: " + e.getMessage());
                    throw new EmailException(e);
                }

            }
        }

        // Process recipients
        dispatcher(email, RecipientType.ADD_TO, mailContent.getTos());
        dispatcher(email, RecipientType.ADD_CC, mailContent.getCcs());
        dispatcher(email, RecipientType.ADD_BCC, mailContent.getBccs());

        // process email content
        email.setFrom(mailContent.getFrom());
        email.setSubject(mailContent.getSubject());
        email.setMsg(mailContent.getMessage());

        return email;
    }

    /**
     * Dispatch recipients
     *
     * @param src           the email to set
     * @param recipientType the type of recipient to set in the email
     * @param param         the recipient descriptor
     * @throws EmailException signals an exception during dispathing
     */
    private static void dispatcher(Email src, RecipientType recipientType, MailRecipientDTO param)
            throws EmailException {
        try {
            if (null != param) {
                String var;
                for (String mail : param.getRecipients()) {
                    var = StringUtils.stripToNull(mail);
                    if (recipientType == RecipientType.ADD_TO) {
                        src.addTo(var);
                    } else if (recipientType == RecipientType.ADD_CC) {
                        src.addCc(var);
                    } else if (recipientType == RecipientType.ADD_BCC) {
                        src.addBcc(var);
                    }
                }
            }
        } catch (SecurityException securityException) {
            LOG.error(securityException);
            throw new EmailException(securityException);
        } catch (IllegalArgumentException illegalArgumentException) {
            LOG.error(illegalArgumentException);
            throw new EmailException(illegalArgumentException);
        } catch (EmailException emailException) {
            LOG.error(emailException);
            throw new EmailException(emailException);
        }
    }

    /**
     * This method allows the user to send a list of mails based on the list of mail content in parameter
     * This is an asynchronous method
     *
     * @param mailContents the list of mail contents
     * @return a list of promises of mail statuses
     */
    @Override
    public List<Future<MailSendStatus>> sendAsyncMail(final List<? extends AbstractMailContentDTO> mailContents) {
        List<Future<MailSendStatus>> resultList = new ArrayList<Future<MailSendStatus>>();

        for (AbstractMailContentDTO mailContent : mailContents) {
            Email email = null;
            MailReportingDTO reporting = null;
            try {
                reporting = mailReportingService.initReporting(mailContent);
            } catch (MailReportingException e) {
                LOG.error("Mail reporting cannot be created. But send mail anyway.", e);
            }


            try {
                email = prepareMail(mailContent);
                resultList.add(aSyncUnitSend(email, reporting));
            } catch (MailServiceException e) {
                if (reporting != null) {
                    reporting.setErrorMessage(e.getMessage());
                    try {
                        mailReportingService.endReporting(MailSendStatus.ERROR, reporting);
                    } catch (MailReportingException e1) {
                        LOG.error("Mail reporting cannot be created. But we can't do anything anymore", e);
                    }
                }
            }
        }

        return resultList;
    }

    /**
     * This is a private function, allowing to asynchronously send one mail
     *
     * @param email     the mail to be sent
     * @param reporting the reporting that has already been initiated and saved in Database
     * @return a promise of the satuts of the mail
     */
    @Async
    private Future<MailSendStatus> aSyncUnitSend(final Email email, final MailReportingDTO reporting) {
        MailSendStatus resultStatus = MailSendStatus.ERROR;
        /* CFR: Warning, the assignment on the next line is ABSOLUTELY NECESSARY */
        Future<MailSendStatus> result = new AsyncResult<MailSendStatus>(resultStatus);

        try {
            email.send();
            resultStatus = MailSendStatus.SENT_OK;
        } catch (EmailException e) {
            LOG.error("Mail send was KO: " + e.getMessage());
            resultStatus = MailSendStatus.ERROR;
        }
        result = new AsyncResult<MailSendStatus>(resultStatus);

        try {
            if (reporting != null) {
                mailReportingService.endReporting(resultStatus, reporting);
            }
        } catch (MailReportingException e) {
            // In this very special case, we just log the fact that reporting has not been performed, but we send the mail anyway
            LOG.warn("Mail reporting cannot be created...", e);
        }

        return result;
    }

    /**
     * Enumeration of recipients types
     *
     * @author
     */
    public enum RecipientType {
        /**
         * Add to
         */
        ADD_TO,
        /**
         * Add copy carbon
         */
        ADD_CC,
        /**
         * Add bind copy carbon
         */
        ADD_BCC
    }

    /**
     * setter of parameter's service.
     *
     * @param parameterService parameter service
     */
    public void setParameterService(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    /**
     * setter of the mailReporting service
     *
     * @param mailReportingService mail reporting service
     */
    public void setMailReportingService(final MailReportingService mailReportingService) {
        this.mailReportingService = mailReportingService;
    }
}
