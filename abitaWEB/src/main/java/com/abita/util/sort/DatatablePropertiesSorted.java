package com.abita.util.sort;

import com.abita.dto.constant.DTOConstants;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe proposant des méthodes permettant des tris dans les datatables
 * @author
 *
 */
public final class DatatablePropertiesSorted {

  /**
   * Constructeur privé
   */
  private DatatablePropertiesSorted() {
  }

  /**
   * Méthode permettant de renvoyer un paramètre pour le tri dans une datatable
   * @param stringToSorted chaine à évaluer
   * @return le paramètre si non null ou non vide, sinon renvoi une chaine avec le dernier charactère ASCII concaténé cins fois
   */
  public static String getSortedString(String stringToSorted) {
    if (StringUtils.isEmpty(stringToSorted)) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return stringToSorted;
    }
  }

  /**
   * Méthode permettant de renvoyer un paramètre pour le tri dans une datatable
   * @param dateToSorted date à évaluer
   * @return le paramètre si non null ou non vide, sinon renvoi un long avec la valeur max
   */
  public static Long getSortedDate(Date dateToSorted) {
    if (null == dateToSorted) {
      return Long.MAX_VALUE;
    } else {
      return dateToSorted.getTime();
    }
  }

  /**
   * Méthode permettant de renvoyer un paramètre pour le tri dans une datatable
   * @param bigDecimalToSorted bigDecimal à évaluer
   * @return le paramètre si non null ou non vide, sinon renvoi un bigDecimal avec la valeur max d'un Double
   */
  public static BigDecimal getSortedBigDecimal(BigDecimal bigDecimalToSorted) {
    if (null == bigDecimalToSorted) {
      return BigDecimal.valueOf(Double.MAX_VALUE);
    } else {
      return bigDecimalToSorted;
    }
  }

  /**
   * Méthode permettant de renvoyer un paramètre pour le tri dans une datatable
   * @param integerToSorted integer à évaluer
   * @return le paramètre si non null ou non vide, sinon renvoi un bigDecimal avec la valeur max d'un Double
   */
  public static Integer getSortedInteger(Integer integerToSorted) {
    if (null == integerToSorted) {
      return Integer.MAX_VALUE;
    } else {
      return integerToSorted;
    }
  }

  /**
   * Méthode permettant de renvoyer un paramètre pour le tri dans une datatable
   * @param booleanToSorted chaine à évaluer
   * @return le paramètre si non null ou non vide, sinon renvoi une chaine avec le dernier charactère ASCII concaténé cins fois
   */
  public static String getSortedBoolean(Boolean booleanToSorted) {
    if (null == booleanToSorted) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return booleanToSorted.toString();
    }
  }
}
