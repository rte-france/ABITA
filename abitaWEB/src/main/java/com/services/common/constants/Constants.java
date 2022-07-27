/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.common.constants;

/**
 * Default constants class.
 * @author
 */
public final class Constants {

	/**
	 * security
	 * session attribute's key for User class
	 */
	public static final String USER_KEY = "user";

	/**
	 * JSF outcome
	 */
	public static final String CURRENT_LOCALE = "current_locale";
	/**
	 * JSF outcome
	 */
	public static final String SUCCESS_OUTCOME = "success";
	/**
	 * JSF outcome
	 */
	public static final String FAILURE_OUTCOME = "failure";
	/**
	 * JSF outcome
	 */
	public static final String CANCEL_OUTCOME = "cancel";
	/**
	 * JSF outcome
	 */
	public static final String INVALID_OUTCOME = "invalid";
	/**
	 * JSF outcome
	 */
	public static final String BLANK_OUTCOME = "";
	/**
	 * dynamic parameter id in parameterService
	 */
	public static final String ENABLE_VIEW_INTERNAL_USER = "enableViewInternalUser";
	/**
	 * dynamic parameter id in parameterService
	 */
	public static final String FILTEREDLOGIN = "filteredlogin";
	/**
	 * css style in webContent/includes/style.css
	 * use for dynamic component's style.
	 */
	public static final String LABEL_STYLE = "label";

	/**
	 * css style in webContent/includes/style.css
	 * use for dynamic component's style.
	 */
	public static final String FIELD_SOLID_STYLE = "field-solid";
	/**
	 * css style in webContent/includes/style.css
	 * use for dynamic component's style.
	 */
	public static final String PANEL_STYLE = "panel";
	/**
	 * css style in webContent/includes/style.css
	 * use for dynamic component's style.
	 */
	public static final String BUTTON_STYLE = "button";
	/**
	 * css style in webContent/includes/style.css
	 * use for dynamic component's style.
	 */
	public static final String VALIDATION_STYLE = "validation";
	/**
	 * css style in webContent/includes/style.css
	 * use for dynamic component's style.
	 */
	public static final String MONTH_YEAR_ROW_STYLE = "yearMonthHeader";
	/**
	 * css style in webContent/includes/style.css
	 * use for dynamic component's style.
	 */
	public static final String WEEK_ROW_STYLE = "weekHeader";
	/**
	 * css style in webContent/includes/style.css
	 * use for dynamic component's style.
	 */
	public static final String POPUP_BUTTON_STYLE = "button";
	/**
	 * css style in webContent/includes/style.css
	 * use for dynamic component's style.
	 */
	public static final String CURRENT_DAY_CELL_STYLE = "currentDayCell";
	/**
	 * css style in webContent/includes/style.css
	 * use for dynamic component's style.
	 */
	public static final String LINK_STYLE = "link-button";
	/**
	 * data type of parameter in parameterService
	 */
	public static final String STRING_DATA_TYPE = "STRING";

	/**
	 * data type of parameter in parameterService
	 */
	public static final String INTEGER_DATA_TYPE = "INTEGER";
	/**
	 * data type of parameter in parameterService
	 */
	public static final String DECIMAL_DATA_TYPE = "DECIMAL";
	/**
	 * data type of parameter in parameterService
	 */
	public static final String BOOLEAN_DATA_TYPE = "BOOLEAN";
	/**
	 * data type of parameter in parameterService
	 */
	public static final String DATE_DATA_TYPE = "DATE";
	/**
	 * data type of parameter in parameterService
	 */
	public static final String LIST_DATA_TYPE = "LIST";
	/**
	 * parameter tabbed pane constants
	 */
	public static final String EDITOR_PANE_ID = "editorPane";

	/**
	 * parameter tabbed pane constants
	 */
	public static final String EDITOR_PANE_ACTIVE_TAB_STYLE = "editorPane_activeTab";
	/**
	 * parameter tabbed pane constants
	 */
	public static final String EDITOR_PANE_INACTIVE_TAB_STYLE = "editorPane_inactiveTab";
	/**
	 * parameter tabbed pane constants
	 */
	public static final String EDITOR_PANE_SUB_STYLE = "editorPane_subStyle";
	/**
	 * parameter tabbed pane constants
	 */
	public static final String EDITOR_PANE_TAB_CONTENT_STYLE = "editorPane_tabContent";
	/**
	 * parameter tabbed pane constants
	 */
	public static final String FACES_SERVLET_KEY = "Faces Servlet";

	/**
	 * String Separator
	 */
	public static final String COMMA_DELIM = ",";

	/**
	 * String Separator
	 */
	public static final String DEUX_POINTS_DELIM = ":";
	/**
	 * String Separator
	 */
	public static final String SEMICOLON_DELIM = ";";
	/**
	 * Default bundle
	 */
	public static final String COMMON_BUNDLE = "com.site.resource.site";

	/**
	 * Constructor
	 */
	private Constants() {
		super();
	}

}
