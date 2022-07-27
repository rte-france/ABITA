/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.access;

import com.dto.UserDTO;
import com.services.access.exception.AccessServiceFacadeException;

/**
 *
 * @author
 *
 */
public interface IAccessServiceFacade {

	/**
	 * Authentification de l'utilisateur
	 *
	 * @param login le login
	 * @param passwd le mot de passe
	 * @return l'utilisateur
	 * @throws AccessServiceFacadeException Si une erreur survient
	 */
	UserDTO login(String login, String passwd) throws AccessServiceFacadeException;

}
