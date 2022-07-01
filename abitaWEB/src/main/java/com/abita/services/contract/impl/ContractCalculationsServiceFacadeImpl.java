package com.abita.services.contract.impl;

import com.abita.dao.contract.entity.ContractEntity;
import com.abita.services.contract.IContractCalculationsServiceFacade;
import com.abita.services.historycontract.IHistoryContractServiceFacade;
import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.contract.ContractUtils;
import com.abita.dto.ContractDTO;
import com.abita.dto.HistoryContractDTO;
import com.abita.services.contract.exceptions.ContractCalculationsServiceFacadeException;
import com.abita.services.historycontract.exceptions.HistoryContractServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dozer.Mapper;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe d’implémentation des services de calcul des contrats occupant
 * */
public class ContractCalculationsServiceFacadeImpl implements IContractCalculationsServiceFacade {

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ContractCalculationsServiceFacadeImpl.class);

  /** Service d'historisation des contrats occupants */
  private IHistoryContractServiceFacade historyContractServiceFacade;

  /** Mapper dozzer */
  private Mapper mapper;

  @Override
  public ContractEntity calculateActuallyTerminationSavingAmount(ContractEntity contractEntity, LocalDate generationDate) throws ContractCalculationsServiceFacadeException {
    try {
      ContractEntity contractEntityWithActuallyTerminationSavingAmount = contractEntity;

      // On initialise le pécule de fin d'occupation à 0
      BigDecimal terminationSavingAmount = BigDecimal.ZERO;

      if (contractEntity.getRetroactivitysMonths() > 0) {
        terminationSavingAmount = calculateActuallyTerminationSavingAmountInCreation(contractEntity, terminationSavingAmount, generationDate);
      } else {
        terminationSavingAmount = calculateActuallyTerminationSavingAmountInModification(contractEntity, terminationSavingAmount, generationDate);
      }

      contractEntityWithActuallyTerminationSavingAmount.setPlainTerminationSavingAmount(terminationSavingAmount);

      return contractEntityWithActuallyTerminationSavingAmount;
    } catch (HistoryContractServiceException e) {
      throw new ContractCalculationsServiceFacadeException(e);
    }
  }

  /**
   * Calcul du pécule de fin d'occupation d'un contrat occupant en création et mise à jour des historisations
   * @param contractEntity une entité de contrat occupant
   * @param terminationSavingAmount un pécule de fin d'occupation
   * @param generationDate date de génération
   * @return le pécule de fin d'occupation du contrat
   * @throws ContractCalculationsServiceFacadeException une ContractCalculationsServiceFacadeException
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  private BigDecimal calculateActuallyTerminationSavingAmountInCreation(ContractEntity contractEntity, BigDecimal terminationSavingAmount, LocalDate generationDate)
    throws ContractCalculationsServiceFacadeException, HistoryContractServiceException {
    ContractDTO contractWithActuallyTerminationSavingAmount = ContractUtils.mapToContractDTO(contractEntity, mapper);
    int numberOfRetroactivityMonths = contractWithActuallyTerminationSavingAmount.getRetroactivitysMonths();
    int absoluteValueOfNumberOfRetroactivityMonths = Math.abs(numberOfRetroactivityMonths);
    BigDecimal calcultateTerminationSavingAmount = terminationSavingAmount;

    // On boucle dans les historisations de la plus ancienne à la plus récente
    for (int i = absoluteValueOfNumberOfRetroactivityMonths; i > 0; i--) {

      LocalDate regularizationDate = new LocalDate(generationDate);
      regularizationDate = regularizationDate.minusMonths(i);

      // on récupère l'historisation
      HistoryContractDTO historyContractDTO = historyContractServiceFacade.get(contractEntity.getId(), regularizationDate.getMonthOfYear(), regularizationDate.getYear());

      if (historyContractDTO != null) {
        // On met à jour l'historisation
        historyContractDTO.setTerminationSavingAmount(calcultateTerminationSavingAmount);
        historyContractServiceFacade.update(historyContractDTO);

        // Si le pécule de fin est à oui sinon il reste à 0
        if (contractEntity.getTerminationSavings()) {
          calcultateTerminationSavingAmount = calcultateTerminationSavingAmount
            .add(ContractUtils.terminationSavingMonthForRegularization(contractWithActuallyTerminationSavingAmount, historyContractDTO, regularizationDate));
          calcultateTerminationSavingAmount = calcultateTerminationSavingAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
        }
      }
    }
    // On retourne le pécule de fin calculé
    return calcultateTerminationSavingAmount;
  }

  /**
   * Calcul du pécule de fin d'occupation d'un contrat occupant en modification
   * @param contractEntity une entité de contrat occupant
   * @param terminationSavingAmount un pécule de fin d'occupation
   * @param generationDate date de génération
   * @return le pécule de fin d'occupation du contrat
   * @throws ContractCalculationsServiceFacadeException une ContractCalculationsServiceFacadeException
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  private BigDecimal calculateActuallyTerminationSavingAmountInModification(ContractEntity contractEntity, BigDecimal terminationSavingAmount, LocalDate generationDate)
    throws ContractCalculationsServiceFacadeException, HistoryContractServiceException {
    ContractDTO contractWithActuallyTerminationSavingAmount = ContractUtils.mapToContractDTO(contractEntity, mapper);
    int numberOfRetroactivityMonths = contractEntity.getRetroactivitysMonths();
    int absoluteValueOfNumberOfRetroactivityMonths = Math.abs(numberOfRetroactivityMonths);
    BigDecimal calcultateTerminationSavingAmount = terminationSavingAmount;

    // on récupère le contrat historisé retroactif le plus ancien
    LocalDate dateBeforeRegularization = new LocalDate(generationDate);
    dateBeforeRegularization = dateBeforeRegularization.minusMonths(absoluteValueOfNumberOfRetroactivityMonths);

    // Si le pécule de fin est à oui sinon il reste à 0
    if (contractEntity.getTerminationSavings()) {

      // On récupère le contrat historisé
      HistoryContractDTO lastHistoryBeforeRetroactivityDTO = historyContractServiceFacade.get(contractEntity.getId(), dateBeforeRegularization.getMonthOfYear(),
        dateBeforeRegularization.getYear());

      if (lastHistoryBeforeRetroactivityDTO != null) {
        calcultateTerminationSavingAmount = lastHistoryBeforeRetroactivityDTO.getTerminationSavingAmount();

        BigDecimal lastCalculateTerminationSavingAmount = ContractUtils.terminationSavingMonthForRegularization(contractWithActuallyTerminationSavingAmount,
          lastHistoryBeforeRetroactivityDTO, dateBeforeRegularization);

        calcultateTerminationSavingAmount = calcultateTerminationSavingAmount.add(lastCalculateTerminationSavingAmount);
      }
    }
    // On retourne le pécule de fin calculé
    return calcultateTerminationSavingAmount;
  }

  @Override
  public ContractEntity calculateActuallyAddedWithdrawnRent(ContractEntity contractEntity, LocalDate generationDate) throws ContractCalculationsServiceFacadeException {
    try {
      ContractEntity contractEntityWithActuallyAddedWithdrawnRent = contractEntity;

      // On initialise le pécule de fin d'occupation à 0
      BigDecimal addedWithdrawnRent = BigDecimal.ZERO;

      if (contractEntity.getRetroactivitysMonths() > 0) {

        addedWithdrawnRent = calculateActuallyAddedWithdrawnRentInCreation(contractEntity, addedWithdrawnRent, generationDate);

      } else {
        addedWithdrawnRent = calculateActuallyAddedWithdrawnRentInModification(contractEntity, addedWithdrawnRent, generationDate);
      }

      contractEntityWithActuallyAddedWithdrawnRent.setPlainAddedWithdrawnRent(addedWithdrawnRent);

      return contractEntityWithActuallyAddedWithdrawnRent;
    } catch (HistoryContractServiceException e) {
      throw new ContractCalculationsServiceFacadeException(e);
    }
  }

  /**
   * Calcule du loyer prélevé cumulé en création et mise à jour des historisations
   * @param contractEntity une entité de contrat occupant
   * @param addedWithdrawnRent le pécule de fin d'occupation
   * @param generationDate date de génération
   * @return le pécule de fin d'occupation calculé
   * @throws ContractCalculationsServiceFacadeException une ContractCalculationsServiceFacadeException
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  private BigDecimal calculateActuallyAddedWithdrawnRentInCreation(ContractEntity contractEntity, BigDecimal addedWithdrawnRent, LocalDate generationDate)
    throws ContractCalculationsServiceFacadeException, HistoryContractServiceException {
    ContractDTO contractWithActuallyAddedWithdrawnRent = ContractUtils.mapToContractDTO(contractEntity, mapper);
    int numberOfRetroactivityMonths = contractWithActuallyAddedWithdrawnRent.getRetroactivitysMonths();
    int absoluteValueOfNumberOfRetroactivityMonths = Math.abs(numberOfRetroactivityMonths);
    BigDecimal calculateAddedWithdrawnRent = addedWithdrawnRent;

    // On boucle dans les historisations de la plus ancienne à la plus récente
    for (int i = absoluteValueOfNumberOfRetroactivityMonths; i > 0; i--) {

      LocalDate regularizationDate = new LocalDate(generationDate);
      regularizationDate = regularizationDate.minusMonths(i);

      // on récupère l'historisation
      HistoryContractDTO historyContractDTO = historyContractServiceFacade.get(contractEntity.getId(), regularizationDate.getMonthOfYear(), regularizationDate.getYear());

      // Si on est en janvier, on remet le loyer prélevé cumulé à 0
      if (regularizationDate.getMonthOfYear() == 1) {
        calculateAddedWithdrawnRent = BigDecimal.ZERO;
      }

      if (historyContractDTO != null) {
        historyContractDTO.setAddedWithdrawnRent(calculateAddedWithdrawnRent);

        // On met à jour l’historisation
        historyContractServiceFacade.update(historyContractDTO);

        // On ajoute le loyer prélevé cumulé du mois historisé au loyer prélevé cumulé
        calculateAddedWithdrawnRent = calculateAddedWithdrawnRent
          .add(ContractUtils.addedWithdrawnRentWithProrataForRegularization(contractWithActuallyAddedWithdrawnRent, historyContractDTO, regularizationDate));
      }
    }
    if (generationDate.getMonthOfYear() == 1) {
      calculateAddedWithdrawnRent = BigDecimal.ZERO;
    }
    // On retourne le loyer prélevé cumulé calculé
    return calculateAddedWithdrawnRent;
  }

  /**
   * Calcule du loyer prélevé cummulé en modification
   * @param contractEntity une entité de contrat occupant
   * @param addedWithdrawnRent le pécule de fin d'occupation
   * @param generationDate date de génération
   * @return le pécule de fin d'occupation calculé
   * @throws ContractCalculationsServiceFacadeException une ContractCalculationsServiceFacadeException
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  private BigDecimal calculateActuallyAddedWithdrawnRentInModification(ContractEntity contractEntity, BigDecimal addedWithdrawnRent, LocalDate generationDate)
    throws ContractCalculationsServiceFacadeException, HistoryContractServiceException {
    ContractDTO contractWithActuallyAddedWithdrawnRent = ContractUtils.mapToContractDTO(contractEntity, mapper);
    int numberOfRetroactivityMonths = contractWithActuallyAddedWithdrawnRent.getRetroactivitysMonths();
    int absoluteValueOfNumberOfRetroactivityMonths = Math.abs(numberOfRetroactivityMonths);
    BigDecimal calculateAddedWithdrawnRent = addedWithdrawnRent;

    LocalDate dateBeforeRegularization = new LocalDate(generationDate);
    dateBeforeRegularization = dateBeforeRegularization.minusMonths(absoluteValueOfNumberOfRetroactivityMonths);

    // Si l'historisation rétroactive est dans l'année précédente, le LPC est de 0
    if (dateBeforeRegularization.getYear() < generationDate.getYear()) {
      return calculateAddedWithdrawnRent;
    }

    // on récupère le contrat historisé retroactif le plus ancien
    HistoryContractDTO lastHistoryBeforeRetroactivityDTO = historyContractServiceFacade.get(contractEntity.getId(), dateBeforeRegularization.getMonthOfYear(),
      dateBeforeRegularization.getYear());

    if (lastHistoryBeforeRetroactivityDTO != null) {
      // On récupère le loyer prélevé cumulé le plus ancien pour lui ajouter le loyer prélevé cumulé du mois historisé
      BigDecimal lastAddedWithdrawnRentBeforeRetroactivityDTO = lastHistoryBeforeRetroactivityDTO.getAddedWithdrawnRent();

      BigDecimal lastWithdrawnRentBeforeRetroactivityDTO = ContractUtils.addedWithdrawnRentWithProrataForRegularization(contractWithActuallyAddedWithdrawnRent,
        lastHistoryBeforeRetroactivityDTO, dateBeforeRegularization);

      // On ajoute le loyer prélevé cumulé retroactif le plus ancien
      calculateAddedWithdrawnRent = lastAddedWithdrawnRentBeforeRetroactivityDTO.add(lastWithdrawnRentBeforeRetroactivityDTO);
    }

    // On retourne le loyer prélevé cumulé calculé
    return calculateAddedWithdrawnRent;
  }

  @Override
  public ContractEntity calculateActuallyWithdrawnRent(ContractEntity contractEntity, LocalDate generationDate) throws ContractCalculationsServiceFacadeException {
    try {
      // L'entité avec le loyer réellement prélevé
      ContractEntity contractEntityWithActuallyWithdrawnRent = contractEntity;

      BigDecimal actuallyWithdrawnRent = BigDecimal.ZERO;
      List<HistoryContractDTO> historyContractDTOs = new ArrayList<HistoryContractDTO>();

      int numberOfRetroactivityMonths = contractEntityWithActuallyWithdrawnRent.getRetroactivitysMonths();
      int absoluteValueOfNumberOfRetroactivityMonths = Math.abs(numberOfRetroactivityMonths);

      if (numberOfRetroactivityMonths != 0) {

        // On récupère les mois rétroactifs
        for (int i = 0; i < absoluteValueOfNumberOfRetroactivityMonths; i++) {

          LocalDate regularizationDate = new LocalDate(generationDate);
          regularizationDate = regularizationDate.minusMonths(i + 1);

          HistoryContractDTO historyContractDTO = historyContractServiceFacade.get(contractEntity.getId(), regularizationDate.getMonthOfYear(), regularizationDate.getYear());

          // on les ajoute à une liste
          historyContractDTOs.add(historyContractDTO);
        }

      }
      // On map l'entité en DTO afin de faire les calculs mais on ne peut le renvoyer car il est immutable
      ContractDTO actuallyContract = ContractUtils.mapToContractDTO(contractEntity, mapper);

      // On fait le calcule du loyer réellement prélevé
      actuallyWithdrawnRent = ContractUtils.withdrawnRentAmountWithAllInvoicingAndRegularization(actuallyContract, historyContractDTOs, generationDate);

      // On met à jour l'entité avec le loyer réellement prélevé
      contractEntityWithActuallyWithdrawnRent.setFixedWithdrawnRent(actuallyWithdrawnRent);

      return contractEntityWithActuallyWithdrawnRent;
    } catch (HistoryContractServiceException e) {
      LOGGER.error("Erreur lors de la récupération des historisations de contrat", e);
      throw new ContractCalculationsServiceFacadeException(e);
    }
  }

  /**
   * @param historyContractServiceFacade the historyContractServiceFacade to set
   */
  public void setHistoryContractServiceFacade(IHistoryContractServiceFacade historyContractServiceFacade) {
    this.historyContractServiceFacade = historyContractServiceFacade;
  }

  /**
   * @param mapper the mapper to set
   */
  public void setMapper(Mapper mapper) {
    this.mapper = mapper;
  }

}
