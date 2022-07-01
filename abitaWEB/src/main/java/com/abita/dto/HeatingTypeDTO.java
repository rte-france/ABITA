package com.abita.dto;

import com.dto.AbstractDTO;

/**
 * DTO type chauffage
 */
public class HeatingTypeDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 5203016296978684195L;

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