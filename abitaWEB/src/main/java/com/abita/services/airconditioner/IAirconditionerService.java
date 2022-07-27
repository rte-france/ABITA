/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.airconditioner;

import com.abita.dto.AirconditionerDTO;
import com.abita.services.airconditioner.exceptions.AirconditionerServiceException;

import java.util.List;

/**
 * Interface du service des assainissements
 */
public interface IAirconditionerService {

  /**
   * Permet de récupérer la liste de tous les assainissements
   *
   * @return une liste de tous les assainissements
   *
   * @throws AirconditionerServiceException une AirconditionerServiceException
   */
  List<AirconditionerDTO> find() throws AirconditionerServiceException;
}
