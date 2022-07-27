/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.actionlogs;

import com.dao.actionlogs.entity.ActionLog;
import com.dao.actionlogs.exception.ActionLogDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 *
 * Requirements :
 * -	RM_LOG_002 : filter and display
 * -	RM_LOG_003 : retrieval of the selected log's information
 *
 * @author
 */
public interface IActionLogDAO extends IAbstractDAO<ActionLog, ActionLogDAOException> {
	/**
	 * Filters and returns the persisted objects
	 *
	 * @param filters The filter to apply
	 *
	 * @return The filtered list
	 *
	 * @throws ActionLogDAOException If an error occurs
	 */
	List<ActionLog> find(Object filters) throws ActionLogDAOException;

	/**
	 * Returns the number of objects that the filter would return
	 *
	 * @param filters The filter to apply
	 *
	 * @return The number of objects
	 *
	 * @throws ActionLogDAOException If an error occurs
	 */
	int getCount(Object filters) throws ActionLogDAOException;
}
