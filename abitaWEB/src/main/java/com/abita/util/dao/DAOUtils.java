package com.abita.util.dao;

import com.abita.dao.constants.GenericConstantsDAO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.type.Type;

/**
 * Classe utilitaire des DAOs
 * @author
 *
 */
public final class DAOUtils {

  /**
   * Constructeur privé
   */
  private DAOUtils() {
  }

  /**
   * Classe permettant de créer un bout de requête à concaténer
   * Cette chaine permet d'ignorer la casse lors d'une recherche par chaine de caractère (like)
   * @param hibernateParameter paramètre hibernate à mapper
   * @param hibernateProperty propriété à rechercher
   * @return une chaine à concaténer ignorant la casse lors d'une recherche
   */
  public static String requestWithoutCaseSensivity(String hibernateParameter, String hibernateProperty) {
    return " and UPPER(TRANSLATE(" + hibernateParameter + "," + GenericConstantsDAO.UNACCENTED_CHARACTER + "," + GenericConstantsDAO.ACCENTED_CHARACTER
      + ")) like UPPER(TRANSLATE(" + hibernateProperty + ", " + GenericConstantsDAO.UNACCENTED_CHARACTER + ", " + GenericConstantsDAO.ACCENTED_CHARACTER + "))";

  }

  /**
   * Permet d’ajouter un critère à une requête en vérifiant si la condition n’est pas vide
   * @param query requête en cours de construction
   * @param condition condition à vérifier avant l’ajout du critère
   * @param criteria critère à ajouter à la requête
   */
  public static void appendNotEmptyCriteria(StringBuilder query, String condition, String criteria) {
    if (!StringUtils.isEmpty(condition)) {
      query.append(criteria);
    }
  }

  /**
   * Permet d’associer un paramètre à une requête en vérifiant si la condition n’est pas vide
   * @param query requête en cours de construction
   * @param criteria critère à vérifier
   * @param name nom du paramètre à ajouter à la requête
   * @param val valeur du paramètre à ajouter à la requête
   * @param type type de la valeur
   */
  public static void setNotEmptyParameter(Query query, String criteria, String name, Object val, Type type) {
    if (!StringUtils.isEmpty(criteria)) {
      query.setParameter(name, val, type);
    }
  }
}
