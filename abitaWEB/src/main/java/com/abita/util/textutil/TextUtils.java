package com.abita.util.textutil;

import com.abita.web.shared.ConstantsWEB;

/**
 * Classe utilitaire concernant le travail sur le texte
 */
public final class TextUtils {

  /**
   * Constructeur privé
   */
  private TextUtils() {
  }

  /** Devise Euro */
  public static final String EURO_CURRENCY_SHORT_LABEL = "EUR";

  /**
   * Permet d’avoir un mot avec la première lettre en majuscule
   * @param s texte d’entrée
   * @return texte transformé
   */
  public static String capitalize(String s) {
    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
  }

  /**
   * Converti une taille définie en bytes en octet en utilisant le multiplicateur
   * le mieux adaptité pour la lisibilité (Mo, Ko, o);.
   *
   * @param bytes la taille en byte
   * @return la chaine formatée
   */
  public static String convertSize(long bytes) {
    if (bytes < ConstantsWEB.KILO_BYTE_SIZE) {
      return bytes + " o";
    }
    int exp = (int) (Math.log(bytes) / Math.log(ConstantsWEB.KILO_BYTE_SIZE));
    char pre = "KMGTPE".charAt(exp - 1);
    return String.format("%.1f %so", bytes / Math.pow(ConstantsWEB.KILO_BYTE_SIZE, exp), pre);
  }
}
