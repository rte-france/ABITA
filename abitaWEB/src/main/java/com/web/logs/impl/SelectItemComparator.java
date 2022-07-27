/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.logs.impl;

import com.web.logs.util.SelectItemHelper;
import org.apache.commons.lang.ObjectUtils;

import javax.faces.model.SelectItem;
import java.util.Comparator;

/**
 * Comparateur pour réorganiser les listes de filtrages sur les colonnes
 *
 * Se base sur le libellé du {@link SelectItem} afin de trier sur la valeur affichée et non sur la valeur interne
 *
 * @author
 */
public class SelectItemComparator implements Comparator<SelectItem> {

	/** Valeur à retourner lorsque le comparateur indique que le premier élément est à placer avant le second */
	public static final int COMPARATOR_FIRST_ARGUMENT_LESSER = -1;

	/** Valeur à retourner lorsque le comparateur indique que le second élément est à placer avant le premier */
	public static final int COMPARATOR_SECOND_ARGUMENT_LESSER = 1;

	@Override
	public int compare(SelectItem o1, SelectItem o2) {
		boolean isNoChoiceItem1 = SelectItemHelper.isNoChoiceItem(o1);
		boolean isNoChoiceItem2 = SelectItemHelper.isNoChoiceItem(o2);

		// les deux objets sont égaux
		if (o1.equals(o2)) {
			return 0;
		}

		if (isNoChoiceItem1 && isNoChoiceItem2) {
			return 0;
		} else if (isNoChoiceItem1) {
			return COMPARATOR_FIRST_ARGUMENT_LESSER;
		} else if (isNoChoiceItem2) {
			return COMPARATOR_SECOND_ARGUMENT_LESSER;
		}

		// les deux valeurs ne sont pas nulles
		// comparaison des libellés
		if (o1.getLabel() == null && o2.getLabel() == null) {
			return 0;
		}

		// récupération du libellé affiché
		// ne pas oublier que la valeur est affichée si le libellé n'est pas défini
		String label1 = o1.getLabel();
		if (label1 == null) {
			label1 = ObjectUtils.toString(o1.getValue());
		}
		String label2 = o2.getLabel();
		if (label2 == null) {
			label2 = ObjectUtils.toString(o2.getValue());
		}

		return label1.compareTo(label2);
	}
}
