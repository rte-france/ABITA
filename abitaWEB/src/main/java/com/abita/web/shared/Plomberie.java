/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.shared;

public enum Plomberie {

  YES("housing.picklist.yes.plomberie"),
  NO("housing.picklist.no.plomberie"),
  UNKNOWN("housing.picklist.runknown.plomberie");

  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
  Plomberie(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
