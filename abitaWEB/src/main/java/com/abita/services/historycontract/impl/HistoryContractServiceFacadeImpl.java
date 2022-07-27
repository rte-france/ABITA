/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.historycontract.impl;

import com.abita.dao.historycontract.entity.HistoryContractEntity;
import com.abita.dao.historycontract.exceptions.HistoryContractDAOException;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.detailcron.IDetailCronService;
import com.abita.services.detailcron.exceptions.DetailCronServiceException;
import com.abita.services.historybenefits.IHistoryBenefitsService;
import com.abita.services.historycontract.IHistoryContractServiceFacade;
import com.abita.services.historyhousing.IHistoryHousingService;
import com.abita.services.historytenant.IHistoryTenantService;
import com.abita.services.historytenant.exceptions.HistoryTenantServiceException;
import com.abita.dao.historycontract.IHistoryContractDAO;
import com.abita.dto.ContractDTO;
import com.abita.dto.DetailCronDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.HistoryBenefitsDTO;
import com.abita.dto.HistoryContractDTO;
import com.abita.dto.HistoryHousingDTO;
import com.abita.dto.HistoryTenantDTO;
import com.abita.dto.RentTypologyDTO;
import com.abita.services.historybenefits.exceptions.HistoryBenefitsServiceException;
import com.abita.services.historycontract.exceptions.HistoryContractServiceException;
import com.abita.services.historyhousing.exceptions.HistoryHousingServiceException;
import com.services.common.impl.AbstractService;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe d’implémentation d’historisation des contrats occupant
 * @author
 *
 */
public class HistoryContractServiceFacadeImpl extends
  AbstractService<HistoryContractEntity, HistoryContractDTO, HistoryContractDAOException, Long, HistoryContractServiceException, IHistoryContractDAO> implements
        IHistoryContractServiceFacade {

  /** DAO pour l’historisation des contrats occupants */
  private IHistoryContractDAO historyContractDAO;

  /** Service des contrats occupants */
  private IContractServiceFacade contractServiceFacade;

  /** Service d’historisation des logements */
  private IHistoryHousingService historyHousingService;

  /** Service d’historisation des occupants */
  private IHistoryTenantService historyTenantService;

  /** Service d’historisation des avantages en nature */
  private IHistoryBenefitsService historyBenefitsService;

  /** Service d'administrations des batchs */
  private IDetailCronService detailCronService;

  @Override
  protected IHistoryContractDAO getSpecificIDAO() {
    return historyContractDAO;
  }

  @Override
  public HistoryContractDTO get(Long id, int month, int year) throws HistoryContractServiceException {
    HistoryContractEntity historyContract;
    try {
      historyContract = historyContractDAO.get(id, month, year);
    } catch (HistoryContractDAOException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    }
    if (historyContract == null) {
      return null;
    } else {
      return mapper.map(historyContract, HistoryContractDTO.class);
    }
  }

  @Override
  public void historizeAllContracts() throws HistoryContractServiceException {
    try {
      historyContractDAO.historizeAllContracts();
    } catch (HistoryContractDAOException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void deleteAllHistoryContractOfOneContract(Long id) throws HistoryContractServiceException {
    try {
      historyContractDAO.deleteAllHistoryContractOfOneContract(id);
    } catch (HistoryContractDAOException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void historizeNewContracts() throws HistoryContractServiceException {
    try {
      List<ContractDTO> contracts = contractServiceFacade.findContractsToHistorize();

      for (ContractDTO contract : contracts) {
        for (int i = 0; i < contract.getRetroactivitysMonths(); i++) {
          LocalDate regularizationDate = new LocalDate();
          regularizationDate = regularizationDate.minusMonths(i + 1);
          historizeOneRetroactivityMonthForNewContract(contract, false, regularizationDate);
        }
      }
    } catch (ContractServiceFacadeException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (HistoryHousingServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (HistoryTenantServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (HistoryBenefitsServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void historizeOneNewContractTemporarily(ContractDTO contractDTO, Integer retroactivitysMonths) throws HistoryContractServiceException {
    try {
      // On récupère la liste du detail des CRON
      DetailCronDTO detailCronDTO = detailCronService.getCronsOfCurrentMonth();
      LocalDateTime generationDate = new LocalDateTime();
      // Si les générations sont déjà passées, on considère qu'on est au mois suivant
      if (generationDate.isAfter(detailCronService.getLastCronOfTheMonth(detailCronDTO).toLocalDateTime())) {
        generationDate = generationDate.plusMonths(1);
      }
      for (int i = 0; i < retroactivitysMonths; i++) {
        LocalDate regularizationDate = generationDate.toLocalDate();
        regularizationDate = regularizationDate.minusMonths(i + 1);
        historizeOneRetroactivityMonthForNewContract(contractDTO, true, regularizationDate);
      }
    } catch (HistoryHousingServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (HistoryTenantServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (HistoryBenefitsServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (DetailCronServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void historizeCurrentMonthOneContractTemporarily(ContractDTO contractDTO) throws HistoryContractServiceException {
    try {
      // On récupère la liste du detail des CRON
      DetailCronDTO detailCronDTO = detailCronService.getCronsOfCurrentMonth();
      LocalDateTime generationDate = new LocalDateTime();
      // Si les générations sont déjà passées, on considère qu'on est au mois suivant
      if (generationDate.isAfter(detailCronService.getLastCronOfTheMonth(detailCronDTO).toLocalDateTime())) {
        generationDate = generationDate.plusMonths(1);
      }

      LocalDate regularizationDate = generationDate.toLocalDate();
      historizeOneRetroactivityMonthForAContract(contractDTO, true, regularizationDate);

    } catch (HistoryHousingServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (HistoryTenantServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (HistoryBenefitsServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (DetailCronServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    }
  }


  /**
   * @param contract
   * @param temp
   * @param regularizationDate
   * @throws HistoryHousingServiceException
   * @throws HistoryTenantServiceException
   * @throws HistoryBenefitsServiceException
   * @throws HistoryContractServiceException
   */
  private void historizeOneRetroactivityMonthForNewContract(ContractDTO contract, Boolean temp, LocalDate regularizationDate) throws HistoryHousingServiceException,
    HistoryTenantServiceException, HistoryBenefitsServiceException, HistoryContractServiceException {
    int month = regularizationDate.getMonthOfYear();
    int year = regularizationDate.getYear();

    HistoryHousingDTO historyHousing = historyHousingService.get(contract.getHousing().getId(), month, year);

    if (historyHousing == null) {
      historyHousing = historyHousingService.getLatest(contract.getHousing(), month, year, temp);
      historyHousing.setId(null);
      historyHousing.setMonth(month);
      historyHousing.setYear(year);
      historyHousing.setTemp(temp);

      Long historyHousingId = historyHousingService.create(historyHousing);
      historyHousing = historyHousingService.get(historyHousingId);
    }

    HistoryTenantDTO historyTenant = historyTenantService.get(contract.getTenant().getId(), month, year);

    if (historyTenant == null) {
      historyTenant = historyTenantService.getLatest(contract.getTenant(), month, year, temp);
      historyTenant.setId(null);
      historyTenant.setMonth(month);
      historyTenant.setYear(year);
      historyTenant.setTemp(temp);

      Long historyTenantId = historyTenantService.create(historyTenant);
      historyTenant = historyTenantService.get(historyTenantId);
    }

    List<HistoryBenefitsDTO> historyBenefitsList = historyBenefitsService.get(month, year);

    if (historyBenefitsList.isEmpty()) {
      LocalDate latestDate = regularizationDate.plusMonths(1);
      int monthLatest = latestDate.getMonthOfYear();
      int yearLatest = latestDate.getYear();

      historyBenefitsList.addAll(historyBenefitsService.get(monthLatest, yearLatest));

      for (HistoryBenefitsDTO historyBenefits : historyBenefitsList) {
        historyBenefits.setId(null);
        historyBenefits.setMonth(month);
        historyBenefits.setYear(year);
        historyTenant.setTemp(temp);

        historyBenefitsService.create(historyBenefits);
      }
    }

    HistoryContractDTO historyContractDTO = new HistoryContractDTO();
    historyContractDTO.setContractId(contract.getId());
    historyContractDTO.setMonth(month);
    historyContractDTO.setYear(year);
    historyContractDTO.setEndValidityDate(contract.getEndValidityDate());
    historyContractDTO.setMarketRentPrice(contract.getMarketRentPrice());
    historyContractDTO.setRentPriceLimit(contract.getRentPriceLimit());
    historyContractDTO.setGarageRent(contract.getGarageRent());
    historyContractDTO.setGardenRent(contract.getGardenRent());
    historyContractDTO.setExtraRent(contract.getExtraRent());
    historyContractDTO.setExpectedChargeCost(contract.getExpectedChargeCost());
    historyContractDTO.setRealEstateRentalValue(contract.getRealEstateRentalValue());
    historyContractDTO.setHouseholdSize(contract.getHouseholdSize());
    historyContractDTO.setTerminationSavingAmount(new BigDecimal(0));
    historyContractDTO.setAddedWithdrawnRent(new BigDecimal(0));
    historyContractDTO.setTemp(temp);
    RentTypologyDTO rentTypology = contract.getRentTypology();
    historyContractDTO.setRentTypologyTechnicalCode(rentTypology.getTechnicalCode());
    historyContractDTO.setRentTypologyHousingIndex(rentTypology.getHousingIndex());

    FieldOfActivityDTO fieldOfActivity = contract.getFieldOfActivity();
    historyContractDTO.setFieldOfActivityLabel(fieldOfActivity.getLabel());

    historyContractDTO.setHousing(historyHousing);
    historyContractDTO.setTenant(historyTenant);

    create(historyContractDTO);
  }

  /**
   * @param contract
   * @param temp
   * @param regularizationDate
   * @throws HistoryHousingServiceException
   * @throws HistoryTenantServiceException
   * @throws HistoryBenefitsServiceException
   * @throws HistoryContractServiceException
   */
  private void historizeOneRetroactivityMonthForAContract(ContractDTO contract, Boolean temp, LocalDate regularizationDate) throws HistoryHousingServiceException,
    HistoryTenantServiceException, HistoryBenefitsServiceException, HistoryContractServiceException {
    int month = regularizationDate.getMonthOfYear();
    int year = regularizationDate.getYear();

    try {
      historyContractDAO.deleteAllTemporaryContractHistorisations(contract.getId());
    } catch (HistoryContractDAOException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    }

    HistoryHousingDTO historyHousing = historyHousingService.get(contract.getHousing().getId(), month, year);

    if (historyHousing == null) {
      historyHousing = historyHousingService.getLatest(contract.getHousing(), month, year, temp);
      historyHousing.setId(null);
      historyHousing.setMonth(month);
      historyHousing.setYear(year);
      historyHousing.setTemp(temp);

      Long historyHousingId = historyHousingService.create(historyHousing);
      historyHousing = historyHousingService.get(historyHousingId);
    }

    HistoryTenantDTO historyTenant = historyTenantService.get(contract.getTenant().getId(), month, year);

    if (historyTenant == null) {
      historyTenant = historyTenantService.getLatest(contract.getTenant(), month, year, temp);
      historyTenant.setId(null);
      historyTenant.setMonth(month);
      historyTenant.setYear(year);
      historyTenant.setTemp(temp);

      Long historyTenantId = historyTenantService.create(historyTenant);
      historyTenant = historyTenantService.get(historyTenantId);
    }

    List<HistoryBenefitsDTO> historyBenefitsList = historyBenefitsService.get(month, year);

    if (historyBenefitsList.isEmpty()) {
      LocalDate latestDate = regularizationDate.plusMonths(1);
      int monthLatest = latestDate.getMonthOfYear();
      int yearLatest = latestDate.getYear();

      historyBenefitsList.addAll(historyBenefitsService.get(monthLatest, yearLatest));

      for (HistoryBenefitsDTO historyBenefits : historyBenefitsList) {
        historyBenefits.setId(null);
        historyBenefits.setMonth(month);
        historyBenefits.setYear(year);
        historyTenant.setTemp(temp);

        historyBenefitsService.create(historyBenefits);
      }
    }

    HistoryContractDTO historyContractDTO = new HistoryContractDTO();
    historyContractDTO.setContractId(contract.getId());
    historyContractDTO.setMonth(month);
    historyContractDTO.setYear(year);
    historyContractDTO.setEndValidityDate(contract.getEndValidityDate());
    historyContractDTO.setMarketRentPrice(contract.getMarketRentPrice());
    historyContractDTO.setRentPriceLimit(contract.getRentPriceLimit());
    historyContractDTO.setGarageRent(contract.getGarageRent());
    historyContractDTO.setGardenRent(contract.getGardenRent());
    historyContractDTO.setExtraRent(contract.getExtraRent());
    historyContractDTO.setExpectedChargeCost(contract.getExpectedChargeCost());
    historyContractDTO.setRealEstateRentalValue(contract.getRealEstateRentalValue());
    historyContractDTO.setHouseholdSize(contract.getHouseholdSize());
    historyContractDTO.setTerminationSavingAmount(contract.getPlainTerminationSavingAmount());
    historyContractDTO.setAddedWithdrawnRent(contract.getPlainAddedWithdrawnRent());
    historyContractDTO.setTemp(temp);
    RentTypologyDTO rentTypology = contract.getRentTypology();
    historyContractDTO.setRentTypologyTechnicalCode(rentTypology.getTechnicalCode());
    historyContractDTO.setRentTypologyHousingIndex(rentTypology.getHousingIndex());

    FieldOfActivityDTO fieldOfActivity = contract.getFieldOfActivity();
    historyContractDTO.setFieldOfActivityLabel(fieldOfActivity.getLabel());

    historyContractDTO.setHousing(historyHousing);
    historyContractDTO.setTenant(historyTenant);

    create(historyContractDTO);
  }

  @Override
  public void deleteOldContracts(int month, int year) throws HistoryContractServiceException {
    try {
      historyContractDAO.deleteOldContracts(month, year);
    } catch (HistoryContractDAOException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void replaceAllTemporaryContractHistorisations(ContractDTO contract) throws HistoryContractServiceException {
    try {
      historyContractDAO.deleteAllTemporaryContractHistorisations(contract.getId());

      // On récupère la liste du detail des CRON
      DetailCronDTO detailCronDTO = detailCronService.getCronsOfCurrentMonth();
      LocalDateTime generationDate = new LocalDateTime();

      // Si les générations sont déjà passées, on considère qu'on est au mois suivant
      if (generationDate.isAfter(detailCronService.getLastCronOfTheMonth(detailCronDTO).toLocalDateTime())) {
        generationDate = generationDate.plusMonths(1);
      }
      for (int i = 0; i < contract.getRetroactivitysMonths(); i++) {
        LocalDate regularizationDate = generationDate.toLocalDate();
        regularizationDate = regularizationDate.minusMonths(i + 1);
        historizeOneRetroactivityMonthForNewContract(contract, true, regularizationDate);
      }
    } catch (HistoryContractDAOException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (HistoryHousingServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (HistoryTenantServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (HistoryBenefitsServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    } catch (DetailCronServiceException e) {
      throw new HistoryContractServiceException(e.getMessage(), e);
    }
  }

  /**
   * @return the historyContractDAO
   */
  public IHistoryContractDAO getHistoryContractDAO() {
    return historyContractDAO;
  }

  /**
   * @param historyContractDAO the historyContractDAO to set
   */
  public void setHistoryContractDAO(IHistoryContractDAO historyContractDAO) {
    this.historyContractDAO = historyContractDAO;
  }

  /**
   * @return the contractServiceFacade
   */
  public IContractServiceFacade getContractServiceFacade() {
    return contractServiceFacade;
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

  /**
   * @return the historyHousingService
   */
  public IHistoryHousingService getHistoryHousingService() {
    return historyHousingService;
  }

  /**
   * @param historyHousingService the historyHousingService to set
   */
  public void setHistoryHousingService(IHistoryHousingService historyHousingService) {
    this.historyHousingService = historyHousingService;
  }

  /**
   * @return the historyTenantService
   */
  public IHistoryTenantService getHistoryTenantService() {
    return historyTenantService;
  }

  /**
   * @param historyTenantService the historyTenantService to set
   */
  public void setHistoryTenantService(IHistoryTenantService historyTenantService) {
    this.historyTenantService = historyTenantService;
  }

  /**
   * @return the historyBenefitsService
   */
  public IHistoryBenefitsService getHistoryBenefitsService() {
    return historyBenefitsService;
  }

  /**
   * @param historyBenefitsService the historyBenefitsService to set
   */
  public void setHistoryBenefitsService(IHistoryBenefitsService historyBenefitsService) {
    this.historyBenefitsService = historyBenefitsService;
  }

  /**
   * @param detailCronService the detailCronService to set
   */
  public void setDetailCronService(IDetailCronService detailCronService) {
    this.detailCronService = detailCronService;
  }

}
