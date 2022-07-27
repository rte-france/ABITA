/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.dto.AbstractDTO;

import javax.validation.constraints.NotNull;

/**
 * DTO domaine d'activité
 * @author
 *
 */
public class FieldOfActivityDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 9174785348799697002L;

  /** Libellé */
  private String label;

  /** GMR */
  private String gmr;

  /** Agence */
  @NotNull(message = "{fieldofactivity.creation.error.agency}")
  private AgencyDTO agency;

  /** Permet de savoir si le domaine d'activité peut être supprimable */
  private boolean removable;

  /**
   * Getter du libellé
   *
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * Setter du libellé
   *
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @return the gmr
   */
  public String getGmr() {
    return gmr;
  }

  /**
   * @param gmr the gmr to set
   */
  public void setGmr(String gmr) {
    this.gmr = gmr;
  }

  /**
   * @return the removable
   */
  public boolean isRemovable() {
    return removable;
  }

  /**
   * @param removable the removable to set
   */
  public void setRemovable(boolean removable) {
    this.removable = removable;
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
