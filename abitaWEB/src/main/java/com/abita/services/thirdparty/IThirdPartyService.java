/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.thirdparty;

import com.abita.dto.ThirdPartyDTO;
import com.abita.dto.unpersist.ThirdPartyCriteriaDTO;
import com.abita.services.thirdparty.exceptions.ThirdPartyServiceException;

import java.util.List;

/**
 * Interface des services des tiers
 * @author
 *
 */
public interface IThirdPartyService {

  /**
   * Permet de créer un nouveau tiers en BDD
   *
   * @param thirdPartyDTO un tiers
   * @return l'identifiant du tiers
   * @throws ThirdPartyServiceException une ThirdPartyServiceException
   */
  Long create(ThirdPartyDTO thirdPartyDTO) throws ThirdPartyServiceException;

  /**
   * Permet de récupérer un tiers par son identifiant en BDD
   *
   * @param id l'identifiant du tiers
   * @return Un tiers
   * @throws ThirdPartyServiceException une ThirdPartyServiceException
   */
  ThirdPartyDTO get(Long id) throws ThirdPartyServiceException;

  /**
   * Met à jour un tiers en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param thirdPartyDTO un tiers
   * @throws ThirdPartyServiceException une ThirdPartyServiceException
   */
  void update(ThirdPartyDTO thirdPartyDTO) throws ThirdPartyServiceException;

  /**
   * Supprime un tiers en BDD
   *
   * @param id l'identifiant tu tiers à supprimer
   * @throws ThirdPartyServiceException une ThirdPartyServiceException
   */
  void delete(Long id) throws ThirdPartyServiceException;

  /**
   * Permet de récupérer la liste de tous les tiers en BDD
   *
   * @return une liste de tous les tiers
   *
   * @throws ThirdPartyServiceException une ThirdPartyServiceException
   */
  List<ThirdPartyDTO> find() throws ThirdPartyServiceException;

  /**
   * Permet de vérifier si un tiers n'existe pas par sa référence GCP
   * @param gcpReference la référence GCP du tiers
   * @return un booléen flagant si le tiers existe (true) ou non (false)
   * @throws ThirdPartyServiceException une ThirdPartyServiceException
   */
  boolean isThirdPartyExist(String gcpReference) throws ThirdPartyServiceException;

  /**
   * Permet de récupérer la liste des tiers en BDD répondant aux critères choisis
   * @param criteria les critères de recherche
   *
   * @return une liste de tous les tiers
   *
   * @throws ThirdPartyServiceException une ThirdPartyServiceException
   */
  List<ThirdPartyDTO> findByCriteria(ThirdPartyCriteriaDTO criteria) throws ThirdPartyServiceException;

  /**
   * Permet de récupérer un tiers par sa référence GCP
   * @param thirdPartyToOpenId la référence GCP
   * @return un tiers
   * @throws ThirdPartyServiceException une ThirdPartyServiceException
   */
  ThirdPartyDTO getByGCPRef(String thirdPartyToOpenId) throws ThirdPartyServiceException;

  /**
   * Permet d'obtenir la liste des tiers d'un logement
   * @param idHousing : l'id du logement
   * @return la liste des tiers
   * @throws ThirdPartyServiceException l'exception
   */
  List<ThirdPartyDTO> findThirdPartyByHousing(long idHousing) throws ThirdPartyServiceException;

  /**
   * Permet de savoir si un tiers est rattaché dans l'application
   * @param idThirdParty l'ID du tiers
   * @return true si vide, false autrement
   * @throws ThirdPartyServiceException l'exception
   */
  boolean isRemovable(long idThirdParty) throws ThirdPartyServiceException;

}
