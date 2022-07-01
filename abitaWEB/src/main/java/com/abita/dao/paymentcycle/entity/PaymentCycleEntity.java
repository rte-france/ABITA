package com.abita.dao.paymentcycle.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Périodicité paiement
 *
 * @author
 */
public class PaymentCycleEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -1671297994087380820L;

  /** Libellé */
  private String label;

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
}
