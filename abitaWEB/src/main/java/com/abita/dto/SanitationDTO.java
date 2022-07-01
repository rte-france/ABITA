package com.abita.dto;

import com.dto.AbstractDTO;

/**
 * DTO assainissement
 */
public class SanitationDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = -1475071614229727316L;

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
