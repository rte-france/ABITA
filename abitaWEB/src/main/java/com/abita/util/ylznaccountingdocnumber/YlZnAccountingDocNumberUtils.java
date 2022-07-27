/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util.ylznaccountingdocnumber;

import com.abita.dto.YlZnAccountingDocNumberDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe utilitaire pour les numéros de pièce comptable
 */
public final class YlZnAccountingDocNumberUtils {

  /**
   * Constructeur privé
   */
  private YlZnAccountingDocNumberUtils() {
  }

  /**
   * Permet de vérifier que tous les champs d’une ancienne génération existent
   * @param ylZnAccountingDocNumberDTO génération YlZn
   * @return vrai si tous les champs requis existent
   */
  public static Boolean verifyOlderGeneration(YlZnAccountingDocNumberDTO ylZnAccountingDocNumberDTO) {
    if (null == ylZnAccountingDocNumberDTO) {
      return false;
    }

    BigDecimal finalRentAmount = ylZnAccountingDocNumberDTO.getYlZnAdnFinalRentAmount();
    BigDecimal finalExpectedChargeCost = ylZnAccountingDocNumberDTO.getYlZnAdnFinalExpectedChargeCost();
    BigDecimal mensualRentAmount = ylZnAccountingDocNumberDTO.getYlZnAdnMensualRentAmount();
    BigDecimal mensualExpectedChargeCost = ylZnAccountingDocNumberDTO.getYlZnAdnMensualExpectedChargeCost();
    Date cycleDate = ylZnAccountingDocNumberDTO.getYlZnAdnCycleDate();

    Boolean valid = true;

    if (null == finalRentAmount) {
      valid = false;
    }
    if (null == finalExpectedChargeCost) {
      valid = false;
    }
    if (null == mensualRentAmount) {
      valid = false;
    }
    if (null == mensualExpectedChargeCost) {
      valid = false;
    }
    if (null == cycleDate) {
      valid = false;
    }

    return valid;
  }
}
