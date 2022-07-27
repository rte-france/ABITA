/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.services.actionlogs.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;

import com.dao.actionlogs.IActionLogDAO;
import com.dao.actionlogs.entity.ActionLog;
import com.dao.actionlogs.exception.ActionLogDAOException;
import com.dao.common.entity.AbstractEntity;
import com.dto.ActionLogDTO;
import com.dto.ActionLogFilterDTO;
import com.services.actionlogs.IActionLogService;
import com.services.actionlogs.constants.ActionLogServiceConstants;
import com.services.actionlogs.constants.ActionLogsConstants;
import com.services.actionlogs.exception.ActionLogServiceException;
import com.services.common.exception.TooManyResultsException;
import com.services.common.util.DozerUtils;
import com.web.logs.constants.ActionLogConstants;

/**
 * Implementation de {@link IActionLogService}
 *
 * La méthode {@link #getSpecificDtoClass()} doit être surchargée si un DTO spécifique est utilisé
 *
 * Exigences liées :
 * -	RM_LOG_002 : affichage, filtrage
 * -	RM_LOG_003 : récupération des informations d'un log
 *
 * @author
 */
public class ActionLogServiceImpl implements IActionLogService {
	/** LOGGER */
	private static final Log LOGGER = LogFactory.getLog(com.services.actionlogs.impl.ActionLogServiceImpl.class);

	/** The DAO */
	private IActionLogDAO actionLogDao;

	/** The objects mapper */
	private Mapper mapper;

	/**
	 * Map entities list to DTOs list
	 *
	 * @param list The list retrieved from DAO
	 *
	 * @return The DTO list
	 */
	private List<ActionLogDTO> mapList(List<? extends AbstractEntity> list) {
		return this.mapList(list, null);
	}

	/**
	 * Map entities list to DTOs list
	 *
	 * @param list The list retrieved from DAO
	 * @param mapperId L'identifiant du mapper à utiliser
	 *
	 * @return The DTO list
	 */
	private List<ActionLogDTO> mapList(List<? extends AbstractEntity> list, String mapperId) {
		return DozerUtils.map(mapper, list, getSpecificDtoClass(), mapperId);
	}

	/* (non-Javadoc)
	 * @see com.services.reporting.ReportingService#get(java.lang.Long)
	 */
	@Override
	public ActionLogDTO get(Long id) throws ActionLogServiceException {
		if (id == null) {
			throw new IllegalArgumentException("Identifier required");
		}

		try {
			ActionLog log = actionLogDao.get(id);
			if (log == null) {
				ActionLogServiceException ex = new ActionLogServiceException("Entity #" + id + " not found");
				LOGGER.error("Error while retrieving one log", ex);
				throw ex;
			}
			return mapper.map(log, getSpecificDtoClass());
		} catch (ActionLogDAOException actionLogDaoException) {
			LOGGER.error("Error while retrieving one log", actionLogDaoException);
			throw new ActionLogServiceException(actionLogDaoException);
		}
	}

	@Override
	public List<ActionLogDTO> find() throws ActionLogServiceException, TooManyResultsException {
		return this.find(null);
	}

	/**
	 * Indique si la limite d'objets retournés est active ou non
	 *
	 * @return true si la limite est activée
	 */
	private boolean isResultsLimitEnabled() {
		try {
			return Boolean.parseBoolean(ResourceBundle.getBundle(ActionLogsConstants.BUNDLE_NAME).getString(
					ActionLogsConstants.BUNDLE_LIMIT_ENABLED));
		} catch (MissingResourceException missingResourceException) {
			LOGGER.error("Clé '" + ActionLogsConstants.BUNDLE_LIMIT_ENABLED + "' du bundle non trouvée",
					missingResourceException);
			return false;
		}
	}

	/**
	 * Renvoie le nombre maximal d'objets à retourner
	 *
	 * @return le nombre d'objets max
	 */
	private int getMaxResults() {
		try {
			return Integer.parseInt(ResourceBundle.getBundle(ActionLogsConstants.BUNDLE_NAME).getString(
					ActionLogsConstants.BUNDLE_MAX_RESULTS));
		} catch (MissingResourceException missingResourceException) {
			LOGGER.error("Clé '" + ActionLogsConstants.BUNDLE_MAX_RESULTS + "' du bundle non trouvée",
					missingResourceException);
			return ActionLogsConstants.NO_MAX_RESULT;
		}
	}

	/**
	 * Indique si le service doit renvoyer des objets légers
	 *
	 * @return true si des objets légers doivent être retournés
	 */
	private boolean isLightweightObjectReturnEnabled() {
		try {
			return Boolean.parseBoolean(ResourceBundle.getBundle(ActionLogsConstants.BUNDLE_NAME).getString(
					ActionLogsConstants.BUNDLE_RETURN_LIGHTWEIGHT));
		} catch (MissingResourceException missingResourceException) {
			LOGGER.error("Clé '" + ActionLogsConstants.BUNDLE_RETURN_LIGHTWEIGHT + "' du bundle non trouvée",
					missingResourceException);
			return false;
		}
	}

	@Override
	public List<ActionLogDTO> find(ActionLogFilterDTO filters) throws ActionLogServiceException,
			TooManyResultsException {

		// gestion du statut sélectionné
		// si l'utilisateur souhaite ne pas filtrer sur le statut
		// on le met à null
		adjustStatusFilter(filters);

		// arrondi des dates des filtres
		roundFilterDates(filters);

		// vérification du nombre d'objets
		checkObjectsNumberIfNeeded(filters);

		// mapping de la liste au bon format
		try {
			return mapAndReturnList(actionLogDao.find(filters));
		} catch (ActionLogDAOException actionLogDaoException) {
			LOGGER.error("Erreur pendant la récupération de la liste des objets", actionLogDaoException);
			throw new ActionLogServiceException(actionLogDaoException);
		}
	}

	/**
	 * Ajustement de la valeur pour le DAO
	 * La valeur spécifique à la couche présentation ne doit pas impacter la couche DAO
	 *
	 * @param filters L'objet de filtrage
	 */
	private void adjustStatusFilter(final ActionLogFilterDTO filters) {
		if (filters != null) {
			if (ActionLogConstants.STATUS_ALL.equals(filters.getStatus())) {
				filters.setStatus(null);
			}
		}
	}

	/**
	 * Arrondi des dates
	 * En effet, il serait dommage de louper des résultats du 29/11/2013 ajoutés à 11:02:00
	 * parce que les dates saisies par l'utilisateur sont du 29/11/2013 à 12:00:00
	 *
	 * @param filters L'objet de filtrage contenant les dates à arrondir
	 */
	private void roundFilterDates(final ActionLogFilterDTO filters) {
		if (filters != null) {
			if (filters.getFrom() != null) {
				filters.setFrom(roundDownFilterDate(filters.getFrom()));
			}

			if (filters.getTo() != null) {
				filters.setTo(roundUpFilterDate(filters.getTo()));
			}
		}
	}

	/**
	 * Renvoie la date représentant le premier instant du jour, c'est-à-dire avec tous les champs initialisés à 0
	 * Ex: 29/11/2011 00:00:00,000
	 *
	 * @param currentDate la date à tronquer
	 *
	 * @return la date tronquée
	 */
	private Date roundDownFilterDate(final Date currentDate) {
		return DateUtils.truncate(currentDate, Calendar.DAY_OF_MONTH);
	}

	/**
	 * Renvoie la date représentant la fin de la journée c'est-à-dire avec tous les champs à leur valeur maximale
	 * Ex: 29/11/2011 23:59:59,999
	 *
	 * @param currentDate la date du jour à maxer
	 *
	 * @return la date maxée
	 */
	private Date roundUpFilterDate(final Date currentDate) {
		Date tomorrow = DateUtils.addDays(DateUtils.truncate(currentDate, Calendar.DATE), 1);
		return DateUtils.addMilliseconds(tomorrow, ActionLogServiceConstants.MILLISECOND_TO_ADD);
	}

	/**
	 * Vérifie que le nombre d'objets est inférieur à la limite fixée
	 *
	 * @param filters L'objet contenant les filtres
	 *
	 * @throws ActionLogServiceException Si une erreur survient pendant la récupération du nombre d'objets
	 * @throws TooManyResultsException S'il y a trop d'objets à retourner
	 */
	private void checkObjectsNumberIfNeeded(final ActionLogFilterDTO filters) throws ActionLogServiceException,
			TooManyResultsException {
		if (isResultsLimitEnabled()) {
			int max = getMaxResults();
			if (max < 0) {
				throw new ActionLogServiceException("La valeur de la propriété "
						+ ActionLogsConstants.BUNDLE_MAX_RESULTS + " doit être un nombre entier positif");
			}

			int count = 0;
			try {
				count = actionLogDao.getCount(filters);
				if (count > max) {
					throw new TooManyResultsException("Le nombre de résulats dépasse la limite spécifiée");
				}
			} catch (ActionLogDAOException actionLogDaoException) {
				LOGGER.error("Erreur pendant la récupération du nombre d'objets", actionLogDaoException);
				throw new ActionLogServiceException("Erreur pendant la récupération du nombre d'objets",
						actionLogDaoException);
			}
		}
	}

	/**
	 * Renvoie la liste après avoir converti les entités en DTOs
	 * La liste est mappé selon la valeur de la propriété dans le fichier de configuration
	 *
	 * @param entities La liste des entités
	 *
	 * @return la liste convertie
	 */
	private List<ActionLogDTO> mapAndReturnList(final List<ActionLog> entities) {
		if (isLightweightObjectReturnEnabled()) {
			return this.mapList(entities, ActionLogsConstants.MAPPER_ID_LIGHT);
		} else {
			return this.mapList(entities);
		}
	}

	@Override
	public Long create(final ActionLogDTO actionLog) throws ActionLogServiceException {
		if (actionLog == null) {
			throw new IllegalArgumentException("Object must not be null");
		}
		ActionLog actionLogEntity = mapper.map(actionLog, ActionLog.class);
		try {
			return (Long) actionLogDao.create(actionLogEntity);
		} catch (ActionLogDAOException actionLogDaoException) {
			LOGGER.error("Error while persisting object", actionLogDaoException);
			throw new ActionLogServiceException(actionLogDaoException);
		}
	}

	@Override
	public ActionLogDTO update(ActionLogDTO actionLog) throws ActionLogServiceException {
		if (actionLog == null) {
			throw new IllegalArgumentException("Object must not be null");
		}
		ActionLog actionLogEntity = mapper.map(actionLog, ActionLog.class);
		try {
			ActionLog updatedEntity = actionLogDao.update(actionLogEntity);
			return mapper.map(updatedEntity, getSpecificDtoClass());
		} catch (ActionLogDAOException actionLogDaoException) {
			LOGGER.error("Error while updating object", actionLogDaoException);
			throw new ActionLogServiceException(actionLogDaoException);
		}
	}

	/**
	 * Renvoie la classe des objets côté présentation
	 *
	 * @return La classe utilisée côté présentation
	 */
	public Class<ActionLogDTO> getSpecificDtoClass() {
		return ActionLogDTO.class;
	}

	/* *************************************************************************
	 * INJECTIONS
	 ************************************************************************ */

	/**
	 * @param actionLogDao the actionLogDao to set
	 */
	public void setActionLogDao(IActionLogDAO actionLogDao) {
		this.actionLogDao = actionLogDao;
	}

	/**
	 * @param mapper the mapper to set
	 */
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}
}
