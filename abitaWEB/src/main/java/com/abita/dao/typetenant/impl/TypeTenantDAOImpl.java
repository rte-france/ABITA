/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.typetenant.impl;

import com.abita.dao.typetenant.ITypeTenantDAO;
import com.abita.dao.typetenant.entity.TypeTenantEntity;
import com.abita.dao.typetenant.exceptions.TypeTenantDAOException;
import com.dao.common.impl.AbstractGenericEntityDAO;

/**
 * Classe d'implémentation des types d'occupant
 *
 * @author
 */
public class TypeTenantDAOImpl extends AbstractGenericEntityDAO<TypeTenantEntity, TypeTenantDAOException> implements ITypeTenantDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = 6350422861809084851L;

}
