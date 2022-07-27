/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto;

/**
 * Représente un thème disponible pour l'utilisateur
 *
 * @author
 */
public class ThemeDTO extends AbstractDTO {
	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = 2724027808084142164L;

	/**
	 * Le libellé du thème (nom affiché)
	 */
	private String label;

	/**
	 * Le nom technique du thème
	 */
	private String name;

	/**
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
