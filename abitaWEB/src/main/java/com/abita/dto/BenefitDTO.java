/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.abita.web.shared.ConstantsWEB;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO Avantages en nature
 * Cette classe n'est pas persistée
 * @author
 * @version 1.0
 */
public class BenefitDTO implements Serializable {

  /** SerialID */
  private static final long serialVersionUID = -6516655838627928186L;

  /** Dans le cas d'un logement avec plusieurs pièces, avantage pour chaque pièce principale */
  @NotNull(message = "{administration.referentiel.benefit.error.benefitformanyrooms.mandatory}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_10, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2,
    message = "{administration.referentiel.benefit.error.benefitformanyrooms.format}")
  private BigDecimal benefitForManyRooms;

  /** Avantage pour une pièce */
  @NotNull(message = "{administration.referentiel.benefit.error.benefitforoneroom.mandatory}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_10, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{administration.referentiel.benefit.error.benefitforoneroom.format}")
  private BigDecimal benefitForOneRoom;

  /**
   * @return the benefitForManyRooms
   */
  public BigDecimal getBenefitForManyRooms() {
    return benefitForManyRooms;
  }

  /**
   * @param benefitForManyRooms the benefitForManyRooms to set
   */
  public void setBenefitForManyRooms(BigDecimal benefitForManyRooms) {
    this.benefitForManyRooms = benefitForManyRooms;
  }

  /**
   * @return the benefitForOneRoom
   */
  public BigDecimal getBenefitForOneRoom() {
    return benefitForOneRoom;
  }

  /**
   * @param benefitForOneRoom the benefitForOneRoom to set
   */
  public void setBenefitForOneRoom(BigDecimal benefitForOneRoom) {
    this.benefitForOneRoom = benefitForOneRoom;
  }

}
