package com.abita.dao.tvacode.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Classe Entité encapsulant l'objet code TVA
 *
 * @author
 */
public class TvaCodeEntity extends AbstractEntity {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 4577039109495655674L;

  /** Libellé */
  private String label;

  /**
   * Code TVA
   */
  private String code;

  /**
   * Getter du libellé
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * Setter du libellé
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * Getter du code comptable
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Setter du code comptable
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

}
