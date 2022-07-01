package com.abita.services.tenant.impl;

import com.abita.dao.tenant.entity.TenantEntity;
import com.abita.dao.tenant.exceptions.TenantDAOException;
import com.abita.services.historytenant.IHistoryTenantService;
import com.abita.services.tenant.ITenantServiceFacade;
import com.abita.dao.tenant.ITenantDAO;
import com.abita.dto.TenantDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.services.historytenant.exceptions.HistoryTenantServiceException;
import com.abita.services.tenant.exceptions.TenantServiceFacadeException;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;
import org.dozer.MappingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'implémentation des services des occupants
 *
 * @author
 */
public class TenantServiceFacadeImpl extends AbstractService<TenantEntity, TenantDTO, TenantDAOException, Long, TenantServiceFacadeException, ITenantDAO> implements
        ITenantServiceFacade {

  /**
  * Le DAO des occupants
  */
  private ITenantDAO tenantDAO;

  /**
   * Le service d'historisation des occupants
   */
  private IHistoryTenantService historyTenantService;

  @Override
  protected ITenantDAO getSpecificIDAO() {
    return tenantDAO;
  }

  @Override
  public Long create(TenantDTO tenantDTO) throws TenantServiceFacadeException {
    return (Long) super.create(tenantDTO);
  }

  @Override
  public List<TenantDTO> findByCriteria(TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria) throws TenantServiceFacadeException {

    List<TenantDTO> lstDTO = new ArrayList<TenantDTO>();
    try {
      List<TenantEntity> lstEntity = tenantDAO.findByCriteria(tenantCriteria, housingCriteria);

      for (TenantEntity tenant : SafetyUtils.emptyIfNull(lstEntity)) {
        lstDTO.add(mapper.map(tenant, TenantDTO.class));
      }
    } catch (TenantDAOException e) {
      throw new TenantServiceFacadeException(e);
    } catch (MappingException e) {
      throw new TenantServiceFacadeException(e);
    }

    return lstDTO;
  }

  @Override
  public boolean isRemovable(long idTenant) throws TenantServiceFacadeException {
    try {
      return getSpecificIDAO().isRemovable(idTenant);
    } catch (TenantDAOException e) {
      throw new TenantServiceFacadeException(e);
    }
  }

  @Override
  public List<String> findNNI() throws TenantServiceFacadeException {
    try {
      return tenantDAO.findNNI();
    } catch (TenantDAOException e) {
      throw new TenantServiceFacadeException(e);
    }
  }

  @Override
  public TenantDTO findTenantByNNI(String nni) throws TenantServiceFacadeException {
    try {
      TenantEntity tenantEntity = tenantDAO.findTenantByNNI(nni);
      if (null != tenantEntity) {
        return mapper.map(tenantEntity, TenantDTO.class);
      }
      return null;
    } catch (TenantDAOException e) {
      throw new TenantServiceFacadeException(e);
    }
  }

  @Override
  public void updateManagerialLastYear() throws TenantServiceFacadeException {
    try {
      tenantDAO.updateManagerialLastYear();
    } catch (TenantDAOException e) {
      logger.error("Erreur lors de la mise à jour des cadres à octobre N-1", e);
      throw new TenantServiceFacadeException(e);
    }
  }

  @Override
  public void updateHouseholdSizeLastYear() throws TenantServiceFacadeException {
    try {
      tenantDAO.updateHouseholdSizeLastYear();
    } catch (TenantDAOException e) {
      logger.error("Erreur lors de la mise à jour du nombre de personne à octobre N-1", e);
      throw new TenantServiceFacadeException(e);
    }
  }

  @Override
  public void delete(Long id) throws TenantServiceFacadeException {
    try {
      historyTenantService.deleteAllHistoryTenantOfOneTenant(id);
      getSpecificIDAO().delete(id);
    } catch (HistoryTenantServiceException e) {
      logger.error("Erreur lors de la suppression des historisations du logement", e);
      throw new TenantServiceFacadeException(e);
    } catch (TenantDAOException e) {
      logger.error("Erreur lors de la suppression du logement", e);
      throw new TenantServiceFacadeException(e);
    }

  }

  /**
   * @param tenantDAO the tenantDAO to set
   */
  public void setTenantDAO(ITenantDAO tenantDAO) {
    this.tenantDAO = tenantDAO;
  }

  /**
   * @param historyTenantService the historyTenantService to set
   */
  public void setHistoryTenantService(IHistoryTenantService historyTenantService) {
    this.historyTenantService = historyTenantService;
  }

}
