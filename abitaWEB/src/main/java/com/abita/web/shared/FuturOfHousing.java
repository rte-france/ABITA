/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.shared;

public enum FuturOfHousing {
  MAINTENANCE("housing.picklist.maintenanceAL"),
  DEMOLITION("housing.picklist.demolitionAL"),
  SALE("housing.picklist.saleAL");

  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
  FuturOfHousing(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
