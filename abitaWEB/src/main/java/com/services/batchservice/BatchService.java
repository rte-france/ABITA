/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.services.batchservice;

import com.dto.batch.BatchHistoriqueDTO;
import com.services.batchservice.exception.BatchServiceException;

import java.util.Date;
import java.util.List;

/**
 * Service associées aux batch.
 * @author
 *
 */
public interface BatchService {

    /**
     * Find last batch.
     *
     * @param batchName the batch name
     * @return the list
     * @throws BatchServiceException Signals that an Service exception has occurred.
     */
    List<BatchHistoriqueDTO> findLastBatchHistorique(String batchName) throws BatchServiceException;

    /**
     * Find batch historique.
     *
     * @param batchName the batch name
     * @param nbHisto the nb histo
     * @return the list
     * @throws BatchServiceException Signals that an Service exception has occurred.
     */
    List<BatchHistoriqueDTO> findBatchHistorique(String batchName, int nbHisto) throws BatchServiceException;

    /**
     * Creates the batch.
     *
     * @param batch the batch
     * @return the batch historique
     * @throws BatchServiceException Signals that an Service exception has occurred.
     */
    BatchHistoriqueDTO createBatch(BatchHistoriqueDTO batch) throws BatchServiceException;

    /**
     * Update batch.
     *
     * @param batch the batch
     * @return the batch historique
     * @throws BatchServiceException Signals that an Service exception has occurred.
     */
    BatchHistoriqueDTO updateBatch(BatchHistoriqueDTO batch) throws BatchServiceException;

    /**
     * Find batch in progress.
     *
     * @param batchName the batch name
     * @param minDate the min date (if the batch is minus, he's probably in error)
     * @return the list
     * @throws BatchServiceException Signals that an Service exception has occurred.
     */
    List<BatchHistoriqueDTO> findBatchInProgress(String batchName, Date minDate) throws BatchServiceException;
}
