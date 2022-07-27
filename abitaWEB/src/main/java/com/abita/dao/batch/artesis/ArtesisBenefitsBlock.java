/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.dao.batch.artesis;

/**
 * Les informations nécessaires à l'écriture du fichier des avantages en nature
 * @author
 *
 */
public class ArtesisBenefitsBlock {

  /** Application d'origine */
  private String originApplication;
  /** NNI + code de période */
  private String nni;
  /** Société de gestion */
  private String managementCompany;
  /** Numéro de bail */
  private String contractReference;
  /** Date de début */
  private String startDate;
  /** Date de fin */
  private String endDate;
  /** Période de paie */
  private String payPeriod;
  /** Date de début de bail */
  private String startValidityDate;
  /** Date de fin de bail ou Date de résiliation */
  private String endValidityDate;
  /** Indice logement imposé ou assigné */
  private String housingIndex;
  /** Nature du local */
  private String housingNature;
  /** Nombre de pièces */
  private String roomCount;
  /** Date de quittancement */
  private String quittancementDate;
  /** Année de la VLF */
  private String realEstateRentalValueYear;
  /** Origine de la VLF */
  private String realEstateRentalValueOrigin;
  /** Signe de la VLF */
  private String realEstateRentalValueSign;
  /** Valeur mensuel de la VLF */
  private String realEstateRentalValueAmount;
  /** Signe du forfait */
  private String benefitsSign;
  /** Forfait mensuel */
  private String benefitsAmount;
  /** Signe du montant loyer */
  private String withdrawnRentSign;
  /** Montant net du loyer mensuel */
  private String withdrawnRentAmount;
  /** Témoin de rappel */
  private String endOfLine;

  /**
   * @return the originApplication
   */
  public String getOriginApplication() {
    return originApplication;
  }

  /**
   * @param originApplication the originApplication to set
   */
  public void setOriginApplication(String originApplication) {
    this.originApplication = originApplication;
  }

  /**
   * @return the nni
   */
  public String getNni() {
    return nni;
  }

  /**
   * @param nni the nni to set
   */
  public void setNni(String nni) {
    this.nni = nni;
  }

  /**
   * @return the managementCompany
   */
  public String getManagementCompany() {
    return managementCompany;
  }

  /**
   * @param managementCompany the managementCompany to set
   */
  public void setManagementCompany(String managementCompany) {
    this.managementCompany = managementCompany;
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
   * @return the startDate
   */
  public String getStartDate() {
    return startDate;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  /**
   * @return the payPeriod
   */
  public String getPayPeriod() {
    return payPeriod;
  }

  /**
   * @param payPeriod the payPeriod to set
   */
  public void setPayPeriod(String payPeriod) {
    this.payPeriod = payPeriod;
  }

  /**
   * @return the startValidityDate
   */
  public String getStartValidityDate() {
    return startValidityDate;
  }

  /**
   * @param startValidityDate the startValidityDate to set
   */
  public void setStartValidityDate(String startValidityDate) {
    this.startValidityDate = startValidityDate;
  }

  /**
   * @return the endValidityDate
   */
  public String getEndValidityDate() {
    return endValidityDate;
  }

  /**
   * @param endValidityDate the endValidityDate to set
   */
  public void setEndValidityDate(String endValidityDate) {
    this.endValidityDate = endValidityDate;
  }

  /**
   * @return the housingIndex
   */
  public String getHousingIndex() {
    return housingIndex;
  }

  /**
   * @param housingIndex the housingIndex to set
   */
  public void setHousingIndex(String housingIndex) {
    this.housingIndex = housingIndex;
  }

  /**
   * @return the housingNature
   */
  public String getHousingNature() {
    return housingNature;
  }

  /**
   * @param housingNature the housingNature to set
   */
  public void setHousingNature(String housingNature) {
    this.housingNature = housingNature;
  }

  /**
   * @return the roomCount
   */
  public String getRoomCount() {
    return roomCount;
  }

  /**
   * @param roomCount the roomCount to set
   */
  public void setRoomCount(String roomCount) {
    this.roomCount = roomCount;
  }

  /**
   * @return the quittancementDate
   */
  public String getQuittancementDate() {
    return quittancementDate;
  }

  /**
   * @param quittancementDate the quittancementDate to set
   */
  public void setQuittancementDate(String quittancementDate) {
    this.quittancementDate = quittancementDate;
  }

  /**
   * @return the realEstateRentalValueYear
   */
  public String getRealEstateRentalValueYear() {
    return realEstateRentalValueYear;
  }

  /**
   * @param realEstateRentalValueYear the realEstateRentalValueYear to set
   */
  public void setRealEstateRentalValueYear(String realEstateRentalValueYear) {
    this.realEstateRentalValueYear = realEstateRentalValueYear;
  }

  /**
   * @return the realEstateRentalValueOrigin
   */
  public String getRealEstateRentalValueOrigin() {
    return realEstateRentalValueOrigin;
  }

  /**
   * @param realEstateRentalValueOrigin the realEstateRentalValueOrigin to set
   */
  public void setRealEstateRentalValueOrigin(String realEstateRentalValueOrigin) {
    this.realEstateRentalValueOrigin = realEstateRentalValueOrigin;
  }

  /**
   * @return the realEstateRentalValueSign
   */
  public String getRealEstateRentalValueSign() {
    return realEstateRentalValueSign;
  }

  /**
   * @param realEstateRentalValueSign the realEstateRentalValueSign to set
   */
  public void setRealEstateRentalValueSign(String realEstateRentalValueSign) {
    this.realEstateRentalValueSign = realEstateRentalValueSign;
  }

  /**
   * @return the realEstateRentalValueAmount
   */
  public String getRealEstateRentalValueAmount() {
    return realEstateRentalValueAmount;
  }

  /**
   * @param realEstateRentalValueAmount the realEstateRentalValueAmount to set
   */
  public void setRealEstateRentalValueAmount(String realEstateRentalValueAmount) {
    this.realEstateRentalValueAmount = realEstateRentalValueAmount;
  }

  /**
   * @return the benefitsSign
   */
  public String getBenefitsSign() {
    return benefitsSign;
  }

  /**
   * @param benefitsSign the benefitsSign to set
   */
  public void setBenefitsSign(String benefitsSign) {
    this.benefitsSign = benefitsSign;
  }

  /**
   * @return the benefitsAmount
   */
  public String getBenefitsAmount() {
    return benefitsAmount;
  }

  /**
   * @param benefitsAmount the benefitsAmount to set
   */
  public void setBenefitsAmount(String benefitsAmount) {
    this.benefitsAmount = benefitsAmount;
  }

  /**
   * @return the withdrawnRentSign
   */
  public String getWithdrawnRentSign() {
    return withdrawnRentSign;
  }

  /**
   * @param withdrawnRentSign the withdrawnRentSign to set
   */
  public void setWithdrawnRentSign(String withdrawnRentSign) {
    this.withdrawnRentSign = withdrawnRentSign;
  }

  /**
   * @return the withdrawnRentAmount
   */
  public String getWithdrawnRentAmount() {
    return withdrawnRentAmount;
  }

  /**
   * @param withdrawnRentAmount the withdrawnRentAmount to set
   */
  public void setWithdrawnRentAmount(String withdrawnRentAmount) {
    this.withdrawnRentAmount = withdrawnRentAmount;
  }

  /**
   * @return the endOfLine
   */
  public String getEndOfLine() {
    return endOfLine;
  }

  /**
   * @param endOfLine the endOfLine to set
   */
  public void setEndOfLine(String endOfLine) {
    this.endOfLine = endOfLine;
  }
}
