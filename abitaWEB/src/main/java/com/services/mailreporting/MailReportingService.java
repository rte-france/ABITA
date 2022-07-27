/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.mailreporting;

import com.dto.mail.AbstractMailContentDTO;
import com.dto.mail.MailReportingDTO;
import com.dto.mail.MailSearchFilterDTO;
import com.dto.mail.MailSendStatus;
import com.services.mailreporting.exception.MailReportingException;

import java.util.List;

/**
 * Interface pour le reporting des envois de mails
 *
 * @author
 */
public interface MailReportingService {
	/**
	 * Cherche et renvoie la liste des mails qui correspondent au filtre
	 * @param filter Le filtre
	 * @return les mails correspondant au filtre
	 * @throws MailReportingException Si une erreur survient
	 */
	List<MailReportingDTO> find(final MailSearchFilterDTO filter) throws MailReportingException;

	/**
	 * Renvoie la liste des messages d'erreur
	 * @return les messages d'erreur
	 * @throws MailReportingException Si une erreur survient
	 */
	List<String> getErrorMessages() throws MailReportingException;

    /**
     * Initialise le reporting
     *
     * @param mailContent le contenu du mail à stocker
     * @return l'id du reporting du mail créé
     * @throws MailReportingException exception lors de l'initialisation du reporting
     */
    MailReportingDTO initReporting(AbstractMailContentDTO mailContent) throws MailReportingException;

    /**
     * Finalise le reporting, après que l'on ait eu le statut de l'envoi
     * @param status statut de l'envoi
     * @param mailReporting le reporting du mail à mettre à jour
     * @throws MailReportingException exception lors de la finalisation du reporting
     */
    void endReporting(MailSendStatus status, MailReportingDTO mailReporting) throws MailReportingException;
}
