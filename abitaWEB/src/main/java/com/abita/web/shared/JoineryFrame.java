package com.abita.web.shared;

public enum JoineryFrame {




  GOODCONDITIONJOINERY("housing.picklist.goodConditionJoinery"),
  BADCONDITIONJOINERY("housing.picklist.badConditionJoinery"),
  DOUBLEGLAZINGJOINERY("housing.picklist.doubleGlazingJoinery"),
  UNKNOWNJOINERY("housing.picklist.unknownJoinery");

  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
  JoineryFrame(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
