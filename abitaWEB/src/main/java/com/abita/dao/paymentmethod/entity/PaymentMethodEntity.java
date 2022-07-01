package com.abita.dao.paymentmethod.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Entité méthodes de paiement
 * @author
 *
 */
public class PaymentMethodEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -4524974188433980189L;

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