package com.abita.web.shared;

/** Enumération pour la liste déroulante des motifs de sortie */
public enum ReasonForExit {
  SALE("housing.picklist.sale"),
  DEMOLITION("housing.picklist.demolition"),
  TERMINATION("housing.picklist.termination"),
  DUPLICATE("housing.picklist.duplicate"),
  OTHER("housing.picklist.other");

  /**
   * Clé du fichier properties correspondant à la valeur à afficher
   */
  private String key;

  /**
   * Constructeur
   *
   * @param key la clé du fichier properties
   */
  ReasonForExit(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
