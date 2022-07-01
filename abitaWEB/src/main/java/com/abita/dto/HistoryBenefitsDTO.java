package com.abita.dto;

import com.dto.AbstractDTO;

import java.math.BigDecimal;

/**
 *  DTO des historisations des avantages en nature
 * @author
 *
 */
public class HistoryBenefitsDTO extends AbstractDTO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 7112481192319271931L;

  /** Identifiant */
  private Long benefitsId;

  /** Mois d’historisation */
  private Integer month;

  /** Année d’historisation */
  private Integer year;

  /** Seuil minimum du barème */
  private BigDecimal minimumThreshold;

  /** Avantage pour une pièce */
  private BigDecimal benefitForOneRoom;

  /** Dans le cas d'un logement avec plusieurs pièces, avantage pour chaque pièce principale */
  private BigDecimal benefitForManyRooms;

  /** Historisation temporaire */
  private Boolean temp;

  /**
   * @return the benefitsId
   */
  public Long getBenefitsId() {
    return benefitsId;
  }

  /**
   * @param benefitsId the benefitsId to set
   */
  public void setBenefitsId(Long benefitsId) {
    this.benefitsId = benefitsId;
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
   * @return the minimumThreshold
   */
  public BigDecimal getMinimumThreshold() {
    return minimumThreshold;
  }

  /**
   * @param minimumThreshold the minimumThreshold to set
   */
  public void setMinimumThreshold(BigDecimal minimumThreshold) {
    this.minimumThreshold = minimumThreshold;
  }

  /**
   * @return the benefitForOneRoom
   */
  public BigDecimal getBenefitForOneRoom() {
    return benefitForOneRoom;
  }

  /**
   * @param benefitForOneRoom the benefitForOneRoom to set
   */
  public void setBenefitForOneRoom(BigDecimal benefitForOneRoom) {
    this.benefitForOneRoom = benefitForOneRoom;
  }

  /**
   * @return the benefitForManyRooms
   */
  public BigDecimal getBenefitForManyRooms() {
    return benefitForManyRooms;
  }

  /**
   * @param benefitForManyRooms the benefitForManyRooms to set
   */
  public void setBenefitForManyRooms(BigDecimal benefitForManyRooms) {
    this.benefitForManyRooms = benefitForManyRooms;
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
