/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.heatingtype;

import com.abita.dto.HeatingTypeDTO;
import com.abita.services.heatingtype.exceptions.HeatingTypeServiceException;

import java.util.List;

/**
 * Interface du service des types de chauffage
 */
public interface IHeatingTypeService {

  /**
   * Permet de récupérer la liste de tous les types de chauffage
   *
   * @return une liste de tous les types de chauffage
   *
   * @throws HeatingTypeServiceException une HeatingTypeServiceException
   */
  List<HeatingTypeDTO> find() throws HeatingTypeServiceException;
}
