/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.builders;

import com.services.amchartsservice.IBuilder;
import com.services.amchartsservice.impl.beans.XYChart;
import com.services.amchartsservice.impl.beans.XYGraph;
import com.services.amchartsservice.impl.beans.XYPoint;
import com.services.amchartsservice.impl.factories.DataAMChartsFactory;
import com.services.common.util.SafetyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.Serializable;

/**
 * Monteur pour les donnees AMCharts (graphes XY)
 * @author
 */
public class XYDataAMChartsBuilder implements IBuilder, Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = -7352949751978514998L;

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

		if (serializableObject instanceof XYChart) {
			XYChart xyChart = (XYChart) serializableObject;
			Element tagGraphs = factory.buildGraphsTag();
			Element tagGraph = null;
			int indiceGraphs = 0;
			for (XYGraph graph : SafetyUtils.emptyIfNull(xyChart.getGraphs())) {
				tagGraph = factory.buildGraphXYTag(indiceGraphs++, graph);
				for (XYPoint point : SafetyUtils.emptyIfNull(graph.getPoints())) {
					tagGraph.addContent(factory.buildXYPointTag(point));
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
