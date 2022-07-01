package com.abita.dao.contract.entity;

import com.abita.dao.agency.entity.AgencyEntity;
import com.abita.dao.tenant.entity.TenantEntity;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dao.costcenter.entity.CostCenterEntity;
import com.abita.dao.fieldofactivity.entity.FieldOfActivityEntity;
import com.abita.dao.housing.entity.HousingEntity;
import com.abita.dao.paymentmethod.entity.PaymentMethodEntity;
import com.abita.dao.renttypology.entity.RentTypologyEntity;
import com.abita.dao.termination.entity.TerminationEntity;
import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entité des contrats occupants
 *
 * @author
 */
public class ContractEntity extends AbstractEntity {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -3473879697156885306L;

  // Statics values
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
  /** Remboursement assurance */
  private BigDecimal insuranceReimbursement;
  /** Date limite de validité de l’attestation d’assurance */
  private Date insuranceCertificateEndDate;
  /** Remboursement taxe habitation */
  private BigDecimal housingTaxReimbursement;
  /** Remboursement ordures ménagères */
  private BigDecimal garbageReimbursement;
  /** Apurement annuel charges locatives */
  private BigDecimal annualClearanceCharges;
  /** Facturation ordures ménagères */
  private BigDecimal garbageInvoicing;
  /** Facturation consommation eau */
  private BigDecimal waterInvoicing;
  /** Autres charges locatives (libellé) */
  private String otherInvoicingLabel;
  /** Autres charges locatives (montant) */
  private BigDecimal otherInvoicingAmount;
  /** Loyer prélevé cumulé fixe */
  private BigDecimal plainAddedWithdrawnRent;
  /** Date de dernier cumule de loyer prélevé */
  private Date lastWithdrawnDate;
  /** Pécule de fin d’occupation */
  private Boolean terminationSavings;
  /** Montant du pécule de fin d'occupation */
  private BigDecimal plainTerminationSavingAmount;
  /** Date de dernier cumule de pécule de fin d’occupation */
  private Date lastSavingDate;
  /** Montant de la valeur locative foncière */
  private BigDecimal realEstateRentalValue;
  /** Logement */
  private HousingEntity housing;
  /** Occupant */
  private TenantEntity tenant;
  /** Typologie de loyer */
  private RentTypologyEntity rentTypology;
  /** Motif de résiliation */
  private TerminationEntity termination;
  /** Domaine d’activité */
  private FieldOfActivityEntity fieldOfActivity;
  /** Centre coût */
  private CostCenterEntity costCenter;
  /** Mode paiement */
  private PaymentMethodEntity paymentMethod;
  /** Loyer écrêté */
  private BigDecimal lopRent;
  /** Loyer net agent */
  private BigDecimal fixedNetAgentRent;
  /** Loyer réellement prélevé */
  private BigDecimal fixedWithdrawnRent;
  /** Abattement de précarité */
  private BigDecimal fixedShortTermContractDiscount;
  /** Nombre d'occupants */
  private Integer householdSize;
  /** Coeficient n/N si contrat clos (champ fixe) */
  private BigDecimal fixedNNCoef;
  /** Règlement du pécule de fin d'occupation */
  private Boolean terminationSavingsPayment;
  /** Date du règlement du pécule de fin d'occupation */
  private Date terminationSavingsPaymentDate;

  /** Nombre de mois de rétroactivité à effectuer */
  private Integer retroactivitysMonths;

  /** Agence si contrat clos (champ fixe) */
  private AgencyEntity fixedAgency;
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

  // Dynamics values
  /** Surface corrigée */
  private BigDecimal revisedSurfaceArea;
  /** Loyer surface corrigée */
  private BigDecimal revisedSurfaceAreaRent;
  /** Avantage en nature */
  private BigDecimal benefit;
  /** Montant du pécule de fin d'occupation */
  private BigDecimal terminationSavingAmount;
  /** Loyer prélevé cumulé */
  private BigDecimal addedWithdrawnRent;
  /** Coefficient n/N */
  private BigDecimal nNCoef;

  /** Loyer écrêté (partie calculée dynamiquement en base) */
  private BigDecimal computedLopRent;

  /** Loyer net agent (partie calculée dynamiquement en base) */
  private BigDecimal netAgentRent;
  /** Loyer prélevé (partie calculée dynamiquement en base) */
  private BigDecimal withdrawnRent;
  /** Abattement de précarité (partie calculée dynamiquement en base) */
  private BigDecimal shortTermContractDiscount;

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
   * @return the tenant
   */
  public TenantEntity getTenant() {
    return tenant;
  }

  /**
   * @param tenant the tenant to set
   */
  public void setTenant(TenantEntity tenant) {
    this.tenant = tenant;
  }

  /**
   * @return the rentTypology
   */
  public RentTypologyEntity getRentTypology() {
    return rentTypology;
  }

  /**
   * @param rentTypology the rentTypology to set
   */
  public void setRentTypology(RentTypologyEntity rentTypology) {
    this.rentTypology = rentTypology;
  }

  /**
   * @return the termination
   */
  public TerminationEntity getTermination() {
    return termination;
  }

  /**
   * @param termination the termination to set
   */
  public void setTermination(TerminationEntity termination) {
    this.termination = termination;
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
   * @return the paymentMethod
   */
  public PaymentMethodEntity getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * @param paymentMethod the paymentMethod to set
   */
  public void setPaymentMethod(PaymentMethodEntity paymentMethod) {
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
   * @return the computedLopRent
   */
  public BigDecimal getComputedLopRent() {
    return computedLopRent;
  }

  /**
   * @param computedLopRent the computedLopRent to set
   */
  public void setComputedLopRent(BigDecimal computedLopRent) {
    this.computedLopRent = computedLopRent;
  }

  /**
   * @return the fixedNetAgentRent
   */
  public BigDecimal getFixedNetAgentRent() {
    return fixedNetAgentRent;
  }

  /**
   * @param fixedNetAgentRent the fixedNetAgentRent to set
   */
  public void setFixedNetAgentRent(BigDecimal fixedNetAgentRent) {
    this.fixedNetAgentRent = fixedNetAgentRent;
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
   * @return the fixedShortTermContractDiscount
   */
  public BigDecimal getFixedShortTermContractDiscount() {
    return fixedShortTermContractDiscount;
  }

  /**
   * @param fixedShortTermContractDiscount the fixedShortTermContractDiscount to set
   */
  public void setFixedShortTermContractDiscount(BigDecimal fixedShortTermContractDiscount) {
    this.fixedShortTermContractDiscount = fixedShortTermContractDiscount;
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


  public Date getInsuranceCertificateEndDate() {
    return DateTimeUtils.clone(insuranceCertificateEndDate);
  }

  public void setInsuranceCertificateEndDate(Date insuranceCertificateEndDate) {
    this.insuranceCertificateEndDate = DateTimeUtils.clone(insuranceCertificateEndDate);
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


}
