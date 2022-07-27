/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.util.sort.DatatablePropertiesSorted;
import com.abita.dto.constant.DTOConstants;
import com.abita.web.shared.ConstantsWEB;
import com.dto.AbstractDTO;
import com.web.common.validators.NullableDigits;
import org.apache.commons.lang.BooleanUtils;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO des contrats tiers
 *
 * @author
 */
public class ThirdPartyContractDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 220687661800298761L;

  /** Référence */
  private String reference;

  /** Durée du contrat */
  @NotNull(message = "{thirdpartycontract.error.mandatory.contractperiod}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_5, fraction = ConstantsWEB.DECIMAL_PART_SIZE_NONE, message = "{thirdpartycontract.error.format.contractperiod}")
  private Integer contractPeriod;

  /** Durée du préavis */
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_5, fraction = ConstantsWEB.DECIMAL_PART_SIZE_NONE, message = "{thirdpartycontract.error.format.noticeperiod}")
  private Integer noticePeriod;

  /** Date de début de validité */
  @NotNull(message = "{thirdpartycontract.error.mandatory.startvalidity}")
  private Date startValidity;

  /** Date de début de paiement fournisseur */
  @NotNull(message = "{thirdpartycontract.error.mandatory.startsupplierpaymentdate}")
  private Date startSupplierPaymentDate;

  /** Montant du loyer mensuel */
  @NotNull(message = "{thirdpartycontract.error.mandatory.rentamount}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{thirdpartycontract.error.format.rentamount}")
  private BigDecimal rentAmount;

  /** Montant de la revision du loyer mensuel */
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{thirdpartycontract.error.format.revisedrentamount}")
  private BigDecimal revisedRentAmount;

  /** Date d'effet de la revision du loyer mensuel */
  private Date revisedRentDate;

  /** La date de la dernière révision du loyer mensuel */
  private Date lastRevisedRent;

  /** Montant des charges prévisionnelles mensuelles */
  @NotNull(message = "{thirdpartycontract.error.mandatory.expectedchargecost}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{thirdpartycontract.error.format.expectedchargecost}")
  private BigDecimal expectedChargeCost;

  /** Montant de la revision des charges prévisionnelles mensuelles */
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{thirdpartycontract.error.format.revisedexpectedchargecostamount}")
  private BigDecimal revisedExpectedChargeCostAmount;

  /** Date d'effet de la revision des charges prévisionnelles mensuelles */
  private Date revisedExpectedChargeCostDate;

  /** La date de la dernière révision des charges prévisionnelles mensuelles */
  private Date lastRevisedExpectedChargeCost;

  /** Loyer à payer */
  private BigDecimal rentToCharge;

  /**
   * Montant du dépot de garantie
   * Règles de validation : {@link #getGuaranteedDepositAmountAsString}
   */
  private BigDecimal guaranteedDepositAmount;

  /**
   * Montant des frais d’agence
   * Règles de validation : {@link #getRealEstateAgencyFeeAsString}
   */
  private BigDecimal realEstateAgencyFee;

  /**
   * Charge ponctuelle
   * Règles de validation : {@link #getSporadicallyInvoicingAsString}
   */
  private BigDecimal sporadicallyInvoicing;

  /** Date de résiliation du contrat */
  private Date cancellationDate;

  /** Remboursement du dépot de garantie */
  private Boolean guaranteedDepositRefund;

  /** Terme échu / échoir */
  private Boolean expiryDate;

  /** Nombre de régularisations YL à effectuer */
  private Integer ylRegularization;

  /** Tiers */
  @NotNull(message = "{thirdpartycontract.error.nonexistent.thirdparty}")
  private ThirdPartyDTO thirdParty;

  /** Logement */
  @NotNull(message = "{thirdpartycontract.error.nonexistent.housing}")
  private HousingDTO housing;

  /** Domaine d'activité */
  @NotNull(message = "{thirdpartycontract.error.mandatory.fieldofactivity}")
  private FieldOfActivityDTO fieldOfActivity;

  /** Centre coût */
  @NotNull(message = "{thirdpartycontract.error.mandatory.costcenter}")
  private CostCenterDTO costCenter;

  /** Périodicité du paiement */
  @NotNull(message = "{thirdpartycontract.error.mandatory.paymentcycle}")
  private PaymentCycleDTO paymentCycle;

  /** Motif de résiliation */
  private ThirdPartyTerminationDTO thirdPartyTermination;

  /** Agence lors de la cloture du contrat, null si le contrat est encore en cours */
  private AgencyDTO fixedAgency;

  /** Contrat clos (utilisation des valeurs "fixed") */
  private Boolean closedThirdPartyContract;

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @param reference the reference to set
   */
  public void setReference(String reference) {
    this.reference = reference;
  }

  /**
   * @return the contractPeriod
   */
  public Integer getContractPeriod() {
    return contractPeriod;
  }

  /**
   * @param contractPeriod the contractPeriod to set
   */
  public void setContractPeriod(Integer contractPeriod) {
    this.contractPeriod = contractPeriod;
  }

  /**
   * @return the noticePeriod
   */
  public Integer getNoticePeriod() {
    return noticePeriod;
  }

  /**
   * Renvoi la durée de préavis formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une valeur plaçant celles-ci après les dernières valeurs.
   * @return the noticePeriod
   */
  public Integer getSortedNoticePeriod() {
    return DatatablePropertiesSorted.getSortedInteger(noticePeriod);
  }

  /**
   * @param noticePeriod the noticePeriod to set
   */
  public void setNoticePeriod(Integer noticePeriod) {
    this.noticePeriod = noticePeriod;
  }

  /**
   * @return the startValidity
   */
  public Date getStartValidity() {
    return DateTimeUtils.clone(startValidity);
  }

  /**
   * @param startValidity the startValidity to set
   */
  public void setStartValidity(Date startValidity) {
    this.startValidity = DateTimeUtils.clone(startValidity);
  }

  /**
   * @return the startSupplierPaymentDate
   */
  public Date getStartSupplierPaymentDate() {
    return DateTimeUtils.clone(startSupplierPaymentDate);
  }

  /**
   * @param startSupplierPaymentDate the startSupplierPaymentDate to set
   */
  public void setStartSupplierPaymentDate(Date startSupplierPaymentDate) {
    this.startSupplierPaymentDate = DateTimeUtils.clone(startSupplierPaymentDate);
  }

  /**
   * @return the rentAmount
   */
  public BigDecimal getRentAmount() {
    return rentAmount;
  }

  /**
   * @param rentAmount the rentAmount to set
   */
  public void setRentAmount(BigDecimal rentAmount) {
    this.rentAmount = rentAmount;
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
   * @return the guaranteedDepositAmount
   */
  public BigDecimal getGuaranteedDepositAmount() {
    return guaranteedDepositAmount;
  }

  /**
   * Renvoi le montant de garantie formaté s'il n'est pas vide ou null. Si c'est le cas renvoi une valeur plaçant celles-ci après les dernières valeurs.
   * @return the guaranteedDepositAmount
   */
  public BigDecimal getSortedGuaranteedDepositAmount() {
    return DatatablePropertiesSorted.getSortedBigDecimal(guaranteedDepositAmount);
  }

  /**
  * @param guaranteedDepositAmount the guaranteedDepositAmount to set
  */
  public void setGuaranteedDepositAmount(BigDecimal guaranteedDepositAmount) {
    this.guaranteedDepositAmount = guaranteedDepositAmount;
  }

  /**
   * @return the guaranteedDepositAmount
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{thirdpartycontract.error.format.guaranteeddepositamount}")
  public String getGuaranteedDepositAmountAsString() {
    return BigDecimalUtils.bigDecimalToString(guaranteedDepositAmount);
  }

  /**
   * @param guaranteedDepositAmountString the guaranteedDepositAmount to set
   */
  public void setGuaranteedDepositAmountAsString(String guaranteedDepositAmountString) {
    guaranteedDepositAmount = BigDecimalUtils.stringToBigDecimal(guaranteedDepositAmountString);
  }

  /**
   * @return the realEstateAgencyFee
   */
  public BigDecimal getRealEstateAgencyFee() {
    return realEstateAgencyFee;
  }

  /**
   * Renvoi le montant des frais d'agence formaté s'il n'est pas vide ou null. Si c'est le cas renvoi une valeur plaçant celles-ci après les dernières valeurs.
   * @return the realEstateAgencyFee
   */
  public BigDecimal getSortedRealEstateAgencyFee() {
    return DatatablePropertiesSorted.getSortedBigDecimal(realEstateAgencyFee);
  }

  /**
   * @param realEstateAgencyFee the realEstateAgencyFee to set
   */
  public void setRealEstateAgencyFee(BigDecimal realEstateAgencyFee) {
    this.realEstateAgencyFee = realEstateAgencyFee;
  }

  /**
   * @return the realEstateAgencyFee
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{thirdpartycontract.error.format.realestateagencyfee}")
  public String getRealEstateAgencyFeeAsString() {
    return BigDecimalUtils.bigDecimalToString(realEstateAgencyFee);
  }

  /**
   * @param realEstateAgencyFeeString the realEstateAgencyFee to set
   */
  public void setRealEstateAgencyFeeAsString(String realEstateAgencyFeeString) {
    realEstateAgencyFee = BigDecimalUtils.stringToBigDecimal(realEstateAgencyFeeString);
  }

  /**
   * @return the sporadicallyInvoicing
   */
  public BigDecimal getSporadicallyInvoicing() {
    return sporadicallyInvoicing;
  }

  /**
   * Renvoi le montant de la charge ponctuelle formaté s'il n'est pas vide ou null. Si c'est le cas renvoi une valeur plaçant celles-ci après les dernières valeurs.
   * @return the sporadicallyInvoicing
   */
  public BigDecimal getSortedSporadicallyInvoicing() {
    return DatatablePropertiesSorted.getSortedBigDecimal(sporadicallyInvoicing);
  }

  /**
   * @param sporadicallyInvoicing the sporadicallyInvoicing to set
   */
  public void setSporadicallyInvoicing(BigDecimal sporadicallyInvoicing) {
    this.sporadicallyInvoicing = sporadicallyInvoicing;
  }

  /**
   * @return the sporadicallyInvoicing
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{thirdpartycontract.error.format.sporadicallyinvoicing}")
  public String getSporadicallyInvoicingAsString() {
    return BigDecimalUtils.bigDecimalToString(sporadicallyInvoicing);
  }

  /**
   * @param sporadicallyInvoicingString the sporadicallyInvoicing to set
   */
  public void setSporadicallyInvoicingAsString(String sporadicallyInvoicingString) {
    sporadicallyInvoicing = BigDecimalUtils.stringToBigDecimal(sporadicallyInvoicingString);
  }

  /**
   * @return the cancellationDate
   */
  public Date getCancellationDate() {
    return DateTimeUtils.clone(cancellationDate);
  }

  /**
   * Renvoi la date de résiliation formatée s'il n'est pas vide ou null. Si c'est le cas renvoi un long plaçant celles-ci après les dernières dates.
   * @return the unregisterDate
   */
  public long getSortedCancellationDate() {
    return DatatablePropertiesSorted.getSortedDate(cancellationDate);
  }

  /**
   * @param cancellationDate the cancellationDate to set
   */
  public void setCancellationDate(Date cancellationDate) {
    this.cancellationDate = DateTimeUtils.clone(cancellationDate);
  }

  /**
   * @return the guaranteedDepositRefund
   */
  public Boolean getGuaranteedDepositRefund() {
    return guaranteedDepositRefund;
  }

  /**
   * @param guaranteedDepositRefund the guaranteedDepositRefund to set
   */
  public void setGuaranteedDepositRefund(Boolean guaranteedDepositRefund) {
    this.guaranteedDepositRefund = guaranteedDepositRefund;
  }

  /**
   * @return the expiryDate
   */
  public Boolean getExpiryDate() {
    return expiryDate;
  }

  /**
   * Renvoi le terme s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant ceux-ci après les z.
   * @return the expiryDate
   */
  public String getSortedExpiryDate() {
    return DatatablePropertiesSorted.getSortedBoolean(expiryDate);
  }

  /**
   * @param expiryDate the expiryDate to set
   */
  public void setExpiryDate(Boolean expiryDate) {
    this.expiryDate = expiryDate;
  }

  /**
   * @return the ylRegularization
   */
  public Integer getYlRegularization() {
    return ylRegularization;
  }

  /**
   * @param ylRegularization the ylRegularization to set
   */
  public void setYlRegularization(Integer ylRegularization) {
    this.ylRegularization = ylRegularization;
  }

  /**
   * @return the thirdParty
   */
  public ThirdPartyDTO getThirdParty() {
    return thirdParty;
  }

  /**
   * @param thirdParty the thirdParty to set
   */
  public void setThirdParty(ThirdPartyDTO thirdParty) {
    this.thirdParty = thirdParty;
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
   * @return the paymentCycle
   */
  public PaymentCycleDTO getPaymentCycle() {
    return paymentCycle;
  }

  /**
   * @param paymentCycle the paymentCycle to set
   */
  public void setPaymentCycle(PaymentCycleDTO paymentCycle) {
    this.paymentCycle = paymentCycle;
  }

  /**
   * @return the thirdPartyTermination
   */
  public ThirdPartyTerminationDTO getThirdPartyTermination() {
    return thirdPartyTermination;
  }

  /**
   * Renvoi le label s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant ceux-ci après les z.
   *
   * @return the label
   */
  public String getSortedThirdPartyTermination() {
    if (null == thirdPartyTermination) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(thirdPartyTermination.getLabel());
    }

  }

  /**
   * @param thirdPartyTermination the thirdPartyTermination to set
   */
  public void setThirdPartyTermination(ThirdPartyTerminationDTO thirdPartyTermination) {
    this.thirdPartyTermination = thirdPartyTermination;
  }

  /**
   * @return the rentToCharge
   */
  public BigDecimal getRentToCharge() {
    return rentToCharge;
  }

  /**
   * @param rentToCharge the rentToCharge to set
   */
  public void setRentToCharge(BigDecimal rentToCharge) {
    this.rentToCharge = rentToCharge;
  }

  /**
   * @return the revisedRentAmount
   */
  public BigDecimal getRevisedRentAmount() {
    return revisedRentAmount;
  }

  /**
   * @param revisedRentAmount the revisedRentAmount to set
   */
  public void setRevisedRentAmount(BigDecimal revisedRentAmount) {
    this.revisedRentAmount = revisedRentAmount;
  }

  /**
   * @return the revisedRentDate
   */
  public Date getRevisedRentDate() {
    return DateTimeUtils.clone(revisedRentDate);
  }

  /**
   * @param revisedRentDate the revisedRentDate to set
   */
  public void setRevisedRentDate(Date revisedRentDate) {
    this.revisedRentDate = DateTimeUtils.clone(revisedRentDate);
  }

  /**
   * @return the revisedExpectedChargeCostAmount
   */
  public BigDecimal getRevisedExpectedChargeCostAmount() {
    return revisedExpectedChargeCostAmount;
  }

  /**
   * @param revisedExpectedChargeCostAmount the revisedExpectedChargeCostAmount to set
   */
  public void setRevisedExpectedChargeCostAmount(BigDecimal revisedExpectedChargeCostAmount) {
    this.revisedExpectedChargeCostAmount = revisedExpectedChargeCostAmount;
  }

  /**
   * @return the revisedExpectedChargeCostDate
   */
  public Date getRevisedExpectedChargeCostDate() {
    return DateTimeUtils.clone(revisedExpectedChargeCostDate);
  }

  /**
   * @param revisedExpectedChargeCostDate the revisedExpectedChargeCostDate to set
   */
  public void setRevisedExpectedChargeCostDate(Date revisedExpectedChargeCostDate) {
    this.revisedExpectedChargeCostDate = DateTimeUtils.clone(revisedExpectedChargeCostDate);
  }

  /**
   * @return the lastRevisedRent
   */
  public Date getLastRevisedRent() {
    return DateTimeUtils.clone(lastRevisedRent);
  }

  /**
   * @param lastRevisedRent the lastRevisedRent to set
   */
  public void setLastRevisedRent(Date lastRevisedRent) {
    this.lastRevisedRent = DateTimeUtils.clone(lastRevisedRent);
  }

  /**
   * @return the lastRevisedExpectedChargeCost
   */
  public Date getLastRevisedExpectedChargeCost() {
    return DateTimeUtils.clone(lastRevisedExpectedChargeCost);
  }

  /**
   * @param lastRevisedExpectedChargeCost the lastRevisedExpectedChargeCost to set
   */
  public void setLastRevisedExpectedChargeCost(Date lastRevisedExpectedChargeCost) {
    this.lastRevisedExpectedChargeCost = DateTimeUtils.clone(lastRevisedExpectedChargeCost);
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
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur de l'agence au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public AgencyDTO getAgencyDisplay() {
    if (BooleanUtils.isTrue(closedThirdPartyContract)) {
      return fixedAgency;
    } else {
      return housing.getAgency();
    }
  }

  /**
   * @return the closedThirdPartyContract
   */
  public Boolean getClosedThirdPartyContract() {
    return closedThirdPartyContract;
  }

  /**
   * @param closedThirdPartyContract the closedThirdPartyContract to set
   */
  public void setClosedThirdPartyContract(Boolean closedThirdPartyContract) {
    this.closedThirdPartyContract = closedThirdPartyContract;
  }
}
