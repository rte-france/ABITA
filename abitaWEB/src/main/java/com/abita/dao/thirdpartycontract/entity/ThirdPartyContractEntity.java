/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.thirdpartycontract.entity;

import com.abita.dao.agency.entity.AgencyEntity;
import com.abita.dao.costcenter.entity.CostCenterEntity;
import com.abita.dao.housing.entity.HousingEntity;
import com.abita.dao.paymentcycle.entity.PaymentCycleEntity;
import com.abita.dao.thirdparty.entity.ThirdPartyEntity;
import com.abita.dao.thirdpartytermination.entity.ThirdPartyTerminationEntity;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dao.fieldofactivity.entity.FieldOfActivityEntity;
import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entité des contrats tiers
 *
 * @author
 */
public class ThirdPartyContractEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = 220687661800298761L;

  /** Référence */
  private String reference;

  /** Durée du contrat */
  private Integer contractPeriod;

  /** Durée du préavis */
  private Integer noticePeriod;

  /** Date de début de validité */
  private Date startValidity;

  /** Date de début de paiement fournisseur */
  private Date startSupplierPaymentDate;

  /** Montant du loyer mensuel */
  private BigDecimal rentAmount;

  /** Montant de la revision du loyer mensuel */
  private BigDecimal revisedRentAmount;

  /** Date d'effet de la revision du loyer mensuel */
  private Date revisedRentDate;

  /** La date de la dernière révision du loyer mensuel */
  private Date lastRevisedRent;

  /** Montant des charges prévisionnelles mensuelles */
  private BigDecimal expectedChargeCost;

  /** Montant de la revision des charges prévisionnelles mensuelles */
  private BigDecimal revisedExpectedChargeCostAmount;

  /** Date d'effet de la revision des charges prévisionnelles mensuelles */
  private Date revisedExpectedChargeCostDate;

  /** La date de la dernière révision des charges prévisionnelles mensuelles */
  private Date lastRevisedExpectedChargeCost;

  /** Montant du dépot de garantie */
  private BigDecimal guaranteedDepositAmount;

  /** Montant des frais d'agence */
  private BigDecimal realEstateAgencyFee;

  /** Charge ponctuelle */
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
  private ThirdPartyEntity thirdParty;

  /** Logement */
  private HousingEntity housing;

  /** Domaine d'activité */
  private FieldOfActivityEntity fieldOfActivity;

  /** Centre coût */
  private CostCenterEntity costCenter;

  /** Périodicité du paiement */
  private PaymentCycleEntity paymentCycle;

  /** Périodicité du paiement */
  private ThirdPartyTerminationEntity thirdPartyTermination;

  /** Agence lors de la cloture du contrat, null si le contrat est encore en cours */
  private AgencyEntity fixedAgency;

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
   * @param guaranteedDepositAmount the guaranteedDepositAmount to set
   */
  public void setGuaranteedDepositAmount(BigDecimal guaranteedDepositAmount) {
    this.guaranteedDepositAmount = guaranteedDepositAmount;
  }

  /**
   * @return the realEstateAgencyFee
   */
  public BigDecimal getRealEstateAgencyFee() {
    return realEstateAgencyFee;
  }

  /**
   * @param realEstateAgencyFee the realEstateAgencyFee to set
   */
  public void setRealEstateAgencyFee(BigDecimal realEstateAgencyFee) {
    this.realEstateAgencyFee = realEstateAgencyFee;
  }

  /**
   * @return the sporadicallyInvoicing
   */
  public BigDecimal getSporadicallyInvoicing() {
    return sporadicallyInvoicing;
  }

  /**
   * @param sporadicallyInvoicing the sporadicallyInvoicing to set
   */
  public void setSporadicallyInvoicing(BigDecimal sporadicallyInvoicing) {
    this.sporadicallyInvoicing = sporadicallyInvoicing;
  }

  /**
   * @return the cancellationDate
   */
  public Date getCancellationDate() {
    return DateTimeUtils.clone(cancellationDate);
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
  public ThirdPartyEntity getThirdParty() {
    return thirdParty;
  }

  /**
   * @param thirdParty the thirdParty to set
   */
  public void setThirdParty(ThirdPartyEntity thirdParty) {
    this.thirdParty = thirdParty;
  }

  /**
   * @return the housing
   */
  public HousingEntity getHousing() {
    return housing;
  }

  /**
   * @param housing the housing to set
   */
  public void setHousing(HousingEntity housing) {
    this.housing = housing;
  }

  /**
   * @return the fieldOfActivity
   */
  public FieldOfActivityEntity getFieldOfActivity() {
    return fieldOfActivity;
  }

  /**
   * @param fieldOfActivity the fieldOfActivity to set
   */
  public void setFieldOfActivity(FieldOfActivityEntity fieldOfActivity) {
    this.fieldOfActivity = fieldOfActivity;
  }

  /**
   * @return the costCenter
   */
  public CostCenterEntity getCostCenter() {
    return costCenter;
  }

  /**
   * @param costCenter the costCenter to set
   */
  public void setCostCenter(CostCenterEntity costCenter) {
    this.costCenter = costCenter;
  }

  /**
   * @return the paymentCycle
   */
  public PaymentCycleEntity getPaymentCycle() {
    return paymentCycle;
  }

  /**
   * @param paymentCycle the paymentCycle to set
   */
  public void setPaymentCycle(PaymentCycleEntity paymentCycle) {
    this.paymentCycle = paymentCycle;
  }

  /**
   * @return the thirdPartyTermination
   */
  public ThirdPartyTerminationEntity getThirdPartyTermination() {
    return thirdPartyTermination;
  }

  /**
   * @param thirdPartyTermination the thirdPartyTermination to set
   */
  public void setThirdPartyTermination(ThirdPartyTerminationEntity thirdPartyTermination) {
    this.thirdPartyTermination = thirdPartyTermination;
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
   *
   * @return the fixedAgency
     */
  public AgencyEntity getFixedAgency() {
    return fixedAgency;
  }

  /**
   * @param fixedAgency the fixedAgency to set
   */
  public void setFixedAgency(AgencyEntity fixedAgency) {
    this.fixedAgency = fixedAgency;
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


