/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.data;

import org.apache.commons.lang.StringUtils;

import javax.faces.model.SelectItem;
import java.io.Serializable;

/**
 * Objet utilisé pour l'affichage de libellé en String afin qu'ils soient pris comme des SelectItems par jsf.
 *
 * @author
 */
public class StringSelectItem extends SelectItem implements Serializable {

	/**
	 * Numéro de version
	 */
	private static final long serialVersionUID = 437225578970635537L;

	/**
	 * Constructeur.
	 *
	 * @param label un libellé
	 */
	public StringSelectItem(String label) {
		this.setLabel(label);
		this.setValue(label);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		StringSelectItem objFaces = (StringSelectItem) obj;
		boolean returnVal = false;
		if (objFaces != null) {
			returnVal = StringUtils.equals(this.getLabel(), objFaces.getLabel());
		}
		return returnVal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getLabel().hashCode() + this.getValue().hashCode();
	}

}
