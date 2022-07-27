/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.abita.util.dateutil.DateTimeUtils;
import com.dto.AbstractDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO des historisations des contrats occupant
 * @author
 *
 */
public class HistoryContractDTO extends AbstractDTO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 7299195516135271106L;

  /** Identifiant */
  private Long contractId;

  /** Mois d’historisation */
  private Integer month;

  /** Année d’historisation */
  private Integer year;

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

  /** Montant de la valeur locative foncière */
  private BigDecimal realEstateRentalValue;

  /** Historisation d'un Logement */
  private HistoryHousingDTO housing;

  /**  Historisation d'un occupant */
  private HistoryTenantDTO tenant;

  /** Code technique d’une typologie de loyer */
  private String rentTypologyTechnicalCode;

  /** Indice logement imposé ou assigné d’une typologie de loyer */
  private String rentTypologyHousingIndex;

  /** Libellé d’un domaine d’activité */
  private String fieldOfActivityLabel;

  /** Nombre d'occupants */
  private Integer householdSize;

  /** Montant du pécule de fin d'occupation */
  private BigDecimal terminationSavingAmount;

  /** Loyer prélevé cumulé */
  private BigDecimal addedWithdrawnRent;

  /** Historisation temporaire */
  private Boolean temp;

  // Dynamics values
  /** Loyer écrêté */
  private BigDecimal lopRent;

  /** Loyer surface corrigée */
  private BigDecimal revisedSurfaceAreaRent;

  /** Avantage en nature */
  private BigDecimal benefit;

  /** Coefficient n/N */
  private BigDecimal nNCoef;

  /** Loyer net agent (partie calculée dynamiquement en base) */
  private BigDecimal netAgentRent;

  /** Abattement de précarité (partie calculée dynamiquement en base) */
  private BigDecimal shortTermContractDiscount;

  /** Loyer prélevé */
  private BigDecimal withdrawnRent;

  /**
   * @return the contractId
   */
  public Long getContractId() {
    return contractId;
  }

  /**
   * @param contractId the contractId to set
   */
  public void setContractId(Long contractId) {
    this.contractId = contractId;
  }

  /**
   * @return the month
   */
  public Integer getMonth() {
    return month;
  }

  /**
   * @param month the month to set
   */
  public void setMonth(Integer month) {
    this.month = month;
  }

  /**
   * @return the year
   */
  public Integer getYear() {
    return year;
  }

  /**
   * @param year the year to set
   */
  public void setYear(Integer year) {
    this.year = year;
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
  public HistoryHousingDTO getHousing() {
    return housing;
  }

  /**
   * @param housing the housing to set
   */
  public void setHousing(HistoryHousingDTO housing) {
    this.housing = housing;
  }

  /**
   * @return the tenant
   */
  public HistoryTenantDTO getTenant() {
    return tenant;
  }

  /**
   * @param tenant the tenant to set
   */
  public void setTenant(HistoryTenantDTO tenant) {
    this.tenant = tenant;
  }

  /**
   * @return the rentTypologyTechnicalCode
   */
  public String getRentTypologyTechnicalCode() {
    return rentTypologyTechnicalCode;
  }

  /**
   * @param rentTypologyTechnicalCode the rentTypologyTechnicalCode to set
   */
  public void setRentTypologyTechnicalCode(String rentTypologyTechnicalCode) {
    this.rentTypologyTechnicalCode = rentTypologyTechnicalCode;
  }

  /**
   * @return the rentTypologyHousingIndex
   */
  public String getRentTypologyHousingIndex() {
    return rentTypologyHousingIndex;
  }

  /**
   * @param rentTypologyHousingIndex the rentTypologyHousingIndex to set
   */
  public void setRentTypologyHousingIndex(String rentTypologyHousingIndex) {
    this.rentTypologyHousingIndex = rentTypologyHousingIndex;
  }

  /**
   * @return the fieldOfActivityLabel
   */
  public String getFieldOfActivityLabel() {
    return fieldOfActivityLabel;
  }

  /**
   * @param fieldOfActivityLabel the fieldOfActivityLabel to set
   */
  public void setFieldOfActivityLabel(String fieldOfActivityLabel) {
    this.fieldOfActivityLabel = fieldOfActivityLabel;
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
   * @return the nNCoef
   */
  public BigDecimal getnNCoef() {
    return nNCoef;
  }

  /**
   * @param nCoef the nNCoef to set
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
   * @return the temp
   */
  public Boolean getTemp() {
    return temp;
  }

  /**
   * @param temp the temp to set
   */
  public void setTemp(Boolean temp) {
    this.temp = temp;
  }

}
