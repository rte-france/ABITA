package com.abita.web.shared;

public enum Couverture {

  GOODCONDITION("housing.picklist.goodCondition.couverture"),
  BADCONDITION("housing.picklist.badCondition.couverture"),
  UNKNOWN("housing.picklist.eunknown.couverture");

  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
  Couverture(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}