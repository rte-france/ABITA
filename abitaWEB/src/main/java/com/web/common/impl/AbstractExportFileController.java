/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.impl;

import com.dto.EventDurationDTO;
import com.dto.UserDTO;
import com.services.auditservice.IActivityRecordService;
import com.services.common.constants.Constants;
import com.services.common.constants.DateTimeConstants;
import com.services.common.util.DateUtils;
import com.web.audit.data.OptionalParameter;
import com.web.common.exception.ExportFileException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Controlleur abstrait d'export de fichier
 * @author
 */
public abstract class AbstractExportFileController implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8644173608934866850L;

	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(AbstractExportFileController.class);

	/** activityRecordeService */
	private IActivityRecordService activityRecordService;

	/** Date de début de l'action */
	private Calendar dateBegin;

	/** Date de fin de l'action */
	private Calendar dateEnd;

	/** Identifiant de l'élément à logguer */
	private String idElement;

	/** nni de l'utilisateur connecté */
	private String nni;

	/** Parametres optionnels */
	private List<OptionalParameter> optionalParameters;

	/**
	 * Export de fichier sans indicateur de durée
	 * @throws ExportFileException exception
	 */
	public abstract void exportFile() throws ExportFileException;

	/**
	 * Envoie du fichier sur le flux de sortie
	 * @throws ExportFileException exception
	 */
	public abstract void sendResponseFile() throws ExportFileException;

	/**
	 * Export de fichier avec indicateur de durée
	 * @param action action
	 * @throws ExportFileException exception
	 */
	public void exportFileWithIndicatorDuration(ActionEvent action) throws ExportFileException {
		// Initialisation de l'indicateur
		this.start();

		// Appel du métier
		this.exportFile();

		// Finalisation de l'indicateur
		this.end(action);

		// Envoi du fichier
		this.sendResponseFile();
	}

	/**
	 * export file
	 * @param action action
	 * @throws ExportFileException exception
	 */
	public void simpleExportFile(ActionEvent action) throws ExportFileException {
		// nothing todo
	}

	/**
	 * Initialise l'indicateur de durée
	 */
	private void start() {
		dateBegin = Calendar.getInstance();
		LOGGER.debug("**** Date de début : "
				+ DateUtils.getDateFormat(dateBegin.getTime(), DateTimeConstants.HOUR_FORMAT));
	}

	/**
	 * Finalise l'indicateur de durée
	 * @param action action
	 */
	private void end(ActionEvent action) {
		dateEnd = Calendar.getInstance();
		LOGGER.debug("**** Date de fin : " + DateUtils.getDateFormat(dateEnd.getTime(), DateTimeConstants.HOUR_FORMAT));

		UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get(Constants.USER_KEY);
		this.nni = user.getLogin();

		// Log de l'indicateur
		this.idElement = action.getComponent().getId();
		EventDurationDTO eventDuration = new EventDurationDTO(nni, idElement, dateBegin.getTimeInMillis(),
				dateEnd.getTimeInMillis(), optionalParameters);
		activityRecordService.logEventDuration((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest(), eventDuration);
	}

	/**
	 * @param idElement the idElement to set
	 */
	public void setIdElement(String idElement) {
		this.idElement = idElement;
	}

	/**
	 * @param optionalParameters the optionalParameters to set
	 */
	public void setOptionalParameters(List<OptionalParameter> optionalParameters) {
		this.optionalParameters = optionalParameters;
	}

	/**
	 * @return the activityRecordService
	 */
	public IActivityRecordService getActivityRecordService() {
		return activityRecordService;
	}

	/**
	 * @param activityRecordService the activityRecordService to set
	 */
	public void setActivityRecordService(IActivityRecordService activityRecordService) {
		this.activityRecordService = activityRecordService;
	}
}
