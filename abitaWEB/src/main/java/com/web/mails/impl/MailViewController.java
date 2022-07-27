/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.mails.impl;

import com.dto.mail.MailReportingDTO;
import com.dto.mail.MailSendStatus;
import com.services.mailreporting.MailReportingService;
import com.services.mailreporting.exception.MailReportingException;
import com.web.mails.constants.MailViewConstants;
import com.web.mails.impl.MailViewBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Le contrôleur pour la consultation des envois de mails
 *
 * @author
 */
public class MailViewController {
    /**
     * Le loggueur
     */
    private static final Log LOGGER = LogFactory.getLog(MailViewController.class);

	/* **************************************************************************
	 * PROPRIETES
	 ************************************************************************* */
    /**
     * Le bean associé
     */
    private com.web.mails.impl.MailViewBean bean;
    /**
     * Le service pour la récupération des mails
     */
    private MailReportingService mailService;
    /**
     * Le bundle associé
     */
    private ResourceBundle bundle;

	/* **************************************************************************
	 * METHODES
	 ************************************************************************* */

    /**
     * Intialisation de la liste des codes d'erreur
     */
    @SuppressWarnings("unused")
    @PostConstruct
    private void init() {
        if (this.getBean().getStatusList() == null) {
            List<SelectItem> statusList = new ArrayList<SelectItem>();

            for (MailSendStatus status : MailSendStatus.values()) {
                statusList.add(new SelectItem(status, this.getCorrespondingStatusLabel(status)));
            }

            this.getBean().setStatusList(statusList);
        }

        if (this.getBean().getErrorMessages() == null) {
            List<String> errorMessages;
            List<SelectItem> codes = null;
            try {
                errorMessages = this.mailService.getErrorMessages();
                codes = new ArrayList<SelectItem>(errorMessages.size() + 1);
                SelectItem noChoiceItem = new SelectItem(null, StringUtils.EMPTY);
                noChoiceItem.setNoSelectionOption(true);
                codes.add(noChoiceItem);
                for (final String message : errorMessages) {
                    codes.add(new SelectItem(message));
                }
            } catch (final MailReportingException mailReportingException) {
                LOGGER.error(mailReportingException);
            } finally {
                this.getBean().setErrorMessages(codes);
            }
        }
    }

    /**
     * Renvoie le libellé pour le statut spécifié
     *
     * @param status le statut dont on veut le libellé
     * @return le libellé ou le nom du statut si non trouvé dans le bundle
     */
    private String getCorrespondingStatusLabel(final MailSendStatus status) {
        String key = MailViewConstants.STATUS_BUNDLE_PREFIX.concat(status.toString());
        if (this.bundle.containsKey(key)) {
            return this.bundle.getString(key);
        }
        return status.toString();
    }

    /**
     * Recherche des mails
     */
    public void search() {
        List<MailReportingDTO> mails = null;
        try {
            mails = this.mailService.find(this.getBean().getMailSearchFilter());
        } catch (final MailReportingException e) {
            LOGGER.error("Erreur pendant la récupération des mails");
        } finally {
            this.getBean().setMails(mails);
        }
    }

    /**
     * Renvoie le nom de la classe CSS à utiliser pour colorier les lignes
     * La classe est calculée à partir du code d'erreur
     *
     * @param mailReporting l'objet en cours d'affichage
     * @return la classe CSS ou null si l'objet est null ou ne possède pas les informations nécessaires au calcul
     */
    public String getRowCssClass(final MailReportingDTO mailReporting) {
        if (mailReporting == null || mailReporting.getStatusCode() == null) {
            return null;
        }
        if (mailReporting.getStatusCode().equals(MailSendStatus.SENT_OK.getValue())) {
            return MailViewConstants.CSS_CLASS_SUCCESS;
        } else if (mailReporting.getStatusCode().equals(MailSendStatus.IN_PROGRESS.getValue())) {
            return MailViewConstants.CSS_CLASS_IN_PROGRESS;
        } else {
            return MailViewConstants.CSS_CLASS_FAILURE;
        }
    }

    /**
     * Reset de la valeur sélectionnée dans la liste des messages
     */
    public void updateErrorMessage() {
        if (this.bean.getMailSearchFilter().getStatus() != MailSendStatus.ERROR) {
            this.bean.getMailSearchFilter().setErrorMessage(null);
        }
    }

    /**
     * Renvoie le statut à afficher
     *
     * @param mail l'objet en cours de traitement
     * @return le statut à afficher OK/KO
     */
    public String getStatus(final MailReportingDTO mail) {

        String status = MailSendStatus.ERROR.name();
        for (MailSendStatus statusPossible : MailSendStatus.values()) {
            if (statusPossible.getValue().equals(mail.getStatusCode())) {
                status = statusPossible.name();
            }
        }
        return status;
    }

	/* **************************************************************************
	 * GETTERS / SETTERS / INJECTION
	 ************************************************************************* */

    /**
     * @return the bean
     */
    public com.web.mails.impl.MailViewBean getBean() {
        return bean;
    }

    /**
     * @param bean the bean to set
     */
    public void setBean(final MailViewBean bean) {
        this.bean = bean;
    }

    /**
     * @param mailService the mailService to set
     */
    public void setMailService(final MailReportingService mailService) {
        this.mailService = mailService;
    }

    /**
     * @param bundle the bundle to set
     */
    public void setBundle(final ResourceBundle bundle) {
		this.bundle = bundle;
	}
}
