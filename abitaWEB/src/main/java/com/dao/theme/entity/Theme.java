package com.dao.theme.entity;

import com.dao.common.entity.AbstractEntity;

import java.io.Serializable;

/**
 * This class define theme using in application
 * @author
 *
 */
public class Theme extends AbstractEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2693667848073389873L;

	/**
	 * Theme's label
	 */
	private String label;

	/**
	 * Theme's name
	 */
	private String name;

	/**
	 * Default constructor
	 */
	public Theme() {
		super();
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(final String label) {
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
	public void setName(final String name) {
		this.name = name;
	}
}
