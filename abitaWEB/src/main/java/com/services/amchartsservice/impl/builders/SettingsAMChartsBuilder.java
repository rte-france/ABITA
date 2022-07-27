/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.builders;

import com.services.amchartsservice.IBuilder;
import com.services.amchartsservice.impl.factories.SettingsAMChartsFactory;
import com.services.common.util.SafetyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Monteur pour les settings AMCharts
 * @author
 */
public class SettingsAMChartsBuilder implements IBuilder, Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = -1928842581691129181L;

    /** factory */
	private SettingsAMChartsFactory factory;

	/**
	 * {@inheritDoc}
	 * @see IBuilder#build(Serializable)
	 */
	@Override
	public String build(Serializable serializableObject) {
		Element racine = factory.buildSettingsTag();
		Document document = factory.buildDocumentSettings(racine);
		Element labels = factory.buildLabelsTag();

		if (serializableObject instanceof List<?>) {
			List<Map<String, String>> listOfSettings = (List<Map<String, String>>) serializableObject;
			int indexSettings = 0;
			Element tagLabel = null;
			for (Map<String, String> settings : SafetyUtils.emptyIfNull(listOfSettings)) {
				tagLabel = factory.buildLabelTag(indexSettings++);
				for (Map.Entry<String, String> settingEntry : SafetyUtils.emptyIfNull(settings.entrySet())) {
					tagLabel.addContent(
                            factory.buildSettingTagWithValue(settingEntry.getKey(), settingEntry.getValue()));
				}
				labels.addContent(tagLabel);
			}
		}

		racine.addContent(labels);
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        return outputter.outputString(document);
	}

	/**
	 * @param factory the factory to set
	 */
	public void setFactory(SettingsAMChartsFactory factory) {
		this.factory = factory;
	}
}
