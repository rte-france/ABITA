/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Pie slice for pie chart
 * @author
 */
public class PieSlice implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -1322007846959800380L;

	/** value */
	private String value;

	/** attributes */
	private HashMap<String, String> attributes;

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

	/**
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = new HashMap<String, String>(attributes);
	}
}
