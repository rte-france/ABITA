/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto;

import com.web.common.constants.AccessConstants;
import com.web.common.data.StringSelectItem;
import com.web.userslist.constants.UserConstants;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Classe regroupant les informations de recherche des utilisateurs
 * @author
 *
 */
public class UsersSearchDTO extends AbstractDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5807147555488552989L;

	/** Bundle des acces. */
	private static final ResourceBundle ACCESSBUNDLE = ResourceBundle.getBundle(AccessConstants.ACCESS_BUNDLE);

	/**
	 * NNI
	 */
	private String nni;

	/**
	 * Prénom
	 */
	private String firstName;

	/**
	 * Nom
	 */
	private String name;

	/**
	 * The phone.
	 */
	private String phone;

	/**
	 * The fax.
	 */
	private String fax;

	/**
	 * Email
	 */
	private String email;

	/**
	 * Lieu
	 */
	private String location;

	/**
	 * Liste de groupes sélectionnés
	 */
	private List<String> groupsSelected;

	/**
	 * Chaine de l'état des utilisateurs sélectionnée
	 */
	private StringSelectItem activatedSelected;

	/**
	 * Constructeur
	 */
	public UsersSearchDTO() {
		super();
	}

	/**
	 * Getter du NNI
	 * @return the nni
	 */
	public String getNni() {
		return nni;
	}

	/**
	 * Setter du NNI
	 * @param nni the nni to set
	 */
	public void setNni(String nni) {
		this.nni = nni;
	}

	/**
	 * Getter du prénom
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter du prénom
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the fax.
	 *
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Sets the fax.
	 *
	 * @param fax the new fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Getter du nom
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter du nom
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter de l'email
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter de l'email
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter du lieu
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Setter du lieu
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Getter de la liste de groupes sélectionnés
	 * @return the groupsSelected
	 */
	public List<String> getGroupsSelected() {
		return groupsSelected;
	}

	/**
	 * Setter de la liste de groupes sélectionnés
	 * @param groupsSelected the groupsSelected to set
	 */
	public void setGroupsSelected(List<String> groupsSelected) {
		this.groupsSelected = groupsSelected;
	}

	/**
	 * Getter de la chaine de l'état des utilisateurs sélectionnée
	 * @return the activatedSelected
	 */
	public StringSelectItem getActivatedSelected() {
		return activatedSelected;
	}

	/**
	 * Setter de la chaine de l'état des utilisateurs sélectionnée
	 * @param activatedSelected the activatedSelected to set
	 */
	public void setActivatedSelected(StringSelectItem activatedSelected) {
		this.activatedSelected = activatedSelected;
	}

	/**
	 * Getter du booléen flagant si les utilisateurs sélectionnés sont activés(VRAI), désactivés(FAUX) ou tous les états (null)
	 * @return the activated
	 */
	public Boolean isActivated() {
		if (null != activatedSelected
				&& !activatedSelected.getValue().equals(ACCESSBUNDLE.getString(UserConstants.USER_LIST_ACTIVATION_ALL))) {
			if (activatedSelected.getValue().equals(ACCESSBUNDLE.getString(UserConstants.USER_LIST_ACTIVATION_ON))) {
				return true;
			}
			return false;
		}
		return null;
	}

}
