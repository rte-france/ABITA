/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.beans;

import com.services.amchartsservice.impl.beans.AbstractChart;
import com.services.amchartsservice.impl.beans.XYGraph;

import java.io.Serializable;
import java.util.List;

/**
 * XYCHart data model
 * @author
 */
public class XYChart extends AbstractChart implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 775671170658954084L;

	/** graphs */
	private List<XYGraph> graphs;

	/**
	 * Constructor
	 */
	public XYChart() {
	    super();
	}

	/**
	 * @return the graphs
	 */
	public List<XYGraph> getGraphs() {
		return graphs;
	}

	/**
	 * @param graphs the graphs to set
	 */
	public void setGraphs(List<XYGraph> graphs) {
		this.graphs = graphs;
	}
}
