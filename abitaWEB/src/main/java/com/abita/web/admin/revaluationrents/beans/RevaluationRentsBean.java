/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.revaluationrents.beans;

import com.abita.dto.RevaluationRentsDTO;

import java.io.Serializable;

/**
 * Backing bean de la page de revalorisation des loyers
 */
public class RevaluationRentsBean implements Serializable {

  /** serialVersionUID */
  private static final long serialVersionUID = -148928017886767908L;

  /** Le RevaluationRentsDTO */
  private RevaluationRentsDTO revaluationRentsDTO;

  /**
   * @return the revaluationRentsDTO
   */
  public RevaluationRentsDTO getRevaluationRentsDTO() {
    return revaluationRentsDTO;
  }

  /**
   * @param revaluationRentsDTO the revaluationRentsDTO to set
   */
  public void setRevaluationRentsDTO(RevaluationRentsDTO revaluationRentsDTO) {
    this.revaluationRentsDTO = revaluationRentsDTO;
  }

}
