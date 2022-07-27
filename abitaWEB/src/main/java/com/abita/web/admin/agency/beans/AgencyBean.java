/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.agency.beans;

import com.abita.dto.AgencyDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Données des agences
 */
public class AgencyBean implements Serializable {

  /** SerialID */
  private static final long serialVersionUID = -6057483952791395L;

  /** Liste des agences */
  private List<AgencyDTO> agencies;

  /** Le centre coût à ajouter */
  private AgencyDTO agency;

  /**
   * @return the agencies
   */
  public List<AgencyDTO> getAgencies() {
    return agencies;
  }

  /**
   * @param agencies the agencies to set
   */
  public void setAgencies(List<AgencyDTO> agencies) {
    this.agencies = agencies;
  }

  /**
   * @return the agency
   */
  public AgencyDTO getAgency() {
    return agency;
  }

  /**
   * @param agency the agency to set
   */
  public void setAgency(AgencyDTO agency) {
    this.agency = agency;
  }

}
