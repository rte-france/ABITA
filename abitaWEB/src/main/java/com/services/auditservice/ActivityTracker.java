/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.auditservice;

import com.dto.EventDurationDTO;
import com.services.auditservice.exception.DataAccessException;
import com.web.audit.data.OptionalParameter;

import java.util.List;

/**
 * Interface in charge of the management of the activity logs.
 * @author
 * @version 1.0
 */
public interface ActivityTracker {

	/**
	 * Save an activity log.
	 * @param idApplication the application identifier (as for instance SAPSEM)
	 * @param eventDuration event duration
	 * @param siteVersion Application version
	 * @throws DataAccessException if there's a problem while reading / saving data
	 */
	void saveDuration(final String idApplication, final String siteVersion, EventDurationDTO eventDuration)
			throws DataAccessException;

	/**
	 * Count how many times an item is clicked in an application
	 * @param idApplication the application identifier (as for instance SAPSEM)
	 * @param idComponent the component identifier (representing for example an action button)
     * @param idComponent2 the component identifier (representing for example an action button)
     * @param optParametersAsList map of optional parameters
	 * @throws DataAccessException if there's a problem while reading / saving data
	 */
	void count(final String idApplication, final String idComponent, String idComponent2,
			List<OptionalParameter> optParametersAsList) throws DataAccessException;

	/**
	 * Method used to check if a user is still active in the application.
	 * @param applicationId the application identifier (as for instance SAPSEM)
	 * @param userId the user id (AKA login, or nni)
	 * @param area the area of the user to log
	 * @param optParametersAsList optional parameters
	 * @throws DataAccessException if there's a problem while reading / saving data
	 */
	void userLogged(final String applicationId, final String userId, final String area,
			List<OptionalParameter> optParametersAsList) throws DataAccessException;

	// FFT 3562
	/**
	 * Method used to check ...
	 * @param applicationId the application identifier (as for instance SAPSEM)
	 * @param userId the user id (AKA login, or nni)
	 * @param area the area of the user to log
	 * @param profile User profile
	 * @throws DataAccessException if there's a problem while reading / saving data
	 */
	void authorizedUser(final String applicationId, final String userId, final String area, final String profile)
			throws DataAccessException;
}
