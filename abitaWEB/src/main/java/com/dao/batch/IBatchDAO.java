/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.batch;

import com.dao.batch.entity.BatchHistorique;
import com.dao.batch.exception.BatchDAOException;
import com.dao.common.IAbstractDAO;

import java.util.Date;
import java.util.List;

/**
 * BatchDAO Interface
 * @author
 *
 */
public interface IBatchDAO extends IAbstractDAO<BatchHistorique, BatchDAOException> {

	/**
	 * Renvoie la liste des historiques d'un Batch.
	 * @param name batch's name
	 * @param maxHisto history level
	 * @return list of histories
	 * @throws BatchDAOException if error occurs
	 */
	List<BatchHistorique> findBatchHistoriqueByName(String name, int maxHisto) throws BatchDAOException;

	/**
	 * Find batch in progress.
	 *
	 * @param batchName the batch name
	 * @param minDate the min date
	 * @return the list
	 * @throws BatchDAOException Signals that an DAO exception has occurred.
	 */
	List<BatchHistorique> findBatchInProgress(String batchName, Date minDate) throws BatchDAOException;

}
