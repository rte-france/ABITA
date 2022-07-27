/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.field.impl;

import com.abita.dao.field.entity.FieldEntity;
import com.abita.dao.field.exceptions.FieldDAOException;
import com.abita.services.field.IFieldService;
import com.abita.services.field.exceptions.FieldServiceException;
import com.abita.dao.field.IFieldDAO;
import com.abita.dto.FieldDTO;
import com.services.common.impl.AbstractService;

/**
 * Classe d'implémentation des domaines d'activité
 * @author
 *
 */
public class FieldServiceImpl extends AbstractService<FieldEntity, FieldDTO, FieldDAOException, Long, FieldServiceException, IFieldDAO> implements IFieldService {

  /**
  * Le DAO des domaines d'activité
  */
  private IFieldDAO fieldDAO;

  @Override
  public Long create(FieldDTO fieldDTO) throws FieldServiceException {
    return (Long) super.create(fieldDTO);
  }

  @Override
  protected IFieldDAO getSpecificIDAO() {
    return fieldDAO;
  }

  /**
   * @param fieldDAO the fieldDAO to set
   */
  public void setFieldDAO(IFieldDAO fieldDAO) {
    this.fieldDAO = fieldDAO;
  }

}
