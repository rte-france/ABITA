package com.abita.dto.unpersist;

import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dto.AgencyDTO;
import com.abita.dto.CostCenterDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.PaymentMethodDTO;
import com.abita.dto.RentTypologyDTO;
import com.abita.dto.TerminationDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe d'objet non persisté appellable depuis le controleur afin de passer les critères de recherche
 *
 * @author
 */
public class ContractCriteriaDTO implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 942010671937635282L;
  /**
   * PROPERTIES
   */
  /** Id */
  private Long id;
  /** Référence Contrat occupant */
  private String contractReference;
  /** Contrat signé */
  private Boolean signature;
  /** Date de début de validité */
  private Date startValidityDate;
  /** Date de fin de validité */
  private Date endValidityDate;
  /** Loyer marché */
  private BigDecimal marketRentPrice;
  /** Loyer plafond */
  private BigDecimal rentPriceLimit;
  /** Loyer garage */
  private BigDecimal garageRent;
  /** Loyer jardin */
  private BigDecimal gardenRent;
  /** Surloyer */
  private BigDecimal extraRent;
  /** Charges prévisionnelles */
  private BigDecimal expectedChargeCost;

  /** Date limite de validité de l'attestation d'assurance par interval, borne inférieure */
  private Date insuranceCertificateEndDateLow;

  /** Date limite de validité de l'attestation d'assurance par interval, borne supérieure */
  private Date insuranceCertificateEndDateHigh;

  /** Apurement annuel des charges */
  private BigDecimal annualClearanceCharges;
  /** Loyer prélevé cumulé fixe */
  private BigDecimal plainAddedWithdrawnRent;
  /** Date de dernier cumule de loyer prélevé */
  private Date lastWithdrawnDate;
  /** Pécule de fin d’occupation */
  private Boolean terminationSavings;
  /** Montant du pécule de fin d'occupation fixe*/
  private BigDecimal plainTerminationSavingAmount;
  /** Montant du pécule de fin d'occupation */
  private BigDecimal terminationSavingAmount;
  /** Date de dernier cumule de pécule de fin d’occupation */
  private Date lastSavingDate;
  /** Montant de la valeur locative foncière */
  private BigDecimal realEstateRentalValue;
  /** Logement */
  private Long housing;
  /** Référence logement */
  private String housingReference;
  /** Référence occupant */
  private String tenantReference;
  /** Typologie de loyer */
  private RentTypologyDTO rentTypology;
  /** Motif de résiliation */
  private TerminationDTO termination;
  /** Domaine d’activité */
  private FieldOfActivityDTO fieldOfActivity;
  /** Centre coût */
  private CostCenterDTO costCenter;
  /** Mode paiement */
  private PaymentMethodDTO paymentMethod;
  /** Nombre d'occupants */
  private Integer householdSize;
  /** Loyer réellement prélevé */
  private BigDecimal fixedWithdrawnRent;
  /** Règlement du pécule de fin d'occupation */
  private Boolean terminationSavingsPayment;
  /** Date du règlement du pécule de fin d'occupation */
  private Boolean terminationSavingsPaymentDate;
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

  /**
   * CALCULATED PROPERTIES
   */
  /** Surface corrigée */
  private BigDecimal revisedSurfaceArea;
  /** Loyer surface corrigée */
  private BigDecimal revisedSurfaceAreaRent;
  /** Coefficient n/N */
  private BigDecimal nNCoef;
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


  public Date getInsuranceCertificateEndDateLow() {
    return DateTimeUtils.clone(insuranceCertificateEndDateLow);
  }

  public void setInsuranceCertificateEndDateLow(Date insuranceCertificateEndDateLow) {
    this.insuranceCertificateEndDateLow = DateTimeUtils.clone(insuranceCertificateEndDateLow);
  }

  public Date getInsuranceCertificateEndDateHigh() {
    return DateTimeUtils.clone(insuranceCertificateEndDateHigh);
  }

  public void setInsuranceCertificateEndDateHigh(Date insuranceCertificateEndDateHigh) {
    this.insuranceCertificateEndDateHigh = DateTimeUtils.clone(insuranceCertificateEndDateHigh);
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
   * @return the tenantReference
   */
  public String getTenantReference() {
    return tenantReference;
  }

  /**
   * @param tenantReference the tenantReference to set
   */
  public void setTenantReference(String tenantReference) {
    this.tenantReference = tenantReference;
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
  public Boolean getTerminationSavingsPaymentDate() {
    return terminationSavingsPaymentDate;
  }

  /**
   * @param terminationSavingsPaymentDate the terminationSavingsPayment to set
   */
  public void setTerminationSavingsPaymentDate(Boolean terminationSavingsPaymentDate) {
    this.terminationSavingsPaymentDate = terminationSavingsPaymentDate;
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

}
