package com.services.accessconfig.impl;

import com.dto.AccessConfigDTO;
import com.dto.AccessConfigPageDTO;
import com.dto.Group;
import com.dto.UserDTO;
import com.services.accessconfig.IAccessConfigService;
import com.services.common.util.SafetyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

/**
 * Implémentation de {@link IAccessConfigService}
 *
 * @author
 */
public class AccessConfigServiceImpl implements IAccessConfigService {

	/** Le loggueur */
	private static final Log LOGGER = LogFactory.getLog(com.services.accessconfig.impl.AccessConfigServiceImpl.class);

	/** L'objet contenant la configuration des accès aux pages */
	private AccessConfigDTO accessConfig;

    /**
     * Permet de déterminer si un utilisateur a accès à une page donnée
     *
     * @param user     l'utilisateur à tester
     * @param pageName la page
     * @return true si l'utilisateur a les droits d'accès à la page, false sinon
     */
    @Override
    public boolean hasUserAcessToPage(UserDTO user, String pageName) {
        Set<Group> allowedGroups = this.getAllowedGroupsForPage(pageName);
        return isUserInAtLeastOneGroup(user, allowedGroups);
    }


    /**
     * Permet de déterminer si un utilisateur dispose d'un groupe donné
     *
     * @param user  l'utilisateur
     * @param group le groupe à tester
     * @return true si l'utilisateur a le groupe, false sinon
     */
    @Override
    public boolean isUserHavingGroup(UserDTO user, Group group) {
        boolean isUserHavingGroup = false;
        for (Group userGroup : SafetyUtils.emptyIfNull(user.getGroups())) {
            if (group.equals(userGroup)) {
                isUserHavingGroup = true;
                break;
            } else {
                isUserHavingGroup = isUserGroupEqualToGroupOrWithinGroup(userGroup, group);
                if (isUserHavingGroup) {
                    break;
                }
            }
        }
        return isUserHavingGroup;
    }

    /**
     * Verifie si le groupe d'un utilisateur est égal ou appartient à un autre groupe
     * @param userGroup le groupe de l'utilisateur
     * @param group le groupe à tester
     * @return true si le groupe de l'utilisateur est égal ou appartient à l'autre groupe, false sinon
     */
    private boolean isUserGroupEqualToGroupOrWithinGroup(Group userGroup, Group group) {
        if (userGroup.equals(group)) {
            return true;
        } else if (userGroup.getParent() == null) {
            return false;
        } else {
            return isUserGroupEqualToGroupOrWithinGroup(userGroup.getParent(), group);
        }
    }

    /**
     * @param pageName nom technique de la page à tester
     * @return ensemble de groupes autorisés pour la page
     */
    private Set<Group> getAllowedGroupsForPage(final String pageName) {
        if (this.accessConfig == null || pageName == null) {
            return Collections.emptySet();
        }
        Set<Group> groups = new HashSet<Group>();
        for (final AccessConfigPageDTO page : this.accessConfig.getPages()) {
            if (page != null && page.getName() != null) {
                try {
                    if (pageName.matches(page.getName())) {
                        groups.addAll(page.getCoreGroups());
                    }
                } catch (PatternSyntaxException patternSyntaxException) {
                    LOGGER.error("Le pattern n'est pas correct", patternSyntaxException);
                }
            }
        }
        return groups;
    }

    /**
     * Vérifie que l'utilisateur fait partie d'au moins un des groupes spécifiés
     *
     * @param user L'utilisateur dont on veut vérifier les droits
     * @param allowedGroups La liste des noms des goupes autorisés
     * @return true si l'utilisateur fait partie de l'un des groupes
     */
    private boolean isUserInAtLeastOneGroup(final UserDTO user, final Set<Group> allowedGroups) {
        if (user == null) {
            return false;
        }
        for (final Group group : user.getGroups()) {
            if (isGroupWithinAllowedGroups(group, allowedGroups)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indique si le groupe spécifié fait partie d'au moins un des groupes autorisés.
     * Les groupes parents sont également vérifiés jusqu'à ce qu'un groupe soit autorisé ou qu'il n'y ait plus de parent
     *
     * @param group Le group à vérifier
     * @param allowedGroups La liste des noms de groupe autorisés
     * @return true si le groupe spécifié contient au moins l'un des groupes autorisés
     */
    private boolean isGroupWithinAllowedGroups(final Group group, final Set<Group> allowedGroups) {
        if (group == null || allowedGroups == null) {
            return false;
        }
        // vérification par rapport au nom du groupe
        if (allowedGroups.contains(group)) {
            return true;
        }
        // véification sur les groupes parents
        return isGroupWithinAllowedGroups(group.getParent(), allowedGroups);
    }

	/**
	 * @param accessConfig the accessConfig to set
	 */
	public void setAccessConfig(AccessConfigDTO accessConfig) {
		this.accessConfig = accessConfig;
	}

}
