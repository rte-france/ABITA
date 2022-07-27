/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.data;

import com.dto.AbstractDTO;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestion d'une liste de SelectItem
 * @author
 * @param <T> l'objet de transfert de données
 */
public class ListSelectItems<T extends AbstractDTO> {

	/** Liste à afficher */
	private List<SelectItem> list;

	/** Map des données affichées */
	private Map<Long, T> mapData;

	/** Item sélectionné */
	private Long selectedItemId;

	/** Item par défaut */
	private Long defaultItemId;

	/**
	 * Constructeur
	 */
	public ListSelectItems() {
		super();
		list = new ArrayList<SelectItem>();
		mapData = new HashMap<Long, T>();
		defaultItemId = null;
		selectedItemId = null;
	}

	/**
	 * Ajout d'un item à la liste
	 * @param item item à ajouter
	 * @param label libellé de l'item
	 */
	public void addSelectItem(T item, String label) {
		list.add(new SelectItem(item.getId(), label));
		mapData.put(item.getId(), item);
	}

	/**
	 * Ajoute un item par défaut à la liste
	 * @param item item par défaut à ajouter
	 * @param label libellé de l'item
	 */
	public void addDefaultSelectItem(T item, String label) {
		list.add(new SelectItem(item.getId(), label));
		mapData.put(item.getId(), item);
		this.setDefaultItem(item);
	}

	/**
	 * Suppression d'un item
	 * Si l'item par défaut ou l'item sélectionné correspondent à l'item à supprimer : affectation à null
	 * @param item item à supprimer
	 */
	public void removeItem(T item) {
		this.removeItem(item.getId());
	}

	/**
	 * Suppression d'un item via son identifiant
	 * Si l'item par défaut ou l'item sélectionné correspondent à l'item à supprimer : affectation à null
	 * @param id identifiant de l'item à supprimer
	 */
	public void removeItem(Long id) {
		for (SelectItem item : list) {
			if (item.getValue().equals(id)) {
				list.remove(item);
				break;
			}
		}
		mapData.remove(id);

		if (defaultItemId != null && defaultItemId.equals(id)) {
			defaultItemId = null;
		}
		if (selectedItemId != null && selectedItemId.equals(id)) {
			selectedItemId = defaultItemId;
		}
	}

	/**
	 * Réinitialise la valeur de l'item sélectionné par la valeur par défaut
	 */
	public void reinitializeSelectedItem() {
		this.selectedItemId = this.defaultItemId;
	}

	/**
	 * Affecte l'item par défaut
	 * @param item item par défaut
	 */
	public void setDefaultItem(T item) {
		this.selectedItemId = item.getId();
		this.defaultItemId = item.getId();
	}

	/**
	 * Affecte l'item par défaut via son identifiant
	 * @param id identifiant de l'item par défaut
	 */
	public void setDefaultItemId(Long id) {
		this.selectedItemId = id;
		this.defaultItemId = id;
	}

	/**
	 * @return the list
	 */
	public List<SelectItem> getList() {
		return list;
	}

	/**
	 * Récupère l'item correspondant à l'id
	 * @return item correspondant
	 */
	public T getSelectedItem() {
		return mapData.get(selectedItemId);
	}

	/**
	 * @return the selectedItem
	 */
	public Long getSelectedItemId() {
		return selectedItemId;
	}

	/**
	 * @param selectedItemId the selectedItemid to set
	 */
	public void setSelectedItemId(Long selectedItemId) {
		this.selectedItemId = selectedItemId;
	}

}
