/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.shared;

/**
 * Classe d'énumération des statuts du logement
 *
 * @author
 */
public enum HousingStatus {

  /** Vide */
  EMPTY("Vide"),
  /** Occupé */
  OCCUPIED("Occupé");

  /** Le nom du mois */
  private final String label;

  /**
   * Constructeur
   * @param label le nom
   */
  private HousingStatus(String label) {
    this.label = label;
  }

  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

}
