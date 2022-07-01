package com.services.amchartsservice.impl.beans;

import java.io.Serializable;

/**
 * XYPoint data model
 * @author
 */
public class XYPoint implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -3005856358404758290L;

	/** x */
	private String x;

	/** y */
	private String y;

	/** value */
	private String value;

	/**
	 * @return the x
	 */
	public String getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(String x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public String getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(String y) {
		this.y = y;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
