/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.housing;

import com.abita.dto.HousingDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.services.housing.exceptions.HousingServiceFacadeException;

import java.util.List;

/**
 * Interface des services des logements
 *
 * @author
 */
public interface IHousingServiceFacade {

  /**
   * Permet de créer un nouveau logement en BDD
   *
   * @param housingDTO un logement
   * @return l'identifiant du logement
   * @throws HousingServiceFacadeException une HousingServiceException
   */
  Long create(HousingDTO housingDTO) throws HousingServiceFacadeException;

  /**
   * Permet de récupérer un logement par son identifiant en BDD
   *
   * @param id l'identifiant du logement
   * @return Un Logement
   * @throws HousingServiceFacadeException une HousingServiceException
   */
  HousingDTO get(Long id) throws HousingServiceFacadeException;

  /**
   * Met à jour un logement en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param housingDTO un logement
   * @throws HousingServiceFacadeException une HousingServiceException
   */
  void update(HousingDTO housingDTO) throws HousingServiceFacadeException;

  /**
   * Supprime un logement en BDD
   *
   * @param id l'identifiant du logement à supprimer
   * @throws HousingServiceFacadeException une HousingServiceException
   */
  void delete(Long id) throws HousingServiceFacadeException;

  /**
   * Permet de récupérer la liste de tous les logements en BDD
   *
   * @return une liste de tous les logements
   *
   * @throws HousingServiceFacadeException une HousingServiceException
   */
  List<HousingDTO> find() throws HousingServiceFacadeException;

  /**
   * Permet de récupérer la liste des logements par agence en BDD
   * @param idAgency l'identifiant technique de l'agence
   * @return la liste des logements
   * @throws HousingServiceFacadeException une HousingServiceFacadeException
   */
  List<HousingDTO> findHousingsByAgency(Long idAgency) throws HousingServiceFacadeException;

  /**
   * Permet de récupérer la liste des logements en BDD répondant aux critères choisis
   * @param housingCriteria les critères de recherche par logement
   * @param tenantCriteria les critères de recherche par occupant
   * @return une liste de tous les logements
   * @throws HousingServiceFacadeException une HousingServiceException
   */
  List<HousingDTO> findByCriteria(HousingCriteriaDTO housingCriteria, TenantCriteriaDTO tenantCriteria) throws HousingServiceFacadeException;

  /**
   * Savoir si un logement est référencé dans l'application
   * @param idHousing le logement recherché
   * @return true si référencé, false autrement
   * @throws HousingServiceFacadeException l'exception
   */
  boolean isRemovable(long idHousing) throws HousingServiceFacadeException;

  /**
   * Génère la référence du logement
   * @return la référence du logement
   * @throws HousingServiceFacadeException l'exception
   */
  String generateReference() throws HousingServiceFacadeException;

}
