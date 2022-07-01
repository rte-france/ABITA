package com.services.amchartsservice.impl.beans;

import com.services.amchartsservice.impl.beans.AbstractChart;
import com.services.amchartsservice.impl.beans.PieSlice;

import java.io.Serializable;
import java.util.List;

/**
 * Pie chart model
 * @author
 */
public class PieChart extends AbstractChart implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 5125425102587107539L;

	/** slices */
	private List<PieSlice> slices;

	/**
	 * Constructor
	 */
	public PieChart() {
	    super();
	}

	/**
	 * @return the slices
	 */
	public List<PieSlice> getSlices() {
		return slices;
	}

	/**
	 * @param slices the slices to set
	 */
	public void setSlices(List<PieSlice> slices) {
		this.slices = slices;
	}
}
