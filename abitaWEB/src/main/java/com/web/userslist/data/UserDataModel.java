/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.userslist.data;

import com.web.common.constants.AccessConstants;
import com.web.userslist.constants.UserConstants;
import com.web.userslist.data.GroupDataModel;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * User data table model in user screen.
 * @author
 *
 */
public class UserDataModel implements Serializable {

	/**
	 * the serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** HASH_CODE_COMPLEMENT */
	private static final int HASH_CODE_COMPLEMENT = 31;

	/** Bundle des acces. */
	private static final ResourceBundle ACCESSBUNDLE = ResourceBundle.getBundle(AccessConstants.ACCESS_BUNDLE);

	/** user id */
	private Long id;

	/** user login */
	private String login;

	/** user first name */
	private String firstName;

	/** user last name */
	private String lastName;

	/** user email */
	private String email;

	/** user fax */
	private String fax;

	/** user phone */
	private String phone;

	/** user activated ? */
	private Boolean activated;

	/**
	 * Libelle de l'utilisateur active ou non
	 */
	private String activatedString;

	/** user location */
	private String location;

	/** user groups */
	private GroupDataModel[] groups;

	/**
	 * Default contructor.
	 */
	public UserDataModel() {
		//
	}

	/**
	 * Fields contructor.
	 * @param login login
	 * @param firstName first name
	 * @param lastName last name
	 * @param location location
	 * @param email email
	 * @param activated true if activated user, false otherwise
	 * @param id identifiant
	 * @param phone phone number
	 * @param fax fax number
	 */
	public UserDataModel(Long id, String login, String firstName, String lastName, String email, String phone,
			String fax, String location, Boolean activated) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.fax = fax;
		this.activated = activated;
		this.login = login;
		this.location = location;
		if (activated) {
			this.activatedString = ACCESSBUNDLE.getString(UserConstants.USER_LIST_ACTIVATION_ON);
		} else {
			this.activatedString = ACCESSBUNDLE.getString(UserConstants.USER_LIST_ACTIVATION_OFF);
		}

	}

	/**
	 * setter of location.
	 * @param location user location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * getter of location
	 * @return user location
	 */
	public String getLocation() {
		return location;
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
	 * getter of login
	 * @return user login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * setter of login
	 * @param login user login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * getter of id.
	 * @return user id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * setter of id.
	 * @param id user id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * getter of first name.
	 * @return user first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * setter of first name.
	 * @param firstName user first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * getter of last name.
	 * @return user last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * setter of last name.
	 * @param lastName user last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * getter of email.
	 * @return user email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * setter of email.
	 * @param email user email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * getter of activated.
	 * @return true if user is activated, false otherwise
	 */
	public Boolean getActivated() {
		return activated;
	}

	/**
	 * setter of activated.
	 * @param activated true if user is activated, false otherwise
	 */
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	/**
	 * Getter du libelle de l'utilisateur active ou non
	 * @return le libelle de l'utilisateur active ou non
	 */
	public String getActivatedString() {
		return activatedString;
	}

	/**
	 * Setter du libelle de l'utilisateur active ou non
	 * @param activatedString activated user label
	 */

	public void setActivatedString(String activatedString) {
		this.activatedString = activatedString;
	}

	/**
	 * setter of groups
	 * @param groups user groups
	 */
	public void setGroups(GroupDataModel[] groups) {
		this.groups = Arrays.copyOf(groups, groups.length);
	}

	/**
	 * getter of groups.
	 * @return user groups
	 */
	public GroupDataModel[] getGroups() {
		return Arrays.copyOf(groups, groups.length);
	}

	/**
	 * getter of groups display.
	 * @return user groups display string
	 */
	public String getGroupsDisplay() {
		return createGroupsDisplay(groups);
	}

	/**
	 * concat name of groups separat by coma.
	 * @param set groups
	 * @return user groups display string
	 */
	private String createGroupsDisplay(GroupDataModel[] set) {
		return StringUtils.arrayToDelimitedString(set, ", ");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof UserDataModel)) {
			return false;
		}

		UserDataModel compare = (UserDataModel) obj;

		if (compare.login == null) {
			return this.login == null;
		}

		return compare.login.equals(this.login);
	}

	@Override
	public int hashCode() {
		int hash = 1;
		return hash * HASH_CODE_COMPLEMENT + login.hashCode();
	}

}
