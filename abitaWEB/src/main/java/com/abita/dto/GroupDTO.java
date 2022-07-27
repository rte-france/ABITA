/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.dto.AbstractDTO;
import com.dto.Group;

/**
 * DTO groupe d'utilisateur
 * Cette classe permet de représenter un élément d'énumération de Group sous la forme d'un DTO
 * afin qu'il puisse être utilisé en combinaison avec le DTOConverter et faciliter sa manipulation dans la vue et le controleur
 * @author
 *
 */
public class GroupDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = -8469975326341575679L;

  /**
   * Constructeur à partir d'un Group
   * @param group le Group a utiliser
   */
  public GroupDTO(Group group) {
    identifier = group.getIdentifier();
    name = group.name();
    if (group.getParent() != null) {
      parentName = group.getParent().getIdentifier();
    } else {
      parentName = null;
    }
  }

  /** Nom du groupe */
  private String identifier;

  /** Nom du groupe */
  private String name;

  /** Nom du groupe parent */
  private String parentName;

  /**
   * @return the identifier
   */
  public String getIdentifier() {
    return identifier;
  }

  /**
   * @param identifier the identifier to set
   */
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the parentName
   */
  public String getParentName() {
    return parentName;
  }

  /**
   * @param parentName the parentName to set
   */
  public void setParentName(String parentName) {
    this.parentName = parentName;
  }

}
