/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.user.impl;

import com.dao.user.IUserDAO;
import com.dao.user.entity.User;
import com.dao.user.exception.UserDAOException;
import com.dto.ThemeDTO;
import com.dto.UserDTO;
import com.dto.UsersSearchDTO;
import com.services.common.exception.NotFoundException;
import com.services.common.impl.AbstractService;
import com.services.common.util.DozerUtils;
import com.services.theme.IThemeService;
import com.services.theme.exception.ThemeServiceException;
import com.services.user.IUserService;
import com.services.user.exception.UserNotFoundException;
import com.services.user.exception.UserServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implementation de {@link IUserService}
 *
 * @author
 */
public class UserServiceImpl extends
		AbstractService<User, UserDTO, UserDAOException, Long, UserServiceException, IUserDAO> implements IUserService {

	/** L'identifiant du mapping à utiliser pour renvoyer des utilisateurs allégés, ie sans thème et qu'avec les groupes directs */
	private static final String MAPPING_ID_USER_LIGHT = "user-light";

	/** Le DAO associé */
	private IUserDAO userDao;

    /** Service pour la récupération des thèmes */
    private IThemeService themeService;

	@Override
	public void disable(final UserDTO user) throws UserServiceException {
		user.setActivated(false);
		update(user);
	}

	@Override
	public UserDTO findByLogin(final String login) throws UserServiceException, UserNotFoundException {
		try {
			final User entity = this.getSpecificIDAO().findByLogin(login);
			if (entity == null) {
				throw new UserNotFoundException(new NotFoundException());
			}
			return mapper.map(entity, getSpecificDTOClass());
		} catch (final UserDAOException userDaoException) {
			throw new UserServiceException(userDaoException);
		}
	}

	@Override
	public int getCount(final List<String> filteredLogins) throws UserServiceException {
		try {
			return getSpecificIDAO().getCount(filteredLogins);
		} catch (final UserDAOException userDaoException) {
			throw new UserServiceException(userDaoException);
		}
	}

	@Override
	public List<UserDTO> find(final UsersSearchDTO usersSearch) throws UserServiceException {
		try {
			List<User> entities = getSpecificIDAO().getUsersList(usersSearch);
			return DozerUtils.map(mapper, entities, UserDTO.class, MAPPING_ID_USER_LIGHT);
		} catch (final UserDAOException userDaoException) {
			throw new UserServiceException(userDaoException);
		}
	}

    @Override
    public UserDTO updateUserTheme(UserDTO currentUser, final String themeName) throws UserServiceException {
		try {
            // récupération du thème
            final ThemeDTO theme = this.themeService.getByName(themeName);

            if (currentUser != null && theme != null) {
                userDao.updateTheme(currentUser.getId(), theme);
                currentUser.setTheme(theme);
            }
        } catch (final UserDAOException e) {
            throw new UserServiceException(e);
        } catch (ThemeServiceException e) {
			throw new UserServiceException(e);
		}
		return currentUser;
    }

    @Override
    protected IUserDAO getSpecificIDAO() {
        return userDao;
    }

    @Override
    protected Class<User> getSpecificEntityClass() {
        return User.class;
    }

    @Override
    protected String getSpecificClassName() {
        return com.services.user.impl.UserServiceImpl.class.getName();
    }

    @Override
    protected UserServiceException createException(final Exception exception) {
        return new UserServiceException(exception);
    }

    /**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(final IUserDAO userDao) {
		this.userDao = userDao;
	}

    /**
     * @param themeService the themeService to set
     */
    public void setThemeService(IThemeService themeService) {
        this.themeService = themeService;
    }
}
