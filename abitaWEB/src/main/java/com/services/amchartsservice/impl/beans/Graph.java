/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.beans;

import java.io.Serializable;
import java.util.List;

/**
 * AMChart graph data
 * @author
 */
public class Graph implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 2049900995711497201L;

	/** values */
	private List<String> values;

	/**
	 * @return the values
	 */
	public List<String> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}
}
