/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util.helper;

/**
 * Enumeration des indexes du fichier Artesis
 * @author
 *
 */
public enum ArtesisIndexEnum {

  /** NNI court */
  SHORTNNI("SHORTNNI", 0, 6),
  /** Code période */
  PERIODCODE("PERIODCODE", 6, 8),
  /** Nom de l'employé */
  NAMEFIRSTNAMEEMPLOYEE("NAMEFIRSTNAMEEMPLOYEE", 24, 49),
  /** Cadre */
  MANAGERIALEMP("MANAGERIALEMP", 79, 86),
  /** Adresse de l'employé */
  EMPLOYEEADRESS("EMPLOYEEADRESS", 86, 112),
  /** Code postal de l'employé */
  EMPLOYEEPOSTALCODE("EMPLOYEEPOSTALCODE", 112, 117),
  /** Ville de l'employé */
  EMPLOYEECITY("EMPLOYEECITY", 117, 143),
  /** Salaire à octobre N-1 */
  REFERENCEGROSSSALARY("REFERENCEGROSSSALARY", 169, 180),
  /** Nombre de personne dans le logement */
  HOUSEHOLDSIZE("HOUSEHOLDSIZE", 191, 193),
  /** Motif de résiliation */
  TERMINATION("TERMINATION", 200, 203);

  /**
   * Chaine représentant la valeur associée
   */
  private String identifier;

  /**
   * Index du début de la chaine de caractères
   */
  private Integer beginIndex;

  /**
   * Longueur de la chaine de caractères
   */
  private Integer lenght;

  /**
   * Constructeur privé
   * @param identifier la clé
   * @param beginIndex l'index de debut de la chaine de caractère
   * @param lenght sa longueur
   */
  private ArtesisIndexEnum(final String identifier, final Integer beginIndex, final Integer lenght) {

    this.identifier = identifier;
    this.beginIndex = beginIndex;
    this.lenght = lenght;
  }

  /**
   * Getter de la chaine représentant la valeur associée
   * @return the identifier
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Getter de l'index du début de la chaine de caractères
   * @return the beginIndex
   */
  public Integer getBeginIndex() {
    return beginIndex;
  }

  /**
   * Getter de la longueur de la chaine de caractères
   * @return the lenght
   */
  public Integer getLenght() {
    return lenght;
  }
}
