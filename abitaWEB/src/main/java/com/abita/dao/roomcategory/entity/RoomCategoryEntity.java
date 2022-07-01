package com.abita.dao.roomcategory.entity;

import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;

/**
 * Entité catégorie de local
 * @author
 *
 */
public class RoomCategoryEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -847361444523852201L;

  /** Libellé */
  private String label;

  /** Pour les 10 premier m² */
  private BigDecimal first10SqrMeterPrice;

  /** Pour les m² suivants */
  private BigDecimal nextSqrMeterPrice;

  /**
   * Getter du libellé
   *
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * Setter du libellé
   *
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * getter Pour les 10 premier m²
   * @return the first10SqrMeterPrice
   */
  public BigDecimal getFirst10SqrMeterPrice() {
    return first10SqrMeterPrice;
  }

  /**
   * setter Pour les 10 premier m²
   * @param first10SqrMeterPrice the first10SqrMeterPrice to set
   */
  public void setFirst10SqrMeterPrice(BigDecimal first10SqrMeterPrice) {
    this.first10SqrMeterPrice = first10SqrMeterPrice;
  }

  /**
   * getter Pour les m² suivants
   * @return the nextSqrMeterPrice
   */
  public BigDecimal getNextSqrMeterPrice() {
    return nextSqrMeterPrice;
  }

  /**
   * setter Pour les m² suivants
   * @param nextSqrMeterPrice the nextSqrMeterPrice to set
   */
  public void setNextSqrMeterPrice(BigDecimal nextSqrMeterPrice) {
    this.nextSqrMeterPrice = nextSqrMeterPrice;
  }
}
