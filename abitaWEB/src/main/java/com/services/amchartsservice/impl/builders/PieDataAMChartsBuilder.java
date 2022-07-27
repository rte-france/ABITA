/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.builders;

import com.services.amchartsservice.IBuilder;
import com.services.amchartsservice.impl.beans.PieChart;
import com.services.amchartsservice.impl.beans.PieSlice;
import com.services.amchartsservice.impl.factories.DataAMChartsFactory;
import com.services.common.util.SafetyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.Serializable;

/**
 * Monteur pour les donnees AMCharts (graphes pie)
 * @author
 */
public class PieDataAMChartsBuilder implements IBuilder, Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = 6502796007329815770L;

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

		if (serializableObject instanceof PieChart) {
			Element tagPie = factory.buildPieTag();
			PieChart pieChart = (PieChart) serializableObject;
			for (PieSlice slice : SafetyUtils.emptyIfNull(pieChart.getSlices())) {
				tagPie.addContent(factory.buildPieSliceTag(slice));
			}
			racine.addContent(tagPie);
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
