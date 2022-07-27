/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.factories;

import com.services.amchartsservice.impl.beans.PieSlice;
import com.services.amchartsservice.impl.beans.XYGraph;
import com.services.amchartsservice.impl.beans.XYPoint;
import com.services.common.util.SafetyUtils;
import org.jdom.Document;
import org.jdom.Element;

import java.io.Serializable;
import java.util.Map;

/**
 * Fabrique de tags de data AMCharts
 * @author
 */
public class DataAMChartsFactory implements Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = 4399972652329974212L;

    /**
	 * @param rootElement le tag racine
	 * @return la racine des settings construite
	 */
	public Document buildDocumentData(Element rootElement) {
        return new Document(rootElement);
	}

	/**
	 * @return objet tag chart
	 */
	public Element buildChartTag() {
		return new Element("chart");
	}

	/**
	 * @return objet tag series
	 */
	public Element buildSeriesTag() {
		return new Element("series");
	}

	/**
	 * @param index value index
	 * @param value value
	 * @return objet tag value
	 */
	public Element buildValueTag(int index, String value) {
		Element valueTag = new Element("value");
		valueTag.setAttribute("xid", String.valueOf(index));
		valueTag.setText(value);
		return valueTag;
	}

	/**
	 * @return objet tag graphs
	 */
	public Element buildGraphsTag() {
		return new Element("graphs");
	}

	/**
	 * @param index graph tag index
	 * @return objet tag graph
	 */
	public Element buildGraphTag(int index) {
		Element tagGraph = new Element("graph");
		tagGraph.setAttribute("gid", String.valueOf(index));
		return tagGraph;
	}

	/**
	 * @return objet tag pie
	 */
	public Element buildPieTag() {
		return new Element("pie");
	}

	/**
	 * @param slice pie slice
	 * @return objet tag slice
	 */
	public Element buildPieSliceTag(PieSlice slice) {
		Element valueTag = new Element("slice");
		for (Map.Entry<String, String> attribute : SafetyUtils.emptyIfNull(slice.getAttributes().entrySet())) {
			valueTag.setAttribute(attribute.getKey(), attribute.getValue());
		}
		valueTag.setText(slice.getValue());
		return valueTag;
	}

	/**
	 * @return objet tag axes
	 */
	public Element buildAxesTag() {
		return new Element("axes");
	}

	/**
	 * @param index axis value index
	 * @param value value to display
	 * @return objet tag axis
	 */
	public Element buildAxisTag(int index, String value) {
		Element valueTag = new Element("axis");
		valueTag.setAttribute("xid", String.valueOf(index));
		valueTag.setText(value);
		return valueTag;
	}

	/**
	 * @param index graph tag index
	 * @param graph xy graph
	 * @return objet tag graph
	 */
	public Element buildGraphXYTag(int index, XYGraph graph) {
		Element tagGraph = new Element("graph");
		tagGraph.setAttribute("gid", String.valueOf(index));
		for (Map.Entry<String, String> attribute : SafetyUtils.emptyIfNull(graph.getAttributes().entrySet())) {
			tagGraph.setAttribute(attribute.getKey(), attribute.getValue());
		}
		return tagGraph;
	}

	/**
	 * @param point xy point
	 * @return objet tag point
	 */
	public Element buildXYPointTag(XYPoint point) {
		Element tagPoint = new Element("point");
		tagPoint.setAttribute("x", point.getX());
		tagPoint.setAttribute("y", point.getY());
		tagPoint.setAttribute("value", point.getValue());
		return tagPoint;
	}
}
