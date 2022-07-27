/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.costcenter.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Entité centre de coût
 * @author
 *
 */
public class CostCenterEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -2003278397794912336L;

  /** Libellé */
  private String label;

  /** Permet de savoir si le centre coût peut être supprimable */
  private boolean removable;

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
   * @return the removable
   */
  public boolean isRemovable() {
    return removable;
  }

  /**
   * @param removable the removable to set
   */
  public void setRemovable(boolean removable) {
    this.removable = removable;
  }

}
