/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.benefit.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Avantage en nature
 * Cette classe n'est pas directement persisté, elle doit être encapsulé dans un SalaryLevelEntity
 * @author
 * @version 1.0
 */
public class Benefit implements Serializable {

  /** SerialID */
  private static final long serialVersionUID = -6522419714507711757L;

  /** Dans le cas d'un logement avec plusieurs pièces, avantage pour chaque pièce principale */
  private BigDecimal benefitForManyRooms;

  /** Avantage pour une pièce */
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
