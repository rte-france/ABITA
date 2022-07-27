/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.chargetax.entity;

import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;

/**
 * Entité TVA pour charges
 * @author
 *
 */
public class ChargeTaxEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = 7336508369993465507L;

  /**
   * Code
   */
  private String code;

  /**
   * Valeur
   */
  private BigDecimal value;

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

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
