/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util.contract;

import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.dao.contract.entity.ContractEntity;
import com.abita.dto.AgencyDTO;
import com.abita.dto.ContractDTO;
import com.abita.dto.CostCenterDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.HistoryContractDTO;
import com.abita.dto.HousingDTO;
import com.abita.dto.PaymentMethodDTO;
import com.abita.dto.RentTypologyDTO;
import com.abita.dto.TenantDTO;
import com.abita.dto.TerminationDTO;
import com.abita.dto.unpersist.ContractInputDTO;
import com.abita.services.jobs.quittancement.constants.QuittancementConstants;
import com.abita.util.dateutil.DateTimeUtils;
import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.YearMonth;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe utilitaire pour les contrats occupants (calculs, comparaisons, date ...)
 * @author
 *
 */
public final class ContractUtils {

  /**
   * Constructeur privé
   */
  private ContractUtils() {
  }

  /** Abattement du loyer jardin */
  public static final double GARDEN_RENT_DISCOUNT = 7.5d;

  /** Code technique pour le loyer d’astreinte */
  private static final String RENT_CALL_CODE = "LAS";

  /** Caractère de division */
  private static final String CHARACTER_DIVISION = " / ";

  /**
   * Calcul le montant de la retenue sur paie au prorata pour la rubrique de paie loyer net de charge
   * @param contract Contrat à traiter
   * @param generationDate Date de traitement
   * @return le resultat du calcul proraté
   */
  public static BigDecimal retainedSalaryNetRentAmount(ContractDTO contract, LocalDate generationDate) {
    BigDecimal result = new BigDecimal(0);

    // LNA
    BigDecimal netAgentRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getNetAgentRent());
    netAgentRent = netAgentRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // loyer jardin (-7.5%)
    BigDecimal gardenRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGardenRent()).subtract(
      BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGardenRent()).multiply(BigDecimal.valueOf(GARDEN_RENT_DISCOUNT))
        .movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION));
    gardenRent = gardenRent.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
    gardenRent = gardenRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // loyer garage
    BigDecimal garageRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGarageRent());
    garageRent = garageRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // surloyer
    BigDecimal extraRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getExtraRent());
    extraRent = extraRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    result = result.add(netAgentRent).add(gardenRent).add(garageRent).add(extraRent);
    return result;
  }

  /**
   * Calcul le montant de la retenue sur paie au prorata pour la rubrique de paie loyer net de charge pour les mois rétroactifs
   * @param historyContract l’historisation du contrat occupant à régulariser
   * @param startValidityDate la date de debut de validité
   * @param endValidityDate la date de fin de validité
   * @param regularizationDate la date de la régularisation
   * @return le resultat du calcul proraté
   */
  public static BigDecimal retainedSalaryNetRentAmountForRegularization(HistoryContractDTO historyContract, Date startValidityDate, Date endValidityDate,
    LocalDate regularizationDate) {
    BigDecimal result = new BigDecimal(0);

    // LNA
    BigDecimal netAgentRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getNetAgentRent());
    netAgentRent = netAgentRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    // loyer jardin (-7.5%)
    BigDecimal gardenRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGardenRent()).subtract(
      BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGardenRent()).multiply(BigDecimal.valueOf(GARDEN_RENT_DISCOUNT))
        .movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION));
    gardenRent = gardenRent.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
    gardenRent = gardenRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    // loyer garage
    BigDecimal garageRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGarageRent());
    garageRent = garageRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    // surloyer
    BigDecimal extraRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getExtraRent());
    extraRent = extraRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    result = result.add(netAgentRent).add(gardenRent).add(garageRent).add(extraRent);
    return result;
  }

  /**
   * Calcul le montant de la retenue sur paie au prorata pour la rubrique de paie charges locatives et charges liées au logement
   * @param contract Contrat à traiter
   * @param generationDate Date de traitement
   * @return le resultat du calcul proraté
   */
  public static BigDecimal retainedSalaryRentalChargesAmount(ContractDTO contract, LocalDate generationDate) {
    BigDecimal result = new BigDecimal(0);

    // charges prévisionelles
    BigDecimal expectedChargeCost = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getExpectedChargeCost());
    expectedChargeCost = expectedChargeCost.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    result = result.add(expectedChargeCost).add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getAnnualClearanceCharges()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGarbageInvoicing()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getWaterInvoicing()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getOtherInvoicingAmount()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    return result;
  }

  /**
   * Calcul le montant de la retenue sur paie au prorata pour la rubrique de paie charges locatives et charges liées au logement pour les mois rétroactifs
   * @param historyContract l’historisation du contrat occupant à régulariser
   * @param startValidityDate la date de debut de validité
   * @param endValidityDate la date de fin de validité
   * @param regularizationDate la date de la régularisation
   * @return le resultat du calcul proraté
   */
  public static BigDecimal retainedSalaryRentalChargesAmountForRegularization(HistoryContractDTO historyContract, Date startValidityDate, Date endValidityDate,
    LocalDate regularizationDate) {

    // charges prévisionelles
    BigDecimal expectedChargeCost = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getExpectedChargeCost());
    expectedChargeCost = expectedChargeCost.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(
      BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    return expectedChargeCost;
  }

  /**
   * Calcul le montant de la retenue sur paie au prorata pour la rubrique de paie remboursement quote part
   * @param contract Contrat à traiter
   * @param generationDate Date de traitement
   * @return le resultat du calcul proraté
   */
  public static BigDecimal retainedSalaryReimbursementAmount(ContractDTO contract, LocalDate generationDate) {
    BigDecimal result = new BigDecimal(0);

    result = result.add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getInsuranceReimbursement())).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN)
      .add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGarbageReimbursement())).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN)
      .add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getHousingTaxReimbursement())).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    return result;
  }

  /**
   * Récupère le montant de la retenue sur paie
   * @param contract Contrat à traiter
   * @param generationDate Date de traitement
   * @return Le resultat du calcul
   */
  public static BigDecimal withdrawnRentAmountWithoutInvoincing(ContractDTO contract, LocalDate generationDate) {
    BigDecimal result = new BigDecimal(0);

    // LNA
    BigDecimal netAgentRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getNetAgentRent());
    netAgentRent = netAgentRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // loyer jardin (-7.5%)
    BigDecimal gardenRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGardenRent()).subtract(
      BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGardenRent()).multiply(BigDecimal.valueOf(GARDEN_RENT_DISCOUNT))
        .movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION));
    gardenRent = gardenRent.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
    gardenRent = gardenRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // loyer garage
    BigDecimal garageRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGarageRent());
    garageRent = garageRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // charges prévisionelles
    BigDecimal expectedChargeCost = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getExpectedChargeCost());
    expectedChargeCost = expectedChargeCost.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // surloyer
    BigDecimal extraRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getExtraRent());
    extraRent = extraRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    result = result.add(netAgentRent).add(gardenRent).add(garageRent).add(expectedChargeCost).add(extraRent);
    return result;
  }

  /**
   * Récupère le montant de la retenue sur paie
   * @param contract Contrat à traiter
   * @param generationDate Date de traitement
   * @return Le resultat du calcul
   */
  public static BigDecimal withdrawnRentAmountWithoutInvoincing(ContractEntity contract, LocalDate generationDate) {
    BigDecimal result = new BigDecimal(0);

    // LNA
    BigDecimal netAgentRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getNetAgentRent());
    netAgentRent = netAgentRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // loyer jardin (-7.5%)
    BigDecimal gardenRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGardenRent()).subtract(
      BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGardenRent()).multiply(BigDecimal.valueOf(GARDEN_RENT_DISCOUNT))
        .movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION));
    gardenRent = gardenRent.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
    gardenRent = gardenRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // loyer garage
    BigDecimal garageRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGarageRent());
    garageRent = garageRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // charges prévisionelles
    BigDecimal expectedChargeCost = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getExpectedChargeCost());
    expectedChargeCost = expectedChargeCost.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // surloyer
    BigDecimal extraRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getExtraRent());
    extraRent = extraRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    result = result.add(netAgentRent).add(gardenRent).add(garageRent).add(expectedChargeCost).add(extraRent);
    return result;
  }

  /**
   * Renvoi le montant de la revenue sur paie à régularisé
   * @param historyContract l’historisation du contrat occupant à régularisé
   * @param startValidityDate la date de debut de validité
   * @param endValidityDate la date de fin de validité
   * @param regularizationDate la date de la régularisation
   * @return le montant de la revenue sur paie
   */
  public static BigDecimal withdrawnRentAmountForRegularizationWithoutInvoicing(HistoryContractDTO historyContract, Date startValidityDate, Date endValidityDate,
    LocalDate regularizationDate) {
    BigDecimal result = new BigDecimal(0);

    // LNA
    BigDecimal netAgentRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getNetAgentRent());
    netAgentRent = netAgentRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    // loyer jardin (-7.5%)
    BigDecimal gardenRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGardenRent()).subtract(
      BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGardenRent()).multiply(BigDecimal.valueOf(GARDEN_RENT_DISCOUNT))
        .movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION));
    gardenRent = gardenRent.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
    gardenRent = gardenRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    // loyer garage
    BigDecimal garageRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGarageRent());
    garageRent = garageRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    // charges prévisionelles
    BigDecimal expectedChargeCost = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getExpectedChargeCost());
    expectedChargeCost = expectedChargeCost.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(
      BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // surloyer
    BigDecimal extraRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getExtraRent());
    extraRent = extraRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    result = result.add(netAgentRent).add(gardenRent).add(garageRent).add(expectedChargeCost).add(extraRent);
    return result;
  }

  /**
   * Récupère le montant du loyer jardin avec l’abatemment (-7.5%)
   * @param contract Contract à traiter
   * @return Le resultat du calcul
   */
  public static BigDecimal gardenRentAmount(ContractDTO contract) {
    BigDecimal result = new BigDecimal(0);
    result = result.add(
      BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGardenRent()).subtract(
        BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGardenRent()).multiply(BigDecimal.valueOf(GARDEN_RENT_DISCOUNT))
          .movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION))).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
    return result;
  }

  /**
   * Récupère le montant du loyer jardin avec l’abatemment (-7.5%)
   * @param historyContract Historisation des contrats occupants
   * @return Le resultat du calcul
   */
  public static BigDecimal gardenRentAmount(HistoryContractDTO historyContract) {
    BigDecimal result = new BigDecimal(0);
    result = result.add(
      BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGardenRent()).subtract(
        BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGardenRent()).multiply(BigDecimal.valueOf(GARDEN_RENT_DISCOUNT))
          .movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION))).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
    return result;
  }

  /**
   * Calcule le montant du loyer prélevé avec les 8 factures ponctuelles
   * @param contract contrat traité
   * @param generationDate date du mois de traitement
   * @return Loyer prévelé brut
   */
  public static BigDecimal withdrawnRentAmountWithAllInvoicing(ContractDTO contract, LocalDate generationDate) {
    // Calcul du montant de retenue sur paie (Loyer prélevé) sans la facturation ponctuelle
    BigDecimal withdrawnRent = ContractUtils.withdrawnRentAmountWithoutInvoincing(contract, generationDate);

    // Ajout de la facturation ponctuelle sans prorata
    withdrawnRent = withdrawnRent.add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getAnnualClearanceCharges()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGarbageInvoicing()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getWaterInvoicing()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).subtract(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getInsuranceReimbursement()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).subtract(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGarbageReimbursement()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).subtract(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getHousingTaxReimbursement()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getOtherInvoicingAmount()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    return withdrawnRent;
  }

  /**
   * Calcule le montant du loyer prélevé avec les 8 factures ponctuelles
   * @param contract contrat traité
   * @param generationDate date du mois de traitement
   * @return Loyer prévelé brut
   */
  public static BigDecimal withdrawnRentAmountWithAllInvoicing(ContractEntity contract, LocalDate generationDate) {
    // Calcul du montant de retenue sur paie (Loyer prélevé) sans la facturation ponctuelle
    BigDecimal withdrawnRent = ContractUtils.withdrawnRentAmountWithoutInvoincing(contract, generationDate);

    // Ajout de la facturation ponctuelle sans prorata
    withdrawnRent = withdrawnRent.add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getAnnualClearanceCharges()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGarbageInvoicing()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getWaterInvoicing()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).subtract(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getInsuranceReimbursement()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).subtract(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGarbageReimbursement()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).subtract(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getHousingTaxReimbursement()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getOtherInvoicingAmount()))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    return withdrawnRent;
  }

  /**
   * Calcule le montant du loyer prélevé avec les 8 factures ponctuelles et les mois rétroactifs
   * @param contract contrat traité
   * @param historyContractDTOs historisations du contrat
   * @param generationDate date du mois de traitement
   * @return Loyer prévelé brut
   */
  public static BigDecimal withdrawnRentAmountWithAllInvoicingAndRegularization(ContractDTO contract, List<HistoryContractDTO> historyContractDTOs, LocalDate generationDate) {
    BigDecimal withdrawnRent = withdrawnRentAmountWithAllInvoicing(contract, generationDate);

    // On calcule le loyer prélevé avec les mois de rétroactivité
    if (contract.getRetroactivitysMonths() != 0) {
      for (HistoryContractDTO historyContractDTO : historyContractDTOs) {
        if (historyContractDTO != null) {
          withdrawnRent = withdrawnRent.add(ContractUtils.withdrawnRentAmountForRegularizationWithoutInvoicing(historyContractDTO, contract.getStartValidityDate(),
            contract.getEndValidityDate(), new LocalDate(historyContractDTO.getYear(), historyContractDTO.getMonth(), QuittancementConstants.FIRST_DAY_OF_MONTH)));
          if (contract.getRetroactivitysMonths() < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
            withdrawnRent = withdrawnRent.subtract(ContractUtils.withdrawnRentAmountForRegularizationWithoutInvoicing(historyContractDTO, contract.getStartValidityDate(),
              historyContractDTO.getEndValidityDate(), new LocalDate(historyContractDTO.getYear(), historyContractDTO.getMonth(), QuittancementConstants.FIRST_DAY_OF_MONTH)));
          }
        }
      }
    }
    return withdrawnRent;
  }

  /**
   * Calcul du montant net de loyer mensuel
   * @param contract contrat traité
   * @param generationDate date du mois traité
   * @return Résultat du calcul
   */
  public static BigDecimal netMensualRentBenefit(ContractDTO contract, LocalDate generationDate) {
    BigDecimal result = new BigDecimal(0);

    // LNA
    BigDecimal netAgentRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getNetAgentRent());
    netAgentRent = netAgentRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // loyer jardin (-7.5%)
    BigDecimal gardenRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGardenRent()).subtract(
      BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGardenRent()).multiply(BigDecimal.valueOf(GARDEN_RENT_DISCOUNT))
        .movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION));
    gardenRent = gardenRent.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
    gardenRent = gardenRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // loyer garage
    BigDecimal garageRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getGarageRent());
    garageRent = garageRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    // surloyer
    BigDecimal extraRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getExtraRent());
    extraRent = extraRent.multiply(ContractUtils.getProportionsDays(contract, generationDate)).setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

    result = result.add(netAgentRent).add(gardenRent).add(garageRent).add(extraRent);
    return result;
  }

  /**
   * Calcul le loyer prélevé net mensuel
   * @param historyContract Un contrat historisé
   * @param startValidityDate la date de début de validité
   * @param endValidityDate la date de fin de validité
   * @param regularizationDate la date du mois à régularisé
   * @return le loyer prélevé
   */
  public static BigDecimal netMensualRentRegularizationBenefit(HistoryContractDTO historyContract, Date startValidityDate, Date endValidityDate, LocalDate regularizationDate) {
    BigDecimal result = new BigDecimal(0);

    // LNA
    BigDecimal netAgentRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getNetAgentRent());
    netAgentRent = netAgentRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    // loyer jardin (-7.5%)
    BigDecimal gardenRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGardenRent()).subtract(
      BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGardenRent()).multiply(BigDecimal.valueOf(GARDEN_RENT_DISCOUNT))
        .movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION));
    gardenRent = gardenRent.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
    gardenRent = gardenRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    // loyer garage
    BigDecimal garageRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getGarageRent());
    garageRent = garageRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    // surloyer
    BigDecimal extraRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getExtraRent());
    extraRent = extraRent.multiply(ContractUtils.getProportionsDays(startValidityDate, endValidityDate, regularizationDate)).setScale(BigDecimalUtils.SCALE_PRICE,
      BigDecimal.ROUND_HALF_EVEN);

    result = result.add(netAgentRent).add(gardenRent).add(garageRent).add(extraRent);
    return result;
  }

  /**
   * Permet de calculer le pécule de fin d’occupation d’un mois pour un contrat
   * @param contract contrat à traiter
   * @param generationDate date de génération
   * @return montant du pécule
   */
  public static BigDecimal terminationSavingMonth(ContractDTO contract, LocalDate generationDate) {
    // ratio du nombre de jours du contrat par rapport au mois
    BigDecimal proportionDays = getProportionsDays(contract, generationDate);
    return terminationSavingMonth(proportionDays, contract.getGardenRent(), contract.getNetAgentRent());
  }

  /**
   * Permet de calculer le pécule de fin d’occupation d’un mois pour un contrat
   * @param contract contrat à traiter
   * @param historyContract Une historisation de contrat
   * @param generationDate date de génération
   * @return montant du pécule
   */
  public static BigDecimal terminationSavingMonthForRegularization(ContractDTO contract, HistoryContractDTO historyContract, LocalDate generationDate) {
    // ratio du nombre de jours du contrat par rapport au mois
    BigDecimal proportionDays = getProportionsDays(contract, generationDate);
    return terminationSavingMonth(proportionDays, historyContract.getGardenRent(), historyContract.getNetAgentRent());
  }

  /**
   * Permet de calculer le pécule de fin d’occupation d’un mois pour un contrat
   * @param contract contrat à traiter
   * @param generationDate date de génération
   * @return montant du pécule
   */
  public static BigDecimal terminationSavingMonth(ContractInputDTO contract, LocalDate generationDate) {
    // ratio du nombre de jours du contrat par rapport au mois
    BigDecimal proportionDays = getProportionsDays(contract, generationDate);
    return terminationSavingMonth(proportionDays, contract.getGardenRent(), contract.getNetAgentRent());
  }

  /**
   * Permet de calculer le pécule de fin d’occupation d’un mois pour une entité de contrat occupant
   * @param contract entité de contrat à traiter
   * @param generationDate date de génération
   * @return montant du pécule
   */
  public static BigDecimal terminationSavingMonth(ContractEntity contract, LocalDate generationDate) {
    // ratio du nombre de jours du contrat par rapport au mois
    BigDecimal proportionDays = getProportionsDays(contract, generationDate);
    return terminationSavingMonth(proportionDays, contract.getGardenRent(), contract.getNetAgentRent());
  }

  /**
   * Permet de calculer le pécule de fin d’occupation d’un mois
   * @param proportionDays proportion du nombre de jour dans le mois
   * @param gardenRent montant du loyer jardin
   * @param netAgentRent montant du loyer net agent
   * @return montant du pécule
   */
  private static BigDecimal terminationSavingMonth(BigDecimal proportionDays, BigDecimal gardenRent, BigDecimal netAgentRent) {
    BigDecimal calculatedGardenRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(gardenRent)
      .subtract(BigDecimalUtils.returnZeroFromBigDecimalIfNull(gardenRent).multiply(BigDecimal.valueOf(GARDEN_RENT_DISCOUNT)).movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION))
      .setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
    // Abbattement de 7.5%
    BigDecimal ratioSavings = BigDecimal.valueOf(GARDEN_RENT_DISCOUNT).movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION);
    BigDecimal savingAmount = new BigDecimal(0);
    if (calculatedGardenRent != null && netAgentRent != null) {
      savingAmount = calculatedGardenRent.add(netAgentRent);
    } else if (netAgentRent == null && calculatedGardenRent != null) {
      savingAmount = calculatedGardenRent;
    } else if (netAgentRent != null) {
      savingAmount = netAgentRent;
    }

    // prise en compte du ratio d'occupation
    savingAmount = savingAmount.multiply(proportionDays);

    // prise en compte du ratio du montant du loyer
    savingAmount = savingAmount.multiply(ratioSavings);

    return savingAmount;
  }

  /**
   * Récupère le prorata du nombre de jours par rapport au mois traité pour un contrat
   * @param contract le contrat occupant à traiter
   * @param generationDate la date de génération
   * @return la valeur du prorata compris entre 0 et 1
   */
  public static BigDecimal getProportionsDays(ContractDTO contract, LocalDate generationDate) {
    return getProportionsDays(contract.getStartValidityDate(), contract.getEndValidityDate(), generationDate);
  }

  /**
   * Récupère le prorata du nombre de jours par rapport au mois traité pour un contrat
   * @param contract le contrat occupant à traiter
   * @param generationDate la date de génération
   * @return la valeur du prorata compris entre 0 et 1
   */
  public static BigDecimal getProportionsDays(ContractEntity contract, LocalDate generationDate) {
    return getProportionsDays(contract.getStartValidityDate(), contract.getEndValidityDate(), generationDate);
  }

  /**
   * Récupère le prorata du nombre de jours par rapport au mois traité pour un contrat historisé
   * @param historyContract le contrat occupant historisé
   * @param contract le contrat occupant
   * @param generationDate la date de génération
   * @return la valeur du prorata compris entre 0 et 1
   */
  public static BigDecimal getProportionsDays(HistoryContractDTO historyContract, ContractDTO contract, LocalDate generationDate) {
    return getProportionsDays(contract.getStartValidityDate(), historyContract.getEndValidityDate(), generationDate);
  }

  /**
   * Récupère le prorata du nombre de jours par rapport au mois traité pour un contrat
   * @param contract le contrat occupant à traiter
   * @param generationDate la date de génération
   * @return la valeur du prorata compris entre 0 et 1
   */
  public static BigDecimal getProportionsDays(ContractInputDTO contract, LocalDate generationDate) {
    return getProportionsDays(contract.getStartValidityDate(), contract.getEndValidityDate(), generationDate);
  }

  /**
   * Récupère le prorata du nombre de jours par rapport au mois traité
   * @param startValidityDate date de début
   * @param endValidityDate date de fin
   * @param generationDate mois à prendre en compte
   * @return la valeur du prorata compris entre 0 et 1
   */
  public static BigDecimal getProportionsDays(Date startValidityDate, Date endValidityDate, LocalDate generationDate) {
    // date de début du contrat
    LocalDate startValidity = new LocalDate(startValidityDate);
    // date de fin du contrat
    LocalDate endValidity = null;
    if (endValidityDate != null) {
      endValidity = new LocalDate(endValidityDate);
    }

    // date de début et date de fin du cycle
    LocalDate startCycle = DateTimeUtils.getFirstDayOfCycle(generationDate, DateTimeUtils.MONTHLY_CYCLE);
    LocalDate endCycle = DateTimeUtils.getLastDayOfCycle(generationDate, DateTimeUtils.MONTHLY_CYCLE);

    // ratio du nombre de jours du contrat par rapport au nombre de jours du cycle
    return new BigDecimal(DateTimeUtils.getProportionOfDaysOnCycle(startCycle, endCycle, startValidity, endValidity));
  }

  /**
   * Applique sur un montant le prorata du nombre de jours occupés sur le mois traité
   * @param amount montant manipulé
   * @param contract contrat correspondant
   * @param generationDate date du mois de traitement
   * @return le montant proraté
   */
  public static BigDecimal getProrataOfAmount(BigDecimal amount, ContractDTO contract, LocalDate generationDate) {
    if (null != amount) {
      BigDecimal newAmount = amount;
      newAmount = newAmount.multiply(ContractUtils.getProportionsDays(contract, generationDate));
      newAmount = newAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
      return newAmount;
    } else {
      return new BigDecimal(0);
    }
  }

  /**
   * Applique sur un montant le prorata du nombre de jours occupés sur le mois traité
   * @param amount montant manipulé
   * @param contract contrat correspondant
   * @param historyContract contrat historisé correspondant
   * @param generationDate date du mois de traitement
   * @return le montant proraté
   */
  public static BigDecimal getProrataOfAmount(BigDecimal amount, ContractDTO contract, HistoryContractDTO historyContract, LocalDate generationDate) {
    if (null != amount) {
      BigDecimal newAmount = amount;
      newAmount = newAmount.multiply(ContractUtils.getProportionsDays(historyContract, contract, generationDate));
      newAmount = newAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
      return newAmount;
    } else {
      return new BigDecimal(0);
    }
  }

  /**
   * Permet d'avoir le prorata d'un montant en fonction de deux dates
   * @param amount le montant
   * @param startDate la date de début du contrat occupant
   * @param endDate la date de fin du contrat occupant
   * @param generationDate la date de génération
   * @return le montant proraté
   */
  public static BigDecimal getProrataOfAmountForRegularization(BigDecimal amount, Date startDate, Date endDate, LocalDate generationDate) {
    if (null != amount) {
      BigDecimal newAmount = amount;
      newAmount = newAmount.multiply(ContractUtils.getProportionsDays(startDate, endDate, generationDate));
      newAmount = newAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
      return newAmount;
    } else {
      return new BigDecimal(0);
    }
  }

  /**
   * Permet de récupérer la date de début du contrat dans le mois
   * @param contract contrat traité
   * @param generationDate date en cours
   * @return date de début du contrat dans le mois
   */
  public static LocalDate getFirstDayOfMonth(ContractDTO contract, LocalDate generationDate) {
    LocalDate firstDayOfGenerationDate = generationDate.dayOfMonth().withMinimumValue();
    LocalDate firstDayOfContract = new LocalDate(contract.getStartValidityDate());

    if (firstDayOfContract.isBefore(firstDayOfGenerationDate)) {
      return firstDayOfGenerationDate;
    } else {
      return firstDayOfContract;
    }
  }

  /**
   * Permet de récupérer la date de fin du contrat dans le mois
   * @param contract contrat traité
   * @param generationDate date en cours
   * @return date de fin du contrat dans le mois
   */
  public static LocalDate getLastDayOfMonth(ContractDTO contract, LocalDate generationDate) {
    LocalDate firstDayOfGenerationDate = generationDate.dayOfMonth().withMinimumValue();
    LocalDate lastDayOfGenerationDate = generationDate.dayOfMonth().withMaximumValue();
    if (contract.getEndValidityDate() == null) {
      return lastDayOfGenerationDate;
    }

    LocalDate lastDayOfContract = new LocalDate(contract.getEndValidityDate());
    if (lastDayOfContract.isBefore(firstDayOfGenerationDate) || lastDayOfContract.isAfter(lastDayOfGenerationDate)) {
      return lastDayOfGenerationDate;
    } else {
      return lastDayOfContract;
    }
  }

  /**
   * Permet de savoir si un contrat contient un champ de facturation ponctuelle
   * @param contract contrat traité
   * @return true si un des champs de facturation ponctuelle est différent de 0
   */
  public static Boolean hasSporadicallyInvoicing(ContractDTO contract) {
    Set<Boolean> sporadicallysInvoicing = new HashSet<Boolean>();

    sporadicallysInvoicing.add(hasWaterInvoicing(contract));
    sporadicallysInvoicing.add(hasGarbageInvoicing(contract));
    sporadicallysInvoicing.add(hasInsuranceReimbursement(contract));
    sporadicallysInvoicing.add(hasHousingTaxReimbursement(contract));
    sporadicallysInvoicing.add(hasGarbageReimbursement(contract));
    sporadicallysInvoicing.add(hasAnnualClearanceCharges(contract));
    sporadicallysInvoicing.add(hasOtherInvoicing(contract));

    return sporadicallysInvoicing.contains(true);
  }

  /**
   * Permet de savoir si un contrat contient une facturation de consommation d’eau
   * @param contract contrat traité
   * @return true si la facturation de consommation d’eau est différente de 0
   */
  private static Boolean hasWaterInvoicing(ContractDTO contract) {
    Boolean existingWaterInvoicing = false;

    /* Facturation consommation eau */
    BigDecimal waterInvoicing = contract.getWaterInvoicing();
    if (null != waterInvoicing && waterInvoicing.compareTo(BigDecimal.ZERO) != 0) {
      existingWaterInvoicing = true;
    }

    return existingWaterInvoicing;
  }

  /**
   * Permet de savoir si un contrat contient une facturation des ordures ménagères
   * @param contract contrat traité
   * @return true si la facturation des ordures ménagères est différente de 0
   */
  private static Boolean hasGarbageInvoicing(ContractDTO contract) {
    Boolean existingGarbageInvoicing = false;

    /* Facturation ordures ménagères */
    BigDecimal garbageInvoicing = contract.getGarbageInvoicing();
    if (null != garbageInvoicing && garbageInvoicing.compareTo(BigDecimal.ZERO) != 0) {
      existingGarbageInvoicing = true;
    }

    return existingGarbageInvoicing;
  }

  /**
   * Permet de savoir si un contrat contient un remboursement assurance
   * @param contract contrat traité
   * @return true si le remboursement assurance est différente de 0
   */
  private static Boolean hasInsuranceReimbursement(ContractDTO contract) {
    Boolean existingInsuranceReimbursement = false;

    /* Remboursement assurance */
    BigDecimal insuranceReimbursement = contract.getInsuranceReimbursement();
    if (null != insuranceReimbursement && insuranceReimbursement.compareTo(BigDecimal.ZERO) != 0) {
      existingInsuranceReimbursement = true;
    }

    return existingInsuranceReimbursement;
  }

  /**
   * Permet de savoir si un contrat contient un remboursement ordures ménagères
   * @param contract contrat traité
   * @return true si le remboursement ordures ménagères est différente de 0
   */
  private static Boolean hasGarbageReimbursement(ContractDTO contract) {
    Boolean existingGarbageReimbursement = false;

    /* Remboursement ordures ménagères */
    BigDecimal garbageReimbursement = contract.getGarbageReimbursement();
    if (null != garbageReimbursement && garbageReimbursement.compareTo(BigDecimal.ZERO) != 0) {
      existingGarbageReimbursement = true;
    }

    return existingGarbageReimbursement;
  }

  /**
   * Permet de savoir si un contrat contient un remboursement habitation
   * @param contract contrat traité
   * @return true si le remboursement habitation est différente de 0
   */
  private static Boolean hasHousingTaxReimbursement(ContractDTO contract) {
    Boolean existingHousingTaxReimbursement = false;

    /* Remboursement taxe habitation */
    BigDecimal housingTaxReimbursement = contract.getHousingTaxReimbursement();
    if (null != housingTaxReimbursement && housingTaxReimbursement.compareTo(BigDecimal.ZERO) != 0) {
      existingHousingTaxReimbursement = true;
    }

    return existingHousingTaxReimbursement;
  }

  /**
   * Permet de savoir si un contrat contient un apurement annuel charges locatives
   * @param contract contrat traité
   * @return true si l’apurement annuel charges locatives est différente de 0
   */
  private static Boolean hasAnnualClearanceCharges(ContractDTO contract) {
    Boolean existingAnnualClearanceCharges = false;

    /* Apurement annuel charges locatives */
    BigDecimal annualClearanceCharges = contract.getAnnualClearanceCharges();
    if (null != annualClearanceCharges && annualClearanceCharges.compareTo(BigDecimal.ZERO) != 0) {
      existingAnnualClearanceCharges = true;
    }

    return existingAnnualClearanceCharges;
  }

  /**
   * Permet de savoir si un contrat contient une autre facturation
   * @param contract contrat traité
   * @return true si l’autre facturation est différente de 0
   */
  private static Boolean hasOtherInvoicing(ContractDTO contract) {
    Boolean existingOtherInvoicing = false;

    /* Autre facturation */
    String otherInvoicingLabel = contract.getOtherInvoicingLabel();
    BigDecimal otherInvoicingAmount = contract.getOtherInvoicingAmount();
    if (null != otherInvoicingLabel && StringUtils.isNotEmpty(otherInvoicingLabel) && null != otherInvoicingAmount && otherInvoicingAmount.compareTo(BigDecimal.ZERO) != 0) {
      existingOtherInvoicing = true;
    }

    return existingOtherInvoicing;
  }

  /**
   * Permet de savoir si un contrat est en cours
   * @param contract contrat traité
   * @return true si le contrat est en cours
   */
  public static Boolean isInProgress(ContractDTO contract) {
    LocalDate now = new LocalDate();
    LocalDate firstDay = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(now).toLocalDate();
    LocalDate lastDay = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toLocalDate();

    LocalDate startValidityDate = new LocalDate(contract.getStartValidityDate());

    LocalDate endValidityDate = null;
    if (contract.getEndValidityDate() != null) {
      endValidityDate = new LocalDate(contract.getEndValidityDate());
    }

    return startValidityDate.isBefore(lastDay) && (endValidityDate == null || endValidityDate.isAfter(firstDay));
  }

  /**
   * Permet de calculer le loyer net agent d'un contrat occupant au prorata
   * @param contract un contrat occupant
   * @param generationDate la date de génération
   * @return retourne le loyer net agent proraté
   */
  public static BigDecimal netAgentRentWithProrata(ContractDTO contract, LocalDate generationDate) {
    BigDecimal netAgentRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getNetAgentRent());
    return getProrataOfAmount(netAgentRent, contract, generationDate);
  }

  /**
   * Permet de calculer le surloyer d'un contrat occupant au prorata
   * @param contract un contrat occupant
   * @param generationDate la date de génération
   * @return retourne le surloyer proraté
   */
  public static BigDecimal extraRentRentWithProrata(ContractDTO contract, LocalDate generationDate) {
    BigDecimal extraRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contract.getExtraRent());
    return getProrataOfAmount(extraRent, contract, generationDate);
  }

  /**
   * Permet de calculer le loyer prélevé cumulé à ajouter pour le mois en cours au prorata
   * @param contract un contrat occupant
   * @param generationDate la date de génération
   * @return retourne le loyer prélevé cumulé à ajouter pour le mois en cours au prorata
   */
  public static BigDecimal addedWithdrawnRentWithProrata(ContractDTO contract, LocalDate generationDate) {
    BigDecimal addedWithdrawnRent = netAgentRentWithProrata(contract, generationDate);
    addedWithdrawnRent = addedWithdrawnRent.add(extraRentRentWithProrata(contract, generationDate));
    return addedWithdrawnRent;
  }

  /**
   * Permet de calculer le loyer net agent historisé d'une historisation de contrat occupant au prorata
   * @param contract un contrat occupant
   * @param historyContract une historisation de contrat occupant
   * @param regularisationDate la date de régularisation
   * @return retourne le loyer net agent proraté
   */
  public static BigDecimal netAgentRentWithProrataForRegularization(ContractDTO contract, HistoryContractDTO historyContract, LocalDate regularisationDate) {
    BigDecimal netAgentRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getNetAgentRent());
    return getProrataOfAmountForRegularization(netAgentRent, contract.getStartValidityDate(), contract.getEndValidityDate(), regularisationDate);
  }

  /**
   * Permet de calculer le surloyer historisé d'une historisation de contrat occupant au prorata
   * @param contract un contrat occupant
   * @param historyContract une historisation de contrat occupant
   * @param regularisationDate la date de régularisation
   * @return retourne le surloyer proraté
   */
  public static BigDecimal extraRentWithProrataForRegularization(ContractDTO contract, HistoryContractDTO historyContract, LocalDate regularisationDate) {
    BigDecimal extraRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContract.getExtraRent());
    return getProrataOfAmountForRegularization(extraRent, contract.getStartValidityDate(), contract.getEndValidityDate(), regularisationDate);
  }

  /**
   * Permet de calculer le loyer prélevé cumulé à ajouter pour une historisation au prorata
   * @param contract un contrat occupant
   * @param historyContract une historisation de contrat occupant
   * @param regularisationDate la date de régularisation
   * @return retourne le loyer prélevé cumulé à ajouter pour une historisation au prorata
   */
  public static BigDecimal addedWithdrawnRentWithProrataForRegularization(ContractDTO contract, HistoryContractDTO historyContract, LocalDate regularisationDate) {
    BigDecimal addedWithdrawnRent = netAgentRentWithProrataForRegularization(contract, historyContract, regularisationDate);
    addedWithdrawnRent = addedWithdrawnRent.add(extraRentWithProrataForRegularization(contract, historyContract, regularisationDate));
    return addedWithdrawnRent;
  }

  /**
   * Permet de verifier si la valeur de deux objets est differentes et d'éviter les NPE
   * @param obj1 le premier objet à comparer
   * @param obj2 le nouvel objet à comparer
   * @return true si l'objet est different
   */
  public static Boolean isValueChanged(Object obj1, Object obj2) {
    if (obj1 != null && obj2 != null) {
      return !obj1.equals(obj2);
    } else {
      return obj1 == null && obj2 != null || obj1 != null && obj2 == null;
    }
  }

  /**
   * Récupère le détail du calcul pour le coefficient n/N
   * @param contractDTO contract utilisé pour le calcul
   * @return le détail du calcul
   */
  public static String getNnCalculation(ContractDTO contractDTO) {
    Integer householdSize = contractDTO.getHouseholdSize();
    Integer roomCount = contractDTO.getHousing().getRoomCount();
    Boolean typeTenant = contractDTO.getTenant().getManagerial();
    String rentTypology = contractDTO.getRentTypology().getTechnicalCode();

    Integer dividend = householdSize;
    if (typeTenant != null && typeTenant) {
      dividend = dividend + 1;
    }

    Integer divisor = roomCount;
    if (RENT_CALL_CODE.equals(rentTypology)) {
      divisor = divisor + 1;
    }

    return dividend + CHARACTER_DIVISION + divisor;
  }

  /**
   * Permet de savoir combien de mois sont rétroactifs en création
   * @param startValidityDate date de début de validité
   * @param generationIsAlreadyPast flag pour savoir si la génération des fichiers est passée
   * @return nombre de mois rétroactifs
   */
  public static Integer calculateTheNumberOfMonthsOfRetroactivity(Date startValidityDate, Boolean generationIsAlreadyPast) {

    // le couple mois/année de la date de début de validité permet de calculer la différence de mois avec la date d’aujourd’hui
    YearMonth startValidityYearMonth = YearMonth.fromDateFields(startValidityDate);
    Months months = Months.monthsBetween(startValidityYearMonth, YearMonth.now());

    if (generationIsAlreadyPast) {
      months = months.plus(1);
    }

    // le nombre de mois de rétroactivité ne peut être supérieur à 12 mois
    if (months.getMonths() > DateTimeUtils.MONTHS_IN_A_YEAR) {
      months = Months.months(DateTimeUtils.MONTHS_IN_A_YEAR);
    }

    return months.getMonths();
  }

  /**
   * Permet de mapper une entity en contractDTO avec le builder
   * @param entity l'entity à mapper
   * @param mapper le mapper de Dozer
   * @return le contract DTO mappé
   */
  public static ContractDTO mapToContractDTO(ContractEntity entity, Mapper mapper) {
    TerminationDTO terminationDTO = null;
    AgencyDTO agencyDTO = null;
    TenantDTO tenantDTO = mapper.map(entity.getTenant(), TenantDTO.class);
    HousingDTO housingDTO = mapper.map(entity.getHousing(), HousingDTO.class);
    FieldOfActivityDTO fieldOfActivityDTO = mapper.map(entity.getFieldOfActivity(), FieldOfActivityDTO.class);
    CostCenterDTO costCenterDTO = mapper.map(entity.getCostCenter(), CostCenterDTO.class);
    RentTypologyDTO rentTypologyDTO = mapper.map(entity.getRentTypology(), RentTypologyDTO.class);
    PaymentMethodDTO paymentMethodDTO = mapper.map(entity.getPaymentMethod(), PaymentMethodDTO.class);
    if (entity.getTermination() != null) {
      terminationDTO = mapper.map(entity.getTermination(), TerminationDTO.class);
    }
    if (entity.getFixedAgency() != null) {
      agencyDTO = mapper.map(entity.getFixedAgency(), AgencyDTO.class);
    }

    return new ContractDTO.ContractBuilder(entity.getId(), entity.getContractReference(), entity.getStartValidityDate(), entity.getPlainAddedWithdrawnRent(),
      entity.getTerminationSavings(), tenantDTO, housingDTO, fieldOfActivityDTO, costCenterDTO, rentTypologyDTO, paymentMethodDTO, terminationDTO, entity.getHouseholdSize(),
      entity.getRetroactivitysMonths()).lastWithdrawnDate(entity.getLastWithdrawnDate()).lastSavingDate(entity.getLastSavingDate()).signature(entity.getSignature())
      .endValidityDate(entity.getEndValidityDate()).marketRentPrice(entity.getMarketRentPrice()).rentPriceLimit(entity.getRentPriceLimit()).garageRent(entity.getGarageRent())
      .gardenRent(entity.getGardenRent()).extraRent(entity.getExtraRent()).expectedChargeCost(entity.getExpectedChargeCost())
      .insuranceReimbursement(entity.getInsuranceReimbursement()).housingTaxReimbursement(entity.getHousingTaxReimbursement())
      .garbageReimbursement(entity.getGarbageReimbursement()).otherInvoicingLabel(entity.getOtherInvoicingLabel()).otherInvoicingAmount(entity.getOtherInvoicingAmount())
      .annualClearanceCharges(entity.getAnnualClearanceCharges()).garbageInvoicing(entity.getGarbageInvoicing()).waterInvoicing(entity.getWaterInvoicing())
      .realEstateRentalValue(entity.getRealEstateRentalValue()).terminationSavingAmount(entity.getTerminationSavingAmount()).revisedSurfaceArea(entity.getRevisedSurfaceArea())
      .revisedSurfaceAreaRent(entity.getRevisedSurfaceAreaRent()).nNCoef(entity.getnNCoef()).netAgentRent(entity.getNetAgentRent())
      .shortTermContractDiscount(entity.getShortTermContractDiscount()).lopRent(entity.getLopRent()).withdrawnRent(entity.getWithdrawnRent()).benefit(entity.getBenefit())
      .addedWithdrawnRent(entity.getAddedWithdrawnRent()).plainTerminationSavingAmount(entity.getPlainTerminationSavingAmount()).fixedWithdrawnRent(entity.getFixedWithdrawnRent())
      .terminationSavingsPayment(entity.getTerminationSavingsPayment()).terminationSavingsPaymentDate(entity.getTerminationSavingsPaymentDate())
      .fixedNNCoef(entity.getFixedNNCoef()).fixedAgency(agencyDTO).fixedBenefit(entity.getFixedBenefit()).fixedLopRent(entity.getFixedLopRent())
      .fixedRevisedSurfaceAreaRent(entity.getFixedRevisedSurfaceAreaRent()).fixedRevisedSurfaceArea(entity.getFixedRevisedSurfaceArea()).closedContract(entity.getClosedContract())
      .insuranceCertificateEndDate(entity.getInsuranceCertificateEndDate())
      .build();
  }
}
