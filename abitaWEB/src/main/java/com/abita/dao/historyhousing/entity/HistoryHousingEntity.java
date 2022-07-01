/**
 *
 */
package com.abita.dao.historyhousing.entity;

import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;

/**
 * Entité d'historisation des logements
 * @author
 *
 */
public class HistoryHousingEntity extends AbstractEntity {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -8099836284390907962L;

  /** Identifiant */
  private Long housingId;

  /** Mois d’historisation */
  private Integer month;

  /** Année d’historisation */
  private Integer year;

  /** Surface corrigée */
  private BigDecimal revisedSurfaceArea;

  /** Nombre de pièce */
  private Integer roomCount;

  /** Nature du local */
  private String natureOfLocal;

  /** Nom du propriétaire */
  private String thirdPartyName;

  /** Barème de catégorie de local pour les 10 premier m² */
  private BigDecimal first10SqrMeterPrice;

  /** Barème de catégorie de local pour les m² suivants */
  private BigDecimal nextSqrMeterPrice;

  /** Historisation temporaire */
  private Boolean temp;

  /**
   * @return the housingId
   */
  public Long getHousingId() {
    return housingId;
  }

  /**
   * @param housingId the housingId to set
   */
  public void setHousingId(Long housingId) {
    this.housingId = housingId;
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
   * @return the roomCount
   */
  public Integer getRoomCount() {
    return roomCount;
  }

  /**
   * @param roomCount the roomCount to set
   */
  public void setRoomCount(Integer roomCount) {
    this.roomCount = roomCount;
  }

  /**
   * @return the natureOfLocal
   */
  public String getNatureOfLocal() {
    return natureOfLocal;
  }

  /**
   * @param natureOfLocal the natureOfLocal to set
   */
  public void setNatureOfLocal(String natureOfLocal) {
    this.natureOfLocal = natureOfLocal;
  }

  /**
   * @return the thirdPartyName
   */
  public String getThirdPartyName() {
    return thirdPartyName;
  }

  /**
   * @param thirdPartyName the thirdPartyName to set
   */
  public void setThirdPartyName(String thirdPartyName) {
    this.thirdPartyName = thirdPartyName;
  }

  /**
   * @return the first10SqrMeterPrice
   */
  public BigDecimal getFirst10SqrMeterPrice() {
    return first10SqrMeterPrice;
  }

  /**
   * @param first10SqrMeterPrice the first10SqrMeterPrice to set
   */
  public void setFirst10SqrMeterPrice(BigDecimal first10SqrMeterPrice) {
    this.first10SqrMeterPrice = first10SqrMeterPrice;
  }

  /**
   * @return the nextSqrMeterPrice
   */
  public BigDecimal getNextSqrMeterPrice() {
    return nextSqrMeterPrice;
  }

  /**
   * @param nextSqrMeterPrice the nextSqrMeterPrice to set
   */
  public void setNextSqrMeterPrice(BigDecimal nextSqrMeterPrice) {
    this.nextSqrMeterPrice = nextSqrMeterPrice;
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
