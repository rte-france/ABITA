/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.costcenter.impl;

import com.abita.dao.costcenter.entity.CostCenterEntity;
import com.abita.dao.costcenter.exceptions.CostCenterDAOException;
import com.abita.services.costcenter.ICostCenterService;
import com.abita.dao.costcenter.ICostCenterDAO;
import com.abita.dto.CostCenterDTO;
import com.abita.services.costcenter.exceptions.CostCenterServiceException;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'implémentation des centres coûts
 *
 * @author
 */
public class CostCenterServiceImpl extends AbstractService<CostCenterEntity, CostCenterDTO, CostCenterDAOException, Long, CostCenterServiceException, ICostCenterDAO> implements
        ICostCenterService {

  /**
  * Le DAO des centres coûts
  */
  private ICostCenterDAO costcenterDAO;

  @Override
  public List<CostCenterDTO> findAllCostCenter() throws CostCenterServiceException {
    List<CostCenterDTO> lstCostCenterDTO = new ArrayList<CostCenterDTO>();
    try {
      List<CostCenterEntity> lstCostCenterEntity = costcenterDAO.findAllCostCenter();
      for (CostCenterEntity costcenterEntity : SafetyUtils.emptyIfNull(lstCostCenterEntity)) {
        lstCostCenterDTO.add(mapper.map(costcenterEntity, CostCenterDTO.class));
      }
    } catch (CostCenterDAOException daoEx) {
      throw new CostCenterServiceException(daoEx.getMessage(), daoEx);
    }
    return lstCostCenterDTO;
  }

  @Override
  public void saveListing(List<CostCenterDTO> lstCostCenter) throws CostCenterServiceException {
    List<CostCenterDTO> lstCostCenterOriginal = find();

    // Suppression
    for (CostCenterDTO costcenter : lstCostCenterOriginal) {
      if (!lstCostCenter.contains(costcenter)) {
        delete(costcenter.getId());
      }
    }

    // Ajout et modification
    for (CostCenterDTO costcenter : lstCostCenter) {
      if (lstCostCenterOriginal.contains(costcenter)) {
        update(costcenter);
      } else {
        create(costcenter);
      }
    }
  }

  @Override
  public Long create(CostCenterDTO costcenterDTO) throws CostCenterServiceException {
    return (Long) super.create(costcenterDTO);
  }

  @Override
  protected ICostCenterDAO getSpecificIDAO() {
    return costcenterDAO;
  }

  /**
   * @param costcenterDAO the costcenterDAO to set
   */
  public void setCostcenterDAO(ICostCenterDAO costcenterDAO) {
    this.costcenterDAO = costcenterDAO;
  }

}
