package com.dao.audit.impl;

import com.services.auditservice.exception.DataAccessException;
import com.web.audit.data.AuthorizedUser;
import com.web.audit.data.Count;
import com.web.audit.data.Duration;
import com.web.audit.data.UserLogged;

/**
 * Interface for data access
 * @author
 *
 */
public interface ActivityTrackerDataAccess {

	/**
	 * Save the duration of an action given its application and component.
	 *
	 * @param duration the duration object to save.
	 * @throws DataAccessException if there's a problem during reading/writing data.
	 */
	void saveDuration(Duration duration) throws DataAccessException;

	/**
	 *
	 * @param count the count object to save / update
	 * @throws DataAccessException if there's a problem during reading/writing data.
	 */
	void saveCount(Count count) throws DataAccessException;

	/**
	 *
	 * @param userInformations the user information object to save / update
	 * @throws DataAccessException if there's a problem during reading/writing data.
	 */
	void saveUser(UserLogged userInformations) throws DataAccessException;

	//FFT 3562
	/**
	 *
	 * @param authorizedUser the user information object to save / update
	 * @throws DataAccessException if there's a problem during reading/writing data.
	 */
	void saveAuthorizedUser(AuthorizedUser authorizedUser) throws DataAccessException;

}
