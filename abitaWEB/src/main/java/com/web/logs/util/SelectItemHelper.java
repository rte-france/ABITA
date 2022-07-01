package com.web.logs.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import javax.faces.model.SelectItem;

/**
 * Classe utilitaire pour les éléments des listes d'options JSF
 *
 * @author
 */
public final class SelectItemHelper {
	/**
	 * Constructeur privé
	 */
	private SelectItemHelper() {
	}

	/**
	 * Indique si le {@link SelectItem} spécifié peut être considéré comme un élement de non filtrage/sélection
	 *
	 * @param s L'élément à analyser
	 *
	 * @return true si l'élément est null, vide, possède une valeur nulle ou est considéré comme une option de non sélection @see {@link SelectItem#isNoSelectionOption()}
	 */
	public static boolean isNoChoiceItem(final SelectItem s) {
		return s == null || StringUtils.EMPTY.equals(ObjectUtils.toString(s.getValue())) || s.isNoSelectionOption();
	}
}
