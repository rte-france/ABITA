/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.constants;

/**
 * Access constant.
 */
public final class AccessConstants {

	/**
	 * Private constructor
	 */
	private AccessConstants() {
		super();
	}

	/**
	 * Parameter ident for default group parameter in  ParameterService
	 */
	public static final String DEFAULT_GROUP_KEY = "defaultUserGroup";
	/**
	 * Parameter ident for enableUserUpdate parameter in  ParameterService
	 */
	public static final String USER_UPDATE_KEY = "enableUserUpdate";

	/**
	 * the bundle key for access service
	 */
	public static final String ACCESS_BUNDLE = "com.access.resource.access";

	/** Fatal error message's key */
	public static final String ERROR_FATAL = "error_fatal";

	/** Clé du message d'erreur indiquant que le groupe n'a pas été trouvé */
	public static final String ERROR_DEFAULT_GROUP_NOT_FOUND = "error_defaultgroup_not_found";

	/** Clé du message d'erreur indiquant que le groupe n'a pas pu être récupéré */
	public static final String ERROR_DEFAULT_GROUP_RETRIEVE = "error_defaultgroup_retrieve";

	/** Clé du message d'erreur indiquant que le groupe par défaut n'a pas pu être mis à jour */
	public static final String ERROR_DEFAULT_GROUP_SAVE = "error_defaultgroup_save";

	/** Clé du message d'information indiquant que le groupe par défaut à été mis à jour */
	public static final String INFO_DEFAULT_GROUP_SAVE = "info_defaultgroup_save";

	/** Clé du message indiquant qu'une erreur s'est produite pendant la récupération des groupes par défaut */
	public static final String ERROR_INIT_DEFAULT_GROUPS = "error_init_defaultgroups";

	/** Clé du messsage indiquant que l'un des groupes par défaut n'est pas correct */
	public static final String WARN_INIT_DEFAULT_GROUPS = "warn_defaultgroup_init";

	/** Clé du messsage indiquant que le groupe racine n'existe pas */
	public static final String ERROR_INIT_ROOT_GROUP = "error_init_root_group";
}
