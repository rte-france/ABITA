package com.abita.dto;

import com.dto.AbstractDTO;

/**
 * Objet de transfert des codes comptables
 * @author
 *
 */
public class AccountingCodeDTO extends AbstractDTO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -2274059674748035512L;

  /** Libellé */
  private String label;

  /**
   * Code comptable
   */
  private String code;

  /**
   * Code comptable
   */
  private String technicalCode;

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

  /**
   * @return the technicalCode
   */
  public String getTechnicalCode() {
    return technicalCode;
  }

  /**
   * @param technicalCode the technicalCode to set
   */
  public void setTechnicalCode(String technicalCode) {
    this.technicalCode = technicalCode;
  }

}
