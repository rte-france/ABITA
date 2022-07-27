/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.historycontract;

import com.abita.dao.historycontract.entity.HistoryContractEntity;
import com.abita.dao.historycontract.exceptions.HistoryContractDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO pour l'historisation des contrats occupants
 * @author
 *
 */
public interface IHistoryContractDAO extends IAbstractDAO<HistoryContractEntity, HistoryContractDAOException> {

  /**
   * DAO qui permet l'historisation des contrats occupants
   * @throws HistoryContractDAOException une HistoryContractDAOException
   */
  void historizeAllContracts() throws HistoryContractDAOException;

  /**
   * DAO qui permet l'historisation des contrats occupant vieille d'un an
   * @param month le mois à supprimer
   * @param year l'année à supprimer
   * @throws HistoryContractDAOException une HistoryContractDAOException
   */
  void deleteOldContracts(int month, int year) throws HistoryContractDAOException;

  /**
   * DAO qui permet de récupérer l'historique d'un contrat occupant selon son id, son mois et son année d'historisation
   * @param id l'id du contrat occupant
   * @param month le mois d'historisation
   * @param year l'année d'historisation
   * @return l'archive du contrat occupant
   * @throws HistoryContractDAOException une HistoryContractDAOException
   */
  HistoryContractEntity get(Long id, int month, int year) throws HistoryContractDAOException;

  /**
   * Supprime toutes les historisations temporaire d'un contrat occupant
   * @param id l'identifiant du contrat occupant
   * @throws HistoryContractDAOException une HistoryContractDAOException
   */
  void deleteAllTemporaryContractHistorisations(Long id) throws HistoryContractDAOException;

  /**
   * Permet de supprimer les historisations d'un contrat occupant
   * @param id identifiant du contrat occupant
   * @throws HistoryContractDAOException une HistoryContractDAOException;
   */
  void deleteAllHistoryContractOfOneContract(Long id) throws HistoryContractDAOException;
}
