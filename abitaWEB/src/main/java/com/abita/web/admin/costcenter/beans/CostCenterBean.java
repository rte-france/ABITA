/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.costcenter.beans;

import com.abita.dto.CostCenterDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Bean en relation avec la page /pages/administration/referentiel/costcenter.xhtml
 *
 * @author
 */
public class CostCenterBean implements Serializable {

  /** SerialVersionUID */
  private static final long serialVersionUID = -7850560757574637178L;

  /** Liste des centres coûts */
  private List<CostCenterDTO> lstCostCenter;

  /** Le centre coût à ajouter */
  private String costcenter;

  /**
   * @return the lstCostCenter
   */
  public List<CostCenterDTO> getLstCostCenter() {
    return lstCostCenter;
  }

  /**
   * @param lstCostCenter the lstCostCenter to set
   */
  public void setLstCostCenter(List<CostCenterDTO> lstCostCenter) {
    this.lstCostCenter = lstCostCenter;
  }

  /**
   * @return the costcenter
   */
  public String getCostcenter() {
    return costcenter;
  }

  /**
   * @param costcenter the costcenter to set
   */
  public void setCostcenter(String costcenter) {
    this.costcenter = costcenter;
  }

}
