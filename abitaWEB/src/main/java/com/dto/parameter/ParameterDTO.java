/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto.parameter;

import com.dto.AbstractDTO;

import java.io.Serializable;

/**
 * Paramètre de l'application
 *
 * @author
 */
public class ParameterDTO extends AbstractDTO implements Serializable {

	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = -8789950502027946797L;

	/**
	 * Numéro de version
	 * Incrémenté à chaque modification
	 */
	private Integer version;

	/** Le nom du paramètre */
	private String name;

	/** Le libellé à afficher */
	private String displayName;

	/** Le commentaire */
	private String comment;

	/** Valeur initiale du paramètre */
	private String initialValue;

	/** Valeur actuelle du paramètre */
	private String value;

	/** Le type */
	private String type;

	/** Si le paramètre est modifiable */
	private boolean mutable;

	/** L'ordre d'affichage du paramètre */
	private Integer index;

	/** Si le paramètre est obligatoire */
	private boolean mandatory;

	/**
	 * Getter
	 *
	 * @return la valeur de {@link ParameterDTO#version}
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Setter de {@link ParameterDTO#version}
	 *
	 * @param version la nouvelle valeur à affecter
	 */
	public void setVersion(final Integer version) {
		this.version = version;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link ParameterDTO#name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter de {@link ParameterDTO#name}
	 *
	 * @param name la nouvelle valeur à affecter
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link ParameterDTO#displayName}
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Setter de {@link ParameterDTO#displayName}
	 *
	 * @param displayName la nouvelle valeur à affecter
	 */
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link ParameterDTO#comment}
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setter de {@link ParameterDTO#comment}
	 *
	 * @param comment la nouvelle valeur à affecter
	 */
	public void setComment(final String comment) {
		this.comment = comment;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link ParameterDTO#initialValue}
	 */
	public String getInitialValue() {
		return initialValue;
	}

	/**
	 * Setter de {@link ParameterDTO#initialValue}
	 *
	 * @param initialValue la nouvelle valeur à affecter
	 */
	public void setInitialValue(final String initialValue) {
		this.initialValue = initialValue;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link ParameterDTO#value}
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setter de {@link ParameterDTO#value}
	 *
	 * @param value la nouvelle valeur à affecter
	 */
	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link ParameterDTO#type}
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter de {@link ParameterDTO#type}
	 *
	 * @param type la nouvelle valeur à affecter
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link ParameterDTO#mutable}
	 */
	public boolean isMutable() {
		return mutable;
	}

	/**
	 * Setter de {@link ParameterDTO#mutable}
	 *
	 * @param mutable la nouvelle valeur à affecter
	 */
	public void setMutable(final boolean mutable) {
		this.mutable = mutable;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link ParameterDTO#index}
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * Setter de {@link ParameterDTO#index}
	 *
	 * @param index la nouvelle valeur à affecter
	 */
	public void setIndex(final Integer index) {
		this.index = index;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link ParameterDTO#mandatory}
	 */
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * Setter de {@link ParameterDTO#mandatory}
	 *
	 * @param mandatory la nouvelle valeur à affecter
	 */
	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}
}
