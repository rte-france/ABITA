/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.housing;

import com.abita.dao.housing.entity.HousingEntity;
import com.abita.dao.housing.exceptions.HousingDAOException;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface des DAO des logements
 *
 * @author
 */
public interface IHousingDAO extends IAbstractDAO<HousingEntity, HousingDAOException> {

  /**
   *  Permet de récupérer la liste des logements en BDD répondant aux critères choisis
   * @param housingCriteria critère de recherche par logement
   * @param tenantCriteria critère de recherche par occupant
   * @return liste de logement
   * @throws HousingDAOException une HousingDAOException
   */
  List<HousingEntity> findByCriteria(HousingCriteriaDTO housingCriteria, TenantCriteriaDTO tenantCriteria) throws HousingDAOException;

  /**
   * Permet de récupérer la liste des logements par agence en BDD
   * @param idAgency l'identifiant technique de l'agence
   * @return la liste des logements
   * @throws HousingDAOException une HousingDAOException
   */
  List<HousingEntity> findHousingsByAgency(Long idAgency) throws HousingDAOException;

  /**
   * Vérifie si un logement est référencé dans l'application
   * @param idHousing le logement recherché
   * @return true si référencé, false autrement
   * @throws HousingDAOException l'exception
   */
  boolean isRemovable(long idHousing) throws HousingDAOException;

  /**
   * Génère la référence du logement
   * @return la référence du logement
   * @throws HousingDAOException l'exception
   */
  String generateReference() throws HousingDAOException;

}
