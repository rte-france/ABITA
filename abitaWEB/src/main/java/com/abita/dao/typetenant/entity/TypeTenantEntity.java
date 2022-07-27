/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.typetenant.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Entité des types d'occupant
 *
 * @author
 */
public class TypeTenantEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -6143850359169608507L;

  /** Libellé */
  private String label;

  /** Libellé pour la pièce NT*/
  private String ntHeaderLabel;

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
   * Getter du libellé pour la pièce NT
   * @return the ntHeaderLabel
   */
  public String getNtHeaderLabel() {
    return ntHeaderLabel;
  }

  /**
   * Setter du libellé pour la pièce NT
   * @param ntHeaderLabel the ntHeaderLabel to set
   */
  public void setNtHeaderLabel(String ntHeaderLabel) {
    this.ntHeaderLabel = ntHeaderLabel;
  }
}
