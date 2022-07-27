/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.thirdparty.bean;

import com.abita.dto.ThirdPartyDTO;

import java.io.Serializable;

/**
 * Backing bean de la page des gestion des tiers
 * @author
 *
 */
public class ThirdPartyBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -8871261459079350765L;

  /**
   *
   * PROPRIETES
   *
   */

  /**
   * Le tiers à créer/modifier
   */
  private ThirdPartyDTO thirdPartyDTO;

  /** La reference GCP */
  private String referenceGCP;

  /**
   *
   * GETTER/SETTER
   *
   */

  /**
   * Getter du tiers à créer/modifier
   * @return the thirdPartyDTO
   */
  public ThirdPartyDTO getThirdPartyDTO() {
    return thirdPartyDTO;
  }

  /**
   * Setter du tiers à créer/modifier
   * @param thirdPartyDTO the thirdPartyDTO to set
   */
  public void setThirdPartyDTO(ThirdPartyDTO thirdPartyDTO) {
    this.thirdPartyDTO = thirdPartyDTO;
  }

  /**
   * @return the referenceGCP
   */
  public String getReferenceGCP() {
    return referenceGCP;
  }

  /**
   * @param referenceGCP the referenceGCP to set
   */
  public void setReferenceGCP(String referenceGCP) {
    this.referenceGCP = referenceGCP;
  }

}
