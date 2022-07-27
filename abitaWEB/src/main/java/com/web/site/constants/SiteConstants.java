/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.site.constants;

import com.services.common.util.MessageSupport;

/**
 * common constants of package
 * @author
 *
 */
public final class SiteConstants {

	/**
	 * Private constructor
	 */
	private SiteConstants() {
		super();
	}

	/**
	 * dynamic parameter id in parameterService
	 */
	public static final String SITE_NAME_PARAMETER_KEY = "siteName";
	/**
	 * dynamic parameter id in parameterService
	 */
	public static final String SITE_SUB_NAME_PARAMETER_KEY = "siteSubName";
	/**
	 * dynamic parameter id in parameterService
	 */
	public static final String SITE_VERSION_PARAMETER_KEY = "siteVersion";

	/** Dynamic parameter timeout in parameterService */
	public static final String SITE_TIMEOUT = "siteTimeout";

	/**
	 * Messages d'erreur de recuperation de la liste des themes
	 */
	public static final String ERROR_MESSAGES_GET_ALL_THEME =
			MessageSupport.getMessage("com.site.resource.site", "unable.to.retrieve.themes.list");

	/**
	 * Bundle specifique de l'application
	 */
	public static final String CUSTOM_BUNDLE = "specificApplicationProperties";

	/**
	 * Chaine representant la date de livraison de l'application
	 */
	public static final String APPLICATION_DELIVERY_DATE = "delivery.date";

	/**
	 * Cle du theme par defaut
	 */
	public static final String DEFAULTTHEME = "defaultTheme";

	/**
	 * Message d'erreur de mise à jour du thème pour l'utilisateur
	 */
	public static final String ERROR_MESSAGES_UPDATE_THEME = "error_update_theme";

	/**
	 * Message d'erreur de mise à jour du thème pour l'utilisateur
	 */
	public static final String ERROR_MESSAGES_UPDATE_THEME_NOT_FOUND = "error_update_theme_not_found";

}
