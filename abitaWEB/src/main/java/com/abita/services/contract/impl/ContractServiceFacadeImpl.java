package com.abita.services.contract.impl;

import com.abita.dao.contract.entity.ContractEntity;
import com.abita.dao.contract.exceptions.ContractDAOException;
import com.abita.dto.ContractDTO;
import com.abita.dto.DetailCronDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.RevaluationRentsDTO;
import com.abita.services.contract.IContractCalculationsServiceFacade;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.detailcron.IDetailCronService;
import com.abita.services.historyamount.IHistoryAmountService;
import com.abita.services.historycontract.IHistoryContractServiceFacade;
import com.abita.services.ncntaccountingdocnumber.INcNtAccountingDocNumberService;
import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.contract.ContractUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dao.contract.IContractDAO;
import com.abita.dto.*;
import com.abita.dto.unpersist.ContractCriteriaDTO;
import com.abita.dto.unpersist.ContractInputDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.services.contract.exceptions.ContractCalculationsServiceFacadeException;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.detailcron.exceptions.DetailCronServiceException;
import com.abita.services.historyamount.exceptions.HistoryAmountServiceException;
import com.abita.services.historycontract.exceptions.HistoryContractServiceException;
import com.abita.services.ncntaccountingdocnumber.exceptions.NcNtAccountingDocNumberServiceException;
import com.abita.web.shared.ConstantsWEB;
import com.dao.common.exception.GenericDAOException;
import com.services.common.exception.NotFoundException;
import com.services.common.util.SafetyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
*
 * Classe d'implémentation des services des contrats occupant
 *
 * Ce service ne peut pas hériter de <code>AbstractService</code> car il gère des objets particulier <code>ContractDTO</code> immutable
 * La création, recherche et modification de ces objets sont spécifiquement traitéd dans les méthodes de ce service
 *
 * @author
 */
public class ContractServiceFacadeImpl implements IContractServiceFacade {

  /** index de récupération de l'exception de service dans les génériques de la classe de service */
  public static final int SERVICE_EXCEPTION_GENERICS_INDEX = 4;

  /** Valeur maximale du pourcentage utilisé dans les calculs */
  private static final BigDecimal TOTAL_POURCENTAGE = new BigDecimal(100);

  /** Valeur maximale du ratio utilisé dans les calculs */
  private static final BigDecimal TOTAL_RATIO = new BigDecimal(1);

  /** Nombre de chiffres après la virgule */
  public static final int SCALE_PRICE = 2;

  /** Mapper dozzer */
  private Mapper mapper;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ContractServiceFacadeImpl.class);

  /** Message lors d’une erreur de mapping */
  private static final String MAPPING_ERROR_CODE = "Erreur survenue lors du mapping";

  /** Message lors d’une erreur de récupération */
  private static final String GET_ERROR_CODE = "Erreur survenue lors de la récuperation";

  /** Message d’erruer lors de la récupération des contrats occupants actifs */
  private static final String GET_ACTIVES_TENANT_CONTRACTS_ERROR_CODE = "Erreur survenue lors de la recuperation des contrats occupants actifs";

  /** Service de calcul des contrats occupant */
  private IContractCalculationsServiceFacade contractCalculationsServiceFacade;

  /** Service d'historisation des contrats occupants */
  private IHistoryContractServiceFacade historyContractServiceFacade;

  /** Service d'administrations des batchs */
  private IDetailCronService detailCronService;

  /** Service des numéros comptables des contrats occupants */
  private INcNtAccountingDocNumberService ncNtAccountingDocNumberService;

  /** Service d'historisation des montants */
  private IHistoryAmountService historyAmountService;

  /**
  * Le DAO des contrats tiers
  */
  private IContractDAO contractDAO;

  @Override
  public Long create(ContractInputDTO contractInputDTO) throws ContractServiceFacadeException {
    // Utilisation du builder avec les parametres obligatoires pour ContractBuilder puis les valeurs optionelles
    NumberFormat nf = new DecimalFormat(ConstantsWEB.CONTRACT_REFERENCE_TENANT_RANK_PATTERN);
    String contractReference = ConstantsWEB.PREFIX_CONTRACT_REFERENCE + contractInputDTO.getHousing().getReference().toString() + ConstantsWEB.CONTRACT_REFERENCE_GROUP
      + nf.format(countContractsInHousing(contractInputDTO.getHousing().getId()) + 1).toString();
    ContractDTO contractDTO = new ContractDTO.ContractBuilder(contractInputDTO.getId(), contractReference, contractInputDTO.getStartValidityDate(),
      contractInputDTO.getPlainAddedWithdrawnRent(), contractInputDTO.getTerminationSavings(), contractInputDTO.getTenant(), contractInputDTO.getHousing(),
      contractInputDTO.getFieldOfActivity(), contractInputDTO.getCostCenter(), contractInputDTO.getRentTypology(), contractInputDTO.getPaymentMethod(),
      contractInputDTO.getTermination(), contractInputDTO.getHouseholdSize(), 0).lastWithdrawnDate(contractInputDTO.getStartValidityDate())
      .lastSavingDate(contractInputDTO.getStartValidityDate()).signature(contractInputDTO.getSignature()).endValidityDate(contractInputDTO.getEndValidityDate())
      .marketRentPrice(contractInputDTO.getMarketRentPrice()).rentPriceLimit(contractInputDTO.getRentPriceLimit()).garageRent(contractInputDTO.getGarageRent())
      .gardenRent(contractInputDTO.getGardenRent()).extraRent(contractInputDTO.getExtraRent()).expectedChargeCost(contractInputDTO.getExpectedChargeCost())
      .waterInvoicing(contractInputDTO.getWaterInvoicing()).garbageInvoicing(contractInputDTO.getGarbageInvoicing())
      .insuranceReimbursement(contractInputDTO.getInsuranceReimbursement()).housingTaxReimbursement(contractInputDTO.getHousingTaxReimbursement())
      .garbageReimbursement(contractInputDTO.getGarbageReimbursement()).annualClearanceCharges(contractInputDTO.getAnnualClearanceCharges())
      .otherInvoicingLabel(contractInputDTO.getOtherInvoicingLabel()).otherInvoicingAmount(contractInputDTO.getOtherInvoicingAmount())
      .realEstateRentalValue(contractInputDTO.getRealEstateRentalValue()).terminationSavingAmount(new BigDecimal(ConstantsWEB.CONTRACT_TERMINATION_SAVING_AMOUNT_INIT))
      .plainTerminationSavingAmount(new BigDecimal(ConstantsWEB.CONTRACT_TERMINATION_SAVING_AMOUNT_INIT))
      .terminationSavingsPayment(contractInputDTO.getTerminationSavingsPayment()).terminationSavingsPaymentDate(contractInputDTO.getTerminationSavingsPaymentDate())
      .fixedNNCoef(contractInputDTO.getFixedNNCoef()).fixedAgency(contractInputDTO.getFixedAgency()).fixedBenefit(contractInputDTO.getFixedBenefit())
      .fixedLopRent(contractInputDTO.getFixedLopRent()).fixedRevisedSurfaceAreaRent(contractInputDTO.getFixedRevisedSurfaceAreaRent())
      .fixedRevisedSurfaceArea(contractInputDTO.getFixedRevisedSurfaceArea()).closedContract(contractInputDTO.getClosedContract())
      .insuranceCertificateEndDate(contractInputDTO.getInsuranceCertificateEndDate()).build();

    Long idContract = create(contractDTO);

    checkRegularisationInCreation(idContract);

    return idContract;
  }

  /**
   *  Permet de créer un contrat occupant
   * @param contractDTO un contrat occupant qui vient de la vue création
   * @return un identifiant du nouveau contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  private Long create(ContractDTO contractDTO) throws ContractServiceFacadeException {
    try {
      return (Long) contractDAO.create(mapper.map(contractDTO, ContractEntity.class));
    } catch (GenericDAOException aenericDaoException) {
      LOGGER.error("Erreur survenue lors de la creation", aenericDaoException);
      throw new ContractServiceFacadeException(aenericDaoException);
    } catch (MappingException mappingException) {
      LOGGER.error(MAPPING_ERROR_CODE, mappingException);
      throw new ContractServiceFacadeException(mappingException);
    }
  }

  @Override
  public ContractDTO get(Long id) throws ContractServiceFacadeException {
    try {
      // Le mapping vers DTO est fait a la main, a cause de la nature immutable de ContractDTO
      ContractEntity entity = contractDAO.get(id);
      if (entity == null) {
        throw new ContractServiceFacadeException(new NotFoundException("Objet non existant pour l'identifiant : " + id));
      }
      return ContractUtils.mapToContractDTO(entity, mapper);
    } catch (final GenericDAOException genericDaoException) {
      LOGGER.error(GET_ERROR_CODE, genericDaoException);
      throw new ContractServiceFacadeException(genericDaoException);
    } catch (MappingException mappingException) {
      LOGGER.error(MAPPING_ERROR_CODE, mappingException);
      throw new ContractServiceFacadeException(mappingException);
    }
  }

  @Override
  public ContractDTO update(ContractInputDTO contractInputDTO, ContractDTO actualContractDTO) throws ContractServiceFacadeException {
    return update(new ContractDTO.ContractBuilder(actualContractDTO.getId(), actualContractDTO.getContractReference(), contractInputDTO.getStartValidityDate(),
      contractInputDTO.getPlainAddedWithdrawnRent(), contractInputDTO.getTerminationSavings(), contractInputDTO.getTenant(), contractInputDTO.getHousing(),
      contractInputDTO.getFieldOfActivity(), contractInputDTO.getCostCenter(), contractInputDTO.getRentTypology(), contractInputDTO.getPaymentMethod(),
      contractInputDTO.getTermination(), contractInputDTO.getHouseholdSize(), contractInputDTO.getRetroactivitysMonths())
      .lastWithdrawnDate(contractInputDTO.getLastWithdrawnDate()).lastSavingDate(contractInputDTO.getLastSavingDate()).signature(contractInputDTO.getSignature())
      .endValidityDate(contractInputDTO.getEndValidityDate()).marketRentPrice(contractInputDTO.getMarketRentPrice()).rentPriceLimit(contractInputDTO.getRentPriceLimit())
      .garageRent(contractInputDTO.getGarageRent()).gardenRent(contractInputDTO.getGardenRent()).extraRent(contractInputDTO.getExtraRent())
      .expectedChargeCost(contractInputDTO.getExpectedChargeCost()).waterInvoicing(contractInputDTO.getWaterInvoicing()).garbageInvoicing(contractInputDTO.getGarbageInvoicing())
      .insuranceReimbursement(contractInputDTO.getInsuranceReimbursement()).housingTaxReimbursement(contractInputDTO.getHousingTaxReimbursement())
      .garbageReimbursement(contractInputDTO.getGarbageReimbursement()).annualClearanceCharges(contractInputDTO.getAnnualClearanceCharges())
      .otherInvoicingLabel(contractInputDTO.getOtherInvoicingLabel()).otherInvoicingAmount(contractInputDTO.getOtherInvoicingAmount())
      .realEstateRentalValue(contractInputDTO.getRealEstateRentalValue()).nNCoef(contractInputDTO.getnNCoef()).netAgentRent(contractInputDTO.getNetAgentRent())
      .shortTermContractDiscount(contractInputDTO.getShortTermContractDiscount()).lopRent(contractInputDTO.getLopRent()).withdrawnRent(contractInputDTO.getWithdrawnRent())
      .terminationSavingAmount(contractInputDTO.getTerminationSavingAmount()).plainTerminationSavingAmount(contractInputDTO.getPlainTerminationSavingAmount())
      .fixedWithdrawnRent(contractInputDTO.getFixedWithdrawnRent()).terminationSavingsPayment(contractInputDTO.getTerminationSavingsPayment())
      .terminationSavingsPaymentDate(contractInputDTO.getTerminationSavingsPaymentDate()).fixedNNCoef(contractInputDTO.getFixedNNCoef())
      .fixedAgency(contractInputDTO.getFixedAgency()).fixedBenefit(contractInputDTO.getFixedBenefit()).fixedLopRent(contractInputDTO.getFixedLopRent())
      .fixedRevisedSurfaceAreaRent(contractInputDTO.getFixedRevisedSurfaceAreaRent()).fixedRevisedSurfaceArea(contractInputDTO.getFixedRevisedSurfaceArea())
      .closedContract(contractInputDTO.getClosedContract()).insuranceCertificateEndDate(contractInputDTO.getInsuranceCertificateEndDate()).build());
  }

  /**
   * permet de modifier un contrat occupant et de mettre à jour ses montants calculées
   * @param contractDTO un contrat occupant
   * @return un ContractDTO
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  private ContractDTO update(ContractDTO contractDTO) throws ContractServiceFacadeException {
    try {
      ContractEntity contractEntity = contractDAO.update(mapper.map(contractDTO, ContractEntity.class));
      // On récupère la liste du detail des CRON
      DetailCronDTO detailCronDTO = detailCronService.getCronsOfCurrentMonth();

      LocalDateTime generationDate = new LocalDateTime();

      // Si les générations sont déjà passées, on considère qu'on est au mois suivant
      if (generationDate.isAfter(detailCronService.getLastCronOfTheMonth(detailCronDTO).toLocalDateTime())) {
        generationDate = generationDate.plusMonths(1);
      }
      // Calcul du loyer réellement prélevé
      contractEntity = contractCalculationsServiceFacade.calculateActuallyWithdrawnRent(contractEntity, generationDate.toLocalDate());
      if (contractEntity.getRetroactivitysMonths() != 0) {
        // Calcul du loyer prélevé cumulé pour la rétroactivité
        contractEntity = contractCalculationsServiceFacade.calculateActuallyAddedWithdrawnRent(contractEntity, generationDate.toLocalDate());
        // Calcul du pécule de fin d'occupation pour la rétroactivité
        contractEntity = contractCalculationsServiceFacade.calculateActuallyTerminationSavingAmount(contractEntity, generationDate.toLocalDate());
      }

      // On met à jour les montants en base de données
      return ContractUtils.mapToContractDTO(contractDAO.update(contractEntity), mapper);
    } catch (GenericDAOException genericDaoException) {
      LOGGER.error("Erreur survenue lors de la mise a jour", genericDaoException);
      throw new ContractServiceFacadeException(genericDaoException);
    } catch (MappingException mappingException) {
      LOGGER.error(MAPPING_ERROR_CODE, mappingException);
      throw new ContractServiceFacadeException(mappingException);
    } catch (DetailCronServiceException e) {
      LOGGER.error("Erreur survenue lors de la récupération des CRONs", e);
      throw new ContractServiceFacadeException(e);
    } catch (ContractCalculationsServiceFacadeException e) {
      LOGGER.error("Erreur survenue lors des calculs", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> find() throws ContractServiceFacadeException {
    try {
      List<ContractEntity> entites = contractDAO.find();
      List<ContractDTO> dtos = new ArrayList<ContractDTO>(entites.size());
      for (final ContractEntity entity : entites) {
        dtos.add(ContractUtils.mapToContractDTO(entity, mapper));
      }
      return dtos;
    } catch (GenericDAOException genericDaoException) {
      LOGGER.error(GET_ERROR_CODE, genericDaoException);
      throw new ContractServiceFacadeException(genericDaoException);
    } catch (MappingException mappingException) {
      LOGGER.error(MAPPING_ERROR_CODE, mappingException);
      throw new ContractServiceFacadeException(mappingException);
    }
  }

  @Override
  public List<ContractDTO> findByCriteria(ContractCriteriaDTO contractCriteria, TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria)
    throws ContractServiceFacadeException {
    try {
      List<ContractDTO> lstDTO = new ArrayList<ContractDTO>();
      LocalDateTime generationDate = LocalDateTime.now();
      DetailCronDTO detailCronDTO = detailCronService.getCronsOfCurrentMonth();

      if (detailCronService.getLastCronOfTheMonth(detailCronDTO).isBefore(generationDate.toDateTime())) {
        generationDate = generationDate.plusMonths(1);
      }

      List<ContractEntity> lstEntity = contractDAO.findByCriteria(contractCriteria, tenantCriteria, housingCriteria);

      for (ContractEntity contract : SafetyUtils.emptyIfNull(lstEntity)) {

        ContractDTO contractDTO = ContractUtils.mapToContractDTO(contract, mapper);

        // On ajoute au loyer prélevé cumulé le loyer prélevé cumulé du mois en cours
        BigDecimal addedWithdrawnRent = contract.getPlainAddedWithdrawnRent();
        if (contract.getNetAgentRent() != null || contract.getExtraRent() != null) {
          addedWithdrawnRent = addedWithdrawnRent.add(ContractUtils.addedWithdrawnRentWithProrata(contractDTO, generationDate.toLocalDate()));
        }
        contract.setPlainAddedWithdrawnRent(addedWithdrawnRent);
        // On ajoute le pécule de fin d'occupation du mois en cours
        if (contract.getTerminationSavings()) {
          BigDecimal terminationSavingsAmount = contract.getPlainTerminationSavingAmount();
          terminationSavingsAmount = terminationSavingsAmount.add(ContractUtils.terminationSavingMonth(contractDTO, generationDate.toLocalDate()));
          contract.setPlainTerminationSavingAmount(terminationSavingsAmount);
        } else {
          contract.setPlainTerminationSavingAmount(BigDecimal.ZERO);
        }
        lstDTO.add(ContractUtils.mapToContractDTO(contract, mapper));
      }

      return lstDTO;
    } catch (ContractDAOException e) {
      throw new ContractServiceFacadeException(e);
    } catch (MappingException e) {
      throw new ContractServiceFacadeException(e);
    } catch (DetailCronServiceException e) {
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public ContractInputDTO mapToInputDTO(ContractDTO contractDTO) throws ContractServiceFacadeException {
    ContractInputDTO contractInputDTO = mapper.map(contractDTO, ContractInputDTO.class);
    contractInputDTO.setnNCoefDetail(ContractUtils.getNnCalculation(contractDTO));
    return contractInputDTO;
  }

  @Override
  public List<ContractInputDTO> mapToInputDTO(List<ContractDTO> contractDTO) throws ContractServiceFacadeException {
    List<ContractInputDTO> contractInputDTOList = new ArrayList<ContractInputDTO>();
    for (ContractDTO aContractDTO : contractDTO) {
      contractInputDTOList.add(mapper.map(aContractDTO, ContractInputDTO.class));
    }
    return contractInputDTOList;
  }

  @Override
  public List<ContractDTO> findContractByHousing(long idHousing) throws ContractServiceFacadeException {
    try {
      List<ContractDTO> lstDTO = new ArrayList<ContractDTO>();
      List<ContractEntity> lstEntity = contractDAO.findContractByHousing(idHousing);

      for (ContractEntity contract : SafetyUtils.emptyIfNull(lstEntity)) {
        lstDTO.add(ContractUtils.mapToContractDTO(contract, mapper));
      }

      return lstDTO;
    } catch (MappingException e) {
      throw new ContractServiceFacadeException(e);
    } catch (ContractDAOException e) {
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findContractByTenant(long idTenant) throws ContractServiceFacadeException {
    try {
      List<ContractDTO> lstDTO = new ArrayList<ContractDTO>();
      List<ContractEntity> lstEntity = contractDAO.findContractByTenant(idTenant);

      for (ContractEntity contract : SafetyUtils.emptyIfNull(lstEntity)) {
        lstDTO.add(ContractUtils.mapToContractDTO(contract, mapper));
      }

      return lstDTO;
    } catch (MappingException e) {
      throw new ContractServiceFacadeException(e);
    } catch (ContractDAOException e) {
      throw new ContractServiceFacadeException(e);
    }

  }

  @Override
  public List<Integer> validityContractDate(Long contractId, Date dateStart, Date dateEnd, Long housingId, Long tenantId) throws ContractServiceFacadeException {
    try {
      List<Object[]> countConflictContractMatrix;
      List<Integer> countConflictContractList = new ArrayList<Integer>();
      Long finalContractId = contractId;

      if (null == contractId) {
        finalContractId = ConstantsWEB.CREATE_CONTRACT_ID_DATE_VALIDITY;
      }

      countConflictContractMatrix = contractDAO.validityContractDate(finalContractId, dateStart, dateEnd, housingId, tenantId);

      for (Object object : Arrays.asList(countConflictContractMatrix.get(0))) {
        countConflictContractList.add((Integer) object);
      }

      return countConflictContractList;
    } catch (ContractDAOException e) {
      LOGGER.error(GET_ERROR_CODE, e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public Long unregisterDateAfterEndValidityDate(Long idHousing, Date unregisterDate) throws ContractServiceFacadeException {
    try {
      return contractDAO.unregisterDateAfterEndValidityDate(idHousing, unregisterDate);
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation du nombre de conflit pour la date de sortie", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findContractsInProgressOfSalariedTenant() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> contractsInProgress = new ArrayList<ContractDTO>();
      for (ContractEntity contractInprogress : contractDAO.findContractsInProgressOfSalariedTenant()) {
        contractsInProgress.add(ContractUtils.mapToContractDTO(contractInprogress, mapper));
      }
      return contractsInProgress;
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation des contrats occupants en cours des salariés ", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findContractsInProgressToRegularizeOfSalariedTenant() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> contractsInProgress = new ArrayList<ContractDTO>();
      for (ContractEntity contractInprogress : contractDAO.findContractsInProgressToRegularizeOfSalariedTenant()) {
        contractsInProgress.add(ContractUtils.mapToContractDTO(contractInprogress, mapper));
      }
      return contractsInProgress;
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation des contrats occupants en cours des salariés ", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findActiveContractsWithRetainedSalary() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> activeContractsWithRetainedSalary = new ArrayList<ContractDTO>();
      for (ContractEntity contractWithRetainedSalary : contractDAO.findContractsWithRetainedSalary()) {
        ContractDTO contractDTO = ContractUtils.mapToContractDTO(contractWithRetainedSalary, mapper);
        // si le contrat est en cours ou s’il contient une facturation ponctuelle
        if (ContractUtils.isInProgress(contractDTO) || ContractUtils.hasSporadicallyInvoicing(contractDTO)) {
          activeContractsWithRetainedSalary.add(contractDTO);
        }
      }
      return activeContractsWithRetainedSalary;
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la récupération des contrats occupants actifs avec le type de paiement retenue sur paie", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findContractsInProgressWithoutInternalCompensation() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> contractsInProgress = new ArrayList<ContractDTO>();
      for (ContractEntity contractInprogressWithoutInternalCompensation : contractDAO.findContractsInProgressWithoutInternalCompensation()) {
        ContractDTO contractDTO = ContractUtils.mapToContractDTO(contractInprogressWithoutInternalCompensation, mapper);
        contractsInProgress.add(contractDTO);
      }
      return contractsInProgress;
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation des contrats occupants en cours", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findActiveContractsWithoutInternalCompensation() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> activeContracts = new ArrayList<ContractDTO>();
      for (ContractEntity contractWithoutInternalCompensation : contractDAO.findContractsWithoutInternalCompensation()) {
        ContractDTO contractDTO = ContractUtils.mapToContractDTO(contractWithoutInternalCompensation, mapper);
        // si le contrat est en cours ou s’il contient une facturation ponctuelle
        if (ContractUtils.isInProgress(contractDTO) || ContractUtils.hasSporadicallyInvoicing(contractDTO)) {
          activeContracts.add(contractDTO);
        }
      }
      return activeContracts;
    } catch (ContractDAOException e) {
      LOGGER.error(GET_ACTIVES_TENANT_CONTRACTS_ERROR_CODE, e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findActiveContractsWithoutInternalCompensationAndWithoutRetroactivityInModification() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> activeContracts = new ArrayList<ContractDTO>();
      for (ContractEntity contractWithoutInternalCompensation : contractDAO.findContractsWithoutInternalCompensation()) {
        ContractDTO contractDTO = ContractUtils.mapToContractDTO(contractWithoutInternalCompensation, mapper);
        // si le contrat est en cours ou s’il contient une facturation ponctuelle
        if ((ContractUtils.isInProgress(contractDTO) || ContractUtils.hasSporadicallyInvoicing(contractDTO)) && contractWithoutInternalCompensation.getRetroactivitysMonths() >= 0) {
          activeContracts.add(contractDTO);
        }
      }
      return activeContracts;
    } catch (ContractDAOException e) {
      LOGGER.error(GET_ACTIVES_TENANT_CONTRACTS_ERROR_CODE, e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findContractsInProgressAndToRegularizeWithoutInternalCompensation() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> contractsToRegularize = new ArrayList<ContractDTO>();
      for (ContractEntity contractWithoutInternalCompensation : contractDAO.findContractsInProgressAndToRegularizeWithoutInternalCompensation()) {
        ContractDTO contractDTO = ContractUtils.mapToContractDTO(contractWithoutInternalCompensation, mapper);
        contractsToRegularize.add(contractDTO);
      }
      return contractsToRegularize;
    } catch (ContractDAOException e) {
      LOGGER.error(GET_ACTIVES_TENANT_CONTRACTS_ERROR_CODE, e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findContractsInProgress(LocalDate date) throws ContractServiceFacadeException {
    try {
      List<ContractDTO> contractsInProgress = new ArrayList<ContractDTO>();
      for (ContractEntity contractInprogress : contractDAO.findContractsInProgress(date)) {
        contractsInProgress.add(ContractUtils.mapToContractDTO(contractInprogress, mapper));
      }
      return contractsInProgress;
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation des contrats occupants en cours", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findContractsInProgressWithoutRetroactivity(LocalDate date) throws ContractServiceFacadeException {
    try {
      List<ContractDTO> contracts = new ArrayList<ContractDTO>();
      for (ContractEntity contract : contractDAO.findContractsInProgressWithoutRetroactivity(date)) {
        contracts.add(ContractUtils.mapToContractDTO(contract, mapper));
      }
      return contracts;
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation des contrats occupants en cours sans rétroactivité", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public void updateContractsInProgress() throws ContractServiceFacadeException {
    List<ContractDTO> contracts = findContractsInProgress(new LocalDate());

    for (ContractDTO contractDTO : contracts) {
      update(contractDTO);
    }
  }

  @Override
  public void updateContractsForHousing(Long housingId) throws ContractServiceFacadeException {
    List<ContractDTO> contracts = findContractByHousing(housingId);

    for (ContractDTO contractDTO : contracts) {
      update(contractDTO);
    }
  }

  @Override
  public void updateContractsForTenant(Long tenantId) throws ContractServiceFacadeException {
    List<ContractDTO> contracts = findContractByTenant(tenantId);

    for (ContractDTO contractDTO : contracts) {
      update(contractDTO);
    }
  }

  @Override
  public void updateSporadicallyInvoicingContracts() throws ContractServiceFacadeException {
    try {
      contractDAO.updateSporadicallyInvoicingContracts();
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la mise à jour de la facturation ponctuelle des contrats occupants", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public void updateResetAddedWithdrawnRentContracts() throws ContractServiceFacadeException {
    try {
      contractDAO.updateResetAddedWithdrawnRentContracts();
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la mise à jour du loyer prélevé cumulé des contrats occupants", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public void updateAddedWithdrawnRentContractsInProgress() throws ContractServiceFacadeException {
    LocalDate now = new LocalDate();
    List<ContractDTO> contracts = findContractsInProgressWithoutRetroactivity(now);

    for (ContractDTO contract : contracts) {

      BigDecimal addedWithdrawnRentOfLastMonth = ContractUtils.addedWithdrawnRentWithProrata(contract, now);

      // arrondi du nombre à 2 chiffres après la virgule
      BigDecimal addedWithdrawRent = contract.getPlainAddedWithdrawnRent();
      addedWithdrawRent = addedWithdrawRent.add(addedWithdrawnRentOfLastMonth).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

      ContractInputDTO contractInputDTO = mapToInputDTO(contract);
      contractInputDTO.setPlainAddedWithdrawnRent(addedWithdrawRent);
      this.update(contractInputDTO, contract);
    }
  }

  @Override
  public void updateTerminationSavingsContractsInProgress() throws ContractServiceFacadeException {
    LocalDate now = new LocalDate();
    List<ContractDTO> contracts = findContractsInProgressWithoutRetroactivity(now);

    for (ContractDTO contract : contracts) {
      if (contract.getTerminationSavings()) {
        BigDecimal savingAmount = ContractUtils.terminationSavingMonth(contract, now);
        // arrondi du nombre à 2 chiffres après la virgule
        BigDecimal totalSavingAmount = contract.getPlainTerminationSavingAmount();
        totalSavingAmount = totalSavingAmount.add(savingAmount).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
        ContractInputDTO contractInputDTO = mapToInputDTO(contract);
        contractInputDTO.setPlainTerminationSavingAmount(totalSavingAmount);
        update(contractInputDTO, contract);
      }
    }
  }

  @Override
  public void updateLopRent() throws ContractServiceFacadeException {
    try {
      contractDAO.updateLopRent();
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur lors de la mise à jour des loyers écrêtés", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public Long countContractsInHousing(Long idHousing) throws ContractServiceFacadeException {
    try {
      return contractDAO.countContractsInHousing(idHousing);
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation du nombre de contrats occupant qu'il y a eu pour un logement ", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public ContractDTO findByReference(String reference) throws ContractServiceFacadeException {
    try {
      ContractEntity contractEntity = contractDAO.findByReference(reference);
      return ContractUtils.mapToContractDTO(contractEntity, mapper);
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation du contrat occupant par sa référence " + reference, e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public void applyRevaluationRent(RevaluationRentsDTO revaluationRentsDTO) throws ContractServiceFacadeException {
    try {
      BigDecimal rate = revaluationRentsDTO.getRate();
      Boolean action = revaluationRentsDTO.getAction();
      for (ContractEntity contract : contractDAO.findContractsWithRentToUpgrade()) {
        applyRevaluationRentOnOneContrat(rate, action, contract);
      }
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur lors de la mise à jour des revalorisations de loyers", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  /**
   * Permet d’appliquer le taux de revalorisation des loyers sur un contrat
   * @param rate taux à appliquer
   * @param action action (ajouter ou enlever)
   * @param contract contrat à traiter
   * @throws ContractServiceFacadeException ContractServiceFacadeException
   */
  private void applyRevaluationRentOnOneContrat(BigDecimal rate, Boolean action, ContractEntity contract) throws ContractServiceFacadeException {

    BigDecimal garageRent = contract.getGarageRent();
    BigDecimal gardenRent = contract.getGardenRent();
    BigDecimal ratio = TOTAL_RATIO.add(rate.divide(TOTAL_POURCENTAGE));

    if (null != action && action) {
      if (null != garageRent) {
        garageRent = garageRent.multiply(ratio);
        garageRent = garageRent.setScale(SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
      }
      if (null != gardenRent) {
        gardenRent = gardenRent.multiply(ratio);
        gardenRent = gardenRent.setScale(SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
      }
    } else if (null != action && !action) {
      if (null != garageRent) {
        garageRent = garageRent.divide(ratio, SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
      }
      if (null != gardenRent) {
        gardenRent = gardenRent.divide(ratio, SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
      }
    }

    contract.setGarageRent(garageRent);
    contract.setGardenRent(gardenRent);

    ContractDTO contractDTO;

    contractDTO = ContractUtils.mapToContractDTO(contract, mapper);

    this.update(contractDTO);
  }

  @Override
  public List<ContractDTO> findContractsInProgressToRegularizeWithoutInternalCompensation() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> contracts = new ArrayList<ContractDTO>();
      for (ContractEntity contract : contractDAO.findContractsInProgressToRegularizeWithoutInternalCompensation()) {
        contracts.add(ContractUtils.mapToContractDTO(contract, mapper));
      }
      return contracts;
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation des contrats à régulariser pour le rétroactivité", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findContractsToRegularizeWithRetainedSalary() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> contracts = new ArrayList<ContractDTO>();
      for (ContractEntity contract : contractDAO.findContractsToRegularizeWithRetainedSalary()) {
        contracts.add(ContractUtils.mapToContractDTO(contract, mapper));
      }
      return contracts;
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation des contrats à régulariser pour le rétroactivité", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findContractsToHistorize() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> contracts = new ArrayList<ContractDTO>();
      for (ContractEntity contract : contractDAO.findContractsToHistorize()) {
        contracts.add(ContractUtils.mapToContractDTO(contract, mapper));
      }
      return contracts;
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation des contrats à historiser", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public List<ContractDTO> findContractsToUpdateDataRetroactivity() throws ContractServiceFacadeException {
    try {
      List<ContractDTO> contracts = new ArrayList<ContractDTO>();
      for (ContractEntity contract : contractDAO.findContractsToUpdateDataRetroactivity()) {
        contracts.add(ContractUtils.mapToContractDTO(contract, mapper));
      }
      return contracts;
    } catch (ContractDAOException e) {
      LOGGER.error("Erreur survenue lors de la recuperation des contrats de rétroactivité avec mises à jour à effectuer sur les données", e);
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public void updateOneContractDataRetroactivity(ContractDTO contract) throws ContractServiceFacadeException {
    update(contract);
  }

  @Override
  public void resetContractsToRegularize() throws ContractServiceFacadeException {
    try {
      contractDAO.resetContractsToRegularize();
    } catch (ContractDAOException e) {
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public void updateAllContracts() throws ContractServiceFacadeException {
    LOGGER.info("Lancement de la mise à jour de tous les contrats occupant");

    List<ContractDTO> contracts = find();
    for (ContractDTO contract : contracts) {
      this.update(contract);
    }

    LOGGER.info("Fin de la mise à jour de tous les contrats occupant");
  }

  @Override
  public void delete(Long id) throws ContractServiceFacadeException {
    try {
      ncNtAccountingDocNumberService.deleteAllNcNtAccountingDocNumberOfOneContract(id);
      historyAmountService.deleteAllHistoryOfOneContract(id);
      historyContractServiceFacade.deleteAllHistoryContractOfOneContract(id);
      contractDAO.delete(id);
    } catch (NcNtAccountingDocNumberServiceException e) {
      throw new ContractServiceFacadeException(e);
    } catch (HistoryAmountServiceException e) {
      throw new ContractServiceFacadeException(e);
    } catch (HistoryContractServiceException e) {
      throw new ContractServiceFacadeException(e);
    } catch (ContractDAOException e) {
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public FieldOfActivityDTO findFieldOfActivityForOneHousing(Long idHousing) throws ContractServiceFacadeException {
    try {
      ContractEntity contractEntity = contractDAO.findCurrentContractOfOneHousing(idHousing);
      if (contractEntity != null) {
        ContractDTO contractDTO = ContractUtils.mapToContractDTO(contractEntity, mapper);
        return contractDTO.getFieldOfActivity();
      } else {
        return null;
      }
    } catch (ContractDAOException e) {
      throw new ContractServiceFacadeException(e);
    }
  }

  /**
   * Permet de vérifier si le contrat est rétroactif lors d’une création
   * @param idContract l'identifiant du contrat occupant
   */
  private void checkRegularisationInCreation(Long idContract) throws ContractServiceFacadeException {
    try {
      // On récupère le contrat occupant créé avec les valeurs calculées
      ContractDTO contractDTO = get(idContract);
      ContractInputDTO contractInputDTO = mapToInputDTO(contractDTO);

      // le premier et dernier jour du mois permet de vérifier si le contrat est rétroactif
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime firstDayOfMonth = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(now.toLocalDate());
      LocalDateTime lastDayOfMonth = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now.toLocalDate());

      // le dernier CRON permet de savoir si la phase de génération est déjà passée
      DetailCronDTO detailCronDTO = detailCronService.getCronsOfCurrentMonth();
      LocalDateTime lastCron = detailCronService.getLastCronOfTheMonth(detailCronDTO).toLocalDateTime();
      Boolean generationIsAlreadyPast = now.isAfter(lastCron);

      // La date de validité en localDateTime
      LocalDateTime startValidity = new LocalDateTime(contractDTO.getStartValidityDate());

      // On test pour savoir si le contrat a les caractéristiques nécessaires pour être rétroactif
      if (startValidity.isBefore(firstDayOfMonth) && !generationIsAlreadyPast || !startValidity.isAfter(lastDayOfMonth) && generationIsAlreadyPast) {
        Integer retroactivitysMonths = ContractUtils.calculateTheNumberOfMonthsOfRetroactivity(contractDTO.getStartValidityDate(), generationIsAlreadyPast);

        if (retroactivitysMonths != 0) {
          historyContractServiceFacade.historizeOneNewContractTemporarily(contractDTO, retroactivitysMonths);
        }

        contractInputDTO.setRetroactivitysMonths(retroactivitysMonths);
      }

      // la mise à jour du contrat permet de recalculer les loyers en tenant compte des historisations
      update(contractInputDTO, contractDTO);
    } catch (DetailCronServiceException e) {
      throw new ContractServiceFacadeException(e);
    } catch (HistoryContractServiceException e) {
      throw new ContractServiceFacadeException(e);
    }
  }

  @Override
  public void saveDatasWhenClosedContract() throws ContractServiceFacadeException {
    List<ContractDTO>  contracts = find();
    for (ContractDTO contract : contracts) {

      // Dernier jour du mois courant
      Date endOfMonth = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(LocalDate.now()).toDate();

      // si le contrat n'était pas clos avant l'action de l'utilisateur et qu'il l'est maintenant,
      // les données de ce dernier vont être figées
      if (!contract.getClosedContract()
        && contractIsClosed(contract, endOfMonth)) {
        ContractInputDTO contractInput = this.mapToInputDTO(contract);

        contractInput.setClosedContract(true);

        contractInput.setFixedAgency(contractInput.getHousing().getAgency());
        contractInput.setFixedBenefit(contractInput.getBenefit());
        contractInput.setFixedLopRent(contractInput.getLopRent());
        contractInput.setFixedNNCoef(contractInput.getnNCoef());
        contractInput.setFixedRevisedSurfaceArea(contractInput.getRevisedSurfaceArea());
        contractInput.setFixedRevisedSurfaceAreaRent(contractInput.getRevisedSurfaceAreaRent());

        update(contractInput, contract);
      }
    }
  }

  /**
   * Permet de savoir si le contrat occupant devient clos
   * @param contract le contrat à tester
   * @param endOfMonth la date de la fin du mois
   * @return true s'il devient clos
   */
  private boolean contractIsClosed(ContractDTO contract, Date endOfMonth) {
    return contract.getEndValidityDate() != null && (contract.getEndValidityDate().before(endOfMonth) || contract.getEndValidityDate().equals(endOfMonth));
  }



  /**
   * @param contractDAO the contractDAO to set
   */
  public void setContractDAO(IContractDAO contractDAO) {
    this.contractDAO = contractDAO;
  }

  /**
   * @param mapper the mapper to set
   */
  public void setMapper(Mapper mapper) {
    this.mapper = mapper;
  }

  /**
   * @param historyContractServiceFacade the historyContractServiceFacade to set
   */
  public void setHistoryContractServiceFacade(IHistoryContractServiceFacade historyContractServiceFacade) {
    this.historyContractServiceFacade = historyContractServiceFacade;
  }

  /**
   * @param detailCronService the detailCronService to set
   */
  public void setDetailCronService(IDetailCronService detailCronService) {
    this.detailCronService = detailCronService;
  }

  /**
   * @param ncNtAccountingDocNumberService the ncNtAccountingDocNumberService to set
   */
  public void setNcNtAccountingDocNumberService(INcNtAccountingDocNumberService ncNtAccountingDocNumberService) {
    this.ncNtAccountingDocNumberService = ncNtAccountingDocNumberService;
  }

  /**
   * @param historyAmountService the historyAmountService to set
   */
  public void setHistoryAmountService(IHistoryAmountService historyAmountService) {
    this.historyAmountService = historyAmountService;
  }

  /**
   * @param contractCalculationsServiceFacade the contractCalculationsServiceFacade to set
   */
  public void setContractCalculationsServiceFacade(IContractCalculationsServiceFacade contractCalculationsServiceFacade) {
    this.contractCalculationsServiceFacade = contractCalculationsServiceFacade;
  }

}
