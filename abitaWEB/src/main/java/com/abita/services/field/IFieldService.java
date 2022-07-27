/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.field;

import com.abita.dto.FieldDTO;
import com.abita.services.field.exceptions.FieldServiceException;

import java.util.List;

/**
 * Interface du service des domaines d'activité
 * @author
 *
 */
public interface IFieldService {

  /**
   * Permet de récupérer la liste de tous les domaines d'activité
   *
   * @return une liste de tous les domaines d'activité
   *
   * @throws FieldServiceException une FieldServiceException
   */
  List<FieldDTO> find() throws FieldServiceException;

  /**
   * Permet de créer un nouveau domaines d'activité  en BDD
   *
   * @param fieldDTO un domaine d'activité
   * @return l'identifiant du domaine d'activité
   * @throws FieldServiceException une FieldServiceException
   */
  Long create(FieldDTO fieldDTO) throws FieldServiceException;

  /**
   * Met à jour un domaine d'activité  en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param fieldDTO domaine d'activité
   * @throws FieldServiceException une FieldServiceException
   */
  void update(FieldDTO fieldDTO) throws FieldServiceException;

  /**
   * Supprime un domaines d'activité  en BDD
   *
   * @param id l'identifiant du domaine d'activité  à supprimer
   * @throws FieldServiceException une FieldServiceException
   */
  void delete(Long id) throws FieldServiceException;

}
