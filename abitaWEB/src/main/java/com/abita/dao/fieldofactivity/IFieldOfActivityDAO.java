/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.fieldofactivity;

import com.abita.dao.fieldofactivity.entity.FieldOfActivityEntity;
import com.abita.dao.fieldofactivity.exceptions.FieldOfActivityDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface des DAO de l'administration des domaines d'activité
 *
 * @author
 */
public interface IFieldOfActivityDAO extends IAbstractDAO<FieldOfActivityEntity, FieldOfActivityDAOException> {

  /**
   * Permet de récupérer la liste de tous les domaines d'activité en BDD ainsi que leur nombre d'utilisation
   *
   * @return une liste de tous les domaines d'activité
   * @throws FieldOfActivityDAOException une FieldOfActivityDAOException
   */
  List<FieldOfActivityEntity> findAllFieldOfActivity() throws FieldOfActivityDAOException;

  /**
   * permet de récupérer la liste de tous les domaines d'activité en fonction d'une agence
   * @param idAgency l'identifiant technique d'une agence
   * @return une liste de tous les domaines d'activité d'une agence
   * @throws FieldOfActivityDAOException une FieldOfActivityDAOException
   */
  List<FieldOfActivityEntity> findFieldOfActivityByAgency(Long idAgency) throws FieldOfActivityDAOException;

  /**
   * Permet de mettre à jour l'agence des contrats occupants lors d'une modification de l'agence de leur domaine d'activité
   * @param idAgency l'identifiant technique de l'agence
   * @param idFieldOfActivity l'identifiant technique du domaine d'activité
   * @throws FieldOfActivityDAOException une FieldOfActivityDAOException
     */
  void updateAgencyOfContract(Long idAgency, Long idFieldOfActivity) throws FieldOfActivityDAOException;

  /**
   * Permet de mettre à jour l'agence des contrats tiers lors d'une modification de l'agence de leur domaine d'activité
   * @param idAgency l'identifiant technique de l'agence
   * @param idFieldOfActivity l'identifiant technique du domaine d'activité
   * @throws FieldOfActivityDAOException une FieldOfActivityDAOException
   */
  void updateAgencyOfThirdPartyContract(Long idAgency, Long idFieldOfActivity) throws FieldOfActivityDAOException;
}
