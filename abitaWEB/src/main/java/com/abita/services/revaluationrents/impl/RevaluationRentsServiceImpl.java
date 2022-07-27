/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.revaluationrents.impl;

import com.abita.dao.revaluationrents.entity.RevaluationRentsEntity;
import com.abita.dao.revaluationrents.exceptions.RevaluationRentsDAOException;
import com.abita.services.revaluationrents.IRevaluationRentsService;
import com.abita.dao.revaluationrents.IRevaluationRentsDAO;
import com.abita.dto.RevaluationRentsDTO;
import com.abita.services.revaluationrents.exceptions.RevaluationRentsServiceException;
import com.services.common.impl.AbstractService;

import java.util.Date;

/**
 * Classe d’implémentation des services de revalorisation des loyers
 */
public class RevaluationRentsServiceImpl extends
  AbstractService<RevaluationRentsEntity, RevaluationRentsDTO, RevaluationRentsDAOException, Long, RevaluationRentsServiceException, IRevaluationRentsDAO> implements
        IRevaluationRentsService {

  /** Le DAO des revalorisations des loyers */
  private IRevaluationRentsDAO revaluationRentsDAO;

  @Override
  protected IRevaluationRentsDAO getSpecificIDAO() {
    return revaluationRentsDAO;
  }

  /**
   * @param revaluationRentsDAO the revaluationRentsDAO to set
   */
  public void setRevaluationRentsDAO(IRevaluationRentsDAO revaluationRentsDAO) {
    this.revaluationRentsDAO = revaluationRentsDAO;
  }

  @Override
  public void update(RevaluationRentsDTO revaluationRentsDTO) throws RevaluationRentsServiceException {
    revaluationRentsDTO.setLastUpdated(new Date());
    super.update(revaluationRentsDTO);
  }

}
