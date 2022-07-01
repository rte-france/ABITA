package com.dto;

import com.dto.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un utilisateur de l'application
 *
 * @author
 */
public class UserDTO extends AbstractDTO {
	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = 3954235274802435626L;

	/**
	 * Numéro de version
	 */
	private Integer version;

	/**
	 * L'identifiant unique de connexion
	 */
	private String login;

	/**
	 * Le prénom
	 */
	private String firstName;

	/**
	 * Le nom de famille
	 */
	private String lastName;

	/**
	 * Adresse
	 */
	private String location;

	/**
	 * Le mot de passe (peut être crypté)
	 */
	private String passwd;

	/**
	 * Adresse mail
	 */
	private String email;

	/**
	 * Si l'utilisateur est activé, il pourra accéder à l'application
	 */
	private Boolean activated;

	/**
	 * Le thème que l'utilisateur utilise
	 */
	private ThemeDTO theme;

	/**
	 * L'identifiant logique
	 */
	private String logicalId;

	/** Le numéro de téléphone */
	private String phone;

	/** Le numéro de fax */
	private String fax;

	/**
	 * La liste des groupes auxquels l'utilisateur appartient
	 */
	private List<Group> groups = new ArrayList<Group>();

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return this.version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return this.login;
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
		return this.firstName;
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
		return this.lastName;
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
		return this.location;
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
		return this.passwd;
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
		return this.email;
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
		return this.activated;
	}

	/**
	 * @param activated the activated to set
	 */
	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	/**
	 * @return the theme
	 */
	public ThemeDTO getTheme() {
		return this.theme;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(ThemeDTO theme) {
		this.theme = theme;
	}

	/**
	 * @return the logicalId
	 */
	public String getLogicalId() {
		return this.logicalId;
	}

	/**
	 * @param logicalId the logicalId to set
	 */
	public void setLogicalId(String logicalId) {
		this.logicalId = logicalId;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return this.fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the groups
	 */
	public List<Group> getGroups() {
		return this.groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
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
		UserDTO other = (UserDTO) obj;
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
        int result = prime;
		if (login != null) {
			result += login.hashCode();
		}
		return result;
	}
}
