/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.userslist.data;

import com.dto.UsersSearchDTO;
import com.web.common.data.StringSelectItem;
import com.web.userslist.data.GroupDataModel;
import com.web.userslist.data.GroupsListDataModel;
import com.web.userslist.data.UserDataModel;
import com.web.userslist.data.UsersListDataModel;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

/**
 * @author
 *
 * RM_UTI_012 : Gestion des groupes par défaut
 */
public class UserListViewBean implements Serializable {
	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = 7913974713959647782L;

	/** La liste des identifiants des goupes sélectionnés */
	private List<String> defaultGroupsId;

	/**
	 * result selection
	 */
	private com.web.userslist.data.UserDataModel selectedUser;

	/** Modele de donnees de la liste des groupes */
	private GroupsListDataModel groupsListDataModel;

	/**
	 * Liste de groupes
	 */
	private List<SelectItem> groups;

	/**
	 * List of users states
	 */
	private List<StringSelectItem> activatedList;

	/**
	 * Regroupe les informations de recherche des utilisateurs
	 */
	private UsersSearchDTO usersSearch = new UsersSearchDTO();

	/**
	 * The data model
	 */
	private List<com.web.userslist.data.UserDataModel> users;

	/** Modele de donnees de la liste d'utilisateurs */
	private UsersListDataModel usersListDataModel;

	/**
	 * Constructeur
	 */
	public UserListViewBean() {
		super();
	}

	/* *************************************************************************
	 * GETTERS / SETTERS
	 ************************************************************************ */

	/**
	 * @return the groupsListDataModel
	 */
	public GroupsListDataModel getGroupsListDataModel() {
		return groupsListDataModel;
	}

	/**
	 * Setter de groupsListDataModel
	 *
	 * @param groupsListDataModel la nouvelle valeur
	 */
	public void setGroupsListDataModel(GroupsListDataModel groupsListDataModel) {
		this.groupsListDataModel = groupsListDataModel;
	}

	/**
	 * getter of selected user in user's list.
	 * @return un UserDataModel
	 */
	public com.web.userslist.data.UserDataModel getSelectedUser() {
		return selectedUser;
	}

	/**
	 * setter of selected user in user's list.
	 * @param selectedUser user selected
	 */
	public void setSelectedUser(com.web.userslist.data.UserDataModel selectedUser) {
		this.selectedUser = selectedUser;
	}

	/**
	 * setter of Selected groups.
	 * @param selectedGroups selected groups
	 */
	public void setSelectedGroups(com.web.userslist.data.GroupDataModel[] selectedGroups) {
		if (this.selectedUser != null) {
			this.selectedUser.setGroups(selectedGroups);
		}
	}

	/**
	 * Renvoie la liste des groupes de l'utilisateur sélectionné ou null s'il n'y a pas d'utilisateur sélectionné
	 *
	 * @return la liste des groupes de l'utilisateur sélectionné ou null
	 */
	public GroupDataModel[] getSelectedGroups() {
		if (this.selectedUser == null) {
			return null;
		}
		return this.selectedUser.getGroups();
	}

	/**
	 * @return the usersListDataModel
	 */
	public UsersListDataModel getUsersListDataModel() {
		return usersListDataModel;
	}

	/**
	 * Setter de usersListDataModel
	 *
	 * @param usersListDataModel la nouvelle valeur
	 */
	public void setUsersListDataModel(UsersListDataModel usersListDataModel) {
		this.usersListDataModel = usersListDataModel;
	}

	/**
	 * Getter du DTO regroupant les informations de recherche des utilisateurs
	 * @return the usersSearch
	 */
	public UsersSearchDTO getUsersSearch() {
		return usersSearch;
	}

	/**
	 * Sets the users search.
	 *
	 * @param usersSearch the new users search
	 */
	public void setUsersSearch(UsersSearchDTO usersSearch) {
		this.usersSearch = usersSearch;
	}

	/**
	 * @return the users
	 */
	public List<com.web.userslist.data.UserDataModel> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<UserDataModel> users) {
		this.users = users;
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
	 * @return the groups
	 */
	public List<SelectItem> getGroups() {
		return groups;
	}

	/**
	 * Getter de {@link #groups}
	 *
	 * @param groups la valeur de la propriété
	 */
	public void setGroups(List<SelectItem> groups) {
		this.groups = groups;
	}

	/**
	 * @return the defaultGroupsId
	 */
	public List<String> getDefaultGroupsId() {
		return defaultGroupsId;
	}

	/**
	 * @param defaultGroupsId the defaultGroupsId to set
	 */
	public void setDefaultGroupsId(List<String> defaultGroupsId) {
		this.defaultGroupsId = defaultGroupsId;
	}
}
