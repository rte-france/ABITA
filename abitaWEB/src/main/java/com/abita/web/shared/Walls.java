/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.shared;

public enum Walls {
  INSIDE("housing.picklist.inside"),
  OUTSIDE("housing.picklist.outside"),
  NOTHING("housing.picklist.nothing"),
  UNKNOWN("housing.picklist.wunknown");

  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
    Walls(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
