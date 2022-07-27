/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.auditservice;

import com.dto.EventDurationDTO;
import com.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * Activite pour le tracage de l'activite de l'utilisateur
 *
 * @author
 */
public interface IActivityRecordService {

	/**
	 * Log l'utilisateur connecte
	 * @param request la request courante
	 * @param connectedUser utilisateur connecte a logger
	 */
	void logConnectedUser(HttpServletRequest request, UserDTO connectedUser);

	/**
	 * Log de la duree d'un evenement
	 * @param request requete courante
	 * @param eventDuration event duration
	 */
	void logEventDuration(HttpServletRequest request, EventDurationDTO eventDuration);

	/**
	 * Log le click sur un element
	 */
	void logElementClick();

}
