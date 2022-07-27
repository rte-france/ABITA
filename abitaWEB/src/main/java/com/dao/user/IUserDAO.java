/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.user;

import com.dao.common.IAbstractDAO;
import com.dao.user.entity.User;
import com.dao.user.exception.UserDAOException;
import com.dto.ThemeDTO;
import com.dto.UsersSearchDTO;
import com.services.user.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * DAO Interface for User
 *
 * @author
 */
public interface IUserDAO extends IAbstractDAO<User, UserDAOException> {

	/**
	 * Get number of users not in filtred login
	 *
	 * @param filtredLogin filtered login
	 * @return user count
	 * @throws UserDAOException If an error occurs
	 */
	int getCount(List<String> filtredLogin) throws UserDAOException;

	/**
	 * find a user by login
	 *
	 * @param login user login
	 * @return User found
	 * @throws UserDAOException If an error occurs
     * @throws UserNotFoundException If an error occurs
	 */
	User findByLogin(String login) throws UserDAOException, UserNotFoundException;

	/**
	 * find a user by logical id.
	 * @param logicalId logical id
	 * @return User found
	 * @throws UserDAOException If an error occurs
     * @throws UserNotFoundException If an error occurs
	 */
	User findByLogicalId(String logicalId) throws UserDAOException, UserNotFoundException;

	/**
	 * @param start first row to retrieve
	 * @param range number max of results
	 * @param sortProperty The property sorted by
	 * @param ascending type of sort (ascending or descending)
	 * @param filtredLogin filtered logins
	 * @return user list
	 * @throws UserDAOException If an error occurs
	 */
	List<User> getListSortedBy(int start, int range, String sortProperty, boolean ascending, List<String> filtredLogin)
			throws UserDAOException;

	/**
	 * Permet de récupérer la liste des utilisateus, basé sur un filtre
	 *
	 * @param usersSearchDTO des paramètres de recherche
	 * @return une liste d'utilisateurs
	 * @throws UserDAOException If an error occurs
	 */
	List<User> getUsersList(UsersSearchDTO usersSearchDTO) throws UserDAOException;

	/**
	 * Update user, immediately persisted.
	 *
	 * @param user the user to update
	 * @throws UserDAOException If an error occurs
	 */
	void updateImmediately(User user) throws UserDAOException;

	/**
	 * Gets real users (the anonymous user is not returned)
	 *
	 * @return the real users
	 * @throws UserDAOException If an error occurs
	 */
	Map<String, User> getRealUsersIdentifiedByLogin() throws UserDAOException;

	/**
	 * Cette méthode permet de mettre à jour le thème. Sera supprimée en 4.6.0
	 * @param id l'id de l'utilisateur
	 * @param theme le thème de mise à jour
     * @throws UserDAOException If an error occurs
	 */
	void updateTheme(Long id, ThemeDTO theme) throws UserDAOException;
}
