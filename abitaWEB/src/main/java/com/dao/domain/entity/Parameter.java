package com.dao.domain.entity;

import com.dao.common.entity.AbstractEntity;

import java.io.Serializable;

/**
 * A dynamic parameter.
 *@author
 *
 */

public class Parameter extends AbstractEntity implements Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = -708500036750768497L;

    /** parameter version */
	private Integer version;

	/** parameter name */
	private String name;

	/** parameter display name */
	private String displayName;

	/** parameter comment */
	private String comment;

	/** parameter initial value */
	private String initialValue;

	/** parameter value */
	private String value;

	/** parameter type */
	private String type;

	/** parameter mutable ? */
	private boolean mutable;

	/** parameter index */
	private Integer index;

	/** parameter mandatory ? */
	private boolean mandatory;

	/**
	 *
	 * Construct Parameter.
	 */
	public Parameter() {
		//
	}

	/**
	 *
	 * Construct Parameter.
	 * @param name parameter name
	 * @param displayName parameter display name
	 * @param comment parameter comment
	 * @param initialValue parameter initial value
	 * @param type parameter type
	 * @param mutable parameter is mutable
	 * @param index parameter index
	 * @param mandatory parameter is mandatory
	 */
	public Parameter(String name, String displayName, String comment, String initialValue, String type,
			boolean mutable, Integer index, boolean mandatory) {
		this.name = name;
		this.displayName = displayName;
		this.comment = comment;
		this.initialValue = initialValue;
		this.type = type;
		this.mutable = mutable;
		this.index = index;
		this.mandatory = mandatory;
	}

	/**
	 * getter of version.
	 * @return parameter version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * setter of version.
	 * @param version parameter version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * getter of name.
	 * @return parameter name
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter of name.
	 * @param name parameter name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter of display name.
	 * @return parameter display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * setter of display name.
	 * @param displayName parameter display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * getter of comment.
	 * @return parameter comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * setter of comment.
	 * @param comment parameter comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * getter of initiale value.
	 * @return parameter initial value
	 */
	public String getInitialValue() {
		return initialValue;
	}

	/**
	 * setter of initiale value.
	 * @param initialValue parameter initial value
	 */
	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}

	/**
	 * getter of value.
	 * @return parameter value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * setter of value.
	 * @param value parameter value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * getter of type.
	 * @return parameter type
	 */
	public String getType() {
		return type;
	}

	/**
	 * setter of type.
	 * @param type parameter type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * getter of mutable.
	 * @return parameter mutable ?
	 */
	public boolean isMutable() {
		return mutable;
	}

	/**
	 * setter of mutable.
	 * @param mutable parameter mutable ?
	 */
	public void setMutable(boolean mutable) {
		this.mutable = mutable;
	}

	/**
	 * getter of index.
	 * @return parameter index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * setter of index.
	 * @param index parameter index
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * getter of mandatory.
	 * @return parameter mandatory ?
	 */
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * @param mandatory the mandatory to set
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}
		if (!(o instanceof Parameter)) {
			return false;
		}

		final Parameter parameter = (Parameter) o;

		if (name == null) {
			return parameter.name == null;
		}

		if (!name.equals(parameter.name)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
