package com.services.amchartsservice.impl.beans;

import com.services.amchartsservice.impl.beans.AbstractChart;
import com.services.amchartsservice.impl.beans.Graph;

import java.io.Serializable;
import java.util.List;

/**
 * Bar/Column/Line chart model
 * @author
 */
public class BarOrLineChart extends AbstractChart implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -1064739431067736254L;

	/** series */
	private List<String> series;

	/** graphs */
	private List<Graph> graphs;

	/**
	 * Constructor
	 */
	public BarOrLineChart() {
	    super();
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

	/**
	 * @return the series
	 */
	public List<String> getSeries() {
		return series;
	}

	/**
	 * @param series the series to set
	 */
	public void setSeries(List<String> series) {
		this.series = series;
	}

}
