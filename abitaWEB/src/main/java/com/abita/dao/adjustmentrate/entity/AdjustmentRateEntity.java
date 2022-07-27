/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.adjustmentrate.entity;

import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;

/**
 * Entité taux de révision du Loyer Surface Corrigée (LSC)
 * @author
 *
 */
public class AdjustmentRateEntity extends AbstractEntity {

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
