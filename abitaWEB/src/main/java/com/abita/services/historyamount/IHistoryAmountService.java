/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.services.historyamount;

import com.abita.dto.HistoryAmountDTO;
import com.abita.services.historyamount.exceptions.HistoryAmountServiceException;

/**
 * Interface du service d'historisation des montants générés
 * @author
 *
 */
public interface IHistoryAmountService {

  /**
   * Permet de créer une nouvelle historisation en BDD
   * @param historyAmountDTO une historisation de montant
   * @return l'identifiant technique de l'historisation
   * @throws HistoryAmountServiceException une HistoryAmountServiceException
   */
  Long create(HistoryAmountDTO historyAmountDTO) throws HistoryAmountServiceException;

  /**
   * Service permettant de supprimer les montants d'un contrat occupant
   * @param id identifiant du contrat occupant
   * @throws HistoryAmountServiceException une HistoryAmountServiceException
   */
  void deleteAllHistoryOfOneContract(Long id) throws HistoryAmountServiceException;

}
