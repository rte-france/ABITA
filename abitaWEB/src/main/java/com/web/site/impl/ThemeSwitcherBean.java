/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.site.impl;

import com.services.common.util.MessageSupport;
import com.services.theme.IThemeService;
import com.services.theme.exception.ThemeServiceException;
import com.web.site.constants.SiteConstants;

import javax.faces.application.FacesMessage;
import java.util.Map;
import java.util.TreeMap;

/**
 * Singleton des themes disponibles dans l'application
 * @author
 *
 */
public enum ThemeSwitcherBean {

	/**
	 * Propriete de l'instance du singleton
	 */
	INSTANCE;

	/**
	 * Permet de recuperer les themes disponibles dans l'application
	 * @param themeService le service pour la récupération des thèmes
	 *
	 * @return une map de themes
	 */
	public Map<String, String> getTheme(final IThemeService themeService) {

		Map<String, String> themeMap = null;
		try {
			themeMap = new TreeMap<String, String>(themeService.getThemes());
		} catch (final ThemeServiceException themeServiceException) {
			MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, SiteConstants.ERROR_MESSAGES_GET_ALL_THEME);
		}
		return themeMap;
	}

}
