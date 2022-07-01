package com.abita.dao.historyreporting.entity;

import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;

/**
 * Entité d’historisation des montants de reporting
 */
public class HistoryReportingEntity extends AbstractEntity {

  /** serialVersionUID */
  private static final long serialVersionUID = -9200621953086900979L;

  /** Mois d’historisation */
  private Integer month;

  /** Année d’historisation */
  private Integer year;

  /** Type de reporting */
  private String type;

  /** Montant */
  private BigDecimal amount;

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
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return the amount
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * @param amount the amount to set
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

}
