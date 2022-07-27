/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.historyamount;

import com.abita.dao.historyamount.entity.HistoryAmountEntity;
import com.abita.dao.historyamount.exceptions.HistoryAmountDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO pour l’historisation des montants générés
 */
public interface IHistoryAmountDAO extends IAbstractDAO<HistoryAmountEntity, HistoryAmountDAOException> {

  /**
   * Permet de créer une nouvelle historisation en BDD
   * @param historyAmountDTO une historisation de montant
   * @return l'identifiant technique de l'historisation
   * @throws HistoryAmountDAOException une HistoryAmountDAOException
   */
  @Override
  Long create(HistoryAmountEntity historyAmountDTO) throws HistoryAmountDAOException;

  /**
   * Permet de supprimer les historisations des montants d'un contrat occupant
   * @param id identifiant du contrat occupant
   * @throws HistoryAmountDAOException une HistoryAmountDAOException;
   */
  void deleteAllHistoryAmountOfOneContract(Long id) throws HistoryAmountDAOException;

}
