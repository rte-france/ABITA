/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.artesis;

import java.math.BigDecimal;

/**
 * Les informations nécessaires à l'écriture du fichier des retenues sur paie
 * @author
 *
 */
public class ArtesisRetainedSalaryBlock {

  /** Application d'origine */
  private String originApplication;
  /** Tranche de décompte */
  private String payArea;
  /** Période de paie */
  private String payPeriod;
  /** NNI + Code période */
  private String nni;
  /** Type d'enregistrement */
  private String recordType;
  /** Zone libre */
  private String freeZone;
  /** Code rubrique de paie */
  private String payCatgoryCode;
  /** Code sens */
  private String senseCode;
  /** Montant */
  private String amount;
  /** Date d'effet */
  private BigDecimal realAmount;
  /** Date d'effet */
  private String effectDate;
  /** Zone libre */
  private String freeZone2;
  /** Reference occupant */
  private String tenantReference;

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
   * @return the payArea
   */
  public String getPayArea() {
    return payArea;
  }

  /**
   * @param payArea the payArea to set
   */
  public void setPayArea(String payArea) {
    this.payArea = payArea;
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
   * @return the recordType
   */
  public String getRecordType() {
    return recordType;
  }

  /**
   * @param recordType the recordType to set
   */
  public void setRecordType(String recordType) {
    this.recordType = recordType;
  }

  /**
   * @return the freeZone
   */
  public String getFreeZone() {
    return freeZone;
  }

  /**
   * @param freeZone the freeZone to set
   */
  public void setFreeZone(String freeZone) {
    this.freeZone = freeZone;
  }

  /**
   * @return the payCatgoryCode
   */
  public String getPayCatgoryCode() {
    return payCatgoryCode;
  }

  /**
   * @param payCatgoryCode the payCatgoryCode to set
   */
  public void setPayCatgoryCode(String payCatgoryCode) {
    this.payCatgoryCode = payCatgoryCode;
  }

  /**
   * @return the senseCode
   */
  public String getSenseCode() {
    return senseCode;
  }

  /**
   * @param senseCode the senseCode to set
   */
  public void setSenseCode(String senseCode) {
    this.senseCode = senseCode;
  }

  /**
   * @return the amount
   */
  public String getAmount() {
    return amount;
  }

  /**
   * @param amount the amount to set
   */
  public void setAmount(String amount) {
    this.amount = amount;
  }

  /**
   * @return the realAmount
   */
  public BigDecimal getRealAmount() {
    return realAmount;
  }

  /**
   * @param realAmount the realAmount to set
   */
  public void setRealAmount(BigDecimal realAmount) {
    this.realAmount = realAmount;
  }

  /**
   * @return the effectDate
   */
  public String getEffectDate() {
    return effectDate;
  }

  /**
   * @param effectDate the effectDate to set
   */
  public void setEffectDate(String effectDate) {
    this.effectDate = effectDate;
  }

  /**
   * @return the freeZone2
   */
  public String getFreeZone2() {
    return freeZone2;
  }

  /**
   * @param freeZone2 the freeZone2 to set
   */
  public void setFreeZone2(String freeZone2) {
    this.freeZone2 = freeZone2;
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

}
