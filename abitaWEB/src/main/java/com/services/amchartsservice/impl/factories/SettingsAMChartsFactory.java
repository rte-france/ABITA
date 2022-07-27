/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.amchartsservice.impl.factories;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;

import java.io.Serializable;

/**
 * Fabrique de tags de settings AMCharts
 * @author
 */
public class SettingsAMChartsFactory implements Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = -7104764851145121604L;

    /**
	 * @param rootElement le tag racine
	 * @return la racine des settings construite
	 */
	public Document buildDocumentSettings(Element rootElement) {
        return new Document(rootElement);
	}

	/**
	 * @return objet tag settings
	 */
	public Element buildSettingsTag() {
		return new Element("settings");
	}

	/**
	 * @return objet tag labels
	 */
	public Element buildLabelsTag() {
		return new Element("labels");
	}

	/**
	 * @param index l'index du tag à construire
	 * @return objet tag label
	 */
	public Element buildLabelTag(int index) {
		Element label = new Element("label");
		label.setAttribute(new Attribute("lid", String.valueOf(index)));
		return label;
	}

	/**
	 * @param settingName setting name
	 * @param settingValue setting value
	 * @return text tag for settings
	 */
	public Element buildSettingTagWithValue(String settingName, String settingValue) {
        Element text = new Element(settingName);
        text.setText(settingValue);
        return text;
	}
}
