/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.site.data;

import java.io.Serializable;

/**
 * Backing bean portant les informations du theme rattache a l'utilisateur courant
 * @author
 *
 */
public class ThemeUserBean implements Serializable {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 4915336070214635341L;

	/**
	 * Nom du theme de l'utilisateur
	 */
	private String userThemeName;

	/**
	 * Getter du theme rattache a l'utilisateur
	 * @return the userThemeName
	 */
	public String getUserThemeName() {
		return userThemeName;
	}

	/**
	 * Setter du theme rattache a l'utilisateur
	 * @param userThemeName the userThemeName to set
	 */
	public void setUserThemeName(String userThemeName) {
		this.userThemeName = userThemeName;
	}

}
