/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.userslist.constants;

/**
 * Classe de constante des utilisateurs
 * @author
 *
 */
public final class UserConstants {

	/**
	 * Constructeur prive
	 */
	private UserConstants() {
	}

	/**
	 * Classe de style de l'utilisateur active
	 */
	public static final String STYLE_CLASSES_USER_ACTIVATED = "styleClassUserActivated";

	/**
	 * Classe de style de l'utilisateur desactive
	 */
	public static final String STYLE_CLASSES_USER_DESACTIVATED = "styleClassUserDesactivated";

	/** label user actived */
	public static final String USER_LIST_ACTIVATION_OFF = "user.list.activation.off";

	/** label user disactived */
	public static final String USER_LIST_ACTIVATION_ON = "user.list.activation.on";

	/** label user disactived */
	public static final String USER_LIST_ACTIVATION_ALL = "user.activated.all";

}
