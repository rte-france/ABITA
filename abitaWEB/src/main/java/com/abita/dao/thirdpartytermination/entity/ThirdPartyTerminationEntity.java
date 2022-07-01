package com.abita.dao.thirdpartytermination.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Entité de Résiliation de contrat tiers
 * @author
 *
 */
public class ThirdPartyTerminationEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -8158702733239010273L;

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
