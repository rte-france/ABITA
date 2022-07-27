/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.dto.AbstractDTO;

/**
 * DTO typologie de loyer
 * @author
 *
 */
public class RentTypologyDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 2287596439036035644L;

  /** Libellé */
  private String label;

  /** Code technique */
  private String technicalCode;

  /** Indice logement imposé ou assigné */
  private String housingIndex;

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
   * Getter du code technique
   * @return the technicalCode
   */
  public String getTechnicalCode() {
    return technicalCode;
  }

  /**
   * Setter du code technique
   * @param technicalCode the technicalCode to set
   */
  public void setTechnicalCode(String technicalCode) {
    this.technicalCode = technicalCode;
  }

  /**
   * Getter de l'indice logement imposé ou assigné
   * @return the housingIndex
   */
  public String getHousingIndex() {
    return housingIndex;
  }

  /**
   * Setter de l'indice logement imposé ou assigné
   * @param housingIndex the housingIndex to set
   */
  public void setHousingIndex(String housingIndex) {
    this.housingIndex = housingIndex;
  }
}
