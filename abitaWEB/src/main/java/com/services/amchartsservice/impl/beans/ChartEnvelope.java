/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.beans;

import com.services.amchartsservice.impl.beans.AbstractChart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Enveloppe pour un chart
 * @author
 */
public class ChartEnvelope implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 5343733439482629703L;

	/** chart */
	private AbstractChart chart;

	/** settings to overload xml file settings */
	private ArrayList<Map<String, String>> settings;

	/**
	 * @return the chart
	 */
	public AbstractChart getChart() {
		return chart;
	}

	/**
	 * @param chart the chart to set
	 */
	public void setChart(AbstractChart chart) {
		this.chart = chart;
	}

	/**
	 * @return the settings
	 */
	public ArrayList<Map<String, String>> getSettings() {
		return settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(ArrayList<Map<String, String>> settings) {
		this.settings = settings;
	}
}
