/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.heatingtype.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Entité type de chauffage
 */
public class HeatingTypeEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = 3614406844040352653L;

  /** Nom */
  private String label;

  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

}
