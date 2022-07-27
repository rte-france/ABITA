/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.site;

import com.abita.dto.SiteDTO;
import com.abita.services.site.exception.SiteServiceException;

import java.util.List;

/**
 * Interface du service des sites
 */
public interface ISiteService {

  /**
   * Permet de récupérer la liste de tous les sites avec l’information de si on peut les supprimer
   *
   * @return une liste de tous les sites
   * @throws SiteServiceException une SiteServiceException
   */
  List<SiteDTO> findAllSites() throws SiteServiceException;

  /**
   * Permet de récupérer la liste des sites en fonction de l'agence choisie par l'utilisateur
   * @param agencyId l'id de l'agence choisie
   * @return la liste des sites rattachés à l'agence choisie
   * @throws SiteServiceException en cas d'exception hibernate
   */
  List<SiteDTO> findSitesByAgency(Long agencyId) throws SiteServiceException;

  /**
   * Permet de créer un site
   *
   * @param site le site à créer au format DTO
   * @return l'identifiant du site
   * @throws SiteServiceException une SiteServiceException
   */
  Long create(SiteDTO site) throws SiteServiceException;

  /**
   * Permet de mettre à jour un site
   *
   * @param site le site à mettre à jour
   * @throws SiteServiceException une SiteServiceException
   */
  void update(SiteDTO site) throws SiteServiceException;

  /**
   * Permet de récupérer un site par son identifiant
   *
   * @param id l'identifiant du site
   * @return le site au format DTO
   * @throws SiteServiceException une SiteServiceException
   */
  SiteDTO get(Long id) throws SiteServiceException;

  /**
   * Permet de Supprimer un site
   *
   * @param id l’identifiant d'un site à supprimer
   * @throws SiteServiceException une SiteServiceException
   */
  void delete(Long id) throws SiteServiceException;

  /**
   * Permet de sauvegarder une liste de sites
   *
   * @param sites la listes contenant tous les sites (y compris ceux déjà existant)
   * @throws SiteServiceException une SiteServiceException
   */
  void save(List<SiteDTO> sites) throws SiteServiceException;

}
