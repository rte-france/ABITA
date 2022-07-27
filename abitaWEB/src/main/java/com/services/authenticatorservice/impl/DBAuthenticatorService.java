/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.authenticatorservice.impl;

import com.dto.CompanyUserInfoDTO;
import com.dto.UserDTO;
import com.dto.UserInfoDTO;
import com.services.authenticatorservice.AuthenticatorService;
import com.services.authenticatorservice.exception.*;
import com.services.common.util.SecurityUtil;
import com.services.user.IUserService;
import com.services.user.exception.UserNotFoundException;
import com.services.user.exception.UserServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Use database User to login.
 */
public class DBAuthenticatorService implements AuthenticatorService, Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -4589349267651052650L;
	/** logger. */
	private static final Log LOG = LogFactory.getLog(com.services.authenticatorservice.impl.DBAuthenticatorService.class);

	/** Service pour la gestion des utilisateurs */
	private IUserService userService;

	/* (non-Javadoc)
	 * @see com.services.authenticatorservice.AuthenticatorService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public UserInfoDTO login(final String login, final String passwd) throws LoginException, InvalidLoginException,
			InvalidPasswdException, PermissionException, InvalidAccountStateException, AuthenticatorServiceException {

		// Get the user in the data base
		UserDTO user = getDataBaseUser(login, passwd);

		/* Si stockage mot de passe encodé
		String encodedPasswd = user.getPasswd();
		String decodedPasswd = SecurityUtil.decode(encodedPasswd, SecurityUtil.SECRET_KEY);
		*/
		if (!passwd.equals(user.getPasswd())) {
			throw new LoginException("Le mot de passe n'est pas valide");
		}
		if (!user.getActivated()) {
			throw new InvalidAccountStateException("user login '" + login + "' is disabled",
					InvalidAccountStateException.ACCOUNT_DISABLED);
		}

		return UserInfoHelper.mapUser2UserInfo(user);
	}

	/**
	 * Gets the user from the data base.
	 *
	 * @param login user login.
	 * @param password user pwd.
	 * @return the user.
	 * @throws LoginException Si la connection de l'utilisateur échoue
	 * @throws AuthenticatorServiceException Si une erreur survient
	 */
	private UserDTO getDataBaseUser(final String login, final String password) throws LoginException,
			AuthenticatorServiceException {
		UserDTO user = null;
		try {
			user = userService.findByLogin(login);
		} catch (UserNotFoundException e) {
            throw new LoginException(e);
        } catch (final UserServiceException userServiceException) {
			throw new AuthenticatorServiceException("Erreur pendant la récupération de l'utilisateur", userServiceException);
		}
        return user;
	}

	@Override
	public void checkLogin(final String login, final String password) throws LoginException,
			InvalidLoginException, InvalidPasswdException, PermissionException, InvalidAccountStateException,
			AuthenticatorServiceException {
		LOG.warn("Attention l'authentification se fera via la base de données! N'oubliez pas de tester la connexion LDAP!");
		// The method login(login, password) will throw
		//       * LoginException if the password is not OK
		//       * InvalidAccountStateException if the the user is disabled
		login(login, password);
	}
	/**
	 * Setter de {@link com.services.authenticatorservice.impl.DBAuthenticatorService#userService}
	 *
	 * @param userService la nouvelle valeur à affecter
	 */
	public void setUserService(final IUserService userService) {
		this.userService = userService;
	}
}
