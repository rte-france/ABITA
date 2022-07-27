/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.beans;

import com.services.amchartsservice.impl.beans.AbstractChart;
import com.services.amchartsservice.impl.beans.Graph;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 */
public class RadarChart extends AbstractChart implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 6771295559569047877L;

	/** axes */
	private List<String> axes;

	/** graphs */
	private List<Graph> graphs;

	/**
	 * Constructor
	 */
	public RadarChart() {
	    super();
	}

	/**
	 * @return the axes
	 */
	public List<String> getAxes() {
		return axes;
	}

	/**
	 * @param axes the axes to set
	 */
	public void setAxes(List<String> axes) {
		this.axes = axes;
	}

	/**
	 * @return the graphs
	 */
	public List<Graph> getGraphs() {
		return graphs;
	}

	/**
	 * @param graphs the graphs to set
	 */
	public void setGraphs(List<Graph> graphs) {
		this.graphs = graphs;
	}
}
