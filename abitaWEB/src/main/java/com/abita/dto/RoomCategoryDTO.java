/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.abita.web.shared.ConstantsWEB;
import com.dto.AbstractDTO;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO catégorie de local
 * @author
 *
 */
public class RoomCategoryDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = -847361444523852201L;

  /** Libellé */
  private String label;

  /** Pour les 10 premier m² */
  @NotNull(message = "{administration.referentiel.roomcategory.error.mandatory.firstsqr}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{administration.referentiel.roomcategory.error.format.firstsqr}")
  private BigDecimal first10SqrMeterPrice;

  /** Pour les m² suivants */
  @NotNull(message = "{administration.referentiel.roomcategory.error.mandatory.nextsqr}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{administration.referentiel.roomcategory.error.format.nextsqr}")
  private BigDecimal nextSqrMeterPrice;

  /** Flag pour empecher de modifier la catégorie "Autre" */
  private boolean modifiable;

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
   * getter Pour les 10 premier m²
   * @return the first10SqrMeterPrice
   */
  public BigDecimal getFirst10SqrMeterPrice() {
    return first10SqrMeterPrice;
  }

  /**
   * setter Pour les 10 premier m²
   * @param first10SqrMeterPrice the first10SqrMeterPrice to set
   */
  public void setFirst10SqrMeterPrice(BigDecimal first10SqrMeterPrice) {
    this.first10SqrMeterPrice = first10SqrMeterPrice;
  }

  /**
   * getter Pour les m² suivants
   * @return the nextSqrMeterPrice
   */
  public BigDecimal getNextSqrMeterPrice() {
    return nextSqrMeterPrice;
  }

  /**
   * setter Pour les m² suivants
   * @param nextSqrMeterPrice the nextSqrMeterPrice to set
   */
  public void setNextSqrMeterPrice(BigDecimal nextSqrMeterPrice) {
    this.nextSqrMeterPrice = nextSqrMeterPrice;
  }

  /**
   * @return the modifiable
   */
  public boolean isModifiable() {
    return modifiable;
  }

  /**
   * @param modifiable the modifiable to set
   */
  public void setModifiable(boolean modifiable) {
    this.modifiable = modifiable;
  }
}
