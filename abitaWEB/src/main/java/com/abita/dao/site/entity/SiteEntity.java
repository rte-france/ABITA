/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.site.entity;

import com.abita.dao.agency.entity.AgencyEntity;
import com.dao.common.entity.AbstractEntity;

/**
 * Entité site
 */
public class SiteEntity extends AbstractEntity {

  private static final long serialVersionUID = -7836505684847510176L;

  /**
   * Nom du site
   */
  private String name;

  /**
   * Agence liée au site
   */
  private AgencyEntity agency;

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

  public AgencyEntity getAgency() {
    return agency;
  }

  public void setAgency(AgencyEntity agency) {
    this.agency = agency;
  }

  public boolean isRemovable() {
    return removable;
  }

  public void setRemovable(boolean removable) {
    this.removable = removable;
  }

}
