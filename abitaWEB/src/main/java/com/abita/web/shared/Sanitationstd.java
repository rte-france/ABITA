package com.abita.web.shared;

public enum Sanitationstd {


  YES("housing.picklist.yes.std"),
  NO("housing.picklist.no.std"),
  UNKNOWN("housing.picklist.runknown.std");

  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
  Sanitationstd(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
