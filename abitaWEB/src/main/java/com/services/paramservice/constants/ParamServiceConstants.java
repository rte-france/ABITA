/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.paramservice.constants;

/**
 * Regroupe l'ensemble des clefs des domaines et des paramètres nomenclature: nomdudomaine_DOMAIN
 * nomduparametre_PARAMETER
 */
public final class ParamServiceConstants {

	/**
	 * Private constructor
	 */
	private ParamServiceConstants() {
		super();
	}

	/**
	 * clef du domaine générale
	 */
	public static final String GENERAL_DOMAIN_KEY = "general";
	/**
	 * clef du domaine access lié au service access
	 */
	public static final String ACCESS_DOMAIN_KEY = "access";
	/**
	 * clef du domaine net
	 */
	public static final String NET_DOMAIN_KEY = "net";

//    /** SECURITY_KEY. */
//    public static final String ROOT_USER_LOGIN_KEY = "root";

}
