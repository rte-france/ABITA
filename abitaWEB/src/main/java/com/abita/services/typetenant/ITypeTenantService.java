/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.typetenant;

import com.abita.dto.TypeTenantDTO;
import com.abita.services.typetenant.exceptions.TypeTenantServiceException;

import java.util.List;

/**
 * Interface du service des types d'occupant
 *
 * @author
 */
public interface ITypeTenantService {

  /**
   * Permet de récupérer la liste de tous les types d'occupant
   *
   * @return une liste de tous les types d'occupant
   *
   * @throws TypeTenantServiceException une TypeTenantServiceException
   */
  List<TypeTenantDTO> find() throws TypeTenantServiceException;

}
