/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.authenticatorservice;

import com.dto.CompanyUserInfoDTO;
import com.dto.UserInfoDTO;
import com.services.authenticatorservice.exception.*;
import com.services.common.exception.NotFoundException;

import java.util.Collection;

/**
 *
 * @author
 *
 */
public interface AuthenticatorService {

	/**
	 * Connexion de l'utilisateur
	 *
	 * @param login le login de l'utilisateur
	 * @param password le mot de passe
	 *
	 * @return les informations de l'utilisateur
	 *
	 * @throws LoginException Si l'un des identifiants n'est pas valide
	 * @throws InvalidLoginException Si le login est invalide
	 * @throws InvalidPasswdException Si le mot de passe est invalide
	 * @throws PermissionException Si l'utilisateur n'a aucun droit
	 * @throws InvalidAccountStateException Si l'utilisateur est désactivé
	 * @throws AuthenticatorServiceException Si une erreur survient
	 */
	UserInfoDTO login(String login, String password) throws LoginException, InvalidLoginException, InvalidPasswdException,
			PermissionException, InvalidAccountStateException, AuthenticatorServiceException;

	/**
	 * Backend for user authentication
	 * @param login the user login
	 * @param password the user password
	 * @return the user properties stored in the backend
	 *
	 * @throws LoginException in case of failure
	 * @throws InvalidLoginException if login is invalid
	 * @throws InvalidPasswdException in case of bad password
	 * @throws PermissionException if user is not authorized to access the application
	 * @throws InvalidAccountStateException if user is disabled
	 * @throws AuthenticatorServiceException object returned by LDAP is null
	 * @throws NotFoundException Si l'utilisateur n'existe pas
	 */
	void checkLogin(String login, String password) throws LoginException, InvalidLoginException,
			InvalidPasswdException, PermissionException, InvalidAccountStateException, AuthenticatorServiceException,
			NotFoundException;


}
