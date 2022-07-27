/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.builders;

import com.services.amchartsservice.IBuilder;
import com.services.amchartsservice.impl.beans.Graph;
import com.services.amchartsservice.impl.beans.RadarChart;
import com.services.amchartsservice.impl.factories.DataAMChartsFactory;
import com.services.common.util.SafetyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.Serializable;

/**
 * Monteur pour les donnees AMCharts (graphes radar)
 * @author
 */
public class RadarDataAMChartsBuilder implements IBuilder, Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = -5462469777747887361L;
    /** factory */
	private DataAMChartsFactory factory;

	/**
	 * {@inheritDoc}
	 * @see IBuilder#build(Serializable)
	 */
	@Override
	public String build(Serializable serializableObject) {
		Element racine = factory.buildChartTag();
		Document document = factory.buildDocumentData(racine);

		if (serializableObject instanceof RadarChart) {
			RadarChart radarChart = (RadarChart) serializableObject;
			Element tagAxes = factory.buildAxesTag();
			int indiceAxes = 0;
			for (String axis : SafetyUtils.emptyIfNull(radarChart.getAxes())) {
				tagAxes.addContent(factory.buildAxisTag(indiceAxes++, axis));
			}
			racine.addContent(tagAxes);
			int indiceGraphs = 0;
			int indiceValues = 0;
			Element tagGraphs = factory.buildGraphsTag();
			Element tagGraph = null;
			for (Graph graph : SafetyUtils.emptyIfNull(radarChart.getGraphs())) {
				tagGraph = factory.buildGraphTag(indiceGraphs++);
				indiceValues = 0;
				for (String value : SafetyUtils.emptyIfNull(graph.getValues())) {
					tagGraph.addContent(factory.buildValueTag(indiceValues++, value));
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
