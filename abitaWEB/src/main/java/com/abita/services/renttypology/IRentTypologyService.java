/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.renttypology;

import com.abita.dto.RentTypologyDTO;
import com.abita.services.renttypology.exceptions.RentTypologyServiceException;

import java.util.List;

/**
 * Interface du service des typologies de loyer
 * @author
 *
 */
public interface IRentTypologyService {

  /**
   * Permet de récupérer la liste de toutes les typologies de loyer
   *
   * @return une liste de toutes les typologies de loyer
   *
   * @throws RentTypologyException une RentTypologyServiceException
   */
  List<RentTypologyDTO> find() throws RentTypologyServiceException;

  /**
   * Permet de créer une nouveau typologie de loyer  en BDD
   *
   * @param rentTypologyDTO une typologie de loyer
   * @return l'identifiant de la typologie de loyer
   * @throws RentTypologyServiceException une RentTypologyServiceException
   */
  Long create(RentTypologyDTO rentTypologyDTO) throws RentTypologyServiceException;

  /**
   * Met à jour une typologie de loyer  en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param rentTypologyDTO typologie de loyer
   * @throws RentTypologyServiceException une RentTypologyServiceException
   */
  void update(RentTypologyDTO rentTypologyDTO) throws RentTypologyServiceException;

  /**
   * Supprime une typologie de loyer  en BDD
   *
   * @param id l'identifiant de la typologie de loyer  à supprimer
   * @throws RentTypologyServiceException une RentTypologyServiceException
   */
  void delete(Long id) throws RentTypologyServiceException;

}
