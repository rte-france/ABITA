/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.tenant;

import com.abita.dto.TenantDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.services.tenant.exceptions.TenantServiceFacadeException;

import java.util.List;

/**
 * Interface des services des occupants
 *
 * @author
 */
public interface ITenantServiceFacade {

  /**
   * Permet de créer un nouvel occupant en BDD
   *
   * @param tenantDTO un occupant
   * @return l'identifiant de l'occupant
   * @throws TenantServiceFacadeException une TenantServiceException
   */
  Long create(TenantDTO tenantDTO) throws TenantServiceFacadeException;

  /**
   * Permet de récupérer un occupant par son identifiant en BDD
   *
   * @param id l'identifiant de l'occupant
   * @return Un occupant
   * @throws TenantServiceFacadeException une TenantServiceException
   */
  TenantDTO get(Long id) throws TenantServiceFacadeException;

  /**
   * Met à jour un occupant en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param tenantDTO un occupant
   * @throws TenantServiceFacadeException une TenantServiceException
   */
  void update(TenantDTO tenantDTO) throws TenantServiceFacadeException;

  /**
   * Supprime un occupant en BDD
   *
   * @param id l'identifiant de l'occupant à supprimer
   * @throws TenantServiceFacadeException une TenantServiceException
   */
  void delete(Long id) throws TenantServiceFacadeException;

  /**
   * Permet de récupérer la liste de tous les occupants en BDD
   *
   * @return une liste de tous les occupants
   *
   * @throws TenantServiceFacadeException une TenantServiceException
   */
  List<TenantDTO> find() throws TenantServiceFacadeException;

  /**
   * Permet de récupérer la liste des occupants en BDD répondant aux critères choisis
   * @param tenantCriteria les critères de recherche occupant
   * @param housingCriteria les critères de recherche logement
   * @return une liste de tous les occupants
   * @throws TenantServiceFacadeException une TenantServiceException
   */
  List<TenantDTO> findByCriteria(TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria) throws TenantServiceFacadeException;

  /**
   * Savoir si un occupant est référencé dans l'application
   * @param idTenant : ID de l'occupant
   * @return true si référencé, false autrement
   * @throws TenantServiceFacadeException l'exception
   */
  boolean isRemovable(long idTenant) throws TenantServiceFacadeException;

  /**
   * Retourne la liste des NNI des salariés logés
   * @return la liste des NNI
   * @throws TenantServiceFacadeException une TenantServiceException
   */
  List<String> findNNI() throws TenantServiceFacadeException;

  /**
   * retourne l'occupant recherché par son NNI
   * @param nni le NNI d'un occupant
   * @return l'occupant recherché
   * @throws TenantServiceFacadeException une TenantServiceException
   */
  TenantDTO findTenantByNNI(String nni) throws TenantServiceFacadeException;

  /**
   * Permet de mettre à jour le champ cadre à octobre N-1
   * @throws TenantServiceFacadeException une TenantServiceException
   */
  void updateManagerialLastYear() throws TenantServiceFacadeException;

  /**
   * Permet de mettre à jour le champ nombre de personne à octobre N-1
   * @throws TenantServiceFacadeException une TenantServiceException
   */
  void updateHouseholdSizeLastYear() throws TenantServiceFacadeException;

}
