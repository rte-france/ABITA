/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.abita.web.shared.ConstantsWEB;
import com.dto.AbstractDTO;
import com.dto.Group;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DTO des utilisateurs étendus
 *
 * @author
 */
public class ExtendedUserDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = -3195918975568566905L;

  /**
   * NNI of user
   */
  private String login;
  /**
   * first name
   */
  private String firstName;
  /**
   * last name
   */
  private String lastName;
  /**
   * email of user
   */
  private String email;
  /**
   * enable or disable user connection.
   */
  private Boolean activated;

  /**
   * enable or disable user connection.
   */
  private Boolean isThirdPartyContractManager;

  /** user groups as list */
  private List<Group> groupsAsList;

  /** Agence de l'utilisateur */
  private List<AgencyDTO> agencies;

  /** Statut de rattachement de toutes les agences */
  private boolean allAgencies;

  /**
   * @return the login
   */
  public String getLogin() {
    return login;
  }

  /**
   * @param login the login to set
   */
  public void setLogin(String login) {
    this.login = login;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the activated
   */
  public Boolean getActivated() {
    return activated;
  }

  /**
   * @param activated the activated to set
   */
  public void setActivated(Boolean activated) {
    this.activated = activated;
  }

  /**
   * @return the isThirdPartyContractManager
   */
  public Boolean getIsThirdPartyContractManager() {
    return isThirdPartyContractManager;
  }

  /**
   * @param isThirdPartyContractManager the isThirdPartyContractManager to set
   */
  public void setIsThirdPartyContractManager(Boolean isThirdPartyContractManager) {
    this.isThirdPartyContractManager = isThirdPartyContractManager;
  }

  /**
   * @return the groups
   */
  public String getGroups() {
    List<String> groupNames = new ArrayList<String>();
    for (Group group : groupsAsList) {
      groupNames.add(group.getIdentifier());
    }
    return StringUtils.join(groupNames, ',');
  }

  /**
   * @param groups the groups to set
   */
  public void setGroups(String groups) {
    groupsAsList = new ArrayList<Group>();

    if (groups != null) {
      List<String> selectedGroupList = Arrays.asList(StringUtils.split(groups, ','));

      for (String groupName : selectedGroupList) {
        if (Group.contains(groupName)) {
          groupsAsList.add(Group.valueOf(groupName.toUpperCase()));
        }
      }
    }
  }

  /**
   * @return the groupsAsList
   */
  public List<Group> getGroupsAsList() {
    return groupsAsList;
  }

  /**
   * @return the agencies
   */
  public List<AgencyDTO> getAgencies() {
    return agencies;
  }

  /**
   * @param agencies the agencies to set
   */
  public void setAgencies(List<AgencyDTO> agencies) {
    this.agencies = agencies;
  }

  /**
   * Permet de créer une chaine de caratère avec une liste d'agence
   * @return la chaine de caractère
   */
  public String getAgenciesToString() {
    String agenciesToString;

    if (allAgencies) {
      agenciesToString = ConstantsWEB.ALL_AGENCIES_VALUES;
    } else {
      List<String> agenciesNames = new ArrayList<String>();
      for (AgencyDTO agency : agencies) {
        agenciesNames.add(agency.getName());
      }
      agenciesToString = StringUtils.join(agenciesNames, ',');
    }

    return agenciesToString;
  }

  /**
   * @param allAgencies the allAgencies to set
   */
  public void setAllAgencies(boolean allAgencies) {
    this.allAgencies = allAgencies;
  }

}
