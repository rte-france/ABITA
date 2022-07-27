/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto.parameter;

import com.dto.AbstractDTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Un domain regroupe plusieurs paramètres ayant un lien
 *
 * @author
 */
public class DomainDTO extends AbstractDTO implements Serializable {

	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = -7562335429967579057L;

	/**
	 * Numéro de version
	 * Incrémenté à chaque modification d'un paramètre lié
	 */
	private Integer version;

	/**
	 * Le nom du domaine
	 */
	private String name;

	/**
	 * Le nom du domaine à afficher
	 */
	private String displayName;

	/**
	 * Commentaire
	 */
	private String comment;

	/**
	 * Ordre d'affichage
	 */
	private Integer index;

	/**
	 * Paramètres liés
	 */
	private Map<String, ParameterDTO> parameters = new HashMap<String, ParameterDTO>();

	/**
	 * Getter
	 *
	 * @return la valeur de {@link DomainDTO#version}
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Setter de {@link DomainDTO#version}
	 *
	 * @param version la nouvelle valeur à affecter
	 */
	public void setVersion(final Integer version) {
		this.version = version;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link DomainDTO#name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter de {@link DomainDTO#name}
	 *
	 * @param name la nouvelle valeur à affecter
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link DomainDTO#displayName}
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Setter de {@link DomainDTO#displayName}
	 *
	 * @param displayName la nouvelle valeur à affecter
	 */
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link DomainDTO#comment}
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setter de {@link DomainDTO#comment}
	 *
	 * @param comment la nouvelle valeur à affecter
	 */
	public void setComment(final String comment) {
		this.comment = comment;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link DomainDTO#index}
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * Setter de {@link DomainDTO#index}
	 *
	 * @param index la nouvelle valeur à affecter
	 */
	public void setIndex(final Integer index) {
		this.index = index;
	}

	/**
	 * Getter
	 *
	 * @return la valeur de {@link DomainDTO#parameters}
	 */
	public Map<String, ParameterDTO> getParameters() {
		return parameters;
	}

	/**
	 * Setter de {@link DomainDTO#parameters}
	 *
	 * @param parameters la nouvelle valeur à affecter
	 */
	public void setParameters(final Map<String, ParameterDTO> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Renvoie le paramètre à partir de son nom
	 *
	 * @param paramKey le nom du paramètre à renvoyer
	 *
	 * @return le paramètre ou null
	 */
	public ParameterDTO getParameter(final String paramKey) {
		return parameters.get(paramKey);
	}
}
