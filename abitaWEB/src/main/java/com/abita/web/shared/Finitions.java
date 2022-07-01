package com.abita.web.shared;

public enum Finitions {

  GOODCONDITION("housing.picklist.goodCondition.finitions"),
  BADCONDITION("housing.picklist.badCondition.finitions"),
  UNKNOWN("housing.picklist.eunknown.finitions");

  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
  Finitions(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
