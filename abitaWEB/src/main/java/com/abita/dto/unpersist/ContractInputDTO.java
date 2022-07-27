/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto.unpersist;

import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dto.AgencyDTO;
import com.abita.dto.CostCenterDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.HousingDTO;
import com.abita.dto.PaymentMethodDTO;
import com.abita.dto.RentTypologyDTO;
import com.abita.dto.TenantDTO;
import com.abita.dto.TerminationDTO;
import com.abita.web.shared.ConstantsWEB;
import com.dto.AbstractDTO;
import com.web.common.validators.NullableDigits;
import com.web.common.validators.NullableMin;
import org.apache.commons.lang.BooleanUtils;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO de Contrat occupant pour stocker la saisie utilisateur
 * @author
 */
public class ContractInputDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 1947077027027096195L;

  /**
   * PROPRIETES
   */

  /** Référence Contrat occupant */
  @Pattern(regexp = "^[0-9]{5}\\.[0-9]{3}\\.[0-9]{3}$", message = "{contract.creation.error.format.contract}")
  @NotNull(message = "{contract.creation.error.mandatory.contract}")
  private String contractReference;

  /** Contrat signé */
  private Boolean signature;

  /** Date de début de validité */
  @NotNull(message = "{contract.creation.error.mandatory.startvalidity}")
  private Date startValidityDate;

  /** Date de fin de validité */
  private Date endValidityDate;

  /**
   * Loyer marché
   * Règles de validation : {@link #getMarketRentPriceAsString}
   */
  private BigDecimal marketRentPrice;

  /**
   * Loyer plafond
   * Règles de validation : {@link #getRentPriceLimitAsString}
   */
  private BigDecimal rentPriceLimit;

  /**
   * Loyer garage
   * Règles de validation : {@link #getGarageRentAsString}
   */
  private BigDecimal garageRent;

  /**
   * Loyer jardin
   * Règles de validation : {@link #getGardenRentAsString}
   */
  private BigDecimal gardenRent;

  /**
   * Surloyer
   * Règles de validation : {@link #getExtraRentAsString}
   */
  private BigDecimal extraRent;

  /**
   * Charges prévisionnelles
   * Règles de validation : {@link #getExpectedChargeCostAsString}
   */
  private BigDecimal expectedChargeCost;

  /**
   * Facturation de la consommation d’eau
   * Règles de validation : {@link #getWaterInvoicingAsString}
   */
  private BigDecimal waterInvoicing;

  /**
   * Facturation des ordures ménagères
   * Règles de validation : {@link #getGarbageInvoicingAsString}
   */
  private BigDecimal garbageInvoicing;

  /**
   * Remboursement assurance
   * Règles de validation : {@link #getInsuranceReimbursementAsString}
   */
  private BigDecimal insuranceReimbursement;

  /** Date limite de validité de l’attestation d’assurance */
  private Date insuranceCertificateEndDate;

  /**
   * Remboursement taxe habitation
   * Règles de validation : {@link #getHousingTaxReimbursementAsString}
   */
  private BigDecimal housingTaxReimbursement;

  /**
   * Remboursement taxe ordures ménagères
   * Règles de validation : {@link #getGarbageReimbursementAsString}
   */
  private BigDecimal garbageReimbursement;

  /**
   * Apurement annuel des charges
   * Règles de validation : {@link #getAnnualClearanceChargesAsString}
   */
  private BigDecimal annualClearanceCharges;

  /** Autre (label) */
  private String otherInvoicingLabel;

  /**
   * Autre (montant)
   * Règles de validation : {@link #getOtherInvoicingAmountAsString}
   */
  private BigDecimal otherInvoicingAmount;

  /** Loyer prélevé cumulé */
  @NotNull(message = "{contract.creation.error.mandatory.addedwithdrawnrent}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.addedwithdrawnrent}")
  private BigDecimal plainAddedWithdrawnRent;

  /** Loyer prélevé cumulé calculé */
  @NotNull(message = "{contract.creation.error.mandatory.addedwithdrawnrent}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.addedwithdrawnrent}")
  private BigDecimal plainTempAddedWithdrawnRent;

  /** Date de dernier cumule de loyer prélevé */
  private Date lastWithdrawnDate;

  /** Pécule de fin d’occupation */
  @NotNull(message = "{contract.creation.error.mandatory.terminationsavings}")
  private Boolean terminationSavings;

  /** Montant du pécule de fin d'occupation */
  private BigDecimal terminationSavingAmount;

  /** Date de dernier cumule de pécule de fin d’occupation */
  private Date lastSavingDate;

  /** Montant de la valeur locative foncière */
  @NotNull(message = "{contract.creation.error.mandatory.realestaterentalvalue}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.realestaterentalvalue}")
  private BigDecimal realEstateRentalValue;

  /** Logement */
  @NotNull(message = "{contract.creation.error.nonexistent.housing}")
  private HousingDTO housing;

  /** Occupant */
  @NotNull(message = "{contract.creation.error.nonexistent.tenant}")
  private TenantDTO tenant;

  /** Typologie de loyer */
  @NotNull(message = "{contract.creation.error.mandatory.renttypology}")
  private RentTypologyDTO rentTypology;

  /** Motif de résiliation */
  private TerminationDTO termination;

  /** Domaine d’activité */
  @NotNull(message = "{contract.creation.error.mandatory.field}")
  private FieldOfActivityDTO fieldOfActivity;

  /** Centre coût */
  @NotNull(message = "{contract.creation.error.mandatory.costcenter}")
  private CostCenterDTO costCenter;

  /** Mode paiement */
  @NotNull(message = "{contract.creation.error.mandatory.paymentmethod}")
  private PaymentMethodDTO paymentMethod;

  /** Montant du pécule de fin fixe */
  private BigDecimal plainTerminationSavingAmount;

  /** Montant du pécule de fin fixe calculé */
  private BigDecimal plainTempTerminationSavingAmount;

  /** Nombre d'occupants */
  @NotNull(message = "{contract.creation.error.household}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_3, fraction = ConstantsWEB.DECIMAL_PART_SIZE_NONE, message = "{contract.creation.error.household.integer}")
  @Min(value = 0, message = "{contract.creation.error.household.integer}")
  private Integer householdSize;

  /** Le loyer réellement prélevé */
  private BigDecimal fixedWithdrawnRent;

  /** Règlement du pécule de fin d’occupation */
  @NotNull(message = "{contract.creation.error.mandatory.terminationsavingspayment}")
  private Boolean terminationSavingsPayment;

  /** Date du règlement du pécule de fin d’occupation */
  @NotNull(message = "{contract.creation.error.mandatory.terminationsavingspaymentdate}")
  private Date terminationSavingsPaymentDate;

  /** Coeficient n/N si contrat clos (champ fixe) */
  private BigDecimal fixedNNCoef;
  /** Agence si contrat clos (champ fixe) */
  private AgencyDTO fixedAgency;
  /** Avantage en nature si contrat clos (champ fixe) */
  private BigDecimal fixedBenefit;
  /** Loyer écrêté si contrat clos (champ fixe) */
  private BigDecimal fixedLopRent;
  /** Loyer surface corrigée si contrat clos (champ fixe) */
  private BigDecimal fixedRevisedSurfaceAreaRent;
  /** Surface corrigée si contrat clos (champ fixe) */
  private BigDecimal fixedRevisedSurfaceArea;
  /** Contrat clos (utilisation des valeurs "fixed") */
  private Boolean closedContract;

  /**
   * VARIABLES CALCULEES
   */
  /** Surface corrigée */
  private BigDecimal revisedSurfaceArea;

  /** Loyer surface corrigée */
  private BigDecimal revisedSurfaceAreaRent;

  /** Coefficient n/N */
  private BigDecimal nNCoef;

  /** Détail du calcul du coefficient n/N */
  private String nNCoefDetail;

  /** Loyer net agent */
  private BigDecimal netAgentRent;

  /** Abattement de précarité */
  private BigDecimal shortTermContractDiscount;

  /** Loyer écrêté */
  private BigDecimal lopRent;

  /** Loyer prélevé */
  private BigDecimal withdrawnRent;

  /** Avantage en nature */
  private BigDecimal benefit;

  /** Loyer prélevé cumulé */
  private BigDecimal addedWithdrawnRent;

  /** Nombre de mois de rétroactivité à effectuer */
  private Integer retroactivitysMonths;

  /**
   * GETTER/SETTER
   */

  /**
   * @return the signature
   */
  public Boolean getSignature() {
    return signature;
  }

  /**
   * @param signature the signature to set
   */
  public void setSignature(Boolean signature) {
    this.signature = signature;
  }

  /**
   * @return the startValidityDate
   */
  public Date getStartValidityDate() {
    return DateTimeUtils.clone(startValidityDate);
  }

  /**
   * @param startValidityDate the startValidityDate to set
   */
  public void setStartValidityDate(Date startValidityDate) {
    this.startValidityDate = DateTimeUtils.clone(startValidityDate);
  }

  /**
   * @return the endValidityDate
   */
  public Date getEndValidityDate() {
    return DateTimeUtils.clone(endValidityDate);
  }

  /**
   * @param endValidityDate the endValidityDate to set
   */
  public void setEndValidityDate(Date endValidityDate) {
    this.endValidityDate = DateTimeUtils.clone(endValidityDate);
  }

  /**
   * @return the marketRentPrice
   */
  public BigDecimal getMarketRentPrice() {
    return marketRentPrice;
  }

  /**
   * @param marketRentPrice the marketRentPrice to set
   */
  public void setMarketRentPrice(BigDecimal marketRentPrice) {
    this.marketRentPrice = marketRentPrice;
  }

  /**
   * @return the marketRentPrice
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.marketrentprice}")
  public String getMarketRentPriceAsString() {
    return BigDecimalUtils.bigDecimalToString(marketRentPrice);
  }

  /**
   * @param marketRentPriceString the marketRentPrice to set
   */
  public void setMarketRentPriceAsString(String marketRentPriceString) {
    marketRentPrice = BigDecimalUtils.stringToBigDecimal(marketRentPriceString);
  }

  /**
   * @return the rentPriceLimit
   */
  public BigDecimal getRentPriceLimit() {
    return rentPriceLimit;
  }

  /**
   * @param rentPriceLimit the rentPriceLimit to set
   */
  public void setRentPriceLimit(BigDecimal rentPriceLimit) {
    this.rentPriceLimit = rentPriceLimit;
  }

  /**
   * @return the rentPriceLimit
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.rentpricelimit}")
  public String getRentPriceLimitAsString() {
    return BigDecimalUtils.bigDecimalToString(rentPriceLimit);
  }

  /**
   * @param rentPriceLimitString the rentPriceLimit to set
   */
  public void setRentPriceLimitAsString(String rentPriceLimitString) {
    rentPriceLimit = BigDecimalUtils.stringToBigDecimal(rentPriceLimitString);
  }

  /**
   * @return the garageRent
   */
  public BigDecimal getGarageRent() {
    return garageRent;
  }

  /**
   * @param garageRent the garageRent to set
   */
  public void setGarageRent(BigDecimal garageRent) {
    this.garageRent = garageRent;
  }

  /**
   * @return the garageRent
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.garagerent}")
  public String getGarageRentAsString() {
    return BigDecimalUtils.bigDecimalToString(garageRent);
  }

  /**
   * @param garageRentString the garageRent to set
   */
  public void setGarageRentAsString(String garageRentString) {
    garageRent = BigDecimalUtils.stringToBigDecimal(garageRentString);
  }

  /**
   * @return the gardenRent
   */
  public BigDecimal getGardenRent() {
    return gardenRent;
  }

  /**
   * @param gardenRent the gardenRent to set
   */
  public void setGardenRent(BigDecimal gardenRent) {
    this.gardenRent = gardenRent;
  }

  /**
   * @return the gardenRent
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.gardenrent}")
  public String getGardenRentAsString() {
    return BigDecimalUtils.bigDecimalToString(gardenRent);
  }

  /**
   * @param gardenRentString the gardenRent to set
   */
  public void setGardenRentAsString(String gardenRentString) {
    gardenRent = BigDecimalUtils.stringToBigDecimal(gardenRentString);
  }

  /**
   * @return the extraRent
   */
  public BigDecimal getExtraRent() {
    return extraRent;
  }

  /**
   * @param extraRent the extraRent to set
   */
  public void setExtraRent(BigDecimal extraRent) {
    this.extraRent = extraRent;
  }

  /**
   * @return the extraRent
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.extrarent}")
  public String getExtraRentAsString() {
    return BigDecimalUtils.bigDecimalToString(extraRent);
  }

  /**
   * @param extraRentString the extraRent to set
   */
  public void setExtraRentAsString(String extraRentString) {
    extraRent = BigDecimalUtils.stringToBigDecimal(extraRentString);
  }

  /**
   * @return the expectedChargeCost
   */
  public BigDecimal getExpectedChargeCost() {
    return expectedChargeCost;
  }

  /**
   * @param expectedChargeCost the expectedChargeCost to set
   */
  public void setExpectedChargeCost(BigDecimal expectedChargeCost) {
    this.expectedChargeCost = expectedChargeCost;
  }

  /**
   * @return the expectedChargeCost
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.expectedchargecost}")
  public String getExpectedChargeCostAsString() {
    return BigDecimalUtils.bigDecimalToString(expectedChargeCost);
  }

  /**
   * @param expectedChargeCostString the expectedChargeCost to set
   */
  public void setExpectedChargeCostAsString(String expectedChargeCostString) {
    expectedChargeCost = BigDecimalUtils.stringToBigDecimal(expectedChargeCostString);
  }

  /**
   * @return the waterInvoicing
   */
  public BigDecimal getWaterInvoicing() {
    return waterInvoicing;
  }

  /**
   * @param waterInvoicing the waterInvoicing to set
   */
  public void setWaterInvoicing(BigDecimal waterInvoicing) {
    this.waterInvoicing = waterInvoicing;
  }

  /**
   * @return the waterInvoicing
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.waterinvoicing}")
  @NullableMin(value = 0, message = "{contract.creation.error.format.waterinvoicing}")
  public String getWaterInvoicingAsString() {
    return BigDecimalUtils.bigDecimalToString(waterInvoicing);
  }

  /**
   * @param waterInvoicingString the waterInvoicing to set
   */
  public void setWaterInvoicingAsString(String waterInvoicingString) {
    waterInvoicing = BigDecimalUtils.stringToBigDecimal(waterInvoicingString);
  }

  /**
   * @return the garbageInvoicing
   */
  public BigDecimal getGarbageInvoicing() {
    return garbageInvoicing;
  }

  /**
   * @param garbageInvoicing the garbageInvoicing to set
   */
  public void setGarbageInvoicing(BigDecimal garbageInvoicing) {
    this.garbageInvoicing = garbageInvoicing;
  }

  /**
   * @return the garbageInvoicing
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.garbageinvoicing}")
  @NullableMin(value = 0, message = "{contract.creation.error.format.garbageinvoicing}")
  public String getGarbageInvoicingAsString() {
    return BigDecimalUtils.bigDecimalToString(garbageInvoicing);
  }

  /**
   * @param garbageInvoicingString the garbageInvoicing to set
   */
  public void setGarbageInvoicingAsString(String garbageInvoicingString) {
    garbageInvoicing = BigDecimalUtils.stringToBigDecimal(garbageInvoicingString);
  }

  public Date getInsuranceCertificateEndDate() {
    return DateTimeUtils.clone(insuranceCertificateEndDate);
  }

  public void setInsuranceCertificateEndDate(Date insuranceCertificateEndDate) {
    this.insuranceCertificateEndDate = DateTimeUtils.clone(insuranceCertificateEndDate);
  }

  /**
   * @return the insuranceReimbursement
   */
  public BigDecimal getInsuranceReimbursement() {
    return insuranceReimbursement;
  }

  /**
   * @param insuranceReimbursement the insuranceReimbursement to set
   */
  public void setInsuranceReimbursement(BigDecimal insuranceReimbursement) {
    this.insuranceReimbursement = insuranceReimbursement;
  }

  /**
   * @return the insuranceReimbursement
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.insurancereimbursement}")
  @NullableMin(value = 0, message = "{contract.creation.error.format.insurancereimbursement}")
  public String getInsuranceReimbursementAsString() {
    return BigDecimalUtils.bigDecimalToString(insuranceReimbursement);
  }

  /**
   * @param insuranceReimbursementString the insuranceReimbursement to set
   */
  public void setInsuranceReimbursementAsString(String insuranceReimbursementString) {
    insuranceReimbursement = BigDecimalUtils.stringToBigDecimal(insuranceReimbursementString);
  }

  /**
   * @return the housingTaxReimbursement
   */
  public BigDecimal getHousingTaxReimbursement() {
    return housingTaxReimbursement;
  }

  /**
   * @param housingTaxReimbursement the housingTaxReimbursement to set
   */
  public void setHousingTaxReimbursement(BigDecimal housingTaxReimbursement) {
    this.housingTaxReimbursement = housingTaxReimbursement;
  }

  /**
   * @return the housingTaxReimbursement
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.housingtaxreimbursement}")
  @NullableMin(value = 0, message = "{contract.creation.error.format.housingtaxreimbursement}")
  public String getHousingTaxReimbursementAsString() {
    return BigDecimalUtils.bigDecimalToString(housingTaxReimbursement);
  }

  /**
   * @param housingTaxReimbursementString the housingTaxReimbursement to set
   */
  public void setHousingTaxReimbursementAsString(String housingTaxReimbursementString) {
    housingTaxReimbursement = BigDecimalUtils.stringToBigDecimal(housingTaxReimbursementString);
  }

  /**
   * @return the garbageReimbursement
   */
  public BigDecimal getGarbageReimbursement() {
    return garbageReimbursement;
  }

  /**
   * @param garbageReimbursement the garbageReimbursement to set
   */
  public void setGarbageReimbursement(BigDecimal garbageReimbursement) {
    this.garbageReimbursement = garbageReimbursement;
  }

  /**
   * @return the garbageReimbursement
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.garbagereimbursement}")
  @NullableMin(value = 0, message = "{contract.creation.error.format.garbagereimbursement}")
  public String getGarbageReimbursementAsString() {
    return BigDecimalUtils.bigDecimalToString(garbageReimbursement);
  }

  /**
   * @param garbageReimbursementString the garbageReimbursement to set
   */
  public void setGarbageReimbursementAsString(String garbageReimbursementString) {
    garbageReimbursement = BigDecimalUtils.stringToBigDecimal(garbageReimbursementString);
  }

  /**
   * @return the annualClearanceCharges
   */
  public BigDecimal getAnnualClearanceCharges() {
    return annualClearanceCharges;
  }

  /**
   * @param annualClearanceCharges the annualClearanceCharges to set
   */
  public void setAnnualClearanceCharges(BigDecimal annualClearanceCharges) {
    this.annualClearanceCharges = annualClearanceCharges;
  }

  /**
   * @return the annualClearanceCharges
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.annualclearancecharges}")
  public String getAnnualClearanceChargesAsString() {
    return BigDecimalUtils.bigDecimalToString(annualClearanceCharges);
  }

  /**
   * @param annualClearanceChargesString the annualClearanceCharges to set
   */
  public void setAnnualClearanceChargesAsString(String annualClearanceChargesString) {
    annualClearanceCharges = BigDecimalUtils.stringToBigDecimal(annualClearanceChargesString);
  }

  /**
   * @return the otherInvoicingLabel
   */
  public String getOtherInvoicingLabel() {
    return otherInvoicingLabel;
  }

  /**
   * @param otherInvoicingLabel the otherInvoicingLabel to set
   */
  public void setOtherInvoicingLabel(String otherInvoicingLabel) {
    this.otherInvoicingLabel = otherInvoicingLabel;
  }

  /**
   * @return the otherInvoicingAmount
   */
  public BigDecimal getOtherInvoicingAmount() {
    return otherInvoicingAmount;
  }

  /**
   * @param otherInvoicingAmount the otherInvoicingAmount to set
   */
  public void setOtherInvoicingAmount(BigDecimal otherInvoicingAmount) {
    this.otherInvoicingAmount = otherInvoicingAmount;
  }

  /**
   * @return the otherInvoicingAmount
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{contract.creation.error.format.otherinvoicingamount}")
  public String getOtherInvoicingAmountAsString() {
    return BigDecimalUtils.bigDecimalToString(otherInvoicingAmount);
  }

  /**
   * @param otherInvoicingAmountString the otherInvoicingAmount to set
   */
  public void setOtherInvoicingAmountAsString(String otherInvoicingAmountString) {
    otherInvoicingAmount = BigDecimalUtils.stringToBigDecimal(otherInvoicingAmountString);
  }

  /**
   * @return the addedWithdrawnRent
   */
  public BigDecimal getAddedWithdrawnRent() {
    return addedWithdrawnRent;
  }

  /**
   * @param addedWithdrawnRent the addedWithdrawnRent to set
   */
  public void setAddedWithdrawnRent(BigDecimal addedWithdrawnRent) {
    this.addedWithdrawnRent = addedWithdrawnRent;
  }

  /**
   * @return the lastWithdrawnDate
   */
  public Date getLastWithdrawnDate() {
    return DateTimeUtils.clone(lastWithdrawnDate);
  }

  /**
   * @param lastWithdrawnDate the lastWithdrawnDate to set
   */
  public void setLastWithdrawnDate(Date lastWithdrawnDate) {
    this.lastWithdrawnDate = DateTimeUtils.clone(lastWithdrawnDate);
  }

  /**
   * @return the terminationSavings
   */
  public Boolean getTerminationSavings() {
    return terminationSavings;
  }

  /**
   * @param terminationSavings the terminationSavings to set
   */
  public void setTerminationSavings(Boolean terminationSavings) {
    this.terminationSavings = terminationSavings;
  }

  /**
   * @return the terminationSavingAmount
   */
  public BigDecimal getTerminationSavingAmount() {
    return terminationSavingAmount;
  }

  /**
   * @param terminationSavingAmount the terminationSavingAmount to set
   */
  public void setTerminationSavingAmount(BigDecimal terminationSavingAmount) {
    this.terminationSavingAmount = terminationSavingAmount;
  }

  /**
   * @return the lastSavingDate
   */
  public Date getLastSavingDate() {
    return DateTimeUtils.clone(lastSavingDate);
  }

  /**
   * @param lastSavingDate the lastSavingDate to set
   */
  public void setLastSavingDate(Date lastSavingDate) {
    this.lastSavingDate = DateTimeUtils.clone(lastSavingDate);
  }

  /**
   * @return the realEstateRentalValue
   */
  public BigDecimal getRealEstateRentalValue() {
    return realEstateRentalValue;
  }

  /**
   * @param realEstateRentalValue the realEstateRentalValue to set
   */
  public void setRealEstateRentalValue(BigDecimal realEstateRentalValue) {
    this.realEstateRentalValue = realEstateRentalValue;
  }

  /**
   * @return the housing
   */
  public HousingDTO getHousing() {
    return housing;
  }

  /**
   * @param housing the housing to set
   */
  public void setHousing(HousingDTO housing) {
    this.housing = housing;
  }

  /**
   * @return the tenant
   */
  public TenantDTO getTenant() {
    return tenant;
  }

  /**
   * @param tenant the tenant to set
   */
  public void setTenant(TenantDTO tenant) {
    this.tenant = tenant;
  }

  /**
   * @return the rentTypology
   */
  public RentTypologyDTO getRentTypology() {
    return rentTypology;
  }

  /**
   * @param rentTypology the rentTypology to set
   */
  public void setRentTypology(RentTypologyDTO rentTypology) {
    this.rentTypology = rentTypology;
  }

  /**
   * @return the termination
   */
  public TerminationDTO getTermination() {
    return termination;
  }

  /**
   * @param termination the termination to set
   */
  public void setTermination(TerminationDTO termination) {
    this.termination = termination;
  }

  /**
   * @return the fieldOfActivity
   */
  public FieldOfActivityDTO getFieldOfActivity() {
    return fieldOfActivity;
  }

  /**
   * @param fieldOfActivity the fieldOfActivity to set
   */
  public void setFieldOfActivity(FieldOfActivityDTO fieldOfActivity) {
    this.fieldOfActivity = fieldOfActivity;
  }

  /**
   * @return the costCenter
   */
  public CostCenterDTO getCostCenter() {
    return costCenter;
  }

  /**
   * @param costCenter the costCenter to set
   */
  public void setCostCenter(CostCenterDTO costCenter) {
    this.costCenter = costCenter;
  }

  /**
   * @return the paymentMethod
   */
  public PaymentMethodDTO getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * @param paymentMethod the paymentMethod to set
   */
  public void setPaymentMethod(PaymentMethodDTO paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  /**
   * @return the revisedSurfaceArea
   */
  public BigDecimal getRevisedSurfaceArea() {
    return revisedSurfaceArea;
  }

  /**
   * @param revisedSurfaceArea the revisedSurfaceArea to set
   */
  public void setRevisedSurfaceArea(BigDecimal revisedSurfaceArea) {
    this.revisedSurfaceArea = revisedSurfaceArea;
  }

  /**
   * @return the revisedSurfaceAreaRent
   */
  public BigDecimal getRevisedSurfaceAreaRent() {
    return revisedSurfaceAreaRent;
  }

  /**
   * @param revisedSurfaceAreaRent the revisedSurfaceAreaRent to set
   */
  public void setRevisedSurfaceAreaRent(BigDecimal revisedSurfaceAreaRent) {
    this.revisedSurfaceAreaRent = revisedSurfaceAreaRent;
  }

  /**
   * @return the nNCoef
   */
  public BigDecimal getnNCoef() {
    return nNCoef;
  }

  /**
   * @param nCoef the nCoef to set
   */
  public void setnNCoef(BigDecimal nCoef) {
    nNCoef = nCoef;
  }

  /**
   * @return the nNCoefDetail
   */
  public String getnNCoefDetail() {
    return nNCoefDetail;
  }

  /**
   * @param nNCoefDetail the nNCoefDetail to set
   */
  public void setnNCoefDetail(String nNCoefDetail) {
    this.nNCoefDetail = nNCoefDetail;
  }

  /**
   * @return the netAgentRent
   */
  public BigDecimal getNetAgentRent() {
    return netAgentRent;
  }

  /**
   * @param netAgentRent the netAgentRent to set
   */
  public void setNetAgentRent(BigDecimal netAgentRent) {
    this.netAgentRent = netAgentRent;
  }

  /**
   * @return the shortTermContractDiscount
   */
  public BigDecimal getShortTermContractDiscount() {
    return shortTermContractDiscount;
  }

  /**
   * @param shortTermContractDiscount the shortTermContractDiscount to set
   */
  public void setShortTermContractDiscount(BigDecimal shortTermContractDiscount) {
    this.shortTermContractDiscount = shortTermContractDiscount;
  }

  /**
   * @return the lopRent
   */
  public BigDecimal getLopRent() {
    return lopRent;
  }

  /**
   * @param lopRent the lopRent to set
   */
  public void setLopRent(BigDecimal lopRent) {
    this.lopRent = lopRent;
  }

  /**
   * @return the withdrawnRent
   */
  public BigDecimal getWithdrawnRent() {
    return withdrawnRent;
  }

  /**
   * @param withdrawnRent the withdrawnRent to set
   */
  public void setWithdrawnRent(BigDecimal withdrawnRent) {
    this.withdrawnRent = withdrawnRent;
  }

  /**
   * @return the benefit
   */
  public BigDecimal getBenefit() {
    return benefit;
  }

  /**
   * @param benefit the benefit to set
   */
  public void setBenefit(BigDecimal benefit) {
    this.benefit = benefit;
  }

  /**
   * @return the plainAddedWithdrawnRent
   */
  public BigDecimal getPlainAddedWithdrawnRent() {
    return plainAddedWithdrawnRent;
  }

  /**
   * @param plainAddedWithdrawnRent the plainAddedWithdrawnRent to set
   */
  public void setPlainAddedWithdrawnRent(BigDecimal plainAddedWithdrawnRent) {
    this.plainAddedWithdrawnRent = plainAddedWithdrawnRent;
  }

  /**
   * @return the plainTempAddedWithdrawnRent
   */
  public BigDecimal getPlainTempAddedWithdrawnRent() {
    return plainTempAddedWithdrawnRent;
  }

  /**
   * @param plainTempAddedWithdrawnRent the plainTempAddedWithdrawnRent to set
   */
  public void setPlainTempAddedWithdrawnRent(BigDecimal plainTempAddedWithdrawnRent) {
    this.plainTempAddedWithdrawnRent = plainTempAddedWithdrawnRent;
  }

  /**
   * @return the contractReference
   */
  public String getContractReference() {
    return contractReference;
  }

  /**
   * @param contractReference the contractReference to set
   */
  public void setContractReference(String contractReference) {
    this.contractReference = contractReference;
  }

  /**
   * @return the plainTerminationSavingAmount
   */
  public BigDecimal getPlainTerminationSavingAmount() {
    return plainTerminationSavingAmount;
  }

  /**
   * @param plainTerminationSavingAmount the plainTerminationSavingAmount to set
   */
  public void setPlainTerminationSavingAmount(BigDecimal plainTerminationSavingAmount) {
    this.plainTerminationSavingAmount = plainTerminationSavingAmount;
  }

  /**
   * @return the plainTempTerminationSavingAmount
   */
  public BigDecimal getPlainTempTerminationSavingAmount() {
    return plainTempTerminationSavingAmount;
  }

  /**
   * @param plainTempTerminationSavingAmount the plainTempTerminationSavingAmount to set
   */
  public void setPlainTempTerminationSavingAmount(BigDecimal plainTempTerminationSavingAmount) {
    this.plainTempTerminationSavingAmount = plainTempTerminationSavingAmount;
  }

  /**
   * @return the householdSize
   */
  public Integer getHouseholdSize() {
    return householdSize;
  }

  /**
   * @param householdSize the householdSize to set
   */
  public void setHouseholdSize(Integer householdSize) {
    this.householdSize = householdSize;
  }

  /**
   * @return the retroactivitysMonths
   */
  public Integer getRetroactivitysMonths() {
    return retroactivitysMonths;
  }

  /**
   * @param retroactivitysMonths the retroactivitysMonths to set
   */
  public void setRetroactivitysMonths(Integer retroactivitysMonths) {
    this.retroactivitysMonths = retroactivitysMonths;
  }

  /**
   * @return the fixedWithdrawnRent
   */
  public BigDecimal getFixedWithdrawnRent() {
    return fixedWithdrawnRent;
  }

  /**
   * @param fixedWithdrawnRent the fixedWithdrawnRent to set
   */
  public void setFixedWithdrawnRent(BigDecimal fixedWithdrawnRent) {
    this.fixedWithdrawnRent = fixedWithdrawnRent;
  }

  /**
   * @return the terminationSavingsPayment
   */
  public Boolean getTerminationSavingsPayment() {
    return terminationSavingsPayment;
  }

  /**
   * @param terminationSavingsPayment the terminationSavingsPayment to set
   */
  public void setTerminationSavingsPayment(Boolean terminationSavingsPayment) {
    this.terminationSavingsPayment = terminationSavingsPayment;
  }

  /**
   * @return the terminationSavingsPaymentDate
   */
  public Date getTerminationSavingsPaymentDate() {
    return DateTimeUtils.clone(terminationSavingsPaymentDate);
  }

  /**
   * @param terminationSavingsPaymentDate the terminationSavingsPayment to set
   */
  public void setTerminationSavingsPaymentDate(Date terminationSavingsPaymentDate) {
    this.terminationSavingsPaymentDate = DateTimeUtils.clone(terminationSavingsPaymentDate);
  }

  /**
   * @return the fixedNNCoef
   */
  public BigDecimal getFixedNNCoef() {
    return fixedNNCoef;
  }

  /**
   * @param fixedNNCoef the fixedNNCoef to set
   */
  public void setFixedNNCoef(BigDecimal fixedNNCoef) {
    this.fixedNNCoef = fixedNNCoef;
  }

  /**
   * @return the fixedAgency
   */
  public AgencyDTO getFixedAgency() {
    return fixedAgency;
  }

  /**
   * @param fixedAgency the fixedAgency to set
   */
  public void setFixedAgency(AgencyDTO fixedAgency) {
    this.fixedAgency = fixedAgency;
  }

  /**
   * @return the fixedRevisedSurfaceArea
   */
  public BigDecimal getFixedRevisedSurfaceArea() {
    return fixedRevisedSurfaceArea;
  }

  /**
   * @param fixedRevisedSurfaceArea the fixedRevisedSurfaceArea to set
   */
  public void setFixedRevisedSurfaceArea(BigDecimal fixedRevisedSurfaceArea) {
    this.fixedRevisedSurfaceArea = fixedRevisedSurfaceArea;
  }

  /**
   * @return the fixedRevisedSurfaceAreaRent
   */
  public BigDecimal getFixedRevisedSurfaceAreaRent() {
    return fixedRevisedSurfaceAreaRent;
  }

  /**
   * @param fixedRevisedSurfaceAreaRent the fixedRevisedSurfaceAreaRent to set
   */
  public void setFixedRevisedSurfaceAreaRent(BigDecimal fixedRevisedSurfaceAreaRent) {
    this.fixedRevisedSurfaceAreaRent = fixedRevisedSurfaceAreaRent;
  }

  /**
   * @return the fixedLopRent
   */
  public BigDecimal getFixedLopRent() {
    return fixedLopRent;
  }

  /**
   * @param fixedLopRent the fixedLopRent to set
   */
  public void setFixedLopRent(BigDecimal fixedLopRent) {
    this.fixedLopRent = fixedLopRent;
  }

  /**
   * @return the fixedBenefit
   */
  public BigDecimal getFixedBenefit() {
    return fixedBenefit;
  }

  /**
   * @param fixedBenefit the fixedBenefit to set
   */
  public void setFixedBenefit(BigDecimal fixedBenefit) {
    this.fixedBenefit = fixedBenefit;
  }

  /**
   * @return the closedContract
   */
  public Boolean getClosedContract() {
    return closedContract;
  }

  /**
   * @param closedContract the closedContract to set
   */
  public void setClosedContract(Boolean closedContract) {
    this.closedContract = closedContract;
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur du coeficient n/N au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public BigDecimal getNNCoefDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedNNCoef;
    } else {
      return nNCoef;
    }
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur de l'agence au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public AgencyDTO getAgencyDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedAgency;
    } else {
      return housing.getAgency();
    }
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur de la surface corrigée au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public BigDecimal getRevisedSurfaceAreaDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedRevisedSurfaceArea;
    } else {
      return revisedSurfaceArea;
    }
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur du loyer surface corrigée au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public BigDecimal getRevisedSurfaceAreaRentDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedRevisedSurfaceAreaRent;
    } else {
      return revisedSurfaceAreaRent;
    }
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur du loyer écrêté au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public BigDecimal getLopRentDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedLopRent;
    } else {
      return lopRent;
    }
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur des avantages en nature au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public BigDecimal getBenefitDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedBenefit;
    } else {
      return benefit;
    }
  }

}
