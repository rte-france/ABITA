/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.logs.impl;

import com.dto.ActionLogDTO;
import com.dto.ActionLogFilterDTO;
import com.dto.ActionLogMessageDTO;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

/**
 * Bean de l'écran des logs applicatifs
 *
 * @author
 */
public class ActionLogsBean implements Serializable {
	/**
	 * Serial id
	 */
	private static final long serialVersionUID = -657848333151993395L;

	// RM_LOG_002 - Propriétés de filtrage

	/**
	 * L'objet contenant les filtres
	 */
	private ActionLogFilterDTO filters;

	/** La liste des statuts */
	private List<SelectItem> status;

	// RM_LOG_002 - Propriétés d'affichage

	/**
	 * Les traces à afficher
	 */
	private List<? extends ActionLogDTO> logs;

	/* Listes de filtrage rapide */

	/** La liste pour le filtrage rapide des statuts */
	private List<SelectItem> statusFilterList;

	/** La liste pour le filtrage rapide des origines */
	private List<SelectItem> originFilterList;

	/** La liste pour le filtrage rapide selon la gravité du message */
	private List<SelectItem> severityFilterList;

	// RM_LOG_003 - Propriétés du log sélectionné

	/** Les messages associés au log sélectionné */
	private List<ActionLogMessageDTO> logMessages;

	/**
	 * Getter de logs
	 *
	 * @return the logs
	 */
	public List<? extends ActionLogDTO> getLogs() {
		return logs;
	}

	/**
	 * Setter de logs
	 *
	 * @param logs the logs to set
	 */
	public void setLogs(List<ActionLogDTO> logs) {
		this.logs = logs;
	}

	/**
	 * @return the logMessages
	 */
	public List<ActionLogMessageDTO> getLogMessages() {
		return logMessages;
	}

	/**
	 * @param logMessages the logMessages to set
	 */
	public void setLogMessages(List<ActionLogMessageDTO> logMessages) {
		this.logMessages = logMessages;
	}

	/**
	 * @return the statusFilterList
	 */
	public List<SelectItem> getStatusFilterList() {
		return statusFilterList;
	}

	/**
	 * @param statusFilterList the statusFilterList to set
	 */
	public void setStatusFilterList(List<SelectItem> statusFilterList) {
		this.statusFilterList = statusFilterList;
	}

	/**
	 * @return the originFilterList
	 */
	public List<SelectItem> getOriginFilterList() {
		return originFilterList;
	}

	/**
	 * @param originFilterList the originFilterList to set
	 */
	public void setOriginFilterList(List<SelectItem> originFilterList) {
		this.originFilterList = originFilterList;
	}

	/**
	 * @return the severityFilterList
	 */
	public List<SelectItem> getSeverityFilterList() {
		return severityFilterList;
	}

	/**
	 * @param severityFilterList the severityFilterList to set
	 */
	public void setSeverityFilterList(List<SelectItem> severityFilterList) {
		this.severityFilterList = severityFilterList;
	}

	/**
	 * @return the filters
	 */
	public ActionLogFilterDTO getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(ActionLogFilterDTO filters) {
		this.filters = filters;
	}

	/**
	 * @return the status
	 */
	public List<SelectItem> getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(List<SelectItem> status) {
		this.status = status;
	}
}
