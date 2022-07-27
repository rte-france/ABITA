/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.typetenant.impl;

import com.abita.dao.typetenant.entity.TypeTenantEntity;
import com.abita.dao.typetenant.exceptions.TypeTenantDAOException;
import com.abita.services.typetenant.ITypeTenantService;
import com.abita.services.typetenant.exceptions.TypeTenantServiceException;
import com.abita.dao.typetenant.ITypeTenantDAO;
import com.abita.dto.TypeTenantDTO;
import com.services.common.impl.AbstractService;

/**
 * Classe d'implémentation des types d'occupant
 *
 * @author
 */
public class TypeTenantServiceImpl extends AbstractService<TypeTenantEntity, TypeTenantDTO, TypeTenantDAOException, Long, TypeTenantServiceException, ITypeTenantDAO> implements
        ITypeTenantService {

  /**
  * Le DAO des types d'occupant
  */
  private ITypeTenantDAO typeTenantDAO;

  @Override
  protected ITypeTenantDAO getSpecificIDAO() {
    return typeTenantDAO;
  }

  /**
   * @param typeTenantDAO the typeTenantDAO to set
   */
  public void setTypeTenantDAO(ITypeTenantDAO typeTenantDAO) {
    this.typeTenantDAO = typeTenantDAO;
  }

}
