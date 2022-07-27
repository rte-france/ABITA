/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 * A. Erisay - 6 juin 2011
 */
package com.web.configuration.impl;

import com.dto.parameter.DomainDTO;
import com.dto.parameter.ParameterDTO;
import com.services.common.constants.Constants;
import com.services.common.constants.DateTimeConstants;
import com.services.common.util.SafetyUtils;
import com.web.configuration.impl.ParamHolderValueExpressionFactory;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlMessage;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.convert.BooleanConverter;
import javax.faces.convert.DoubleConverter;
import javax.faces.convert.IntegerConverter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @author
 *
 */
public abstract class TabViewFactory {

	/**
	 * Constructor
	 */
	private TabViewFactory() {
		super();
	}

	/**
	 * the _900
	 */
	private static final String SIZE_PANEL = "100%";
	/**
	 * the _3
	 */
	private static final int NUMBER_FIELDS = 3;

	/**
	 * name of value property tag's property.
	 */
	private static final String VALUE_PROPERTY = "value";

	/**
	 * create parameter tabView
	 * @param context faces context
	 * @param domains domain list
	 * @return TabView
	 */
	public static TabView createTabView(FacesContext context, List<DomainDTO> domains) {
		TabView tabview = new TabView();
		tabview.setId(Constants.EDITOR_PANE_ID);
		for (DomainDTO domain : SafetyUtils.emptyIfNull(domains)) {
			tabview.getChildren().add(createTab(context, domain));
		}
		return tabview;
	}

	/**
	 * create tabs
	 * @param context faces context
	 * @param domain domain
	 * @return Tab
	 */
	public static Tab createTab(FacesContext context, DomainDTO domain) {
		Tab tab = new Tab();
		tab.setId(domain.getName());
		tab.setTitle(domain.getDisplayName());
		tab.setRendered(true);

		HtmlPanelGrid gridPanel = createPanelGrid(context, domain);
		tab.getChildren().add(gridPanel);
		return tab;
	}

	/**
	 * @param context faces context
	 * @param domain domain
	 * @return HtmlPanelGrid
	 */
	public static HtmlPanelGrid createPanelGrid(FacesContext context, DomainDTO domain) {
		HtmlPanelGrid gridPanel = new HtmlPanelGrid();
		gridPanel.setColumns(NUMBER_FIELDS);
		gridPanel.setWidth(SIZE_PANEL);

		for (ParameterDTO parameter : domain.getParameters().values()) {
			gridPanel.getChildren().add(createLabel(parameter));
			gridPanel.getChildren().add(createInputWithComment(context, domain, parameter));
			gridPanel.getChildren().add(createValidationMessage(parameter));
		}
		return gridPanel;
	}

	/**
	 * RM_CONF_014
	 * Create input with its comment
	 * @param context faces context
	 * @param domain parameter domain
	 * @param parameter parameter
	 * @return UIComponent
	 */
	private static UIComponent createInputWithComment(final FacesContext context, final DomainDTO domain,
			final ParameterDTO parameter) {
		HtmlPanelGrid grid = new HtmlPanelGrid();
		grid.setColumns(1);
		grid.getChildren().add(createInput(context, domain, parameter));
		grid.getChildren().add(createComment(context, parameter));
		return grid;
	}

	/**
	 * @param context faces context
	 * @param domain parameter domain
	 * @param parameter parameter
	 * @return UIComponent
	 */
	private static UIComponent createInput(final FacesContext context, final DomainDTO domain,
			final ParameterDTO parameter) {
		if (Constants.STRING_DATA_TYPE.equals(parameter.getType())
				|| Constants.DECIMAL_DATA_TYPE.equals(parameter.getType())
				|| Constants.INTEGER_DATA_TYPE.equals(parameter.getType())) {
			return createInputText(context, domain, parameter);
		} else if (Constants.DATE_DATA_TYPE.equals(parameter.getType())) {
			return createCalendar(context, domain, parameter);
		} else if (Constants.BOOLEAN_DATA_TYPE.equals(parameter.getType())) {
			return createCheckBox(context, domain, parameter);
		} else if (parameter.getType().indexOf(Constants.LIST_DATA_TYPE) != StringUtils.INDEX_NOT_FOUND) {
			return createListSelectItems(context, domain, parameter);
		} else {
			return null;
		}
	}

	/**
	 * @param p parameter
	 * @return HtmlMessage
	 */
	public static HtmlMessage createValidationMessage(final ParameterDTO p) {
		HtmlMessage pMessage = new HtmlMessage();
		pMessage.setFor(p.getName());
		pMessage.setStyleClass(Constants.VALIDATION_STYLE);
		return pMessage;
	}

	/**
	 * RM_CONF_014
	 * @param p parameter
	 * @param context faces context
	 * @return HtmlOutputText
	 */
	public static HtmlOutputText createComment(final FacesContext context, final ParameterDTO p) {
		HtmlOutputText text = new HtmlOutputText();
		text.setValue(p.getComment());
		return text;
	}

	/**
	 * @param p parameter
	 * @return HtmlOutputLabel
	 */
	public static HtmlOutputLabel createLabel(final ParameterDTO p) {
		HtmlOutputLabel pOutLabel = new HtmlOutputLabel();
		pOutLabel.setValue(p.getDisplayName());
		pOutLabel.setStyleClass(Constants.LABEL_STYLE);
		pOutLabel.setFor(p.getName());
		return pOutLabel;
	}

	/**
	 * Make Calendar with Parameter Class with data type DATE.
	 * @param context faces context
	 * @param domain domain of parameter
	 * @param parameter parameter Class
	 * @return Calendar
	 */
	public static Calendar createCalendar(final FacesContext context, final DomainDTO domain,
			final ParameterDTO parameter) {
		Calendar calendar = new Calendar();
		calendar.setId(parameter.getName());
		calendar.setValueExpression(VALUE_PROPERTY, ParamHolderValueExpressionFactory.createValueExpression(context,
				domain.getName(), parameter.getName(), Date.class));
		calendar.setPattern(DateTimeConstants.FRENCH_DATE_FORMAT);
		calendar.setLocale(Locale.FRANCE);
		// can not be changed
		// RM_CONF_013
		calendar.setReadonly(!parameter.isMutable());
		calendar.setDisabled(!parameter.isMutable());
		// is mandatory
		calendar.setRequired(parameter.isMandatory());
		return calendar;
	}

	/**
	 * Make HtmlInputText with Paramter Class with data type INTEGER,DECIMAL AND STRING.
	 * @param context faces context
	 * @param domain domain of parameter
	 * @param parameter parameter Class
	 * @return built HtmlInputText
	 */
	public static HtmlInputText createInputText(final FacesContext context, final DomainDTO domain,
			final ParameterDTO parameter) {
		HtmlInputText pInText = new HtmlInputText();
		pInText.setId(parameter.getName());
		pInText.setStyleClass(Constants.FIELD_SOLID_STYLE);
		pInText.setStyle("width:250px");
		// can not be changed
		// RM_CONF_013
		pInText.setReadonly(!parameter.isMutable());
		pInText.setDisabled(!parameter.isMutable());
		// is mandatory
		pInText.setRequired(parameter.isMandatory());
		// converter and validation
		if (Constants.INTEGER_DATA_TYPE.equals(parameter.getType())) {
			pInText.setConverter(new IntegerConverter());
			pInText.setValueExpression(
					VALUE_PROPERTY,
					ParamHolderValueExpressionFactory.createValueExpression(context, domain.getName(),
							parameter.getName(), Integer.class));
			pInText.setOnkeypress("return isNumerical(event, false);");
			pInText.setOnkeydown("return isNumerical(event, false);");
		} else if (Constants.DECIMAL_DATA_TYPE.equals(parameter.getType())) {
			pInText.setConverter(new DoubleConverter());
			pInText.setValueExpression(
					VALUE_PROPERTY,
					ParamHolderValueExpressionFactory.createValueExpression(context, domain.getName(),
							parameter.getName(), Double.class));
			pInText.setOnkeypress("return isNumerical(event, true);");
			pInText.setOnkeydown("return isNumerical(event, true);");
		} else {
			pInText.setValueExpression(
					VALUE_PROPERTY,
					ParamHolderValueExpressionFactory.createValueExpression(context, domain.getName(),
							parameter.getName(), String.class));
		}

		return pInText;

	}

	/**
	 * Make HtmlSelectBooleanCheckbox with Paramter Class with data type BOOLEAN. Add this menu in gridPanel.
	 * @param context faces context
	 * @param domain domain of parameter
	 * @param parameter parameter Class
	 * @return HtmlSelectBooleanCheckbox
	 */
	public static HtmlSelectBooleanCheckbox createCheckBox(final FacesContext context, final DomainDTO domain,
			final ParameterDTO parameter) {
		HtmlSelectBooleanCheckbox check = new HtmlSelectBooleanCheckbox();
		check.setId(parameter.getName());
		check.setStyleClass("");
		check.setConverter(new BooleanConverter());
		check.setValueExpression(VALUE_PROPERTY, ParamHolderValueExpressionFactory.createValueExpression(context,
				domain.getName(), parameter.getName(), Boolean.class));
		// can not be changed
		// RM_CONF_013
		check.setReadonly(!parameter.isMutable());
		check.setDisabled(!parameter.isMutable());
		return check;

	}

	/**
	 * Make HtmlSelectOneMenu with Paramter Class with data type LIST. Add this menu in gridPanel.
	 * @param context faces context
	 * @param domain domain of parameter
	 * @param parameter parameter Class
	 * @return HtmlSelectOneMenu
	 */
	public static HtmlSelectOneMenu createListSelectItems(final FacesContext context, final DomainDTO domain,
			final ParameterDTO parameter) {
		HtmlSelectOneMenu select = new HtmlSelectOneMenu();
		// Recuperation des donnees depuis le type (LIST:item1,item2...)
		String donnees = parameter.getType().substring(parameter.getType().indexOf(Constants.DEUX_POINTS_DELIM) + 1);

		// Tansform chain into list
		StringTokenizer strToken = new StringTokenizer(donnees, Constants.COMMA_DELIM);
		select.getChildren().clear();
		String val;
		UISelectItem ui;
		while (strToken.hasMoreTokens()) {
			val = strToken.nextToken();
			ui = new UISelectItem();
			ui.setItemValue(val);
			ui.setItemLabel(val);
			select.getChildren().add(ui);
		}
		select.setId(parameter.getName());
		select.setStyle("width:180px");
		select.setValueExpression(VALUE_PROPERTY, ParamHolderValueExpressionFactory.createValueExpression(context,
				domain.getName(), parameter.getName(), String.class));

		// can not be changed
		// RM_CONF_013
		select.setReadonly(!parameter.isMutable());
		select.setDisabled(!parameter.isMutable());
		return select;
	}

}
