/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util.bigdecimalutil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Classe utilitaire sur les Big Decimal
 * @author
 *
 */
public final class BigDecimalUtils {

  /**
   * Constructeur privé
   */
  private BigDecimalUtils() {
  }

  /** Nombre de chiffres après la virgule */
  public static final int SCALE_PRICE = 2;

  /** Taille, en nombre de chiffre, de la partie décimal d'un nombre */
  public static final int POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO = 0;

  /** Division par 100 */
  public static final int PERCENTAGE_DIVISION = 2;

  /**
   * Permet de retourner un bigDecimal à zéro si le chiffre passé est nul
   * @param bigDecimal un chiffre en bigdecimal
   * @return un bigDecimal à zéro si le chiffre passé est nul sinon le chiffre lui-même
   */
  public static BigDecimal returnZeroFromBigDecimalIfNull(BigDecimal bigDecimal) {

    if (null == bigDecimal) {
      return BigDecimal.ZERO;
    }
    return bigDecimal;

  }

  /**
   * Permet de formater un chiffre suivant un format français
   * @param number un chiffre en bigdecimal
   * @return un chiffre suivant un format français
   */
  public static String formatFranceNumber(BigDecimal number) {
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    symbols.setDecimalSeparator(',');

    DecimalFormat formatter = new DecimalFormat("0.00", symbols);
    return formatter.format(number);
  }

  /**
   * Permet de transformer une chaîne de caractère en nombre
   * @param input chaine de caractère
   * @return nombre correspondant à la chaîne de caractère ou null si elle est vide
   */
  public static BigDecimal stringToBigDecimal(String input) {
    BigDecimal output = null;
    if (input != null && !input.isEmpty()) {
      output = new BigDecimal(input);
    }
    return output;
  }

  /**
   * Permet de transformer un nombre en chaîne de caractères
   * @param input nombre
   * @return chaîne de caractère correspondant au nombre ou vide s’il est vide
   */
  public static String bigDecimalToString(BigDecimal input) {
    String output = "";
    if (input != null) {
      output = input.toString();
    }
    return output;
  }
}
