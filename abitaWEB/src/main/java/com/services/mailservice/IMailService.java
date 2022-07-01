package com.services.mailservice;

import com.dto.mail.AbstractMailContentDTO;
import com.dto.mail.MailSendStatus;
import com.services.mailservice.exception.MailServiceException;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Service to send mail.
 */
public interface IMailService {

	/**
	 * Send mail on default mail's server.
	 *
	 * @param mailContent mail content (subject, tos, ...)
	 * @throws MailServiceException {@link MailServiceException}
     * @return le statut du mail
	 */
	MailSendStatus sendMail(AbstractMailContentDTO mailContent) throws MailServiceException;

    /**
     * Asynchronously Send mail on default mail's server.
     * @param mailContent mail content (subject, tos, ...)
     * @return le statut du mail
     */
	List<Future<MailSendStatus>> sendAsyncMail(List<? extends AbstractMailContentDTO> mailContent);

}
