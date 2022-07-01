package com.abita.dto;

import com.dto.AbstractDTO;

import java.math.BigDecimal;

/**
 * DTO taux de révision du Loyer Surface Corrigée (LSC)
 * @author
 *
 */
public class AdjustmentRateDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 1444894051516022906L;
  /**
   * Valeur
   */
  private BigDecimal value;

  /**
   * @return the value
   */
  public BigDecimal getValue() {
    return value;
  }

  /**
   * @param value the value to set
   */
  public void setValue(BigDecimal value) {
    this.value = value;
  }
}
