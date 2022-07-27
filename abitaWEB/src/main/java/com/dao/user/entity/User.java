/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.user.entity;

import com.dao.common.entity.AbstractEntity;
import com.dao.theme.entity.Theme;
import com.dto.Group;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * An application's user
 * @author
 */
public class User extends AbstractEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8479209919529469131L;

	/**
	 * optimistic versionning
	 */
	private Integer version;
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
	 * location
	 */
	private String location;
	/**
	 * must be empty for ldap or SSO conection or encryted for DB connection.
	 */
	private String passwd;
	/**
	 * email of user
	 */
	private String email;
	/**
	 * enable or disable user connection.
	 */
	private Boolean activated;

	/**
	 * Theme associated with the user
	 */
	private Theme theme;

	/**
	 * user logical id.e
	 */
	private String logicalId;

	/** The phone. */
	private String phone;

	/** The fax. */
	private String fax;

	/** User groups */
	private String groups;

    /** user groups as list */
    private List<Group> groupsAsList;

	/**
	 *
	 * Construct User.
	 */
	public User() {
        super();
		this.groupsAsList = new LinkedList<Group>();
	}

	/**
	 * Getter
	 *
	 * @return value of {@link User#version}
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Setter of {@link User#version}
	 *
	 * @param version the new value
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
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
	 * @return the logicalId
	 */
	public String getLogicalId() {
		return logicalId;
	}

	/**
	 * @param logicalId the logicalId to set
	 */
	public void setLogicalId(String logicalId) {
		this.logicalId = logicalId;
	}

	/**
	 * Getter of the theme's user
	 * @return the theme
	 */
	public Theme getTheme() {
		return theme;
	}

	/**
	 * Setter of the theme's user
	 * @param theme the theme to set
	 */
	public void setTheme(Theme theme) {
		this.theme = theme;
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

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (login == null) {
            if (other.login != null) {
                return false;
            }
        } else if (!login.equals(other.login)) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (login != null) {
            result += login.hashCode();
        }
        return result;
    }

    /**
     * @return groupsAsList.
     */
    public List<Group> getGroupsAsList() {
        return groupsAsList;
    }

    /**
     * @return groups.
     */
    public String getGroups() {
        return groups;
    }

    /**
     * @param groupsAsList the groupsAsList to set
     */
    public void setGroupsAsList(List<Group> groupsAsList) {
        this.groupsAsList = groupsAsList;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(String groups) {
        this.groups = groups;
    }
}
