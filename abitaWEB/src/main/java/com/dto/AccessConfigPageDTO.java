package com.dto;

import java.util.ArrayList;
import java.util.List;

import com.dto.Group;
import com.services.common.util.SafetyUtils;

/**
 * Classe représentant l'objet configuration l'accès à une page
 *
 * @author
 */
public class AccessConfigPageDTO {
	/** le nom de la règle */
	private String name;

	/** la liste des noms des groupes */
	private List<String> groups;

    /** groupes rattachés, calculés à partir des noms de groupes */
    private List<Group> coreGroups;

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
	 * @return the groups
	 */
	public List<String> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(List<String> groups) {
		this.groups = groups;
        fillCoreGroups();
	}

    /**
     * @return the coreGroups
     */
    public List<Group> getCoreGroups() {
        return coreGroups;
    }

    /**
     * Méthode de remplissage des groupes à partir des noms fournis par configuration
     */
    private void fillCoreGroups() {
        coreGroups = new ArrayList<Group>();
        for (String groupName : SafetyUtils.emptyIfNull(groups)) {
            for (Group group : Group.values()) {
                if (groupName.equals(group.getIdentifier())) {
                    coreGroups.add(group);
                    break;
                }
            }
        }
    }
}
