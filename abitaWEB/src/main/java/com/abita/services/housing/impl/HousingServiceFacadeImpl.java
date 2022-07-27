/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.housing.impl;

import com.abita.dao.housing.entity.HousingEntity;
import com.abita.dao.housing.exceptions.HousingDAOException;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.historyhousing.IHistoryHousingService;
import com.abita.services.housing.IHousingServiceFacade;
import com.abita.services.housing.exceptions.HousingServiceFacadeException;
import com.abita.dao.housing.IHousingDAO;
import com.abita.dto.HousingDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.services.historyhousing.exceptions.HistoryHousingServiceException;
import com.services.common.exception.NotFoundException;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;
import org.dozer.MappingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'implémentation des services des logements
 *
 * @author
 */
public class HousingServiceFacadeImpl extends AbstractService<HousingEntity, HousingDTO, HousingDAOException, Long, HousingServiceFacadeException, IHousingDAO> implements
        IHousingServiceFacade {

  /**
  * Le DAO des logements
  */
  private IHousingDAO housingDAO;

  /**
   * Service d'historisation des logements
   */
  private IHistoryHousingService historyHousingService;

  /**
   * Service des contrats occupant
   */
  private IContractServiceFacade contractServiceFacade;

  @Override
  protected IHousingDAO getSpecificIDAO() {
    return housingDAO;
  }

  @Override
  public Long create(HousingDTO housingDTO) throws HousingServiceFacadeException {
    return (Long) super.create(housingDTO);
  }

  @Override
  public HousingDTO get(Long id) throws HousingServiceFacadeException {
    try {
      HousingEntity housingEntity = housingDAO.get(id);
      if (housingEntity == null) {
        throw createException(new NotFoundException("Objet non existant pour l'identifiant : " + id));
      }
      HousingDTO housing = mapHousingEntityToHousingDTO(housingEntity);
      housing.setFieldOfActivity(contractServiceFacade.findFieldOfActivityForOneHousing(housing.getId()));
      return housing;
    } catch (HousingDAOException e) {
      throw new HousingServiceFacadeException(e);
    } catch (ContractServiceFacadeException e) {
      throw new HousingServiceFacadeException(e);
    }

  }

  @Override
  public void update(HousingDTO housing) throws HousingServiceFacadeException {

    if (housing.getGardenAvailable() != null && !housing.getGardenAvailable()) {
      housing.setSquareMeter(null);
    }

    if (housing.getGas() != null && !housing.getGas()) {
      housing.setGasAno(null);
      housing.setGasDate(null);
    }

    if (housing.getDpe() != null && !housing.getDpe()) {
      housing.setDpeDate(null);
    }

    if (housing.getBathroom() != null && !housing.getBathroom()) {
      housing.setBathroomDate(null);
    }

    if (housing.getEquippedKitchen() != null && !housing.getEquippedKitchen()) {
      housing.setKitchenDate(null);
    }

    if (housing.getAsbestos() != null && !housing.getAsbestos()) {
      housing.setAsbestosAno(null);
      housing.setAsbestosDate(null);
    }

    if (housing.getErnmt() != null && !housing.getErnmt()) {
      housing.setErnmtDate(null);
    }

    if (housing.getCarrez() != null && !housing.getCarrez()) {
      housing.setCarrezDate(null);
    }

    if (housing.getTermite() != null && !housing.getTermite()) {
      housing.setTermiteAno(null);
      housing.setTermiteDate(null);
    }

    if (housing.getTermite() != null && !housing.getLead()) {
      housing.setLeadAno(null);
      housing.setLeadDate(null);
    }

    if (housing.getElect() != null && !housing.getElect()) {
      housing.setElectAno(null);
      housing.setElectDate(null);
    }

    super.update(housing);
  }

  @Override
  public List<HousingDTO> findByCriteria(HousingCriteriaDTO housingCriteria, TenantCriteriaDTO tenantCriteria) throws HousingServiceFacadeException {

    List<HousingDTO> lstDTO = new ArrayList<HousingDTO>();
    try {
      List<HousingEntity> lstEntity = housingDAO.findByCriteria(housingCriteria, tenantCriteria);

      for (HousingEntity housing : SafetyUtils.emptyIfNull(lstEntity)) {
        lstDTO.add(mapHousingEntityToHousingDTO(housing));
      }
    } catch (HousingDAOException e) {
      throw new HousingServiceFacadeException(e);
    }

    return lstDTO;
  }

  private HousingDTO mapHousingEntityToHousingDTO(HousingEntity housing) throws HousingServiceFacadeException {
    try {
      return mapper.map(housing, HousingDTO.class);
    } catch (MappingException e) {
      throw new HousingServiceFacadeException(e);
    }
  }

  @Override
  public List<HousingDTO> findHousingsByAgency(Long idAgency) throws HousingServiceFacadeException {
    List<HousingDTO> housings = new ArrayList<HousingDTO>();
    try {
      List<HousingEntity> housingEntities = housingDAO.findHousingsByAgency(idAgency);

      for (HousingEntity housing : SafetyUtils.emptyIfNull(housingEntities)) {
        housings.add(mapHousingEntityToHousingDTO(housing));
      }
    } catch (HousingDAOException e) {
      throw new HousingServiceFacadeException(e);
    }
    return housings;
  }


  @Override
  public boolean isRemovable(long idHousing) throws HousingServiceFacadeException {
    try {
      return getSpecificIDAO().isRemovable(idHousing);
    } catch (HousingDAOException e) {
      throw new HousingServiceFacadeException(e);
    }
  }

  @Override
  public void delete(Long id) throws HousingServiceFacadeException {
    try {
      historyHousingService.deleteAllHistoryHousingOfOneHousing(id);
      getSpecificIDAO().delete(id);
    } catch (HousingDAOException e) {
      throw new HousingServiceFacadeException(e);
    } catch (HistoryHousingServiceException e) {
      throw new HousingServiceFacadeException(e);
    }
  }

  @Override
  public String generateReference() throws HousingServiceFacadeException {
    try {
      return getSpecificIDAO().generateReference();
    } catch (HousingDAOException e) {
      throw new HousingServiceFacadeException(e);
    }
  }

  /**
   * @param housingDAO the housingDAO to set
   */
  public void setHousingDAO(IHousingDAO housingDAO) {
    this.housingDAO = housingDAO;
  }

  public void setHistoryHousingService(IHistoryHousingService historyHousingService) {
    this.historyHousingService = historyHousingService;
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }
}
