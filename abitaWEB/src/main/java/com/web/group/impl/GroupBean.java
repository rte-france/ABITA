/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.group.impl;

import org.primefaces.model.TreeNode;

import java.io.Serializable;

/**
 * Bean pour l'écran de consultation des groupes
 *
 * @author
 */
public class GroupBean implements Serializable {
	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = 4049233429959477654L;

	/** L'arbre des groupes */
	private TreeNode groups;

	/**
	 * Getter
	 *
	 * @return la valeur de {@link GroupBean#groups}
	 */
	public TreeNode getGroups() {
		return groups;
	}

	/**
	 * Setter de {@link GroupBean#groups}
	 *
	 * @param groups la nouvelle valeur à affecter
	 */
	public void setGroups(final TreeNode groups) {
		this.groups = groups;
	}
}
