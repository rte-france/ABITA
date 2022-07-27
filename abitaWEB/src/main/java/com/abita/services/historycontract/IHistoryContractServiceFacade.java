/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.historycontract;

import com.abita.dto.ContractDTO;
import com.abita.dto.HistoryContractDTO;
import com.abita.services.historycontract.exceptions.HistoryContractServiceException;

/**
 * Interface du service façade d’historisation des contrats occupants
 * @author
 *
 */
public interface IHistoryContractServiceFacade {

  /**
   * Service qui permet l’historisation des contrats occupants
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  void historizeAllContracts() throws HistoryContractServiceException;

  /**
   * Service qui permet l’historisation des nouveaux contrats occupants
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  void historizeNewContracts() throws HistoryContractServiceException;

  /**
   * Service qui permet l’historisation temporaire des nouveaux contrats occupants à partir d'un contrat et de son nombre de mois rétroactif
   * @param contractDTO un contrat occupant
   * @param retroactivitysMonths le nombre de mois rétroactif
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  void historizeOneNewContractTemporarily(ContractDTO contractDTO, Integer retroactivitysMonths) throws HistoryContractServiceException;

  /**
   *  Service qui permet de supprimer les historisations des contrats occupants vieille d’un an
   * @param month le mois à supprimer
   * @param year l’année à supprimer
   * @throws HistoryContractServiceException une HistoryBenefitsServiceException
   */
  void deleteOldContracts(int month, int year) throws HistoryContractServiceException;

  /**
   * Service qui permet de récuperer un contrat occupant en fonction de son id, de son mois d’historisation et de son année
   * @param id l’identifiant du contrat occupant
   * @param month le mois d’historisation du contrat occupant
   * @param year l’année d’historisation du contrat occupant
   * @return l’historisation du contrat occupant
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  HistoryContractDTO get(Long id, int month, int year) throws HistoryContractServiceException;

  /**
   * Met à jour une historisation d’un contrat occupant
   * @param historyContractDTO historisation du contrat occupant modifié
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  void update(HistoryContractDTO historyContractDTO) throws HistoryContractServiceException;

  /**
   * remplace toutes les historisations temporaire d'un contrat occupant
   * @param contract un contrat occupant
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  void replaceAllTemporaryContractHistorisations(ContractDTO contract) throws HistoryContractServiceException;

  /**
   * Service permettant de supprimer les historisations d'un contrat occupant
   * @param id identifiant du contrat occupant
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  void deleteAllHistoryContractOfOneContract(Long id) throws HistoryContractServiceException;


  /**
   * Service qui permet l’historisation temporaire d'un contrat occupants à partir d'un contrat
   * @param contractDTO un contrat occupant
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  void historizeCurrentMonthOneContractTemporarily(ContractDTO contractDTO) throws HistoryContractServiceException;
}
