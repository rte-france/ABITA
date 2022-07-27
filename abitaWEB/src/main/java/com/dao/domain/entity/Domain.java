/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.domain.entity;

import com.dao.domain.entity.Parameter;
import com.dao.common.entity.AbstractEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Domain of parameter.
 * @author
 *
 */
public class Domain extends AbstractEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8127674455170320297L;

	/**
	 * optimistic versionning
	 */
	private Integer version;

	/**
	 * name of domain
	 */
	private String name;

	/**
	 * display name
	 */
	private String displayName;

	/**
	 * info of the domain
	 */
	private String comment;

	/**
	 * index of sort
	 */
	private Integer index;

	/**
	 * Parameters are identfied by id and name
	 * So a map fit well for this association
	 */
	private Map<String, Parameter> parameters = new HashMap<String, Parameter>();

	/**
	 * Default constructor.
	 */
	public Domain() {
	}

	/**
	 * Field constructor.
	 * @param name domain name
	 * @param displayName display name
	 * @param comment domain comment
	 * @param index domain index
	 */
	public Domain(String name, String displayName, String comment, Integer index) {
		this.name = name;
		this.displayName = displayName;
		this.comment = comment;
		this.index = index;
	}

	/**
	 * getter version.
	 * @return version.
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * setter version.
	 * @param version domain version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * getter name.
	 * @return domain name
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter name.
	 * @param name domain name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter display name.
	 * @return display name.
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * setter display name.
	 * @param displayName display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * getter comment.
	 * @return comment.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * setter comment.
	 * @param comment domain comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * getter index.
	 * @return domain index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * setter index.
	 * @param index domain index
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, Parameter> getParameters() {
		return this.parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, Parameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Search and return the parameter
	 *
	 * @param parameterName the name of paramter to find.
	 *
	 * @return the parameter or null
	 */
	public Parameter getParameter(String parameterName) {
		if (this.parameters == null) {
			return null;
		}
		return this.parameters.get(parameterName);
	}
}
