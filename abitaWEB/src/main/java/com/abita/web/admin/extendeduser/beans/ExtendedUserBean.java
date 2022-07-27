/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.extendeduser.beans;

import com.abita.dto.AgencyDTO;
import com.abita.dto.ExtendedUserDTO;
import com.abita.dto.GroupDTO;
import com.abita.dto.unpersist.ExtendedUserCriteriaDTO;
import com.web.common.data.StringSelectItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bean en relation avec la page /pages/administration/referentiel/extendeduser.xhtml
 *
 * @author
 */
public class ExtendedUserBean implements Serializable {

  /** serialVersionUID */
  private static final long serialVersionUID = -8129930137909234753L;

  /**
   *
   * PROPRIETES
   *
   */

  /** Liste des groupes */
  private List<GroupDTO> groupList;

  /** Liste des agences */
  private List<AgencyDTO> agencyList;

  /** Liste des choix des agences */
  private List<AgencyDTO> agencyChoices;

  /** L'utilisateur sélectionné en cours d'édition est-il gestionnaire de contrat tiers ? */
  private ExtendedUserDTO selectedUser;

  /** La liste des identifiants des goupes sélectionnés pour l'utilisateur sélectionné */
  private List<GroupDTO> selectedGroups;

  /** L'agence sélectionnée pour l'utilisateur sélectionné */
  private AgencyDTO selectedAgency;

  /** L'utilisateur sélectionné est-il gestionnaire de contrat tiers ? */
  private Boolean isThirdPartyContractManager;

  /** Les critères de recherches utilisés pour la recherche */
  private ExtendedUserCriteriaDTO extendedUserCriteria = new ExtendedUserCriteriaDTO();

  /** La liste des utilisateurs correspondant au résultat de la recherche */
  private List<ExtendedUserDTO> extendedUserDTOList = new ArrayList<ExtendedUserDTO>();

  /**
   * List of users states
   */
  private List<StringSelectItem> yesNoOptions;

  /**
   * List of users states
   */
  private List<StringSelectItem> activatedList;

  /**
   * List of users states
   */
  private List<StringSelectItem> thirdPartyContractManagerOptionList;

  /**
   * @return the extendedUserDTOList
   */
  public List<ExtendedUserDTO> getExtendedUserDTOList() {
    return extendedUserDTOList;
  }

  /**
   * @param extendedUserDTOList the extendedUserDTOList to set
   */
  public void setExtendedUserDTOList(List<ExtendedUserDTO> extendedUserDTOList) {
    this.extendedUserDTOList = extendedUserDTOList;
  }

  /**
   * @return the selectedUser
   */
  public ExtendedUserDTO getSelectedUser() {
    return selectedUser;
  }

  /**
   * @param selectedUser the selectedUser to set
   */
  public void setSelectedUser(ExtendedUserDTO selectedUser) {
    this.selectedUser = selectedUser;
  }

  /**
   * @return the extendedUserCriteria
   */
  public ExtendedUserCriteriaDTO getExtendedUserCriteria() {
    return extendedUserCriteria;
  }

  /**
   * @param extendedUserCriteria the extendedUserCriteria to set
   */
  public void setExtendedUserCriteria(ExtendedUserCriteriaDTO extendedUserCriteria) {
    this.extendedUserCriteria = extendedUserCriteria;
  }

  /**
   * @return the defaultGroupsId
   */
  public List<GroupDTO> getSelectedGroups() {
    return selectedGroups;
  }

  /**
   * @param selectedGroups the selectedGroups to set
   */
  public void setSelectedGroups(List<GroupDTO> selectedGroups) {
    this.selectedGroups = selectedGroups;
  }

  /**
   *  * @return the defaultGroupsId
   */
  public GroupDTO[] getSelectedGroupsAsArray() {
    if (selectedGroups != null) {
      return selectedGroups.toArray(new GroupDTO[selectedGroups.size()]);
    }
    return new GroupDTO[0];
  }

  /**
   * @param selectedGroup the selectedGroups to set
   */
  public void setSelectedGroupsAsArray(GroupDTO[] selectedGroup) {
    selectedGroups = Arrays.asList(selectedGroup);
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
   * @return the groupList
   */
  public List<GroupDTO> getGroupList() {
    return groupList;
  }

  /**
   * @param groupList the groupList to set
   */
  public void setGroupList(List<GroupDTO> groupList) {
    this.groupList = groupList;
  }

  /**
   * @return the yesNoOptions
   */
  public List<StringSelectItem> getYesNoOptions() {
    return yesNoOptions;
  }

  /**
   * @param yesNoOptions the yesNoOptions to set
   */
  public void setYesNoOptions(List<StringSelectItem> yesNoOptions) {
    this.yesNoOptions = yesNoOptions;
  }

  /**
   * @return the activatedList
   */
  public List<StringSelectItem> getActivatedList() {
    return activatedList;
  }

  /**
   * @param activatedList the activatedList to set
   */
  public void setActivatedList(List<StringSelectItem> activatedList) {
    this.activatedList = activatedList;
  }

  /**
   * @return the thirdPartyContractManagerOptionList
   */
  public List<StringSelectItem> getThirdPartyContractManagerOptionList() {
    return thirdPartyContractManagerOptionList;
  }

  /**
   * @param thirdPartyContractManagerOptionList the thirdPartyContractManagerOptionList to set
   */
  public void setThirdPartyContractManagerOptionList(List<StringSelectItem> thirdPartyContractManagerOptionList) {
    this.thirdPartyContractManagerOptionList = thirdPartyContractManagerOptionList;
  }

  /**
   * @return the agencyList
   */
  public List<AgencyDTO> getAgencyList() {
    return agencyList;
  }

  /**
   * @param agencyList the agencyList to set
   */
  public void setAgencyList(List<AgencyDTO> agencyList) {
    this.agencyList = agencyList;
  }

  /**
   * @return the selectedAgency
   */
  public AgencyDTO getSelectedAgency() {
    return selectedAgency;
  }

  /**
   * @param selectedAgency the selectedAgency to set
   */
  public void setSelectedAgency(AgencyDTO selectedAgency) {
    this.selectedAgency = selectedAgency;
  }

  /**
   * @return the agencyChoices
   */
  public List<AgencyDTO> getAgencyChoices() {
    return agencyChoices;
  }

  /**
   * @param agencyChoices the agencyChoices to set
   */
  public void setAgencyChoices(List<AgencyDTO> agencyChoices) {
    this.agencyChoices = agencyChoices;
  }

}
