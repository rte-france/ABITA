package com.abita.dto;

import com.dto.AbstractDTO;

import java.math.BigDecimal;

/**
 * DTO TVA pour charges
 * @author
 *
 */
public class ChargeTaxDTO extends AbstractDTO {

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