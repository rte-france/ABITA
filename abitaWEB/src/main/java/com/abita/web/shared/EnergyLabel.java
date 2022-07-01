package com.abita.web.shared;

public enum EnergyLabel {
  A("housing.picklist.a"),
  B("housing.picklist.b"),
  C("housing.picklist.c"),
  D("housing.picklist.d"),
  E("housing.picklist.e"),
  F("housing.picklist.f"),
  G("housing.picklist.g");
  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
  EnergyLabel(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
