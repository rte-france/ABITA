/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.actionlogs;

import com.dto.ActionLogDTO;
import com.dto.ActionLogFilterDTO;
import com.services.actionlogs.exception.ActionLogServiceException;
import com.services.common.exception.TooManyResultsException;

import java.util.List;

/**
 * Service used to create, update and retrieve ActionLogDTOs
 *
 * Exigences liées :
 * -	RM_LOG_002 : affichage, filtrage
 * -	RM_LOG_003 : récupération des informations d'un log
 *
 * @author
 */
public interface IActionLogService {

	/**
	 * Retrieve and returns all the persisted light objects
	 *
	 * @return List<ActionLogDTO> All the objects without messages
	 *
	 * @throws ActionLogServiceException if an error occurs
	 * @throws TooManyResultsException If method could return too many results
	 */
	List<ActionLogDTO> find() throws ActionLogServiceException, TooManyResultsException;

	/**
	 * Retrieve and returns all the persisted objects that match the specified filter
	 *
	 * @param filters The objects containing the filter values
	 *
	 * @return List<ActionLogDTO> All the objects that match the specified filter without messages
	 *
	 * @throws ActionLogServiceException If an error occurs
	 * @throws TooManyResultsException If method could return too many results
	 */
	List<ActionLogDTO> find(ActionLogFilterDTO filters) throws ActionLogServiceException, TooManyResultsException;

	/**
	 * Retrieve and returns the objects with the specified identifier.
	 * An {@link IllegalArgumentException} is thrown if the id is null
	 *
	 * @param id The object's identifier (must not be null)
	 *
	 * @return The object
	 *
	 * @throws ActionLogServiceException If an error occurs
	 */
	ActionLogDTO get(Long id) throws ActionLogServiceException;

	/**
	 * Save or update the specified object
	 *
	 * @param actionLog The object to save
	 *
	 * @throws ActionLogServiceException If an error occurs
	 */
	//	void save(ActionLogDTO actionLog) throws ActionLogServiceException;

	/**
	 * Persist the specified object
	 *
	 * @param actionLog The object to persist
	 * @return The identifier generated
	 *
	 * @throws ActionLogServiceException If an error occurs
	 */
	Long create(final ActionLogDTO actionLog) throws ActionLogServiceException;

	/**
	 * Update the specified object
	 *
	 * @param actionLog The specified object
	 * @return The updated object
	 * @throws ActionLogServiceException If an error occurs
	 */
	ActionLogDTO update(ActionLogDTO actionLog) throws ActionLogServiceException;
}
