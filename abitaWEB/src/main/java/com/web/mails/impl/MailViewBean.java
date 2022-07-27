/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.mails.impl;

import com.dto.mail.MailReportingDTO;
import com.dto.mail.MailSearchFilterDTO;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

/**
 * Bean pour la consultation des envois de mails
 *
 * @author
 */
public class MailViewBean implements Serializable {

	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = -8770051286963833657L;

	/** Les critères de recherche */
	private MailSearchFilterDTO mailSearchFilter = new MailSearchFilterDTO();

	/** La liste des mails envoyés */
	private List<MailReportingDTO> mails;

	/** La liste des codes d'erreur disponibles */
	private List<SelectItem> errorMessages;

	/** La liste des status sélectionnables*/
	private List<SelectItem> statusList;

	/**
	 * @return the mailSearchFilter
	 */
	public MailSearchFilterDTO getMailSearchFilter() {
		return mailSearchFilter;
	}

	/**
	 * @param mailSearchFilter the mailSearchFilter to set
	 */
	public void setMailSearchFilter(MailSearchFilterDTO mailSearchFilter) {
		this.mailSearchFilter = mailSearchFilter;
	}

	/**
	 * @return the mails
	 */
	public List<MailReportingDTO> getMails() {
		return mails;
	}

	/**
	 * @param mails the mails to set
	 */
	public void setMails(List<MailReportingDTO> mails) {
		this.mails = mails;
	}

	/**
	 * @return the errorMessages
	 */
	public List<SelectItem> getErrorMessages() {
		return errorMessages;
	}

	/**
	 * @param errorMessages the errorMessages to set
	 */
	public void setErrorMessages(List<SelectItem> errorMessages) {
		this.errorMessages = errorMessages;
	}

	/**
	 * @return the statusList
	 */
	public List<SelectItem> getStatusList() {
		return statusList;
	}

	/**
	 * @param statusList the statusList to set
	 */
	public void setStatusList(List<SelectItem> statusList) {
		this.statusList = statusList;
	}
}
