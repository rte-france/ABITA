/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.agency.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Entité agence
 */
public class AgencyEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = 6070735992869628923L;

  /** Nom */
  private String name;

  /** Permet de savoir si l’agence peut être supprimable */
  private boolean removable;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
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

}
