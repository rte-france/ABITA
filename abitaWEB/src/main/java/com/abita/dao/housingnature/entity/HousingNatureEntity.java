package com.abita.dao.housingnature.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Nature Logement
 * @author
 *
 */
public class HousingNatureEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -5845279216101806522L;

  /** Libellé */
  private String label;

  /** Nature du local */
  private String natureOfLocal;

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
   * Getter de la nature du local
   * @return the natureOfLocal
   */
  public String getNatureOfLocal() {
    return natureOfLocal;
  }

  /**
   * Setter de la nature du local
   * @param natureOfLocal the natureOfLocal to set
   */
  public void setNatureOfLocal(String natureOfLocal) {
    this.natureOfLocal = natureOfLocal;
  }
}
