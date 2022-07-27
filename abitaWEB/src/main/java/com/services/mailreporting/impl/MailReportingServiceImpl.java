/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.mailreporting.impl;

import com.dao.mailreporting.MailReportingDAO;
import com.dao.mailreporting.entity.MailReporting;
import com.dao.mailreporting.exception.MailReportingDAOException;
import com.dto.mail.AbstractMailContentDTO;
import com.dto.mail.MailReportingDTO;
import com.dto.mail.MailSearchFilterDTO;
import com.dto.mail.MailSendStatus;
import com.services.common.exception.NotFoundException;
import com.services.common.util.DozerUtils;
import com.services.mailreporting.MailReportingService;
import com.services.mailreporting.exception.MailReportingException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Mail Reporting service implementation
 * @author
 */
public class MailReportingServiceImpl implements MailReportingService {

    /** logger */
    private static final Log LOGGER = LogFactory.getLog(com.services.mailreporting.impl.MailReportingServiceImpl.class);

    /** If nothing has to be put in the field, then we put this label */
    public static final String VIDE = "<VIDE>";

    /** the underlying DAO layer*/
    private MailReportingDAO mailReportingDao;

    /** Dozer mapper*/
    private Mapper mapper;

    /**
     * Returns all mails corresponding to the filter
     * @param filter the filter to respect
     * @return a list of mails corresponding to the filter
     * @throws MailReportingException Signals that an exception has occurred during reporting
     */
    @Override
    public List<MailReportingDTO> find(final MailSearchFilterDTO filter) throws MailReportingException {
        try {
            return DozerUtils.map(mapper, mailReportingDao.find(filter), MailReportingDTO.class);
        } catch (MailReportingDAOException mailReportingDAOException) {
            LOGGER.error("Erreur pendant le filtrage des envois de mail");
            throw new MailReportingException("Erreur pendant le filtrage des entitiés", mailReportingDAOException);
        }
    }

    /**
     * Initialize a mail send, with in progress status
     * @param mailContent the content of the mail to be stored
     * @return the corresponding reporting object
     * @throws MailReportingException Signals that an exception has occurred during reporting
     */
    @Override
    public MailReportingDTO initReporting(final AbstractMailContentDTO mailContent) throws MailReportingException {

        /* Create the mail report */
        MailReporting mailReporting = new MailReporting();
        mailReporting.setStatusCode(MailSendStatus.IN_PROGRESS.getValue());
        if (StringUtils.isNotEmpty(mailContent.getSubject())) {
            mailReporting.setSubject(mailContent.getSubject());
        } else {
            mailReporting.setSubject(VIDE);
        }
        mailReporting.setDateSent(new Date());
        if (StringUtils.isNotEmpty(mailContent.getFrom())) {
            mailReporting.setSender(mailContent.getFrom());
        } else {
            mailReporting.setSender(VIDE);
        }
        if (mailContent.getTos() != null && mailContent.getTos().getRecipients() != null && mailContent.getTos().getRecipients().size() != 0) {
            mailReporting.setRecipients(StringUtils.join(mailContent.getTos().getRecipients(), ","));
        } else {
            mailReporting.setRecipients(VIDE);
        }
        if (mailContent.getCcs() != null && mailContent.getCcs().getRecipients() != null && mailContent.getCcs().getRecipients().size() != 0) {
            mailReporting.setCopyRecipients(StringUtils.join(mailContent.getCcs().getRecipients(), ","));
        } else {
            mailReporting.setCopyRecipients(VIDE);
        }
        if (mailContent.getBccs() != null && mailContent.getBccs().getRecipients() != null && mailContent.getBccs().getRecipients().size() != 0) {
            mailReporting.setBlindCopyRecipients(StringUtils.join(mailContent.getBccs().getRecipients(), ","));
        } else {
            mailReporting.setBlindCopyRecipients(VIDE);
        }
        if (mailContent.getAttachments() != null) {
            mailReporting.setAttachmentsCount(mailContent.getAttachments().length);
        } else {
            mailReporting.setAttachmentsCount(0);
        }

        try {
            /* persist the created reporting */
            mailReporting.setId(mailReportingDao.create(mailReporting));
            return mapper.map(mailReporting, MailReportingDTO.class);
        } catch (MailReportingDAOException mailReportingDAOException) {
            LOGGER.error("Erreur pendant la création d'un reporting");
            throw new MailReportingException("Erreur pendant la création d'un reporting",
                    mailReportingDAOException);
        }
    }

    /**
     * Finalize a reporting with the status passed as a parameter
     * @param status mail send status
     * @param mailReporting mail reporting to update
     * @throws MailReportingException Signals that an exception has occurred during reporting
     */
    @Override
    public void endReporting(final MailSendStatus status, final MailReportingDTO mailReporting) throws MailReportingException {
        try {
            MailReporting reporting = mailReportingDao.get(mailReporting.getId());

            /* Update the mail */
            reporting.setStatusCode(status.getValue());
            reporting.setSubject(mailReporting.getSubject());
            reporting.setSender(mailReporting.getSender());
            reporting.setRecipients(mailReporting.getRecipients());
            reporting.setCopyRecipients(mailReporting.getCopyRecipients());
            reporting.setBlindCopyRecipients(mailReporting.getBlindCopyRecipients());
            reporting.setAttachmentsCount(mailReporting.getAttachmentsCount());
            reporting.setErrorMessage(mailReporting.getErrorMessage());

            /* persist it */
            mailReportingDao.update(reporting);
        } catch (MailReportingDAOException e) {
            throw new MailReportingException("Erreur pendant la maj d'un reporting", e);
        } catch (NotFoundException e) {
            throw new MailReportingException("Erreur pendant la maj d'un reporting", e);
        }
    }

    /**
     * Returns all types of errors we received on previous mails send
     * @return a list of error strings
     * @throws MailReportingException Signals that an exception has occurred during reporting
     */
    @Override
    public List<String> getErrorMessages() throws MailReportingException {
        try {
            return mailReportingDao.getErrorMessages();
        } catch (MailReportingDAOException mailReportingDAOException) {
            LOGGER.error("Erreur pendant la récupération des codes d'erreur");
            throw new MailReportingException("Erreur pendant la récupération des codes d'erreur",
                    mailReportingDAOException);
        }
    }

    /**
     * Setter of the underlying DAO
     * @param mailReportingDao the mailReportingDao to set
     */
    public void setMailReportingDao(MailReportingDAO mailReportingDao) {
        this.mailReportingDao = mailReportingDao;
    }

    /**
     * Setter of the Dozer Mapper
     * @param mapper the mapper to set
     */
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
