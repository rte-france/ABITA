/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.historytenant.impl;

import com.abita.dao.historytenant.entity.HistoryTenantEntity;
import com.abita.dao.historytenant.exceptions.HistoryTenantDAOException;
import com.abita.services.historytenant.IHistoryTenantService;
import com.abita.services.historytenant.exceptions.HistoryTenantServiceException;
import com.abita.dao.historytenant.IHistoryTenantDAO;
import com.abita.dto.HistoryTenantDTO;
import com.abita.dto.TenantDTO;
import com.services.common.impl.AbstractService;

import java.util.List;

/**
 *  Classe d'implémentation d'historisation des occupants
 * @author
 *
 */
public class HistoryTenantServiceImpl extends
  AbstractService<HistoryTenantEntity, HistoryTenantDTO, HistoryTenantDAOException, Long, HistoryTenantServiceException, IHistoryTenantDAO> implements IHistoryTenantService {

  /** DAO pour l’historisation des occupants */
  private IHistoryTenantDAO historyTenantDAO;

  @Override
  public Long create(HistoryTenantDTO historyTenantDTO) throws HistoryTenantServiceException {
    return (Long) super.create(historyTenantDTO);
  }

  @Override
  public HistoryTenantDTO get(Long id, int month, int year) throws HistoryTenantServiceException {
    HistoryTenantEntity historyTenant;
    try {
      historyTenant = historyTenantDAO.get(id, month, year);
    } catch (HistoryTenantDAOException e) {
      throw new HistoryTenantServiceException(e.getMessage(), e);
    }
    if (historyTenant == null) {
      return null;
    } else {
      return mapper.map(historyTenant, HistoryTenantDTO.class);
    }
  }

  @Override
  public HistoryTenantDTO getLatest(TenantDTO tenant, int month, int year, Boolean temp) throws HistoryTenantServiceException {
    HistoryTenantEntity historyTenant;
    try {
      historyTenant = historyTenantDAO.getLatest(tenant.getId(), month, year);
      if (historyTenant == null && !temp) {
        return null;
      }
    } catch (HistoryTenantDAOException e) {
      throw new HistoryTenantServiceException(e.getMessage(), e);
    }
    if (historyTenant == null) {
      HistoryTenantDTO historyTenantDTO = new HistoryTenantDTO();
      historyTenantDTO.setTenantId(tenant.getId());
      historyTenantDTO.setReference(tenant.getReference());
      if (tenant.getManagerial() != null) {
        historyTenantDTO.setManagerial(tenant.getManagerial());
      }
      if (tenant.getActualSalary() != null) {
        historyTenantDTO.setActualSalary(tenant.getActualSalary());
      }
      if (tenant.getReferenceGrossSalary() != null) {
        historyTenantDTO.setReferenceGrossSalary(tenant.getReferenceGrossSalary());
      }
      historyTenantDTO.setTypeTenantHeaderLabel(tenant.getTypeTenant().getNtHeaderLabel());
      return historyTenantDTO;
    } else {
      return mapper.map(historyTenant, HistoryTenantDTO.class);
    }
  }

  @Override
  public void historizeAllTenants() throws HistoryTenantServiceException {
    try {
      historyTenantDAO.historizeAllTenants();
    } catch (HistoryTenantDAOException e) {
      throw new HistoryTenantServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void updateTemporaries(TenantDTO tenantDTO) throws HistoryTenantServiceException {
    try {
      List<HistoryTenantEntity> historyTenantList = historyTenantDAO.getTemporaries(tenantDTO.getId());

      for (HistoryTenantEntity historyTenantEntity : historyTenantList) {
        historyTenantEntity.setManagerial(tenantDTO.getManagerial());
        historyTenantEntity.setActualSalary(tenantDTO.getActualSalary());
        historyTenantEntity.setReferenceGrossSalary(tenantDTO.getReferenceGrossSalary());
        historyTenantDAO.update(historyTenantEntity);
      }

    } catch (HistoryTenantDAOException e) {
      throw new HistoryTenantServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void deleteOldTenants(int month, int year) throws HistoryTenantServiceException {
    try {
      historyTenantDAO.deleteOldTenants(month, year);
    } catch (HistoryTenantDAOException e) {
      throw new HistoryTenantServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void deleteAllHistoryTenantOfOneTenant(Long id) throws HistoryTenantServiceException {
    try {
      historyTenantDAO.deleteAllHistoryTenantOfOneTenant(id);
    } catch (HistoryTenantDAOException e) {
      throw new HistoryTenantServiceException(e.getMessage(), e);
    }
  }

  @Override
  protected IHistoryTenantDAO getSpecificIDAO() {
    return historyTenantDAO;
  }

  /**
   * @param historyTenantDAO the historyTenantDAO to set
   */
  public void setHistoryTenantDAO(IHistoryTenantDAO historyTenantDAO) {
    this.historyTenantDAO = historyTenantDAO;
  }

}
