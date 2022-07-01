package com.abita.util.signum;

/**
 * Classe utilitaire gérant les signes
 * @author
 *
 */
public final class SignumUtils {

  /**
   * Constructeur privé
   */
  private SignumUtils() {
  }

  /** Signe positif */
  public static final String POSITIVE_SIGN = "+";

  /** Signe négatif */
  public static final String NEGATIVE_SIGN = "-";

  /**
   * Permet de retrouner un signe en charactere suivant sa valeur
   * @param signum la valeur du signe
   * @return un caractère +,-
   */
  public static String getSignumByValue(int signum) {
    if (signum == -1) {
      return "-";
    }
    return "+";
  }
}
