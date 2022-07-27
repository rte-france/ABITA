/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.benefit.entity;

import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;

/**
 * Entité barème pour avantage en nature
 * Cette classe se nomme "SalaryLevel" car elle ne porte que le seuil minimum du barème
 * @author
 * @version 1.0
 */
public class SalaryLevelEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -7881010321024806727L;

  /** Le détail des avantages en nautre */
  private Benefit benefit;

  /** Seuil minimum du barème */
  private BigDecimal minimumThreshold;

  /**
   * @return the benefit
   */
  public Benefit getBenefit() {
    return benefit;
  }

  /**
   * @param benefit the benefit to set
   */
  public void setBenefit(Benefit benefit) {
    this.benefit = benefit;
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

}
