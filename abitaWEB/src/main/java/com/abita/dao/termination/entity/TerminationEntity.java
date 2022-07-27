/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.termination.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Entité résiliation de contrat
 * @author
 *
 */
public class TerminationEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -6587889374501189512L;

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
