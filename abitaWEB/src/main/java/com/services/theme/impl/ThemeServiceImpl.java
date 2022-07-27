/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.theme.impl;

import com.dao.theme.IThemeDAO;
import com.dao.theme.entity.Theme;
import com.dao.theme.exception.ThemeDAOException;
import com.dto.ThemeDTO;
import com.services.common.exception.NotFoundException;
import com.services.theme.IThemeService;
import com.services.theme.exception.ThemeServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation de {@link IThemeService}
 *
 * @author
 */
public class ThemeServiceImpl implements IThemeService {
	/** Le loggueur */
	private static final Log LOGGER = LogFactory.getLog(com.services.theme.impl.ThemeServiceImpl.class);

	/** Le DAO associé */
	private IThemeDAO themeDao;

	/** Le mapper Entités <=> DTOs */
	private Mapper mapper;

	@Override
	public Map<String, String> getThemes() throws ThemeServiceException {
		try {
			final List<Theme> entities = this.themeDao.find();
			Map<String, String> map = new HashMap<String, String>(entities.size());

			for (final Theme t : entities) {
				map.put(t.getLabel(), t.getName());
			}

			return map;
		} catch (final ThemeDAOException themeDaoException) {
			LOGGER.error("Erreur pendant la récupération de la liste des thèmes");
			throw new ThemeServiceException(themeDaoException);
		}
	}

	@Override
	public ThemeDTO getByName(final String name) throws ThemeServiceException {
		try {
			Theme theme = this.themeDao.findByName(name);
			if (theme == null) {
				throw new ThemeServiceException(new NotFoundException("Le thème spécifié n'existe pas"));
			}
			return this.mapper.map(theme, ThemeDTO.class);
		} catch (final ThemeDAOException themeDaoException) {
			throw new ThemeServiceException(themeDaoException);
		}
	}

	/**
	 * @param themeDao the themeDao to set
	 */
	public void setThemeDao(final IThemeDAO themeDao) {
		this.themeDao = themeDao;
	}

	/**
	 * @param mapper the mapper to set
	 */
	public void setMapper(final Mapper mapper) {
		this.mapper = mapper;
	}
}
