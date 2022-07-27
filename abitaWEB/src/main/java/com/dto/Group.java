/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Groupe à appliquer à un utilisateur du framework
 * Surchargé dans le cas d'ABITA afin de porter les groupes fonctionnels de l'application
 *
 * ATTENTION :
 * -----------
 * Les groupes ROOT, ANONYMOUS, REGISTERED et ADMINISTRATEUR sont requis
 * pour que le fonctionnement de la sécurité du framework soit opérationnel.
 *
 * @author
 */
public enum Group {

  /** Groupe racine */
  ROOT("root", null),

  /** Groupe anonyme */
  ANONYMOUS("anonymous", ROOT),

  /** Utilisateur enregistré */
  REGISTERED("registered", ROOT),

  /** Administrateur du framework */
  ADMINISTRATEUR("administrateur", REGISTERED),

  /** Administrateur du framework */
  CONSULTATION("consultation", REGISTERED),

  /** Administrateur du framework */
  UTILISATEUR("utilisateur", REGISTERED),

  /** Groupe par défaut fonctionnel de l'application */
  SANS_GROUPE("sans groupe", REGISTERED);

  /** identifiant technique */
  private String identifier;

  /** Groupe parent */
  private Group parent;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(Group.class);

  /**
   * Constructeur paramétré
   *
   * @param identifier l'identifiant du groupe : son nom
   * @param parent le groupe parent
   */
  private Group(final String identifier, final Group parent) {
    this.identifier = identifier;
    this.parent = parent;
  }

  /**
   * @return les groupes parents
   */
  public Group getParent() {
    return parent;
  }

  /**
   * @return identifier.
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * Vérifie la présence de l'élément indiqué en paramètre
   * @param test le nom du groupe a tester
   * @return si le groupe existe
   */
  public static boolean contains(String test) {
    boolean result = true;
    try {
      Group.valueOf(test.toUpperCase());
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      // Si une exception se produit, l'élément n'est pas présent
      result = false;
    }
    return result;

  }

  /**
   * Renvoie tous les groupes ayant pour parent groupName
   * @param parentName nom du groupe parent
   * @return Liste des groupes ayant pour parent groupName
   */
  public static List<Group> getChilds(String parentName) {
    if (parentName == null) {
      return getChilds(ROOT);
    } else if (contains(parentName)) {
      return getChilds(Group.valueOf(parentName.toUpperCase()));
    }
    return new ArrayList<Group>();
  }

  /**
   * Renvoie tous les groupes ayant pour parent le groupe <code>parent</code>
   * @param parent le groupe parent
   * @return Liste des groupes ayant pour parent le groupe <code>parent</code>
   */
  public static List<Group> getChilds(Group parent) {
    List<Group> result = new ArrayList<Group>();
    Group testedParent;
    for (Group group : Group.values()) {
      testedParent = group.getParent();
      if (testedParent == parent) {
        result.add(group);
      }
    }
    return result;

  }
}
