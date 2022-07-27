/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.historytenant;

import com.abita.dto.HistoryTenantDTO;
import com.abita.dto.TenantDTO;
import com.abita.services.historytenant.exceptions.HistoryTenantServiceException;

/**
 * Interface du service d'historisation des occupants
 * @author
 *
 */
public interface IHistoryTenantService {

  /**
   * Permet de créer un nouvel occupant d’historisation
   *
   * @param historyTenantDTO un occupant
   * @return l’identifiant de l’occupant
   * @throws HistoryTenantServiceException une HistoryTenantServiceException
   */
  Long create(HistoryTenantDTO historyTenantDTO) throws HistoryTenantServiceException;

  /**
   * Service qui permet l'historisation des occupants
   * @throws HistoryTenantServiceException une HistoryTenantServiceException
   */
  void historizeAllTenants() throws HistoryTenantServiceException;

  /**
   * Service qui permet de supprimer les historisations des occupants vieille d'un an
   * @param month le mois d'historisation
   * @param year l'année d'historisation
   * @throws HistoryTenantServiceException une HistoryTenantServiceException
   */
  void deleteOldTenants(int month, int year) throws HistoryTenantServiceException;

  /**
   * Service qui permet de récuperer un occupant en fonction de son id, de son mois d’historisation et de son année
   * @param id l’identifiant de l’occupant
   * @param month le mois d’historisation de l’occupant
   * @param year l’année d’historisation de l’occupant
   * @return l’historisation de l’occupant
   * @throws HistoryTenantServiceException une HistoryTenantServiceException
   */
  HistoryTenantDTO get(Long id, int month, int year) throws HistoryTenantServiceException;

  /**
   * Service qui permet de récuperer l’occupant le plus récent en fonction de son id, de son mois d’historisation et de son année
   * @param tenant l’occupant
   * @param month le mois d’historisation de l’occupant
   * @param year l’année d’historisation de l’occupant
   * @param temp true si l'historisation est temporaire
   * @return l’historisation de l’occupant
   * @throws HistoryTenantServiceException une HistoryTenantServiceException
   */
  HistoryTenantDTO getLatest(TenantDTO tenant, int month, int year, Boolean temp) throws HistoryTenantServiceException;

  /**
   * Service qui permet de récupérer une historisation d’occupant en fonction de son id
   * @param historyTenantId identifiant de l’historisation de l’occupant
   * @return historisation d’occupant
   * @throws HistoryTenantServiceException une HistoryTenantServiceException
   */
  HistoryTenantDTO get(Long historyTenantId) throws HistoryTenantServiceException;

  /**
   * Service qui permet de mettre à jour les historisations temporaires d’un occupant
   * @param tenant occupant
   * @throws HistoryTenantServiceException une HistoryTenantServiceException
   */
  void updateTemporaries(TenantDTO tenant) throws HistoryTenantServiceException;

  /**
   * Service permettant de supprimer les historisations d'un occupant
   * @param id identifiant d'un occupant
   * @throws HistoryTenantServiceException une HistoryTenantServiceException
   */
  void deleteAllHistoryTenantOfOneTenant(Long id) throws HistoryTenantServiceException;

}
