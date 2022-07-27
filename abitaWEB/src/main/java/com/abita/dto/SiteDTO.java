/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.dto.AbstractDTO;

import javax.validation.constraints.NotNull;

/**
 * DTO site
 */
public class SiteDTO extends AbstractDTO {

  private static final long serialVersionUID = -715182797289322093L;
  /**
   * Nom du site
   */
  private String name;

  /**
   * Agence rattachée au site
   */
  @NotNull(message = "{site.creation.error.agency}")
  private AgencyDTO agency;

  /**
   * Permet de savoir si le site peut être supprimé
   */
  private boolean removable;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isRemovable() {
    return removable;
  }

  public void setRemovable(boolean removable) {
    this.removable = removable;
  }

  public AgencyDTO getAgency() {
    return agency;
  }

  public void setAgency(AgencyDTO agency) {
    this.agency = agency;
  }
}
