package com.web.login.impl;

import java.io.Serializable;

import com.services.common.util.SafetyUtils;
import com.web.login.impl.AccessSupport;
import org.apache.commons.lang.StringUtils;

import com.dto.Group;
import com.dto.UserDTO;
import com.services.accessconfig.IAccessConfigService;

/**
 * A controller of security.
 * @author
 *
 */
public class SecurityController implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 7729707795902452059L;

    /** service de consultation des accès utilisateur */
    private IAccessConfigService accessConfigService;

	/**
	 *
	 * @return return true if User are connected
	 */
	public boolean getLoginStatus() {
		return com.web.login.impl.AccessSupport.getCurrentUser() != null;
	}

	/**
	 *
	 * @return return true if User are disconnected
	 */
	public boolean getLogoutStatus() {
		return !this.getLoginStatus();
	}

	/**
	 *
	 * @return return user's login if user is connect or null
	 */
	public String getUserLogin() {
		UserDTO user = com.web.login.impl.AccessSupport.getCurrentUser();
		if (user != null) {
			return user.getLogin();
		} else {
			return null;
		}
	}

	/**
	 *
	 * @return  return user's class if user is connect or null
	 */
	public UserDTO getCurrentUser() {
		return com.web.login.impl.AccessSupport.getCurrentUser();
	}

	/**
	 * Indique si l'utilisateur en cours à le droit d'accèder à l'élément
	 * Cette méthode prend en charge des arguments de type {@link String} ou {@link Group}
	 *
	 * @param groups La liste des groupes ayant accès à l'élément. Peut être de type String ou Group
	 *
	 * @return true si l'utilisateur a le droit de voir l'élément
	 */
	public boolean access(final Object... groups) {

		boolean hasRight = false;

		UserDTO currentUser = AccessSupport.getCurrentUser();

		/** anonymous */
		if (currentUser == null) {
			for (Object group : groups) {
                if (isAnonymous(group)) {
                    hasRight = true;
                    break;
                }
			}
		} else {
			Group group = null;
            String groupName;
			for (Object groupObject : SafetyUtils.emptyIfNull(groups)) {
                groupName = StringUtils.trim((String) groupObject);
                for (Group groupToFind : Group.values()) {
                    if (groupName.equals(groupToFind.getIdentifier())) {
                        group = groupToFind;
                        break;
                    }
                }

				if (group != null) {
					if (accessConfigService.isUserHavingGroup(currentUser, group)) {
						hasRight = true;
						break;
					}
				}
			}
		}

		return hasRight;
	}

    /**
     * @param group le groupe à tester
     * @return true si le groupe est le groupe anonyme, false sinon
     */
    private boolean isAnonymous(Object group) {
        boolean hasRight = false;
        if (Group.ANONYMOUS.getIdentifier().equals(group)) {
            hasRight = true;
        }
        return hasRight;
    }


    /**
     * @param accessConfigService the accessConfigService to set
     */
    public void setAccessConfigService(IAccessConfigService accessConfigService) {
        this.accessConfigService = accessConfigService;
    }
}
