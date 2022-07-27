/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.benefit.beans;

import com.abita.dto.SalaryLevelDTO;
import com.abita.util.SalaryRangeList;

import java.io.Serializable;

/**
 * @author
 * @version 1.0
 */
public class BenefitBean implements Serializable {

  /** SerialID */
  private static final long serialVersionUID = 688660719768193288L;

  /** Liste des barèmes des avantages en nature */
  private SalaryRangeList benefitList;

  /** Le barème en cours d'édition (création/modification) */
  private SalaryLevelDTO editedSalaryLevel;

  /**
   * @return the benefitList
   */
  public SalaryRangeList getBenefitList() {
    return benefitList;
  }

  /**
   * @param benefitList the benefitList to set
   */
  public void setBenefitList(SalaryRangeList benefitList) {
    this.benefitList = benefitList;
  }

  /**
   * @return the editedSalaryLevel
   */
  public SalaryLevelDTO getEditedSalaryLevel() {
    return editedSalaryLevel;
  }

  /**
   * @param editedSalaryLevel the editedSalaryLevel to set
   */
  public void setEditedSalaryLevel(SalaryLevelDTO editedSalaryLevel) {
    this.editedSalaryLevel = editedSalaryLevel;
  }

}
