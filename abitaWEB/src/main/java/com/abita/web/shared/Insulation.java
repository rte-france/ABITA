/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.shared;

public enum Insulation {
  COVER("housing.picklist.cover"),
  WALLS("housing.picklist.walls"),
  UNKNOWN("housing.picklist.Iunknown");

  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
  Insulation(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

}
