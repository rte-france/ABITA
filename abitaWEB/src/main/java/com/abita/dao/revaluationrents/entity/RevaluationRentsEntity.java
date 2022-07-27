/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.revaluationrents.entity;

import com.abita.util.dateutil.DateTimeUtils;
import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entité de revalorisation des loyers
 */
public class RevaluationRentsEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -8096568835907595490L;

  /** Taux */
  private BigDecimal rate;

  /** Date de dernière mise à jour */
  private Date lastUpdated;

  /** Dernière action enregistrée */
  private Boolean action;

  /**
   * @return the rate
   */
  public BigDecimal getRate() {
    return rate;
  }

  /**
   * @param rate the rate to set
   */
  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  /**
   * @return the lastUpdated
   */
  public Date getLastUpdated() {
    return DateTimeUtils.clone(lastUpdated);
  }

  /**
   * @param lastUpdated the lastUpdated to set
   */
  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = DateTimeUtils.clone(lastUpdated);
  }

  /**
   * @return the action
   */
  public Boolean getAction() {
    return action;
  }

  /**
   * @param action the action to set
   */
  public void setAction(Boolean action) {
    this.action = action;
  }

}
