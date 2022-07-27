/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.impl;

import com.services.amchartsservice.IBuilder;
import com.services.amchartsservice.impl.beans.ChartEnvelope;
import com.web.common.servlets.GenerateAmchartsXmlServlet;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

/**
 * Abstract controller to AMCharts
 * @author
 */
public abstract class AbstractChartController implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -1699041521716536179L;

	/** settingsBuilder */
	private IBuilder settingsBuilder;

	/** dataBuilder */
	private IBuilder dataBuilder;

	/** charts */
	private Map<String, ChartEnvelope> charts;

	/**
	 * Constructor
	 */
	public AbstractChartController() {
		super();
	}

	/**
	 * @param settingsBuilder the settingsBuilder to set
	 */
	public void setSettingsBuilder(IBuilder settingsBuilder) {
		this.settingsBuilder = settingsBuilder;
	}

	/**
	 * @param dataBuilder the dataBuilder to set
	 */
	public void setDataBuilder(IBuilder dataBuilder) {
		this.dataBuilder = dataBuilder;
	}

	/**
	 * Generate settings and data for graph in session
	 */
	@PostConstruct
	protected void generateChartsSettingsAndDataToSession() {
		this.charts = buildCharts();
		ChartEnvelope chartEnvelope = null;
		String xmlSettings = null;
		String xmlData = null;
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		for (Map.Entry<String, ChartEnvelope> mapEntry : charts.entrySet()) {
			chartEnvelope = mapEntry.getValue();
			xmlSettings = settingsBuilder.build(chartEnvelope.getSettings());
			xmlData = dataBuilder.build(chartEnvelope.getChart());
			sessionMap.put(GenerateAmchartsXmlServlet.SETTINGS + mapEntry.getKey(), xmlSettings);
			sessionMap.put(GenerateAmchartsXmlServlet.DATA + mapEntry.getKey(), xmlData);
		}
	}

	/**
	 * @return data and settings for graph(s) in session
	 */
	public abstract Map<String, ChartEnvelope> buildCharts();

	/**
	 * @return the charts
	 */
	public Map<String, ChartEnvelope> getCharts() {
		return charts;
	}
}
