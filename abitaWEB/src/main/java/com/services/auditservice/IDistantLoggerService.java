/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.services.auditservice;

import com.dto.EventDurationDTO;
import com.dto.UserDTO;
import com.web.audit.data.OptionalParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author
 *
 */
public interface IDistantLoggerService {

	/**
	 * Log l'utilisateur connecte
	 * @param request request
	 * @param connectedUser utilisateur connecte a logger
	 * @param optionalParameters optional parameters
	 */
	void logConnectedUser(HttpServletRequest request, UserDTO connectedUser, List<OptionalParameter> optionalParameters);

	/**
	 * Log de la duree d'un evenement
	 * @param request requete en cours
	 * @param enventDuration envent duration
	 */
	void logEventDuration(HttpServletRequest request, EventDurationDTO enventDuration);

	/**
	 * Log le click sur un element
	 */
	void logElementClick();
}
