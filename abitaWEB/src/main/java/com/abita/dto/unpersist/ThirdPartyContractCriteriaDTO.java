/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto.unpersist;

import com.abita.dto.CostCenterDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.PaymentCycleDTO;
import com.abita.dto.ThirdPartyTerminationDTO;
import com.abita.util.dateutil.DateTimeUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe d'objet non persisté appellable depuis le controleur afin de passer les critères de recherche
 *
 * @author
 */
public class ThirdPartyContractCriteriaDTO implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 942010671937635282L;

  /** Référence */
  private Long id;

  /** Référence */
  private String reference;

  /** Durée du contrat */
  private String contractPeriod;

  /** Durée du préavis */
  private String noticePeriod;

  /** Date de début de validité */
  private Date startValidity;

  /** Date de début de paiement fournisseur */
  private Date startSupplierPaymentDate;

  /** Montant du loyer mensuel */
  private String rentAmount;

  /** Montant des charges prévisionnelles mensuelles */
  private String expectedChargeCost;

  /** Montant du dépot de garantie */
  private String guaranteedDepositAmount;

  /** Montant des frais d'agence */
  private String realEstateAgencyFee;

  /** Charge ponctuelle */
  private String sporadicallyInvoicing;

  /** Date de résiliation du contrat */
  private Date cancellationDate;

  /** Remboursement du dépot de garantie */
  private Boolean guaranteedDepositRefund;

  /** Terme échu / échoir */
  private Boolean expiryDate;

  /** Tiers */
  private Long thirdParty;

  /** Logement */
  private Long housing;

  /** Référence logement */
  private String housingReference;

  /** Domaine d'activité */
  private FieldOfActivityDTO fieldOfActivity;

  /** Centre coût */
  private CostCenterDTO costCenter;

  /** Périodicité du paiement */
  private PaymentCycleDTO paymentCycle;

  /** Motif de résiliation */
  private ThirdPartyTerminationDTO thirdPartyTermination;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

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
  public String getContractPeriod() {
    return contractPeriod;
  }

  /**
   * @param contractPeriod the contractPeriod to set
   */
  public void setContractPeriod(String contractPeriod) {
    this.contractPeriod = contractPeriod;
  }

  /**
   * @return the noticePeriod
   */
  public String getNoticePeriod() {
    return noticePeriod;
  }

  /**
   * @param noticePeriod the noticePeriod to set
   */
  public void setNoticePeriod(String noticePeriod) {
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
  public String getRentAmount() {
    return rentAmount;
  }

  /**
   * @param rentAmount the rentAmount to set
   */
  public void setRentAmount(String rentAmount) {
    this.rentAmount = rentAmount;
  }

  /**
   * @return the expectedChargeCost
   */
  public String getExpectedChargeCost() {
    return expectedChargeCost;
  }

  /**
   * @param expectedChargeCost the expectedChargeCost to set
   */
  public void setExpectedChargeCost(String expectedChargeCost) {
    this.expectedChargeCost = expectedChargeCost;
  }

  /**
   * @return the guaranteedDepositAmount
   */
  public String getGuaranteedDepositAmount() {
    return guaranteedDepositAmount;
  }

  /**
   * @param guaranteedDepositAmount the guaranteedDepositAmount to set
   */
  public void setGuaranteedDepositAmount(String guaranteedDepositAmount) {
    this.guaranteedDepositAmount = guaranteedDepositAmount;
  }

  /**
   * @return the realEstateAgencyFee
   */
  public String getRealEstateAgencyFee() {
    return realEstateAgencyFee;
  }

  /**
   * @param realEstateAgencyFee the realEstateAgencyFee to set
   */
  public void setRealEstateAgencyFee(String realEstateAgencyFee) {
    this.realEstateAgencyFee = realEstateAgencyFee;
  }

  /**
   * @return the sporadicallyInvoicing
   */
  public String getSporadicallyInvoicing() {
    return sporadicallyInvoicing;
  }

  /**
   * @param sporadicallyInvoicing the sporadicallyInvoicing to set
   */
  public void setSporadicallyInvoicing(String sporadicallyInvoicing) {
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
  public Boolean isGuaranteedDepositRefund() {
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
   * @return the thirdParty
   */
  public Long getThirdParty() {
    return thirdParty;
  }

  /**
   * @param thirdParty the thirdParty to set
   */
  public void setThirdParty(Long thirdParty) {
    this.thirdParty = thirdParty;
  }

  /**
   * @return the housing
   */
  public Long getHousing() {
    return housing;
  }

  /**
   * @param housing the housing to set
   */
  public void setHousing(Long housing) {
    this.housing = housing;
  }

  /**
   * @return the housingReference
   */
  public String getHousingReference() {
    return housingReference;
  }

  /**
   * @param housingReference the housingReference to set
   */
  public void setHousingReference(String housingReference) {
    this.housingReference = housingReference;
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
   * @param thirdPartyTermination the thirdPartyTermination to set
   */
  public void setThirdPartyTermination(ThirdPartyTerminationDTO thirdPartyTermination) {
    this.thirdPartyTermination = thirdPartyTermination;
  }

}
