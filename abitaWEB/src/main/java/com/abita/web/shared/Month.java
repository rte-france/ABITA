package com.abita.web.shared;

/**
 * Classe d'énumération des mois
 *
 * @author
 */
public enum Month {

  /** La représentation du mois de Janvier */
  JANUARY("Janvier", 1),
  /** La représentation du mois de Février */
  FEBRUARY("Février", 2),
  /** La représentation du mois de Mars */
  MARCH("Mars", 3),
  /** La représentation du mois de Avril */
  APRIL("Avril", 4),
  /** La représentation du mois de Mai */
  MAY("Mai", 5),
  /** La représentation du mois de Juin */
  JUNE("Juin", 6),
  /** La représentation du mois de Juillet */
  JULY("Juillet", 7),
  /** La représentation du mois de Août */
  AUGUST("Août", 8),
  /** La représentation du mois de Septembre */
  SEPTEMBER("Septembre", 9),
  /** La représentation du mois de Octobre */
  OCTOBER("Octobre", 10),
  /** La représentation du mois de Novembre */
  NOVEMBER("Novembre", 11),
  /** La représentation du mois de Décembre */
  DECEMBER("Décembre", 12);

  /** Le nom du mois */
  private final String label;

  /** valeur du mois */
  private final Integer value;

  /**
   * Constructeur
   * @param label le nom
   * @param value la valeur
   */
  private Month(String label, Integer value) {
    this.label = label;
    this.value = value;
  }

  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @return the value
   */
  public Integer getValue() {
    return value;
  }

  /**
   * Permete de récupérer le label d'après sa valeur
   * @param value : la valeur
   * @return le label
   */
  public static String getLabelByValue(Integer value) {
    for (Month month : Month.values()) {
      if (month.value.equals(value)) {
        return month.getLabel();
      }
    }

    return null;
  }

}
