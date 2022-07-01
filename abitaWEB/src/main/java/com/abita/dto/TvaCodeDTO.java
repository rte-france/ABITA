package com.abita.dto;

import com.dto.AbstractDTO;

/**
 * Objet de transfert des codes TVA
 *
 * @author
 */
public class TvaCodeDTO extends AbstractDTO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -5920351482277695133L;

  /** Libellé */
  private String label;

  /**
   * Code comptable
   */
  private String code;

  /**
   * GETTER / SETTER
   */

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
