package com.abita.web.shared;

public enum Vmc {

  YES("housing.picklist.yes.vmc"),
  NO("housing.picklist.no.vmc"),
  UNKNOWN("housing.picklist.runknown.vmc");

  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
  Vmc(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
