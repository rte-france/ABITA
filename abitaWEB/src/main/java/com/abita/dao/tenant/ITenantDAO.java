/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.tenant;

import com.abita.dao.tenant.entity.TenantEntity;
import com.abita.dao.tenant.exceptions.TenantDAOException;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface des DAO des occupants
 *
 * @author
 */
public interface ITenantDAO extends IAbstractDAO<TenantEntity, TenantDAOException> {

  /**
   * Retourne la liste des NNI des salariés logés
   * @return la liste des NNI
   * @throws TenantDAOException une TenantDAOException
   */
  List<String> findNNI() throws TenantDAOException;

  /**
   * Permet de récupérer la liste des occupants en BDD répondant aux critères choisis
   * @param tenantCriteria les critères de recherche occupants
   * @param housingCriteria les critères de recherche logements
   * @return une liste de tous les occupants
   * @throws TenantDAOException une TenantDAOException
   */
  List<TenantEntity> findByCriteria(TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria) throws TenantDAOException;

  /**
   * Vérifie si un occupant est référencé dans l'application
   * @param idTenant : l'ID de l'occupant
   * @return true si référencé, false autrement
   * @throws TenantDAOException l'exception
   */
  boolean isRemovable(long idTenant) throws TenantDAOException;

  /**
   * Permet de recherche un occupant par son NNI
   * @param nni Un NNI
   * @return l'occupant
   * @throws TenantDAOException une TenantDAOException
   */
  TenantEntity findTenantByNNI(String nni) throws TenantDAOException;

  /**
   * Permet de mettre à jour les cadres avec la valeur cadre à octobre N-1
   * @throws TenantDAOException une TenantDAOException
   */
  void updateManagerialLastYear() throws TenantDAOException;

  /**
   * Permet de mettre à jour le nombre de personne à octobre N-1 avec le nombre de personne à octobre N-1
   * @throws TenantDAOException une TenantDAOException
   */
  void updateHouseholdSizeLastYear() throws TenantDAOException;

}
