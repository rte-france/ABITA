/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.builders;

import com.services.amchartsservice.IBuilder;
import com.services.amchartsservice.impl.beans.BarOrLineChart;
import com.services.amchartsservice.impl.beans.Graph;
import com.services.amchartsservice.impl.factories.DataAMChartsFactory;
import com.services.common.util.SafetyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.Serializable;

/**
 * Monteur pour les donnees AMCharts (graphes bar/column/line)
 * @author
 */
public class BarOrLineDataAMChartsBuilder implements IBuilder, Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = 824407447340884720L;

    /** factory */
	private DataAMChartsFactory factory;

	/**
	 * {@inheritDoc}
	 * @see IBuilder#build(Serializable)
	 */
	public String build(Serializable serializableObject) {
		Element racine = factory.buildChartTag();
		Document document = factory.buildDocumentData(racine);

		if (serializableObject instanceof BarOrLineChart) {
			BarOrLineChart chart = (BarOrLineChart) serializableObject;
			Element tagSeries = factory.buildSeriesTag();
			int indiceSeries = 0;
			for (String value : SafetyUtils.emptyIfNull(chart.getSeries())) {
				tagSeries.addContent(factory.buildValueTag(indiceSeries++, value));
			}
			racine.addContent(tagSeries);
			int indiceGraphs = 0;
			Element tagGraphs = factory.buildGraphsTag();
			Element tagGraph = null;
			for (Graph graph : SafetyUtils.emptyIfNull(chart.getGraphs())) {
				tagGraph = factory.buildGraphTag(indiceGraphs++);
				indiceSeries = 0;
				for (String value : SafetyUtils.emptyIfNull(graph.getValues())) {
					tagGraph.addContent(factory.buildValueTag(indiceSeries++, value));
				}
				tagGraphs.addContent(tagGraph);
			}
			racine.addContent(tagGraphs);
		}
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        return outputter.outputString(document);
	}

	/**
	 * @param factory the factory to set
	 */
	public void setFactory(DataAMChartsFactory factory) {
		this.factory = factory;
	}

}
