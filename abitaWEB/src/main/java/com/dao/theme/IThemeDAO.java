/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.theme;

import com.dao.common.IAbstractDAO;
import com.dao.theme.entity.Theme;
import com.dao.theme.exception.ThemeDAOException;

/**
 * ThemeDAOImpl interface
 *
 * @author
 */
public interface IThemeDAO extends IAbstractDAO<Theme, ThemeDAOException> {

	/**
	 * Find the theme with the specified technical name
	 *
	 * @param themeName the searched theme's technical name
	 *
	 * @return the theme if found
	 *
	 * @throws ThemeDAOException If an error occurs
	 */
	Theme findByName(String themeName) throws ThemeDAOException;
}
