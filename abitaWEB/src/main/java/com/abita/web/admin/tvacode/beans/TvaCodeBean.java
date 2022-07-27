/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.tvacode.beans;

import com.abita.dto.TvaCodeDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Backing bean de la page de gestion des codes TVA
 *
 * @author
 */
public class TvaCodeBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1479515589561539091L;

  /**
   * Objet de transfert des codes TVA
   */
  private TvaCodeDTO tvaCode;

  /**
   * Liste des codes TVA
   */
  private List<TvaCodeDTO> lstTvaCode;

  /**
   * @return the tvaCode
   */
  public TvaCodeDTO getTvaCode() {
    return tvaCode;
  }

  /**
   * @param tvaCode the tvaCode to set
   */
  public void setTvaCode(TvaCodeDTO tvaCode) {
    this.tvaCode = tvaCode;
  }

  /**
   * @return the lstTvaCode
   */
  public List<TvaCodeDTO> getLstTvaCode() {
    return lstTvaCode;
  }

  /**
   * @param lstTvaCode the lstTvaCode to set
   */
  public void setLstTvaCode(List<TvaCodeDTO> lstTvaCode) {
    this.lstTvaCode = lstTvaCode;
  }

}
