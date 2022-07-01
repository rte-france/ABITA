/**
 *
 */
package com.abita.dao.historyamount.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Entité d’historisation des montants générés
 * @author
 *
 */
public class HistoryAmountEntity extends AbstractEntity {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1962243010790705792L;

  /** Identifiant du contrat */
  private Long contractId;

  /** Type de génération */
  private String generationType;

  /** Mois de génération */
  private Integer monthGeneration;

  /** Année de génération */
  private Integer yearGeneration;

  /** Mois de rétroactivité */
  private Integer monthRetroactivity;

  /** Année de rétroactivité */
  private Integer yearRetroactivity;

  /** Details */
  private String details;

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
   * @return the monthGeneration
   */
  public Integer getMonthGeneration() {
    return monthGeneration;
  }

  /**
   * @param monthGeneration the monthGeneration to set
   */
  public void setMonthGeneration(Integer monthGeneration) {
    this.monthGeneration = monthGeneration;
  }

  /**
   * @return the yearGeneration
   */
  public Integer getYearGeneration() {
    return yearGeneration;
  }

  /**
   * @param yearGeneration the yearGeneration to set
   */
  public void setYearGeneration(Integer yearGeneration) {
    this.yearGeneration = yearGeneration;
  }

  /**
   * @return the monthRetroactivity
   */
  public Integer getMonthRetroactivity() {
    return monthRetroactivity;
  }

  /**
   * @param monthRetroactivity the monthRetroactivity to set
   */
  public void setMonthRetroactivity(Integer monthRetroactivity) {
    this.monthRetroactivity = monthRetroactivity;
  }

  /**
   * @return the yearRetroactivity
   */
  public Integer getYearRetroactivity() {
    return yearRetroactivity;
  }

  /**
   * @param yearRetroactivity the yearRetroactivity to set
   */
  public void setYearRetroactivity(Integer yearRetroactivity) {
    this.yearRetroactivity = yearRetroactivity;
  }

  /**
   * @return the details
   */
  public String getDetails() {
    return details;
  }

  /**
   * @param details the details to set
   */
  public void setDetails(String details) {
    this.details = details;
  }

  /**
   * @return the generationType
   */
  public String getGenerationType() {
    return generationType;
  }

  /**
   * @param generationType the generationType to set
   */
  public void setGenerationType(String generationType) {
    this.generationType = generationType;
  }
}
