/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.group.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.web.group.impl.GroupBean;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.dto.Group;

/**
 * Contrôleur pour la consultation des groupes
 *
 * @author
 */
public class GroupController implements Serializable {

	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = 8812180125833348931L;

	/** Le bean associé */
	private com.web.group.impl.GroupBean bean;

	/**
	 * Construction de l'arbre à afficher à partir de l'enum {@link Group}
	 */
	@PostConstruct
	@SuppressWarnings("unused")
	private void init() {
		if (bean.getGroups() == null) {
			// une map temporaire pour construire l'arbre
			final Map<String, TreeNode> mappedGroups = new HashMap<String, TreeNode>();

			final TreeNode hiddenNode = new DefaultTreeNode(null, null);

			for (Group group : Group.values()) {
                TreeNode node;
                if (group.getParent() == null) {
                    node = new DefaultTreeNode(group, hiddenNode);
				} else {
                    // récupération du parent
                    TreeNode parentNode = mappedGroups.get(group.getParent().getIdentifier());
                    // création du noeud à partir du groupe actuel et du parent
                    node = new DefaultTreeNode(group, parentNode);
                }
				node.setExpanded(true);
				// sauvegarde du noeud pour le récupérer plus tard s'il a des enfants
				mappedGroups.put(group.getIdentifier(), node);
			}

			bean.setGroups(hiddenNode);
		}
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link GroupController#bean}
	 */
	public com.web.group.impl.GroupBean getBean() {
		return bean;
	}

	/**
	 * Setter de {@link GroupController#bean}
	 *
	 * @param bean la nouvelle valeur à affecter
	 */
	public void setBean(final GroupBean bean) {
		this.bean = bean;
	}
}
